# BU research · Phase 3 — Neuroscience: The Brain That Tends the Fire

**Version** 1.0 · **Date** 2026-11-05 · **Status** Delivered — awaiting builder review
**Program** `research/RESEARCH-PLAN.md` v1.0 (Phase 3 of 7)
**Scope held** ~60% science / ~40% BU design implications · replication flags on load-bearing claims · P0-first bias · every "Application to BU" block tagged P0–P4 · founders this pass: Hebb and Merzenich (both own this phase) · master plan's BTSP citation line corrected in §2.1
**Owner** Dhivakaran · reviewed by any LLM session that reads `MEMORY/` first

---

## 1 · TL;DR — The Nine Verdicts

1. **Hebb's rule survived 1949, but the pop version is banned from BU copy.** The postulate was confirmed as LTP (Bliss & Lømo, 1973), made coincidence-sensitive by STDP (~10–100 ms windows; Feldman, 2012), made stable by homeostatic normalization (Oja, 1982; BCM, 1982), and extended by BTSP — which potentiates inputs arriving *seconds* from the firing event, in a single trial `[EMERGING]` (Bittner et al., 2017, *Science* — the master plan's citation line corrected in §2.1). Cue-routine-reward has a real consolidation rationale; "neurons that fire together" as UI copy does not.
2. **Dopamine computes a prediction error, not pleasure.** Dopamine activity ≈ δ = received − predicted (Schultz, Dayan & Montague, 1997, *Science*): burst for the unpredicted, transfer to the predictor, depression at omission — formalized by temporal-difference learning and confirmed by blocking (Waelti et al., 2001) `[REPLICATED]`. A fire that brightens identically every evening is a dead signal by design. Rewards that keep teaching are immediate, small, information-carrying, not fully predictable — and never faked.
3. **Wanting is not liking, and cues pull the wanting trigger** (Berridge & Robinson, 1998; 2016) `[REPLICATED]`. Cue-triggered wanting is amplified by stress and state. BU builds cues that serve the user's loops — and takes seriously that the app itself can become a compulsive-checking cue. This bounds how game-like the fire may ever get.
4. **Control migrates from cortex to striatum as behavior repeats, and the striatum announces when.** The habit criterion is outcome-devaluation insensitivity (Yin & Knowlton, 2006); striatal "action-bracketing" emerges in step with behavioral automaticity (Jog et al., 1999; Barnes et al., 2005; Smith & Graybiel, 2013) `[REPLICATED-consistent]`. Scaffolding must fade on a measured automaticity estimate (D2) — persistence past automatization is interference, not help.
5. **The real habit-formation distribution kills the countdown.** Lally et al. (2010): 96 volunteers, 82 modelled, **median 66 days to 95%-of-asymptote, range 18–254**, one missed day immaterial, longer estimates extrapolated beyond the 84-day window. Singh et al. (2024; 20 studies, N = 2,601): medians 59–66 days, means 106–154, range **4–335**, gains SMD ≈ 0.69, 11/20 high risk of bias. "21 days" traces to a 1960 plastic-surgery anecdote (Maltz). Fire copy uses the distribution and the user's own curve — never a day counter.
6. **Roughly 43% of daily behavior is already context-bound habit** (Wood, Quinn & Kashy, 2002; 279 students, hourly diary; ~41% under the stricter same-time-and-place definition, ~47% of time mind-elsewhere) `[REPLICATED-consistent]`. BU does not outcompete context; it *becomes* one — same time, same screen, plays anchored to existing daily cues (D6).
7. **Adult plasticity is real but gated.** Attended, repetitive use redraws adult cortex (Jenkins et al., 1990); repetition crosses the gate only with a modulatory/reward signal — nucleus basalis pairing remaps auditory cortex (Kilgard & Merzenich, 1998, *Science*), VTA dopamine pairing does the same (Bao et al., 2001, *Nature*) — and bad repetition also trains (Byl et al., 1996). Every Play must satisfy the plasticity checklist (D7). The commercial record is cautionary: Fast ForWord's RCT advantage was null at scale (Gillam et al., 2008, N = 206) `[CONTESTED]`; ACTIVE shows durability of *trained-task* gains only.
8. **Sleep is a consolidation and regulation budget, treated as one line of honest math.** Systems consolidation is real (Rasch & Born, 2013) `[REPLICATED]`; deprivation costs are domain-graded, worst for attention lapses (ḡ ≈ −0.78) and mildest for reasoning (ns; Lim & Dinges, 2010; 70 articles, 147 tests) `[REPLICATED]`; sleep loss disconnects prefrontal control from amygdala (Yoo et al., 2007). Why-We-Sleep-class numbers are excluded (audited, §2.7). P0 gets a manual sleep check-in sizing tomorrow's plays (D3); the P1 screen-off proxy carries stated error bounds (±45 min class).
9. **Stress switches control from plans to habits** (Schwabe & Wolf, 2009, *J. Neurosci.*: stressed humans' choices became outcome-value-insensitive) `[REPLICATED-consistent]`. In high-stress windows, routines protect performance more than plans do — exam-season design: hold anchors, shrink plays, suspend novelty (D8). Exercise is a real but slow plasticity lever (+2% anterior hippocampus over a 1-year aerobic RCT in older adults; Erickson et al., 2011) — logged passively at P4, never nudged (D5).

**P0 haul (what this phase changes in the 4-week build):** a one-line manual sleep check-in inside Evening Close that sizes tomorrow's play priors (D3) · the plasticity checklist as static copy on the study-play card (D7) · an extended copy ban-list — no pop-neuroscience phrases, no "21 days," no "brain training" (D9) · an optional stress line in Evening Close that later powers exam-season mode (D8 seed) · confirmation that the Play grammar's context anchor (Phase 1 D1) is neuroscience-correct and must never be weakened (D6).

---

## 2 · Science

### 2.1 Hebb After 1949: The Postulate, Its Confirmations, and Its Second Timescale

**Mechanism.** Hebb (1949, *The Organization of Behavior*, p. 62 — T3, but the primary text) stated the rule precisely: *"When an axon of cell A is near enough to excite cell B and repeatedly or persistently takes part in firing it, some growth process or metabolic change takes place in one or both cells such that A's efficiency, as one of the cells firing B, is increased."* Three things matter for BU in that sentence, and all three are usually lost in the pop paraphrase. First, the rule is **causal-coincidence**: co-activation, not mere activity. Second, it is **repetition-gated** ("repeatedly or persistently") — a single co-occurrence does nothing, in the classical form. Third, it predicts a **physical change** (consolidation), not a metaphorical strengthening.

The postulate's empirical career is one of neuroscience's cleanest confirmation arcs. Long-term potentiation (LTP) — persistent synaptic strengthening after high-frequency co-activation — was demonstrated in rabbit hippocampus (Bliss & Lømo, 1973, *J. Physiology* 232, 331–356) `[REPLICATED]`, and shown to depend on the NMDA receptor's coincidence detection: the channel passes Ca²⁺ only when pre- and postsynaptic conditions coincide (Collingridge, Kehl & McLennan, 1983, *J. Physiology* 334, 33–46) `[REPLICATED]`. The mirror process, long-term depression (LTD), followed (Dudek & Bear, 1992, *PNAS* 89, 4363–4367): low-frequency co-activation *weakens* synapses. The rule became bidirectional — synapses track causal correlation in both signs.

**The math.** The rate-based Hebbian rule is `Δw = η · x · y` (x = presynaptic activity, y = postsynaptic activity). Left alone it is unstable — co-active weights grow without bound, a runaway that no real brain permits. Two classical fixes define the field: **Oja's rule** (Oja, 1982, *J. Math. Biology* 15, 267–280) adds an activity-dependent decay, `Δw = η · y · (x − w·y)`, which self-normalizes weights (converging to the principal component of the input) `[REPLICATED-consistent]`; and the **BCM rule** (Bienenstock, Cooper & Munro, 1982, *J. Neurosci.* 2, 32–48) makes the potentiation/depression crossover point a *sliding threshold* that rises with mean activity, producing selectivity and stability together `[REPLICATED-consistent]`. The design translation: real plasticity is **homeostatically budgeted** — it strengthens some connections only by weeding others, and it resists unbounded growth. Any system that claims to "strengthen" without a budget is not describing a brain.

The modern refinement is **spike-timing-dependent plasticity (STDP)**: the sign and size of `Δw` depend on the *order and interval* of pre- and postsynaptic spikes, with LTP when pre precedes post by ~0–20 ms and LTD when post precedes pre (canonical experiments: Markram, Lübke, Frotscher & Sakmann, 1997, *Science* 275, 213–215; Bi & Poo, 1998, *J. Neurosci.* 18, 10464–10472; review: Bi & Poo, 2001, *Physiological Reviews* 81, 1545–1565). A standard formalization: `Δw(Δt) = A₊·exp(−Δt/τ₊)` for Δt > 0, `−A₋·exp(Δt/τ₋)` for Δt < 0, with windows on the order of **10–100 ms** (Feldman, 2012, *Neuron* 75, 556–571 — which also stresses that spike timing is only one determinant among rate, cooperativity, and depolarization, and varies by synapse) `[REPLICATED]`. Millisecond windows are why *simultaneity* of cue, action, and feedback is not a slogan: it is the timescale the mechanism actually reads.

**The second timescale — BTSP `[EMERGING]`.** In 2017 the Magee lab reported that hippocampal place fields can be produced **in a single trial** by a potentiating rule that is *not* classical Hebbian: inputs that arrived **seconds** before and after a postsynaptic calcium plateau potential were potentiated, even when they were not coincident with spikes or depolarization (Bittner, Milstein, Grienberger, Romani & Magee, 2017, *Science* 357, 1033–1036). In slices, five pairings of subthreshold presynaptic activity with Ca²⁺ plateau potentials produced large potentiation with an asymmetric, seconds-long time course. The rule was named **behavioral time scale synaptic plasticity (BTSP)** because it operates on the timescale of behavior, and it can store an entire experienced sequence in one pass. Follow-up work established bidirectionality and rapid representational restructuring (Milstein et al., 2021, *Cell Reports*) and the field is actively mapping where BTSP applies `[EMERGING — few labs, short literature]`. **Correction to the master plan:** the plan's topic map cited "Bittner, Milstein, Chen & Magee (2017, *Cell Reports*)"; the primary BTSP paper is Bittner, Milstein, Grienberger, Romani & Magee (2017, *Science* 357, 1033–1036), and Milstein et al. (2021, *Cell Reports*) is the bidirectionality follow-up. Logged in the v6 amendments below.

**Why the second timescale matters more than its [EMERGING] status suggests.** The classical rule (repetition over days) and BTSP (one experience, seconds) are not rivals; they are the mechanisms of two different memory systems. Declarative/episodic memory *can* encode a single event — you remember the day you failed the exam once — while dispositions and skills consolidate slowly, by repetition. This is the neuroscience behind a distinction BU's architecture already makes: **the journal is the fast system's surface; the fire is the slow system's ledger.** A single journal entry can legitimately capture an event (hippocampal class); a Play only becomes *you* by repetition in a stable context (striatal class).

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Hebb (1949), *The Organization of Behavior* | Postulate: co-activation → increased efficiency; repetition-gated | foundational (T3) |
| Bliss & Lømo (1973), J. Physiol. 232, 331–356 | First LTP demonstration, rabbit perforant path | `[REPLICATED]` |
| Collingridge et al. (1983), J. Physiol. 334, 33–46 | NMDA dependence of LTP induction | `[REPLICATED]` |
| Dudek & Bear (1992), PNAS 89, 4363–4367 | Homosynaptic LTD — the rule runs both ways | `[REPLICATED]` |
| Oja (1982), J. Math. Biol. 15, 267–280 | Normalized Hebbian rule (stability) | `[REPLICATED-consistent]` |
| Bienenstock, Cooper & Munro (1982), J. Neurosci. 2, 32–48 | Sliding-threshold rule; selectivity + stability | `[REPLICATED-consistent]` |
| Markram et al. (1997), Science 275, 213–215 | Coincidence detection in pairs of connected neurons | `[REPLICATED]` |
| Bi & Poo (1998; 2001 review) | STDP: order-dependent, ~10–100 ms windows | `[REPLICATED]` |
| Feldman (2012), Neuron 75, 556–571 | STDP windows, prevalence, and limits | review (T1) |
| Bittner et al. (2017), Science 357, 1033–1036 | BTSP: single-trial, seconds-scale potentiation via plateaus | `[EMERGING]` |
| Milstein et al. (2021), Cell Reports | BTSP is bidirectional; rapid representational change | `[EMERGING]` |

**Application to BU.**

- `[P0]` **The cue-routine-reward structure is load-bearing, and this is why.** Repeating a play in a stable context recruits exactly the co-activation + repetition-gated consolidation the postulate describes; the habit circuitry that inherits it is §2.3. The onboarding copy may say "repetition in a stable context is how the brain consolidates" — because it is true at this level of abstraction.
- `[P0]` **Pop-neuroscience phrasing is banned (D9).** "Neurons that fire together wire together" as UI copy implies one-shot magic and invites overclaiming; the honest line is slower and more useful: "repeated, timed, and rewarded co-activation consolidates."
- `[P0]` **One strong experience *can* enter memory in one trial — the journal is not reduced by the fire's slowness.** Copy rule: journal entries are records (fast system); the fire tracks only the slow system. Never tell the user that one intense day "rewired" anything.
- `[P3]` **BTSP stays out of Forge speech entirely.** A single-study-class mechanism never justifies a product claim; if it matures, revisit in a later phase.
- `[P4]` **Homeostatic budgeting is the anti-burnout principle.** Consolidation strengthens some circuits by pruning others; a life system that adds without subtracting is biologically incoherent. The fire's evidence ledger should reward *sustained* structure, not accumulating volume (this constrains Phase 7's weighting function: recency-weighted, decaying evidence, never a hoard).

