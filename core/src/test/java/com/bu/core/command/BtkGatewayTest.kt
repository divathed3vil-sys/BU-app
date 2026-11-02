package com.bu.core.command

import com.bu.core.event.AggregateTypes
import com.bu.core.event.DomainEvent
import com.bu.core.time.BuClock
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Kernel contract tests — the properties the whole app depends on:
 * commit atomicity, idempotent retries, rejection reasons, undo compensation.
 */
class BtkGatewayTest {

    private class FakeClock(var ms: Long = 1_000_000L) : BuClock {
        override fun nowMs(): Long = ms
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

        override suspend fun pending(): List<OutboxEffect> = effects.filter { it.status == "PENDING" }

        override suspend fun markDone(outboxId: String, atMs: Long) {
            for (i in effects.indices) {
                val effect = effects[i]
                if (effect.outboxId == outboxId) effects[i] = effect.copy(status = "DONE", attempts = effect.attempts + 1)
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
    }

    private lateinit var inbox: FakeInbox
    private lateinit var events: FakeEvents
    private lateinit var outbox: FakeOutbox
    private lateinit var captures: FakeCaptures
    private lateinit var gateway: BtkGateway

    @Before
    fun setUp() {
        inbox = FakeInbox()
        events = FakeEvents()
        outbox = FakeOutbox()
        captures = FakeCaptures()
        gateway = BtkGateway(inbox, events, outbox, captures, FakeTx(), FakeClock())
    }

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
}
