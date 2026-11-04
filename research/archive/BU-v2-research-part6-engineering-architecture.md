# BU v2 · Research Part 6 — Deep Engineering Architecture
### A computer-engineering-grade architecture study for a single-user, offline-first Android life system with an on-device AI core

Date: 3 Nov 2026 · Status: delivered for owner review · Feeds the v3 unified spec (Part 7) and replaces the architecture chapter of the v1 BUILD-GUIDE where they disagree.

> **One-sentence thesis.** BU is not "an app with a database" — it is a **24/7 instrument**: a real-time sensing system (usage, journal, plans) feeding a deterministic decision core, surfaced through three always-alive renderers (widget, notification, app), with a small on-device model as the parser layer — and every architectural choice below is made to serve *correctness under neglect, survival under Samsung, and latency under 3 seconds*.

---

## 0 · Inputs reconciled

| Input | Says | Kept / Changed |
|---|---|---|
| v1 BUILD-GUIDE (2 Nov) | single Gradle module, manual DI, minSdk 31, no Hilt, no INTERNET, Dial-centric | Kept: philosophy, storage discipline, perf budgets, vault concepts. Changed: module split, DI, permissions (INTERNET accepted by owner for v2 Guardian) |
| v2 research parts 1–3 (approved) | 5 modules `:app :core :designsystem :identity :guardian`, Kotlin 2 + Compose M3 + Hilt, minSdk 29 / target 35, ~20 tables, custom reminder engine, dual-plane (Identity + Guardian) | Kept as the base. Part 6 refines rather than replaces |
| Owner's Claude blueprint | Flutter UI + Kotlin services hybrid; Needle via JNI; Tomorrow Contract/Week Compass; Firmness Ladder; bandit; template engine; ~9 screens | **Flutter rejected** (§1); all ritual/loop/ladder/bandit concepts adopted into v2 vocabulary (Part 7) |
| Mockup v2 (approved) | the UX truth | Part 5 fixes its defects; Part 7 blends features |

---

## 1 · The platform decision: native Kotlin+Compose vs Flutter hybrid (decisive analysis)

The owner's Claude blueprint proposes *Flutter UI + Kotlin native services sharing state through the database*. That is a legitimate pattern (it's how Mindful-like hybrids and many BaaS apps ship), but for **this** product it is the wrong call. Engineering reasons, ranked:

1. **Every BU surface is a platform surface.** Widget (Glance), persistent notification (custom layout), lock-screen, quick-settings tile, AccessibilityService overlay, VPN control, full-screen intents. On Flutter these are all platform-channel round-trips into hand-written Kotlin anyway — meaning 100% of the *hard* code is Kotlin regardless; Flutter would only own the screens, and BU has ~9 of them. The UI is the *small* half of this codebase.
2. **The 3-Second Contract is a latency contract.** Flutter's texture-composited UI adds a frame pipeline between platform events and pixels. Edge-swipe predictive back, shared-element tab indicators, and 120 Hz spring motion are all *first-class* in Compose and *second-class* in Flutter on Android (predictive back especially).
3. **One model, one JNI bridge, one memory domain.** Needle's C API binds into Kotlin/JNI directly. Under Flutter, the same JNI host must be bridged again through pigeon/channels for Dart — two FFI boundaries, two serialization layers, one more failure surface for the highest-risk dependency.
4. **State synchronization.** "Share state through the database, not platform channels" (blueprint §11) is the right instinct — and it is *simpler* when the UI runtime is the same process language as the services: one Room instance, one `StateFlow` bus, no channel encoding at all.
5. **Team size = 1 human + AI agents.** Two toolchains (Dart + Kotlin + Gradle + pods of platform glue) doubles context for zero user-visible gain. Compile speed is protected by modules, not by switching UI kits.

**Decision: 100% native Kotlin + Jetpack Compose (Material 3), minSdk 29 (Galaxy A54 = Android 13, but 29 keeps One-UI-12 fallback devices alive), targetSdk 35, JVM target 17.** Record: if a public Flutter release is ever wanted, the data core + decision engine are pure Kotlin and portable; only `:app` UI would be rewritten — an acceptable exit cost.

