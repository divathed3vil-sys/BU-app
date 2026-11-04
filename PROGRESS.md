# PROGRESS

Running log of what's been done and what's next. Append-only. Newest at top.

---

## 2026-11-04 · v6 vision locked, repo restructured

### Done
- Killed v5 garden concept (was a skin, not a soul).
- Synthesized v6 "The Fire" concept from owner's research + 6 design questions.
- Wrote v6 vision document (28-page PDF, `research/v6-fire/BU_v6_The_Fire.pdf`).
- Restructured GitHub repo: wiped v4 code (preserved in git history), archived v1-v4 research to `research/archive/`, built new v6 structure (README, PLAN, PROGRESS, MEMORY/, docs/, src/ modules, .github/).
- Added GPL-3.0 LICENSE.
- Added `.gitignore` for Android/Kotlin.
- Added `.github/workflows/ci.yml` placeholder (will configure when Android project initializes).
- Added `.github/ISSUE_TEMPLATE/` (bug, experiment-idea, decision-needed, learned-this).
- Wrote `docs/privacy.md` v1 (the founding contract on what the Watcher sees).
- Wrote `docs/architecture.md` v1 (the five-layer architecture, public).

### Decisions locked
- License: GPL-3.0 (open source, copyleft, protects "my own invention" status).
- Two-tier product: Lite (no AI) + Full (opt-in model download).
- Architecture: 5 layers (Flame / Ember / Watcher / Forge / Bedrock).
- Build order: P0 Spark (4 weeks, BU Lite) → P1 Ember (6 weeks, BU Full opt-in) → P2 Flame (8 weeks, intervention) → P3 Forge (12 weeks, smart context + Gorgeous) → P4 Constellation (ongoing).
- Aesthetic: quiet luxury + Apple + Material 3 + fire (warm cream, deep ink, deep amber accent, procedural fire animation).
- Model stack: Gemma 3 1B (529MB int4) as router + Qwen 0.6B for JSON tools + Gemini Nano as fast path if present + ObjectBox 4.0 HNSW for memory.

### Next
- **P0 Spark — initialize the Android project scaffold.** This will happen on the owner's local machine (Galaxy A54 dev environment). Next session will plan P0 in detail: gradle modules, Compose setup, ObjectBox integration, the fire ring UI (static state, no growth yet), the manual Play (you write it), the blank-page journal, the manual Life Log.
- Owner to revoke GitHub PAT after this push (single-use pattern).

### Open questions (to resolve during P0 planning)
- Android Studio version + Kotlin plugin version pin.
- ObjectBox vs Room for the Ember layer (ObjectBox recommended for HNSW, but Room is more familiar — decision needed).
- Compose Multiplatform vs pure Jetpack Compose (recommendation: pure Compose for v1, KMP later if desktop reader becomes real).
- Min SDK (recommendation: 29 / Android 10 — covers Galaxy A54, drops <5% of users).
- Whether to ship a web prototype first to validate UX, or go straight to Android (recommendation: straight to Android — web can't simulate the Watcher meaningfully).

---

## Historical context (preserved for reference)

### v4 era (2026-11-02, killed 2026-11-04)
- 5-tab LOOM IA, BTK 5-plane kernel, 12 invariants, 30-table schema, 8-phase plan.
- Real Kotlin/Compose code shipped (~70 files across app/core/designsystem/guardian/identity modules).
- Reached P2 (rituals & reminders) before being killed.
- Killed because: enterprise architecture for one student, vocabulary of a knowledge worker, garden was a skin, complexity prevented shipping.
- All v4 code wiped from working tree. Preserved in git history (commit `9e8d116` and ancestors). Recoverable if needed.

### v5 era (2026-11-03, killed 2026-11-04)
- "The Living Bloom" — garden metaphor, 5 petals (Bloom/Garden/Soil/Keeper/Voice).
- Web prototype built (Next.js, ~12 components). Worked end-to-end.
- Killed because: garden was a skin not a soul, no discipline pressure, not unique enough.
- Web prototype code wiped from local sandbox (not pushed to repo).

### v6 era (2026-11-04, current)
- "The Fire" — discipline-first, two-tier (Lite/Full), system-level Watcher, on-device Forge.
- Vision doc locked. Repo restructured. P0 Spark about to begin.
