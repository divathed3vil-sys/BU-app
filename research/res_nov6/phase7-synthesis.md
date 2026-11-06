# BU Deep Research Program — Phase 7: Synthesis — The BU Design Specification & the Evidence Charter

**Version** 1.0 · **Date** 2026-11-05 · **Status** Delivered — awaiting builder review
**Program** `research/RESEARCH-PLAN.md` v1.0 (Phase 7 of 7 — the closing phase)
**Scope held** ~60% science (scoring-function defense · consolidation audit · Charter citation chains) / ~40% BU design (Design Spec v1 · P0 punch list) · replication flags on load-bearing claims · P0-first bias
**Owner** Dhivakaran · reviewed by any LLM session that reads `MEMORY/` first
**Consumes** `phase1-discipline.md` · `phase2-learning.md` · `phase3-neuroscience.md` · `phase4-attention.md` · `phase5-interventions.md` · `phase6-measurement.md` (their §6 source lists remain the citation base of record; this document only newly opens what it lists in §9)

---

## 1 · TL;DR — What Seven Phases Lock

1. **The fire scoring function is locked, parameterized, and simulated.** The master plan's seed survives contact with the evidence in amended form: a **two-ledger design** (a state ledger that excludes INFERRED entirely; a coaching ledger that keeps it at 0.3) satisfies Phase 6 D1's two hard constraints by construction rather than by conditional checks. With τ = 3 days and thresholds {2.0, 1.0, 0.4, 0.1}, the function **reproduces the locked P0 relight rule arithmetically** — three consecutive self-reported tendings score 1.12, crossing BURNING on day three, exactly as Phase 1 D5 promised in words. The daily self-reported user holds BURNING (1.59); a fully Watcher-verified week holds BLAZING (2.87); a week of silence reaches OUT. One v6 amendment falls out: **BLAZING becomes a verification-only state** — self-report alone can never reach the top, which is Gorgeous's little sibling and the honest reading of v6's own "30 days of truth" logic.
2. **Every load-bearing mechanic in BU v6 now sits in one of the three mission buckets** (§2.2): citation chain, marked unknown, or queued N=1 experiment. Fifteen mechanics audited; zero remain metaphor-only. The program's founding standard (master plan §1) is met.
3. **The BU Evidence Charter v1 ratifies twelve invariants** (§2.3), each with its citation chain and an operational test — from *no shame, no character verdicts* (AVE literature) to *gaps are marked, never zeroed* (Life Log schema) to *calibrated claims only* (conformal prediction). The Charter is also a **build gate**: no mechanic ships at any phase without naming its Charter clause, its BCT code, and its evidence.
4. **All 21 contradiction cards from Phases 1–6 are resolved** (§5): 18 confirmed with their working resolutions, 2 merged (Phase 1 Card B into Phase 5 Card B), 1 superseded by the scoring function (Phase 3 D10's "mirror delta" arithmetic note — its copy rules stand, its arithmetic retires). Two new synthesis-level cards are opened (one τ vs. per-anchor τ; honesty vs. encouragement at the top of the state ladder). Citation hygiene: the BCT code for badges is **10.4 *Social reward*** (not "stimulus reward" as session memory held); Wixted & Ebbesen (1991) is *Psychological Science* 2:409–415, "On the Form of Forgetting" — both corrected this pass.
5. **The P0 punch list sequences the complete P0 set — 35 tagged decision rows across Phases 1–6 plus Phase 7's own — into 30 build items for the 4-week Spark build** (§8), with a week-by-week plan, a P0 acceptance checklist, and the P0→P1 handoff spec (the storage schema carries grade/kind columns from day one, so the computed fire at P1 warms up from honest data). Phase 7 also delivers the two artifacts the plan promised: the **BU Design Specification v1** (§3, surface by surface) and the **Evidence Charter** (§2.3).

---

## 2 · Science

### 2.1 The Fire Scoring Function, Defended

The master plan seeded a draft and demanded it be *justified, parameterized, or replaced* — not pre-approved:

```
fire(t) = Σ over tendings e in last 7 days of  w(grade_e) · decay(d_e)
  w: OUTCOME_CONFIRMED 1.0 · OBSERVED 0.9 · USER_CONFIRMED 0.5 · INFERRED 0.3
  decay(d) = exp(−d / τ),  τ ≈ 3 days   (τ resolved in Phase 7)
state = threshold(fire) → OUT < EMBERS < FLICKERING < BURNING < BLAZING
  (+ Gorgeous gate: 30-day verified-tending streak, per evidence-grade rules)
```

**The conceptual move: the fire models none of the three things it looks like it models.** BU's data now support three distinct psychological timescales, and confusing them is the single most common way a habit app starts lying:

| Timescale | What it is | Its constant | Who owns it in BU |
|---|---|---|---|
| Content memory | How well a fact or skill remains retrievable | FSRS stability `S`, days–months | Ember resurfacing engine (Phase 2 D6) |
| Habit formation | How long a context-cued routine takes to automatize | Asymptotic curve; median ≈ 2 months, range 18–254 d (Lally et al. 2010, EJSP 40:998–1009; medians 59–66 d re-confirmed by Singh et al. 2024, Healthcare 12:2488, 20 studies, N = 2,601 `[REPLICATED-consistent]`) | Automaticity estimator + scaffolding fade (Phase 3 D2) |
| Evidence recency | How much a *past act* still counts as evidence of present behavior | τ, days | The fire (this section) |

The fire is an **evidence ledger, not a battery** (Phase 1 D4, grounded in the ego-depletion collapse `[FAILED]`, Hagger & Chatzisarantis 2016, PPS 11:546–573). Its decay constant is therefore a *display-semantics* parameter — an encoding of the identity grammar "you've shown up 4 of the last 7 days" — not a claim that the user's habit or memory decays with a 3-day constant. The formation timescale (months) is deliberately ~20× slower than the display timescale (days): an app whose *whole UI* swung on every missed evening would be measuring noise (Phase 3 Card D kept the two lanes separate; this function is where the slow lane gets arithmetic).

**Retention-form honesty note.** The classic forgetting literature fits retention by power and exponential families over long horizons (Wixted & Ebbesen 1991, *Psychological Science* 2:409–415 — "On the Form of Forgetting" `[citation corrected this pass]`; Rubin & Wenzel 1996, *Psychological Review* 103:734–760, 210 data sets; Murre & Dros 2015, PLOS ONE 10:e0120644, resolving Phase 2's debt item by replicating Ebbinghaus's savings function). Over a **7-day window at five-state granularity**, the power and exponential forms are nearly indistinguishable (both monotonically decaying, both within a fraction of a band of each other at every integer day). BU's exponential is therefore a **semantics decision** — chosen because one closed-form term with one interpretable constant is auditable by users (Phase 2 Card C's transparency principle) — and *not* an empirical bet about memory. If a future phase wants the FSRS-class power curve `R(t,S) = (1 + factor·t/S)^(−w20)` here too, the swap is local to `decay()` and invisible at state granularity.

#### 2.1.1 The two-ledger design (reconciling Phase 6 D1)

Phase 6 D1 locked two hard constraints on the seed: (a) **INFERRED never fires a state transition alone**; (b) **the Gorgeous gate counts only OBSERVED / OUTCOME_CONFIRMED**. Card B of that phase asked whether INFERRED should earn weight at all. The synthesis resolution is not a conditional transition check ("a transition may fire only if it would also fire with INFERRED zeroed") — that rule is correct but produces four ugly edge cases (transitions *within* a band, simultaneous crossing, decay-driven transitions where INFERRED *slows* the dim, and an unauditable runtime "would it also fire?" test). The clean mechanism is **two ledgers**:

- **State ledger** — drives the five states, all transitions, and the Gorgeous gate:
  `fire_state(t) = Σ_{e: grade_e ∈ {OUTCOME_CONFIRMED, OBSERVED, USER_CONFIRMED}} w_e · exp(−d_e/τ)`
- **Coaching ledger** — drives only the coaching layer (Forge context, pattern models, priors), rendered *never* as the fire's displayed state:
  `fire_coach(t) = fire_state(t) + Σ_{e: grade_e = INFERRED} 0.3 · exp(−d_e/τ)`

This satisfies (a) *by construction* — INFERRED is not in the state ledger at all, so no transition can ever be driven by it, and "keeps the 0.3 seed" is honored in the ledger where 0.3 does real work — and it satisfies the spirit of Phase 6 Card B: the fire's *state*, the thing the user acts on, is computed only from what happened or what the user said; inference shades the picture the Forge reasons over, always displaying its rule (P6 D1). Constraint (b) is untouched: the gate runs on the state ledger's verified tendings only. Simulation sanity (§2.1.3D): a perfect 7-day INFERRED week scores 0.96 on the coaching ledger — below BURNING even there — while the state ledger reads OUT, and a real week (5 × USER_CONFIRMED + 1 × INFERRED) transitions on the strength of its 1.43 state score with the coaching ledger shaded +0.06.

