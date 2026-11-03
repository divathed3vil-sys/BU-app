package com.bu.core.command

import com.bu.core.event.AggregateTypes
import com.bu.core.event.DomainEvent
import com.bu.core.event.EffectTypes
import com.bu.core.event.EventTypes
import com.bu.core.ledger.CarryRow
import com.bu.core.ledger.DayContractRow
import com.bu.core.ledger.FocusSessionRow
import com.bu.core.ledger.ObjectRow
import com.bu.core.ledger.RelationshipRow
import com.bu.core.ledger.ReminderRow
import com.bu.core.ledger.RevisionRow
import com.bu.core.ledger.TaskRow
import com.bu.core.ledger.TrackRow
import com.bu.core.time.BuClock
import javax.inject.Inject
import java.util.UUID
import kotlinx.serialization.json.Json

/**
 * The BU Temporal Kernel gateway — the ONLY mutation path in BU (locked plan §5.2).
 *
 * Flow: envelope → idempotency check → validate → transactional commit
 * (state + events + outbox in one transaction) → receipt.
 * Effects (alarms, interpretation, widget refresh) are applied after commit by
 * the outbox processor, at-least-once, idempotent by effect key.
 *
 * The kernel may read the ledger while deciding (overlap checks, focus-state
 * checks); it may never write outside a transaction.
 */
