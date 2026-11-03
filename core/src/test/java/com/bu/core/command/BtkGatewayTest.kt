package com.bu.core.command

import com.bu.core.event.AggregateTypes
import com.bu.core.event.DomainEvent
import com.bu.core.event.EffectTypes
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
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Kernel contract tests — the properties the whole app depends on:
 * commit atomicity, idempotent retries, rejection reasons, undo compensation,
 * and the P1 loop commands (tasks, tallies, Big Three, care, focus).
 */
class BtkGatewayTest {

    private class FakeClock(var ms: Long = 1_000_000L) : BuClock {
        override fun nowMs(): Long = ms
        override fun elapsedRealtimeMs(): Long = ms // tests advance both together
    }

    private class FakeTx : TransactionRunner {
        override suspend fun <T> inTransaction(block: suspend () -> T): T = block()
    }

    private class FakeInbox : CommandInboxStore {
        val rows = mutableListOf<CommandRecord>()
        override suspend fun insert(record: CommandRecord) {
            rows.removeAll { it.commandId == record.commandId }
            rows.add(record)
        }

        override suspend fun updateStatus(commandId: String, status: CommandStatus, errorCode: String?, atMs: Long) {
            for (i in rows.indices) {
                val row = rows[i]
                if (row.commandId == commandId) rows[i] = row.copy(status = status, errorCode = errorCode, updatedAtMs = atMs)
            }
        }

        override suspend fun findByIdempotencyKey(key: String): CommandRecord? =
            rows.firstOrNull { it.idempotencyKey == key }

        override suspend fun findById(commandId: String): CommandRecord? =
            rows.firstOrNull { it.commandId == commandId }
    }

    private class FakeEvents : EventStore {
        val list = mutableListOf<DomainEvent>()
        override suspend fun append(event: DomainEvent) {
            list.removeAll { it.eventId == event.eventId }
            list.add(event)
        }

        override suspend fun findByCorrelation(commandId: String, type: String): DomainEvent? =
            list.firstOrNull { it.correlationId == commandId && it.type == type }
    }

    private class FakeOutbox : OutboxStore {
        val effects = mutableListOf<OutboxEffect>()
        override suspend fun enqueue(effect: OutboxEffect) {
            effects.removeAll { it.outboxId == effect.outboxId }
            effects.add(effect)
        }

        override suspend fun pending(limit: Int): List<OutboxEffect> =
            effects.filter { it.status == "PENDING" }.take(limit)

        override suspend fun markDone(outboxId: String, atMs: Long) {
            for (i in effects.indices) {
                val effect = effects[i]
                if (effect.outboxId == outboxId) effects[i] = effect.copy(status = "DONE", attempts = effect.attempts + 1)
            }
        }

        override suspend fun markFailed(outboxId: String, code: String) {
            for (i in effects.indices) {
                val effect = effects[i]
                if (effect.outboxId == outboxId) effects[i] = effect.copy(status = "FAILED", attempts = effect.attempts + 1)
            }
        }
    }

    private class FakeCaptures : CaptureStore {
        val rows = mutableListOf<CaptureRecord>()
        override suspend fun insert(capture: CaptureRecord) {
            rows.removeAll { it.captureId == capture.captureId }
            rows.add(capture)
        }

        override suspend fun updateStatus(captureId: String, status: String) {
            for (i in rows.indices) {
                val row = rows[i]
                if (row.captureId == captureId) rows[i] = row.copy(status = status)
            }
        }

        override suspend fun findById(captureId: String): CaptureRecord? =
            rows.firstOrNull { it.captureId == captureId }

        override suspend fun linkTask(captureId: String, status: String, taskObjectId: String?, atMs: Long) {
            for (i in rows.indices) {
                val row = rows[i]
                if (row.captureId == captureId) {
                    rows[i] = row.copy(status = status, taskObjectId = taskObjectId)
                }
            }
        }
    }

    // ── Domain fakes (in-memory, P1 surface) ─────────────────────────────────

