# BU v6 — The Fire · LOCKED PLAN

**Date:** 4 November 2026
**Status:** LOCKED. All future work implements this document. Tweaks happen inside the app, never inside this plan.
**Supersedes:** v1 build guide, v2 parts 1–7, v3 LOOM/BTK spec, v4 locked plan, v5 the garden (killed).

The full v6 vision is in [`research/v6-fire/BU_v6_The_Fire.pdf`](./research/v6-fire/BU_v6_The_Fire.pdf). This file is the canonical reference for LLMs and contributors — short, scannable, complete.

---

## 1. The One Paragraph

BU is a discipline-first life OS. The central metaphor is a **fire** — the literal functional state of the app. Lit, the app is sharp, present, watching with you. Dead, the app goes dark and silent. Two tiers: **Lite** (plan/execute/focus/journal, no AI) and **Full** (opt-in model download → Watcher activates → Forge intelligence comes alive). Everything on-device. No cloud. No account. The fire is the discipline.

## 2. The Two Tiers

| | BU Lite | BU Full |
|---|---|---|
| Model | None | Gemma 3 1B (529MB int4) + Qwen 0.6B |
| Watcher | Off | On (FGS + UsageStats + NotificationListener + Accessibility opt-in) |
| Fire state | Manual (you mark done) | Honest (Forge computes from evidence) |
| The Play | You write it | Forge computes it |
| Life Log | Manual only | Auto phone activity + manual off-phone |
| Journaling | Write + Voice + Draw | Same three, but Forge reads them |
| Pattern detection | None | Yes (causal chains, predictions, identity streaks) |

The tier boundary is the model download. A deliberate commitment moment, not silent bloat.

## 3. The Five Fire States

| State | Visual | Meaning |
|---|---|---|
| BLAZING | Solid, bright, full | You are becoming who you said you'd be |
| BURNING | Steady, warm | You are holding course |
| FLICKERING | Slight wobble, dimmer | You are slipping, slowly |
| EMBERS | Dim, half-empty | You have stopped |
| OUT | Dark, thin | You are losing ground. Tap to relight. |
| GORGEOUS | Halo + gold + floating embers | 30 honest days (photo-proof gated). Then keeps growing. |

The fire grows over months and years — never ending, procedural, like Egg, Inc.

## 4. The Five Layers

1. **Flame** — UI (Jetpack Compose). The fire ring, the Play, journaling (write/voice/draw), the Life Log browser.
2. **Ember** — ObjectBox 4.0 with HNSW vector index. Every action is an ember. The single source of truth.
3. **Watcher** — Android APIs (UsageStats, FGS, NotificationListener, Accessibility opt-in). Reports to the Ember.
4. **Forge** — Gemma 3 1B (router) + Qwen 0.6B (JSON tools) + Gemini Nano (fast path if present). RAG over embers.
5. **Bedrock** — Three invariants enforced at compile + test time.

## 5. The Three Invariants

1. **Every tap survives process death.** Write-ahead-logged persistence. No data loss of a user-confirmed action. Ever.
2. **The fire is honest.** Evidence grades preserved on every claim: OBSERVED / INFERRED / USER_CONFIRMED / OUTCOME_CONFIRMED. A model guess never silently becomes a fact.
3. **You own your embers.** Full Markdown export anytime. No account. No cloud. No lock-in.

## 6. The Daily Cycle

- **Morning Spark (30 sec)** — Open BU. See the fire. Tap. Get the one-sentence Play. Commit. Close.
- **Tending (the day)** — The Watcher watches (Full) or doesn't (Lite). Drift triggers the intervention ladder (Full only). Tend the play → fire brightens.
- **Evening Close (60 sec)** — "Did you do it?" Three taps: yes / partial / no. One optional line of reflection. Fire updates. Sleep.
- **Sunday Review (5 min)** — The Forge speaks: "Here's what grew / withered / the pattern I see." You plant one intention for next week.

## 7. The Intervention Ladder (Full only, inside sealed Normal Focus)

| Time in drift | Action |
|---|---|
| 0–20 min | Soft nudge notification |
| 20–30 min | Aggressive nudge (notification + sound) |
| 30–60 min | Full-screen overlay + 30-min YouTube block |

**Smart context:** AccessibilityService reads the YouTube video title → Gemma 3 1B classifies educational vs not → if educational + user confirms → no punishment. (Hardest engineering piece in v1. Marked experimental.)

**Hard Focus mode:** Whitelist (calls + 2-3 apps). Everything else blocked for the session.

## 8. Journaling — Three Surfaces

