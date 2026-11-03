# BU — Personal Life Operating System

**Local-first · offline · one user · Galaxy A54.** LOOM is the interface. The BU Temporal Kernel is the law. This repo is the single source of truth for research, plan, and code.

> **The plan is LOCKED** — see [`research/BU-v4-FINAL-PLAN-LOCKED.md`](research/BU-v4-FINAL-PLAN-LOCKED.md). All future work implements it; tweaks happen in the app, never in the plan.

## The one law

> AI may understand the request. The kernel decides what is valid. The ledger records what became true. The evidence system records why BU believes it. The projections make it useful.

## Architecture

```
:app            UI shells (LOOM: NOW·PLAN·JOURNAL·ATLAS·SHIELD), navigation, workers host
:designsystem   tokens, motion v3 springs, haptics, Arc, Arc-Dial, Pebble   (leaf)
:identity       BU Loop domain — pure Kotlin, zero Android imports
:guardian       Shield domain — pure Kotlin, zero Android imports
:core           BTK: command gateway · ledger (Room/SQLite) · events · outbox
```

- **BTK flow:** command → idempotency → validate → transactional commit (state + events + outbox) → receipt → effects applied at-least-once after commit.
- **Single-writer ledger:** SQLite owns all truth; Markdown bodies live in the ledger; FTS/embeddings/alarms/exports are rebuildable projections.
- **Needle 3** (P6) is an interpreter with ≤5-tool contextual menus — confidence is never authorization.

## Phases (owner tests an APK after each; CI artifact on every push)

| Phase | Status | Contents |
|---|---|---|
| **P0** Scaffolding & proof | ✅ owner-approved | 5 modules · Hilt · kernel round-trip · LOOM shell · Arc + Arc-Dial + Pebble |
| **P1** The Loop | ✅ owner-approved | ledger schema v1 · tracks/Big Three/Blocks/Sparks · tally · capture→alarm→tally round-trip · focus · floating nav |
| **P2** Rituals & reminders | pending owner test | Dusk Close contract · Dawn Push · auto-draft · moved-3 law · recommitment escalation · Care engine v3 |
| P3 Memory & Atlas | pending | Markdown threads · evidence grades · FTS5 · links/graph · export |
| P4 Guardian I | pending | UsageStats · Drift · Pull · budgets · L2 pause |
| P5 Guardian II | pending | Slip Score · ladder 0–3 · guards · Adapter · Voice |
| P6 Needle | pending | M0 telemetry-gate spike · extraction · embeddings · voice |
| P7 Hardening | pending | Sunday Review · Reality Multiplier · backup/restore · perf gates |

## Research library (`/research`)

- `BU-v4-FINAL-PLAN-LOCKED.md` — **the constitution**
- `BU_2_Product_Strategy_and_LOOM_Report.md` — product strategy & LOOM spec
- `BU_3_Final_Engineering_Architecture_Research.md` — BTK engineering research
- `BU-2.0-Research-Blueprint-OWNER-CLAUDE.md` — owner's original Claude research
- `BU-v2-research-part5/6/7` — zero-resistance UX · deep engineering · the blend
- `BU-v1-BUILD-GUIDE-archive.md` — v1 archive

## Build

```bash
./gradlew :app:assembleDebug        # APK at app/build/outputs/apk/debug/
./gradlew :core:testDebugUnitTest :identity:test :guardian:test
```

CI builds a debug APK on every push to `main` — download it from the run's **Artifacts**.