    private class FakeObjects : ObjectStore {
        val rows = mutableListOf<ObjectRow>()
        val relationships = mutableListOf<RelationshipRow>()
        val revisions = mutableListOf<RevisionRow>()
        override suspend fun insert(row: ObjectRow) {
            rows.removeAll { it.objectId == row.objectId }
            rows.add(row)
        }

        override suspend fun byId(objectId: String): ObjectRow? = rows.firstOrNull { it.objectId == objectId }
        override suspend fun rename(objectId: String, title: String, atMs: Long) {
            for (i in rows.indices) {
                if (rows[i].objectId == objectId) rows[i] = rows[i].copy(title = title, updatedAtMs = atMs)
            }
        }

        override suspend fun archive(objectId: String, atMs: Long) {
            for (i in rows.indices) {
                if (rows[i].objectId == objectId) rows[i] = rows[i].copy(deletedAtMs = atMs)
            }
        }

        override suspend fun insertRelationship(row: RelationshipRow) {
            relationships.add(row)
        }

        override suspend fun insertRevision(row: RevisionRow) {
            revisions.add(row)
        }

        override suspend fun revisionCount(objectId: String): Int =
            revisions.count { it.objectId == objectId }
    }

    private class FakeTracks : TrackStore {
        val rows = mutableListOf<TrackRow>()
        override suspend fun save(row: TrackRow) {
            rows.removeAll { it.objectId == row.objectId }
            rows.add(row)
        }

        override suspend fun byId(objectId: String): TrackRow? = rows.firstOrNull { it.objectId == objectId }
        override suspend fun archive(objectId: String, atMs: Long) {
            for (i in rows.indices) {
                if (rows[i].objectId == objectId) rows[i] = rows[i].copy(archivedAtMs = atMs)
            }
        }
    }

    private class FakeTasks : TaskStore {
        val rows = mutableListOf<TaskRow>()
        val carries = mutableListOf<CarryRow>()
        override suspend fun insert(row: TaskRow) {
            rows.removeAll { it.objectId == row.objectId }
            rows.add(row)
        }

        override suspend fun byId(objectId: String): TaskRow? = rows.firstOrNull { it.objectId == objectId }
        override suspend fun replaceOpenBig(dateIso: String, atMs: Long) {
            for (i in rows.indices) {
                val row = rows[i]
                if (row.taskClass == "BIG" && row.scheduledDate == dateIso && row.status == "OPEN") {
                    rows[i] = row.copy(status = "REPLACED", updatedAtMs = atMs)
                }
            }
        }

        override suspend fun setTally(objectId: String, status: String, atMs: Long, dateIso: String, actualMinutes: Int?) {
            for (i in rows.indices) {
                val row = rows[i]
                if (row.objectId == objectId) {
                    rows[i] = row.copy(status = status, talliedAtMs = atMs, talliedDate = dateIso, actualMinutes = actualMinutes)
                }
            }
        }

        override suspend fun setSchedule(objectId: String, toDate: String, startMinute: Int?, movedCount: Int, atMs: Long) {
            for (i in rows.indices) {
                val row = rows[i]
                if (row.objectId == objectId) {
                    rows[i] = row.copy(scheduledDate = toDate, startMinute = startMinute, movedCount = movedCount, updatedAtMs = atMs)
                }
            }
        }

        override suspend fun overlappingBlock(dateIso: String, startMinute: Int, endMinute: Int): TaskRow? =
            rows.firstOrNull {
                it.taskClass == "BLOCK" && it.scheduledDate == dateIso && it.status == "OPEN" &&
                    it.startMinute != null && it.endMinute != null &&
                    it.startMinute < endMinute && it.endMinute > startMinute
            }

        override suspend fun insertCarry(row: CarryRow) {
            carries.add(row)
        }
    }