class BtkGateway @Inject constructor(
    private val inbox: CommandInboxStore,
    private val events: EventStore,
    private val outbox: OutboxStore,
    private val captures: CaptureStore,
    private val domain: DomainStores,
    private val tx: TransactionRunner,
    private val clock: BuClock,
) : CommandGateway {

    override suspend fun submit(command: BuCommand): CommandReceipt {
        val key = command.idempotencyKey ?: newId()

        // I5 — retry safety: a committed/received command with the same key returns its original receipt.
        inbox.findByIdempotencyKey(key)?.let { existing ->
            return CommandReceipt(
                commandId = existing.commandId,
                idempotencyKey = key,
                status = existing.status,
                reason = if (existing.status == CommandStatus.COMMITTED) null else existing.errorCode,
            )
        }

        val commandId = newId()
        val now = clock.nowMs()
        val envelope = CommandEnvelope(
            commandId = commandId,
            idempotencyKey = key,
            commandType = command.commandType,
            source = command.source,
            createdAtMs = now,
            actor = command.source.name,
            payloadJson = command.payloadJson(),
        )
        inbox.insert(
            CommandRecord(
                commandId = commandId,
                idempotencyKey = key,
                commandType = envelope.commandType,
                status = CommandStatus.RECEIVED,
                source = envelope.source.name,
                payloadJson = envelope.payloadJson,
                createdAtMs = now,
                updatedAtMs = now,
            ),
        )
        events.append(receivedEvent(envelope, now, key))

        return when (val verdict = Validators.validate(command)) {
            is Verdict.Reject -> {
                inbox.updateStatus(commandId, CommandStatus.REJECTED, verdict.reason, clock.nowMs())
                events.append(
                    DomainEvent(
                        eventId = newId(),
                        type = EventTypes.COMMAND_REJECTED,
                        aggregateId = commandId,
                        aggregateType = AggregateTypes.COMMAND,
                        occurredAtMs = clock.nowMs(),
                        recordedAtMs = clock.nowMs(),
                        actor = envelope.actor,
                        payloadJson = envelope.payloadJson,
                        correlationId = commandId,
                        idempotencyKey = key,
                    ),
                )
                CommandReceipt(commandId, key, CommandStatus.REJECTED, reason = verdict.reason)
            }

            is Verdict.Commit -> tx.inTransaction { commit(command, envelope) }
        }
    }

    override suspend fun approve(commandId: String, approval: Approval): CommandReceipt {
        val record = inbox.findById(commandId)
            ?: return receiptUnknown(commandId)
        return if (record.status == CommandStatus.AWAITING_APPROVAL) {
            // Approval flows arrive with AI proposals (P6). P1 keeps the contract.
            CommandReceipt(commandId, record.idempotencyKey, record.status, reason = "NO_PENDING_APPROVAL")
        } else {
            CommandReceipt(commandId, record.idempotencyKey, record.status, reason = "NOT_AWAITING_APPROVAL")
        }
    }

    override suspend fun reject(commandId: String, reason: String?): CommandReceipt {
        val record = inbox.findById(commandId)
            ?: return receiptUnknown(commandId)
        if (record.status != CommandStatus.AWAITING_APPROVAL) {
            return CommandReceipt(commandId, record.idempotencyKey, record.status, reason = "NOT_AWAITING_APPROVAL")
        }
        val now = clock.nowMs()
        inbox.updateStatus(commandId, CommandStatus.REJECTED, reason, now)
        events.append(rejectionEvent(record, reason, now))
        return CommandReceipt(commandId, record.idempotencyKey, CommandStatus.REJECTED, reason = reason)
    }

    override suspend fun undo(commandId: String): CommandReceipt {
        val record = inbox.findById(commandId)
            ?: return receiptUnknown(commandId)
        if (record.status != CommandStatus.COMMITTED) {
            return CommandReceipt(commandId, record.idempotencyKey, record.status, reason = "NOT_COMMITTED")
        }
        return tx.inTransaction { undo(record) }
    }

    // ── Undo: compensating commands, audit preserved (I10) ──────────────────

    private suspend fun undo(record: CommandRecord): CommandReceipt {
        val now = clock.nowMs()
        val savedEvent = events.findByCorrelation(record.commandId, EventTypes.CAPTURE_SAVED)
        return if (savedEvent != null) {
            captures.updateStatus(savedEvent.aggregateId, "DISCARDED")
            events.append(
                DomainEvent(
                    eventId = newId(),
                    type = EventTypes.CAPTURE_DISCARDED,
                    aggregateId = savedEvent.aggregateId,
                    aggregateType = AggregateTypes.CAPTURE,
                    occurredAtMs = now,
                    recordedAtMs = now,
                    actor = record.source,
                    payloadJson = record.payloadJson,
                    correlationId = record.commandId,
                ),
            )
            enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, "${savedEvent.aggregateId}:undo", record.payloadJson, now)
            events.append(commandUndoneEvent(record, now))
            CommandReceipt(record.commandId, record.idempotencyKey, CommandStatus.UNDONE)
        } else {
            CommandReceipt(record.commandId, record.idempotencyKey, record.status, reason = "NO_COMPENSATION_DEFINED")
        }
    }

    // ── Commit — one branch per command type ─────────────────────────────────

    private suspend fun commit(command: BuCommand, envelope: CommandEnvelope): CommandReceipt {
        val now = clock.nowMs()
        return when (command) {
            is BuCommand.SaveCapture -> commitCapture(command, envelope, now)
            is BuCommand.InterpretCapture -> commitInterpret(command, envelope, now)
            is BuCommand.PromoteCapture -> commitPromote(command, envelope, now)
            is BuCommand.CreateTrack -> commitCreateTrack(command, envelope, now)
            is BuCommand.UpdateTrack -> commitUpdateTrack(command, envelope, now)
            is BuCommand.ArchiveTrack -> commitArchiveTrack(command, envelope, now)
            is BuCommand.CreateTask -> commitCreateTask(command, envelope, now)
            is BuCommand.TallyTask -> commitTally(command, envelope, now)
            is BuCommand.SetBigThree -> commitBigThree(command, envelope, now)
            is BuCommand.CreateReminder -> commitCreateReminder(command, envelope, now)
            is BuCommand.CompleteReminder -> commitCompleteReminder(command, envelope, now)
            is BuCommand.SnoozeReminder -> commitSnoozeReminder(command, envelope, now)
            is BuCommand.CancelReminder -> commitCancelReminder(command, envelope, now)
            is BuCommand.StartFocus -> commitStartFocus(command, envelope, now)
            is BuCommand.EndFocus -> commitEndFocus(command, envelope, now)
            is BuCommand.SetDayWindow -> commitDayWindow(command, envelope, now)
            is BuCommand.MarkOnboarded -> commitOnboarded(envelope, now)
            is BuCommand.SealTomorrow -> commitSealTomorrow(command, envelope, now)
            is BuCommand.ShrinkTask -> commitShrink(command, envelope, now)
            is BuCommand.KillTask -> commitKill(command, envelope, now)
            is BuCommand.RecordDawnPush -> commitDawnPush(command, envelope, now)
            is BuCommand.SetSetting -> commitSetSetting(command, envelope, now)
        }
    }

    private suspend fun commitCapture(command: BuCommand.SaveCapture, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val captureId = newId()
        val event = DomainEvent(
            eventId = newId(),
            type = EventTypes.CAPTURE_SAVED,
            aggregateId = captureId,
            aggregateType = AggregateTypes.CAPTURE,
            occurredAtMs = now,
            recordedAtMs = now,
            actor = envelope.actor,
            payloadJson = envelope.payloadJson,
            correlationId = envelope.commandId,
            idempotencyKey = envelope.idempotencyKey,
        )
        captures.insert(
            CaptureRecord(
                captureId = captureId,
                rawText = command.text,
                capturedAtMs = now,
                source = command.captureSource,
                status = "RAW_CAPTURED",
                linkedCommandId = envelope.commandId,
            ),
        )
        events.append(event)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, captureId, envelope.payloadJson, now)
        enqueueEffect(
            EffectTypes.CAPTURE_INTERPRET,
            "$captureId:interpret",
            Json.encodeToString(InterpretTicketPayload.serializer(), InterpretTicketPayload(captureId)),
            now,
        )
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(
            commandId = envelope.commandId,
            idempotencyKey = envelope.idempotencyKey,
            status = CommandStatus.COMMITTED,
            eventIds = listOf(event.eventId),
        )
    }

    private suspend fun commitInterpret(command: BuCommand.InterpretCapture, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val capture = captures.findById(command.captureId)
            ?: return rejectInCommit(envelope, "UNKNOWN_CAPTURE", now)
        // Idempotent semantics: an already-interpreted capture is never re-parsed.
        if (capture.status != "RAW_CAPTURED") {
            inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
            return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
        }

        if (!command.isTask) {
            captures.updateStatus(command.captureId, "SPARK")
            appendEvent(EventTypes.CAPTURE_INTERPRETED, command.captureId, AggregateTypes.CAPTURE, envelope, now)
            enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, command.captureId, envelope.payloadJson, now)
        } else {
            val taskId = newId()
            insertTaskObject(
                title = command.title,
                taskClass = "SPARK",
                trackId = null,
                scheduledDate = command.scheduledDateIso,
                startMinute = command.startMinute,
                endMinute = null,
                dueAtMs = null,
                sourceCaptureId = command.captureId,
                estimateMinutes = DEFAULT_SPARK_ESTIMATE,
                now = now,
            )
            captures.linkTask(command.captureId, "PROMOTED_TASK", taskId, now)
            domain.objects.insertRelationship(
                RelationshipRow(
                    relationshipId = newId(),
                    fromId = taskId,
                    toId = command.captureId,
                    type = "derived_from",
                    createdAtMs = now,
                ),
            )
            appendEvent(EventTypes.CAPTURE_PROMOTED, taskId, AggregateTypes.TASK, envelope, now)
            enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, taskId, envelope.payloadJson, now)
        }
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitPromote(command: BuCommand.PromoteCapture, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val capture = captures.findById(command.captureId)
            ?: return rejectInCommit(envelope, "UNKNOWN_CAPTURE", now)
        if (capture.status == "PROMOTED_TASK") {
            inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
            return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
        }
        val taskId = newId()
        insertTaskObject(
            title = capture.rawText.trim(),
            taskClass = "SPARK",
            trackId = null,
            scheduledDate = null,
            startMinute = null,
            endMinute = null,
            dueAtMs = null,
            sourceCaptureId = capture.captureId,
            estimateMinutes = DEFAULT_SPARK_ESTIMATE,
            now = now,
        )
        captures.linkTask(capture.captureId, "PROMOTED_TASK", taskId, now)
        domain.objects.insertRelationship(
            RelationshipRow(
                relationshipId = newId(),
                fromId = taskId,
                toId = capture.captureId,
                type = "derived_from",
                createdAtMs = now,
            ),
        )
        appendEvent(EventTypes.CAPTURE_PROMOTED, taskId, AggregateTypes.TASK, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, taskId, envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitCreateTrack(command: BuCommand.CreateTrack, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val trackId = newId()
        domain.objects.insert(
            ObjectRow(
                objectId = trackId,
                kind = "TRACK",
                title = command.name.trim(),
                createdAtMs = now,
                updatedAtMs = now,
            ),
        )
        domain.tracks.save(
            TrackRow(objectId = trackId, estimateMinutes = command.estimateMinutes, estimateSamples = 0),
        )
        appendEvent(EventTypes.TRACK_CREATED, trackId, AggregateTypes.TRACK, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, trackId, envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitUpdateTrack(command: BuCommand.UpdateTrack, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val existing = domain.objects.byId(command.trackId)
            ?: return rejectInCommit(envelope, "UNKNOWN_TRACK", now)
        if (existing.deletedAtMs != null) return rejectInCommit(envelope, "TRACK_ARCHIVED", now)
        command.name?.let { name -> domain.objects.rename(command.trackId, name.trim(), now) }
        command.estimateMinutes?.let { minutes ->
            val track = domain.tracks.byId(command.trackId)
            if (track != null) {
                domain.tracks.save(track.copy(estimateMinutes = minutes))
            }
        }
        snapshotRevision(command.trackId, now)
        appendEvent(EventTypes.TRACK_UPDATED, command.trackId, AggregateTypes.TRACK, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, command.trackId, envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitArchiveTrack(command: BuCommand.ArchiveTrack, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val existing = domain.objects.byId(command.trackId)
            ?: return rejectInCommit(envelope, "UNKNOWN_TRACK", now)
        if (existing.deletedAtMs != null) {
            inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
            return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
        }
        domain.objects.archive(command.trackId, now)
        domain.tracks.archive(command.trackId, now)
        appendEvent(EventTypes.TRACK_ARCHIVED, command.trackId, AggregateTypes.TRACK, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, command.trackId, envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitCreateTask(command: BuCommand.CreateTask, envelope: CommandEnvelope, now: Long): CommandReceipt {
        // A block may not sit on top of another block — the timeline refuses to lie.
        if (command.taskClass == "BLOCK") {
            val start = command.startMinute ?: 0
            val duration = command.durationMinutes ?: 0
            val conflict = domain.tasks.overlappingBlock(command.scheduledDateIso ?: "", start, start + duration)
            if (conflict != null) {
                return rejectInCommit(envelope, "OVERLAP_BLOCK", now)
            }
        }
        val endMinute = command.startMinute?.let { s -> command.durationMinutes?.let { d -> s + d } }
        val taskId = insertTaskObject(
            title = command.title.trim(),
            taskClass = command.taskClass,
            trackId = command.trackId,
            scheduledDate = command.scheduledDateIso,
            startMinute = command.startMinute,
            endMinute = endMinute,
            dueAtMs = command.dueAtMs,
            sourceCaptureId = command.sourceCaptureId,
            estimateMinutes = command.durationMinutes ?: defaultEstimate(command.taskClass),
            now = now,
        )
        command.sourceCaptureId?.let { captureId ->
            captures.findById(captureId)?.takeIf { it.status != "PROMOTED_TASK" }?.let {
                captures.linkTask(captureId, "PROMOTED_TASK", taskId, now)
                domain.objects.insertRelationship(
                    RelationshipRow(newId(), taskId, captureId, "derived_from", now),
                )
            }
        }
        command.trackId?.let { trackId ->
            domain.objects.byId(trackId)?.let {
                domain.objects.insertRelationship(RelationshipRow(newId(), taskId, trackId, "belongs_to", now))
            }
        }
        appendEvent(EventTypes.TASK_CREATED, taskId, AggregateTypes.TASK, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, taskId, envelope.payloadJson, now)
        enqueueWidgetRefresh(now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitTally(command: BuCommand.TallyTask, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val task = domain.tasks.byId(command.taskId)
            ?: return rejectInCommit(envelope, "UNKNOWN_TASK", now)
        if (task.status !in TALLIABLE_STATUSES) {
            return rejectInCommit(envelope, "TASK_NOT_TALLIABLE", now)
        }

        val tallyDate = when (command.tally) {
            "CARRIED" -> command.toDateIso!!
            else -> task.scheduledDate ?: todayIsoFallback()
        }

        when (command.tally) {
            "DONE" -> {
                domain.tasks.setTally(task.objectId, "DONE", now, tallyDate, task.estimateMinutes)
                domain.evidence.insert(
                    task.objectId, "TASK", "OUTCOME_CONFIRMED",
                    "Owner marked this done", now,
                )
            }
            "PARTIAL" -> {
                domain.tasks.setTally(task.objectId, "PARTIAL", now, tallyDate, task.estimateMinutes)
                domain.evidence.insert(
                    task.objectId, "TASK", "OUTCOME_CONFIRMED",
                    "Owner marked this partially done", now,
                )
            }
            // Carry = cooldown deferral, never shame (locked plan §2). The task
            // simply lives on tomorrow's field with its move count remembered.
            "CARRIED" -> {
                // The moved-3 law (locked plan §2): after three moves the
                // cooldown closes — Do, Shrink, or Kill; the carry lane is done.
                if (task.movedCount >= Validators.CARRY_LIMIT) {
                    return rejectInCommit(envelope, "CARRY_LIMIT_REACHED", now)
                }
                val toDate = command.toDateIso!!
                domain.tasks.insertCarry(
                    CarryRow(
                        carryId = newId(),
                        taskId = task.objectId,
                        fromDate = task.scheduledDate ?: tallyDate,
                        toDate = toDate,
                        movedAtMs = now,
                    ),
                )
                domain.tasks.setSchedule(task.objectId, toDate, task.startMinute, task.movedCount + 1, now)
                domain.evidence.insert(
                    task.objectId, "TASK", "OBSERVED",
                    "Carried to $toDate", now,
                )
            }
        }

        snapshotRevision(task.objectId, now)
        val eventType = if (command.tally == "CARRIED") EventTypes.TASK_CARRIED else EventTypes.TASK_TALLIED
        appendEvent(eventType, task.objectId, AggregateTypes.TASK, envelope, now)
        domain.momentum.recountDay(tallyDate)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, task.objectId, envelope.payloadJson, now)
        enqueueWidgetRefresh(now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitBigThree(command: BuCommand.SetBigThree, envelope: CommandEnvelope, now: Long): CommandReceipt {
        // Yesterday's OPEN slots are superseded — quiet, never deleted, never shamed.
        domain.tasks.replaceOpenBig(command.dateIso, now)

        var mainFrontId: String? = null
        command.slots.forEach { slot ->
            val taskId = insertTaskObject(
                title = slot.title.trim(),
                taskClass = "BIG",
                trackId = null,
                scheduledDate = command.dateIso,
                startMinute = slot.startMinute,
                endMinute = null,
                dueAtMs = null,
                sourceCaptureId = null,
                estimateMinutes = DEFAULT_BIG_ESTIMATE,
                now = now,
            )
            if (slot.isMainFront && mainFrontId == null) mainFrontId = taskId
        }

        val existing = domain.contracts.byDate(command.dateIso)
        domain.contracts.upsert(
            DayContractRow(
                dateIso = command.dateIso,
                mainFrontTaskId = mainFrontId,
                minimumDay = existing?.minimumDay,
                ifThenJson = existing?.ifThenJson,
                ceilingRung = existing?.ceilingRung,
                sealedAtMs = existing?.sealedAtMs,
                wakePlan = existing?.wakePlan,
                mood = existing?.mood,
                updatedAtMs = now,
            ),
        )
        appendEvent(EventTypes.BIG_THREE_SET, command.dateIso, AggregateTypes.CONTRACT, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, command.dateIso, envelope.payloadJson, now)
        enqueueWidgetRefresh(now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitCreateReminder(command: BuCommand.CreateReminder, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val reminderId = newId()
        val overdue = command.dueAtMs <= now
        domain.reminders.insert(
            ReminderRow(
                reminderId = reminderId,
                title = command.title.trim(),
                dueAtMs = command.dueAtMs,
                kind = "CARE",
                careType = command.careType,
                status = if (overdue) "DUE" else "PENDING",
                alarmRevision = 0,
                createdAtMs = now,
                updatedAtMs = now,
            ),
        )
        domain.evidence.insert(
            reminderId, "REMINDER", "SCHEDULED",
            "Care item set for ${command.dueAtMs} · ${CareTypes.label(command.careType)}", now,
        )
        appendEvent(EventTypes.REMINDER_CREATED, reminderId, AggregateTypes.REMINDER, envelope, now)
        enqueueAlarm(reminderId, command.dueAtMs, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, reminderId, envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitCompleteReminder(command: BuCommand.CompleteReminder, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val reminder = domain.reminders.byId(command.reminderId)
            ?: return rejectInCommit(envelope, "UNKNOWN_REMINDER", now)
        if (reminder.status == "DONE" || reminder.status == "CANCELLED") {
            inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
            return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
        }
        domain.reminders.setStatus(reminder.reminderId, "DONE", now)
        domain.evidence.insert(reminder.reminderId, "REMINDER", "OUTCOME_CONFIRMED", "Owner closed this care item", now)
        enqueueCancelAlarm(reminder.reminderId, now)
        appendEvent(EventTypes.REMINDER_COMPLETED, reminder.reminderId, AggregateTypes.REMINDER, envelope, now)

        // Care v3: recurring kinds re-arm themselves — the rhythm continues,
        // quietly, same time tomorrow. One transaction, one truth (I4).
        if (CareTypes.isRecurring(reminder.careType)) {
            val nextId = newId()
            val nextDue = reminder.dueAtMs + 24 * 60 * 60_000L
            domain.reminders.insert(
                ReminderRow(
                    reminderId = nextId,
                    title = reminder.title,
                    dueAtMs = nextDue,
                    kind = "CARE",
                    careType = reminder.careType,
                    status = "PENDING",
                    alarmRevision = 0,
                    createdAtMs = now,
                    updatedAtMs = now,
                ),
            )
            domain.evidence.insert(nextId, "REMINDER", "SCHEDULED", "Rhythm continues — next round tomorrow", now)
            appendEvent(EventTypes.REMINDER_RECURRED, nextId, AggregateTypes.REMINDER, envelope, now)
            enqueueAlarm(nextId, nextDue, now)
        }

        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, reminder.reminderId, envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitSnoozeReminder(command: BuCommand.SnoozeReminder, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val reminder = domain.reminders.byId(command.reminderId)
            ?: return rejectInCommit(envelope, "UNKNOWN_REMINDER", now)
        if (reminder.status == "DONE" || reminder.status == "CANCELLED") {
            return rejectInCommit(envelope, "REMINDER_CLOSED", now)
        }
        val nextDue = reminder.dueAtMs + command.minutes * 60_000L
        domain.reminders.reschedule(reminder.reminderId, nextDue, now)
        appendEvent(EventTypes.REMINDER_SNOOZED, reminder.reminderId, AggregateTypes.REMINDER, envelope, now)
        enqueueCancelAlarm(reminder.reminderId, now)
        enqueueAlarm(reminder.reminderId, nextDue, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, reminder.reminderId, envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitCancelReminder(command: BuCommand.CancelReminder, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val reminder = domain.reminders.byId(command.reminderId)
            ?: return rejectInCommit(envelope, "UNKNOWN_REMINDER", now)
        if (reminder.status == "DONE" || reminder.status == "CANCELLED") {
            inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
            return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
        }
        domain.reminders.setStatus(reminder.reminderId, "CANCELLED", now)
        enqueueCancelAlarm(reminder.reminderId, now)
        appendEvent(EventTypes.REMINDER_CANCELLED, reminder.reminderId, AggregateTypes.REMINDER, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, reminder.reminderId, envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitStartFocus(command: BuCommand.StartFocus, envelope: CommandEnvelope, now: Long): CommandReceipt {
        if (domain.focus.running() != null) {
            return rejectInCommit(envelope, "FOCUS_ALREADY_RUNNING", now)
        }
        val sessionId = newId()
        domain.focus.insert(
            FocusSessionRow(
                sessionId = sessionId,
                trackId = command.trackId,
                plannedMinutes = command.plannedMinutes,
                state = "RUNNING",
                startedAtMs = now,
                startedElapsedMs = clock.elapsedRealtimeMs(),
            ),
        )
        appendEvent(EventTypes.FOCUS_STARTED, sessionId, AggregateTypes.FOCUS_SESSION, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, sessionId, envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitEndFocus(command: BuCommand.EndFocus, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val session = domain.focus.byId(command.sessionId)
            ?: return rejectInCommit(envelope, "UNKNOWN_FOCUS_SESSION", now)
        if (session.state != "RUNNING") {
            return rejectInCommit(envelope, "FOCUS_NOT_RUNNING", now)
        }

        val elapsedNow = clock.elapsedRealtimeMs()
        // Monotonic law (locked plan §6.1): a reboot rolls elapsedRealtime under
        // the recorded start — BU records the gap honestly, it never invents time.
        val rebooted = elapsedNow < session.startedElapsedMs
        val state = if (rebooted) {
            "INTERRUPTED_OR_UNKNOWN"
        } else {
            if (command.completedAsPlanned) "COMPLETED" else "ENDED_EARLY"
        }
        val actualMinutes = if (rebooted) null else ((elapsedNow - session.startedElapsedMs) / 60_000L).toInt()

        val updated = domain.focus.end(session.sessionId, state, now, actualMinutes)
        if (updated == 0) return rejectInCommit(envelope, "FOCUS_NOT_RUNNING", now)

        appendEvent(EventTypes.FOCUS_ENDED, session.sessionId, AggregateTypes.FOCUS_SESSION, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, session.sessionId, envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitDayWindow(command: BuCommand.SetDayWindow, envelope: CommandEnvelope, now: Long): CommandReceipt {
        domain.settings.set(SETTING_DAY_START, command.startMinute.toString())
        domain.settings.set(SETTING_DAY_END, command.endMinute.toString())
        appendEvent(EventTypes.SETTINGS_UPDATED, "day_window", AggregateTypes.SETTINGS, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, "day_window", envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitOnboarded(envelope: CommandEnvelope, now: Long): CommandReceipt {
        domain.settings.set(SETTING_ONBOARDED, "1")
        appendEvent(EventTypes.SETTINGS_UPDATED, "onboarded", AggregateTypes.SETTINGS, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, "onboarded", envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    // ── P2 — rituals, triage, care v3 ─────────────────────────────────────────

    /**
     * Dusk Close's seal: tomorrow's contract lands in one atomic breath —
     * Big Three (or a legal rest day), Minimum Day, If-Then shield, ceiling.
     * The day being closed keeps its mood on its own contract row.
     */
    private suspend fun commitSealTomorrow(command: BuCommand.SealTomorrow, envelope: CommandEnvelope, now: Long): CommandReceipt {
        // The closed day keeps its mood (Receipt step).
        val closedExisting = domain.contracts.byDate(command.closeDateIso)
        domain.contracts.upsert(
            DayContractRow(
                dateIso = command.closeDateIso,
                mainFrontTaskId = closedExisting?.mainFrontTaskId,
                minimumDay = closedExisting?.minimumDay,
                ifThenJson = closedExisting?.ifThenJson,
                ceilingRung = closedExisting?.ceilingRung,
                sealedAtMs = closedExisting?.sealedAtMs,
                wakePlan = closedExisting?.wakePlan,
                mood = command.mood ?: closedExisting?.mood,
                updatedAtMs = now,
            ),
        )

        // Tomorrow's Big Three — the draft replaced quietly, never shamed.
        domain.tasks.replaceOpenBig(command.targetDateIso, now)
        var mainFrontId: String? = null
        command.slots.forEach { slot ->
            val taskId = insertTaskObject(
                title = slot.title.trim(),
                taskClass = "BIG",
                trackId = null,
                scheduledDate = command.targetDateIso,
                startMinute = slot.startMinute,
                endMinute = null,
                dueAtMs = null,
                sourceCaptureId = null,
                estimateMinutes = DEFAULT_BIG_ESTIMATE,
                now = now,
            )
            if (slot.isMainFront && mainFrontId == null) mainFrontId = taskId
        }

        val ifThenJson = if (command.obstacle != null && command.ifThenPlan != null) {
            Json.encodeToString(IfThen.serializer(), IfThen(command.obstacle, command.ifThenPlan))
        } else {
            null
        }
        domain.contracts.upsert(
            DayContractRow(
                dateIso = command.targetDateIso,
                mainFrontTaskId = mainFrontId,
                minimumDay = command.minimumDay?.trim()?.takeIf { it.isNotEmpty() },
                ifThenJson = ifThenJson,
                ceilingRung = command.ceilingRung,
                sealedAtMs = now,
                wakePlan = null,
                mood = null,
                updatedAtMs = now,
            ),
        )
        domain.evidence.insert(
            command.targetDateIso, "CONTRACT", "USER_CONFIRMED",
            "Owner sealed the day at Dusk Close", now,
        )
        appendEvent(EventTypes.RITUAL_SEALED, command.targetDateIso, AggregateTypes.CONTRACT, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, command.targetDateIso, envelope.payloadJson, now)
        enqueueWidgetRefresh(now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    /**
     * Triage escape two: the task returns tomorrow, smaller. Even a task at
     * the carry limit may shrink — shrinking is one of the honest exits.
     */
    private suspend fun commitShrink(command: BuCommand.ShrinkTask, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val task = domain.tasks.byId(command.taskId)
            ?: return rejectInCommit(envelope, "UNKNOWN_TASK", now)
        if (task.status !in TALLIABLE_STATUSES) {
            return rejectInCommit(envelope, "TASK_NOT_TALLIABLE", now)
        }
        domain.tasks.setEstimate(task.objectId, command.newEstimateMinutes, now)
        domain.tasks.insertCarry(
            CarryRow(
                carryId = newId(),
                taskId = task.objectId,
                fromDate = task.scheduledDate ?: command.toDateIso,
                toDate = command.toDateIso,
                movedAtMs = now,
            ),
        )
        domain.tasks.setSchedule(task.objectId, command.toDateIso, task.startMinute, task.movedCount + 1, now)
        domain.evidence.insert(
            task.objectId, "TASK", "OBSERVED",
            "Shrunk to ${command.newEstimateMinutes} min and moved to ${command.toDateIso}", now,
        )
        snapshotRevision(task.objectId, now)
        appendEvent(EventTypes.TASK_SHRUNK, task.objectId, AggregateTypes.TASK, envelope, now)
        domain.momentum.recountDay(task.scheduledDate ?: command.toDateIso)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, task.objectId, envelope.payloadJson, now)
        enqueueWidgetRefresh(now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    /** Triage escape three: let go. Soft delete — the tombstone stays readable (I10). */
    private suspend fun commitKill(command: BuCommand.KillTask, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val task = domain.tasks.byId(command.taskId)
            ?: return rejectInCommit(envelope, "UNKNOWN_TASK", now)
        if (task.status == "KILLED") {
            inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
            return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
        }
        domain.tasks.kill(task.objectId, now)
        domain.objects.archive(task.objectId, now)
        domain.evidence.insert(task.objectId, "TASK", "USER_CONFIRMED", "Owner let this one go", now)
        appendEvent(EventTypes.TASK_KILLED, task.objectId, AggregateTypes.TASK, envelope, now)
        domain.momentum.recountDay(task.scheduledDate ?: todayIsoFallback())
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, task.objectId, envelope.payloadJson, now)
        enqueueWidgetRefresh(now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    /**
     * Dawn Push, recorded once per day. The first move becomes today's spark.
     * A second dawn the same day commits as a no-op — mornings are not doubled.
     */
    private suspend fun commitDawnPush(command: BuCommand.RecordDawnPush, envelope: CommandEnvelope, now: Long): CommandReceipt {
        val existing = events.findByTypeAndAggregate(EventTypes.DAWN_PUSH_DONE, command.dateIso)
        if (existing != null) {
            inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
            return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
        }
        if (!command.firstMove.isNullOrBlank()) {
            insertTaskObject(
                title = command.firstMove.trim(),
                taskClass = "SPARK",
                trackId = null,
                scheduledDate = command.dateIso,
                startMinute = null,
                endMinute = null,
                dueAtMs = null,
                sourceCaptureId = null,
                estimateMinutes = DEFAULT_SPARK_ESTIMATE,
                now = now,
            )
        }
        domain.evidence.insert(command.dateIso, "CONTRACT", "OBSERVED", "Dawn Push recorded", now)
        appendEvent(EventTypes.DAWN_PUSH_DONE, command.dateIso, AggregateTypes.CONTRACT, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, command.dateIso, envelope.payloadJson, now)
        enqueueWidgetRefresh(now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    private suspend fun commitSetSetting(command: BuCommand.SetSetting, envelope: CommandEnvelope, now: Long): CommandReceipt {
        domain.settings.set(command.key, command.value)
        appendEvent(EventTypes.SETTINGS_UPDATED, command.key, AggregateTypes.SETTINGS, envelope, now)
        enqueueEffect(EffectTypes.PROJECTIONS_DIRTY, command.key, envelope.payloadJson, now)
        inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.COMMITTED)
    }

    // ── Task insertion shared by task-creating branches ──────────────────────

    /** Seeds a task's estimate by class until real calibration arrives (P7). */
    private fun defaultEstimate(taskClass: String): Int = when (taskClass) {
        "SPARK" -> DEFAULT_SPARK_ESTIMATE
        "EXAM" -> 120
        else -> DEFAULT_BIG_ESTIMATE
    }

    private suspend fun insertTaskObject(
        title: String,
        taskClass: String,
        trackId: String?,
        scheduledDate: String?,
        startMinute: Int?,
        endMinute: Int?,
        dueAtMs: Long?,
        sourceCaptureId: String?,
        estimateMinutes: Int,
        now: Long,
    ): String {
        val taskId = newId()
        domain.objects.insert(
            ObjectRow(
                objectId = taskId,
                kind = "TASK",
                title = title,
                createdAtMs = now,
                updatedAtMs = now,
            ),
        )
        domain.tasks.insert(
            TaskRow(
                objectId = taskId,
                taskClass = taskClass,
                status = "OPEN",
                priority = 0,
                estimateMinutes = estimateMinutes,
                dueAtMs = dueAtMs,
                scheduledDate = scheduledDate,
                startMinute = startMinute,
                endMinute = endMinute,
                timezone = DEFAULT_ZONE,
                movedCount = 0,
                sourceCaptureId = sourceCaptureId,
                createdAtMs = now,
                updatedAtMs = now,
            ),
        )
        return taskId
    }

    private suspend fun snapshotRevision(objectId: String, now: Long) {
        val objectRow = domain.objects.byId(objectId) ?: return
        val revision = domain.objects.revisionCount(objectId) + 1
        domain.objects.insertRevision(
            RevisionRow(
                revisionId = newId(),
                objectId = objectId,
                revision = revision,
                snapshotJson = Json.encodeToString(ObjectSnapshot.serializer(), ObjectSnapshot(objectRow.title, now)),
                createdAtMs = now,
            ),
        )
    }

    // ── Receipt plumbing ─────────────────────────────────────────────────────

    private suspend fun rejectInCommit(envelope: CommandEnvelope, code: String, now: Long): CommandReceipt {
        inbox.updateStatus(envelope.commandId, CommandStatus.REJECTED, code, now)
        events.append(
            DomainEvent(
                eventId = newId(),
                type = EventTypes.COMMAND_REJECTED,
                aggregateId = envelope.commandId,
                aggregateType = AggregateTypes.COMMAND,
                occurredAtMs = now,
                recordedAtMs = now,
                actor = envelope.actor,
                payloadJson = envelope.payloadJson,
                correlationId = envelope.commandId,
                idempotencyKey = envelope.idempotencyKey,
            ),
        )
        return CommandReceipt(envelope.commandId, envelope.idempotencyKey, CommandStatus.REJECTED, reason = code)
    }

    private suspend fun appendEvent(type: String, aggregateId: String, aggregateType: String, envelope: CommandEnvelope, now: Long) {
        events.append(
            DomainEvent(
                eventId = newId(),
                type = type,
                aggregateId = aggregateId,
                aggregateType = aggregateType,
                occurredAtMs = now,
                recordedAtMs = now,
                actor = envelope.actor,
                payloadJson = envelope.payloadJson,
                correlationId = envelope.commandId,
                idempotencyKey = envelope.idempotencyKey,
            ),
        )
    }

    private suspend fun enqueueEffect(effectType: String, effectKey: String, payloadJson: String, now: Long) {
        outbox.enqueue(
            OutboxEffect(
                outboxId = newId(),
                effectType = effectType,
                effectKey = effectKey,
                payloadJson = payloadJson,
                createdAtMs = now,
            ),
        )
    }

    private suspend fun enqueueAlarm(reminderId: String, dueAtMs: Long, now: Long) {
        outbox.enqueue(
            OutboxEffect(
                outboxId = newId(),
                effectType = EffectTypes.SCHEDULE_REMINDER_ALARM,
                effectKey = "$reminderId:schedule:${now}",
                payloadJson = Json.encodeToString(AlarmTicketPayload.serializer(), AlarmTicketPayload(reminderId, dueAtMs)),
                createdAtMs = now,
            ),
        )
    }

    private suspend fun enqueueCancelAlarm(reminderId: String, now: Long) {
        outbox.enqueue(
            OutboxEffect(
                outboxId = newId(),
                effectType = EffectTypes.CANCEL_REMINDER_ALARM,
                effectKey = "$reminderId:cancel:$now",
                payloadJson = Json.encodeToString(AlarmTicketPayload.serializer(), AlarmTicketPayload(reminderId, 0L)),
                createdAtMs = now,
            ),
        )
    }

    private suspend fun enqueueWidgetRefresh(now: Long) {
        outbox.enqueue(
            OutboxEffect(
                outboxId = newId(),
                effectType = EffectTypes.WIDGET_DIRTY,
                effectKey = "widget:$now:${newId()}",
                payloadJson = "{}",
                createdAtMs = now,
            ),
        )
    }

    private fun receivedEvent(envelope: CommandEnvelope, now: Long, key: String) = DomainEvent(
        eventId = newId(),
        type = EventTypes.COMMAND_RECEIVED,
        aggregateId = envelope.commandId,
        aggregateType = AggregateTypes.COMMAND,
        occurredAtMs = now,
        recordedAtMs = now,
        actor = envelope.actor,
        payloadJson = envelope.payloadJson,
        correlationId = envelope.commandId,
        idempotencyKey = key,
    )

    private fun rejectionEvent(record: CommandRecord, reason: String?, now: Long) = DomainEvent(
        eventId = newId(),
        type = EventTypes.COMMAND_REJECTED,
        aggregateId = record.commandId,
        aggregateType = AggregateTypes.COMMAND,
        occurredAtMs = now,
        recordedAtMs = now,
        actor = record.source,
        payloadJson = record.payloadJson,
        correlationId = record.commandId,
        idempotencyKey = record.idempotencyKey,
    )

    private fun commandUndoneEvent(record: CommandRecord, now: Long) = DomainEvent(
        eventId = newId(),
        type = EventTypes.COMMAND_UNDONE,
        aggregateId = record.commandId,
        aggregateType = AggregateTypes.COMMAND,
        occurredAtMs = now,
        recordedAtMs = now,
        actor = record.source,
        payloadJson = record.payloadJson,
        correlationId = record.commandId,
    )

    private fun receiptUnknown(commandId: String) = CommandReceipt(
        commandId = commandId,
        idempotencyKey = "",
        status = CommandStatus.FAILED,
        reason = "UNKNOWN_COMMAND",
    )

    private fun todayIsoFallback(): String =
        // Tally date fallback when the task had no scheduled date: the wall
        // clock decides, in the device's zone. UI commands normally pass dates.
        java.time.LocalDate.now().toString()

    private fun newId(): String = UUID.randomUUID().toString()

    companion object {
        const val SETTING_DAY_START = "day_start_min"
        const val SETTING_DAY_END = "day_end_min"
        const val SETTING_ONBOARDED = "onboarded"
        const val SETTING_NOTIF_BUDGET = "notif_budget"
        const val DEFAULT_ZONE = "Asia/Colombo"
        const val DEFAULT_SPARK_ESTIMATE = 15
        const val DEFAULT_BIG_ESTIMATE = 60
        private val TALLIABLE_STATUSES = setOf("OPEN", "DONE", "PARTIAL")
    }
}

/** The only mutation gateway in BU (locked plan §5.2). */
interface CommandGateway {
    suspend fun submit(command: BuCommand): CommandReceipt
    suspend fun approve(commandId: String, approval: Approval): CommandReceipt
    suspend fun reject(commandId: String, reason: String?): CommandReceipt
    suspend fun undo(commandId: String): CommandReceipt
}

/** Approval marker — typed so approvals can never be smuggled in as flags. */
data class Approval(val byActor: String, val atMs: Long, val note: String? = null)

@kotlinx.serialization.Serializable
private data class ObjectSnapshot(val title: String, val atMs: Long)

/** The If-Then shield, serialised into day_contracts.ifThenJson (locked plan §2). */
@kotlinx.serialization.Serializable
data class IfThen(val obstacle: String, val plan: String)