### 2.2 What Dopamine Computes: Prediction Error, Wanting, and the Fire's Reward Timing

**Mechanism.** The dominant account of midbrain dopamine's role in learning is the **reward prediction error (RPE) hypothesis**: dopamine transients encode the difference between received and predicted reward. Schultz, Dayan and Montague (1997, *Science* 275, 1593–1599) recorded from dopamine neurons in awake monkeys performing appetitive tasks and found the three-way pattern that formalizes it: (1) an unpredicted reward produces a phasic burst; (2) as learning proceeds, the burst **transfers** from the reward to the predictive cue; (3) at the expected time, an **omitted** reward produces a depression below baseline. The formal bridge is temporal-difference learning (Montague, Dayan & Sejnowski, 1996, *J. Neurosci.* 16, 1936–1947; Sutton & Barto, 1981, *Psych. Review* 88, 135–170) `[REPLICATED]`:

```
δₜ = rₜ₊₁ + γ·V(sₜ₊₁) − V(sₜ)          (prediction error)
V(sₜ) ← V(sₜ) + α·δₜ                    (value update)
```

Dopamine firing rate ≈ δ: positive for better-than-expected, near zero for fully expected, negative for worse-than-expected. The RPE account passed its sharpest formal test — **blocking**: a cue trained in compound with an already-predictive cue fails to elicit dopamine responses, exactly as TD theory requires (Waelti, Dickinson & Schultz, 2001, *Nature* 412, 43–48) `[REPLICATED]`. Human fMRI shows RPE-class signals in striatum (reviewed in Glimcher, 2011, *Annu. Rev. Neuroscience* 34, 225–258). Schultz's own refinement (Schultz, 2016, *J. Neurophysiology* — two-component response model: phasic burst + subsequent pause) `[verify pagination]` keeps the core intact.

Two consequences matter for design. First, **a fully predictable reward generates no teaching signal** — day 400 of an unbroken streak gets no dopamine response to the reward itself; the signal lives in *surprise*. Second, **omission is itself a signal** — the negative error (a dimming fire) is informative and motivating in the RPE sense, which is the biological ground under Phase 1's fire-dims design; but see §4 Card A for why variability must be capped.

**Wanting vs. liking.** Berridge & Robinson's dissociation is the second half of the dopamine story: dopamine is *not* the pleasure transmitter. "Liking" is mediated by small, fragile hedonic hotspots (μ-opioid/GABA microcircuits in nucleus accumbens shell and ventral pallidum), while "wanting" — incentive salience, the cue-triggered pull toward a reward — is generated by the large, robust mesolimbic dopamine system `[REPLICATED]` (Berridge & Robinson, 1998, *Brain Research Reviews* 28, 309–369; 2016, *American Psychologist* 71, 670–679). Dopamine-depleted animals still like what they get but stop working for it; cues themselves acquire the power to trigger wanting, and that cue-triggered wanting is **amplified by stress, excitement, and relevant states** — one reason addictions relapse under stress without any pleasure in prospect. Design translation, stated bluntly: a cue that reliably triggers *wanting to check* is the mechanism of compulsive checking, whether it wants to be or not. The fire, if it is a cue, is a cue.

