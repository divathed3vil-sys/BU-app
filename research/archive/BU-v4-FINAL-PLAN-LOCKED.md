# BU v4 — THE FINAL PLAN · LOCKED
## Personal Life OS · LOOM interface · BU Temporal Kernel

**Date:** 4 Nov 2026 · **Status: LOCKED — no further plan changes.** All future work implements this document. Tweaks happen inside the app, never inside this plan.
**Supersedes:** v1 BUILD-GUIDE, v2 research parts 1–7, the owner's Claude blueprint (all preserved in `/research` as history).
**Inputs blended:** BU_2 Product Strategy & LOOM Report · BU_3 Engineering Architecture Research · owner's Claude blueprint · Parts 5–7 · every owner-approved decision since day one.

---

## 1 · What BU is (final definition)

**BU is a local-first Personal Life Operating System** for one user (Dhivakaran, Galaxy A54). It combines five pillars:

| Pillar | Capability |
|---|---|
| Knowledge | Obsidian-grade personal knowledge environment (ATLAS) |
| Memory | Automatically maintained, evidence-linked chronicle (JOURNAL) |
| Planning | Adaptive, capacity-honest life planning (NOW + PLAN + rituals) |
| Behaviour | Discipline & attention engine (SHIELD) |
| Intelligence | Offline AI orchestration (Needle 3 — interpreter, never authority) |

**The loop:** Intention → Understand → Organise → Plan → Act → Observe → Reflect → Adapt — surfaced to the owner as the BU Loop: **CAPTURE → PLAN → DO → CLOSE → GROW.**

**Core rule (verbatim law):** *AI may understand the request. The kernel decides what is valid. The ledger records what became true. The evidence system records why BU believes it. The projections make it useful.*

---

## 2 · Vocabulary — one system, no synonyms (FINAL)

