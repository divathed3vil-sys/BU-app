# BU · PROGRESS

Discipline: this file is updated after every phase. English-only. Commit format `feat(scope): …`.

---

## P0 — Scaffolding & proof  ·  2026-11-04  ·  status: CI GREEN · APK artifact live

**Locked plan:** `research/BU-v4-FINAL-PLAN-LOCKED.md` (supersedes all prior plans).

**Delivered**

- 5 Gradle modules per the dependency law (`:app :core :designsystem :identity :guardian`), Kotlin 2.0.21 / AGP 8.7.3 / Compose BOM 2024.10.01 / Hilt 2.52 / Room 2.6.1, minSdk 29 · target 35.
- **BTK skeleton in `:core`:** `CommandGateway` (submit/approve/reject/undo) · durable `command_inbox` · append-only `domain_events` · transactional `outbox` · `capture_inbox` · idempotency-key retry safety · pure validators · injectable clock · Room-backed stores with a `TransactionRunner` seam for JVM tests.
- **Kernel contract tests (6, JVM):** atomic commit (capture+event+outbox in one transaction), idempotent retry (I5), rejection with audit, undo-as-compensation (I10), unknown-command refusal, approval-contract stub. Plus Big Three rules tests and Shield ladder ceiling tests (I11).
- **Design system v3:** near-black/gray/mint tokens · dark-native theme · four named springs (`snappy/bouncy/screen/soft`) · haptic grammar (`tick/grab/win/guard`) · `BuDayArc` Canvas · **`BuArcDial`** (the slider replacement: 15-min snap, haptic ticks, value bubble, ±5 m accessible steppers) · **`BuPebble`** (draggable, magnetic edge-dock, position remembered, drag never fires capture — owner's lively-+ requirement) · `BuCard` + instructive `BuEmptyState`.
- **LOOM shell:** 5 tabs (NOW · PLAN · JOURNAL · ATLAS · SHIELD) as a swipe pager with a leading spring indicator; capture sheet submits through the kernel and shows the receipt; NOW shows a live kernel pulse (captures today + queued async work) reading real ledger flows; PLAN hosts an interactive Arc-Dial demo; every future screen states its phase honestly.
- CI: assembleDebug + kernel/domain unit tests + lint → debug APK artifact on every push.

**Exit criteria check (locked plan §13 P0)**

- [x] CI green + APK artifact
- [x] 5 tabs swipe · Arc renders
- [x] one demo command round-trips through kernel with event + receipt (Pebble → CaptureSheet → BTK → capture row + CAPTURE_SAVED event + 2 outbox effects → receipt toast)

**Owner test checklist (download APK from the Actions run)**

1. App installs and opens dark; five tabs swipe with the sliding mint indicator.
2. Tap the Pebble (it drags, docks to an edge, and position is remembered) → Capture sheet → type → Capture → toast shows a receipt id.
3. Kill the app, reopen: the NOW tab's "captures today" count survives — the capture survived process death.
4. PLAN tab: drag the Arc-Dial — snaps in 15-minute steps with haptic ticks; ±5m buttons work.
5. No red anywhere; carried/danger colours only where destructive.

**Next phase:** P1 — The Loop (vertical slice), starts on owner approval.