- **Swipe right** — Blank page. Write whatever. Forge reads on-device, extracts promises, indexes.
- **Swipe left** — Voice. Hold to record, 30 sec max. Whisper-tiny transcribes on-device.
- **Swipe up** — Free-draw canvas (Excalidraw-style). Pen, shapes, arrows, text. Stored as SVG + embedding.

## 9. The Life Log

- **Auto (Full only)** — Watcher logs every app foreground event with timestamp + duration. Aggregates into a daily timeline.
- **Manual (both tiers)** — For off-phone time, user adds title + description + duration at evening close. Fills the gaps.
- **Complete record** — Auto phone activity + manual off-phone = your complete day, every day. Browsable by date, searchable by keyword or semantic query, exportable as Markdown.

## 10. The Build Plan — Four Phases, One Year

| Phase | Name | Duration | Contents | Exit criterion |
|---|---|---|---|---|
| **P0** | SPARK | 4 weeks | BU Lite: planning + journaling (write only) + manual fire tending. Compose UI, ObjectBox schema, fire ring (static state, no growth yet). | You plan tomorrow, write a play, journal, mark it done. Fire is dim but planning works. |
| **P1** | EMBER | 6 weeks | BU Full opt-in: model download flow, Watcher v1 (UsageStats + FGS, no Accessibility yet), Forge v1 (Fire state computation, daily Play choice via Gemma 3 1B). Life Log auto-capture. | You opt in. Model downloads. Watcher runs. Fire is honest. Play is computed. |
| **P2** | FLAME | 8 weeks | Intervention ladder (3-stage, no smart context yet). Hard Focus mode (whitelist). Voice journaling (Whisper-tiny). Photo proof v1 (MobileNet embedding, no anti-cheat yet). Manual off-phone Life Log entries. | Drift triggers ladder. Hard Focus blocks apps. Voice + photo proof work. Life Log fills both ways. |
| **P3** | FORGE | 12 weeks | AccessibilityService for YT title reading + Gemma educational classification (smart context). Free-draw canvas. Causal chain detection. Prediction surfacing. The Gorgeous state + first growth animation milestones. | Smart context works. Canvas works. Patterns surface. Fire can reach Gorgeous. Growth animation begins. |
| **P4** | CONSTELLATION | ongoing | Multi-year growth animation system. Year-arc data model. Performance hardening. Backup/restore. Desktop reader (read-only). The long arc. | The fire grows over years. New stages unlock. |

**Order is fixed.** No phase starts until the previous one is approved by the owner.

## 11. Aesthetic

- **Palette:** warm cream paper (#FAF6EE), deep ink (#0A0A0A), single signal accent (deep amber #C25A1F for the fire). Secondary: ember orange (#E8893A), warm umber (#8B5A2B).
- **Type:** SF Pro Display for headings (tight tracking, confident weight). Inter for body. JetBrains Mono for numbers.
- **Surfaces:** Material 3 tonal layers, rounded corners (16-24px), soft elevation.
- **Motion:** playful, springy, jumpy — but never decorative. The fire ring bounces open when tapped. The Play snaps in with overshoot. Fire glow pulses with 4-second breathe. Reduced-motion fully honored.
- **The fire:** bloomy, gorgeous. Procedural (particle system + parametric flame tongues + milestone-driven state changes). Never hand-animated.

## 12. Originality — The Moat

The defensible novelty is the combination, and the moat is the architecture decision itself:

1. Two-tier with model-download-as-commitment. No free app does this.
2. The fire as functional state, not decoration. Nobody does this because inverting engagement is bad business.
3. On-device AI at the system level. Gemma 3 1B + ObjectBox + RAG. The unit economics don't work for free apps.
4. Smart-context intervention. Accessibility reads YT title, Gemma classifies, user confirms. No blocker does this.
5. The Life Log (auto + manual). No app combines system-level auto-capture with user-authored manual entries.
6. Multi-year growth animation. Procedural fire, never-ending, tied to real behavioral milestones.
7. Evidence grades on every claim. Research-grade provenance in a consumer app.

## 13. The Stack

- **Android (production):** Kotlin 2.x + Jetpack Compose · Material 3 · ObjectBox 4.0 (HNSW) · MediaPipe LLM Inference API (Gemma 3 1B) · Hilt · WorkManager + Foreground Service · AccessibilityService · SQLCipher · minSdk 29 · targetSdk 35.
- **Web prototype (proof of concept, may skip):** Next.js 16 + TypeScript · Tailwind 4 + shadcn/ui · IndexedDB (simulated Watcher).

---

*— END OF LOCKED PLAN. Next artifact: P0 Spark build, starting with Android project initialization on the owner's local machine.*