    private class FakeContracts : ContractStore {
        val rows = mutableListOf<DayContractRow>()
        override suspend fun upsert(row: DayContractRow) {
            rows.removeAll { it.dateIso == row.dateIso }
            rows.add(row)
        }

        override suspend fun byDate(dateIso: String): DayContractRow? = rows.firstOrNull { it.dateIso == dateIso }
    }

    private class FakeReminders : ReminderStore {
        val rows = mutableListOf<ReminderRow>()
        override suspend fun insert(row: ReminderRow) {
            rows.removeAll { it.reminderId == row.reminderId }
            rows.add(row)
        }

        override suspend fun byId(reminderId: String): ReminderRow? = rows.firstOrNull { it.reminderId == reminderId }
        override suspend fun pending(): List<ReminderRow> = rows.filter { it.status == "PENDING" }
        override suspend fun setStatus(reminderId: String, status: String, atMs: Long) {
            for (i in rows.indices) {
                if (rows[i].reminderId == reminderId) rows[i] = rows[i].copy(status = status, updatedAtMs = atMs)
            }
        }

        override suspend fun reschedule(reminderId: String, dueAtMs: Long, atMs: Long) {
            for (i in rows.indices) {
                if (rows[i].reminderId == reminderId) {
                    rows[i] = rows[i].copy(dueAtMs = dueAtMs, alarmRevision = rows[i].alarmRevision + 1, status = "PENDING", updatedAtMs = atMs)
                }
            }
        }

        override suspend fun setAlarmExact(reminderId: String, exact: Boolean, atMs: Long) {
            for (i in rows.indices) {
                if (rows[i].reminderId == reminderId) rows[i] = rows[i].copy(alarmExact = exact, updatedAtMs = atMs)
            }
        }
    }

    private class FakeFocus : FocusStore {
        val rows = mutableListOf<FocusSessionRow>()
        override suspend fun insert(row: FocusSessionRow) {
            rows.removeAll { it.sessionId == row.sessionId }
            rows.add(row)
        }

        override suspend fun byId(sessionId: String): FocusSessionRow? = rows.firstOrNull { it.sessionId == sessionId }
        override suspend fun running(): FocusSessionRow? = rows.firstOrNull { it.state == "RUNNING" }
        override suspend fun end(sessionId: String, state: String, endedAtMs: Long, actualMinutes: Int?): Int {
            for (i in rows.indices) {
                val row = rows[i]
                if (row.sessionId == sessionId && row.state == "RUNNING") {
                    rows[i] = row.copy(state = state, endedAtMs = endedAtMs, actualMinutes = actualMinutes)
                    return 1
                }
            }
            return 0
        }
    }

    private class FakeSettings : SettingsStore {
        val map = mutableMapOf<String, String>()
        override suspend fun set(key: String, value: String) {
            map[key] = value
        }
    }

    private class FakeEvidence : EvidenceStore {
        val rows = mutableListOf<Triple<String, String, String>>()
        override suspend fun insert(subjectId: String, subjectType: String, grade: String, statement: String, atMs: Long) {
            rows.add(Triple(subjectId, grade, statement))
        }
    }

    private class FakeMomentum : MomentumStore {
        val recounted = mutableListOf<String>()
        override suspend fun recountDay(dateIso: String) {
            recounted.add(dateIso)
        }
    }

    private lateinit var inbox: FakeInbox
    private lateinit var events: FakeEvents
    private lateinit var outbox: FakeOutbox
    private lateinit var captures: FakeCaptures
    private lateinit var tasks: FakeTasks
    private lateinit var contracts: FakeContracts
    private lateinit var reminders: FakeReminders
    private lateinit var focus: FakeFocus
    private lateinit var evidence: FakeEvidence
    private lateinit var momentum: FakeMomentum
    private lateinit var gateway: BtkGateway
    private lateinit var clock: FakeClock

