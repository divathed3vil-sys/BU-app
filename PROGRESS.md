# BU · PROGRESS

Discipline: this file is updated after every phase. English-only. Commit format `feat(scope): …`.

---

## P1 — The Loop (vertical slice)  ·  status: pushed · awaiting owner test

**Locked plan:** `research/BU-v4-FINAL-PLAN-LOCKED.md` (supersedes all prior plans).

**Owner decision folded in first:** the LOOM nav is now a **floating pill** — detached from the
screen edges, lifted by a soft shadow, content glides beneath it (Pebble, snackbar and every
tab's scroll clearance re-tuned around it).

**Delivered**

- **Ledger schema v2 (all core P1 tables):** objects · object_revisions · relationships · tracks ·
  tasks · day_contracts · carry_ledger · reminders · focus_sessions · evidence_events · settings ·
  momentum_cache · notification_log. Additive hand-written `MIGRATION_1_2` — a P0 install keeps
  every capture (I1), and capture rows gain their promotion link.
- **BTK grew the P1 command vocabulary:** capture → interpret/promote · tracks CRUD · tasks
  (BIG/BLOCK/SPARK) · TallyTask (DONE/PARTIAL/CARRIED) · SetBigThree (≤3, exactly one Main
  Front, enforced in Validators) · care reminders (create/complete/snooze/cancel) · monotonic
  focus (start/end, reboot ⇒ INTERRUPTED_OR_UNKNOWN, never invented) · day window · onboarded.
  Every branch: validate → one transaction (state + events + outbox) → receipt → effects.
- **Universal capture, two clocks (§5.5):** SaveCapture commits instantly; the outbox runs the
  deterministic interpreter (`:core/interpret`) — relative times, clock times, tonight/tomorrow/
  weekday anchors, intent-prefix stripping, honest ambiguity (unresolvable time ⇒ timeless task,
  raw text never rewritten). Verdicts re-enter the kernel as commands; sparks that look like
  tasks become SPARK tasks with a `derived_from` relationship.
- **Care round-trip complete:** CreateReminder → outbox `SCHEDULE_REMINDER_ALARM` →
  AndroidReminderScheduler (exact where the OS allows, honest 10-min window where it does not,
  honesty written back to `alarmExact`) → CareReceiver marks DUE + logs delivery + posts the
  notification → owner tallies in PLAN. Cancel/snooze re-arm idempotently. BootReceiver re-arms
  from SQLite after a reboot; startup reconcile marks past-due honestly.
- **The NOW widget:** home-screen projection (Main Front ▲, Big Three with ✓/◐/○ glyphs,
  captures count) rebuilt from the ledger by the WIDGET_DIRTY outbox effect — never a second truth.
- **NOW tab is the live cockpit:** real Day Arc (window from settings, elapsed vs landed progress) ·
  Momentum headline (7-day rolling ratio from `momentum_cache`) · Big Three with **Tally Trio**
  (swipe right = Done · swipe left = Carry with cooling-grey intent · buttons for the accessible
  path; carried N× counts shown without shame) · Blocks strip + Arc-Dial block placement with
  overlap refusal surfaced kindly · Sparks with direct capture + tally · Focus card (15/25/45/open,
  live monotonic elapsed, Completed/End-early) · kernel pulse.
- **PLAN tab:** Tracks CRUD (create/edit/archive; estimates are guesses BU will learn to correct)
  + Care list (exact/inexact honesty chips, Done ✓, +10 m snooze).
- **Onboarding, one decision per screen:** Begin → day window on two Arc-Dials (4 h floor,
  enforced twice) → notification permission (ask once, explain, never block on a no) → ledger
  marks onboarded. MainActivity gates on the ledger, splash-quiet while it reads.
- **Kernel contract tests extended to P1 commands** (tally + evidence + momentum recount, carry
  cooldown row + moved count, Big Three replacement + validator refusals, block overlap refusal,
  care alarm enqueue, snooze math, focus exclusivity + honest end) alongside the P0 suite.

**Exit criteria check (locked plan §13 P1)**

- [x] Full loop on device: capture → interpret → plan (Big Three/blocks) → do (focus) → tally
      — all through the kernel, all receipted.
- [x] Process-death boundaries: captures, tasks, tallies, reminders, focus state all live in the
      ledger; alarms re-arm on boot; outbox drains on start.
- [x] NOW widget ships as a rebuildable projection.

**Owner test checklist (download APK from the Actions run)**

1. First launch: onboarding — drag the two Arc-Dials, set your day, decide on notifications.
2. Tap the Pebble → type "call amma tomorrow at 6pm" → capture → watch NOW: a spark appears,
   then becomes a task scheduled for tomorrow (the deterministic parser, no AI).
3. NOW → "Set" on Big Three → three lines, mark the front → Commit. Swipe one right (Done —
   mint), one left (Carry — cooling grey, "carried 1×" appears), use ◐ on one.
4. "Add" a block on the Arc-Dial → try overlapping a second one on the same time — BU refuses,
   kindly. Try 25/45/60/90 durations.
5. PLAN → create a track; create a Care reminder for ~2 minutes ahead → wait for the
   notification → tally it ✓ in PLAN. The row shows "exact" or "inexact window" honestly.
6. Start a 25-min focus, watch the live timer, end it early — it records honestly.
7. Add the "BU · NOW" widget to your home screen; tally something in-app and watch it repaint.
8. Kill the app, reopen — everything you did is still there. Reboot the phone — reminders
   survive.

**Next phase:** P2 — Rituals & reminders (Dusk Close, Dawn Push, carry cooldown + moved-3 rule,
Care engine v3), starts on owner approval.

---

## P0 — Scaffolding & proof  ·  2026-11-04  ·  status: APPROVED by owner

**Locked plan:** `research/BU-v4-FINAL-PLAN-LOCKED.md` (supersedes all prior plans).

**Delivered**

- 5 Gradle modules per the dependency law (`:app :core :designsystem :identity :guardian`), Kotlin 2.0.21 / AGP 8.7.3 / Compose BOM 2024.10.01 / Hilt 2.52 / Room 2.6.1, minSdk 29 · target 35.
- **BTK skeleton in `:core`:** `CommandGateway` (submit/approve/reject/undo) · durable `command_inbox` · append-only `domain_events` · transactional `outbox` · `capture_inbox` · idempotency-key retry safety · pure validators · injectable clock · Room-backed stores with a `TransactionRunner` seam for JVM tests.
- **Kernel contract tests (6, JVM):** atomic commit (capture+event+outbox in one transaction), idempotent retry (I5), rejection with audit, undo-as-compensation (I10), unknown-command refusal, approval-contract stub. Plus Big Three rules tests and Shield ladder ceiling tests (I11).
- **Design system v3:** near-black/gray/mint tokens · dark-native theme · four named springs (`snappy/bouncy/screen/soft`) · haptic grammar (`tick/grab/win/guard`) · `BuDayArc` Canvas · **`BuArcDial`** (the slider replacement: 15-min snap, haptic ticks, value bubble, ±5 m accessible steppers) · **`BuPebble`** (draggable, magnetic edge-dock, position remembered, drag never fires capture — owner's lively-+ requirement) · `BuCard` + instructive `BuEmptyState`.
- **LOOM shell:** 5 tabs (NOW · PLAN · JOURNAL · ATLAS · SHIELD) as a swipe pager with a leading spring indicator; capture sheet submits through the kernel and shows the receipt; NOW shows a live kernel pulse (captures today + queued async work) reading real ledger flows; PLAN hosts an interactive Arc-Dial demo; every future screen states its phase honestly.
- CI: assembleDebug + kernel/domain unit tests + lint → debug APK artifact on every push.

**Result:** owner tested on device — approved. One change request carried into P1: floating nav.
