# BU — Build Guide
### The single source of truth for the building agent

**Project:** BU — a local-first, offline, 24/7 day system for one person (Android).
**Status:** Design + architecture locked. Implementation starts here.
**Date:** 2 Nov 2026
**Device:** Samsung Galaxy A54 (arm64-v8a, Android 13+). Personal sideloaded APK.
**Owner:** one human. **Builder:** GLM 5.2 with full GitHub authorization.

---

## 0 · How to use this document

1. **Read all of §1 and §2 before writing a single line of code.** They are the constitution. Every later section is an implementation of them.
2. Work in **vertical slices** (§18). A slice is a feature that builds, runs and is demoable — not a layer.
3. **Keep the build green.** After every meaningful change: `./gradlew assembleDebug`. If it doesn't build, it isn't done.
4. **Write your progress into `PROGRESS.md`** at the repo root after each slice: what's done, what's next, what's broken. Keep it current, not polished.
5. **Commit per slice**, message format: `feat(scope): short imperative summary`.
6. Where this document says "you may invent", you are explicitly encouraged to — as long as the invention obeys §1 and uses the tokens in §7. Record new components in `DESIGN-NOTES.md`.
7. Where this document gives an exact value (a colour, a duration, a table name, a wire format), **use it exactly**. It was reviewed and decided; drift causes churn.
8. If you hit a genuine contradiction inside this document, **stop, pick the interpretation that best serves §1, implement it, and record the decision in `PROGRESS.md`**. Do not silently improvise.

---

## 1 · The constitution (non-negotiable)

These are the product's soul. They are not style preferences. **A build that violates them is wrong, even if every screen is beautiful.**

1. **Local-first.** Everything lives on this phone. No account, no cloud, no telemetry, no ads, no analytics, no crash reporting to third parties. The app works fully offline from first launch.
2. **No gamification. Ever.** No streaks, no badges, no points, no levels, no achievements, no leaderboards, no "keep the chain alive", no confetti, no comparison to past selves.
3. **No guilt mechanics.** Nothing may imply failure for having "missed" something. Red is reserved for destructive actions only. A day with nothing logged is **not** a failure state. A week of absence is not a failure state.
4. **No pressure notifications.** BU never nags. The only notifications are: (a) reminders the user explicitly created, (b) exactly one daily journal ritual reminder at a user-set time, (c) a session that the user explicitly started and asked to be told about. No escalation loops, no "you're falling behind", no re-nagging, no streaks at risk.
5. **The AI never writes the user's words.** BU may *offer* machine-generated material as an explicit, dismissible suggestion (a "ghost fact", a draft title, a parsed date). It may never insert prose into a journal, never write reflections, never summarise the user's feelings, never write a "summary of your day". The user accepts, edits or rejects — always explicitly.
6. **Show, don't nag.** Status is *shown in the app* (stalled projects, carried-over tasks, inbox age), not pushed.
7. **Low friction over cleverness.** "Three words is a valid note." Any capture path — share sheet, FAB, widget, quick-settings tile, voice — must land in the Inbox in one action.
8. **Explanations, not black boxes.** Every automated action is explainable on screen: what triggered it, what it did, what data it used. Every rule is a readable card the user can edit or disable.
9. **The system must survive neglect.** If the user does nothing for a week, BU must present a calm, honest state — never a wall of red, never a backlog of guilt, never a broken screen.
10. **The user's data is theirs.** Full plain-markdown export at any time, offline, with no tool required. Delete means soft-delete to a 30-day trash, recoverable in-app.
11. **Low friction over cleverness (restated as an engineering rule):** prefer boring, proven APIs. `UsageStatsManager`, `NotificationListenerService`, `AlarmManager`, `WorkManager`, `Room`. Invent only where §6 (the Dial) and §5 (the journal) demand it.
12. **Offline AI, no cloud AI.** Any model runs on-device. If the on-device model fails, the app degrades to a deterministic rule-based parser — never to a network call.

---

## 2 · Locked decisions

Do not revisit these without explicit instruction.

### 2.1 Product