    @Before
    fun setUp() {
        inbox = FakeInbox()
        events = FakeEvents()
        outbox = FakeOutbox()
        captures = FakeCaptures()
        tasks = FakeTasks()
        contracts = FakeContracts()
        reminders = FakeReminders()
        focus = FakeFocus()
        evidence = FakeEvidence()
        momentum = FakeMomentum()
        clock = FakeClock()
        val domain = DomainStores(
            objects = FakeObjects(),
            tracks = FakeTracks(),
            tasks = tasks,
            contracts = contracts,
            reminders = reminders,
            focus = focus,
            settings = FakeSettings(),
            evidence = evidence,
            momentum = momentum,
        )
        gateway = BtkGateway(inbox, events, outbox, captures, domain, FakeTx(), clock)
    }

    // ── P0 kernel contracts ───────────────────────────────────────────────────

    @Test
    fun `capture commits atomically - record, event, outbox, receipt`() = runTest {
        val receipt = gateway.submit(BuCommand.SaveCapture(text = "study integration", idempotencyKey = "k1"))

        assertEquals(CommandStatus.COMMITTED, receipt.status)
        assertEquals(1, captures.rows.size)
        assertEquals("study integration", captures.rows.first().rawText)
        assertEquals(1, events.list.count { it.type == "CAPTURE_SAVED" })
        assertEquals(2, outbox.effects.size)
        assertNotNull(inbox.findById(receipt.commandId))
        // event links back to the capture aggregate and carries the idempotency key
        val saved = events.list.first { it.type == "CAPTURE_SAVED" }
        assertEquals(captures.rows.first().captureId, saved.aggregateId)
        assertEquals(AggregateTypes.CAPTURE, saved.aggregateType)
        assertEquals("k1", saved.idempotencyKey)
    }

    @Test
    fun `retry with same idempotency key never duplicates effects (I5)`() = runTest {
        val first = gateway.submit(BuCommand.SaveCapture(text = "hello", idempotencyKey = "same"))
        val second = gateway.submit(BuCommand.SaveCapture(text = "hello", idempotencyKey = "same"))

        assertEquals(first.commandId, second.commandId)
        assertEquals(1, captures.rows.size)
        assertEquals(1, events.list.count { it.type == "CAPTURE_SAVED" })
        assertEquals(2, outbox.effects.size)
    }

    @Test
    fun `blank capture is rejected with reason and no side effects`() = runTest {
        val receipt = gateway.submit(BuCommand.SaveCapture(text = "   ", idempotencyKey = "k2"))

        assertEquals(CommandStatus.REJECTED, receipt.status)
        assertEquals("EMPTY_CAPTURE", receipt.reason)
        assertEquals(0, captures.rows.size)
        assertEquals(0, outbox.effects.size)
        // but the attempt is audited
        assertEquals(1, events.list.count { it.type == "COMMAND_REJECTED" })
    }

    @Test
    fun `undo is a compensating command - capture discarded, audit preserved (I10)`() = runTest {
        val receipt = gateway.submit(BuCommand.SaveCapture(text = "temp thought", idempotencyKey = "k3"))

        val undone = gateway.undo(receipt.commandId)

        assertEquals(CommandStatus.UNDONE, undone.status)
        assertEquals("DISCARDED", captures.rows.first().status)
        assertEquals(1, events.list.count { it.type == "CAPTURE_DISCARDED" })
        // history is never rewritten — the saved event remains
        assertEquals(1, events.list.count { it.type == "CAPTURE_SAVED" })
    }

    @Test
    fun `undo of unknown or non-committed command is refused, not invented`() = runTest {
        val unknown = gateway.undo("nope")
        assertEquals(CommandStatus.FAILED, unknown.status)
        assertEquals("UNKNOWN_COMMAND", unknown.reason)

        val rejected = gateway.submit(BuCommand.SaveCapture(text = "", idempotencyKey = "k4"))
        val undoRejected = gateway.undo(rejected.commandId)
        assertEquals(CommandStatus.REJECTED, undoRejected.status)
        assertEquals("NOT_COMMITTED", undoRejected.reason)
    }