---

## 2 · Module architecture (revised: 5 modules, dependency-graded)

```
:app            (UI shells, navigation, DI wiring, workers, services host)  — Android
:designsystem   (tokens, motion, haptics, components: Arc, cards, pickers)  — Android lib
:identity       (BU Loop domain: plans, rituals, momentum, constellation, journal) — pure Kotlin*
:guardian       (Shield domain: ladder, slip score, enforcement contracts, bandit) — pure Kotlin*
:core           (data core: Room, event log, vault, Needle host, time, result types) — Android lib
```
\* pure-Kotlin modules carry **no Android imports** → JVM unit-testable at full speed (the v1 guide's best instinct, generalised).

Dependency law (acyclic, enforced by Gradle):
`:app → { :designsystem, :identity, :guardian, :core }`; `:identity → :core`; `:guardian → :core`; `:designsystem → nothing (leaf)`.

Why keep 5 modules when 1 would build? (The v1 guide said 1; v2 research said 5; adjudication:)
- **Compile-time boundaries are free reviews.** `:identity` physically cannot import Accessibility classes; `:guardian` cannot touch journal tables. For an AI-agent-built codebase, structural constraints replace "taste" — an agent that can't compile a violation *doesn't write it*.
- **Parallel CI caching:** touch `:designsystem` → only `:app` rebuilds; the 20-table data core stays cached.
- **Cost check:** K2 + configuration cache makes 5 modules ≈ single-module wall-clock at this code size. Measured threshold: revisit only if full build > 90 s.

**DI: Hilt v4 (locked in v2 research).** The v1 guide's manual `AppContainer` is fine at 8 classes; BU has 30+ (repos, engines, workers ×5, services ×5, bandit, rule engine, Needle host). Hilt gives worker/service injection (which manual DI handles badly), testable components, and is boring-proven. `@HiltAndroidApp`, `@HiltWorker` for WorkManager, `@AndroidEntryPoint` on the single Activity.

---

## 3 · The data core (the "single Room/SQLite data core" done properly)

### 3.1 Storage engines and division of truth

| Store | Truth for | Engine | Why |
|---|---|---|---|
| Room/SQLite (WAL) | all *state*: tracks, plans, items, rituals, reminders, rules, contracts, capabilities, momentum cache | `androidx.room` + KSP | typed DAOs, migrations, Flow |
| Event log | *what happened* (append-only, never updated) | same SQLite file, `events` table, month-partitioned views | replayability, Slip Score, Drift, debugging "why did BU act" |
| Vault files | *human-readable prose* (journal bodies) + JSONL day exports | filesystem, atomic write (tmp→fsync→rename→dir fsync), SHA-256 manifest in DB | the user's data must be readable in 10 years with no app (v1 guide §10 kept) |
| Embeddings | Needle vectors for semantic search/constellation | `embeddings` table, **float16-packed BLOB**, brute-force cosine | ≤ 10⁴ entries → brute force is < 5 ms; no vector index needed (re-derive if it ever matters) |
| FTS | full-text search over threads + evidence | SQLite **FTS5** virtual table, content-synced from Room triggers | instant search, zero external deps |

**Encryption:** SQLCipher wraps the DB; Tink AES-GCM wraps vault files; key in Android Keystore (StrongBox when present). Asked at onboarding (capability-card style, tradeoffs stated). *Cost honesty:* SQLCipher adds ~5–10% DB latency — invisible at BU's write volume (< 200 writes/day typical).

### 3.2 Event sourcing — the spine

- `events(id, ts, type, subjectId, payloadJson, source, confidence)` — append-only. Types supersede v1's list: `PLAN_SEALED, ITEM_TALLIED, ITEM_CARRIED, CAPTURE_ADDED, RITUAL_COMPLETED, FOCUS_*`, `USAGE_FOREGROUND_*`, `NOTIF_META_*`, `LADDER_ESCALATED, LADDER_DEESCALATED, OVERRIDE_TAKEN, BANDIT_REWARDED, NEEDLE_ACTED, NEEDLE_CONFIRMED, NEEDLE_REFUSED …`
- **Projections are rebuildable.** Momentum curve, Slip Score, Drift, Reality Multiplier, Care Graph frequencies are *derived views* — if the math changes in v4, we replay history and get new curves. This is the single most important property for a system the owner will trust for 10 years: **the raw truth is never rewritten; the interpretation can be.**
- Determinism rule: every decision-producing function (rule engine, ladder, bandit read) is a pure function of (event history, settings, sealed contract). Coroutines and randomness live only at the edges (bandit *sampling* takes an injectable RNG — replayable with a fixed seed).

### 3.3 Schema skeleton (~24 tables, supersedes both prior lists)

`tracks, plan_items (big|block|spark), item_evidence, day_contracts (Tomorrow Contract rows), week_reviews, carry_ledger, momentum_cache, sparks, threads, thread_revisions, stamps, links, events, reminders (Care Items w/ type anchor|contextual|relational|value|deadline|habit), notification_log, focus_sessions, usage_cache, rules, sealed_policies (ladder ceilings), capability_state, settings, vault_files, embeddings, templates (voice bank), bandit_state`

Key columns carried from the blueprint: `plan_items.estimate_min, adjusted_estimate_min, moved_count (neutral), energy`; `tracks.estimate_min (EWMA), estimate_samples`; `day_contracts.main_front_id, mvd_text, if_then_json, ceiling_ladder (0–5), sealed_at, wake_plan`.

---

## 4 · Process & service architecture — surviving Samsung One-UI

### 4.1 The three renderers, one truth

Widget (Glance), persistent notification (the NOW strip), and the app UI all read the same `StateFlow` from a process-wide `BuGraph` singleton. **No surface computes state locally** — the widget's RemoteViews and the notification's layout are pure projections of the same state snapshot, so the three can never disagree (v1 guide's "one source of truth" law, kept).

### 4.2 Service topology

| Component | Kind | Lifetime |
|---|---|---|
| `BuForegroundService` | FGS `specialUse` | runs whenever any track runs OR ladder ≥ L3 OR a Care anchor is armed within 24 h; otherwise **stopped** (idle = zero cost) |
| `UsageCaptureWorker` | WorkManager periodic 15 min + exact "screen-on" catch-up via incremental `queryEvents(lastTs, now)` | forever, ~0.5%/day battery budget |
| `NotificationListener` | `NotificationListenerService` | metadata-only; exclusion list applied at capture boundary (zero rows for excluded apps) |
| `AccessibilityGuardian` | `AccessibilityService` | foreground-package detection for auto-track + soft-pause + short-form budgets; **event-stream only, never node-text** |
| `ReminderScheduler` | AlarmManager exact (canExactExpiredFallback→ inexact) + BootReceiver re-arm | forever |
| `GuardianOverlayActivity` | activity (not service) for L2 Pause / interstitial | on demand |
| `LadderEnforcer` (later capability) | local VPN (VpnService) for network cut | on demand |

### 4.3 Samsung One-UI survival kit (A54-specific)

1. Onboarding capability card "Battery exemption" with a *device-specific* deep-link (`SECTION_IGNORE_BATTERY_OPTIMIZATIONS` intent) and a fallback instruction card (Settings → Battery → Background limits → Never sleeping apps).
2. After reboot: BootReceiver re-arms exact alarms + posts the NOW notification **silently** (Samsung suppresses FGS start from BOOT_COMPLETED — we degrade to a 09:00 catch-up worker, never crash).
3. The app **never relies on being alive to be right**: every schedule is persisted; every boot re-derives; missed anchors fire a *calm* catch-up line in Dawn Push ("2 anchors waited overnight") instead of instant notification spam.
4. `setAndAllowWhileIdle` + `SCHEDULE_EXACT_ALARM` with graceful degrade card in You → Capabilities (v1 guide's honesty pattern kept).

### 4.4 Concurrency model

- One `SupervisorJob` scope per plane (Identity scope, Guardian scope, IO scope). Rule engine = single-threaded serial channel (v1 guide §13 kept — order matters for ladder math).
- All DB access via Room suspend DAOs on `Dispatchers.IO.limitedParallelism(2)` (SQLite write lock protection).
- StateBus: `MutableStateFlow< BuState >` + `SharedFlow<BuEvent>` (replay 0) — UI collects via `repeatOnLifecycle`, services via `collectLatest` on their own scope.

---

## 5 · The Needle 3 host (highest-risk dependency → engineered like a reactor)

### 5.1 Integration shape

```
:core/ai/NeedleHost.kt            (lifecycle: load → warm → serve → evict)
:core/ai/JniBridge.kt             (System.loadLibrary("needle"); external funs: init/complete/embed/free)
cpp/needle-jni.c                  (C shim over needle.h; NO telemetry paths compiled in — verified at M0 spike)
assets/models/needle3-<layers>.cact   (SHA-256 verified at first run; 8–29 MB by depth)
```

- **Model lifecycle:** lazy load on first AI-requiring action of the session; LRU keep-warm 90 s after last call; evict on `onTrimMemory(TRIM_MEMORY_RUNNING_LOW)` + on background. RSS budget: ≤ 120 MB total process including model.
- **Serving pattern:** `Dispatchers.Default.limitedParallelism(1)` single-flight queue (tiny model → serialize, no batching complexity); calls carry a 400 ms soft timeout → on timeout return `NeedleResult.Refused` and fall through to the deterministic parser (user never waits).
- **Confidence routing (3 tiers, adopted verbatim from blueprint):** high+low-risk → act + log; medium → confirm chip; low/irreversible → refuse + offer deterministic path. **Hard Kotlin guards behind every tool regardless of confidence** (date validation, clamps, §-constitution checks). *Needle may propose; only the sealed-policy rule engine may restrict.*
- **Fine-tuning pipeline (post-M0, before P6):** generate ~3 k synthetic journal entries + commands offline with Claude → LoRA on the frozen base → slice 6–8-layer 4-bit subnetwork → evaluate on owner's hand-labelled 50 real entries → keep the parser if win-rate < threshold. Whistle (16.9 MB STT) evaluated in the same spike for voice capture (multi-language check for Tamil/Sinhala is a spike acceptance item).

### 5.2 The M0 go/no-go spike (unchanged from blueprint, hardened)

Bench on-device: 50 owner phrases, 10-tool schema; record cold-start, p50/p95 latency, RSS, battery/10 calls, extraction accuracy on 10 real-style entries. **Telemetry-off verification is a hard gate** (strace/Network-Inspector during calls; any outbound attempt = NO-GO → parser-only ship, Needle deferred). Airplane-mode test mandatory.

---

## 6 · The decision core (rules + ladder + bandit, deterministic & explainable)

```
:guardian/
  slip/SlipScore.kt        pure: f(events 72h) → 0..1, 3-day decay
  ladder/LadderPolicy.kt   pure: f(slip, sealed ceiling, mood trend, bypass rate) → rung L0..L5
  ladder/EscalationLog.kt  every transition = event + human-readable "why now?" string
  bandit/ThompsonSampler.kt arms = (timeWindow × template × channel); reward = acted ≤ 30 min
  voice/TemplateBank.kt    400–600 tagged templates (situation × firmness × tone); combinatorial grammar
  contracts/SealedPolicies.kt the only writer allowed to raise firmness above L2
```

- **Explainability is a type, not a habit:** every action the decision core takes returns `Decision(action, reasons: List<String>, evidence: List<EventId>)` — the "Why now?" line is rendered from `reasons`, so UI can never show an unexplained action.
- Notification budget: daily cap (default 5 interruptive; rest → digests at bandit-learned moments); rest days per template family (ROGUE habituation model); full-screen only for L4+ anchors the user sealed.
- Low-Mood Guard: mood-trend classifier (pure rule, not Needle) caps ladder at L2, swaps template voice to `supportive`, suppresses all countdown/gap framing for 72 h minimum, surfaces trusted-contact + local helpline card once. Acute-distress heuristics are deliberately conservative (false-positive = extra kindness, acceptable; false-negative reviewed in weekly calibration).

---

## 7 · Performance engineering (A54, 120 Hz)

| Budget (from v1 guide, ratified) | How it's actually enforced |
|---|---|
| cold start < 900 ms | Baseline Profile (Compose + BU critical path) generated by macrobenchmark in CI; `androidx.startup` minimal; Hilt eager-singletons trimmed; no IO on main pre-first-frame |
| frame ≤ 16 ms scrolling Arc | Arc drawn once per frame on Canvas w/ cached Path; tick ring is a pre-built Path list; digits recompose at 1 Hz (separate composition scope); **the Arc never recomposes the 60-tick ring** — only dashoffset moves |
| FGS ≤ 3%/day | 1 Hz notification tick only while a track runs; 1/60 Hz idle; deep-sleep-aligned WorkManager |
| DB write ≤ 50 ms | WAL + prepared statements via Room; batch event inserts (channel → 100/batch) |
| RSS ≤ 120 MB | model eviction policy; `onTrimMemory` hooks; bitmap cache for widget preview only |
| startup-to-interactive animations never block | all entry animation transform/opacity (Part 5 §3.1) |

Profiling gates in CI: `./gradlew :app:connectedBaselineProfileAndroidTest` on a running emulator profile (A54-equivalent: arm64, 6 GB), macrobenchmark table printed to PR comment. Micro-rules: no allocation in `drawArc` path; `derivedStateOf` for anything computed from state in composition; `immutable`/`stable` annotations on UI models.

---

## 8 · Security & privacy threat model (single-user, hostile-world)

| Threat | Control |
|---|---|
| Device theft / shoulder-surf | SQLCipher at rest (optional), app lock (biometric, optional capability card), privacy screen on recents |
| AI overreach | Needle ≠ authority (§5.1); sealed policies only from nightly contract; typed reasons for every override |
| Self-harm-context mishandling | Low-Mood Guard conservative triggers; never diagnostic language; helpline card is local-first (configurable country) — reviewed strings by owner |
| Data loss | 30-day trash (soft delete), weekly export prompt (once), atomic+checksum vault writes, crash-drill test in CI |
| Supply chain | Needle model SHA-256 pinned; Gradle dependency verification (`gradle/verification-metadata.xml`); no dynamic code |
| Over-collection | NotificationListener stores metadata only (enforced by a type that *cannot hold text*); Accessibility reads package transitions only; exclusion list at capture boundary |
| Tamper (protecting user's own rules from their 2 AM self) | Contract Lock: sealed ceiling changes require the nightly ritual context (or a 24 h cool-off), mirroring parental-control lock patterns for the *self* |

---

## 9 · Testing & simulation strategy

1. **Domain purity:** `:identity` / `:guardian` are pure JVM → full TDD coverage of: rollover, carry cooldown, EWMA estimate, marker math, Slip Score decay, ladder transitions, bandit posterior math, Reality Multiplier, template selection.
2. **Event-replay simulator:** record real days of events (owner beta) → replay through the decision core with alternative policies → "what would the ladder have done?" **This is how the ladder is tuned before it ever restricts the owner** (blueprint §11 idea, formalised).
3. **Room migration tests** from every shipped schema (exported schemas checked in).
4. **Crash drills:** kill mid-vault-write, mid-transaction, mid-model-call → recovery assertions.
5. **Screenshot tests** (Paparazzi) for the 9 core screens × dark/soft modes × empty/loading/full states.
6. **Fallback-parser table test:** ≥ 40 owner-real phrases (v1 guide kept).
7. **Manual acceptance script** (12 items, v1 guide §16 kept, re-worded to v2 vocabulary in Part 7).

---

## 10 · CI/CD (GitHub Actions, ratifying v1 guide §17)

- Triggers: push → `main`, PRs, manual. JDK 17 temurin, Gradle 8.11+, AGP 8.7+, Kotlin 2.x, NDK 27.x, SDK 35.
- Pipeline: `testDebugUnitTest` (all modules) → `lintDebug` (errors fail the run) → `assembleDebug` → `assembleRelease` (signed from base64 secrets; **debug-signed fallback if secrets absent — CI never red on missing keystore**) → artifacts `bu-<run>-debug.apk` + release, retention 30 d.
- `versionCode = GITHUB_RUN_NUMBER`, `versionName = 0.<run>`.
- Extra v3 gates: Paparazzi screenshot diffs (warnings), macrobenchmark report (info), dependency-verification (hard fail).
- Repo conventions from v1 guide §19 kept: `PROGRESS.md` after every slice, `DESIGN-NOTES.md` for inventions, commit format `feat(scope): …`.

---

## 11 · Build phases (v3, reconciling v1 M0–M6 with v2 P0–P7)

| Phase | Name | Contents | Exit criterion |
|---|---|---|---|
| **P0** | Scaffolding | repo, CI, 5 modules, tokens/motion v3 in `:designsystem`, BuGraph skeleton, empty Arc screen on device | CI green + APK installs + Arc renders |
| **P1** | Identity core | tracks CRUD, Big Three/Blocks/Sparks, Day Arc live, NOW strip + widget, day boundaries | acceptance 1–5 |
| **P2** | Rituals & reminders | Dusk Close (=Tomorrow Contract), Dawn Push, custom reminder engine, Care anchors, escalation | full nightly loop on real phone, 7 days |
| **P3** | Journal & constellation | threads, vault atomic+checksums, FTS, links/graph, evidence pre-fill, 3-tap check-ins | export→reimport round trip |
| **P4** | Guardian I | usage capture, focus, soft pause, app budgets, notification digests | a real day of events matches reality |
| **P5** | Guardian II | Slip Score, ladder L0–L3, sealed policies, overrides, Reactance/Low-Mood guards | replay-simulated week passes policy review |
| **P6** | Needle | M0 spike (first!), then extraction, embeddings, promise detection, tool calls, voice | spike go/no-go recorded; parser parity |
| **P7** | Hardening | Sunday Review (=Week Compass), Reality Multiplier, widgets polish, encryption, export, backup, perf gates | DoD checklist v1 guide §21 |

**Hard gate order change vs blueprint:** the Needle spike stays *first inside P6*, not global-first — because the parser-fallback (T5) makes Needle deferable, while the Identity core is not. Risk is managed by shipping the deterministic spine first.

---

## 12 · Risk register (engineering view)

| Risk | L | I | Mitigation |
|---|---|---|---|
| Needle JNI/telemetry fails | M | H | M0 spike go/no-go; parser ships regardless; model optional asset |
| Samsung kills FGS / alarms drift | H | M | §4.3 kit; persisted schedules; catch-up in Dawn; user-visible capability health card |
| Accessibility service instability | M | M | degrade to manual chip tap; usage-based fallback for auto-track |
| SQLCipher + FTS5 interactions | M | M | FTS on unencrypted shadow copy of *searched fields only*, or clear-text column vectors — decided at P3 with a benchmark |
| Scope creep (65-screen era relapse) | H | H | 9 screens law (Part 7); every PR must map to a phase exit criterion |
| Data loss during migration from Obsidian vault | L | C | old vault read-only import, additive, checksummed, reversible (v1 guide) |
| Battery verdict from owner after week 1 | M | H | budgets §7 + idle-stopped service + You → System battery page |

---

## 13 · Summary of engineering verdicts

1. **Native Kotlin+Compose everywhere. Flutter rejected** (§1) — services are the product.
2. **5 modules, Hilt, pure-Kotlin identity/guardian planes** — boundaries as enforced reviews.
3. **Event-sourced truth + rebuildable projections** — 10-year interpretation freedom.
4. **Vault files as human-readable second truth** — atomic, checksummed, exportable.
5. **Needle as reactor:** lazy, single-flight, 400 ms timeout, 3-tier confidence, hard guards, telemetry gate, parser fallback always.
6. **Deterministic decision core with typed explainability** — every action self-explains.
7. **Samsung survival by never-needing-to-be-alive** — persisted schedules + catch-up lines, not nagging.
8. **Performance budgets enforced in CI**, not hoped for.
9. **Simulator-first ladder tuning** — the Guardian proves itself on replays before it ever touches the real day.
10. **Phases P0–P7 with the deterministic spine before the AI** — the loop is useful on day one, and Needle makes it brilliant later.