| # | Decision |
|---|---|
| P1 | App name is **BU**. Package `app.bu.life`. |
| P2 | **BU replaces the Obsidian vault** as the system of record. The vault's *folder grammar and markdown format are preserved* so the old "Life Of Diva" vault imports cleanly and exports remain readable. |
| P3 | **v1 driver = the day system** (today screen, capture, tasks, reminders). |
| P4 | **v1 = Android-only, local-first.** No sync, no desktop client, no cloud, of any kind. |
| P5 | **Journal is a first-class citizen**: a linked, wiki-style note system (threads, properties, wikilinks, backlinks, graph). |
| P6 | **Journal policy = blend**: an automatic factual timeline (observed) + manual prose (the user's), with a fixed daily ritual time the user sets, and journaling allowed at any moment. |
| P7 | **Quick capture has its own place**: Inbox → typed into Task / Idea / Experience / Journal fragment / Raw note. Ideas and Experiences are first-class records, never folded into the journal. |
| P8 | **Automation never writes into the journal automatically.** The bridge is an explicit one-tap insertion of an *observed fact*, visually labelled. |
| P9 | The **study system** (subjects → units → topics → practice → recall) is **post-exam** (M6). It must not delay M1–M5. |
| P10 | The ACS exam is **Dec 2026 / Jan 2027, date unconfirmed**. It is stored as data (a reminder + countdown), not hardcoded. |

### 2.2 Design & UX

| # | Decision |
|---|---|
| D1 | **Design language: Material 3 Expressive on ink surfaces.** NOT an Apple imitation. Custom fixed palette (no dynamic colour). Two typeface voices: geometric sans for the system, **serif for journal prose**, mono+tabular for all data/numerals. |
| D2 | **The Dial** (§6) is the signature control and the app's home. It is reused as the widget, the persistent notification and the lock-screen shortcut. |
| D3 | **Track chips** (horizontal rail) are the primary navigation between activities, not a filter. |
| D4 | **Soft blocking only in v1**: apps are paused via the AccessibilityService (an interstitial "BU is focusing" surface), not network-blocked. The Local-VPN block ships later behind its own capability card. |
| D5 | **The Dial's plan marker is a moving estimate** (§6.4). It never judges. Crossing it shifts the arc to muted amber and says "running long — extend or wrap up". It never turns red. |
| D6 | **A one-time setup wizard** builds the initial tracks with the user. No hardcoded starter set. Default output is six tracks, not sixty. |
| D7 | **Journal encryption is asked during onboarding**, in the capability-manifest style, with the tradeoff stated plainly. |
| D8 | **The capability manifest** (§12) is the first-run permission experience. Each card shows what BU *sees*, what it will *never* see, and the literal JSON payload of one event. |
| D9 | **Command palette** (⌘K / centre nav slot) replaces most navigation. Slash-commands inside a thread. |
| D10 | Colour = **track identity**. A track owns a hue for its entire life; the dial, chips, stamps and graph nodes all use it. |
| D11 | The background **wash** is a function of the hour (dawn → day → dusk → night). It never animates except a slow 20s drift. |

### 2.3 Technical

| # | Decision |
|---|---|
| T1 | Kotlin + Jetpack Compose (Material 3) + Room. Single Gradle module, clear package boundaries. |
| T2 | **Manual DI** via an `AppContainer` — no Hilt. Fewer moving parts, faster builds, fewer agent-induced failures. |
| T3 | **Incremental UsageStats queries** (`queryEvents(lastTs, now)`), never a polling loop. |
| T4 | Needle 3 runs **on-device via a JNI shim**, arm64-v8a only, telemetry off, model SHA-256 verified. A spike with a **go/no-go** precedes any dependency on it (§11.1). |
| T5 | **Fallback NL parser** (pure Kotlin, zero model) ships in the same release and takes over automatically if the model is absent, fails to load, or exceeds the accuracy threshold. |
| T6 | Every file write is **atomic** (temp → fsync → rename) and **checksummed** (SHA-256 in the DB). Corruption must never present as "your files are gone". |
| T7 | Room + WAL. All state changes transactional. Soft delete everywhere. |
| T8 | **CI publishes APK artifacts** on every push to `main` (§17). |
| T9 | Keep **minification off** for the first release (JNI + reflection), or add explicit keep-rules if enabled. |
| T10 | `minSdk 31`, `targetSdk 35`, `compileSdk 35`, `abiFilters "arm64-v8a"`. |

---

## 3 · Product spec — the feature inventory

### 3.1 The mental model

- **Track** — one real-life goal, e.g. "Physics", "Reading", "Workout", "Guitar", "Sleep", "Free time". A track has a *mode* (§6.3) and a hue.
- **Session** — one run of a track. Timed, counted, or simply bounded.
- **Thread** — a markdown note. Daily threads (one per day) and standalone notes/ideas/experiences.
- **Stamp** — a timestamped line in a thread. Either *observed* (machine-recorded fact) or *authored* (the user's words). A ghost fact is a machine-offered observation awaiting explicit acceptance.
- **Event** — the append-only record of what the device did. Events become stamps only when accepted.
- **Link** — a `[[wikilink]]` between threads/tracks/ideas.
- **Rule** — a visible automation card: trigger → condition → action, with a run log.
- **Capability** — a permission the app may hold, described to the user in plain language.

### 3.2 Feature list (grouped, with milestone)

**A · Onboarding & capabilities** (M0/M1)
- A1 Welcome screen: three promises, "Grant capabilities", "Skip for now".
- A2 **Capability manifest** (§12) — 8 cards, live JSON samples, per-item grant, "grant the remaining".
- A3 **Setup wizard** (D6): pick goals → generated tracks with modes → day-start hour + ritual time → "Start BU".
- A4 Encryption choice (§D7) inside the manifest.

**B · The Dial / Now** (M1)
- B1 The Dial (§6).
- B2 Track chip rail: switch track in one tap; shows the live counter.
- B3 "Today so far" — stamps of the day, observed vs confirmed, with ghost facts offered inline.
- B4 "Next up" — upcoming fixed events and blocks.
- B5 NOW strip — persistent on every screen: current track, elapsed, play/pause.
- B6 Pause / resume / switch / log-stamp actions.

**C · Journal / Threads** (M2)
- C1 Thread list (daily / notes / ideas / all) with property chips and serif previews.
- C2 Thread editor: markdown, `[[wikilink]]` autocomplete, `/`-commands, live property block.
- C3 Property block: typed key/value grid (one thing, energy, sleep, tags, tracks).
- C4 Backlinks at the bottom of a thread.
- C5 Local graph view + orphan list ("a thought you haven't revisited").
- C6 Revision history per thread: any version restorable in-app. No git, no terminal.
- C7 Journal ritual: one quiet daily reminder at the user-set time, opening today's thread with the day's stamps pre-laid-out.
- C8 Observed facts can be inserted into a thread with one tap; inserted lines are visually distinct and deletable.

**D · Capture & Inbox** (M2)
- D1 Share-sheet receiver, in-app FAB, widget, quick-settings tile → Inbox.
- D2 Inbox triage: oldest first; route to Task / Idea / Experience / Journal fragment / Raw note.
- D3 NL capture (Needle, with fallback parser): "in 3 days book lab slot" → task + reminder.
- D4 Voice capture (on-device `SpeechRecognizer` where available) and photo attach — optional, non-blocking.

**E · Tasks, projects, day plan** (M1/M2)
- E1 Tasks: text, done, due date, recurrence, project link, source (manual / NL / carried).
- E2 **Auto-rollover**: an undone task due on or before today carries forward to today; the original is preserved, nothing is lost, and carrying is never framed as failure.
- E3 Fixed events (recurring: daily / every N days / weekdays / monthly / yearly) and time blocks with planned-vs-logged minutes.
- E4 Projects with lanes (Now / Waiting / Someday), a `nextAction`, and a **stalled** state surfaced as a question, never a failure.
- E5 Repeating tasks and events appear on the relevant day automatically.

**F · Automation & rules** (M3)
- F1 Rule cards: trigger, condition, action, enabled, last-run, run count, dry-run preview.
- F2 Automatic track start when an app is opened (AccessibilityService), per track.
- F3 Focus sessions: profile (Study / Work / Creative), duration, paused apps, notification policy.
- F4 Soft pause of chosen apps during a focus session (D4).
- F5 Notification policy during focus: mute, or collect-and-deliver-once at the end.
- F6 End-of-session debrief: **exactly one question** — "what got done?" — and the answer is filed into the day's thread as an authored stamp.

**G · Observation** (M4)
- G1 Foreground app timeline from UsageStats (incremental queries, T3).
- G2 Notification metadata log (package, category, timestamp, dismissed?) — **no message text, ever**.
- G3 Exclusion list (banking, 2FA, password managers, private chat, health): excluded apps produce **no events at all**, and the exclusion is visible to the user.
- G4 Sleep estimation from screen-off duration (a fact, labelled as such).
- G5 Raw daily event log as JSONL in the vault (for the user's own inspection).

**H · Focus, wellbeing, control** (M3/M4)
- H1 Bedtime mode: pause listed apps, DND on, resume at the day-start hour.
- H2 Invincible mode (later): once a limit is hit, the app cannot lift it.
- H3 Day boundary hour (default 04:00) so a night owl's day is correct.

**I · Data, backup, security** (M5)
- I1 Full vault export (folder or zip) + single-thread export (md / pdf).
- I2 Scheduled weekly export reminder (one quiet prompt, after the first week).
- I3 Soft delete → 30-day trash → restore.
- I4 Optional encryption at rest (DB + vault) with a hardware-keystore-backed key.
- I5 Never-implied-loss guarantee: an empty index or a lagging index must never render as "files missing".

**J · Widget & surfaces** (M3/M4)
- J1 Home-screen widget: the Dial, tappable.
- J2 Persistent notification: the Dial, with actions.
- J3 Quick-settings tile: log stamp / start focus / open today's thread.

**K · Study system** (M6, post-exam)
- K1 Subjects → units → topics; practice write-back; recall queue; spaced repetition.

---

## 4 · Information architecture

```
Onboarding (no nav bar visible)
  1  Welcome
  2  Capability manifest
  3  Setup wizard  →  4  Your tracks

Main — bottom navigation, 5 slots, centre slot = command palette
  5  Now        — the Dial, chips, today-so-far, next up
  6  Threads    — journal list + orphans
  7  Thread     — editor, properties, backlinks, history
  8  Graph      — local link graph + orphans
  9  Focus      — session, paused apps, notification policy
 10  Inbox      — capture and triage
 11  You        — capabilities, tracks, rules, data, export

Overlays
  ●  Command palette (⌘K)
  ●  Capture sheet (FAB, share sheet)
  ●  NOW strip (persistent, above the nav bar)
```

**Navigation rules**
- The nav bar is hidden during onboarding.
- The NOW strip floats above the nav bar on Now / Threads / Inbox. It is hidden on Focus (the session owns the screen) and during onboarding.
- The command palette is the only way to reach: new stamp, start focus, insert fact, jump to a track/thread, export. Keep it under 20 entries, grouped, with fuzzy search.
- Every screen reachable in ≤2 taps from Now.

---

## 5 · UI/UX design system

### 5.1 Principles restated for the builder

Minimal but complete. Quiet chrome, loud data. The instrument is the interface. **No decorative gradients except the Dial arc and the brand mark. No drop shadows except elevation of floating surfaces. No rounded-pill buttons except primary actions and chips.**

### 5.2 Colour tokens

Dark is the default theme.

```
--ink            #06060B   app background
--ink-2          #0A0A11   raised background
--surface        rgba(21,21,29,.72)   glass card
--surface-2      #14141C   solid card
--surface-3      #1C1C26   control fill
--hairline       rgba(255,255,255,.085)
--hairline-2     rgba(255,255,255,.15)

--text           #F5F5F7
--text-2         rgba(245,245,247,.56)
--text-3         rgba(245,245,247,.30)

--amber          #FFB020   signal · current track · primary action
--pink           #FF6FA5   gradient tail · "Work" track
--cyan           #3DE0D0   observed facts · links
--violet         #8B7BFF   ideas · secondary tracks
--green          #4ADE80   done · granted
--red            #FF5C5C   destructive ONLY — never "you missed"
```

Light theme = **warm paper**, not grey:

```
--ink            #F3F0E9
--surface        rgba(255,255,255,.80)
--surface-2      #FBFAF7
--surface-3      #F1EEE6
--text           #14141A
--text-2         rgba(20,20,25,.55)
--amber          #B26A00
--cyan           #0B8C83
--violet         #5C48D6
--green          #128C4A
--red            #C93A3A
```

**Rules**
- Red appears only on destructive confirmation UI. Never on a missed task, an incomplete track, or a streak.
- Amber = the current thing (track, action, focus). It is the app's accent.
- Cyan = machine-observed data. Violet = the user's ideas. Green = done/granted.
- Every track is assigned a hue from the palette at creation; the user may override.

### 5.3 Type

| Role | Family | Size/Line | Weight | Tracking |
|---|---|---|---|---|
| Display (journal thread title) | serif | 30/34 | 400 | −0.6 |
| Title 1 | sans | 30/34 | 700 | −1.1 |
| Title 2 | sans | 19/25 | 700 | −0.5 |
| Headline | sans | 15.5/21 | 600 | −0.3 |
| Body | sans | 14.5/21 | 400 | −0.2 |
| Body serif (journal prose) | serif | 16.5/27 | 400 | 0 |
| Label | sans | 12.5/17 | 600 | −0.1 |
| Caption | sans | 11.5/15 | 400 | 0 |
| Eyebrow (uppercase) | sans | 10/14 | 800 | 1.5 |
| Mono / data | mono | 11.5–13/18 | 500–600 | 0 |
| Dial digits | mono | 44/46 | 600 | −2.5 |

- Sans: `FontFamily.SansSerif` (Roboto) or bundle **Inter** if you prefer the look; **do not** fetch webfonts at runtime.
- Serif: `FontFamily.Serif` (Noto Serif) — used **only** inside threads.
- Mono: `FontFamily.Monospace` with `FontFeatureSettings("tnum")` for all times/durations so digits never reflow.

### 5.4 Shape, spacing, elevation

- Spacing grid: **4dp base**; standard insets 20dp (screen), 15–16dp (card content), 10dp (list rows).
- Radii: card 26dp · inner tile 20dp · chip 999dp · button 999dp (primary) / 20dp (secondary) · settings-row icon 8dp · sheet top 30dp.
- Elevation: 0 for cards (they are separated by hairlines), 24dp for floating surfaces (NOW strip, sheet, FAB) with a coloured shadow (`spotColor` = accent at 35%).
- Glass: real backdrop blur via **Haze** (`dev.chrisbanes.haze`) where available. **Never nest a translucent surface inside another translucent surface.** One blur layer per screen.

### 5.5 Motion

| Action | Spec |
|---|---|
| Dial arc sweep | 600ms, decelerate, never loops |
| Dial digits | update once per second, tabular, never reflows |
| Chip switch | 160ms colour cross-fade + 2% scale |
| Button press | scale 0.97, 140ms |
| Sheet present | 420ms, spring `damping 0.9 / stiffness 380` |
| Palette open | 120ms fade + 8dp rise |
| Everything else | still. A control room should not fidget. |

Respect the system "remove animations" setting. The only ambient animation is the 20s wash drift.

### 5.6 Component inventory (minimum)

`Dial`, `TrackChip`, `NowStrip`, `StampRow`, `GhostFact`, `PropertyBlock`, `ThreadListItem`, `LinkChip`, `CapabilityCard`, `CapabilityPayload`, `RuleCard`, `Segmented`, `PrimaryButton`, `GhostButton`, `Chip`, `Pill`, `Switch`, `ProgressArc`, `TagInput`, `CommandPalette`, `CaptureSheet`, `EmptyState`, `ConfirmDestructive`, `RevisionList`, `GraphView`, `SessionTimer`, `AppTile`.

**Empty states are instructions, not apologies.** e.g. "Nothing written yet today — write a line, or insert a fact you observed."

### 5.7 Screen specs

**Welcome** — wordmark, headline "A day system that runs itself.", three bullet promises, primary "Grant capabilities", secondary "Skip for now", footnote "No account. No cloud. Everything stays on this phone."

**Capability manifest** — title "Capabilities"; progress meter `3 / 8 granted`; per card: icon tile, name, one-line purpose, status pill or switch, a **Sees** line, a **Never** line, and a mono JSON payload sample; final primary button "Grant the remaining 4"; footnote about revocability.

**Setup wizard** — "What matters?" → 2-column grid of goal cards (selected / add) → "Build my tracks" → track list with editable name + mode + hue + plan → "Your day" block (day-start hour, ritual time, plan-marker policy) → "Start BU".

**Now** — the Dial centred; below it "Pause / Log stamp"; a one-line explanation of the moving marker; the track chip rail; "Today so far" stamp list with ghost facts; "Next up". The NOW strip floats above the nav bar.

**Threads** — search field; segmented `Daily · Notes · Ideas · All`; thread list items with mono date, serif title, serif preview, property chips; an "Ideas" section showing unlinked ideas with an "unconnected" pill.

**Thread** — back button + date in the top bar; property block (mono values, inline-editable); serif body; inserted observed facts as cyan left-bordered stamps; ghost facts as dashed cyan blocks with an "Insert" button; "Linked from" backlinks; "Revisions" section; footer hint "Type / for commands · [[ to link".

**Graph** — node/edge canvas; node shapes by kind (daily = amber square, idea = violet circle, track = cyan diamond, orphan = grey small circle); legend; orphan list.

**Focus** — session dial; "Blocked during this session" list with switches; "Notifications" policy switches; "End session" primary button; footnote about the single debrief question.

**Inbox** — capture field at the top; segmented filter; rows with mono capture time, content, type pill.

**You** — capability summary, ritual time, day-start hour, model status; Tracks list (name, mode, colour dot); Rules list (plain-language trigger, last run); Data (export vault, last backup, trash).

---

## 6 · The Dial (signature component)

### 6.1 Geometry

- Canvas 268dp × 268dp. Centre 134,134.
- **Tick ring:** 60 ticks between radius 120 and 127. Every 5th tick is 11dp long and 2.2dp wide; the rest 5dp long, 1.3dp wide. Ticks up to the elapsed fraction are amber; the remainder are `rgba(255,255,255,.10)`. Round caps.
  - 60 ticks = 60 minutes: the ring is a gauge *and* a clock face.
- **Progress arc:** radius 128, stroke 5dp, round cap, linear gradient amber → pink at 135°. `dasharray = circumference × elapsedFraction`.
- **Plan marker:** radius 128, stroke 6dp, round cap, **cyan**, a short 16dp dash parked at the marker fraction.
- **Core:** inset 34dp, filled with `--surface-2` plus a faint radial amber glow at the top.
- **Core content:** eyebrow (`PHYSICS · TOTAL TODAY`), digits (mono, 44sp), track name, plan line.

### 6.2 States

| State | Digits | Plan line | Arc |
|---|---|---|---|
| Running | elapsed, mono | "on track · ~20 min left" | amber→pink gradient |
| Paused | elapsed + `⏸` | "paused" | gradient at 60% opacity |
| Count-to mode | `3 / 5` | "chapters" | arc = progress of the count |
| Over the moving marker | elapsed | "running long — extend or wrap up" | muted amber (never red) |
| No track | `--:--` | "pick a track" | empty, inert ticks |

### 6.3 Track modes

| Mode | Behaviour |
|---|---|
| `COUNTDOWN` | counts down from `planMinutes`; shows overtime after zero, in amber, never as an error |
| `STOPWATCH` | counts up from 0 |
| `TOTAL` | counts up, accumulating across the whole day (does not reset on pause/resume) |
| `COUNT_TO` | a counter with a unit ("5 chapters"); the dial arc shows the count's progress |
| `ALWAYS` | has a checklist but **structurally cannot be incomplete** — it is complete by definition |

### 6.4 The moving-estimate policy (D5) — implement exactly

```
estimateMinutes  = EWMA of actual session durations for this track
initial value     = planMinutes
update after each completed session of this track:
    estimate ← 0.7 × estimate + 0.3 × actualDuration
    estimate = clamp(estimate, 0.5 × planMinutes, 2.0 × planMinutes)

markerFraction   = (elapsedMs + estimateRemainingMs) / planMinutes   (clamped to [0,1.4])
```
- `planMinutes` and `estimateMinutes` are **separate persisted columns**. The UI shows the estimate; the plan stays visible in the track's settings.
- With fewer than 3 recorded sessions, the marker **is** the plan, and the plan line says "90 min planned".
- The estimate is per track, user-overridable, and can be pinned to a fixed number.

---

## 7 · Architecture

### 7.1 Shape

**One Gradle module (`app`)**. Clear package boundaries, no multi-module churn.

```
app/src/main/java/app/bu/life/
├── BUApp.kt                  Application; owns AppContainer; starts services
├── MainActivity.kt           Single activity, edge-to-edge, Compose host
├── core/
│   ├── AppContainer.kt        manual DI graph
│   ├── Dispatchers.kt        io/default/main qualifiers
│   └── TimeProvider.kt       injectable clock (testability) + DayBoundary
├── domain/
│   ├── model/                pure Kotlin: Track, Session, Thread, Stamp, Link, Event, Rule, Capability
│   ├── usecase/              RollOver, CloseDay, Triage, TrackEstimate, StalledProjects, Debrief, GhostFacts, PlanMarker
│   ├── rules/                deterministic engines: recurrence, marker math, daily aggregation
│   └── nlp/                  NL parser interfaces + fallback rule parser
├── data/
│   ├── db/                   Room: entities, DAOs, database, migrations, type converters
│   ├── vault/                markdown read/write, atomic IO, checksums, frontmatter, link index
│   ├── events/               append-only event store + writers
│   ├── repo/                 repositories: Track, Thread, Stamp, Task, Event, Rule, Capability, Settings
│   └── export/               vault export (folder/zip), thread export (md/pdf), JSONL writer
├── services/
│   ├── DayForegroundService.kt     always-on; the Dial notification
│   ├── UsageCaptureWorker.kt       incremental UsageStats queries (WorkManager)
│   ├── NotificationCaptureService.kt  NotificationListenerService, metadata only
│   ├── AccessibilityLaunchService.kt  app-open detection + soft pause + auto track start
│   ├── ReminderReceiver.kt         AlarmManager exact alarms
│   ├── BootReceiver.kt             reschedule alarms + services after reboot
│   ├── RuleEngine.kt               evaluates rule cards
│   └── WidgetProvider.kt           Glance widget (the Dial)
├── ai/
│   ├── NeedleEngine.kt       Kotlin facade over the JNI shim
│   ├── ToolSchemas.kt        tool definitions + JSON schemas
│   ├── ConfidencePolicy.kt   execute / confirm / refuse thresholds
│   └── NativeBridge.kt       external funcs
├── ui/
│   ├── theme/                Color.kt, Type.kt, Shape.kt, Motion.kt, Wash.kt
│   ├── components/           the §5.6 inventory
│   ├── now/ threads/ thread/ graph/ focus/ inbox/ you/ onboarding/
│   ├── palette/              command palette
│   └── nav/                  bottom bar, NOW strip
└── platform/                 Tink/Keystore, SQLCipher setup, FileLock, Backup, DeviceInfo
```

**Layering rules**
- `domain` is **pure Kotlin** — no Android imports. Unit-testable on the JVM.
- `data` knows nothing about Compose. `ui` never touches Room directly; it goes through repositories/use cases.
- `services` may call repositories; repositories never call services.
- `ui` state is held in ViewModels with `StateFlow`; no Compose in domain.

### 7.2 Dependencies

Use **current stable** versions available at implementation time; if a version below doesn't resolve, take the nearest stable. Record the resolved versions in `PROGRESS.md`.

| Need | Artifact |
|---|---|
| Compose | `androidx.compose:compose-bom` (latest stable) + `material3`, `foundation`, `ui-tooling-preview` |
| Navigation | `androidx.navigation:navigation-compose` |
| Room | `androidx.room:room-runtime`, `room-ktx`, `room-compiler` (KSP) |
| Serialisation | `org.jetbrains.kotlinx:kotlinx-serialization-json` |
| Coroutines | `org.jetbrains.kotlinx:kotlinx-coroutines-android` |
| Datetime | `org.jetbrains.kotlinx:kotlinx-datetime` |
| Work | `androidx.work:work-runtime-ktx` |
| Haze (backdrop blur) | `dev.chrisbanes.haze:haze` (+ `haze-material3` if present) |
| Glance (widget) | `androidx.glance:glance-appwidget` |
| Markdown | `org.commonmark:commonmark` (parse) + a small Compose renderer you write |
| Crypto | `com.google.crypto.tink:tink-android` (AES-GCM per-file keys wrapped by the Android Keystore) |
| SQLCipher (optional, when D7 = encrypted) | `net.zetetic:sqlcipher-android` + `androidx.sqlite:sqlite` |
| Tests | `junit`, `kotlinx-coroutines-test`, `turbine`, `androidx.test.ext:junit`, `androidx.room:room-testing` |

Do **not** add: Hilt, Retrofit/OkHttp, Firebase, any analytics SDK, any ad SDK, any image-loading library that needs the network.

### 7.3 Storage model

Two layers, one truth each:
- **SQLite (Room)** = the truth for *state* (tracks, sessions, tasks, links, events, rules, settings, indexes).
- **Markdown vault (files)** = the truth for *prose* (thread bodies) and the human-readable record.

They are kept in sync by a single writer (`VaultWriter`) that is the only code allowed to touch vault files.

---

## 8 · Data model (Room)

Exact table and column names — implement as specified.

```kotlin
// tracks
@Entity(tableName="tracks")
data class Track(
  @PrimaryKey val id: String,                 // uuid v4
  val name: String,
  val mode: String,                           // COUNTDOWN | STOPWATCH | TOTAL | COUNT_TO | ALWAYS
  val colorHex: String,
  val planMinutes: Int?,                     // COUNTDOWN/TOTAL
  val targetCount: Int?,                     // COUNT_TO
  val unit: String?,                         // "chapters"
  val estimateMinutes: Int?,                 // EWMA (§6.4)
  val estimateSampleCount: Int,              // how many sessions fed the EWMA
  val checklistJson: String,                 // ["step 1","step 2"]
  val sortOrder: Int,
  val archived: Boolean,
  val createdAt: Long,
  val updatedAt: Long
)

// track_sessions
@Entity(tableName="track_sessions")
data class TrackSession(
  @PrimaryKey val id: String,
  val trackId: String,
  val mode: String,                          // snapshot at session start
  val startedAt: Long,
  val endedAt: Long?,
  val plannedMinutes: Int?,
  val countStart: Int?,
  val countEnd: Int?,
  val completedManually: Boolean,
  val debriefAnswer: String?,                // "what got done?"
  val source: String                         // MANUAL | AUTO_OPENED_APP | RULE | WIDGET
)

// threads (journal notes: daily, notes, ideas, experiences)
@Entity(tableName="threads", indices=[Index(value=["date"], unique=true)])
data class Thread(
  @PrimaryKey val id: String,
  val kind: String,                          // DAILY | NOTE | IDEA | EXPERIENCE
  val date: String?,                         // yyyy-MM-dd, unique for DAILY
  val title: String,
  val vaultPath: String,                     // relative path in the vault
  val propertiesJson: String,                // {"one_thing":"...","energy":3,...}
  val body: String,                          // markdown prose
  val wordCount: Int,
  val createdAt: Long,
  val updatedAt: Long,
  val archived: Boolean
)

// stamps (lines inside threads)
@Entity(tableName="stamps", indices=[Index("threadId"), Index("eventId")])
data class Stamp(
  @PrimaryKey val id: String,
  val threadId: String,
  val at: Long,
  val text: String,
  val source: String,                        // OBSERVED | CONFIRMED | AUTHORED
  val eventId: String?,                      // traceable to an activity event
  val trackId: String?,
  val durationMs: Long?
)

// links (wikilinks between threads / tracks)
@Entity(tableName="links", indices=[Index("fromId"), Index("toId")])
data class Link(
  @PrimaryKey val id: String,
  val fromType: String, val fromId: String,
  val toType: String, val toId: String,
  val context: String?
)

// events (append-only; never updated, never deleted except on export/reset)
@Entity(tableName="events", indices=[Index("ts"), Index("packageName")])
data class ActivityEvent(
  @PrimaryKey val id: String,
  val type: String,                          // see §9.1
  val ts: Long,
  val endTs: Long?,
  val packageName: String?,
  val payloadJson: String,
  val source: String,                        // USAGE_STATS | NOTIFICATION_LISTENER | ACCESSIBILITY | SERVICE | USER
  val confidence: Float
)

// tasks
@Entity(tableName="tasks")
data class Task(
  @PrimaryKey val id: String,
  val text: String,
  val done: Boolean,
  val dueDate: String?,                      // yyyy-MM-dd
  val projectId: String?,
  val recurrence: String?,                   // DAILY | EVERY_N | WEEKDAYS | MONTHLY | YEARLY | "n:3"
  val source: String,                        // MANUAL | NL | CARRIED | CAPTURE
  val carriedFromId: String?,
  val carriedCount: Int,                     // times carried — displayed neutrally, never as a streak
  val createdAt: Long,
  val completedAt: Long?,
  val archived: Boolean
)

// projects
@Entity(tableName="projects")
data class Project(
  @PrimaryKey val id: String,
  val name: String,
  val lane: String,                          // NOW | WAITING | SOMEDAY
  val why: String?,
  val doneLooksLike: String?,
  val nextAction: String?,
  val nextActionUpdatedAt: Long?,
  val colorHex: String,
  val archived: Boolean,
  val createdAt: Long
)

// inbox
@Entity(tableName="inbox_items")
data class InboxItem(
  @PrimaryKey val id: String,
  val content: String,
  val kind: String,                          // TASK | IDEA | EXPERIENCE | JOURNAL | NOTE
  val createdAt: Long,
  val triagedAt: Long?,
  val targetRef: String?
)

// rules (visible automation cards)
@Entity(tableName="rules")
data class Rule(
  @PrimaryKey val id: String,
  val title: String,                         // plain language: "When I open Figma → start Work"
  val trigger: String,                       // APP_OPENED | TIME | EVENT | SESSION_END
  val conditionJson: String,
  val actionJson: String,
  val enabled: Boolean,
  val runCount: Int,
  val lastRunAt: Long?,
  val lastResult: String?
)

// capabilities (drives the manifest screen — same table that requests them)
@Entity(tableName="capabilities")
data class Capability(
  @PrimaryKey val key: String,               // USAGE_ACCESS | NOTIFICATIONS | EXACT_ALARM | ...
  val title: String,
  val purpose: String,
  val sees: String,
  val never: String,
  val samplePayload: String,
  val required: Boolean,
  val sortOrder: Int
)

// reminders
@Entity(tableName="reminders")
data class Reminder(
  @PrimaryKey val id: String,
  val title: String,
  val at: Long,
  val repeat: String?,
  val taskId: String?,
  val trackId: String?,
  val enabled: Boolean,
  val lastFiredAt: Long?
)

// focus_sessions
@Entity(tableName="focus_sessions")
data class FocusSession(
  @PrimaryKey val id: String,
  val profile: String,                       // STUDY | WORK | CREATIVE | CUSTOM
  val trackId: String?,
  val startedAt: Long,
  val endedAt: Long?,
  val plannedMinutes: Int?,
  val pausedPackagesJson: String,
  val notificationsMuted: Boolean,
  val debriefAnswer: String?
)

// settings (key/value)
@Entity(tableName="settings")
data class Setting(@PrimaryKey val key: String, val value: String)

// vault_files (checksums + revision tracking for markdown files)
@Entity(tableName="vault_files")
data class VaultFile(
  @PrimaryKey val path: String,
  val sha256: String,
  val sizeBytes: Long,
  val modifiedAt: Long,
  val lastVerifiedAt: Long,
  val revision: Int
)

// thread_revisions (in-app history)
@Entity(tableName="thread_revisions")
data class ThreadRevision(
  @PrimaryKey val id: String,
  val threadId: String,
  val createdAt: Long,
  val body: String,
  val propertiesJson: String
)
```

**Indices:** `tasks(dueDate, done)`, `track_sessions(trackId, startedAt)`, `stamps(threadId, at)`, `events(ts)`, `events(packageName)`.

**Foreign keys:** use explicit `ForeignKey` with `onDelete = CASCADE` for children of a track/thread. Provide `exportSchema = true` and a `schemas/` directory (checked in) so migrations are testable.

---

## 9 · The event log

### 9.1 Event types

```
APP_FOREGROUND_STARTED / APP_FOREGROUND_ENDED
NOTIFICATION_POSTED / NOTIFICATION_DISMISSED
TRACK_STARTED / TRACK_PAUSED / TRACK_RESUMED / TRACK_ENDED
TASK_CREATED / TASK_COMPLETED / TASK_CARRIED
SESSION_STARTED / SESSION_ENDED / SESSION_DEBRIEF_RECORDED
FOCUS_STARTED / FOCUS_ENDED
RULE_FIRED / RULE_BLOCKED
REMINDER_FIRED / REMINDER_DISMISSED
THREAD_OPENED / THREAD_EDITED / STAMP_INSERTED
CAPTURE_RECEIVED / INBOX_TRIAGED
PERMISSION_CHANGED / EXPORT_CREATED / DAY_ROLLED
```

### 9.2 Capture rules

- **UsageStats:** query incrementally. Persist `lastUsageQueryTs`; each run calls `queryEvents(lastTs, now)` and translates `ACTIVITY_RESUMED`/`ACTIVITY_PAUSED` into start/end pairs. Run it from a `WorkManager` periodic job (~2 min) **and** on screen-on. Never a tight loop.
- **Notifications:** `NotificationListenerService`. Record only package, category, timestamp, and dismissed-state. **Never read `Notification.extras` message text.** Apply the exclusion list before writing anything.
- **Accessibility:** detect the foreground package on `TYPE_WINDOW_STATE_CHANGED` to auto-start tracks and to intercept launches for soft pause. Never inspect node text except the package name.
- **Exclusions:** the user's exclusion list is applied at the **capture** boundary, so excluded apps produce zero rows — not "hidden later".

### 9.3 Privacy

- All events are local. The JSONL log in the vault is the user's own copy.
- No event ever leaves the device. No network permission is required for any core feature; if the later VPN blocking feature ships, the `INTERNET` permission is added with an explicit manifest card (§12) and the app must still never make an outbound request.

---

## 10 · The vault (markdown)

### 10.1 Layout

```
BU/
├── Threads/2026/11/2026-11-03.md      daily thread
├── Threads/notes/<slug>.md            standalone notes
├── Ideas/<slug>.md
├── Experiences/2026.md
├── Inbox/<id>.md                      untriaged captures (optional; DB is the truth)
├── Activity/2026/11/2026-11-03.jsonl  raw daily events
├── Exports/
└── System/preferences.md
```

### 10.2 Frontmatter

```yaml
---
id: 0192f0c1-...
kind: daily
date: 2026-11-03
title: Tuesday
one_thing: Finish problem set 3-7
energy: 3
sleep: 6h 42m
tags: [physics, exam]
tracks: [physics, reading]
links: 7
---
```

### 10.3 Write discipline (T6) — non-negotiable

```
writeVaultFile(path, content):
  1. bytes = content.encode()
  2. sha = sha256(bytes)
  3. tmp = path + ".tmp"           // same directory → same filesystem
  4. tmp.write(bytes); tmp.flush(); fsync(tmp)
  5. atomicRename(tmp, path)      // java.nio.file.Files.move(ATOMIC_MOVE, REPLACE_EXISTING)
  6. fsync(directory)
  7. upsert VaultFile(path, sha, size, now, revision+1)
```

**Read discipline**
```
readVaultFile(path):
  1. bytes = path.read()
  2. expected = VaultFile(path).sha256
  3. if sha256(bytes) != expected:
       a. try to reconstruct from the DB projection (body + properties) → rewrite atomically
       b. if that fails: keep the corrupt file as path.corrupt-<ts>, keep the DB copy,
          and surface a clear, non-alarming message in the app:
          "One file couldn't be read. It's been kept, not deleted. [View] [Restore from app data]"
       c. NEVER delete the only copy of anything.
```

**Revisions**: every thread edit writes a `thread_revisions` row (keep the last 50 per thread) before overwriting, so any version is restorable in-app. Never use git, never require a terminal.

**The never-implied-loss rule:** an empty index, a failed scan or a lagging FTS index must never render as "your files are missing". Show "Index not ready — showing files directly" instead.

---

## 11 · The AI layer (Needle 3)

### 11.1 M0 spike — go/no-go, before anything depends on it

Deliverable: a tiny Compose screen in the app that loads the engine and runs a bench.

1. Fetch the Needle 3 Android engine: `libneedle.a` (android-arm64), `needle.h`, and the `needle3.cact` model (~29 MB) from the official Cactus distribution. Bundle the engine via `externalNativeBuild` (CMake) in `app/src/main/cpp/`.
2. Write the JNI shim exposing: `load(modelPath)`, `init()`, `complete(prompt, toolsJson)`, `embed(text)`, `reset()`, `telemetryDisabled()`.
3. **Verify telemetry can be disabled.** If the Android engine cannot be configured to send nothing, **the spike fails** — fall back to T5 and do not ship the model.
4. Verify the model SHA-256 against the official value before use; store it under `filesDir/models/` after verification.
5. **Bench on the owner's own phrases** (~50): "in 3 days book lab slot", "remind me before physics", "I finished logarithms", "read 2 chapters", "no class tomorrow", etc., against a 10-tool schema. Record accuracy, cold-start time, per-call latency, battery drain.
6. **Go/no-go**: ship Needle if accuracy ≥ the threshold the owner agrees; otherwise the fallback parser ships and Needle is deferred.

**Constraints**
- **arm64-v8a only.** The 32-bit ARM archive fails to link on current NDK — do not attempt it.
- NDK 27.x, CMake, `abiFilters "arm64-v8a"`.
- The model may be downloaded on first run (with a visible, honest progress state) or bundled in assets — your call; bundled is simpler and fully offline. Whichever you choose, it must work with the device in airplane mode.

### 11.2 Tools exposed to the model

Each tool: name, JSON schema, argument validation, permission check, side-effect class, and an undo.

```
createTask(text, dueDate?, recurrence?, projectRef?)
rescheduleTask(taskId, newDate)
completeTask(taskId)
addInboxItem(text, kind)
createIdea(title, whyICare?)
createExperience(text, date?)
startTrack(trackId)
endTrack(trackId, count?)
startFocus(profile, minutes, pausedPackages[])
scheduleReminder(title, at, repeat?)
```

### 11.3 Confidence policy

The model returns a calibrated confidence. Three tiers:

| Confidence | Action |
|---|---|
| High + low-risk (read-only, additive, reversible) | Execute immediately; log to `rule`-style audit with the raw output stored |
| Medium | Show an inline confirm chip: "Book lab slot on Thu 6 Nov?  [Yes] [Edit]" |
| Low, or irreversible (delete, reschedule an existing commitment) | Refuse and explain why, offering the deterministic path instead |

**Hard Kotlin guards sit behind every tool regardless of confidence**: date parsing is validated, ranges are clamped, and nothing the model says can violate §1.

### 11.4 Fallback parser (T5) — ships in the same release

Pure Kotlin, no model. Handles: "in 3 days", "tomorrow", "next Tuesday", "every monday", "after physics", "20 min", "read 2 chapters", "remind me to X at 18:00". Extracts intent + entities, then routes to the same use cases. The UI must not reveal which engine answered — the user asked for a working app, not a benchmark.

---

## 12 · Permissions & the capability manifest

Eight capabilities. Each is a row in the `capabilities` table, and the manifest screen is **generated from that table**, so the card can never drift from what the app actually does.

| Key | Android permission | What BU sees | Never | Required |
|---|---|---|---|---|
| `USAGE_ACCESS` | `PACKAGE_USAGE_STATS` (special access) | foreground package + timestamps | page content, keystrokes | yes |
| `NOTIFICATIONS` | `BIND_NOTIFICATION_LISTENER_SERVICE` | package, category, timestamp, dismissed? | message text | yes |
| `EXACT_ALARM` | `SCHEDULE_EXACT_ALARM` | nothing | — | yes |
| `ACCESSIBILITY` | `BIND_ACCESSIBILITY_SERVICE` | which app opened | typed content | yes (soft pause + auto track start) |
| `BATTERY_EXEMPTION` | `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS` | nothing | — | yes |
| `LOCAL_VPN` | `INTERNET` + `BIND_VPN_SERVICE` | nothing (traffic blocked on-device) | traffic never leaves the device | **later** (D4) |
| `CAMERA_MIC` | `CAMERA`, `RECORD_AUDIO` | only what the user points at | anything else | optional |
| `ENCRYPTION` | none (a choice, not a permission) | nothing | — | optional, asked at onboarding |

**Manifest copy rules**
- Each card shows: name · one-line purpose · status · **Sees** · **Never** · a real mono JSON sample of one event.
- The JSON sample is generated from the actual event model so it is never aspirational.
- "Grant the remaining N" is offered, and every individual grant is revocable in-app.
- The Local-VPN card must explicitly say the network permission looks scarier than it is and that no data is ever transmitted.

---

## 13 · Rules engine

A rule is: **trigger → optional condition → action**, stored as JSON and rendered as a human-readable card.

Triggers: `APP_OPENED`, `TIME`, `EVENT`, `SESSION_ENDED`, `DAY_ROLLED`.
Actions: `START_TRACK`, `END_TRACK`, `ADD_STAMP`, `CREATE_TASK`, `SCHEDULE_REMINDER`, `SHOW_SUGGESTION`, `SET_FOCUS_PROFILE`.

- Rules are evaluated by a single coroutine in `RuleEngine`, serially, on the app's process. No background work beyond what's needed.
- Every evaluation is logged (`RULE_FIRED` / `RULE_BLOCKED`) with a reason.
- **No rule may ever write prose into a thread.** `ADD_STAMP` may add an *observed* stamp; a suggestion is offered, never applied.
- The Rules screen lists every rule in plain language with its run count and last result, plus a "test this rule" dry run.

---

## 14 · Reliability, backup, security

- Room + WAL; all multi-table writes transactional.
- Atomic, checksummed file writes (§10.3). Crash drills: kill the process mid-write and verify recovery (automated test).
- Soft delete → `trash` table or `archived` flag → 30-day purge task.
- Export: full vault as a folder or zip; single thread as `.md` or `.pdf`; raw events as JSONL. All offline, all plain text.
- Weekly export reminder: offered once, after the first week of use. Not a nag.
- Optional encryption (D7): Tink AES-GCM per-file keys wrapped by the Android Keystore; the DB via SQLCipher. Losing the key loses the data — state that plainly in the manifest.
- `android:allowBackup="false"` by default; if enabled, exclude the vault and DB from cloud backup and say so in-app.
- No cleartext traffic. No `INTERNET` permission in the first release (D4 keeps the VPN out of v1).

---

## 15 · Performance budget (A54, 120Hz OLED)

| Metric | Budget |
|---|---|
| Cold start to interactive | < 900 ms |
| Frame time while scrolling the Dial | ≤ 16 ms (no recomposition of the arc per frame) |
| Daily battery cost of the always-on service | ≤ 3% |
| Usage capture battery cost | ≤ 0.5% (incremental queries only) |
| Vault write | ≤ 50 ms for a typical thread, on the IO dispatcher |
| Memory | ≤ 120 MB RSS with a full day of events |

**Rules**
- The Dial arc is drawn on a `Canvas` with a cached path; only the changed fraction is recomputed.
- Digits update once per second, and only while a track is running and the screen is on.
- The foreground service updates its notification at 1 Hz while a track runs, 1/60 Hz otherwise.
- No `while(true)` polling loops anywhere. Use `WorkManager`, `AlarmManager`, `Flow` and event callbacks.
- Query Room off the main thread; observe with `Flow`.
- Budget the blur: one Haze layer per screen, no nesting.

---

## 16 · Testing strategy

**Unit (JVM) — required for**
- Domain use cases: rollover, recurrence expansion, daily aggregation, the §6.4 estimate math, the marker fraction math, stalled-project detection, debrief filing.
- The fallback NL parser: a table-driven test with ≥ 40 phrases (the owner's real sentences).
- Day-boundary logic across midnight and across the 04:00 boundary.

**Instrumentation — required for**
- Room migrations from every shipped schema version.
- Vault atomic write + checksum corruption + recovery (kill mid-write).
- Export → re-import round trip preserving every thread, link and property.

**Manual acceptance script (must pass before a milestone is "done")**
1. Grant all capabilities → the manifest shows 8/8 → the app proceeds.
2. Run the wizard → six tracks appear with the right modes → Physics is TOTAL/90 min, Sleep and Free time are ALWAYS.
3. Start Physics → the Dial counts up; the NOW strip and the notification show the same elapsed time.
4. Switch to Reading → the counter shows 0/5; +1 → 1/5.
5. Write in a thread → serif prose; insert a fact → it appears as a labelled stamp with a cyan edge.
6. Link two threads → the backlink appears on the other; the graph shows the edge.
7. Capture from another app via the share sheet → it lands in the Inbox with a mono timestamp.
8. "in 3 days book lab slot" → a task with a date, or a confirm chip.
9. Open a blocked app during a focus session → the soft-pause interstitial appears.
10. Kill the app mid-write → relaunch → nothing is lost; the file is intact or clearly reported.
11. Export the vault → open a thread file in any text editor → it is readable markdown with frontmatter.
12. Aircraft mode → everything above still works, including NL capture (model or fallback).

---

## 17 · Build system & CI

### 17.1 Versions

- JDK 17, Gradle 8.11+, AGP 8.7+, Kotlin 2.x (K2), KSP matching the Kotlin version.
- `compileSdk 35`, `targetSdk 35`, `minSdk 31`.
- `ndkVersion` = 27.x, `cmake` 3.22+.
- `abiFilters += "arm64-v8a"`.

### 17.2 `gradle.properties`

```properties
org.gradle.jvmargs=-Xmx3072m -Dfile.encoding=UTF-8
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configuration-cache=true
android.useAndroidX=true
android.nonTransitiveRClass=true
kotlin.code.style=official
```

### 17.3 `app/build.gradle.kts` (key parts)

```kotlin
android {
    namespace = "app.bu.life"
    compileSdk = 35
    defaultConfig {
        applicationId = "app.bu.life"
        minSdk = 31
        targetSdk = 35
        versionCode = (System.getenv("GITHUB_RUN_NUMBER")?.toIntOrNull() ?: 1)
        versionName = "0.${System.getenv("GITHUB_RUN_NUMBER") ?: "1"}"
        ndk { abiFilters += "arm64-v8a" }
        externalNativeBuild { cmake { cppFlags += "-std=c++20" } }
    }
    externalNativeBuild { cmake { path = file("src/main/cpp/CMakeLists.txt"); version = "3.22.1" } }
    signingConfigs { create("release") { /* from env vars, see 17.5 */ } }
    buildTypes {
        release {
            isMinifyEnabled = false          // T9 — keep off while JNI is in play
            signingConfig = signingConfigs.getByName("release")
        }
        debug { applicationIdSuffix = ".debug" }
    }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { compose = true; buildConfig = true }
    packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}
```

### 17.4 `settings.gradle.kts`

```kotlin
pluginManagement {
    repositories { google(); mavenCentral(); gradlePluginPortal() }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories { google(); mavenCentral() }
}
rootProject.name = "BU"
include(":app")
```

### 17.5 GitHub Actions — publish APK artifacts

Create `.github/workflows/android.yml`:

```yaml
name: Android CI
on:
  push:
    branches: [main]
  pull_request:
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with: { distribution: temurin, java-version: '17' }
      - uses: gradle/actions/setup-gradle@v4
      - name: Accept Android licenses
        run: yes | sdkmanager --licenses > /dev/null
      - name: Install SDK
        run: sdkmanager "platforms;android-35" "build-tools;35.0.0" "platform-tools" "ndk;27.0.12077973" "cmake;3.22.1"
      - name: Unit tests
        run: ./gradlew testDebugUnitTest --stacktrace
      - name: Lint
        run: ./gradlew lintDebug
      - name: Assemble debug
        run: ./gradlew assembleDebug
      - name: Assemble release (signed)
        run: ./gradlew assembleRelease
        env:
          BU_KEYSTORE_B64: ${{ secrets.BU_KEYSTORE_B64 }}
          BU_KEYSTORE_PASSWORD: ${{ secrets.BU_KEYSTORE_PASSWORD }}
          BU_KEY_ALIAS: ${{ secrets.BU_KEY_ALIAS }}
          BU_KEY_PASSWORD: ${{ secrets.BU_KEY_PASSWORD }}
      - name: Rename artifacts
        run: |
          mkdir -p dist
          for f in app/build/outputs/apk/debug/*.apk; do [ -e "$f" ] && cp "$f" "dist/bu-${{ github.run_number }}-debug.apk"; done
          for f in app/build/outputs/apk/release/*.apk; do [ -e "$f" ] && cp "$f" "dist/bu-${{ github.run_number }}-release.apk"; done
      - uses: actions/upload-artifact@v4
        with:
          name: bu-apk-${{ github.run_number }}
          path: dist/*.apk
          retention-days: 30
```

**Signing secrets (set once in repo Settings → Secrets):**
`BU_KEYSTORE_B64` (base64 of the .jks), `BU_KEYSTORE_PASSWORD`, `BU_KEY_ALIAS`, `BU_KEY_PASSWORD`.

In `build.gradle.kts`, decode the keystore from `BU_KEYSTORE_B64` into a temp file when the env var is present; otherwise fall back to the debug signing config so CI never fails on a missing secret:

```kotlin
val ksB64 = System.getenv("BU_KEYSTORE_B64")
signingConfigs {
    create("release") {
        if (!ksB64.isNullOrBlank()) {
            val f = File(System.getProperty("java.io.tmpdir"), "bu-release.jks")
            f.writeBytes(java.util.Base64.getDecoder().decode(ksB64))
            storeFile = f
            storePassword = System.getenv("BU_KEYSTORE_PASSWORD")
            keyAlias = System.getenv("BU_KEY_ALIAS")
            keyPassword = System.getenv("BU_KEY_PASSWORD")
        }
    }
}
```
…and in `buildTypes.release`, use `signingConfig = if (!ksB64.isNullOrBlank()) signingConfigs.getByName("release") else signingConfigs.getByName("debug")`.

**The owner's download path:** push to `main` → open the repo's **Actions** tab → the run → **Artifacts** → download `bu-apk-<run number>` → unzip → install `bu-<run number>-release.apk` on the A54.

**Notes**
- `versionCode`/`versionName` come from `GITHUB_RUN_NUMBER` so every build is uniquely installable and upgradable.
- Keep `minifyEnabled = false` (T9). If you enable R8 later, add keep rules for the JNI bridge classes and `org.commonmark`.
- The CI job must **fail** on unit-test or lint errors. That is the quality gate.

---

## 18 · Milestones

Each milestone ends with a runnable APK on the A54 and a green CI run.

| M | Name | Contents | Exit criterion |
|---|---|---|---|
| **M0** | Spike | Project scaffold, theme + tokens, the Dial component (static), Needle JNI + telemetry check, bench report | §11.1 go/no-go recorded in `PROGRESS.md`; APK installs and shows the Dial |
| **M1** | The day | Track CRUD, the Dial live, NOW strip, foreground service + notification, chip rail, day start hour | Start/stop a track; the dial, strip and notification agree; survives screen-off |
| **M2** | Journal & capture | Threads, markdown vault (atomic + checksums), property block, wikilinks + backlinks, editor, capture sheet + share sheet + Inbox, ritual reminder, revision history | Write, link, insert a fact, export; crash drill passes (§16) |
| **M3** | Automation | Rules engine + rule cards, NL capture (Needle or fallback), focus sessions, soft pause, notification policy, debrief, widget | The owner's acceptance script items 3–9 pass |
| **M4** | Observation | UsageStats incremental capture, notification metadata, exclusions, sleep estimate, activity timeline, graph view, quick-settings tile | A real day of events matches reality; excluded apps produce zero rows |
| **M5** | Reliability | Checksums + repair, trash, export/import round trip, optional encryption, backup prompt, CI polish | The kill-mid-write test and the export round trip pass |
| **M6** | Post-exam | Study system (subjects → topics → practice → recall), weekly review, PDF export | The Diva "must-have" list is complete |

**M0 → M5 is v1.** Nothing in M6 may delay M1. M1 must be started before anything else is designed further.

---

## 19 · Repo layout & conventions

```
BU/
├── BUILD-GUIDE.md          ← this file (the contract)
├── README.md               ← what BU is, how to build, how to install the artifact
├── PROGRESS.md             ← living status, updated after every slice
├── DESIGN-NOTES.md         ← any new component or pattern you invent
├── .github/workflows/android.yml
├── gradle/libs.versions.toml
├── app/
│   ├── build.gradle.kts
│   └── src/main/{java/app/bu/life/…, cpp/, res/}
├── docs/                   ← the two design documents (reference, not authority)
└── mockup/                 ← the v2 HTML mockup (visual reference)
```

**Conventions**
- Kotlin official style; `ktlint` if available, but correctness first.
- Package names match the §7.1 tree exactly.
- One public class per file, named for its primary abstraction.
- No `!!`. No `GlobalScope`. No `Thread.sleep`. No blocking I/O on the main thread.
- `StateFlow` for state; `SharedFlow` for events. No mutable global state.
- Comments explain **why**, not what. Every non-obvious decision gets a `// Why:` comment.
- Commit format: `feat(scope): summary` · `fix(scope): summary` · `chore: summary`.
- Never commit secrets, keystores, `local.properties`, or build outputs.

---

## 20 · Agent operating rules

1. **Read §1 and §2 first.** They are not negotiable and are not style suggestions.
2. **Work in vertical slices**; land a slice end-to-end before starting the next.
3. **Build after every change.** `./gradlew assembleDebug` must be green. A slice that doesn't compile is not a slice.
4. **Commit after every slice**, and update `PROGRESS.md` in the same commit.
5. **Never invent an API.** If a library or permission doesn't exist, verify it before using it. The Needle 3 integration is the highest-risk area — prove it in M0 before building on it.
6. **Prefer boring, proven APIs** over clever ones (§1.11).
7. **No network calls, ever**, and no `INTERNET` permission in v1.
8. **Ask before adding dependencies.** Adding a dependency is an architectural decision; prefer the platform.
9. **Design freedom:** you may invent additional screens, micro-interactions, gestures and visual details, and you are encouraged to make the app excellent — as long as §1 holds and §5's tokens are respected. Record what you invent in `DESIGN-NOTES.md`.
10. **If the spec and reality conflict** (e.g. a permission is unavailable on the A54, or the model won't load), **do not fake it**. Implement the graceful path (§11.4, §16), and record the conflict and the decision in `PROGRESS.md`.
11. **Test the domain.** The `domain` package must have JVM unit tests for every use case. UI polish is secondary to correctness of the model.
12. **Keep the APK small.** The model (~29 MB) is the largest asset; everything else should be lean.

---

## 21 · Definition of Done (per slice)

- [ ] Feature works on the A54, demonstrated (not assumed).
- [ ] `./gradlew assembleDebug` and `./gradlew testDebugUnitTest` are green.
- [ ] `./gradlew lintDebug` reports no errors.
- [ ] Unit tests cover the new domain logic.
- [ ] No new warnings that indicate real problems.
- [ ] §1 not violated — self-check the relevant items explicitly.
- [ ] Empty, loading, error and offline states all handled.
- [ ] Accessibility: content descriptions, 48dp touch targets, no colour-only signalling.
- [ ] `PROGRESS.md` updated; commit made.
- [ ] If a new component or pattern was introduced, `DESIGN-NOTES.md` updated.

---

## 22 · Risk register

| Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|
| Needle 3 Android engine won't integrate (JNI/toolchain/telemetry) | medium | high | M0 spike with an explicit go/no-go; the fallback parser ships regardless (§11.4) |
| Exact alarms / special access refused or flaky on the A54 | medium | medium | graceful degradation to inexact alarms; show the permission status in-app |
| Accessibility service rejected by the user or unstable across app updates | medium | medium | soft pause is optional; auto-track-start degrades to a manual chip tap |
| Notification listener captures more than intended | low | high | metadata-only by construction; exclusion list applied at the capture boundary |
| Battery cost of the always-on service | medium | medium | 1 Hz notification updates only while a track runs; per-track disable |
| Export/import corrupts the user's existing vault | low | critical | the old vault is **read-only** until the owner explicitly imports; import is additive and reversible; checksums on every file |
| Scope creep from M6 before the exam | high | high | M0–M5 is v1; M6 may not delay M1 (§18) |

---

## 23 · First four tasks for the agent

1. **Scaffold** the project per §7 and §17: Gradle files, `libs.versions.toml`, manifest, `AppContainer`, theme tokens from §5, and a single Compose screen showing the Dial rendering the §6 geometry with fake data. Commit: `feat(ui): the Dial`.
2. **M0 spike:** the Needle 3 JNI bridge, telemetry verification, and the bench harness. Write the go/no-go into `PROGRESS.md`. Commit: `feat(ai): needle 3 jni spike`.
3. **Track domain:** the `Track`/`TrackSession` model, Room entities + DAOs, the §6.4 estimate math with unit tests, and the chip rail + live Dial driven by real data. Commit: `feat(domain): tracks and sessions`.
4. **Foreground service:** the always-on service + the Dial notification + the NOW strip, all reading one source of truth so the three surfaces can never disagree. Commit: `feat(service): always-on dial`.

---

*BU — a day system that runs itself. Build it carefully; the owner will use it every day for years.*