**The math of a feedback schedule.** Let the confirming act (Evening Close, or a play's verified completion) be the event the fire answers. An RPE-consistent brightness update is:

```
Bₜ = Bₜ₋₁ + β·δₜ ,   δₜ = (eₜ − Ēₜ) / σ ,   clipped to [−1, +1]
```

where eₜ is the evidence grade of the event and Ēₜ the running expectation of it. Properties: (i) **immediacy** — δ is computed at the moment of confirmation, not at some later "reward time" (biological windows are millisecond-scale, §2.1; UI-scale contiguity chosen here: ≤ 2 s); (ii) **surprise-scaling** — the same completed play yields a smaller visual response when completion is expected (a streak's third week) than when it is a return from a lapse; (iii) **bounded** — no runaway brightness (homeostatics, §2.1); (iv) **omission** — a missed evening moves B down by the mirror term, always paired with the relight path (Phase 1 D5). Variability enters only as *capped, honest* embellishment (see D1), never as fabricated variance in the signal itself — because a fake signal is a manipulation (Phase 1's autonomy findings) and a deception (Phase 1's honesty brand).

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Schultz, Dayan & Montague (1997), Science 275, 1593–1599 | Dopamine ≈ RPE: burst / transfer-to-cue / omission dip | `[REPLICATED]` |
| Montague, Dayan & Sejnowski (1996), J. Neurosci. 16, 1936–1947 | TD framework formalizes the response | `[REPLICATED]` |
| Waelti, Dickinson & Schultz (2001), Nature 412, 43–48 | Blocking: dopamine complies with formal learning theory | `[REPLICATED]` |
| Glimcher (2011), Annu. Rev. Neurosci. 34, 225–258 | RPE hypothesis review incl. human evidence | review (T1) |
| Berridge & Robinson (1998), Brain Res. Reviews 28, 309–369 | Wanting ≠ liking; dopamine = incentive salience | `[REPLICATED]` |
| Berridge & Robinson (2016), Am. Psychologist 71, 670–679 | 25-year audit of incentive-sensitization; stress amplifies wanting | `[REPLICATED]` |

**Application to BU.**

- `[P1]` **D1 — Fire reward-timing rules.** Brightness feedback fires at the confirming act, within ~2 s; magnitude scales with surprise (delta vs. running expectation), is capped, and never fires for zero-information events (a fully expected outcome gets acknowledgment, not a celebration). Omission (missed close) produces the mirrored dim event, always paired with the smallest relight step. Full rules table in §3.
- `[P1]` **No confetti economy.** Badge/sparkle inflation is an anti-RPE design: it decouples the visual signal from information, trains tolerance (hedonic adaptation — Frederick & Loewenstein, 1999, "Hedonic adaptation," in *Well-Being*, T3), and pushes the fire toward a slot machine (Card A). The fire's expressiveness is budgeted, rotated slowly, and information-first.
- `[P1]` **The fire is a cue; treat it with cue discipline.** Wanting science (Berridge) predicts that a reliably-rewarding fire invite can become a compulsion trigger. Mitigations: no push notification exists to *open* the fire in P0 (the Evening Close is time-boxed, one surface), and any future reminder must be scheduled (user-set), variable only in *content*, never in frequency.
- `[P2]` **Stress amplifies cue-triggered wanting** (Berridge & Robinson, 2016) — one more reason exam-season mode (D8) mutes novelty and reward surfaces rather than enriching them.
- `[P3]` **Forge never cites dopamine in user-facing copy.** "Your brain rewards you" is banned (D9); the copy says what the app computes ("the fire answers what you actually did").

### 2.3 The Habit Circuit: How Control Moves From Plan to Body

**Mechanism.** The animal literature separates two action-control systems with a behavioral razor: the **outcome-devaluation test**. Goal-directed control is demonstrated when an animal whose preferred outcome has been devalued (sated/illness-paired) *reduces* the action that earns it; habitual control is demonstrated when the action continues regardless. Behavior that survives devaluation is, by definition, a habit — insensitive to what it yields, driven by the antecedent cue. The basal-ganglia evidence maps the systems: early in training, goal-directed control depends on the **dorsomedial striatum** and prefrontal/associative circuitry; with extended training, control shifts to the **dorsolateral striatum** (DLS), where lesions preserve outcome expectancy but *prevent* habit formation, and inactivation of DLS restores goal-directed sensitivity after habit has formed (Yin & Knowlton, 2006, *Nature Reviews Neuroscience* 7, 464–476; Yin, Knowlton & Balleine, 2004, *Eur. J. Neuroscience* 19, 181–189) `[REPLICATED]`.

Inside the DLS, the unit of storage is the **chunk**. Jog et al. (1999, *Science* 286, 1745–1749) recorded striatal activity while rats learned a T-maze routine and saw two transformations: sequence-related activity became compressed to the beginning and end of the run ("task-bracket" activity), and acquisition shifted off the associative striatum onto the sensorimotor striatum as the behavior habitualized. Barnes et al. (2005, *Nature* 437, 1158–1161) then showed this bracketing is **dynamic**: striatal ensembles re-code at the moments of procedural transition, and the pattern re-organizes again when task demands change. The decisive link to humans' subjective report came from Smith & Graybiel (2013, *Neuron* 79, 361–374): as rat lever-pressing automatized under a variable-interval schedule, striatal bracketing emerged *in step with behavioral automaticity* — the neural and the phenomenological curves track each other `[REPLICATED-consistent]`. Graybiel (2008, *Annu. Rev. Neuroscience* 31, 359–387) generalizes the frame: habits are the striatum's compression of repeated, rewarding sequences into single executable units — "chunks" whose bracketed boundaries a cue can trigger end-to-end.

**What "the play is in the body" means operationally.** It means three measurable things, not a vibe: (1) **initiation moves to the cue** — the behavior is triggered by the context, not deliberated (Wood & Rünger, 2016, *Annu. Rev. Psychology* 67, 289–311, review `[REPLICATED-consistent]`); (2) **the outcome stops steering** — devaluation-class insensitivity, whose human proxy is "I did it without thinking about whether I wanted to"; (3) **the sequence brackets** — start and end are automatic, the middle runs off attention. BU's practical read: a play has automatized when it is *triggered* and *completed* without deliberation, not when it is enjoyed. The gold-standard criterion is unavailable to an app (no devaluation test for humans-in-the-wild), so BU estimates automaticity from the observable proxy stack below — and treats "still effortful" as a *state of the curve*, not a failure of character (Phase 1's copy rules).

**The math: the automaticity estimator (D2).** Lally's curves (§2.4) are well-described by an asymptotic exponential. For each play p, fit the daily series (did it happen: 0/1, optionally weighted by evidence grade g):

```
aₚ(t) = Aₚ · (1 − e^(−t/τₚ))          (automaticity estimate, t = day of run)
```

- Fit online with an exponential forgetting factor (λ ≈ 0.97) so the estimate can *fall* after context breaks — the estimator must be able to decay, because the underlying circuitry can (Barnes et al., 2005's re-coding; Wood, Tam & Witt, 2005's context disruption, §2.5).
- Completion data alone is honest but coarse; where the user self-rates automaticity (optional, P1+: a 4-item SRBAI-class micro-check monthly — Gardner, Lally & Wardle's short instruments, T1-adjacent), the self-report acts as a calibration target for Aₚ, not as the daily signal.
- **Fade rule (joins Phase 2 D10):** hint density and prompt frequency scale as `h = h₀ · max(0, 1 − aₚ/Aₚ*')`, where Aₚ*' is the user's calibrated plateau. Concretely: below ~30% of plateau → full guidance; 30–70% → study-card and anchor reminders only; >70% → the app goes quiet; the Evening Close (the daily tend) never fades. The asymmetry is deliberate: scaffolding fades because persistence past automatization is interference (§2.3's bracketing argument), but the daily tend is the user's chosen ritual, not scaffolding.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Yin & Knowlton (2006), Nat. Rev. Neurosci. 7, 464–476 | DMS→DLS control shift; habit = devaluation-insensitivity | `[REPLICATED]` |
| Yin, Knowlton & Balleine (2004), EJN 19, 181–189 | DLS lesions preserve expectancy, block habit | `[REPLICATED]` |
| Jog et al. (1999), Science 286, 1745–1749 | Task-bracketing; acquisition shifts to sensorimotor striatum | `[REPLICATED]` |
| Barnes et al. (2005), Nature 437, 1158–1161 | Bracketing is dynamic; re-coding at transitions | `[REPLICATED]` |
| Smith & Graybiel (2013), Neuron 79, 361–374 | Striatal bracketing tracks behavioral automaticity | `[REPLICATED-consistent]` |
| Graybiel (2008), Annu. Rev. Neurosci. 31, 359–387 | Habits as chunked, cue-triggered sequence units | review (T1) |
| Wood & Rünger (2016), Annu. Rev. Psych. 67, 289–311 | Modern psychology-of-habit synthesis | review (T1) |

**Application to BU.**

- `[P1]` **D2 — Automaticity estimate → scaffolding-fade rule.** Every play carries the estimator from §2.3; guidance surfaces (study-play card, anchor reminders, Forge hints at P3) shrink as the estimate climbs; the Evening Close never fades. The estimator is visible in plain units when asked ("this one's been on its own legs for a while") — never as a percentage score to grind (Phase 2's no-metric-grind rule).
- `[P1]` **Context breaks must be *shown*, not hidden.** The estimator decays after a context change (travel, job shift); copy says "the context moved — it'll need you again for a bit," which is the Barnes/Wood result stated honestly, and pre-empts the shame reading.
- `[P3]` **Forge's "computed plays" inherit the estimator.** When Forge proposes plays for a target habit, it initializes from the user's fitted τₚ class (simple behaviors sit lower on §2.4's range than complex ones) rather than a generic 66.
- `[P4]` **The multi-year procedural fire gets a sanity bound from the same math.** A play still below ~70% of its fitted plateau after 6+ months of genuine attempts is not "lazy" — the complexity/cue structure is wrong; Forge's advice at that point is re-anchoring, not escalation (cross-ref §2.5).
- `[P0]` **The 21:00 Evening Close is the app's one non-fading surface — by design.** The tend is a *chosen ritual* (user's decision, SDT-clean, Phase 1), not scaffolding on a behavior; it stays constant while everything else gets quieter.

### 2.4 How Long Habits Actually Take: The Real Distribution and the 21-Day Myth

**The numbers, audited.** Lally, van Jaarsveld, Potts and Wardle (2010, *European Journal of Social Psychology* 40(6), 998–1009) remains the only prospective study that tracked *daily* automaticity curves for new behaviors in daily life: **96 volunteers** each chose one eating/drinking/activity behavior to perform daily in a self-chosen stable context (e.g., "a piece of fruit with lunch," "50 sit-ups after morning coffee"), rating automaticity each day (short Self-Report Habit Index) for the 12-week study; **82 provided analysable data**. Findings, with the caveats that pop culture strips off: (1) the curve is **asymptotic** — steep early gains, then a long flattening; (2) time to reach **95% of each individual's estimated asymptote** had a **median of 66 days**, with an individual **range of 18–254 days**; (3) complexity mattered — drinking a glass of water sat at the fast end, multi-step exercise at the slow end; (4) **missing one day did not measurably bend the curve**; (5) the observation window was 84 days, so estimates beyond it — including the 254 — are **extrapolations from fitted curves**, and only a subset of curves fit cleanly enough to produce plateau estimates. The "66 days" that escaped into popular culture is a median of a heterogeneous, extrapolated distribution — not a finish line, and not a law.

The field's first systematic review updated the picture: Singh, Murphy, Maher and Smith (2024, *Healthcare* 12(23), 2488, PMC11641623) pooled **20 studies, N = 2,601** (behaviors: physical activity, diet, flossing, water, vitamins, sedentary reduction). Four studies reported time-to-habit: **medians 59–66 days; means 106–154 days; individual range 4–335 days** (the means sit far above the medians because the distribution has a long right tail). Pre/post habit-score meta-analysis: **SMD ≈ 0.69 (95% CI 0.49–0.88)** — habit strength is trainable. Determinants: frequency, timing, behavior type, individual choice, and **self-selected habits and morning practices formed stronger habits** — an independent line of evidence for BU's autonomy-first, anchor-first grammar. Two honesty flags: 11 of 20 studies rated high risk of bias, and the evidence base is health-behavior-sized (flossing, water), not life-project-sized `[CONTESTED scope]`. Adjacent: Kaushal & Rhodes (2015, *J. Health Psychology*) followed 111 new gym members and found gym habits took on the order of six weekly sessions × 4 weeks minimum to register as habitual — exercise sits at the slow end of the range `[verify exact stats]`.

**The myth audit.** "21 days" has no study behind it. Its traceable origin is Maxwell Maltz's *Psycho-Cybernetics* (1960 — T3): a plastic surgeon's observation that patients took roughly three weeks to *adjust to changed appearance*, generalized in one passage ("a minimum of about 21 days") into a self-help maxim that then propagated for decades. Gardner, Lally and Wardle (2012, *British Journal of General Practice* 62(604), 664–666) dismantled it explicitly in the primary literature `[verify pagination]`. The number is not merely wrong; it is *harmfully* wrong for anyone building a habit app: a user on day 25 of an unformed habit is roughly a third of the way up a typical curve, and a 21-day frame converts that into personal failure — which is precisely the shame path Phase 1 banned.

**The math of the display.** BU shows the user their own fitted curve against the *distribution*, not a countdown:

```
copy rule: "median ≈ 2 months, huge spread (people ranged 18–254 days in the
best-known study); this one's on day N, plateau-estimate P."
banned:    "21 days to a new you" · "day 66/66 ✓ habit complete" · any ring that closes.
```

The plateau estimate Aₚ (§2.3) is a smooth, never-100% quantity — asymptotes, not finish lines.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Lally et al. (2010), EJSP 40(6), 998–1009 | Median 66 d (18–254) to 95% of asymptote; asymptotic curves; 1-day miss ≈ null; N = 96/82 | `[REPLICATED-consistent]` |
| Singh et al. (2024), Healthcare 12(23), 2488 | 20 studies, N = 2,601: medians 59–66 d, means 106–154, range 4–335; SMD 0.69; 11/20 high RoB | `[T2 meta, heterogeneity high]` |
| Gardner, Lally & Wardle (2012), BJGP 62(604) | The 21-day claim has no evidence base | `[REPLICATED-consistent]` |
| Maltz (1960), *Psycho-Cybernetics* | Traceable origin of "21 days" (surgical-adjustment anecdote) | origin (T3) |
| Kaushal & Rhodes (2015), J. Health Psych. | Gym habits at the slow end (~6 wks × 4/wk min dose) | `[verify stats]` |

**Application to BU.**

- `[P0]` **D9 — copy rules lock:** never "21 days," never a countdown ring, never "habit complete." Onboarding says the honest thing once: "habits typically take about two months to feel automatic, with enormous spread — you'll see your own curve, not a deadline."
- `[P0]` **The missed-day rule is evidence-locked:** a single miss does not reset anything (Lally's null). The fire dims on a missed *close* (that's the tending signal), but a missed *play* costs the curve only what it costs — one point, not the run. This kills the "start over from day one" pattern at the root.
- `[P1]` **Fire state semantics use the distribution.** When fire copy references progress, it references the user's own fitted curve (§2.3), not population milestones.
- `[P3]` **Forge's habit-target proposals carry the range explicitly** when the user asks "how long will this take": "median around two months for behaviors like this; the range in the literature runs from under three weeks to over eight; exercise-type behaviors sit at the slow end."
- `[P4]` **Constellation's multi-year fire is where the slow tail lives.** The architecture already assumes years; §2.4's right tail (means 106–154 days even for flossing-class behaviors) says multi-month plateaus are *normal*, and the build plan's patience is evidence-aligned.

### 2.5 The 43% World: Behavior Is Context-Bound, So BU Becomes a Context

**The numbers.** Wood, Quinn and Kashy (2002, *JPSP* 83(6), 1281–1297) sampled everyday life with hourly diary prompts (programmable wristwatches; **279 college students across two studies**). When "habitual" was defined as behaviors repeated **almost daily and usually in the same location**, participants performed **43% of reported behaviors** habitually (a stricter same-time-and-place definition lands at ~41%; a companion analysis found participants were thinking about something *other* than the current activity roughly 47% of the time — mind elsewhere, behavior on rails) `[REPLICATED-consistent]`. Two nuances the pop version drops: habitual behaviors coexisted with less stress (the self-regulatory *benefit* of habits — automated behavior conserves control), and habit ≠ absence of thought (behavior ran with minimal *monitoring*). The follow-up that matters for design: Wood, Tam and Witt (2005, *JPSP* 88(6), 918–933) showed **context disruption dissolves habit structure** — transfer students stopped auto-driving to the old campus; behavior re-decomposed into deliberated steps until a new context re-automatized it `[REPLICATED-consistent]`.

**The design theorem.** BU's plays compete for slots in a day that is mostly pre-allocated by existing cue-bound routines. Two strategy families exist, and the evidence splits them cleanly:

1. **Fight the context** — argue for attention against established cues. This is the willpower-reservoir strategy in disguise (Phase 1 `[FAILED]`), now with a neural reason it loses: the incumbent routine runs on cue-triggered striatal brackets that fire *before* deliberation begins (§2.3).
2. **Become/borrow the context** — attach new behavior to stable existing cues (the habit literature's "cue" of cue-routine-reward), or perform change *during* natural context disruptions (moves, new jobs, new terms — when old brackets are already dissolved).

The second is what the evidence supports, and BU's P0 grammar already encodes it: the Phase 1 Play grammar's `When [time/context anchor]` clause is not a stylistic tic; it is the mechanism. What Phase 3 adds is the *system-level* rule: **the app itself must behave like a stable context** — same place in the day, same screen, same meaning of each state — because a variable, surprising interface is itself a context-disruptor for the one routine (the Evening Close) that everything else depends on.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Wood, Quinn & Kashy (2002), JPSP 83(6), 1281–1297 | 43% of daily behaviors habitual (daily + same location); ~47% mind-elsewhere; habits ↔ less stress | `[REPLICATED-consistent]` |
| Wood, Tam & Witt (2005), JPSP 88(6), 918–933 | Context disruption dissolves habit structure until re-automatization | `[REPLICATED-consistent]` |
| Wood & Rünger (2016), Annu. Rev. Psych. 67, 289–311 | Habits as context-response associations; inference for behavior change | review (T1) |

**Application to BU.**

- `[P1]` **D6 — Context-stack rule.** Every play carries its anchor; the daily surface is positionally stable; the fire's state vocabulary never changes meaning under the user. Anchor changes are *events* in the record (logged, because they predict decay, §2.3).
- `[P1]` **Watch what BU competes with, concretely.** The 21:00 close competes with whatever the user's late-evening bracket already is (Wind-down scroll, TV). The Watcher (Phase 4) gets this data; the P0 version just asks once during onboarding what the evening usually looks like — and lets the user pick the anchor rather than assuming one (autonomy + Singh 2024's self-selection determinant).
- `[P2]` **Context disruptions are prime formation windows — and BU should say so.** When the user's life visibly changes (new term, move — self-declared), copy invites (never pushes) re-anchoring: "contexts reset habits. good time to choose yours again." This is Wood 2005 turned into a feature, and it is one of the few places BU may *initiate* a formation conversation.
- `[P3]` **Forge never schedules against an incumbent bracket it can see.** If Life Log (P1 auto-capture) shows a stable 22:00–23:00 scroll block, a 22:30 study play is predicted to fail; Forge moves it or shrinks it and says why ("that hour already belongs to something else — we start smaller, after dinner").

### 2.6 Merzenich's Conditions: Adult Plasticity Is Real, Gated, and Harder to Sell Than to Do

**Mechanism.** The founding demonstrations: adult cortex is not fixed. In owl monkeys, restricted deafferentation or digit manipulation caused somatosensory cortex's digit map to reorganize (Merzenich et al., 1983/1984 lineage) — but the *behaviorally controlled* version is the one that matters for BU. Jenkins, Merzenich, Ochs, Allard and Guíc-Robles (1990, *J. Neurophysiology* 63(1), 82–104) had monkeys perform an attended, repetitive tactile task with digits 2–4 (rotating-disk contact) for about an hour a day over weeks: the cortical territory representing the stimulated digits **expanded substantially and displaced neighboring representations**, with receptive-field sharpening — use, under attention, redraws the adult map `[REPLICATED]`. The audiotory analog: training adult monkeys on a frequency-discrimination task reorganized primary auditory cortex in step with discrimination improvement (Recanzone, Schreiner & Merzenich, 1993, *J. Neurosci.* 13, 87–103 `[verify volume]`).

The gate. Repetition alone was never sufficient. Kilgard & Merzenich (1998, *Science* 279, 1714–1718) paired tones with **nucleus basalis** stimulation in adult rats: episodic pairing drove a massive, progressive reorganization of auditory cortex toward the paired frequencies — the nucleus basalis signal (attention/arousal/modulatory input) acts as the *plasticity gate* that turns mere co-occurrence into map change `[REPLICATED]`. Bao, Chan and Merzenich (2001, *Nature* 412, 79–83) then showed the reward system does the same job: **VTA dopamine** stimulation paired with a tone enlarged and sharpened the cortical representation of that tone. Later work sharpened the caveat: cortical map plasticity *improves* learning but is **not necessary** for it (Reed et al., 2011, *Neuron* 70, 193–200) — so maps are an index of plasticity conditions, not the proof of learning. And repetition is symmetric: monkeys trained in a repetitive grasping task developed **degraded, dedifferentiated** hand representations — a genesis model of repetitive-strain/focal-dystonia (Byl, Merzenich & Jenkins, 1996, *Neurology* 47, 508–520 `[verify pages]`). The cortex trains on whatever is repeated under salience — good or bad.

**The conditions, extracted.** Across the Merzenich-lab line, four conditions recur whenever adult plasticity is demonstrated: (1) **attention/salience** — the input must be attended, not ambient; (2) **reward/modulatory signaling** — a dopamine-class "this matters" signal must accompany the input (Kilgard 1998; Bao 2001); (3) **repetition** — tens to hundreds of trials, distributed (§2.2 of Phase 2's spacing work applies to neural training too); (4) **task engagement** — behaviorally controlled performance, not passive exposure. This is BU's **plasticity checklist**, and it is satisfiable by a 15-minute play: user-chosen (salience/autonomy), performed with attention (single-task — cross-ref Phase 4), closed with immediate feedback (the reward signal, §2.2), repeated across days in a stable context (§2.4–2.5).

**The translation audit — where the commercial record diverges from the lab.** Merzenich co-founded Scientific Learning, whose Fast ForWord product translated the auditory-training line into a shipped intervention (origin: acoustically modified speech training improved language comprehension in language-learning-impaired children — Tallal et al., 1996, *Science* 271, 77–81, with the companion training paper Merzenich et al., 1996, *Science* 271, 77–80 `[verify page ranges]`). The follow-through is the cautionary tale: an independent RCT with **206 children** across four six-week conditions found **all conditions improved, with no advantage** for Fast ForWord Language over active controls on global language outcomes (Gillam et al., 2008, *J. Speech, Language, and Hearing Research* 51(1), 97–119) `[CONTESTED — vendor claims vs RCT]`; a systematic meta-analytic review found the supporting evidence base small and low-quality (Strong, Torgerson, Torgerson & Hulme, 2011 `[verify venue]`). The broader "brain training" genre fared similarly: the largest controlled trial (ACTIVE: 2,802 older adults; Ball et al., 2002, *JAMA* 288, 2271–2281) found durable **trained-task** gains at 10 years (speed-of-processing held; Rebok et al., 2014, *JAMA* 312, 2505–2516), but far-transfer claims rest on secondary analyses (Edwards et al., 2017, dementia-hazard reduction ≈ 29% in speed-trained participants) `[secondary analysis, not load-bearing]`, and a 70+scientist consensus statement rejected far-transfer marketing (Stanford Center on Longevity / Max Planck Institute, 2014 — T4-lead). **BU's rule: mechanisms from this section justify how BU structures its own plays; they never justify "brain training" claims about BU.**

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Jenkins et al. (1990), J. Neurophysiol. 63(1), 82–104 | Attended repetitive use redraws adult S1 digit maps | `[REPLICATED]` |
| Recanzone et al. (1993), J. Neurosci. 13, 87–103 | A1 reorganization tracks discrimination learning | `[REPLICATED]` `[verify vol]` |
| Kilgard & Merzenich (1998), Science 279, 1714–1718 | Nucleus basalis pairing gates cortical remapping | `[REPLICATED]` |
| Bao, Chan & Merzenich (2001), Nature 412, 79–83 | VTA dopamine pairing drives map expansion | `[REPLICATED]` |
| Reed et al. (2011), Neuron 70, 193–200 | Map plasticity helps learning but isn't necessary | `[REPLICATED]` |
| Byl, Merzenich & Jenkins (1996), Neurology 47 | Bad repetition also trains: degraded hand maps | `[REPLICATED]` `[verify pages]` |
| Tallal et al. (1996); Merzenich et al. (1996), Science 271 | Fast ForWord's founding results | origin claims |
| Gillam et al. (2008), JSLHR 51(1), 97–119 | RCT, N=206: no advantage over active controls | `[CONTESTED]` |
| Ball et al. (2002) JAMA 288; Rebok et al. (2014) JAMA 312 | ACTIVE: durable trained-task gains at 10 y | `[REPLICATED, trained-task only]` |
| Edwards et al. (2017), Alzheimer's & Dementia: TRCI | Dementia-hazard reduction (secondary analysis) | `[secondary, EMERGING]` |

**Application to BU.**

- `[P0]` **D7 — The plasticity checklist ships as copy on every study-marked play** (the Phase 2 D1 study-card gains one line): "chosen by you · one thing at a time · closed with an answer · repeated here tomorrow." Four checks, each mapping to a lab condition above. Static text in P0; the guided flow (P2) *enforces* it by construction (single-task timer, immediate close, same anchor).
- `[P0]` **D9 — "Brain training" ban.** BU never claims to train the brain, improve cognition, or prevent decline. The fire's copy speaks about the *day* ("the fire got fed"), never about neurons (§2.1's ban, extended: no "neuroplasticity," no "rewiring," no "cognitive enhancement").
- `[P2]` **Bad repetition gets named.** The watch surfaces (Phase 4) are where users *untrain* attention (Byl's symmetry: salient repetition entrenches whatever it entrenches). Copy for focus plays says the honest thing: "every rep trains something — pick what."
- `[P3]` **Forge's play proposals must pass the checklist programmatically** (user-chosen flag, single-task, close-feedback present, anchor stability) before entering the day's plan; proposals that fail the gate are demoted to suggestions-with-reasons.
- `[P4]` **The exercise link** (§2.9) is the one physiological plasticity lever BU touches — and it is logged, not gamified.

### 2.7 Sleep: The Consolidation Budget, the Deprivation Tax, and the Screen-Off Proxy

**Consolidation — what sleep does for what you did today.** Rasch & Born (2013, *Physiological Reviews* 93(2), 681–766) is the field's synthesis `[REPLICATED]`: new declarative memories are labile until **active systems consolidation** moves them from hippocampal replay to cortical storage, and this transfer is sleep's job. The mechanism has an architecture: slow oscillations of slow-wave sleep nest **thalamocortical sleep spindles** and **hippocampal sharp-wave ripples**, and the ripples replay the day's sequences — compressed, repeated, and coupled to the spindle/oscillation phase. REM sleep's role skews toward emotional and procedural integration. The design-sharpest fact inside the literature is **targeted memory reactivation**: a cue presented during sleep selectively strengthens *the specific memory it was associated with* (Rudoy, Voss, Westerberg & Paller, 2009, *Science* 326, 1426 — sounds cued during slow-wave sleep boosted exactly those object memories `[REPLICATED-consistent]`). Even a short nap carries declarative gains (Tucker et al., 2006, *Neurobiology of Learning and Memory* 86, 241–247 `[verify pages]`). Two design consequences: (1) the evening review matters *because* sleep follows it — the day's last encoding window is preferentially consolidated; (2) "log one gap" (Phase 2 D1) is not just tomorrow's retrieval seed, it is what tonight's replay will pass over. BU does not and should not manipulate sleep (no audio-cuing, ever — P0–P4), but its **Evening Close before sleep** placement is mechanism-aligned, not aesthetic.

**Deprivation — what missing sleep taxes.** Lim & Dinges (2010, *Psychological Bulletin* 136(6), 875–888) meta-analyzed short-term (<48 h) total sleep deprivation across **70 articles, 147 cognitive tests**: effect sizes span from nonsignificant (reasoning accuracy ḡ ≈ −0.125) to large (**attention lapses ḡ ≈ −0.776**), with time-awake the only significant moderator `[REPLICATED]`. The domain-graded profile is the design-relevant fact: sleep loss hits **vigilance and sustained attention** first and hardest; working memory and processing speed moderately; high-load reasoning is comparatively defended (until lapses make it moot). Partial restriction (≤5–6 h/night) produces impairments in the same direction, often on par with short-term total deprivation on vigilance measures. Sleep loss also **disconnects medial-prefrontal regulation from the amygdala**, amplifying emotional reactivity (Yoo, Gujar, Hu, Jolesz & Walker, 2007, *Current Biology* 17(20), R877–R878) — the neuroscience of "everything feels worse when I'm tired," and the reason a low-sleep day gets smaller, easier plays rather than motivational speeches (D3's rule below).

**The pop-book audit.** Matthew Walker's *Why We Sleep* (2017 — T3) popularized sleep science with numbers that do not survive checking: the essay *Why We Sleep Is Riddled With Scientific and Factual Errors* (Guzey, 2019 — T4-lead) documents the unsupported "shorter sleep → shorter life" framing, a misattributed growth-hormone figure, a non-existent WHO "sleep loss epidemic" declaration, and a wrong "two-thirds of adults fail to get recommended sleep" statistic — corrections never systematically rebutted. What *does* survive: mortality risk is **U-shaped** in meta-analysis (Cappuccio et al., 2010, *Sleep* 33(5), 585–592 — both short *and* long sleep associate with higher all-cause mortality `[REPLICATED-consistent]`), and the deprivation-cost meta-analyses above. **BU's rule: sleep claims come from meta-analyses with named effect sizes; the book's rhetoric (and any app copy echoing it) is banned (D9).** BU is not a sleep app; it makes exactly two sleep-dependent decisions — play sizing and pattern language — and those need the meta-analytic numbers, nothing more.

**The screen-off proxy — can a Galaxy A54 estimate sleep honestly?** The validation literature is small but real. Lin et al. (2019, *JMIR mHealth and uHealth* 7(5), e13421) built sleep-time estimation from **screen-on/screen-off patterns** (proactive on/off + notification timing) and reached **~83% agreement** against reference sleep detection in their pilot `[verify denominator — agreement class]`. Lin, Chen, Chuang, Wang, Lin & Lin (2023, *J. Medical Internet Research* 25, e48044; 33 insomnia patients + 33 matched controls, **2,097 person-days**) compared app-defined (smartphone-interaction) sleep onset, wake time, and total sleep time against actigraphy: **no significant differences on the group measures**, and the app-derived circadian metrics captured the clinical insomnia signature `[REPLICATED-consistent, group-level]`. The known failure modes are exactly the ones an honest spec must carry: phone left charging in another room, screen-off ≠ sleep onset (reading in bed), short night wakings invisible. P0 therefore uses **manual sleep check-in** (one line, D3); P1's Watcher may use screen-off only as a *trend* signal with **stated error bounds (±45 min class, person-day level)** — never as a claim about "your sleep quality," and never feeding anything but play-sizing priors and the user's own pattern view (D4's grammar).

**The math of the P0 prior.** Let s = last night's self-reported sleep in hours (one line: hours + a 1–5 "rested" tap). Play-size prior scales as:

```
size_prior(play) = base_size · f(s) ,  f(s) = 1.0 for s ≥ 7h
                                        0.75 for 5–7h
                                        0.5  for s < 5h
```

The prior **suggests**, never forces (Phase 1 autonomy); the user can dismiss it in one tap; and the copy says why ("short sleep hits sustained attention hardest — we start smaller"). This is Lim & Dinges's domain-graded profile translated into one product rule.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Rasch & Born (2013), Physiol. Reviews 93(2), 681–766 | Systems consolidation: SO–spindle–ripple replay architecture | `[REPLICATED]` |
| Rudoy et al. (2009), Science 326, 1426 | Sleep-cued memory reactivation is memory-specific | `[REPLICATED-consistent]` |
| Tucker et al. (2006), Neurobiol. Learn. Mem. 86 | Short naps carry declarative gains | `[verify pages]` |
| Lim & Dinges (2010), Psych. Bulletin 136(6), 875–888 | SD costs domain-graded: ḡ −0.78 lapses → −0.13 reasoning | `[REPLICATED]` |
| Yoo et al. (2007), Current Biology 17(20), R877–R878 | Sleep loss → amygdala hyperreactivity, PFC disconnect | `[REPLICATED-consistent]` |
| Cappuccio et al. (2010), Sleep 33(5), 585–592 | Mortality U-shape (short AND long sleep) | `[REPLICATED-consistent]` |
| Lin et al. (2019), JMIR mHealth 7(5), e13421 | Screen-based sleep estimation ≈ 83% agreement | `[verify class]` |
| Lin et al. (2023), JMIR 25, e48044 | App vs actigraphy: no significant differences (2,097 person-days) | `[REPLICATED-consistent]` |
| Guzey (2019), guzey.com/books/why-we-sleep | Walker error audit | T4-lead |
| Ball et al. 2002 / Rebok et al. 2014 (ACTIVE) | (cross-ref §2.6) durability of trained-task gains | — |

**Application to BU.**

- `[P0]` **D3 — Sleep-aware Evening Close.** One optional line in Evening Close v2: "hours slept last night + one rested-tap." It does three things immediately: (a) sizes tomorrow's play prior per the f(s) rule; (b) seeds the P3 sleep→pattern evidence base; (c) gives the user's bad day a *physical* cause to attribute — attribution research says a controllable, temporary cause beats a character verdict (Phase 1's relight copy rules).
- `[P0]` **Copy rule (D9): no sleep-scolding.** "Short sleep hits attention hardest — we start smaller" is the register. No "sleep is the foundation of discipline," no sleep-hygiene lectures, no 5-hour-panic numbers from pop books.
- `[P1]` **Screen-off enters only as trend, with bounds.** The Watcher's sleep proxy is labeled with its ±45-min-class error; it adjusts priors and shows the user their own nights; it never gates features or grades the fire.
- `[P3]` **D4 — Forge sleep→pattern claims use causal-humble grammar:** "on nights you logged <6 h, you completed ~X% of plays vs ~Y% otherwise" (observational, shown as the user's own counts, N-stamped when small). Never "you're burning out," never health claims.
- `[P4]` **Constellation may surface long-run sleep–performance arcs** with the same grammar — multi-year correlation displays are honest precisely because they show association, in the user's own data, with sample sizes visible.

### 2.8 Stress Moves Control to Habits: Exam-Season Design

**Mechanism.** Schwabe & Wolf (2009, *Journal of Neuroscience* 29(22), 7191–7198) stressed human participants (socially evaluated cold-pressor test) **before** instrumental training: stressed participants' later choices became **insensitive to outcome devaluation** — they kept choosing actions for rewards that had been devalued — i.e., stress shifted control from the goal-directed system to the habit system `[REPLICATED-consistent]`. The stress-before-learning timing matters (stress *after* learning affects retrieval/consolidation along a different axis — Yerkes-Dvorsky-type inverted-U effects on memory expression), and follow-ups localized the shift to reduced involvement of medial-prefrontal/ventromedial control with increased dorsal-striatal contribution (review: Schwabe & Wolf, 2013, *Current Directions in Psychological Science* `[verify volume]`; see also Schwabe, Tegenthoff, Höffken & Wolf, 2012, *J. Neurosci.*, simultaneous DLPFC–DLS recordings showing the balance flip). The wanting connection from §2.2 completes the picture: stress also amplifies cue-triggered wanting (Berridge & Robinson, 2016) — the stressed user is pushed toward *incumbent* cue-driven behavior from both ends (habits more automatic, cravings louder).

**The design consequence is counterintuitive and load-bearing:** in high-stress windows, **routines protect performance more than plans do.** The goal-directed system is the thing stress degrades; the habit system is comparatively stress-resistant (it does not need outcome value to run). A student with a routinized evening block runs it during exam week; a student who improvises "depends on how I feel" has delegated exam week to the most stressed system in the brain. BU's job in declared stress windows is therefore to *conserve structure*, not to add motivation: hold anchors constant, keep plays small and familiar, suspend novelty (no new interleaving schemes, no new play types, no re-anchoring experiments), and let the fire's feedback get quieter rather than louder (stress amplifies reactivity to social-evaluative-feeling signals; D8 mutes).

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Schwabe & Wolf (2009), J. Neurosci. 29(22), 7191–7198 | Stress before training → devaluation-insensitive choices in humans | `[REPLICATED-consistent]` |
| Schwabe et al. (2012), J. Neurosci. | PFC/DLS balance flips under stress (simultaneous recordings) | `[REPLICATED-consistent]` `[verify cite]` |
| Schwabe & Wolf (2013), Curr. Dir. Psych. Sci. | Review: stress and the multiple-memory-systems switch | review `[verify vol]` |
| Berridge & Robinson (2016), Am. Psychologist 71, 670–679 | Stress amplifies cue-triggered wanting | `[REPLICATED]` |

**Application to BU.**

- `[P0]` **D8 seed — the optional stress line.** Evening Close carries one optional question (user-configurable, default on during self-declared exam/deadline windows): "pressure today? high / normal." P0 does nothing with it except record — but the record is what powers D8 later, and the act of flagging stress is itself a (weak) regulation lever.
- `[P2]` **D8 — Stress-window mode (declared).** When the user declares a stress window ("exams Jun 3–14"), the ladder logic inverts: plays shrink toward the floor (Phase 1's low-energy sizing), anchors are held exactly (no re-anchoring), novelty is suspended (no new play types), and fire feedback mutes (fewer celebratory states; information-only copy). Exit is user-controlled. The science says this *raises* expected completion precisely when completion matters most.
- `[P3]` **Forge learns the signature.** From P0–P2 data, Forge can *propose* stress windows when it sees the user's own pattern (completion drops + sleep drops + evening-time shifts) — as a question, never a switch ("rough week coming? want me to hold the routine?").
- `[P0]` **Copy rule (D9): no "stress is good/bad for you" talk.** The honest line: "under pressure, routines carry you when plans can't. keep the shape, shrink the size."

### 2.9 Exercise as a Plasticity Lever: The One Physiology Link BU Logs but Never Sells

**Mechanism.** The strongest causal evidence that exercise changes brain structure relevant to cognition is Erickson et al. (2011, *PNAS* 108(7), 3017–3022): a **1-year RCT in 120 older adults** (aerobic walking vs. stretching control, 40 min × 3/week) found aerobic training **increased anterior hippocampal volume by ~2%**, effectively reversing 1–2 years of age-related loss, with **serum BDNF increases** mediating the volume effect and **spatial memory** improving; caudate and thalamus were unaffected — the plasticity is structure-specific `[REPLICATED-consistent]`. The mechanism family is sketched by Cotman & Berchtold (2002, *Trends in Neurosciences* 25, 295–301): exercise upregulates BDNF (a synapse-consolidation factor — cf. §2.1's growth processes), among other neurotrophic pathways. Boundaries, honestly: the flagship RCT is in **older adults**, on **volume + spatial memory**, at a **1-year dose**; acute single-session effects on learning are smaller, mixed, and timing-dependent; effects on young-adult discipline or habit formation are not established by this literature `[CONTESTED scope]`. Meta-analytic support for exercise benefit on executive function exists (e.g., the Erickson-adjacent literature; a full executive-function meta is Phase 4/6 debt if the Watcher ever needs it).

**The decision.** For BU, exercise sits exactly where the master plan's P4 tag implied: a real biological lever with (a) an evidence base built on a different population than the likely user, (b) no immediate product surface where it helps, and (c) high gamification risk (exercise rewards collide with Phase 1's extrinsic-motivation findings and with §2.2's cue-discipline). **D5: abstain from nudging.** BU logs user-declared exercise (P4, optional Life-Log entry), lets it inform *recovery copy* ("big run today — tomorrow's plays start smaller"), and never attaches reward, streaks, or fire-brightening to exercise volume. If the builder's own N=1 data later shows declared-exercise days predict completion (a plausible, testable association — OQ5), the *only* escalation permitted is copy, not mechanics.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Erickson et al. (2011), PNAS 108(7), 3017–3022 | 1-y aerobic RCT (N=120, older adults): +2% anterior hippocampus, BDNF-linked, spatial memory ↑ | `[REPLICATED-consistent]` |
| Cotman & Berchtold (2002), Trends Neurosci. 25, 295–301 | BDNF as exercise's plasticity mediator | review (T1) |

**Application to BU.**

- `[P4]` **D5 — Exercise-nudge decision: abstain.** No exercise nudges, no exercise rewards, no "movement streaks." Passive user-declared logging + recovery copy only. Rationale recorded above; revisit only if Phase 6's N=1 data shows a strong, replicated-in-user association.
- `[P4]` **Recovery copy is the permitted surface.** "Long day, big run — start with the one-line play" is SDT-clean (information, not evaluation).

## 3 · Design Decisions

Every decision: Decision / Evidence / Confidence / Tag. P0 decisions are the build's homework for the 4-week Spark phase.

| # | Decision | Evidence | Confidence | Tag |
|---|---|---|---|---|
| D1 | **Fire reward-timing rules:** brightness feedback computes at the confirming act, delivered ≤2 s; magnitude scales with prediction error vs. the user's running expectation, clipped to a fixed band; fully-expected outcomes get acknowledgment, never celebration; feedback vocabulary rotates slowly and never fabricates variance (no fake randomness in the signal itself); badge/sparkle economy banned | §2.2 (Schultz 1997 RPE; Waelti 2001 blocking; Berridge & Robinson 2016 cue-amplification; hedonic adaptation T3) | High | P1 |
| D2 | **Automaticity estimate → scaffolding-fade rule:** per-play asymptotic fit `a(t) = A(1 − e^(−t/τ))` with forgetting factor (estimate decays after context breaks); hint/prompt density scales down as `h ∝ (1 − a/A*)` across bands (<30% full → 30–70% card-only → >70% silent); Evening Close never fades; estimator shown in plain words, never as a grindable % | §2.3 (Yin & Knowlton 2006; Jog 1999; Barnes 2005; Smith & Graybiel 2013 bracketing↔automaticity), §2.4 (Lally 2010 curves) | High (circuit) / Medium (thresholds) | P1 |
| D3 | **Sleep-aware Evening Close (P0):** one optional line — hours slept + rested-tap — sizes tomorrow's play prior `f(s)`: 1.0 / 0.75 / 0.5 at ≥7h / 5–7h / <5h; suggest-only, one-tap dismiss; copy names the mechanism in one clause ("short sleep hits attention hardest") | §2.7 (Lim & Dinges 2010 domain-graded costs; Yoo 2007; Rasch & Born 2013 evening-before-sleep placement) | High (costs) / Medium (thresholds) | P0 |
| D4 | **Forge sleep→pattern grammar (causal-humble):** observational sentences over the user's own counts ("on nights you logged <6h, ~X% vs ~Y% completion"), N-stamped when small; no health claims, no "burnout" language, no sleep-quality scores; screen-off proxy (P1) feeds priors and trend views only, labeled ±45-min-class error | §2.7 (Lin 2019 83%; Lin 2023 2,097 person-days; Cappuccio 2010 U-shape as the honest shape) | High (grammar) / Medium (proxy) | P3 |
| D5 | **Exercise-nudge decision: abstain.** No exercise nudges/rewards/streaks ever; P4 optional user-declared exercise log informs recovery copy only ("big run — start with the one-line play"); revisit only on Phase 6 N=1 evidence | §2.9 (Erickson 2011 scope: older adults, 1-y dose; Cotman & Berchtold 2002), §2.2 (cue discipline), Phase 1 (SDT) | High (decision) / Medium (mechanism transfer) | P4 |
| D6 | **Context-stack rule:** every play carries a user-chosen anchor; daily surface positionally stable (same time, same screen, same state meanings); anchor changes logged as events; onboarding asks what the evening already looks like and lets the user pick the close's anchor; declared life-changes open a re-anchoring invitation (never a push) | §2.5 (Wood, Quinn & Kashy 2002 43%; Wood, Tam & Witt 2005), §2.4 (Singh 2024 self-selection determinant), Phase 1 D1 (grammar) | High | P0/P1 |
| D7 | **Plasticity checklist on every study-marked play:** "chosen by you · one thing at a time · closed with an answer · repeated here tomorrow" — static copy in P0; enforced by construction in the P2 guided flow (single-task timer, immediate close, stable anchor); Forge proposals must pass the checklist programmatically before entering the day (P3) | §2.6 (Jenkins 1990; Kilgard & Merzenich 1998; Bao 2001 gating conditions) | High (conditions) / Medium (form) | P0/P2/P3 |
| D8 | **Stress-window mode (declared):** P0 = optional "pressure today? high/normal" line recorded in Evening Close; P2 = declared windows hold anchors exactly, shrink plays to the floor, suspend novelty (no new play types, no re-anchoring), and mute fire feedback to information-only; exit user-controlled; P3 = Forge may *propose* a window from the user's own pattern, as a question | §2.8 (Schwabe & Wolf 2009 devaluation-insensitivity; stress-resistant habit system) | High (effect) / Medium (copy) | P0 seed / P2 |
| D9 | **Copy ban-list extension (joins Phase 1 D7/D18, Phase 2 D3):** no "neurons that fire together" phrasing, no "neuroplasticity/rewiring/brain training/cognitive enhancement" claims, no "21 days," no countdown rings or "habit complete," no dopamine-talk ("your brain rewards you"), no sleep-scolding or pop-book numbers, no stress verdicts | §2.1, §2.4 (Maltz origin; Gardner, Lally & Wardle 2012), §2.6 (Gillam 2008; consensus statement), §2.7 (Guzey audit; meta-only rule) | High | P0 |
| D10 | **Omission = information, paired with the smallest relight step:** a missed close dims the fire by the mirror of its brightening delta and, in the same screen, offers the one-line relight play; chronic-omission weeks trigger smaller plays, never louder prompts (helplessness guard; Phase 1 D5 copy rules govern tone) | §2.2 (RPE omission signal), §2.8 (stress-reactivity bound), Phase 1 (self-forgiveness evidence) | High (mechanism) / Medium (UX) | P1 |

**v6-doc amendments produced by this phase** (change log for `PLAN.md`): (1) the v0 draft's neuroscience sourcing (content farms, magazine piece, JWT-laden PDF link) is replaced wholesale by this phase's tables; (2) the BTSP citation line is corrected — primary: Bittner, Milstein, Grienberger, Romani & Magee (2017, *Science* 357, 1033–1036); the *Cell Reports* 2021 paper is the bidirectionality follow-up, not the primary; (3) any "21 days"-class framing in v6 materials is replaced by distribution language (median ~2 months, range 18–254 in the best-known study; 4–335 across 20 studies); (4) sleep claims on all BU surfaces are scoped to meta-analytic sources — Why-We-Sleep-class rhetoric is excluded; (5) the "43% of behavior is habit" claim is stated in its precise form (43% same-location-almost-daily definition, hourly diary, 279 students, with ~41% and ~47% companion figures); (6) "the play is in the body" is operationalized as the three-part test in §2.3 (cue-initiated, outcome-insensitive, bracketed) rather than a metaphor.

## 4 · Contradiction Cards

**Card A — Variable-reward engineering (dopamine science) vs. autonomy and honesty (Phase 1).** The RPE literature (§2.2) says variable, surprise-scaled feedback teaches; Phase 1's SDT audit says controlling, manipulative design backfires, and Phase 1's honesty brand forbids deception. Variable rewards sit one design error away from a slot machine — and the fire is a cue (§2.2), so compulsion risk is real, not theoretical. *Working resolution:* variability lives in **information**, never in **judgment or fabrication** — what surprises is which ember resurfaces (FSRS-class randomness, Phase 2 D6), which gap got hit, what the day actually fed; the *meaning* of every state is deterministic, documented, and user-visible. Brightness variance is capped, omission is mirrored, and no state exists whose purpose is to be missed (no loss-framed lottery). *Flip condition:* if the builder's N=1 logs show fire-checking frequency rising while Evening-Close tending stays flat (checking without tending — the compulsive signature), all variance except the deterministic evidence signal is removed in the next build, and the change is logged as a v6 amendment.

**Card B — The app that must fade vs. the app that must survive.** Neuroscience says success means needing the scaffolding less (§2.3 fade rule), while every business instinct — and some of v6's engagement language — wants the opposite. *Working resolution:* BU's product thesis (v6) is that the *fire* is the durable surface and the scaffolding is disposable; the fade rule is scoped so that only scaffolding fades (hints, cards, reminders), while the chosen ritual (Evening Close) and the fire's record persist for years. Engagement metrics must therefore never measure prompt-interaction, or the fade rule and the metrics will fight (Phase 6 owns what gets measured). *Flip condition:* if tending collapses as hints fade (not just hint-interaction), the estimator's plateau calibration is wrong — retune A*, don't un-fade.

**Card C — Sleep as data vs. sleep as scope creep.** Sleep is the highest-leverage state variable BU can read (§2.7), and the same section shows what happens to apps that adopt sleep-science rhetoric: they inherit pop-book errors and become health advisors they are not qualified to be. *Working resolution:* one manual line in P0, one bounded proxy in P1, one grammar rule in P3 — nothing else. No sleep-hygiene content, no quality scores, no clinical language, no long-sleep panic (the U-shape says long sleep isn't a virtue either). BU's sleep surface is four sentences long, forever. *Flip condition:* none — scope is invariant; only the evidence base behind the two decisions it feeds can update.

**Card D — One experience can change a memory; one evening cannot change a habit.** BTSP (§2.1) says single events can enter hippocampal storage in seconds; the habit literature (§2.3–2.4) says dispositions take months of repetition. A user who journal-logs a breakthrough night and still fails the play next week is not experiencing a contradiction — but an app that doesn't know the difference will either over-promise (journal as transformation) or under-value it (journal as diary). *Working resolution:* BU's copy and mechanics keep the two timescales in separate lanes with separate expectations: the journal *records* (fast system; one entry can matter for years as evidence), the fire *trains* (slow system; nothing happens in one evening, by design). Where they touch is allowed and honest: a single entry can seed an ember (fast→slow bridge), and slow-system evidence can make an entry worth writing (slow→fast). *Flip condition:* none — this is the architecture, not a tunable.

## 5 · Open Questions + N=1 Experiment Queue

Single-case designs formalized in Phase 6; seeded here so the builder can start during P0.

- **OQ1 · Does the sleep check-in predict anything?** The D3 prior assumes Lim & Dinges's domain costs transfer to play completion. Test: weekly logistic association between reported sleep (<6.5h vs ≥7h) and next-day play completion, from P0 data. If the association is absent by week 8, the prior is decoration — cut or reword (the line itself may still stay for attribution value; that call is logged). [P0-testable]
- **OQ2 · Surprise-scaled fire feedback vs. fixed feedback.** Does D1's delta-scaled brightening hold tending rates better than a fixed animation over 6 weeks (ABAB on alternating weeks, P1 data)? Look specifically for tolerance (fixed feedback decaying into wallpaper) vs. compulsive checking (Card A's signature). [P1-testable]
- **OQ3 · Estimator calibration.** Do D2's fitted plateaus agree with the user's monthly 4-item automaticity self-check (SRBAI-class)? Mismatch direction matters: over-estimation → premature silence; under-estimation → nagging past automatization. [P1/P2-testable]
- **OQ4 · Stress-window routine mode.** During self-declared exam weeks, does D8 (anchors held, plays at floor, novelty suspended) beat the user's own non-declared stressed weeks on completion and Evening-Close rate? N=1 paired comparison. [P2-testable]
- **OQ5 · Declared exercise and next-day completion.** Association between user-declared exercise days and next-day play completion (§2.9's permitted escalation check). If a stable positive association exists, the only permitted change is recovery copy; if absent, the log line stays voluntary and dumb. [P4-testable, P0-data if the builder logs it manually]

**Research debt (verified-number gaps for the next pass):** Jenkins et al. 1990 exact expansion magnitude (report vs. zone fractions); Recanzone et al. 1993 volume/pages; Byl et al. 1996 exact pages; Strong et al. 2011 exact venue + pooled effect; Gillam et al. 2008 full condition labels + effect sizes; Tallal/Merzenich 1996 exact page ranges; Edwards et al. 2017 exact hazard ratio + session-dose split; Schultz 2016 exact pagination; Schwabe & Wolf 2013 volume/pages; Schwabe et al. 2012 full citation; Gardner, Lally & Wardle 2012 exact pages; Kaushal & Rhodes 2015 exact median/weeks; Smith & Graybiel 2013 exact session counts for bracketing onset; Lin et al. 2019 accuracy metric class (agreement of what exactly, against what reference); Milstein et al. 2021 volume/article number; Tucker et al. 2006 pages; Wood, Quinn & Kashy 2002 per-study N split (70/209 confirmation) and exact 43/41/47 percentages from the paper itself.

## 6 · Sources

**T1/T2 — opened or verified this pass.**

- Hebb, D. O. (1949). *The Organization of Behavior*. Wiley. (primary text, T3-class book; load-bearing quote from p. 62)
- Bliss, T. V. P., & Lømo, T. (1973). Long-lasting potentiation of synaptic transmission in the dentate area of the anaesthetized rabbit following stimulation of the perforant path. *J. Physiology*, 232, 331–356.
- Collingridge, G. L., Kehl, S. J., & McLennan, H. (1983). *J. Physiology*, 334, 33–46. (NMDA dependence of LTP induction)
- Dudek, S. M., & Bear, M. F. (1992). Homosynaptic long-term depression in area CA1 of hippocampus. *PNAS*, 89, 4363–4367.
- Oja, E. (1982). A simplified neuron model as a principal component analyzer. *J. Mathematical Biology*, 15, 267–280.
- Bienenstock, E. L., Cooper, L. N., & Munro, P. W. (1982). Theory for the development of neuron selectivity. *J. Neuroscience*, 2, 32–48.
- Markram, H., Lübke, J., Frotscher, M., & Sakmann, B. (1997). Regulation of synaptic efficacy by coincidence of postsynaptic APs and EPSPs. *Science*, 275, 213–215.
- Bi, G.-q., & Poo, M.-m. (1998). Synaptic modifications in cultured hippocampal neurons: dependence on spike timing. *J. Neuroscience*, 18, 10464–10472.
- Bi, G., & Poo, M. (2001). Synaptic modification by correlated activity: Hebb's postulate revisited. *Physiological Reviews*, 81, 1545–1565.
- Feldman, D. E. (2012). The spike-timing dependence of plasticity. *Neuron*, 75(4), 556–571. pmc.ncbi.nlm.nih.gov/articles/PMC3431193
- **Bittner, K. C., Milstein, A. D., Grienberger, C., Romani, S., & Magee, J. C. (2017). Behavioral time scale synaptic plasticity underlies CA1 place fields. *Science*, 357(6355), 1033–1036.** doi 10.1126/science.aan3846 — corrects the master plan's citation line
- Milstein, A. D., et al. (2021). Bidirectional synaptic plasticity rapidly modifies hippocampal representational structure. *Cell Reports*. `[vol/article verify]`
- Schultz, W., Dayan, P., & Montague, P. R. (1997). A neural substrate of prediction and reward. *Science*, 275(5306), 1593–1599.
- Montague, P. R., Dayan, P., & Sejnowski, T. J. (1996). A framework for mesencephalic dopamine systems based on predictive Hebbian learning. *J. Neuroscience*, 16, 1936–1947.
- Sutton, R. S., & Barto, A. G. (1981). Toward a modern theory of adaptive networks: expectation and prediction. *Psychological Review*, 88, 135–170.
- Waelti, P., Dickinson, A., & Schultz, W. (2001). Dopamine responses comply with basic assumptions of formal learning theory. *Nature*, 412, 43–48.
- Glimcher, P. W. (2011). The dopamine reward prediction error hypothesis. *Annual Review of Neuroscience*, 34, 225–258. pmc.ncbi.nlm.nih.gov/articles/PMC3176615
- Berridge, K. C., & Robinson, T. E. (1998). What is the role of dopamine in reward: hedonic impact, reward learning, or incentive salience? *Brain Research Reviews*, 28, 309–369.
- Berridge, K. C., & Robinson, T. E. (2016). Liking, wanting and the incentive-sensitization theory of addiction. *American Psychologist*, 71(8), 670–679. pmc.ncbi.nlm.nih.gov/articles/PMC5171207
- Yin, H. H., & Knowlton, B. J. (2006). The role of the basal ganglia in habit formation. *Nature Reviews Neuroscience*, 7(6), 464–476.
- Yin, H. H., Knowlton, B. J., & Balleine, B. W. (2004). Lesions of dorsolateral striatum preserve outcome expectancy but disrupt habit formation in instrumental learning. *European J. Neuroscience*, 19, 181–189.
- Jog, M. S., Kubota, Y., Connolly, C. I., Hillegaart, V., & Graybiel, A. M. (1999). Building neural representations of habits. *Science*, 286, 1745–1749.
- Barnes, T. D., Kubota, Y., Hu, D., Jin, D. Z., & Graybiel, A. M. (2005). Activity of striatal neurons reflects dynamic encoding and recoding of procedural memories. *Nature*, 437, 1158–1161.
- Smith, K. S., & Graybiel, A. M. (2013). A dual operator view of habitual behavior reflecting cortical and striatal dynamics. *Neuron*, 79(2), 361–374.
- Graybiel, A. M. (2008). Habits, rituals, and the evaluative brain. *Annual Review of Neuroscience*, 31, 359–387.
- Wood, W., & Rünger, D. (2016). Psychology of habit. *Annual Review of Psychology*, 67, 289–311.
- Lally, P., van Jaarsveld, C. H. M., Potts, H. W. W., & Wardle, J. (2010). How are habits formed: modelling habit formation in the real world. *European J. Social Psychology*, 40(6), 998–1009.
- Singh, B., Murphy, A., Maher, C., & Smith, A. E. (2024). Time to form a habit: a systematic review and meta-analysis of health behaviour habit formation and its determinants. *Healthcare*, 12(23), 2488. doi 10.3390/healthcare12232488 · pmc.ncbi.nlm.nih.gov/articles/PMC11641623
- Gardner, B., Lally, P., & Wardle, J. (2012). Making health habitual: the psychology of "habit-formation" and general practice. *British J. General Practice*, 62(604), 664–666. `[pages verify]`
- Kaushal, N., & Rhodes, R. E. (2015). Exercise habit formation in new gym members: a longitudinal study. *J. Health Psychology*, 20(6), 705–714. `[verify]`
- Wood, W., Quinn, J. M., & Kashy, D. A. (2002). Habits in everyday life: thought, emotion, and action. *JPSP*, 83(6), 1281–1297.
- Wood, W., Tam, L., & Witt, M. G. (2005). Changing circumstances, disrupting habits. *JPSP*, 88(6), 918–933.
- Jenkins, W. M., Merzenich, M. M., Ochs, M. T., Allard, T., & Guíc-Robles, E. (1990). Functional reorganization of primary somatosensory cortex in adult owl monkeys after behaviorally controlled tactile stimulation. *J. Neurophysiology*, 63(1), 82–104. pubmed.ncbi.nlm.nih.gov/2299388
- Recanzone, G. H., Schreiner, C. E., & Merzenich, M. M. (1993). Plasticity in the frequency representation of primary auditory cortex following discrimination training in adult owl monkeys. *J. Neuroscience*, 13, 87–103. `[verify vol]`
- Kilgard, M. P., & Merzenich, M. M. (1998). Cortical map reorganization enabled by nucleus basalis activity. *Science*, 279(5357), 1714–1718. pubmed.ncbi.nlm.nih.gov/9497289
- Bao, S., Chan, V. T., & Merzenich, M. M. (2001). Cortical remodelling induced by activity of ventral tegmental dopamine neurons. *Nature*, 412(6842), 79–83. pubmed.ncbi.nlm.nih.gov/11452310
- Reed, A., et al. (2011). Cortical map plasticity improves learning but is not necessary for improved learning. *Neuron*, 70, 193–200.
- Byl, N. N., Merzenich, M. M., & Jenkins, W. M. (1996). A primate genesis model of focal dystonia and repetitive strain injury: I. *Neurology*, 47, 508–520. `[verify pages]`
- Tallal, P., Merzenich, M. M., Miller, S., & Jenkins, W. (1996). Language comprehension in language-learning impaired children improved with acoustically modified speech. *Science*, 271, 77–81.
- Gillam, R. B., et al. (2008). The efficacy of Fast ForWord Language intervention in school-age children with language impairment: a randomized controlled trial. *J. Speech, Language, and Hearing Research*, 51(1), 97–119. pubmed.ncbi.nlm.nih.gov/18230858
- Ball, K., et al. (2002). Effects of cognitive training interventions with older adults: a randomized controlled trial. *JAMA*, 288, 2271–2281.
- Rebok, G. W., et al. (2014). Ten-year effects of the ACTIVE cognitive training trial. *JAMA*, 312, 2505–2516.
- Edwards, J. D., et al. (2017). Speed of processing training results in lower risk of dementia. *Alzheimer's & Dementia: TRCI*, 3, 603–611. `[HR verify]`
- Rasch, B., & Born, J. (2013). About sleep's role in memory. *Physiological Reviews*, 93(2), 681–766.
- Rudoy, J. D., Voss, J. L., Westerberg, C. E., & Paller, K. A. (2009). Strengthening individual memories by reactivating them during sleep. *Science*, 326, 1426.
- Tucker, M. A., et al. (2006). A daytime nap containing solely non-REM sleep enhances declarative but not procedural memory. *Neurobiology of Learning and Memory*, 86, 241–247. `[verify pages]`
- Lim, J., & Dinges, D. F. (2010). A meta-analysis of the impact of short-term sleep deprivation on cognitive variables. *Psychological Bulletin*, 136(6), 875–888. pmc.ncbi.nlm.nih.gov/articles/PMC3290659
- Yoo, S.-S., Gujar, N., Hu, P., Jolesz, F. A., & Walker, M. P. (2007). The human emotional brain without sleep — a prefrontal amygdala disconnect. *Current Biology*, 17(20), R877–R878.
- Cappuccio, F. P., D'Elia, L., Strazzullo, P., & Miller, M. A. (2010). Sleep duration and all-cause mortality: a systematic review and meta-analysis. *Sleep*, 33(5), 585–592.
- Lin, Y.-H., et al. (2019). Validation of the mobile app-recorded circadian rhythm by a digital footprint. *JMIR mHealth and uHealth*, 7(5), e13421.
- Lin, C., Chen, I.-M., Chuang, H.-H., Wang, Z.-W., Lin, H.-H., & Lin, Y.-H. (2023). Examining human-smartphone interaction as a proxy for circadian rhythm in patients with insomnia. *J. Medical Internet Research*, 25, e48044. doi 10.2196/48044
- Schwabe, L., & Wolf, O. T. (2009). Stress prompts habit behavior in humans. *J. Neuroscience*, 29(22), 7191–7198. jneurosci.org/content/29/22/7191
- Erickson, K. I., Voss, M. W., Prakash, R. S., et al. (2011). Exercise training increases size of hippocampus and improves memory. *PNAS*, 108(7), 3017–3022. doi 10.1073/pnas.1015950108 · pmc.ncbi.nlm.nih.gov/articles/PMC3041121
- Cotman, C. W., & Berchtold, N. C. (2002). Exercise: a behavioral intervention to enhance brain health and plasticity. *Trends in Neurosciences*, 25(6), 295–301.
- Posner, M. I., & Petersen, S. E. (1990). The attention system of the human brain. *Annual Review of Neuroscience*, 13, 25–42. (bridge to Phase 4)
- Smallwood, J., & Schooler, J. W. (2006). The restless mind. *Psychological Bulletin*, 132(6), 946–958. (bridge to Phase 4)

**T3 — context only.** Merzenich (2013), *Soft-Wired* (the founder's own narrative; commercial claims in it are excluded per §2.6's audit); Maltz (1960), *Psycho-Cybernetics* (the 21-day myth's traceable origin); Walker (2017), *Why We Sleep* (context only; load-bearing numbers excluded — §2.7 audit); Ferster & Skinner (1957), *Schedules of Reinforcement* (variable-ratio persistence background for Card A); Frederick & Loewenstein (1999), "Hedonic adaptation," in *Well-Being: The Foundations of Hedonic Psychology*; Gleick (1992) not cited here (Feynman was Phase 2).

**T4 — leads, not evidence.** Guzey (2019), *Matthew Walker's "Why We Sleep" Is Riddled With Scientific and Factual Errors* (guzey.com — the audit essay; every load-bearing correction cross-checked against the meta-analytic record before use here); Stanford Center on Longevity / Max Planck Institute (2014), brain-training consensus statement (signatory count as reported; used only as the history of the far-transfer rejection); FitCraft/Keelify habit-formation explainers and Lumen Learning's habits module (used as leads to Lally/Singh/Wood primary sources; no number from them enters this document unverified); Scientific Learning's scilearn.com product pages (vendor claims, cited only as the claim under audit).

**Cross-references.** Phase 1: fire feedback rules (D1/D10) sit inside the copy-classes and SDT constraints of Phase 1 D4/D5; the sleep check-in joins Evening Close v2 (Phase 1 D2); D9 extends the ban-list (Phase 1 D7/D18). Phase 2: D2's fade rule implements Phase 2 D10's promise with the estimator Phase 2 requested; the plasticity checklist (D7) rides the study-play card (Phase 2 D1); "log one gap" gets its consolidation rationale (§2.7). Phase 4: attention/single-task conditions in the checklist are Phase 4's to operationalize; the 43% context world is the Watcher's home turf; Posner/Smallwood bridges are pre-positioned. Phase 5: variable-reward caps (D1) bound any future ladder mechanics; stress-window mode (D8) constrains intervention timing under load. Phase 6: the sleep check-in's validation (OQ1), estimator calibration (OQ3), and all N-stamped grammars (D4) are Phase 6's measurement designs. Phase 7: homeostatic budgeting (§2.1), RPE-scaled brightness (D1), and the fast/slow timescale lanes (Card D) are seeds for the fire's final scoring function.

---

*End of Phase 3. Tracker updated in `RESEARCH-PLAN.md` Appendix C. Next: "Research Phase 4" produces `phase4-attention.md` — the Watcher's thresholds: a defensible drift definition, the true cost of interruption, when nudge ladders work vs. provoke reactance, chronotype vs. morning defaults, and BU's own notification discipline.*




