# MEMORY · Context

> **If you are an LLM starting a session on this repo, read this file first.**
> It tells you what BU is, where we are, and how to help effectively.

---

## What BU is

BU is a discipline-first life operating system for one user (Dhivakaran, on a Galaxy A54). The central metaphor is a **fire** — not decorative, not gamified, but the literal functional state of the app. When the fire is lit, BU is sharp, present, watching with you. When the fire is dead, BU goes dark and silent.

The product ships in two tiers:
- **BU Lite** — plan/execute/focus/journal, no AI, no Watcher. Works immediately.
- **BU Full** — user opts in → app downloads Gemma 3 1B (529MB) locally → the Watcher activates → the Forge intelligence comes alive.

Everything stays on device. No cloud. No account. No data leaves the phone. GPL-3.0 licensed.

## Where we are right now

**v6 vision is locked. P0 Spark is about to begin.**

- The vision document is `research/v6-fire/BU_v6_The_Fire.pdf` (28 pages).
- The locked plan is `PLAN.md`.
- The progress log is `PROGRESS.md`.
- No code has been written for v6 yet. The Android project will be initialized on the owner's local machine in the next session.

## The five layers (memorize these)

1. **Flame** — UI (Jetpack Compose). The fire ring, the Play, journaling surfaces, the Life Log browser.
2. **Ember** — ObjectBox 4.0 with HNSW vector index. Every action is an ember. Single source of truth.
3. **Watcher** — Android APIs (UsageStats, FGS, NotificationListener, Accessibility opt-in).
4. **Forge** — Gemma 3 1B + Qwen 0.6B + RAG over embers. Computes fire state, daily Play, patterns, predictions.
5. **Bedrock** — 3 invariants: tap survives · fire honest · you own your embers.

## The vocabulary (full glossary in glossary.md)

- **Fire** — the central metaphor. Five states: Blazing / Burning / Flickering / Embers / Out. Plus Gorgeous (30 honest days).
- **Play** — the one-sentence action that matters today. Lite: you write it. Full: Forge computes it.
- **Watcher** — the system-level observer. Foreground service + UsageStats + (opt-in) Accessibility.
- **Forge** — the on-device intelligence layer. Gemma 3 1B + Qwen 0.6B + ObjectBox RAG.
- **Ember** — any action/event/entry stored in the ObjectBox database with an embedding.
- **Lite / Full** — the two tiers. The tier boundary is the model download.
- **Life Log** — the complete record of the user's day. Auto (Watcher) + Manual (user-entered off-phone time).
- **Gorgeous** — the fire state above Blazing. Earned only through 30 honest days. Photo-proof gated.

## How to help effectively

1. **Always read PROGRESS.md first** — know what was just done before suggesting anything.
2. **Always read decisions.md** — know what's been decided before re-opening a question.
3. **Check lessons-learned.md** — know what failed before suggesting it again.
4. **Append to session-log.md** at the end of every session — what was worked on, what was decided, what's next.
5. **Never suggest features outside the v6 plan** without explicitly flagging it as a plan amendment.
6. **The owner is a student, not a knowledge worker.** Suggestions must fit a student's life, not a manager's.
7. **The owner is learning as we go.** Prefer Kotlin/Compose patterns that are well-documented over clever ones.
8. **2 hours a day.** Don't propose anything that requires a 6-hour sprint.
9. **Respect the invariants.** Tap survives. Fire honest. You own your embers. Never compromise these for a feature.
10. **The fire is the discipline.** Don't propose streaks, badges, or gamification — those are v4/v5 thinking.

## What was killed (don't suggest these)

- The garden metaphor (v5) — was a skin, not a soul.
- LOOM 5-tab IA (v4) — too many tabs for a student.
- BTK 5-plane kernel (v4) — over-engineered for one user.
- 12 invariants (v4) — compressed to 3.
- 30-table schema (v4) — compressed to ~10.
- 8-phase plan (v4) — compressed to 4 + ongoing.
- Adaptive Firmness Ladder L0-L5 (v4) — replaced by 3-stage intervention with smart context.
- Slip Score / Drift Index (v4) — the fire state replaces these.
- The Pebble / Arc-Dial / Tally Trio (v4) — killed as branded gestures.
- Mindful feature parity (v4) — out of v1 scope.

## What's preserved from v4 (don't re-decide these)

- Evidence grades (OBSERVED / INFERRED / USER_CONFIRMED / OUTCOME_CONFIRMED).
- Offline-first, no cloud, no account.
- Markdown export anytime.
- Reality Multiplier (silent, behind the Forge).
- Stoic + autonomy-supportive tone.

## The owner's working style

- Sends voice-notes-style free text with many ideas at once.
- Expects the LLM to synthesize, structure, and ask sharp questions.
- Willing to spend a year building this at 2 hours a day.
- Wants to learn and use AI throughout — public repo, MEMORY folder for LLM continuity.
- Will push from local PC after each session. The LLM in this sandbox does not write code directly to the repo; it advises and the owner implements locally.

## The current session's goal

Help the owner initialize P0 Spark (BU Lite, no AI) on their local machine. Plan the Android project scaffold, recommend code decisions, generate code as needed, and guide file placement. The owner will commit and push from their PC.
