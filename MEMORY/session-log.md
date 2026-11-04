# MEMORY · Session Log

> Append-only log of AI work sessions. Newest at top. Each entry: date, what was worked on, what was decided, what's next.

---

## 2026-11-04 · v6 vision locked, repo restructured

**Worked on:**
- Killed v5 garden concept.
- Synthesized v6 "The Fire" concept from owner's research + 6 design questions.
- Wrote 28-page v6 vision PDF.
- Restructured GitHub repo: wiped v4 code (preserved in git history), archived v1-v4 research, built new v6 structure.
- Added GPL-3.0 LICENSE, .gitignore, .github/workflows/ci.yml placeholder, .github/ISSUE_TEMPLATE/.
- Wrote docs/privacy.md v1, docs/architecture.md v1.
- Wrote MEMORY/ files (context, decisions, glossary, lessons-learned, this session-log).

**Decided:**
- License: GPL-3.0.
- Two-tier product: Lite + Full.
- 5 layers: Flame / Ember / Watcher / Forge / Bedrock.
- 3 invariants: tap survives / fire honest / you own your embers.
- Model stack: Gemma 3 1B + Qwen 0.6B + ObjectBox 4.0 HNSW + Gemini Nano (optional fast path).
- 4-phase build plan: Spark / Ember / Flame / Forge + ongoing Constellation.
- Aesthetic: quiet luxury + Apple + Material 3 + fire.

**Next:**
- Owner revokes the GitHub PAT (single-use pattern).
- Owner clones the repo to their local PC.
- Next session: plan P0 Spark in detail (Android project scaffold, gradle modules, Compose setup, ObjectBox integration, the fire ring UI, the manual Play, the blank-page journal, the manual Life Log).
- After P0 plan is locked: owner implements locally, LLM advises + generates code as needed, owner pushes from PC.

**Files touched this session:**
- README.md (new)
- PLAN.md (new)
- PROGRESS.md (new)
- LICENSE (new, GPL-3.0)
- .gitignore (new, Android/Kotlin)
- MEMORY/context.md (new)
- MEMORY/decisions.md (new)
- MEMORY/glossary.md (new)
- MEMORY/lessons-learned.md (new)
- MEMORY/session-log.md (new, this file)
- docs/privacy.md (new)
- docs/architecture.md (new)
- docs/changelog.md (new, placeholder)
- research/archive/ (8 v1-v4 research files preserved)
- research/v6-fire/BU_v6_The_Fire.pdf (new, the 28-page vision)
- research/v6-fire/notes.md (new, supporting notes)
- experiments/README.md (new, convention for spike code)
- src/{app,flame,ember,watcher,forge,bedrock}/.gitkeep (new, module placeholders)
- native/.gitkeep, assets/.gitkeep (new)
- .github/workflows/ci.yml (new, placeholder)
- .github/ISSUE_TEMPLATE/{bug,experiment-idea,decision-needed,learned-this}.md (new)
- .gitattributes (new, LFS for model files)

---

*Add new entries above this line. Format: YYYY-MM-DD · short title. Then **Worked on**, **Decided**, **Next**, **Files touched this session**.*
