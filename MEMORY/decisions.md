# MEMORY · Decisions

> Every architectural decision, with rationale and date. Append-only. Newest at top.

---

## 2026-11-04 · License: GPL-3.0

**Decision:** BU is licensed under GPL-3.0.

**Rationale:** The owner wants BU to be "my own invention" and defensible. GPL-3.0 is copyleft — anyone can read, learn, and contribute, but no one can fork it closed-source. This protects the never-before status while keeping the project honest and open. MIT/Apache would allow a competitor to fork and close. GPL-3.0 prevents that.

**Alternatives considered:**
- MIT — too permissive, allows closed-source forks.
- Apache 2.0 — permissive + patent grant, but still allows closed-source forks.
- AGPL-3.0 — copyleft + network clause, but BU has no server component so AGPL adds nothing.

**Status:** Locked. To change, would require explicit owner decision.

---

## 2026-11-04 · Two-tier product (Lite + Full)

**Decision:** BU ships in two tiers. Lite (no model, no Watcher) and Full (opt-in model download → Watcher activates). Both free, both local, both private.

**Rationale:**
1. **Friction** — a 529MB silent download on first open is hostile. The opt-in moment makes it deliberate.
2. **Consent** — the Watcher is a system-level service that sees everything the user does. The user must choose it.
3. **Honesty** — if the user doesn't care enough to opt in, they get nothing — which is exactly the discipline pressure the app is built to apply.

**Status:** Locked.

---

## 2026-11-04 · Architecture: 5 layers (Flame / Ember / Watcher / Forge / Bedrock)

**Decision:** Replace v4's BTK 5-plane kernel (Intent / Authority / Evidence / Projection / Platform) with 5 layers named after fire: Flame (UI), Ember (state + memory), Watcher (Android APIs), Forge (intelligence), Bedrock (invariants).

**Rationale:** The v4 planes were correct systems-thinking but wrong product-thinking — the user never feels planes, they feel one organism. The fire layers are both architecturally sound (clear separation of concerns) and metaphorically coherent (every layer name evokes the fire).

**Status:** Locked.

---

## 2026-11-04 · 3 invariants, not 12

**Decision:** Compress v4's 12 invariants (I1-I12) to 3: (1) every tap survives process death, (2) the fire is honest (evidence grades preserved), (3) you own your embers (Markdown export anytime).

**Rationale:** 12 commandments is a creed, not a checklist. The 3 invariants capture the spirit of all 12 — durability, honesty, ownership — and are enforceable at compile + test time. The other 9 are implied by these.

**Status:** Locked.

---

## 2026-11-04 · Model stack: Gemma 3 1B + Qwen 0.6B + ObjectBox 4.0

**Decision:**
- **Gemma 3 1B (int4, 529MB)** as the router — intent classification, daily Play choice, journal summarization, promise extraction. Runs on MediaPipe LLM Inference API.
- **Qwen 2.5 0.6B (quantized)** for structured JSON tool calls.
- **Gemini Nano** as fast path if present (Pixel 8 Pro, S24+, 2026 flagships). Detected at runtime. Not required.
- **ObjectBox 4.0** with HNSW vector index for memory. Not SQLite + brute-force cosine — that chokes past 10k entries.

**Rationale:** Confirmed via research (Google AI Edge blog March 2025; ObjectBox docs). Gemma 3 1B is the right size for mid-tier Android (Galaxy A54, 6GB RAM). ObjectBox is the first on-device vector DB for Android with millisecond HNSW search. SQLite + brute-force was the v4 plan and is wrong for the scale v6 needs.

**Status:** Locked. To swap models, requires a spike with latency + RSS + accuracy benchmarks.

---

## 2026-11-04 · The Fire replaces the Bloom

**Decision:** Kill the v5 garden metaphor (Bloom, Garden, Soil, Keeper, Voice). Replace with the Fire as the central metaphor. The fire is the literal functional state of the app — lit, the app is sharp; dead, the app goes dark.

**Rationale:** The garden tolerated neglect (gardens do). The fire demands fuel or it dies (fires do). The fire is the discipline pressure no ring or streak can match. The garden was a skin; the fire is the soul.

**Status:** Locked.

---

## 2026-11-04 · Build plan: 4 phases + ongoing, ~1 year

**Decision:** Replace v4's 8-phase plan with 4 phases + ongoing:
- **P0 Spark** (4 weeks) — BU Lite: planning + journaling (write only) + manual fire.
- **P1 Ember** (6 weeks) — BU Full opt-in: Watcher v1, Forge v1, Life Log auto-capture.
- **P2 Flame** (8 weeks) — Intervention ladder, Hard Focus, voice journaling, photo proof, manual Life Log.
- **P3 Forge** (12 weeks) — Smart context (Accessibility + Gemma classification), free-draw canvas, causal chains, prediction, Gorgeous state + first growth animation milestones.
- **P4 Constellation** (ongoing) — Multi-year growth animation, desktop reader, backup/restore, the long arc.

**Rationale:** v4's 8 phases is why nothing shipped. 4 phases + ongoing, each ending with a thing the owner actually uses, is the path to shipping.

**Status:** Locked. Order is fixed. No phase starts until the previous one is approved by the owner.

---

## 2026-11-04 · Aesthetic: quiet luxury + Apple + Material 3 + fire

**Decision:**
- **Palette:** warm cream paper (#FAF6EE), deep ink (#0A0A0A), single signal accent (deep amber #C25A1F). Secondary: ember orange (#E8893A), warm umber (#8B5A2B). No pure white, no pure black except deepest ink. No gradients except the fire itself.
- **Type:** SF Pro Display for headings (tight tracking, confident weight). Inter for body. JetBrains Mono for numbers/timestamps.
- **Surfaces:** Material 3 tonal layers, rounded corners (16-24px), soft elevation.
- **Motion:** playful, springy, jumpy — but never decorative. Fire ring bounces open. Play snaps in with overshoot. Fire glow pulses 4-second breathe. Reduced-motion fully honored.
- **The fire:** bloomy, gorgeous. Procedural (particle system + parametric flame tongues + milestone-driven state changes). Never hand-animated.

**Rationale:** Owner specified: "apple style but in material 3 design and playful jumpy animations." Combined with the fire as the visual hero. Quiet luxury restraint (one accent, no decoration, lots of negative space) prevents the app from feeling busy.

**Status:** Locked.

---

## Open decisions (to resolve during P0)

- ObjectBox vs Room for the Ember layer (recommendation: ObjectBox for HNSW, but Room is more familiar).
- Compose Multiplatform vs pure Jetpack Compose (recommendation: pure Compose for v1, KMP later).
- Min SDK (recommendation: 29 / Android 10).
- Web prototype first vs straight to Android (recommendation: straight to Android — web can't simulate the Watcher meaningfully).
- Android Studio version + Kotlin plugin version pin.