| Term | Meaning |
|---|---|
| **LOOM** | The interaction system: time · meaning · attention as one environment |
| **NOW** | The living cockpit (today) |
| **PLAN** | The temporal workspace (blocks, care items, capacity) |
| **JOURNAL** | The living chronicle (threads, check-ins, evidence) |
| **ATLAS** | The knowledge universe (notes, links, graph) |
| **BU Loop** | CAPTURE → PLAN → DO → CLOSE → GROW |
| **Track** | A purely user-built stream of work (no presets, ever) |
| **Big Three** | ≤3 daily priorities; slot #1 is labelled **Main Front** (exactly one) |
| **Minimum Day** | The one thing that makes tomorrow a success if all else collapses |
| **Block** | A time-boxed calendar piece |
| **Spark** | Tiny task/idea, capture-anywhere |
| **Carry** | Cooldown-based deferral (never shame-red); moved-3-times forces Do/Shrink/Kill |
| **Dusk Close** | Nightly ritual = Tomorrow Contract: Receipt → Offload → Triage → Commit → Seal |
| **Dawn Push** | Morning ritual: wins → front → if-then recall → first move |
| **Sunday Review** | Weekly ritual: scorecard → Reality Multiplier → Drift/Pull → week rules + seeds |
| **Day Receipt** | Auto-filled planned-vs-done + drift + biggest Pull source + mood |
| **If-Then shield** | Obstacle (classified: phone/fatigue/interruption/ambiguity/people) → one if-then plan |
| **Reality Multiplier** | 4-week actual/estimate ratio per task class; auto-scales new estimates |
| **Constellation** | Growth graph of objects and typed relationships |
| **Ghost stamp** | Machine-offered observation awaiting one-tap accept (AI never writes the user's words) |
| **Promise** | Detected commitment ("I'll call X Friday") → confirm-chip reminder |
| **Care Item** | Reminder typed: anchor · contextual · relational · value · deadline · habit |
| **Slip Score / Drift / Pull** | Behaviour metrics (3-day decay / planned-vs-actual / distraction load) |
| **Shield rungs 0–5** | Observe · Nudge · Pause · Soft · Hard · Contract Lock (sealed ceilings only) |
| **The Adapter** | On-device Thompson-sampling bandit (window × template × channel; reward = acted ≤30 min) |
| **The Voice** | 400–600 tagged templates + combinatorial grammar; never shames; autonomy-supportive |
| **Blocker taxonomy** | procrastination · overplanning · distraction · ambiguity · fatigue · avoidance · estimation · inconsistency |
| **Resistance budget** | Taps/seconds/guilt per action, measured; goal = 0 resistance |
| **BTK** | BU Temporal Kernel — the only mutation gateway |

---

## 3 · Final Information Architecture (LOCKED)

**5 bottom tabs** (horizontal-swipe pager, sliding indicator) **+ 3 ritual flows + 1 settings screen:**

| Tab | Serves | Contents |
|---|---|---|
| **NOW** | Capture · Plan · Do | Day Arc, Big Three (Main Front + 2 + Minimum Day line), Blocks strip, Sparks, quick capture, Day Receipt summary, ghost stamps, momentum headline, If-Then chip, exam horizon |
| **PLAN** | Plan | Day timeline + 3-day strip + capacity bar; segmented views: **Blocks** and **Care** (Care Items: types, per-reminder control, Why-now, escalation chain, exact-alarm health) |
| **JOURNAL** | Capture · Grow | Chronicle (auto evidence-backed entries) + Deep Write; 3-tap check-ins; prompts; promises; "days like this"; provenance affordances |
| **ATLAS** | Grow | Notes (Markdown), wiki links, backlinks, tags, typed relationships, bounded graph view, orphans surfaced kindly |
| **SHIELD** | Do (protection) | Rung ladder 0–5 with sealed ceiling, focus sessions (inherit tracks), app budgets, Pull view, digests, always-allowed list, rung health |
| **YOU** (top-right identity chip) | Grow | Capabilities (Sees/Never/payload), tracks, The Voice tone preview, resistance budget dashboard, data (export/trash/backup), soft mode, settings |

**Overlays:** Capture sheet (the Pebble) · Command palette · Guardian block overlay (one-breath pause) · Notification banner.

**The Pebble:** always-available capture affordance — lively, draggable, magnetic edge-docking, position remembered, squash on grab, never triggers capture after a drag. Distinct from the Sparks capture button (which captures-as-spark directly).

**Rituals (locked timings):** Dawn Push ≤60 s · Dusk Close 60–90 s (hard ceiling 6 min) · Sunday Review ≤15 min, anchor ≤3 weeks.

---

## 4 · Zero-Resistance UX law (from Part 5 — ratified into the build)

1. **No native sliders anywhere.** The **Arc-Dial** is the only time picker (drag arc, value bubble rides thumb, ±5 m steppers, snap-15 with haptic tick, live consequence). This resolves the owner-reported "intro sliders feel bad."
2. **Motion v3 springs:** `snappy / bouncy / screen / soft` only. All entry animations are transform/opacity (never layout) — this resolves the owner-reported animation bug class. Predictive back, directional wizard steps, no tab flicker, blur background pre-warmed and frame-budgeted.
3. **Gestures:** horizontal swipe between tabs · edge-swipe back with predictive peek · pull-down refresh on NOW/JOURNAL · long-press card quick actions · double-tap done card = pin as win · pebble reposition with magnetic dock · swipe-to-dismiss banner · Tally Trio swipe (right = Done, left = Carry, down = Partial, colour intent while dragging) · two-finger swipe down = palette. Coach marks ≤3 total.
4. **Zero-guilt copy law:** "missed" never appears; "carried/cooling/rescheduled" instead; no red except destructive; no streaks — Momentum is a 7-day rolling ratio, unbreakable.
5. **≤2-tap law** for every frequent action; empty states are instructions; onboarding = one decision per screen, giant type, ≤90 s, live payload capability cards, directional transitions, Arc-Dial hours (5:00–23:00 default, adjustable).
6. **Reduced-motion honoured** everywhere; 48 dp targets; focus-visible rings; screen-reader labels on the Arc-Dial (steppers = accessible path).
7. **Success metric inversion:** minutes-in-BU per day should go *down* over time. Life Yield over engagement.

---

## 5 · The BU Temporal Kernel (BTK) — architecture (LOCKED)

### 5.1 Five planes
1. **Intent** — capture surfaces (Pebble, tab actions, share target, widget, ritual flows). Raw intent is captured *before* any model processing.
2. **Authority** — deterministic Kotlin: validation, ID resolution, time resolution, policy, approval requests, transactional commit, audit receipt. **The only writer.**
3. **Evidence** — provenance-recorded observations with grades: `SCHEDULED · OBSERVED · INFERRED · USER_CONFIRMED · OUTCOME_CONFIRMED`.
4. **Projection** — NOW/PLAN/JOURNAL/ATLAS/search/FTS/alarms/exports are rebuildable views over canonical state.
5. **Platform** — Android adapters (AlarmManager, WorkManager, UsageStats, notifications, storage). They report and deliver; they never own truth.

### 5.2 Command gateway (the only mutation path)
```kotlin
interface CommandGateway {
    suspend fun submit(command: BuCommand): CommandReceipt
    suspend fun approve(commandId: String, approval: Approval): CommandReceipt
    suspend fun reject(commandId: String, reason: String?): CommandReceipt
    suspend fun undo(commandId: String): CommandReceipt   // compensating command, audit preserved
}
```
- Durable command state machine: `RECEIVED → INTERPRETING → PROPOSED → VALIDATING → (AWAITING_APPROVAL)? → READY → COMMITTING → COMMITTED | FAILED | REJECTED` (+ `NEEDS_CLARIFICATION`, `UNDONE`).
- Every command carries an **idempotency key**; retries never duplicate domain effects.
- **Transactional outbox:** DB commit + outbox row in one transaction; effects (alarms, FTS, embeddings, exports) applied after commit, at-least-once, idempotent by stable effect key.
- **Undo = compensating command.** Soft delete + tombstones + retention. AI can never empty the vault.

### 5.3 The twelve invariants (engineering constitution)
- **I1 Capture durability** — an acknowledged capture survives process death.
- **I2 Single authority** — each mutable field has one owner.
- **I3 No model writes** — Needle never touches the DB or system APIs directly.
- **I4 Transactional state** — operations commit fully or not at all.
- **I5 Retry safety** — redelivery never duplicates effects.
- **I6 Provenance** — derived claims link to their evidence.
- **I7 Rebuildability** — FTS, embeddings, graph, alarms, exports are recreatable.
- **I8 Offline core** — capture/read/write/plan/journal/search work with no network.
- **I9 Permission honesty** — missing permission degrades a feature; never fabricates data.
- **I10 User agency** — inspect, correct, override, export, delete.
- **I11 No hidden escalation** — Shield cannot silently raise firmness.
- **I12 Portable knowledge** — Markdown export with stable identities.
- Plus BU laws: **fallback parser always** · **red-only-destructive** · **≤2-tap** · **empty states instruct**.

### 5.4 Module structure (Gradle, 5 modules — boundary law)
```
:app            UI shells, navigation, DI wiring, workers, services host
:designsystem   tokens, motion, haptics, Arc, cards, Arc-Dial, pebble   (leaf — no deps)
:identity       BU Loop domain: plans, rituals, momentum, constellation, journal (pure Kotlin)
:guardian       Shield domain: slip, ladder, bandit, voice, sealed policies   (pure Kotlin)
:core           BTK: command, ledger (Room), evidence, markdown, search, planning,
                time, ai-contract, ai-needle host, security, platform-api interfaces
```
Dependency law: `app → {designsystem, identity, guardian, core}` · `identity → core` · `guardian → core` · designsystem → nothing. Inside `:core`, BTK planes are **packages**, not modules (module count stays 5; compile boundaries + package boundaries = free reviews). `:identity`/`:guardian` carry zero Android imports (JVM-testable).

**DI:** Hilt. **UI:** Kotlin + Jetpack Compose (Material 3), single Activity, Compose Navigation. **minSdk 29 · targetSdk 35 · JVM 17 · Kotlin 2.x.**

### 5.5 Capture: two clocks
- Raw capture → `capture_inbox` row **instantly** (≤100 ms ack), never blocked on Needle or network.
- Interpretation async: Needle or deterministic parser → typed proposal → policy validation → preview when ambiguous → approve → one transaction commits everything.
- Ambiguity is a first-class state (`NEEDS_CLARIFICATION`); the raw text is never deleted on interpretation failure.

---

## 6 · Storage — single-writer ledger (LOCKED)

**SQLite (Room, WAL) is the only canonical store.** Note/journal bodies are Markdown text *inside* the ledger (`documents` table) — no dual file+DB masters (BU_3's decisive correction of the v2 vault design). Attachments are immutable SHA-256 content-addressed files. Markdown vault export, FTS, embeddings, graph layouts, alarms = **derived, rebuildable artifacts**. The 10-year readability promise is honoured by one-tap full export (Markdown vault + manifest + JSONL), not by live dual files.

### 6.1 Final schema (~30 tables)
**BTK core (adopted from BU_3):** `objects` · `documents` · `object_revisions` · `relationships` · `domain_events` · `command_inbox` · `outbox` · `capture_inbox` · `attachments` · `object_attachments` · `reminders` · `evidence_events` · `focus_sessions` · `documents_fts` (FTS5).
**BU domain (merged):** `tracks` (object extension: EWMA estimate + samples) · `tasks` (object extension: class `BIG|BLOCK|SPARK|EXAM`, status, priority, estimate/adjusted/actual minutes, due/scheduled instants, timezone, recurrence, `moved_count`, energy, source_capture) · `day_contracts` (date, main_front_id, minimum_day, if_then_json, ceiling_rung, sealed_at, wake_plan, mood) · `carry_ledger` · `week_reviews` · `usage_events` (short retention) · `usage_daily` (aggregates) · `notification_log` · `rules` · `sealed_policies` · `capability_state` · `settings` · `embeddings` (float16 BLOB, brute-force cosine) · `templates` · `bandit_state` · `momentum_cache`.

Object kinds at P1: `TRACK · TASK · NOTE · THREAD · EXAM · PERSON · VIRTUE`. Relationship types: `belongs_to · supports · blocks · references · derived_from · scheduled_for · related_to · completed_by · involves`.
Times: UTC epoch-ms instants + IANA timezone + local wall-clock intent for recurrences. Wall clock for user-visible time; `elapsedRealtime` for focus measurement (reboot during session ⇒ `INTERRUPTED_OR_UNKNOWN`, never invented).

### 6.2 Ledger rules
Short transactions; no model/network/user-input inside a transaction; batched event inserts; polymorphic FKs validated in the repository + tested; soft delete by default; migrations tested from every shipped schema.

---

## 7 · Temporal & planning correctness (LOCKED)

- Time resolution pipeline: extract phrase → deterministic Kotlin resolver → timezone/locale candidates → calendar anchors ("after class") → ambiguity detector → explicit absolute date/time shown before commit. **The LLM never does arithmetic.**
- Planner: deterministic heuristic — free intervals from fixed commitments, deadline slack, lexicographic ranking (feasibility → priority → dependency readiness → stability → energy fit → fragmentation), minimum-block filter, breaks preserved, **capacity invariant:** `planned ≤ capacity − protected buffer`; overload is *shown*, never silently compressed; commit only after acceptance.
- **Estimate calibration (Reality Multiplier):** shrinkage median — `adjusted = w·category_ratio + (1−w)·global_ratio`, `w = n/(n+k)`; only clean focus sessions; sample size shown; owner can disable.

---

## 8 · Needle 3 law (LOCKED)

- **Role:** local interpreter — intent classification, structured extraction, entity linking, journal drafts, embeddings, tool selection. Never conversational reasoning, never authority.
- **Integration:** NDK/JNI, single `NeedleHost` lifecycle owner (load → warm → serve → evict), one serialized inference queue, 400 ms soft timeout → deterministic parser fallback, no JNI on UI thread, lazy load (never on cold start), evict on memory pressure.
- **Tool menus:** contextual, **≤5 tools per context** (capture / task / journal sets) — never one generic mega-tool.
- **Confidence is not authorization.** Routing: high → act + log (low-risk only) · medium → confirm chip · low/irreversible → refuse + deterministic path. Hard Kotlin guards behind every tool regardless of confidence.
- **M0 spike (first task of P6, on-device):** telemetry-off verification is a **hard gate** (airplane mode + traffic audit; any outbound = NO-GO → parser-only ship); benchmark depths 4/8/12/20 (latency p50/p95, RSS, thermal, accuracy); pin engine + weights checksums; test Tamil/Sinhala/code-switch separately — no promise without measurement.
- **AI-off mode:** full app functionality with the model absent — always.

---

## 9 · SHIELD (Guardian) design (LOCKED)

- Rungs **L0 Observe · L1 Nudge · L2 Pause · L3 Soft · L4 Hard · L5 Contract Lock.** Escalation only within ceilings sealed at Dusk Close; de-escalate after 3 compliant days; every action returns typed `Decision(action, reasons, evidence)` — the "Why now?" line can never be absent.
- **Blocker taxonomy** drives responses (8 causes → 8 different responses, never one generic motivational push).
- **Guards:** Reactance (rising bypass → de-escalate + check-in) · Low-Mood (mood trend caps at L2, supportive voice, no countdown framing, helpline card) — conservative, never diagnostic.
- Focus sessions inherit tracks; always-allowed list (calls/maps/banking/2FA/health) never touched; notification budget (default 5 interruptive/day, rest → digests); habituation rest days (ROGUE).
- Usage collection: consent-gated `UsageStatsManager`, watermark + overlap + dedupe, retention tiers, exclusions at capture boundary; missing data = collection gap, never "did nothing."
- L2 Pause overlay: one-breath friction — 5–10 s, "What are you here for?", easy exit (one sec evidence: friction + deliberation + dismissal option).

---

## 10 · Platform survival, security, performance (LOCKED)

- **Samsung kit:** persisted schedules (never rely on being alive), BootReceiver re-arm, capability health cards with device-specific battery deep-links, calm catch-up lines in Dawn Push instead of notification spam; FGS only while a track runs / ladder ≥ L3 / anchor < 24 h.
- **Reminders:** SQLite canonical, alarms a projection; exact only where justified + `canScheduleExactAlarms()` checked; stable PendingIntent = reminder id + revision; reconcile at startup; inexact described as inexact.
- **Security:** Keystore-wrapped DEK; SQLCipher decision benchmarked at P3 (default ON if clean); authenticated encryption for backups; no private text in logs (command ids, durations, error categories only); prompt-injection defense (retrieved text is data, never instructions; tool set fixed by app).
- **Backups:** versioned logical archive (manifest + hashes + Markdown + JSONL), restore preview + staging DB + atomic swap + rollback path.
- **Budgets:** cold start < 900 ms · NOW render p95 < 300 ms · capture ack < 100 ms · FTS p95 < 300 ms @ 10k notes · frame ≤ 16 ms on Arc · RSS ≤ 120 MB · FGS ≤ 3%/day · no wake loops. Enforced by Baseline Profiles + macrobenchmark in CI.

---

## 11 · Testing (LOCKED)

Pure-JVM TDD on `:identity`/`:guardian` (rollover, carry cooldown, EWMA, Slip decay, ladder transitions, bandit math, Reality Multiplier) · command correctness suite (duplicate idempotency, kill-before/after-commit, retry) · Markdown round-trip corpus · lifecycle matrix (reboot, force-stop, Doze, battery saver, revoked permissions, clock/timezone changes) · Room migration tests · event-replay simulator to tune the ladder before it ever restricts the owner · fallback-parser table (≥40 owner phrases) · backup/restore drill.

---

## 12 · CI/CD (LOCKED)

GitHub Actions: JDK 17 temurin · Android SDK 35 · `testDebugUnitTest` → `lintDebug` → `assembleDebug` → **debug APK artifact on every run** (owner downloads and tests each phase). `versionCode = run number`. Repo conventions: `PROGRESS.md` updated every phase · `DESIGN-NOTES.md` for inventions · conventional commits · English-only.

---

## 13 · BUILD PHASES (LOCKED — owner tests an APK after each phase, then replies)

| Phase | Name | Contents | Exit criterion |
|---|---|---|---|
| **P0** | Scaffolding & proof | Repo + CI artifact · 5 modules · Hilt · design tokens + motion v3 primitives · BTK skeleton (gateway, command inbox, outbox, event log, idempotency) · shell app: 5-tab LOOM nav, gesture scaffold, Day Arc rendering, Pebble | CI green · APK installs · tabs swipe · Arc renders · one demo command round-trips through kernel with event + receipt |
| **P1** | The Loop (vertical slice) | Ledger schema v1 (all core tables) · tracks CRUD · Big Three / Blocks / Sparks · Tally Trio · Day Arc live · NOW strip + widget · universal capture (instant save, async deterministic parse) · one Care reminder round-trip (alarm → notification → tally) · focus session (monotonic) · minimal setup onboarding | The full loop works on device and survives process death at every boundary |
| **P2** | Rituals & reminders | Dusk Close 5-step contract (Receipt → Offload → Triage Do/Shrink/Kill → Commit incl. Minimum Day + If-Then + ceiling → Seal) · Dawn Push · auto-draft tomorrow · carry cooldown + moved-3 rule · recommitment escalation · Care engine v3 (types, budget, digests, Why-now, escalation chain, exact-alarm health) | 7 consecutive nights of the real loop on the owner's phone |
| **P3** | Memory & Atlas | Threads + Markdown-in-ledger · 3-tap check-ins · evidence events + grades + provenance UI · FTS5 · wiki links + backlinks + bounded graph (ATLAS v1) · content-addressed attachments · ghost stamps · promises · vault export/import v1 · energy tints · embeddings table (no model) | Export → re-import round-trip; every auto statement traceable to its grade |
| **P4** | Guardian I | UsageStats collector (watermark/dedupe/retention) · Drift · Pull · sleep proxy · app budgets + always-allowed · L2 soft-pause overlay · digests · capability cards + Samsung battery kit | A real day of events matches reality |
| **P5** | Guardian II | Slip Score · ladder L0–L3 (L4/L5 behind sealed policies) · overrides with reasons · Reactance + Low-Mood guards · The Adapter (bandit) · The Voice v1 · Blocker taxonomy wired into triage + voice | Replay-simulated week passes policy review |
| **P6** | Needle | M0 spike FIRST (telemetry hard gate) · JNI host · LocalInterpreter · contextual ≤5-tool menus · extraction · semantic search · promise detection · confidence routing + hard guards · Whistle voice eval | Spike go/no-go recorded; AI-off mode fully functional |
| **P7** | Hardening & polish | Sunday Review full (Reality Multiplier, capacity map, seeds, week rules) · estimate calibration · encryption decision · backup/restore · perf gates (Baseline Profiles, macrobenchmark) · onboarding v3 final · resistance-budget dashboard · Life Yield metrics | Definition-of-Done checklist passes |

**Order is fixed.** No phase starts before the owner approves the previous APK. Screens beyond the IA in §3, Canvas, note databases, templates engine, plugins, live vault sync, desktop client = **explicitly out of v1 scope** (post-v1 backlog).

---

## 14 · Life Yield — what success means (LOCKED)

Commitment conversion · planning accuracy · recovery time · capture friction · journal completeness with provenance · reminder usefulness · retrieval quality · autonomy · **administration overhead.** Anti-metrics (never optimised): opens, screen time, notification clicks, streak length. **Primary signal: the owner spends less time managing BU while becoming more effective at life.**

---

## 15 · Final decision record (adjudications made in this lock)

| Question | v2 said | New research said | **LOCKED** |
|---|---|---|---|
| UX system | 9-screen BU IA | LOOM NOW/PLAN/JOURNAL/ATLAS | **LOOM 5-tab IA (§3)** — BU ritual names kept, LOOM adopted as interaction system |
| Note storage | vault files as 2nd truth | SQLite single-writer | **SQLite canonical, Markdown in ledger**; vault export = derived artifact |
| Kernel | dual-plane (Identity/Guardian) only | BTK 5 planes | **BTK 5 planes**; Identity/Guardian remain the two domain modules |
| Tools for Needle | ~25 global tools | ≤5 contextual tools | **≤5 contextual tool menus** |
| Mutation path | repositories | Command gateway + outbox + idempotency | **BTK gateway only** (repos become gateway internals) |
| Module count | 5 Gradle modules | ~11 core modules | **5 Gradle modules, BTK as packages** (small monolith law) |
| Capture | instant sparks | two-clock capture + ambiguity states | **Adopted fully** |
| Invariants | ad-hoc laws | I1–I12 | **Adopted I1–I12 + BU laws** |
| Scheduling | capacity-aware | deterministic lexicographic + shrinkage calibration | **Adopted** |
| Flutter | rejected | Kotlin recommended | **Kotlin + Compose confirmed** |
| Mindful code | reimplement (GPL) | same | **Reimplement, never copy** |
| Mock UI | mockup v3 planned | owner: skip mock | **No mock — build real app phase-by-phase** |

*— END OF PLAN. LOCKED. Next artifact: P0 code on GitHub.*