#### 2.1.2 The parameters, each with its defense

| Parameter | Locked value | Defense | Status |
|---|---|---|---|
| `w(OUTCOME_CONFIRMED)` | 1.0 | The anchor. External-world verification (photo-embedding, exam score, declared artifact) is the strongest instrument BU has; it defines the scale. | Locked (structure) |
| `w(OBSERVED)` | 0.9 | Machine-verified behavior (Watcher/time-proof). Nearly the anchor: Phase 6 D1 assigns it the same acquisition-channel discipline; the 0.1 gap encodes that machine presence proves *the act*, while outcome proof also speaks to *the point* of the act. | Locked (structure) |
| `w(USER_CONFIRMED)` | 0.5 | Honest self-report is **a different instrument, not a weak version of observation** (Phase 6 §2.1: Parry et al. 2021, Nat Hum Behav 5:1535–1547 — 106 effect sizes, self-report↔logs correlate only *moderately*). Half weight = real but partial evidence; the evening close is "honest about being self-report" (Phase 1 §2.8). | Locked (structure) |
| `w(INFERRED)` | 0.3, coaching only | Rule-based inference over OBSERVED inputs; deterministic and displayable, but its premise fits many lives (P6 Card B). Coaches; never rules. | Locked (structure) |
| `τ` | 3 days (pinned seed) | Three independent justifications: (i) the **identity grammar** is 7-day ("4 of last 7"), and τ=3 puts a week-old single tending at 9.7% — inside EMBERS' floor, so *a week of silence is OUT*, matching v6's five-state promise; (ii) the **relight arithmetic** (§2.1.3C): three small tendings cross BURNING at τ=3 with threshold 1.0 — the function reproduces Phase 1 D5's locked copy rule exactly, which is the strongest consistency check available pre-data; (iii) half-life = τ·ln2 ≈ **2.08 days**, so the fire visibly responds within a day or two of changed behavior — slow enough to be fair, fast enough to be honest. **No literature pins a behavioral-recency constant for app tendings; this is a declared set-and-tested parameter and E5 (Phase 6 D5) owns its final value.** | Seed pinned; τ final = E5 (P1 data) |
| Window | 7 days | The identity grammar's own window (Phase 1 D9; Phase 5 D4). Seamlessness check: the weight cut at the edge is exp(−7/3) ≈ **0.097**, essentially the EMBERS floor (0.1) — so the hard cutoff removes ≤0.05 per tending, invisible at band granularity. The window is the decay's cliff disguised as a rule. | Locked |
| Thresholds | BLAZING ≥ 2.0 · BURNING ≥ 1.0 · FLICKERING ≥ 0.4 · EMBERS ≥ 0.1 · else OUT | Derived, not free: **1.0** is the relight rule (3 × USER_CONFIRMED = 1.12) and the self-reported daily habit (steady 1.26–1.59); **0.4** separates "a fresh self-reported tending" (0.5, or 0.36 at two days) from "not for two days"; **0.1** is the OUT floor ≈ window-edge weight; **2.0** makes the top state reachable only by *verified* daily tending (OBSERVED daily = 2.87; a 3-day OBSERVED relight streak = 2.01) — see the v6 amendment below. | Declared set-and-tested (P1 field metrics tune; structure locked) |
| Partial day | `w(USER_CONFIRMED) × 0.5` = 0.25 per tending | The "partial" close option exists for honesty (Phase 1 D2; OQ2 tests it). Honest partials earn real but partial evidence: three *partial* days rebuild to 0.56 — FLICKERING, not BURNING. The 3-day rebuild rule applies to *completed* small plays; size never discounts weight, completion does. | Locked (rule) |
| Play size | Never discounts weight | A 15-minute relight play is a *full* tending (Phase 1 D5's "3 small days" would otherwise be a lie). Evidence grades measure provenance; duration measures the plan. | Locked (rule) |
| Tending unit | The day's play, graded at the Evening Close | The close is the *confirming act* (Phase 3 D1's reward timing attaches to it); it contributes no separate weight. One play/day per v6; multi-tending days sum naturally if v6 ever grows them. | Locked (rule) |

#### 2.1.3 The simulation — every design scenario, computed

All values below are computed from the locked function (`scripts/p7_fire_sim.py`); nothing here is hand-waved. The common case throughout is USER_CONFIRMED (the P0/P1 daily reality: a self-confirmed play, confirmed at close).

**(A) Steady states — what a stable life looks like:**

| Daily behavior | fire (7-day sum) | State | Steady-state asymptote (τ=3) |
|---|---|---|---|
| Daily tending, OUTCOME_CONFIRMED | 3.19 | BLAZING | 2.53 |
| Daily tending, OBSERVED | 2.87 | BLAZING | 2.28 |
| Daily tending, USER_CONFIRMED | 1.59 | BURNING | 1.26 |
| Daily INFERRED (coaching ledger only) | 0.96 | state ledger reads OUT | 0.76 |

The design reads correctly: showing up daily on your own word holds **BURNING** — v6's own copy makes BURNING respectable ("steady, warm. you are holding course") — and the top of the ladder requires the machine or the world to corroborate.

**(B) The miss cascade — how silence lands (daily USER_CONFIRMED, then k silent days):**

| Missed closes | 1 | 2 | 3 | 4 | 5 | 6 | 7 |
|---|---|---|---|---|---|---|---|
| fire | 1.09 | 0.74 | 0.48 | 0.29 | 0.16 | 0.07 | 0.00 |
| State | BURNING | FLICKERING | FLICKERING | EMBERS | EMBERS | OUT | OUT |

One missed evening dims visibly but does not collapse (−0.17 from 1.26 steady — a flicker, not a fall); three silent days leave embers; a full week extinguishes. This is v6's "Out: dark, thin" after about a week, now exact.

**(C) The relight arithmetic — Phase 1 D5 reproduced:**

| Relight day | 1 | 2 | 3 | 4 |
|---|---|---|---|---|
| fire (USER_CONFIRMED) | 0.50 | 0.86 | **1.12** | 1.30 |
| State | FLICKERING | FLICKERING | **BURNING** | BURNING |

Three small tendings rebuild the fire — the locked P0 copy rule ("Relight: 3 small days") is what the function *computes*. With full Watcher verification the same three days score 0.90 / 1.55 / **2.01** — BLAZING on day three. Tuning note, flagged honestly: evidence-strong relight reaching the top state in three days is defensible (the evidence *is* stronger) but may feel cheap against a 30-day verified month; if P1 field data agrees, the fix is threshold placement (BLAZING at 2.2 ≈ a 4-day OBSERVED streak), never a change to the weights.

**(D) The INFERRED ceiling (two-ledger sanity):** perfect INFERRED week → coaching 0.96 (below BURNING even on the coaching scale), state ledger OUT; 5×USER + 1×INFERRED → state 1.43 (BURNING, earned by the five), coaching 1.49 vs. 1.53 for 6×USER — inference shades by hundredths, never rules.

**(E) Gorgeous gate feasibility:** the gate (30 *consecutive* verified tendings, display-less; Phase 1 D10 + Phase 6 D1) is arithmetically reachable only inside the OBSERVED/OUTCOME_CONFIRMED regime — a self-report-only user can hold BURNING forever and never see the gate, which is the intended reading of "30 days of truth."

**Reconciliations this function forces (each logged once, here):**

