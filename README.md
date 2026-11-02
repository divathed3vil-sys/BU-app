# BU

> one loop. your day, closed every night.

**BU** is a single-user, offline-first Android life system — built for one person
(Dhivakaran), on one device first (Galaxy A54). No cloud. No accounts. No pre-made
tracks. Your words never leave the device.

## The shape — two planes, one heart

```
┌──────────────────────────────────────────────────────────────┐
│                          BU (one app)                         │
│  ┌──────────── IDENTITY PLANE ─────────┐  ┌─── GUARDIAN ───┐ │
│  │ capture · plan · journal · threads  │  │  detector      │ │
│  │ rituals · reminders · constellation │◄─┤  restrictor    │ │
│  │ momentum · needle 3 (on-device AI)  │  │  defender      │ │
│  └──────────────────┬──────────────────┘  │  observer      │ │
│            ┌────────▼─────────────────────▼──┐             │
│            │   ONE DATA CORE (Room/SQLite)   │             │
│            └─────────────────────────────────┘             │
└──────────────────────────────────────────────────────────────┘
```

- **Identity plane** — the user's world. Plans, tracks, journal, graph, AI. Writes freely.
- **Guardian plane** — the shield. Detection, blocking, observation. Reads config, writes evidence.
- **One data core** — a single efficient SQLite file. 10 years < 50 MB.

## The loop

`CAPTURE → PLAN → DO → CLOSE → GROW`

- **Track** — user-created only. BU ships zero tracks, ever.
- **Big Three** — at most three things own a day.
- **Carry** — cooling carry-over, never a red shame.
- **Momentum** — a 7-day rolling ratio. Streakless. Unbreakable.
- **Dusk Close** — 90 seconds, evidence pre-filled, tomorrow drafted.
- **Constellation** — your thoughts, grown into a graph.

## Modules

| module | owns |
|---|---|
| `:app` | screens, navigation, DI wiring |
| `:core` | models, data core, time, settings |
| `:designsystem` | near-black/mint theme, motion doctrine, haptic grammar |
| `:identity` | plan, capture, journal, threads, graph, rituals, reminders, needle |
| `:guardian` | all native services + enforcement + observation |

Stack: Kotlin 2 · Compose M3 · Hilt · Room · minSdk 29 / target 35 · dark-only.

## Doctrine (non-negotiable)

1. Near-black base. Slight gray layering. Slight mint. **No red, ever.**
2. Every press answers in <100 ms. Springs, not durations.
3. Haptics are a grammar: tap · tick · lock · win · warn.
4. The user's own words are serif and untouchable. AI drafts; the user decides.
5. Nothing pre-made. Setup builds *your* system from zero.

## Build

```bash
./gradlew :app:assembleDebug
```

CI (`.github/workflows/ci.yml`) builds a debug APK on every push to `main`
and uploads it as the `BU-debug-apk` artifact.

## Phase plan

| phase | ships | artifact |
|---|---|---|
| **P0** ✅ | repo+CI, 5 modules, design system, onboarding + setup wizard, settings | debug APK |
| **P1** | tracks, Day Arc, Big Three, blocks, sparks, plan-tomorrow | + unit tests |
| **P2** | Dusk Close, Dawn Push, Sunday Review, reminder engine, exam countdown | + tests |
| **P3** | journal, threads, capture inbox, Constellation v1, momentum | + tests |
| **P4** | guardian I: detection, app rules, overlay, emergency, focus | + tests |
| **P5** | guardian II: batching, shorts budget, host block, widgets, VPN | + tests |
| **P6** | needle 3: capture parse, evidence pre-fill, recaps, patterns | + tests |
| **P7** | hardening: device admin, invincible, watchdog, backup, perf | release-candidate |