    @Test
    fun `approve and reject keep the contract when nothing awaits approval`() = runTest {
        val receipt = gateway.submit(BuCommand.SaveCapture(text = "hi", idempotencyKey = "k5"))

        val approved = gateway.approve(receipt.commandId, Approval(byActor = "OWNER", atMs = 1L))
        assertEquals("NOT_AWAITING_APPROVAL", approved.reason)

        val rejected = gateway.reject(receipt.commandId, "no")
        assertEquals("NOT_AWAITING_APPROVAL", rejected.reason)
        assertTrue(captures.rows.first().status != "DISCARDED")
    }

    // ── P1 loop commands ──────────────────────────────────────────────────────

    @Test
    fun `tally done lands, records evidence and recounts momentum`() = runTest {
        val created = gateway.submit(
            BuCommand.CreateTask(
                title = "physics chapter 4",
                taskClass = "BIG",
                scheduledDateIso = "2026-11-04",
                idempotencyKey = "t1",
            ),
        )
        assertEquals(CommandStatus.COMMITTED, created.status)
        val taskId = tasks.rows.single().objectId

        val receipt = gateway.submit(
            BuCommand.TallyTask(taskId = taskId, tally = "DONE", idempotencyKey = "t2"),
        )

        assertEquals(CommandStatus.COMMITTED, receipt.status)
        assertEquals("DONE", tasks.rows.single().status)
        assertEquals(1, evidence.rows.count { it.first == taskId })
        assertEquals(listOf("2026-11-04"), momentum.recounted)
        assertTrue(outbox.effects.any { it.effectType == EffectTypes.WIDGET_DIRTY })
    }

    @Test
    fun `carry moves the task to tomorrow with a cooldown entry - never shame`() = runTest {
        val created = gateway.submit(
            BuCommand.CreateTask(title = "math revision", taskClass = "BIG", scheduledDateIso = "2026-11-04", idempotencyKey = "c1"),
        )
        val taskId = tasks.rows.single().objectId

        val receipt = gateway.submit(
            BuCommand.TallyTask(taskId = taskId, tally = "CARRIED", toDateIso = "2026-11-05", idempotencyKey = "c2"),
        )

        assertEquals(CommandStatus.COMMITTED, receipt.status)
        assertEquals("2026-11-05", tasks.rows.single().scheduledDate)
        assertEquals("OPEN", tasks.rows.single().status) // carried is not a failure state
        assertEquals(1, tasks.carries.size)
        assertEquals(1, tasks.rows.single().movedCount)
        assertEquals(1, events.list.count { it.type == "TASK_CARRIED" })
    }

    @Test
    fun `set big three replaces open slots and keeps exactly one main front`() = runTest {
        val first = gateway.submit(
            BuCommand.SetBigThree(
                dateIso = "2026-11-04",
                slots = listOf(BigThreeSlotInput("old plan", isMainFront = true)),
                idempotencyKey = "b0",
            ),
        )
        assertEquals(CommandStatus.COMMITTED, first.status)

        val receipt = gateway.submit(
            BuCommand.SetBigThree(
                dateIso = "2026-11-04",
                slots = listOf(
                    BigThreeSlotInput("physics", isMainFront = true),
                    BigThreeSlotInput("maths"),
                ),
                idempotencyKey = "b1",
            ),
        )

        assertEquals(CommandStatus.COMMITTED, receipt.status)
        val open = tasks.rows.filter { it.taskClass == "BIG" && it.status == "OPEN" }
        assertEquals(2, open.size)
        assertEquals(1, tasks.rows.count { it.status == "REPLACED" })
        assertEquals(1, contracts.rows.size)
        assertNotNull(contracts.rows.single().mainFrontTaskId)
        assertEquals(1, events.list.count { it.type == "BIG_THREE_SET" })
    }