1. **Phase 3 D10's "mirror delta" arithmetic is superseded.** The omission rule as written ("dims the fire by the mirror of its brightening delta") was an implementation note drafted before any function existed. In the sum function, omission dims are *emergent*: the steady-state single-miss dim (−0.17) is about a third of a fresh tending's weight (+0.5) — gentler than a strict mirror, and honest (the loss equals exactly the decay of the evidence, nothing rhetorical added). D10's *copy* rules — omission is information, the one-line relight play offered in the same screen, chronic-omission weeks shrink plays rather than raise voices — are unchanged. Logged as a spec amendment; no v6 sentence changes.
2. **Phase 4 D8 is honored by construction.** Drift events never enter the sum; drift dims the fire only insofar as it displaces tendings — the honest causal chain, now the only chain the arithmetic supports.
3. **v6 amendment (one sentence): BLAZING is a verification-only state.** v6's five-state ladder never said so, but its own Gorgeous logic ("no cheating the app") implies it; the arithmetic now states it plainly. Self-reported discipline is honored as BURNING, forever warm, never the top.
4. **The P0 manual fire is the same semantics without the machine.** In Spark, the user self-selects one of the five states at the Evening Close (tagged MANUAL in the record; state meanings frozen as v6's descriptions verbatim, §3.5). A skipped close persists the last-set state and is recorded as a no-close line — no silent forgiveness (Phase 1 D16) — so P1's ledger starts from honest data. At the P1 switch-on the manual tag retires and the computed fire starts from an empty ledger at OUT: the release note is the designed relight moment ("BU can see now. Show it three days.") — Phase 1 D5's ritual, applied to BU itself.

#### 2.1.4 What would move the function (pre-declared flip conditions)

| Trigger | What changes | Owner |
|---|---|---|
| E5 τ-sweep data (τ ∈ {2, 3, 5}, 12 weeks post-P1) | τ re-pinned; per-anchor τ (one constant per play context) considered only if single-τ residuals are systematic | Phase 6 D5 / Phase 7 Card E |
| P1 field metrics: threshold complaints (state feels wrong at close) | Thresholds retuned; weights and structure untouched | §4 D1 field test |
| INFERRED rule-agreement ≥ 90% over 60 days (measurable via the gap-flow) | Coaching weight revisited (0.3 → up); state ledger still closed to INFERRED | P6 Card B flip |
| Relight-to-BLAZING feels cheap at OBSERVED grade | BLAZING threshold 2.0 → 2.2 | §2.1.3C tuning note |
| Form dispute (power vs. exponential) becomes display-relevant | `decay()` swapped locally; states re-simulated before shipping | §2.1 form note |

---

### 2.2 The Consolidation Audit — Every Load-Bearing Mechanic, Bucketed

The master plan's founding standard (§1): by the end of Phase 7, every load-bearing mechanic in BU v6 sits in one of three buckets — **(a) a citation chain to primary evidence, (b) a marked unknown, or (c) an N=1 experiment queued for the builder**. The audit below is that standard, executed. "Chain" names the owning phase's evidence section; "experiment" names the queued design (§6). A mechanic may be (a)+(c) — evidence-supported in structure, personally testable in magnitude.

| # | v6 mechanic (the implicit claim) | Bucket | The chain, or the marked unknown, or the experiment |
|---|---|---|---|
| 1 | One-sentence Play ("one sentence per day beats a task list") | **(a)** + (c) | Implementation intentions d ≈ .65 across ~94 tests (Gollwitzer & Sheeran 2006, Adv. Exp. Soc. Psych. 38:69–119) `[REPLICATED]`; 2025 update d ≈ .27 post-bias-correction — still real; monitoring meta d ≈ .40 (Harkin et al. 2016, Psych Bull 142:198–229). The "beats a task list" head-to-head has no literature → OQ3 (grammar-valid vs. free-form completion, P0-testable). |
| 2 | Evening Close ("a one-line nightly audit changes behavior") | **(a)** | Harkin 2016 (above), frequency + recording + salient feedback all satisfied by construction (Phase 1 §2.8); friction budget honored (≤60 s). |
| 3 | Fire dims on drift ("watching produces honesty") | **(a)** + (c) | Justified *indirectly only*: drift dims nothing directly — it displaces tendings (P4 D8, honored by the state ledger's construction); the Watcher itself is grounded in the self-report gap (Parry 2021 `[T2]`); observation reactivity is real and disclosed (Harkin caveat; P4 OQ6) → **E2** (Watcher-on ITS, P1). |
| 4 | Visible decay disciplines better than streak counts | (b) + (c) | No literature tests decaying-display vs. counting-display head-to-head. Loss aversion is real (Kahneman & Tversky 1979, Econometrica 47:263–291; λ ≈ 2.25 in Tversky & Kahneman 1992) and token economies corrode intrinsic motivation (Deci, Koestner & Ryan 1999, Psych Bull 125:627–668 `[REPLICATED, boundary conditions]`) — both support the *design direction*; the magnitude is a bet, bounded by copy rules (P5 D4) and armed as **OQ1** (loss-framing ABAB, P0-testable). Stated publicly as a design bet (§7). |
| 5 | Intervention ladder 20/30/60 ("small frictions help rather than harm") | **(a)** + (b) | Ordering and guardrails are evidence-driven: one sec RCT (Grüning, Riedel & Lorenz-Spreen 2023, PNAS 120(8), e2213114120 — 37% fewer attempts, 57% fewer openings at 6 weeks; the *dismiss option* beat the delay); reactance bounds (Brehm 1966; Dillard & Shen 2005). The **minutes are marked unknowns** — declared set-and-tested (P4 D2), tuned at **OQ4** (P2 field test). |
| 6 | Hard Focus / sealed sessions ("removing the option is the discipline") | **(a)** | Situation modification outperforms in-the-moment resistance (Galla & Duckworth 2015, JPSP — six studies, N = 2,274, habits-not-inhibition mediate `[REPLICATED]`; Duckworth, Gendler & Gross 2016 process model); self-imposed deadlines help (Ariely & Wertenbroch 2002); blocking provokes reactance only when *not* user-installed (P4 Card A resolution). |
| 7 | Relight ritual & 3-day rule ("a dead fire can be rebuilt without shame") | **(a)** + (c) | AVE cascade (Marlatt & Gordon 1985); self-forgiveness → less procrastination (Wohl, Pychyl & Bennett 2010, PSPB); self-compassion → more self-improvement motivation (Breines & Chen 2012, PSPB) `[REPLICATED-consistent]`; fresh-start effect (Dai, Milkman & Riis 2014). The ritual's own efficacy → **OQ4rel** (P1-OQ1 relight survival log, P0-runnable) + **P5 Card B's flip metric** (relight-within-7-days). |
| 8 | Blank-page journal ("the page must stay empty") | **(a)** | Guided discovery beats unassisted (Alfieri et al. 2011, JEP — unassisted d ≈ −.38; enhanced/guided d ≈ +.50); resolution: blank center + edge guidance (P2 D4). The *aesthetic* survives; the *pedagogy* is edge-mounted. |
| 9 | Feynman-mode ("explain it simply works") | **(a)** | Provenance honestly folk (P2 §2.6); mechanisms measured: self-explanation (Chi et al. 1994, Cognitive Science 18:439–477), generation effect (Slamecka & Graf 1978), retrieval practice (Roediger & Karpicke 2006 `[REPLICATED]`). The name never ships as evidence (P2 Card D — non-flipping). |
| 10 | Ember resurfacing ("old entries should come back") | **(a)** | Spacing effect `[REPLICATED]` (Cepeda et al. 2006/2008); FSRS-class scheduler with pinned params, SM-2 floor at P1 (P2 D6); cap ≤3/day (P2 D13). |
| 11 | Forge predictions ("34% chance you do this tomorrow") | **(a)** — the *claim* fails, the *capability* passes | Conformal prediction gives honest intervals (Angelopoulos & Bates 2021); the v6 sentence is a banned copy form (P6 D4 pass/fail table). Prediction ships only with intervals, abstention rules, and the confidence-language table (P6 D3). |
| 12 | Sleep→discipline priors ("short sleep, small plays") | **(a)** | Deprivation costs domain-graded (Lim & Dinges 2010, Psych Bull 136:875–902 meta `[REPLICATED]`); consolidation (Rasch & Born 2013); screen-off proxy bounded (Lin 2019/2023) `[T2-adjacent]`; sleep surface four sentences forever (P3 Card C). |
| 13 | Stress-window mode ("under stress, protect the routine") | **(a)** | Stress shifts control to habits (Schwabe & Wolf 2009 `[REPLICATED-consistent]`); declared windows hold anchors, floor plays, mute feedback (P3 D8). |
| 14 | Gorgeous gate ("30 verified days means something") | **(a)** + (b) | Mastery experiences are self-efficacy's strongest source (Bandura 1977/1997 `[REPLICATED]`) — the gate is a 30-day mastery ledger; "30" itself is a ritual threshold, not a literature number → marked unknown, defended as design (§7); operationally locked per grade (§3.4). |
| 15 | Goes-dark ("the app withholds its value to be missed") | (c) — a bounded design bet | No literature tests the mechanic. Ethically amended, not defended (P5 D7): computed value dims, the user's record never, return = one tap + one play; instrumented from day one → **OQ1rel** (P5-OQ1 relight survival) and the <10%-conversion flip condition. Multi-year procedural fire (P4 Constellation): marked unknown, explicitly deferred with no claims made. |

**Audit verdicts.** Fifteen mechanics: **eleven with full citation chains** (rows 1–3, 6–13), **three evidence-structure + declared-number hybrids** (row 4's display question, row 5's minutes, row 14's "30") where the unknown is honestly marked and a queued experiment owns it, and **one bounded design bet** (goes-dark, row 15) that no literature can settle and that BU therefore states publicly and instruments (§7). The founder coverage matrix (master plan Appendix A) also closes here: ten founders + Feynman all landed in their owning phases, and Dewey's Phase 7 half — the Sunday Review structure — is specified at §3.10. Nothing in v6's load-bearing set remains metaphor-only.

---

### 2.3 The BU Evidence Charter v1 — Twelve Invariants with Citation Chains

**What a Charter invariant is:** a rule no feature may violate at any build phase, written to be *testable in a code review*, each carrying its citation chain and its operational test. The Charter is the program's final distillation — six phases of evidence compressed to twelve sentences a builder can check. It is ratified as §4 D5 and enforced as the build gate (§8): a mechanic without a named clause, BCT code, and evidence does not ship.

**C1 · No shame, no character verdicts — ever.** Feedback reports events and states, never essences ("you failed", "weak", "lazy" are banned classes). *Chain:* AVE cascade (Marlatt & Gordon 1985); self-forgiveness reduces procrastination (Wohl et al. 2010); self-compassion increases self-improvement motivation (Breines & Chen 2012). *Test:* the post-lapse copy matrix + lint file (P1 D5/D7); every string that renders after a "no" close or a fire drop.

**C2 · Discipline is a system, not a fuel.** No resource metaphors, no depletion talk, no "you ran out." *Chain:* ego depletion `[FAILED]` (Hagger & Chatzisarantis 2016 RRR, 23 labs, d ≈ .04; Carter et al. 2015 bias-corrected meta); willpower *beliefs* matter (Job, Dweck & Walton 2010) — so onboarding states the belief line. *Test:* lint ban-list family "resource"; the fire renders as evidence ledger, never battery.

**C3 · The user installs every constraint; BU proposes, never imposes.** Every hard mechanism (seal, block, ladder rung, stress window) is user-declared, reversible, and carries its rationale; every intervention has a dismiss path; propose-and-confirm is the default interaction. *Chain:* SDT (Ryan & Deci 2000, Am Psych 55:68–78); rewards/control undermine intrinsic motivation (Deci et al. 1999 `[REPLICATED]`); reactance (Brehm 1966); autonomy-supportive health-app design (Peters, Calvo & Ryan 2018). *Test:* rung audit — dismiss/override present at R1–R3; no default-on restriction anywhere.

**C4 · Scaffolding fades on evidence, never on schedule.** Hint/prompt density scales down with demonstrated competence (automaticity estimate + historical success), never with calendar time; Evening Close never fades. *Chain:* scaffolding + contingent fading (Wood, Bruner & Ross 1976, JCPP 17:89–100); striatal takeover in step with automaticity (Smith & Graybiel 2013); guided-discovery budgets (Alfieri 2011). *Test:* fade rule `h ∝ (1 − a/A*)` implemented; "days since install" appears in no fade expression.

**C5 · Every entry carries an evidence grade; INFERRED never moves the fire's state.** Four grades assigned by acquisition channel; no silent upgrades; INFERRED lives in the coaching ledger only and always displays its rule. *Chain:* self-report gap (Parry 2021, 106 effect sizes); provenance standards (Wand & Wang 1996, CACM 39(2):86–95); P6 D1. *Test:* two-ledger function + schema's grade column; a state transition traced to INFERRED is a bug, by definition.

**C6 · Calibrated claims only — uncertainty shown, or not claimed.** No naked point predictions; percentages only inside intervals or as true frequency counts; association verbs outside declared experiments; "BU doesn't know why" rides every conditional frequency; counterfactual uplifts are banned. *Chain:* conformal prediction (Angelopoulos & Bates 2021); probability communication (Joslyn & LeClerc 2012); the causal grammar (P6 D3/D4). *Test:* the claim formatter + the v6 pass/fail table (P6 §4's six sentences are regression tests).

**C7 · The user's record is never withheld, and return never costs more than one tap.** At every fire state including OUT: journal, Life Log, embers, exports fully accessible; relight = one tap + one play. What dims is only the coaching layer BU computes. *Chain:* the goes-dark ethics amendment (P5 D7, grounded in SDT + dark-pattern audit); attrition reality (Baumel et al. 2019: 3.3% median 30-day retention `[T2]`) — BU cannot afford hostage data morally or strategically. *Test:* OUT-state access audit; return-path timing ≤ one play.

**C8 · No dark patterns; the ban-list is normative and versioned.** Urgency timers, confirmshaming, win-back pushes, fake landmarks, milestone bribery, slot-machine schedules (RPE carve-out: reward fixed to the confirming act, ≤2 s, informational), forced continuity, roach motels — banned classes, lint-enforced. *Chain:* Mathur et al. 2019 (dark patterns audit); Gray et al. 2018; BCT abstention rows (P5 D6 — points/badges/leaderboards/comparison = permanent abstentions; points = 10.3 *Non-specific reward*, badges = 10.4 *Social reward* `[codes verified this pass; 10.4 name corrected]`). *Test:* the lint file; every new mechanic names its BCT and passes the pattern-family check.

**C9 · Behaviors are measured; experiences are never quantified.** The never-quantify list: journal (no counts/sentiment), relationships (no metrics/comparison), rest (no scores/charts), affect (no rating scales feeding surfaces), fire (five states, never a percentage). *Chain:* measurement corrodes enjoyment of measured activities (Etkin 2016, JCR 42(6):967–984 — six experiments `[T1, journal corrected P6]`) against monitoring's gains (Harkin 2016); the boundary is the activity's purpose (P6 Card A resolution). *Test:* the never-quantify list in code review; drift minutes remain the single quantified boundary case under report-only copy rules.

**C10 · Gaps are marked, never zeroed.** Untracked time is a first-class GAP entry rendered "not tracked"; no computation treats it as zero; no completeness metric exists. *Chain:* personal-informatics lapse literature (Li, Dey & Forlizzi 2010; Epstein et al. 2015); "complete by construction, never by coverage" (P6 D2). *Test:* GAP rendering + drift-math exclusion of non-tracked time.

**C11 · Consent is informed, granular, and revocable; on-device is the default.** The "Light the fire" moment = one screen, five facts (collected / stays-on-device / costs / how to stop / what you keep), no pre-selected defaults, no urgency, declining = Lite with zero loss; journal is plain-file local and exportable from day one. *Chain:* privacy calculus (Acquisti, Brandimarte & Loewenstein 2015, Science); consent design (P6 D6); P5 D7's exit-path rule. *Test:* the five-facts screen audit; one-toggle revocation exercised in QA.

**C12 · Real landmarks only; the fire never lies about time.** Monday, month-start, New Year, declared life events may time a line; no fabricated "Day 1", no engineered resets, no countdown rings; the fire's only reset is its 7-day rolling window, which never displays as zero. *Chain:* fresh-start effect (Dai et al. 2014) and its industrialization hazard (Dai, Milkman & Gohil 2018 — engineered resets help strugglers, erase high performers' momentum); streak framing rules (P5 D4). *Test:* landmark allowlist in code; lint family "fabrication."

---

## 3 · BU Design Specification v1

The consolidated decision log the build consumes. Twelve surfaces; each lists its locked rules with the owning decisions cited, so a builder reads this — not six phase documents — to ship. Anything not restated here is unchanged from its owning phase.

### 3.1 Identity & storage `[P0]`

Single-user, on-device, no accounts, no subscriptions, no telemetry leaving the phone. **Storage is plain-file local and exportable from day one** (P6 D6's P0 rule): journal entries, day records, and later Life Log exports are human-readable files; export carries every field with evidence grades as bracket tags (P6 D1). Target device Galaxy A54; the on-device model layer (Gemma 3 1B int4 ≈ 529 MB + Qwen 0.6B) arrives at P1's model-download moment, behind the consent flow (§3.11). Lite is an honest product, not a punishment: planning + execution surface, manual fire, the fire dim by default (v6 §Lite; P5 D3's ritual-first stance applies to it identically).

### 3.2 Onboarding v2 — the 3+1 questions `[P0]`

≤ 90 seconds, four inputs + one belief line, each from evidence (P1 D3 + P4 D5 + P3 D6):

| # | Question | Consumes | Feeds |
|---|---|---|---|
| 0 | Belief line, first open: "discipline is a system, not a fuel." | Job, Dweck & Walton 2010; ego-depletion verdict | inoculates the depletion mindset |
| 1 | Goal + "why does it matter?" (internalization anchor) | SDT; goal-setting (Locke & Latham 2002) | goal registry; identity copy |
| 2 | "What pulls you off track?" → obstacle + plan (WOOP, not confession) | Oettingen 2014 MCII; P1 D3 | play anchors; Evening Close pull-away mapping |
| 3 | "When does your head work best?" + optional wake anchor | Chronotype/synchrony (P4 D5) | suggested play window (default only; no labels) |
| 4 | "What does your evening already look like?" → pick the close's anchor | Context-stack rule (P3 D6) | close anchor; positional stability |

No trait claims anywhere (P1 D18); the 21:00 close default is fixed in P0, adjustable from P1 without guilt copy.

### 3.3 The Play `[P0] core; extensions tagged`

- **Grammar enforced** (P1 D1): `When [anchor], I will [single verb-phrase action] for [duration] at [place/tool].` Vague cues rejected with the one-line repair ("when exactly?"); the `at` slot is where temptation-bundling lives by grammar, never by reward-gating (P5 D5).
- **Sealed state explicit** (P1 D11): "sealed for today — swap to change"; **swap = re-commit** (P1 D15): re-formed in grammar, re-sealed, authorship stays with the user.
- **Study-marked plays** carry the retrieval-first card (P2 D1: dump → check → re-retrieve gaps → log one gap as tomorrow's seed; ≈ 20/10/10/5 in a 45-min play), the rereading warning (P2 D11, one line), and the plasticity checklist (P3 D7: *chosen by you · one thing at a time · closed with an answer · repeated here tomorrow* — static copy P0, enforced flow P2, programmatic gate on proposals P3).
- **Sizing:** P0 user-sized; P2 low-energy sizing `f(sleep_proxy, historical_completion)` silent and calm (P1 D6); P3 computed plays hold the 75–90% success band (P2 D7) with below-band splits and above-band graduation.
- **Forge proposals always carry the full grammar** — no anchor, no proposal (P1 D14); proposals pass the checklist programmatically (P3).

### 3.4 The Fire `[P0] manual → [P1] computed`

- **States:** OUT < EMBERS < FLICKERING < BURNING < BLAZING, computed by the two-ledger function of §2.1 (state ledger excludes INFERRED; window 7 d; τ = 3 pinned; thresholds 2.0/1.0/0.4/0.1). P0 renders the same five states manually (§2.1.3 note 4). **The fire is never rendered as a number, percentage, ring, or countdown** (P6 D7; P5 D4).
- **State meanings (frozen verbatim from v6, the P0 manual copy):** BLAZING "solid, bright, full. You are becoming who you said you would be." · BURNING "steady, warm. You are holding course." · FLICKERING "slight wobble, dimmer." · EMBERS "dim, half-empty." · OUT "dark, thin… to relight: tap the fire, do the play, repeat for 3 days."
- **Reward timing** (P3 D1, P1): brightness feedback computes at the confirming act, delivered ≤2 s; RPE-scaled vs. the user's running expectation, clipped band; expected outcomes get acknowledgment, never celebration; no fabricated variance; no badge/sparkle economy.
- **Omission** (P3 D10 copy + §2.1 reconciliation): dims are emergent from decay; the one-line relight play is offered in the same screen; chronic-omission weeks shrink plays, never raise voices; no re-ping, ever (P5 D2).
- **Identity copy** (P5 D4): "You've shown up 4 of last 7 days. That's who you are now." — computed from evidence-graded tendings, frequency claims true by construction; banned: numeric streaks, freezes, countdowns, milestones, percentiles, "don't break the chain," loss-urgency.
- **Gorgeous gate — "no cheating the app," operational per grade.** The gate counts a day when the day's tending carries: **OBSERVED** = Watcher saw the declared tool app for ≥ the play's declared duration minus grace, *or* the play timer ran to completion inside the sealed window; **OUTCOME_CONFIRMED** = a declared external verification event (P3: photo-embedding novelty ≥ threshold — embedding computed on-device, raw photo deleted after embedding, per v6 §proofs; exam score; user-declared artifact check). USER_CONFIRMED alone never counts; INFERRED never counts; partial days never count; a GAP day breaks the consecutive chain. 30 consecutive verified days; display-less (no countdown — P5 D4); the visible reward surface is the evidence-graded history itself (P1 D10). The declared-pause question (does a USER_CONFIRMED pause break the chain?) is an accepted unknown tied to P1-OQ5 (§7).
- **Multi-year arc** (P4): procedural growth animation past Gorgeous, mapped to behavioral milestones only; no evidence exists that it changes behavior — marked unknown, no claims (§7, row 8).

### 3.5 Evening Close — question set v3 `[P0]`

One screen, ≤ 60 s, one required question + optional lines (order A/B tested by **E4**, P6 D5):

| Slot | Content | Tag | Consumes |
|---|---|---|---|
| Q1 (required) | "Did you do it?" — did it / partial / no | P0 | P1 D2; Harkin 2016 |
| Optional reflection | one line, always optional, never compelled | P0 | P1 D2 |
| Study-day micro-prompt | "what did you recall today without looking?" (study days only) | P0 | P2 D2 |
| Sleep line | hours slept + rested-tap → tomorrow's play prior ×1.0 / ×0.75 / ×0.5 at ≥7 h / 5–7 / <5 h; suggest-only | P0 | P3 D3 |
| Pull-away line | "what pulled you away?" (drift days only) → obstacle + plan | P0 | P4 D8; P1 D3 |
| Pressure line | "pressure today? high/normal" (P0 seed of stress windows) | P0 | P3 D8 |
| Gap-flow | "what were you doing 2–5 pm?" — ≥2 h gaps, once/day, one-tap skip | P1 | P6 D2 |

Answer updates the fire in the same session (P1 D2's same-evening feedback); a skipped close is recorded, never forgiven silently (P1 D16); the close never fades (P3 D2).

### 3.6 Journal surfaces — write / voice / draw `[P0 / P2 / P3]`

**Write (P0):** blank center, write-only, plain-file; guidance confined to three edge surfaces — the study card (pre-play), the Evening Close micro-prompt (post-play), the opt-in Feynman-mode (P2 D4). **Feynman-mode (P2, write/voice):** name → explain plainly from memory → mark where it broke → one-line analogy; output = ordinary entry + gap-ember; ends with an accuracy check against source; honest attribution in docs ("the name is folk — the mechanisms are measured"), never a feature label (P2 D5, Card D). **Voice (P2):** on-device transcription only; the transcript is an ordinary entry. **Draw (P3):** Excalidraw-style canvas; entries become embers like any other. **All three: never quantified, never analyzed for sentiment, never scored** (C9; P6 D7); max one Forge-proposed edge prompt/day at P3 (P2 D4).

### 3.7 Watcher & Life Log `[P1]`

- **Consent:** the "Light the fire" five-facts screen (P6 D6): what's collected (app class + timestamps, never content), what stays on device (everything incl. the model), what it costs (v6's ~3–5%/day battery, verbatim), how to stop (one toggle), what you keep (journal/fire/export per C7). No pre-selected defaults; declining = Lite, zero loss.
- **Drift definition:** the P4 D1 matrix (never-counts list, 60 s grace = touch, conditional classes, always-count classes); P1 coarse form = any non-whitelisted app-open during a sealed play; accumulator + rung state machine per P4 D2; the user can always see what was counted and correct a label.
- **Life Log schema v1** (P6 D2, verbatim fields): `id · t_start · t_end · kind (auto|manual|inferred|outcome|gap) · grade (OBSERVED|USER_CONFIRMED|INFERRED|OUTCOME_CONFIRMED|GAP) · title · rule_id · verification · place · anchor · day_type · note`; manual ≤30 s/day friction budget; GAP renders "not tracked," never a zero; **no completeness metric, ever** (C10). The "SCHEDULED grade" ghost (P6 OQ6) is hereby swept: SCHEDULED exists nowhere in v1 — it is an entry *status* if needed, never a grade.
- **Interpretation caps** (P6 D7): the Watcher reports its own counts (app-class, timestamps, drift minutes) — never states, traits, mood, or diagnoses; observation reactivity disclosed at consent (P4 D6).
- **Evidence grades** (P6 D1): assignment by acquisition channel; plausibility checks downgrade-and-flag, never upgrade; Watcher suspension → gap-marking, no inference over dead sensors.

### 3.8 The Ladder — JITAI final form `[P2]`

The Phase 5 D1 decision table is the implementation spec and is not reprinted here (six decision-point rows, nine tailoring variables, nine intervention options, receptivity constraints). Locked invariants at synthesis: ladder capped at rung 1 during declared stress windows (P3 D8 interlock); block exists only inside declared Hard Focus, active from seal, override asked + logged, auto-pause after 2 override-abandoned sessions (P4 D2); thresholds 20/30/60 + 60 s grace + 30-min cool-down are declared set-and-tested priors — the ordering and guardrails are the evidence-bearing part (P5 Card A of P4); the v6 §7 copy "YouTube for 18 min. The fire is dimming." stays retired — drift lines report minutes, never price the fire (P4 D6).

### 3.9 Forge `[P1] v1 → [P3] full`

- **P1:** computed Play (deterministic chooser + templates; grammar enforced by construction), fire-state computation (§2.1), SM-2-class expanding resurfacing floor (P2 D6), the confidence-language table (P6 D3), frequency/co-occurrence claim forms (P6 D4).
- **P3:** FSRS-class ember engine with parameters pinned to a tagged FSRS release (P2 D6); ZPD difficulty targeting (P2 D7); scaffolding-fade rule driven by the automaticity estimate (P3 D2 + P2 D10); resurfacing cap ≤3/day (P2 D13); prediction pipeline with conformal intervals + abstention rules (P6 D3); causal-grammar enforcement in the claim formatter (P6 D4); pattern claims over the user's own counts only — Watcher interpretation caps hold (C5/C9).
- **Forge proposal intake checklist (programmatic, P3):** full grammar · ZPD band · plasticity checklist · resurfacing cap · Charter clause named (§2.3 build gate).

### 3.10 Sunday Review `[P1] minimal → [P3] full` *(the Dewey deliverable)*

Sundays, 5 minutes, one screen, three sections + one intention (v6 §10), now with its evidence structure: the three sections are **Dewey's reflective-thinking stages made tangible** (Dewey 1910, *How We Think* — felt difficulty → located/defined → suggestion of solution → reasoning → further observation): *Here's what withered* (the felt difficulty, stated with true counts), *here's the pattern I see* (the difficulty located — co-occurrence forms only, base rates shown, "BU doesn't know why" attached; change-class claims at P3 only under the declared-change-point discipline), *here's what grew* (the reasoning material — evidence-graded wins, 15.3's permitted home per P5 D6's gap note). The intention is one sentence, planted as next week's WOOP obstacle+plan seed — and next week's close data is Dewey's fifth stage: further observation. P1 ships frequency-only forms (true counts, no percentages below the P6 D3 display rules); P3 unlocks pattern class under the causal grammar. No shame register in any section (C1); "specific, not shaming" is the v6 line and it survives.

### 3.11 Notifications & the copy system `[P0]`

Exactly two P0 notification classes (Evening Close at user-set time; user-created play reminders, zero default), boundary delivery only, fire-once, never mid-seal, queue during seals (P4 D3); P1 adds the persistent status line with content rules (no urgency, no streak pressure — P5 D2's copy guide governs every string). FBM vocabulary (facilitator/spark/signal) is internal design language, never user-facing (P5 D2).

**The master copy ban-list (the lint file's families — normative, versioned; owning decision per family):**

| Family | Banned (classes, not exhaustive) | Owns |
|---|---|---|
| Shame/character | "you failed", "weak", "lazy", character verdicts after lapses | P1 D5/D7 |
| Resource/fuel | depletion talk, "ran out", battery metaphors for discipline | P1 D18 |
| Trait promises | trait rewrites, "builds willpower", "makes you disciplined", chronotype labels | P1 D18 / P4 D6 |
| Mechanic gaming | numeric streaks, freezes, countdown rings, milestone bribery, earned/unlocked, percentiles, "don't break the chain", loss-urgency, win-back | P5 D4/D7 |
| Pop-science | "21 days", "23 minutes", goldfish, brain-drain assertions, "multitasking ruins your brain", dopamine-talk, "neurons that fire together", neuroplasticity/rewiring/brain-training claims, learning styles, "10,000 hours", mastery forecasts, sleep-scolding, stress verdicts | P3 D9 / P4 D6 / P2 D3 |
| Fabrication | fake landmarks, "Day 1!" resets, urgency timers, confirmshaming, journey-milestone counters | P5 D7/D4 |
| Quantification caps | fire as number/percentage, journal counts/sentiment, completeness metrics, focus scores | P6 D7 |
| Claim grammar | naked point predictions, counterfactual uplifts ("raises it to 71%"), causal verbs outside declared experiments, missing "BU doesn't know why" | P6 D3/D4 |

### 3.12 Constellation `[P4]`

Desktop reader (the record, read-only), backup/export flows, multi-year procedural fire (no behavioral claims), spiral mapping for long-horizon knowledge arcs (P2 D9: foundational embers revisited at 5–10% of horizon, each revisit one complexity rung higher — recall → apply → teach), declared-pause support (P1-OQ5's subject), exercise log line (P3 D5: voluntary, dumb, recovery-copy only). No social features through P4 (P5 D5); no monetary stakes ever (P1 D13).

---

## 4 · Design Decisions

Phase 7's own decisions, numbered into the program's register. Every decision: Decision / Evidence / Confidence / Tag.

| # | Decision | Evidence | Confidence | Tag |
|---|---|---|---|---|
| D1 | **Fire scoring function v1 locked** — two ledgers (state excludes INFERRED; coaching keeps 0.3); w = 1.0/0.9/0.5/0.3; decay = exp(−d/τ), τ = 3 d pinned seed, E5 owns the final value; window 7 d; thresholds 2.0/1.0/0.4/0.1 (declared set-and-tested); partial = 0.5×; size never discounts; omission dims emergent; BLAZING verification-only (v6 amendment); P3 D10's mirror arithmetic superseded, copy rules unchanged | §2.1 (simulation A–E; Parry 2021; Bandura; Lally/Singh timescale separation) | High (structure) / Medium (parameter values — declared) | P1 (P0 manual semantics by §2.1.3 note 4) |
| D2 | **P0 state-meaning table frozen** — the five v6 state descriptions verbatim as the manual fire's copy; MANUAL tag in the record; state updates only at the Evening Close; no numbers ever | v6 §states; P5 D4; P6 D7 | High | P0 |
| D3 | **Build gate adopted** — no mechanic ships at any phase without naming (a) its Charter clause (§2.3), (b) its BCT code + evidence, (c) pattern-family clearance; the master ban-list (§3.11) ships as the lint file, normative and versioned | P5 D6/D7; §2.3 | High | P0 (lint file) / all (gate) |
| D4 | **N=1 master queue adopted** — 12 prioritized experiments (§6) consolidate the 35 phase-level OQs; instrumentation requirements (relight log, per-day close/play counters, E1/E4 toggles) land in the P0 schema | P6 D5; §6 | High (structure) / Medium (individual outcomes) | P0 |
| D5 | **Evidence Charter v1 ratified** — twelve invariants (C1–C12), each with citation chain + operational test; survives export; binding on Forge speech as much as on UI | §2.3; six phase evidence bases | High | all (ratified P0) |
| D6 | **Sunday Review v1** — three sections + one intention; Dewey's reflective stages as the structure; P1 frequency-only forms; P3 pattern class under the causal grammar; no shame register | v6 §10; Dewey 1910 (T3, bridged); P6 D3/D4 | Medium | P1 (forms) / P3 (patterns) |
| D7 | **Research debt stated in public** — the accepted-unknowns register (§7) ships in the repo; every declared set-and-tested number carries its experiment or is marked unknown in-app docs | master plan §1 three-bucket standard | High | P0 |
| D8 | **P0→P1 handoff spec** — P0 storage schema carries kind/grade columns (unused but present), no-close lines recorded, MANUAL tag retires at the P1 switch-on with the designed relight moment ("BU can see now. Show it three days.") | P6 D6; §2.1.3 note 4; P1 D16 | High | P0 |

**v6-doc amendments produced by this phase** (change log for `PLAN.md`): (1) BLAZING is a verification-only state (§2.1.3 note 3); (2) Phase 3 D10's mirror-delta arithmetic superseded by emergent decay dims (§2.1 reconciliation 1); (3) the SCHEDULED "grade" is swept from all surfaces — four grades, SCHEDULED is at most an entry status (P6 OQ6 closure); (4) the partial-day rule (0.5× weight) and the size-never-discounts rule are now part of the fire's definition (§2.1.2); (5) Gorgeous's "consecutive" is defined to break on GAP days and partial days (§3.4); (6) the identity grammar's "4 of last 7" is confirmed as the source of the 7-day window and thus the decay constant's first justification (§2.1.2).

---

## 5 · Contradiction Resolution Log (21 cards resolved, 2 opened)

Per master plan §1.5, every card from Phases 1–6 rolls up here. Statuses: **CONFIRMED** (working resolution stands, carried into the Charter or the spec), **MERGED** (two cards, one dispute), **SUPERSEDED** (arithmetic replaced, rules intact), **RESOLVED** (the synthesis itself answered it). Flip conditions remain armed as written in their owning phases.

| Card | Dispute | Status at synthesis | Final home |
|---|---|---|---|
| P1-A | External structure vs. autonomy | CONFIRMED | C3; swap-rate >30% flip armed |
| P1-B | App goes dark vs. support | **MERGED** into P5-B | C7; relight-within-7-days metric (P5 D3) |
| P1-C | Loss-framed fire vs. shame risk | CONFIRMED | C1 + structural-loss rule (P5 D4); OQ1 armed |
| P1-D | "Speaks the truth" branding vs. willpower mythology | CONFIRMED | C1/C2; harsh register points at systems, never persons |
| P2-A | Blank page vs. guided learning | CONFIRMED | §3.6 edge-guidance architecture; 2-week degeneration flip armed |
| P2-B | Desirable difficulty vs. competence protection | CONFIRMED | §3.3 sizing band (75–90% floor); C4-adjacent |
| P2-C | Scheduling optimization vs. user agency | CONFIRMED | §3.9 transparency + override; >50% override flip armed |
| P2-D | "Feynman" brand vs. honesty | CONFIRMED (non-flipping) | §3.6 attribution invariant; honesty outranks search traffic |
| P3-A | Variable-reward engineering vs. autonomy/honesty | CONFIRMED | C8's RPE carve-out (fixed to confirming act, ≤2 s, informational); compulsive-signature flip armed |
| P3-B | The app that must fade vs. the app that must survive | CONFIRMED | C4; engagement metrics never measure prompt-interaction (P6 owns) |
| P3-C | Sleep as data vs. sleep as scope creep | CONFIRMED (non-flipping scope) | §3.5 sleep line; the four-sentence sleep surface |
| P3-D | Fast journal vs. slow fire timescales | CONFIRMED — now arithmetic | §2.1's three-timescale table: journal records (fast), fire trains (slow), embers remember (content) |
| P4-A | Hard blocking vs. autonomy | CONFIRMED | §3.8 (block = declared-only, asked, logged, auto-pause); override-rate >30% flip armed |
| P4-B | Mere-presence guidance vs. contested evidence | CONFIRMED | §3.8's experiment-card framing; dissolves or upgrades on future replications |
| P4-C | Flow ideal vs. attention's natural ebbs | CONFIRMED | C6-adjacent copy rule (sell the door, not the trance); flow promises stay banned |
| P5-A | Engagement engineering vs. honesty | CONFIRMED | C8 permanent abstentions (10.3/10.4 rows); ≥90-day vendor-class evidence flip stated |
| P5-B | Goes-dark vs. attrition | CONFIRMED (bounded bet) | C7 + D3's danger-zone plan; <10% relight-conversion flip armed |
| P5-C | Fresh-start engineering vs. authenticity | CONFIRMED | C12 real-landmarks-only; landmark-clustering flip armed (P5-OQ5) |
| P6-A | Quantify the doing vs. protect the enjoying | CONFIRMED | C9 behaviors/experiences boundary; enjoyment-degradation flip armed |
| P6-B | Should INFERRED earn fire weight at all? | **RESOLVED** | §2.1.1 two-ledger design: coaching ledger keeps 0.3, state ledger closed by construction; ≥90% rule-agreement flip revisits the coaching weight only |
| P6-C | How far may passive data be interpreted? | CONFIRMED | C5/C9; the Watcher reports its own counts; consumer-grade validation literature = research debt (§7) |

**Two new synthesis cards** (opened here, owned by the builder's P1 data):

**Card E — One τ for all tendings vs. per-anchor τ.** *Claim A:* a single decay constant is auditable, E5-testable, and matches the identity grammar's uniform 7-day window. *Claim B:* evidence recency plausibly differs by play context (a work-adjacent play may decay faster than a keystone anchor) — a per-anchor τ would track that. *Working resolution:* single τ = 3 d pinned for v1; per-anchor τ is E5's stretch read, adopted only if single-τ residuals are systematic *and* the added complexity passes the C6 transparency test (every constant must stay explainable in one line). *Flip condition:* E5 residuals.

**Card F — The fire's honesty vs. the fire's encouragement at the top.** The verification-only BLAZING (D1's v6 amendment) is honest but could read as "my daily word is second-class" — an autonomy cost the SDT audit warns about. *Working resolution:* BURNING is honored as the daily-discipline state in copy and visuals ("steady, warm. you are holding course" is v6's own language, kept warm forever); the top state is framed as *more evidence*, not *more virtue*; the Gorgeous gate remains the only status surface and stays display-less. *Flip condition:* N=1 (E2 window) shows self-report-only weeks with flat or declining close-completion despite stable BURNING — if the plateau demotivates, the copy is re-examined (never the weights; the evidence math is not up for negotiation).

---

## 6 · N=1 Experiment Queue (consolidated & prioritized)

Thirty-five phase-level open questions collapse into one queue. Designs per the Phase 6 D5 playbook (ABAB / alternating treatments / changing criterion / declared-change-point ITS; pre-declared outcome; ≥14-day phases; no competing changes). The top twelve, prioritized by (i) decision weight, (ii) build-phase readiness, (iii) cost:

| # | Experiment (source) | Design | Outcome (pre-declared) | Tag |
|---|---|---|---|---|
| 1 | E4 · Evening Close question order (P6 D5) | alternating treatments, 4 wks | close-completion + optional-line answer rate | P0 |
| 2 | E1 · Play-window placement (P6 D5) | alternating treatments, 6 wks | tending rate per window; week-2+ only | P0 |
| 3 | Relight survival (P5-OQ1) | event log from P0 install | relight completion + time-to-relight per OUT episode | P0 |
| 4 | Grammar compliance (P1-OQ3) | completion by grammar-validity flag, 8 wks | completion delta, grammar-valid vs. free-form | P0 |
| 5 | Graded honesty — "partial" (P1-OQ2) | interruptible time series | self-report distribution after partial's enablement | P0 |
| 6 | The builder's day 3/14/30 (P5-OQ2) | daily counters from install | close-completion, tendings/day at the danger zones | P0 |
| 7 | Sleep-line association (P3-OQ1) | weekly logistic association from P0 data | <6.5 h vs. ≥7 h → next-day completion (else the prior is decoration) | P0-data |
| 8 | E5 · Fire-decay τ sweep (P6 D5) | within-user τ ∈ {2,3,5}, 12 wks post-P1 | felt-state vs. earned-state agreement; *also tracks conformal interval hit-rates* (P6-OQ2) | P1 |
| 9 | E2 · Watcher reactivity (P6 D5; absorbs P4-OQ6, P6-OQ5) | declared-change-point ITS | drift-level change on Watcher-on; months 1–3 vs. 4–6 decay | P1 |
| 10 | E3 · Hard Focus whitelist content (P6 D5; P4-OQ2's formal form) | ABAB, 4 × 14 d | deep-work session count per phase | P2 |
| 11 | Ladder threshold tuning (P4-OQ4) | P2 field metrics vs. declared priors | drift→relight conversion up, abandonment flat | P2 |
| 12 | Gap-flow recall contamination (P6-OQ3) | alternating gap-flow on/off weeks at P2 | next-day manual-entry contradiction rate vs. OBSERVED record | P2 |

The remaining OQs stay armed in their owning phase documents (P1-OQ1 loss framing & OQ6 chronotype · P2-OQ1 study-card cost, OQ3 interleaving, OQ5 Feynman usage · P3-OQ2 feedback-variance ABAB, OQ3 estimator calibration, OQ4 stress windows · P4-OQ1 nudge habituation, OQ5 in-seal friction · P5-OQ3 bundling self-trial, OQ4 identity line, OQ5 landmark clustering · P6-OQ4 consent cost · P1-OQ5 planned pauses [P4]) — each with its design already written; none is lost, none is duplicated.

---

## 7 · Research Debt & Accepted Unknowns

**7.1 Accepted unknowns (the literature cannot settle these; BU says so in public).** (1) The decay-vs-streak display question — BU's design bet, armed as OQ1; (2) the goes-dark mechanic — bounded by C7, instrumented from P0 (row 3); (3) Gorgeous's "30" — a ritual threshold, not a literature number; (4) ladder minutes 20/30/60 + grace + cool-down — declared priors (row 11); (5) the identity line's effect size — single flagged study (Bryan et al. 2011, PNAS 108:12653–12656 `[T1; replication status open]`), grammar kept under the truth rule, bans do not depend on it (P5 D4); (6) conformal coverage at N=1 under non-stationarity — declared mitigation, tracked inside E5; (7) consumer-grade passive-data interpretability — no per-claim validation literature exists (P6 Card C); (8) the multi-year fire's behavioral value — no evidence, no claims, P4 defers; (9) photo-embedding novelty as anti-cheat at consumer scale — `[EMERGING]`, gate-safe because OUTCOME_CONFIRMED also admits declared artifact checks; (10) whether a declared pause should break Gorgeous's consecutive chain — tied to P1-OQ5, decided with data, not by vibes.

**7.2 Citation debt carried forward (verified-number gaps; none blocks a locked decision).** P1: RRR exact d/N · CARES quit rates · Multon ρ · Harkin exact k/N · the 2025 implementation-intentions meta primary · Ariely–Wertenbroch replication outcome · Herman & Polivy canonical cite. P2: Rowland/Adesope/Bisra exact g · Macnamara domain %s · Brunmair & Richter g · Latimier 2021 · Smit 2025 outcome · srs-benchmark table values · Kornell 2009 ES · Bertsch g · RoPL canonical cites · Di Stefano status. P3: Jenkins magnitude · Recanzone/Byl pages · Strong 2011 venue+effect · Gillam condition labels · Edwards hazard ratio · Schultz 2016 pagination · Schwabe & Wolf 2013 vol/pages · Gardner 2012 pages · Kaushal & Rhodes median · Smith & Graybiel session counts · Lin 2019 metric class · Milstein 2021 article no. · Wood/Quinn/Kashy per-study split. P4: Leroy per-study Ns · Ward exact Ns · Stothart N · one sec arm sizes · Ophir N · Dillard & Shen ES. P5: BCTTv1 official table — **partially cleared this pass** (all codes used by the Charter verified against two independent secondary listings; the official 2013 Table 2 remains PDF-only debt; badge code name corrected to 10.4 *Social reward*) · Sailer Ns · Hamari study count · Bryan magnitudes · fresh-start magnitudes · Mathur/Gray page checks. P6: Parry exact pooled r (abstract confirms 106 effect sizes, moderate correspondence; the printed r needs the full text — one page-level read, queued for the Charter's next revision). P7: none new beyond the BCTTv1 PDF note.

---

## 8 · The P0 Punch List — the 4-Week Spark Sequence

The complete P0 set — 35 tagged decision rows across Phases 1–6, plus Phase 7's own D2/D3/D4/D7/D8 — consolidated into 30 numbered build items and sequenced for the 4-week build. Nothing else ships in Spark. The full v6 vision waits its turn; this list is the whole of P0.

**Week 1 — Foundation.** (1) Storage architecture: plain-file local, human-readable, exportable day one; schema carries `kind`/`grade` columns unused-but-present (D8); **no Life Log surface ships in Spark** — the friction budget is met by construction (P6 D2's P0 constraint) [P6 D6, D8]; (2) the lint file: master ban-list families as the code-review checklist + runtime checks where strings render [P1 D7, D18; P3 D9; P4 D6; P5 D4/D7]; (3) onboarding v2: belief line + 3+1 questions [P1 D3; P4 D5; P3 D6]; (4) notification policy: two classes, boundary delivery, fire-once [P4 D3; P5 D2]; (5) the no-trait-promises sweep across every drafted string [P1 D18].

**Week 2 — Play & fire.** (6) Play grammar input + validation + repair line [P1 D1]; (7) sealed-play explicit state + swap=re-commit [P1 D11, D15]; (8) manual fire: five states, state meanings verbatim (D2), MANUAL tag, same-session evening update [P1 D4 semantics; P5 D4 copy]; (9) relight ritual: tap → smallest play (15-min class) → 3-day rule, zero shame copy [P1 D5]; (10) identity/manual-fire copy rules: "4 of last 7" grammar shape, banned mechanic language [P5 D4]; (11) skipped-close honest recording [P1 D16].

**Week 3 — Close & journal.** (12) Evening Close v3: Q1 + optional reflection [P1 D2]; (13) study-day recall micro-prompt, with ember seeds recorded grade-ready from day one — the P0 floor of P2 D6's engine ladder [P2 D2, D6]; (14) sleep line with play-prior logic (suggest-only) [P3 D3]; (15) pull-away line → obstacle+plan mapping [P4 D8; P1 D3]; (16) pressure line [P3 D8]; (17) journal: blank center + the three edge surfaces; study-play card + rereading warning [P2 D1, D4, D11]; (18) process-praise + struggle-copy rules [P1 D8]; (19) context-stack: anchors required in grammar, stable daily surface [P3 D6]; (20) plasticity checklist static copy on study plays [P3 D7]; (21) prepared-environment layout: stable order, ≤3 visible plays [P2 D12].

**Week 4 — Instrumentation & hardening.** (22) N=1 instrumentation: relight log, per-day close/play counters, E1/E4 toggles [D4; P6 D5]; (23) bundling-by-grammar check: the `at` slot renders, no reward-gating exists [P5 D5]; (24) goes-dark conformance: journal/exports accessible at every fire state incl. OUT [P5 D7]; (25) never-quantify conformance: fire shows states, journal unmeasured [P6 D7]; (26) Lite honesty: manual fire, dim-by-default, no fake surfaces [P5 D3]; (27) the copy guide applied to every notification string [P5 D2]; (28) P0 acceptance run: each of the 30 items demoed against its owning decision; (29) P0→P1 handoff note: schema readiness, MANUAL retirement plan, the relight release line [D8]; (30) research-debt & unknowns register shipped in-repo [D7].

**P0 acceptance checklist (the demo that ends Spark):** a stranger could install BU, complete onboarding in ≤90 s, write a grammar-valid play, seal it, swap it once (re-commit), miss a day honestly, relight with three small days, close every evening in ≤60 s, write one blank-page entry, export everything as readable files, uninstall without losing a byte — and never once see a banned string, a number on the fire, or a notification that lied about time. That is the whole P0, and it is evidence-based from the first commit.

---

## 9 · Sources

**T1/T2 — newly opened or record-verified this pass** (all other citations resolve to the six phase documents' §6 lists, which remain the citation base of record).

- Wixted, J. T., & Ebbesen, E. B. (1991). On the form of forgetting. *Psychological Science*, 2(6), 409–415. doi 10.1111/j.1467-9280.1991.tb00175.x `[citation corrected this pass — not Psychological Review]`
- Rubin, D. C., & Wenzel, A. E. (1996). One hundred years of forgetting: A quantitative description of retention. *Psychological Review*, 103(4), 734–760. doi 10.1037/0033-295X.103.4.734
- Murre, J. M. J., & Dros, J. (2015). Replication and analysis of Ebbinghaus' forgetting curve. *PLOS ONE*, 10(7), e0120644. doi 10.1371/journal.pone.0120644 `[resolves Phase 2 debt item]`
- Parry, D. A., Davidson, B. I., Sewall, C. J. R., Fisher, J. T., Mieczkowski, H., & Quintana, D. S. (2021). A systematic review and meta-analysis of discrepancies between logged and self-reported digital media use. *Nature Human Behaviour*, 5, 1535–1547. PMID 34002052 `[abstract-level this pass: 106 effect sizes, moderate correspondence, weaker for problematic-use measures; exact pooled r = remaining debt]`
- Singh, B., Murphy, A., Maher, C., & Smith, A. E. (2024). Time to form a habit: A systematic review and meta-analysis of health behaviour habit formation and its determinants. *Healthcare*, 12(23), 2488. PMID 39685110 `[record re-verified]`
- Nahum-Shani, I., et al. (2018). Just-in-Time Adaptive Interventions (JITAIs) in mobile health. *Annals of Behavioral Medicine*, 52(6), 446–462. doi 10.1007/s12160-016-9830-8 `[record re-verified]`
- Michie, S., et al. (2013). The behavior change technique taxonomy (v1) of 93 hierarchically clustered techniques. *Annals of Behavioral Medicine*, 46(1), 81–95. `[codes verified this pass against two independent secondary listings (an open-access BCTTv1-coded umbrella review, Behav Sci 2025, PMC12649445; the Behavioral Scientist BCT glossary); badge code name corrected to 10.4 Social reward; the official Table 2 PDF remains citation debt]`
- Dewey, J. (1910). *How We Think*. D. C. Heath. (T3; the Sunday Review structure's bridge, §3.10)

**T4 consulted, never cited as evidence:** none this pass.

**Simulation code:** `scripts/p7_fire_sim.py` (all §2.1.3 values reproducible from the locked function).

---

*End of the seven-phase program. The research plan's mission statement is now testable against its own standard: every load-bearing mechanic in BU v6 sits in bucket (a), (b), or (c); the Charter gates the build; the P0 punch list is the bridge to Spark. Research debt is stated in public, the design bets are instrumented, and the builder's own N=1 experiments are queued. The fire's arithmetic now keeps the promises the metaphor made.*
