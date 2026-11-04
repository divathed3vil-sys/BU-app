# BU — The Fire

> A discipline-first life operating system. It watches, it remembers, it speaks the truth. The fire is alive — keep it lit.

**Status:** v6 vision locked. P0 Spark not yet started.
**Owner:** Dhivakaran · Galaxy A54 (mid-tier Android, 6GB RAM).
**License:** GPL-3.0 (see [LICENSE](./LICENSE) — open source, copyleft, "my own invention" protected).
**Build cycle:** ~1 year at 2 hours/day, solo, learning as we go. Public from day one.

---

## What BU is

BU is a discipline OS, not a productivity app. The central metaphor is a **fire** — not decorative, not gamified, but the literal functional state of the app. When the fire is lit, BU is sharp, present, watching with you. When the fire is dead, BU goes dark and silent. You want it back? Tend it.

The product ships in **two tiers**:

- **BU Lite** — plan/execute/focus/journal, no model, no Watcher. Works offline, immediately. The fire is dim (the app cannot see what you actually do).
- **BU Full** — user opts in → app downloads Gemma 3 1B (529MB) locally → the Watcher activates (system-level awareness) → the Forge intelligence comes alive. The fire becomes honest. The Play is computed, not written. The Life Log fills automatically.

Both tiers are free. Both are local. Both are private. The tier boundary is the model download — a deliberate commitment moment, not silent bloat.

## The five layers

| Layer | Job |
|---|---|
| **Flame** | UI — Jetpack Compose. The fire ring, the Play, journaling (write/voice/draw), the Life Log browser. |
| **Ember** | State + memory — ObjectBox 4.0 with HNSW vector index. Every action is an ember. |
| **Watcher** | Android system APIs — UsageStats, Foreground Service, Notification Listener, Accessibility (opt-in). |
| **Forge** | On-device intelligence — Gemma 3 1B + Qwen 0.6B + RAG over embers. Computes fire state, daily Play, patterns, predictions. |
| **Bedrock** | Three invariants: tap survives · fire honest · you own your embers. |

## The current state

- ✅ v6 vision locked — see [PLAN.md](./PLAN.md) and [research/v6-fire/](./research/v6-fire/)
- ⏭️ Next: P0 Spark — initialize the Android project scaffold, build BU Lite (planning + journaling + manual fire tending, no AI).

See [PROGRESS.md](./PROGRESS.md) for the running log of what's done and what's next.

## Working with this repo (for humans and LLMs)

This repository is designed to be read by both humans and AI assistants. If you are an LLM (Claude, GPT, Cursor, etc.) starting a session on this repo, **read these files first, in this order**:

1. [MEMORY/context.md](./MEMORY/context.md) — what BU is, what you need to know
2. [PROGRESS.md](./PROGRESS.md) — what was done, what's next
3. [PLAN.md](./PLAN.md) — the locked v6 vision
4. [MEMORY/decisions.md](./MEMORY/decisions.md) — architectural decisions and rationale
5. [MEMORY/glossary.md](./MEMORY/glossary.md) — the vocabulary
6. [MEMORY/lessons-learned.md](./MEMORY/lessons-learned.md) — what failed, what worked

Then check [MEMORY/session-log.md](./MEMORY/session-log.md) for the most recent working sessions.

## Repository structure

```
bu-app/
├── README.md                    ← you are here
├── PLAN.md                      ← the locked v6 vision
├── PROGRESS.md                  ← running log of work done
├── LICENSE                      ← GPL-3.0
├── MEMORY/                      ← for any LLM reading the repo
│   ├── context.md
│   ├── decisions.md
│   ├── glossary.md
│   ├── lessons-learned.md
│   └── session-log.md
├── research/
│   ├── archive/                 ← v1, v2, v3, v4 research (preserved as history)
│   └── v6-fire/                 ← v6 vision PDF + supporting notes
├── docs/                        ← user-facing docs
│   ├── privacy.md               ← what the Watcher sees, what stays on device
│   ├── architecture.md          ← the five-layer architecture, public
│   └── changelog.md
├── src/                         ← Android app (Kotlin + Compose)
│   ├── app/                     ← UI shell, navigation, DI wiring
│   ├── flame/                   ← UI layer: fire ring, the Play, journaling
│   ├── ember/                   ← ObjectBox state + memory
│   ├── watcher/                 ← Android APIs: UsageStats, FGS, Accessibility
│   ├── forge/                   ← Gemma 3 1B + RAG + chooser + patterns
│   └── bedrock/                 ← invariants, persistence, security
├── native/                      ← JNI bridges, model hosting
├── experiments/                 ← spike code (see experiments/README.md)
├── assets/                      ← fire animation stages, model files (git-lfs)
└── .github/
    ├── workflows/               ← CI: build APK, lint, test
    └── ISSUE_TEMPLATE/          ← bug, experiment-idea, decision-needed, learned-this
```

## Founding principles

1. **The fire is the discipline.** Not a meter, not a streak — the literal functional state of the app.
2. **The app withholds its own value when you drift.** Free apps chase you. BU goes dark.
3. **Everything stays on device.** No cloud, no account, no data leaves your phone. Ever.
4. **The garden never lies.** Evidence grades (Observed / Inferred / User-confirmed / Outcome-confirmed) preserved on every claim.
5. **You own your embers.** Full Markdown export anytime. No lock-in.

## License

GPL-3.0 — open source, copyleft. Anyone can read, learn, and contribute. No one can fork this closed-source. This protects the "never-before invention" status while keeping the project honest and open.