    @Test
    fun `big three with zero or two fronts is refused by the validator`() = runTest {
        val none = gateway.submit(
            BuCommand.SetBigThree(dateIso = "2026-11-04", slots = listOf(BigThreeSlotInput("a")), idempotencyKey = "v1"),
        )
        assertEquals(CommandStatus.REJECTED, none.status)
        assertEquals("EXACTLY_ONE_MAIN_FRONT", none.reason)

        val two = gateway.submit(
            BuCommand.SetBigThree(
                dateIso = "2026-11-04",
                slots = listOf(BigThreeSlotInput("a", isMainFront = true), BigThreeSlotInput("b", isMainFront = true)),
                idempotencyKey = "v2",
            ),
        )
        assertEquals(CommandStatus.REJECTED, two.status)
        assertEquals("EXACTLY_ONE_MAIN_FRONT", two.reason)
        assertEquals(0, tasks.rows.size)
    }

    @Test
    fun `overlapping blocks are refused - the timeline refuses to lie`() = runTest {
        val first = gateway.submit(
            BuCommand.CreateTask(
                title = "study block", taskClass = "BLOCK", scheduledDateIso = "2026-11-04",
                startMinute = 9 * 60, durationMinutes = 60, idempotencyKey = "k1",
            ),
        )
        assertEquals(CommandStatus.COMMITTED, first.status)

        val overlap = gateway.submit(
            BuCommand.CreateTask(
                title = "collision", taskClass = "BLOCK", scheduledDateIso = "2026-11-04",
                startMinute = 9 * 60 + 30, durationMinutes = 45, idempotencyKey = "k2",
            ),
        )
        assertEquals(CommandStatus.REJECTED, overlap.status)
        assertEquals("OVERLAP_BLOCK", overlap.reason)
        assertEquals(1, tasks.rows.count { it.taskClass == "BLOCK" })
    }

    @Test
    fun `care reminder commits and enqueues its alarm projection`() = runTest {
        val receipt = gateway.submit(
            BuCommand.CreateReminder(title = "call amma", dueAtMs = clock.ms + 3_600_000L, idempotencyKey = "r1"),
        )

        assertEquals(CommandStatus.COMMITTED, receipt.status)
        assertEquals(1, reminders.rows.size)
        assertEquals("PENDING", reminders.rows.single().status)
        assertTrue(outbox.effects.any { it.effectType == EffectTypes.SCHEDULE_REMINDER_ALARM })
        assertTrue(evidence.rows.isNotEmpty())
    }

    @Test
    fun `snooze pushes the due time and re-arms the projection`() = runTest {
        gateway.submit(BuCommand.CreateReminder(title = "water plants", dueAtMs = clock.ms + 1_000, idempotencyKey = "r2"))
        val id = reminders.rows.single().reminderId

        val receipt = gateway.submit(BuCommand.SnoozeReminder(reminderId = id, minutes = 10, idempotencyKey = "r3"))

        assertEquals(CommandStatus.COMMITTED, receipt.status)
        assertEquals(clock.ms + 1_000 + 10 * 60_000L, reminders.rows.single().dueAtMs)
    }

    @Test
    fun `only one focus session can run - and it ends honestly`() = runTest {
        val started = gateway.submit(BuCommand.StartFocus(plannedMinutes = 25, idempotencyKey = "f1"))
        assertEquals(CommandStatus.COMMITTED, started.status)

        val double = gateway.submit(BuCommand.StartFocus(plannedMinutes = 25, idempotencyKey = "f2"))
        assertEquals(CommandStatus.REJECTED, double.status)
        assertEquals("FOCUS_ALREADY_RUNNING", double.reason)

        clock.ms += 25 * 60_000L
        val session = focus.rows.single()
        val ended = gateway.submit(BuCommand.EndFocus(session.sessionId, completedAsPlanned = true, idempotencyKey = "f3"))
        assertEquals(CommandStatus.COMMITTED, ended.status)
        assertEquals("COMPLETED", focus.rows.single().state)
        assertEquals(25, focus.rows.single().actualMinutes)
    }
}
