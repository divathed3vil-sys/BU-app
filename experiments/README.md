# Experiments

> Spike code. Each experiment is a folder with a README stating the hypothesis, what was tested, what was learned, and whether it was kept or killed.

Experiments are **not production code**. They are isolated proofs-of-concept to answer specific technical questions before committing to an implementation in `src/`.

## Convention

Each experiment lives in its own folder:

```
experiments/
└── YYYY-MM-DD-short-name/
    ├── README.md          ← hypothesis, what was tested, what was learned, verdict
    ├── (source files)
    └── (results — logs, screenshots, benchmarks)
```

The `README.md` is mandatory. Use this template:

```markdown
# Experiment: Short Name

**Date:** YYYY-MM-DD
**Hypothesis:** What we're trying to prove/disprove.
**Status:** kept / killed / inconclusive

## What was tested

(1-2 paragraphs on the setup)

## Results

(What happened. Numbers if available.)

## What I learned

(The takeaway. Be honest.)

## Verdict

kept / killed / inconclusive — and why.

## Applies to

(Which layer/phase/component of BU this informs.)
```

## Examples of experiments to run

- `2026-11-15-gemma-3-1b-latency/` — benchmark Gemma 3 1B inference latency on Galaxy A54.
- `2026-11-20-objectbox-hnsw-scale/` — test ObjectBox HNSW query time at 10k, 100k, 1M vectors.
- `2026-12-01-usagestats-state-machine/` — build the foreground-event state machine and verify session durations.
- `2026-12-10-accessibility-yt-title/` — prove AccessibilityService can read YT video title; document the selectors.
- `2027-01-05-fire-canvas-procedural/` — prototype the procedural fire animation in Compose Canvas.

## Rules

1. **One experiment per folder.** Don't mix experiments.
2. **Always write the README.** An experiment without a README is junk.
3. **Mark the verdict honestly.** "Killed" is as valuable as "kept" — it stops us re-trying the same thing.
4. **Don't depend on experiments from `src/`.** Experiments are isolated.
5. **Delete experiments that are no longer relevant.** Don't let this folder grow unbounded.
