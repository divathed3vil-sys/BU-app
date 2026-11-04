# MEMORY · Lessons Learned

> What failed, what worked, what surprised us. Append-only. Newest at top.

*This file starts empty. Each session that learns something adds an entry here. Be honest — failures are more valuable than successes.*

---

## Template for entries

```
## YYYY-MM-DD · Topic
**What happened:** ...
**What I learned:** ...
**What I'd do differently:** ...
**Applies to:** (which layers/phases/components)
```

---

## 2026-11-04 · v4 and v5 both failed for the same reason

**What happened:** v4 (BTK/LOOM) reached P2 with ~70 files of Kotlin code, then was killed. v5 (the garden) had a working web prototype, then was killed. Both rethinks happened within 48 hours of each other.

**What I learned:** Both v4 and v5 failed because they optimized for systems-thinking correctness (5 planes, 12 invariants, 5 petals, evidence grades, etc.) instead of for the user's actual felt experience. The user said: "the app must care and push notifications when I drift" — v4 and v5 were both too polite to push. Discipline requires push. The fire metaphor works because it pushes (the fire dies) without nagging.

**What I'd do differently:** When designing v7 (if ever needed), start from the user's felt experience ("what does discipline feel like?") and work backward to the architecture. v6 did this — the fire was chosen because it has the right *emotional* properties (demands fuel, can be rebuilt, gives light when alive), and the architecture follows.

**Applies to:** all layers, all phases. The discipline pressure is non-negotiable.

---

## 2026-11-04 · The garden metaphor tolerated neglect, which is wrong for a discipline app

**What happened:** v5's garden metaphor (Plants, Birds, Streams) was aesthetically beautiful but functionally soft — gardens tolerate neglect. The Keeper (one-breath friction) was too gentle to push. The user explicitly said: "the app must care and push notifications when I drift."

**What I learned:** For a discipline app, the metaphor must have consequences for neglect. A fire dies without fuel. A garden just goes weedy. The metaphor is not decoration — it determines the discipline pressure the app can honestly apply.

**What I'd do differently:** When choosing a metaphor, ask: "what happens when the user neglects it?" If the answer is "nothing much," the metaphor is wrong for a discipline app.

**Applies to:** the Fire (the central metaphor), the intervention ladder design.

---

## 2026-11-04 · v4's 8-phase plan is why nothing shipped

**What happened:** v4 had 8 phases (P0 through P7). The owner reached P2 in v4 after extended work, then killed it. The plan was too big to ship.

**What I learned:** A plan that takes more than ~2 months to reach a usable artifact is too big. v6's 4-phase plan (P0 Spark in 4 weeks → usable BU Lite) is the right size. Each phase ends with a thing the owner uses daily.

**What I'd do differently:** Always plan to ship a usable thing in 4-6 weeks. If a phase is longer, split it.

**Applies to:** the build plan structure.

---

## 2026-11-04 · Public repo + MEMORY folder is the right pattern for year-long AI-assisted builds

**What happened:** The owner wants to build BU over a year at 2 hours/day, learning as we go, with AI assistance. The challenge: every LLM session starts cold and forgets context.

**What I learned:** The MEMORY folder (context.md, decisions.md, glossary.md, lessons-learned.md, session-log.md) is the solution. Any LLM reading the repo reads MEMORY first and picks up where the last session left off. This is the pattern for year-long AI-assisted projects.

**What I'd do differently:** Always initialize the MEMORY folder on day one of any long AI-assisted build. Update it every session.

**Applies to:** the repo structure, all future sessions.

---

*Add new entries above this line. Be honest, be specific, be brief.*
