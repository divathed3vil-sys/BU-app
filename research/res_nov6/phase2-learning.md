# BU Research · Phase 2 — Learning & Memory: The Science of the Play

**Version** 1.0 · **Date** 2026-11-05 · **Status** Delivered — awaiting builder review
**Program** `research/RESEARCH-PLAN.md` v1.0 (Phase 2 of 7)
**Scope held** ~60% science / ~40% BU design implications · replication flags on load-bearing claims · P0-first bias · every "Application to BU" block tagged P0–P4 · Feynman held to technique-only scope
**Owner** Dhivakaran · reviewed by any LLM session that reads `MEMORY/` first

---

## 1 · TL;DR — The Ten Verdicts

1. **Retrieval practice is the strongest learning lever this program has found.** Classroom-scale meta-analysis: quizzing boosts achievement g ≈ 0.50 (48,478 students) `[REPLICATED]`; of the ten techniques in the field's benchmark utility review, only *practice testing* and *distributed practice* earn "high utility." A study play is a recall session, not a reading session. This is a P0 design fact.
2. **Rereading, highlighting, and summarization are low-utility — and students love them.** Preference tracks fluency, not learning. BU must not build reward surfaces for re-consumption, and must warn against them in one calm line, not a lecture.
3. **Forgetting is not the textbook exponential.** Empirical retention curves flatten (power-law-ish), and the optimal review gap scales with the retention horizon: ≈ 20–40% of a 1-week horizon down to ≈ 5–10% of a 1-year horizon (Cepeda et al. 2008, N > 1,350). Fixed "review weekly" rules are wrong by construction; intervals must be computed from a horizon.
4. **The Forge inherits a real memory model, not vibes.** SM-2 (1987) defines the lineage: an ease factor multiplying intervals, no probability. FSRS-6 (open-source, 21 trained parameters) models Difficulty, Stability, Retrievability, predicts recall probability, and beats SM-2 on the public benchmark. Verdict: adopt an FSRS-class model for ember resurfacing at P3, with parameters pinned to a tagged release; a SM-2-class fixed rule is the P0/P1 stopgap.
5. **Interleaving beats blocked practice where it counts** — the same problems, rearranged, moved a one-day test from 38% to 72% (d ≈ 1.05) and survived a 1-month delayed test `[REPLICATED]`. Multi-subject weeks interleave; brand-new skills may block within a single session.
6. **The right time to retrieve is when recall is *hard*** — FSRS's own fitted law says stability gain grows as retrievability falls, and the desirable-difficulty literature puts the productive success band roughly at 75–90% (the "85% rule" is single-paper `[EMERGING]`). Difficulty is the mechanism, not a design failure — but it must be wrapped in Phase 1's no-shame copy rules.
7. **Feelings misreport memory.** Fluency illusions and biased judgments of learning are among the most replicated results in metacognition; that is why BU grades *evidence*, never confidence, and why the Evening Close asks what you actually recalled, not how it felt.
8. **The Feynman Technique is folk-named.** The first known use of the name is a September 2011 blog post by Scott Young; Feynman's own documented practice is Gleick's "NOTEBOOK OF THINGS I DON'T KNOW ABOUT" reorganization. Its four steps decompose into mechanisms with real evidence — self-explanation, generation, retrieval, simplification-as-elaboration. Feynman-mode journal ships as a guided self-explanation flow `[P2]`, honestly credited.
9. **Guided beats blank.** Unassisted discovery *underperforms* explicit instruction (d ≈ −.38); guided discovery with scaffolding or elicited explanation *outperforms* other instruction (d ≈ +.50/+ .36) `[REPLICATED]`. BU's resolution: the page stays blank; guidance lives at the edges (study-play card, Evening Close micro-prompt, opt-in Feynman mode).
10. **Deliberate practice is real but over-sold.** Practice explains a *minority*, domain-dependent fraction of performance variance (roughly 26% games, 21% music, 18% sports, 4% education, <1% professions in the meta-analytic audit `[CONTESTED numbers — verify]`). BU promises the mechanism (effortful, feedback-rich, repeated practice) and never the magnitude ("10,000 hours" language is banned).

**P0 haul (what this phase changes in the 4-week build):** a study-play structure card (recall-first recipe) shipped inside the Play's hint surface (D1) · one calibration micro-prompt added to Evening Close v2 (D2) · copy rules: no learning-styles talk, no hours-logged framing, one-line rereading warning (D3, D11) · journal surfaces confirmed as evidence surfaces with edge-guidance placement (D4). No AI required; all P0 items are copy and structure.

---

## 2 · Science

### 2.1 What Actually Works: The Utility Audit and the Retrieval Effect

**Mechanism.** Retrieval practice works because the act of pulling a memory out re-encodes it: each successful retrieval reconstructs and strengthens the retrieval route, and the *difficulty* of the retrieval is diagnostic of how far the route had decayed — reconstructing from a partly-decayed state produces more gain than re-exposure to a complete trace. This is why testing is not measurement but an intervention: the test *is* the study event. The complementary mechanism is forward testing — a prior retrieval makes future study of the same material deeper, and even failed retrieval attempts followed by feedback beat studying without the attempt (the pretesting effect; Kornell, Hays and Bjork, 2009, *JEP: Learning, Memory, and Cognition* 35(4), 983–1003 `[REPLICATED, effect-size verify]`).

**The evidence.** The field's benchmark utility review (Dunlosky, Rawson, Marsh, Nathan and Willingham, 2013, *Psychological Science in the Public Interest* 14(1), 4–58, doi 10.1177/1529100612453266) rated ten techniques across desired-difficulty, breadth-of-use, and durability criteria. Only two earned **high utility**: practice testing and distributed practice (the latter is §2.2). **Moderate:** interleaved practice, elaborative interrogation, self-explanation. **Low:** rereading, highlighting, summarization, keyword mnemonic, imagery-for-text. The low ratings are the interesting half — all three "low" techniques are the ones students report using most, which is the metacognitive problem in §2.5.

Retrieval's size, in the strongest designs available:

- **Classroom scale:** Yang, Luo, Vadillo, Yu and Shanks (2021, *Psychological Bulletin* 147(4), 399–435) systematically reviewed and meta-analyzed classroom quizzing — g ≈ 0.499 on achievement across 48,478 students `[REPLICATED]`. Effect holds with feedback and without; larger when quizzes were low-stakes.
- **Lab scale:** Rowland (2014, *Psychological Bulletin* 140(6), 1439–1463) — testing vs. restudy g ≈ 0.50 across 695 effect sizes `[verify exact k/N at next pass]`. Effects are larger when retrieval is success-with-feedback rather than failure-without.
- **Against an active control:** Karpicke and Blunt (2011, *Science* 331(6018), 772–775) showed retrieval practice beat *elaborative concept mapping* — an active, constructive control, not a passive one — on both verbatim and inference questions one week later `[REPLICATED]`. This closes the "it's just deep processing" objection: retrieval is doing something concept-mapping is not.
- **The rereading null:** Callender and McDaniel (2009, *JEP: Learning, Memory, and Cognition* 35(1), 82–95) found rereading produced negligible gains on realistic educational materials across multiple experiments `[REPLICATED]`.

**The math of a retrieval-first session.** A study play that maximizes durable gain per minute has a fixed skeleton, derived directly from the mechanisms: (1) **close the book** — recall everything possible *from memory first* (free recall dump; this is both retrieval practice and the pretesting effect working on what you half-know); (2) **check against source** — the gap between dump and source is the actual study content, which is now *error-focused* rather than re-read wholesale; (3) **re-retrieve** — close again and re-recall the gaps at least once (the repeated-retrieval requirement from Karpicke and Roediger, 2008, *Science* 319(5865), 966–968: dropping items after first successful recall produces steep subsequent forgetting `[REPLICATED]`); (4) **log one gap** as tomorrow's seed (feeds the ember system, §2.3). Time-split for a 45-minute play: roughly 20 dump / 10 check / 10 re-retrieve / 5 log. The proportions are set-and-testable, not evidence-locked; the *order* is evidence-locked.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Dunlosky et al. (2013), PSPI 14(1), 4–58 | Ten-technique utility table: only practice testing + distributed practice = high utility | `[REPLICATED]` |
| Yang et al. (2021), Psych Bulletin 147(4), 399–435 | Classroom quizzing → achievement, g ≈ 0.499, 48,478 students | `[REPLICATED]` |
| Rowland (2014), Psych Bulletin 140(6), 1439–1463 | Testing vs restudy g ≈ 0.50 (lab) | `[verify k/N]` |
| Karpicke & Blunt (2011), Science 331, 772–775 | Retrieval beats elaborative concept mapping | `[REPLICATED]` |
| Karpicke & Roediger (2008), Science 319, 966–968 | Repeated retrieval required; recall-once-then-drop → forgetting | `[REPLICATED]` |
| Kornell, Hays & Bjork (2009), JEP:LMC 35(4), 983–1003 | Pretesting: failed attempts + feedback beat no attempt | `[REPLICATED, es verify]` |
| Callender & McDaniel (2009), JEP:LMC 35(1), 82–95 | Rereading ≈ null on realistic materials | `[REPLICATED]` |

**Application to BU.**

- `[P0]` **D1 — Study-play structure card.** The Play's hint surface carries the four-step recipe (dump → check → re-retrieve → log-a-gap) with the time split above, phrased in BU's plain register: "close the book. write what you remember. now check. that gap is your study." Lite ships this as static copy on any play the user marks as study.
- `[P0]` **D11 — The rereading warning is one line, not a module.** "Reading it again feels like studying. Testing yourself *is* studying." — placed in the study-play card only. No behavior-policing: the journal remains write-only.
- `[P0]` **The blank-page evening journal already satisfies retrieval's form** for the day's learning (recall from memory, unaided). This is why the v6 instinct survives the utility table: the journal is a free-recall event. What P0 adds is the *study-play* card so the same mechanism covers coursework, not just the day's reflection.
- `[P2]` **Self-graded recall becomes an evidence grade.** When voice journal lands, a study play can end with one prompt: "say what you recalled." The gap between dump and source, self-graded, is the ember's first difficulty signal (feeds §2.3's model). Grade = USER_CONFIRMED until P1 makes it OBSERVED (Phase 6 owns grade criteria).
- `[P3]` **Forge never proposes a "review notes" play.** Computed plays for learning targets are recall-shaped ("45 min: blank page, Kinematics — recall + check"), because that is what the evidence supports and because the grammar (Phase 1 D1) forces a concrete action verb anyway.

### 2.2 Forgetting and the Spacing Function: From Ebbinghaus to a Schedulable Curve

**Mechanism.** Memory decays after each encounter, and a *spaced* repetition — one that arrives after partial forgetting — produces more durable memory than an immediately repeated one, at every timescale ever tested from seconds to years. Three classical mechanism families compete and probably co-occur: **study-phase retrieval** (the second presentation retrieves the first, and that retrieval act strengthens both — a retrieval-practice mechanism, tying §2.1 and §2.2 together), **encoding variability** (spaced events are encoded in more varied contexts, giving the trace more retrieval routes), and **deficient processing** (massed repetition is skimmed because it feels already-known). Cepeda et al. (2006, *Psychological Bulletin* 132(3), 354–380) — 254 studies, ~14,000 participants — concluded no single account explains the full pattern; all three survive as partial mechanisms `[REPLICATED, mechanism plural]`. For BU the pluralism is fine: the *function* is what gets implemented, and its parameters are fit, not assumed.

**The math.** Ebbinghaus (1885) introduced the savings method and the first forgetting curve. Two mathematical families describe retention R(t) over delay t: the **exponential** `R = exp(−t/τ)` and the **power law** `R = (1+t/τ)^(−α)`-class. The empirical verdict across pooled datasets (Wixted and Ebbesen, 1991, *Psychological Review* 98(3), 404–415; Rubin and Wenzel, 1996, *Psychological Review* 103(4), 734–760, 105 datasets; Wixted, 2004, *Annual Review of Psychology* 55, 235–269): power-family curves fit systematically better than exponentials — pooled memory is a mixture of traces of different strengths, and a mixture of exponentials is itself power-like `[REPLICATED]`. Ebbinghaus's own curve replicated cleanly more than a century later (Murre and Dros, 2015, *PLoS ONE*, direct replication with the original method) `[REPLICATED]`.

The design-relevant shape fact: exponential curves *halve at a constant delay* (constant half-life), so a fixed schedule ("review every 7 days forever") would be optimal if forgetting were exponential and stable. Power curves *flatten* — the older the memory, the slower the loss — so optimal intervals must *expand*. This is the mathematical reason every good scheduler (SM-2 onward) multiplies intervals by a growing factor, and why BU's ember intervals must expand with demonstrated stability.

**The optimal gap.** Cepeda et al. (2008, *Psychological Science* 19(11), 1095–1102, doi 10.1111/j.1467-9280.2008.02209.x) mapped the gap-to-test-delay function directly: >1,350 participants, up to 3.5-month gaps, final tests up to 1 year later. At any test delay, performance rises then falls as the gap grows — a temporal ridgeline. The optimal gap **increases in absolute terms** with test delay but **declines as a proportion of it**: roughly 20–40% of a 1-week horizon, 5–10% of a 1-year horizon. Practical consequence for any "how soon should BU bring this back?" decision: if the user needs material *next week*, first revisit ≈ 1–2 days later; if the material is for a life-scale horizon (the ember system's premise), revisit ≈ 1–2 weeks after first learning, then expand.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Ebbinghaus (1885/1913), *Memory* | Savings method; first forgetting curve | foundational |
| Murre & Dros (2015), PLoS ONE | Direct replication of Ebbinghaus's method | `[REPLICATED]` |
| Wixted & Ebbesen (1991), Psych Review 98(3), 404–415 | Power functions fit retention better than exponential | `[REPLICATED]` |
| Rubin & Wenzel (1996), Psych Review 103(4), 734–760 | 105 datasets: power-family best fit | `[REPLICATED]` |
| Cepeda et al. (2006), Psych Bulletin 132(3), 354–380 | 254 studies: spaced > massed; mechanisms plural | `[REPLICATED]` |
| Cepeda et al. (2008), Psych Science 19(11), 1095–1102 | Optimal gap ≈ 20–40% (1 wk) → 5–10% (1 yr) of retention horizon | `[REPLICATED]` |

**Application to BU.**

- `[P0]` **P0's stopgap revisit rule is horizon-derived, not "weekly."** Lite has no model, so the study-play card's "log one gap" line seeds tomorrow's play — a 1-day gap, which is inside the optimal band for the only horizon a daily journal can serve. This is the honest P0 approximation of the ridgeline: fixed-and-correct-at-one-horizon beats a fake adaptivity.
- `[P1]` **Computed play scheduling must never use fixed multiples forever.** When Forge v1 (P1) first schedules anything, intervals expand by construction (any SM-2-class rule qualifies as the floor). Fixed-spacing "resurface every Sunday" features are banned by the curve's shape.
- `[P3]` **Ember intervals are fit to the user's horizon.** The resurfacing engine (D6, §2.3) parameterizes the target retention and horizon per ember class (exam-driven ember: 1–2-week horizon; life-knowledge ember: year-scale). The Cepeda ridgeline is the sanity bound on whatever the model outputs.
- `[P3]` **Forgetting-curve claims in Forge speech stay shape-humble.** "Your Kinematics ember is due — last touched 9 days ago" (computable, shown), never "you've forgotten 63% of it" (a precision the model does not earn; grammar rules from Phase 6).
- `[P4]` **Long-horizon arcs use the year-scale band.** Spiral mapping (D9) revisits foundational embers on the 5–10% band: months apart, each time at higher complexity (§2.9 Bruner).

### 2.3 The Scheduler Family: SM-2, FSRS, and What the Forge Should Adopt

**SM-2 — the ancestor (Wozniak, 1990, *Optimization of Learning*, M.Sc. thesis, University of Technology in Poznań; algorithm shipped December 1987 in SuperMemo).** Each card carries an easiness factor `EF`, initialized `EF₀ = 2.5`, clamped `EF ≥ 1.3`. After each review, the user grades recall `q ∈ {0..5}`; the next interval is:

```
I(1) = 1 day
I(2) = 6 days
I(n) = I(n−1) · EF
EF′  = EF + (0.1 − d·(0.08 + 0.02·d)),  where d = 5 − q     [q < 3 → restart at I(1), EF unchanged]
```

SM-2's virtues: trivial to implement, robust, explainable, and still competitive for its age. Its structural limit, exactly stated: **it outputs a date, not a probability.** Nothing in SM-2 answers "how likely is recall right now?", so the schedule cannot be tuned to a target retention, cannot be fit to a user's data, and cannot tell the app how confident to be about a claim like "this ember is remembered." Anki's everyday default remained a modified SM-2 for 35 years.

**FSRS — the current open-source state of the art** (Free Spaced Repetition Scheduler; Jarrett Ye and the open-spaced-repetition community, first integrated into Anki 23.10). Academic lineage: Ye, Su and Cao (2022, *Proc. 28th ACM SIGKDD*, 4381–4390, doi 10.1145/3534678.3539081 — stochastic-shortest-path scheduling on the MaiMemo dataset) and Su, Ye, Nie, Cao and Chen (2023, *IEEE TKDE*, doi 10.1109/TKDE.2023.3251721 — the DSR/FSRS memory-dynamics paper). *Note: the master plan's placeholder said "ACM TKDD"; the correct venue for the 2023 paper is IEEE TKDE — corrected here.*

**The three-component model.** Each item carries:

- **Retrievability R(t, S)** — probability the item is recallable now, after t days. FSRS-6 uses a *trainable-decay power curve* (power family, per §2.2's empirical verdict):

```
R(t, S) = (1 + factor · t/S)^(−w20),   factor = 0.9^(−1/w20) − 1,   default w20 ≈ 0.154
```

- **Stability S** — defined as the time for R to fall from 100% to 90% (so `I(0.9, S) = S`: a stability-9-day card is 90% recallable 9 days later). Interval selection inverts the curve: `I(r, S) = S · (r^(−1/w20) − 1)/factor` for desired retention r.
- **Difficulty D ∈ [1, 10]** — initialized from the first grade `D₀(G) = w4 − e^(w5·(G−1)) + 1`, updated with linear damping toward mean reversion: `ΔD = −w6·(G−3)`, `D′ = D + ΔD·(10−D)/9`, `D″ = w7·D₀(4) + (1−w7)·D′`.

On each review, stability multiplies: `S′ = S · SInc`, with the fitted laws (from the algorithm's own documentation and the benchmark team's technical write-ups — T4-for-numbers, consistent with the TKDE paper's structure):

- `SInc ≥ 1` always on successful recall — **stability never decreases on success**;
- higher D ⇒ smaller SInc — hard material compounds slower;
- higher S ⇒ smaller SInc — stability saturates (diminishing returns at long horizons);
- **lower R at review time ⇒ larger SInc — the best time to retrieve is when recall is hard**, provided it succeeds (desirable difficulty, formalized: §2.4).

After a lapse, stability resets to a lower value driven by D and R (`S′ = w11·D^(−w12)·((S+1)^w13 − 1)·e^(w14·(1−R))`-class). **FSRS-6** (2025) added the trainable curve decay `w20` (per-user curve flatness) and an improved same-day-review update `S′ = S·e^(w17·(G−3+w18)·S^(−w19))`; default weights w0…w20 are published in the wiki and pinned per release. **Parameter churn is real** — FSRS-4.5 has 17 parameters, FSRS-5 has 19, FSRS-6 has 21 — which is why BU's build rule is *pin to a tagged release* (D6).

**The benchmark.** The open srs-benchmark (open-spaced-repetition/srs-benchmark, GitHub) evaluates algorithms on ~10,000 Anki users' histories (~727 million reviews; an earlier release covered ~20,000 users / ~1.7B reviews) with time-series splits and three calibration metrics (log loss, RMSE-bins, AUC). Read from the repo this pass: FSRS (default and per-user-fitted) outperforms SM-2 and Anki's modified SM-2 on log loss by roughly a factor of ~2 (SM-2 ≈ 0.72 raw / 0.62 Anki-modified vs FSRS ≈ 0.38 default / 0.36 fitted — read from the repo README and secondary summaries; **the table updates continuously: verify the current table before quoting externally**) `[T4-code-docs, direction robust]`. Independent academic support that ML-fitted schedulers beat fixed heuristics: Upadhyay, Lancashire, Moser and Gomez-Rodriguez (2021, *npj Science of Learning* 6, doi 10.1038/s41539-021-00105-8 — large-scale randomized experiments) `[REPLICATED-consistent]`.

**Key sources.**

| Source | Finding | Flag |
|---|---|---|
| Wozniak (1990), *Optimization of Learning*, Poznań | SM-2: EF/interval rules as quoted above | foundational |
| Ye, Su & Cao (2022), KDD, 4381–4390 | Stochastic-shortest-path scheduler (MaiMemo) | `[EMERGING→REPLICATED-consistent]` |
| Su, Ye, Nie, Cao & Chen (2023), IEEE TKDE | DSR memory dynamics → FSRS model class | `[EMERGING→REPLICATED-consistent]` |
| open-spaced-repetition/srs-benchmark (GitHub) | FSRS < SM-2 log loss ~2× on 10k-user corpus | `[T4-code-docs; verify current table]` |
| Upadhyay et al. (2021), npj Sci. Learning | RCTs: ML-fitted instruction beats fixed scheduling | `[REPLICATED-consistent]` |

**Application to BU.**

- `[P3]` **D6 — the Forge's ember resurfacing engine is FSRS-class.** Embers (journal entries, logged gaps, played subjects) carry (D, S, R). Resurface when predicted R hits the target (default 0.90). The engine is small — the state is two floats and a timestamp per ember — and fits a Galaxy A54 with no AI inference.
- `[P1]` **P1 ships the *state*, P3 ships the *scheduler*.** Forge v1 stores (last-touched, stability seed) and shows "due" by the P0 stopgap rule; the model only starts *deciding* at P3. This sequencing keeps Lite honest (manual, predictable) while the data needed to fit personal parameters accumulates silently.
- `[P0]` **User-owned overrides are a feature of the spec, not an escape hatch.** Any ember can be pinned ("keep coming back daily until I say stop") or deferred. The scheduler proposes; the user disposes (SDT continuity with Phase 1 §2.3).
- `[P3]` **R and S are shown, in plain units.** "You've held this for 3 weeks" (S ≈ 21) is the honest display of a fitted quantity. Hidden optimization would make BU an unreliable environment (Phase 1 §2.2's reliability principle).
- `[P4]` **Parameter fitting is per-user, opt-in, on-device.** The benchmark shows fitting helps; the privacy architecture (Phase 6) forbids shipping review history to fit it. Pin defaults until on-device fitting is built and benchmarked.
- `[P0]` **No fake precision before P3.** Lite may say "logged — seeds tomorrow's play"; it may not display a made-up "87% remembered" number. Calibration of displayed probabilities is owned by Phase 6's conformal spec; BU earns percentages only when it can score them.

### 2.4 Difficulty Is Desirable: Interleaving, Effort, and the Success Band

**Interleaving.** In blocked practice, every problem in a set uses the same strategy (you know the method before you read the problem). In interleaved practice, no two consecutive problems share a strategy, forcing *strategy selection* — the step real tests actually examine — and incidentally spacing each problem type (Rohrer, 2012, *Educational Psychology Review* 24, 355–367, "Interleaving helps students distinguish among similar concepts"). The classroom evidence: Rohrer, Dedrick and Burgess (2014, *Psychonomic Bulletin & Review* 21(6), 1323–1330) — 7th-graders, same problems, rearranged: interleaved 72% vs blocked 38% on a test one day later, d ≈ 1.05 `[REPLICATED]`. Rohrer, Dedrick and Stershic (2015, *Journal of Educational Psychology* 107(3), 712–720) extended it with a 3-month schedule and both a 1-day and a 1-month test; interleaved practice won on both, with the 1-month delayed test around 61% vs 51% (approx.; verify exact N/scores at next pass) `[REPLICATED]`. Meta-analytically, interleaving helps most for *confusable categories* — mathematics problem types, artwork attribution, taxonomy — and can be null-to-negative for non-confusable material (vocabulary; Brunmair and Richter, 2019, *Educational Psychology Review* 31, 683–741, g ≈ 0.4 overall with wide moderators `[verify g at next pass]`). Hausman and Kornell (2014, *JARMAC* 3, 153–160) show interleaving alone (mixing topics without confusability) does not enhance learning — the mechanism is discrimination, not mere variety.

**The success band.** How hard should retrieval be during a play? Four converging lines set the band:

1. **Retrieval-effort hypothesis** (Pyc and Rawson, 2009, *Psychological Science* 20(8), 1015–1022): more effortful *successful* retrievals produce larger stability gains `[REPLICATED-consistent]` — hard-but-successful beats easy-and-successful.
2. **FSRS's fitted law** (§2.3): stability gain increases as R at review time falls — the scheduler's own optimum is "just before you'd forget."
3. **The 85% rule** (Wilson, Shenhav, Straccia and Cohen, 2019, *Nature Communications* 10, 4646): across learning architectures, training accuracy ≈ 85% maximizes learning rate `[EMERGING — single paper, theory-driven]`.
4. **Region of proximal learning** (Metcalfe's program): learners gain most per unit time from material just beyond current mastery — the empirically-tested descendant of Vygotsky's ZPD (§2.9) `[REPLICATED-consistent]`.

Synthesis for BU: during a play, the user should **mostly succeed, but not comfortably** — a rough success band of 75–90%, with failures answered by feedback within the same session (pretesting effect, §2.1). Below ~75% success, the play is sized down or the material split; above ~90% (it's all easy), the play should add interleaving, complexity, or spacing — or the ember graduates.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Rohrer, Dedrick & Burgess (2014), Psych Bull Rev 21(6), 1323–1330 | Interleaved 72% vs blocked 38%, d ≈ 1.05 (1-day test) | `[REPLICATED]` |
| Rohrer, Dedrick & Stershic (2015), JEP 107(3), 712–720 | Effect survives at 1 month (≈61% vs ≈51%) | `[REPLICATED, scores approx]` |
| Brunmair & Richter (2019), Ed Psych Review 31, 683–741 | Meta: g ≈ 0.4 overall; confusable-material moderator | `[verify g]` |
| Hausman & Kornell (2014), JARMAC 3, 153–160 | Mixing without confusability ≈ null | `[REPLICATED-consistent]` |
| Pyc & Rawson (2009), Psych Science 20(8), 1015–1022 | Retrieval effort → retention gain | `[REPLICATED-consistent]` |
| Wilson et al. (2019), Nat Communications 10, 4646 | 85% training-accuracy optimum | `[EMERGING]` |

**Application to BU.**

- `[P2]` **D8 — the week is interleaved, the session may be blocked.** When the user runs multiple subjects, plays rotate across subjects through the week (that is interleaving *between* topics with confusable problem types — the case Rohrer's evidence covers). Within one play for a brand-new skill, blocked repetition is legitimate (the crutch is useful before the first success; Rohrer's own framing).
- `[P3]` **D7 — ZPD difficulty targeting, computable.** The Forge sizes computed plays to keep predicted success in-band: `size′ = size · band_adjust(historical_success_rate)`, shrinking after failures (guarding competence — Phase 1's SDT rules), stretching only after a demonstrated easy streak. The band is 75–90% until the builder's N=1 data (OQ-queue) says otherwise.
- `[P0]` **Failure is framed as information from day one.** The study-play card's check step treats gaps found as "the study, located" — the pretesting effect turned into copy. This is also Phase 1's D8 (effort+strategy praise) applied at the moment difficulty peaks.
- `[P2]` **Feynman-mode and voice plays make difficulty visible honestly.** A fumbled explanation is logged as a gap-ember, never as a "failed play." The evidence grade records what happened; the copy records what it means ("found the edge — that's the work").
- `[P4]` **Ember graduation rule.** When an ember's stability exceeds the horizon (R stays above target across two scheduled visits), it graduates out of the active queue — the spiral moves on (§2.9). Graduation is visible; nothing silently disappears.

### 2.5 Metacognitive Illusions: Why BU Grades Evidence, Not Feelings

**Mechanism.** Judgments of learning (JOLs) are inferred from *cue fluency* — how smoothly material processes right now — and fluency is a terrible proxy for durable memory: rereading makes text feel smoother while producing negligible retention (§2.1), highlighting makes important lines feel mastered while isolating them from their arguments, and recognition ("I'd know it if I saw it") masquerades as recall (you can't recognize your way through an exam or a conversation). The classics: Jacoby, Kelley and Brown (1989) on fluency attributions; Simon and Bjork (2001, *JEP:LMC* 27(1), 90–103) — learners' JOLs *inverted* the true effect of practice schedule (they predicted best retention from the condition that produced worst); Rhodes and Castel (2008, *JEP:LMC* 34(4), 915–924) — larger font inflates JOLs with zero memory benefit `[REPLICATED-consistent cluster]`. Karpicke, Butler and Roediger (2009, *Memory* 17(4), 471–479) surveyed students' actual strategies: rereading dominates; retrieval practice is rare and students cannot predict its advantage `[REPLICATED]`.

**The calibration move.** Two reliable correctives exist. First, **retrieval-based JOLs**: judgments made *after attempting recall* track actual performance far better than judgments made during reading (Dunlosky and Nelson's delayed-JOL work; the general principle is robust) `[REPLICATED-consistent]`. Second, **externalizing**: writing the explanation forces the fluent feeling to produce actual words, which either come or don't — this is one reason §2.6's technique works at all. The general design law: *any surface that lets a user report a feeling about memory will collect miscalibration; any surface that makes them produce a sample of memory collects evidence.*

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Jacoby, Kelley & Brown (1989) | Fluency → attribution of knowing | foundational |
| Simon & Bjork (2001), JEP:LMC 27(1), 90–103 | JOLs invert actual practice-schedule effects | `[REPLICATED-consistent]` |
| Rhodes & Castel (2008), JEP:LMC 34(4), 915–924 | Font-size illusion: JOLs shift, memory doesn't | `[REPLICATED-consistent]` |
| Karpicke, Butler & Roediger (2009), Memory 17(4), 471–479 | Students reread; can't predict testing's advantage | `[REPLICATED]` |

**Application to BU.**

- `[P0]` **D2 — the Evening Close calibration micro-prompt.** Evening Close v2 (Phase 1 D2: "did it / partial / no") gains one optional line on study days: "what did you recall today without looking?" — a retrieval-based JOL in disguise. The answer (or its absence) becomes part of the day's ember record. This is the cheapest high-value P0 item in this phase.
- `[P0]` **BU never asks "how confident are you?" as a graded input.** Confidence prompts collect fluency, not knowledge; the fire must not weight them. The journal's blank page and the study-play card both sample *output*, which is the only honest currency.
- `[P1]` **The journal is an evidence surface, structurally.** Entries are timestamped, tied to the play they belong to, and never rewritten by the app (write-only page, v6 invariant). This is what makes later calibration possible: the entry is the *sample* the Forge's future checks can be graded against.
- `[P3]` **Forge calibration checks are retrieval-shaped.** When the Forge resurfaces an ember, the check is "restate it / work one," and the *result* — not the user's confidence — updates (D, S). Phase 6's conformal machinery wraps the prediction claims.
- `[P0]` **No "mastered" badges.** A fluency-labeled state would institutionalize the illusion. The closest BU gets is the Gorgeous gate (30 verified days), which is a record of *tendings*, not a claim about memory.

### 2.6 The Feynman Technique: Provenance Audit and Verified Mechanisms

**Provenance, stated precisely.** Richard Feynman never named, described, or taught a "Feynman Technique." The technique's name first appears — as far as documented tracing this pass could establish — in a September 1, 2011 blog post, "Learn Faster with the Feynman Technique," by Scott H. Young (scotthyoung.com/blog/2011/09/01/learn-faster), who has consistently said it was *inspired loosely* by an account of Feynman's study habits. The account behind it is real and specific: James Gleick's biography *Genius* (1992) describes Feynman preparing for his oral qualifying examination at MIT by opening a fresh notebook titled — on Feynman's own title page — **"NOTEBOOK OF THINGS I DON'T KNOW ABOUT"**, then spending weeks disassembling each branch of physics, "oiling the parts, and putting them back together, looking all the while for the raw edges and inconsistencies." That is the honest core: a gap-first, reorganization-from-memory practice in the physicist's own notebooks (Feynman's Calculus notebook; see also his own accounts of working things out from scratch). The popular four steps — (1) write the concept's name; (2) explain it in plain language as if teaching a child, from memory; (3) locate where the explanation breaks, return to the source; (4) simplify further and build analogies — are the blog-era codification, stabilized in derivative sources (fs.blog's adaptation is representative). **Verdict for BU: teach the technique, cite the mechanisms, never claim the man.** The app's copy may say "an explain-it-plainly loop" — "the Feynman Technique" may appear once, attributed honestly ("named after him; the name is folk").

**The mechanisms underneath each step** — every step of the folk technique is a verified intervention wearing a costume:

1. **Step 2 (explain from memory, simply) = retrieval practice + self-explanation.** Chi, de Leeuw, Chiu and LaVancher (1994, *Cognitive Science* 18(4), 439–477): students prompted to self-explain a biology text gained substantially more than reading twice — self-explanation surfaces gaps and repairs the mental model `[REPLICATED]`. Meta-analytically, prompting self-explanation yields a small-to-moderate pooled effect (Bisra, Liu, Nesbit, Salimi and Winne, 2018, *Educational Psychology Review* 30, 703–725; g ≈ 0.5-class `[verify g at next pass]`). Generation itself adds a memory bonus over reading (the generation effect; Slamecka and Graf, 1978, *JEP:HLM* 4(6), 592–604; meta Bertsch et al. 2007 `[REPLICATED]`).
2. **Step 3 (find where it breaks, go back) = error-focused study + pretesting.** The fumbled half of the explanation is the diagnostic; returning to the source *only for the gap* is error-focused re-study, which §2.1's evidence says is where re-exposure actually pays.
3. **Step 4 (simplify, analogize) = elaboration + dual coding.** Building an analogy forces relational structure (elaborative interrogation's mechanism), and a sketchable analogy adds a second representational channel (Paivio's dual coding, 1986 — T3 foundational; Mayer's multimedia work inherits the logic).
4. **The whole loop = spaced re-describing.** Each pass is a retrieval event on the concept; resurfacing it across days (§2.2) compounds it.

**Key sources.**

| Source | Finding | Flag |
|---|---|---|
| Gleick (1992), *Genius* (T3) | The notebook passage; Feynman's actual practice | provenance anchor |
| Young (2011, blog; 2019, *Ultralearning* — T3/T4) | First known use of the technique's name; 4-step form | provenance anchor |
| Chi, de Leeuw, Chiu & LaVancher (1994), Cognitive Science 18(4), 439–477 | Elicited self-explanation → deeper understanding | `[REPLICATED]` |
| Bisra et al. (2018), Ed Psych Review 30, 703–725 | Self-explanation prompt meta | `[verify g]` |
| Slamecka & Graf (1978), JEP:HLM 4(6), 592–604 | Generation effect | `[REPLICATED]` |
| Paivio (1986), *Mental Representations* (T3) | Dual coding | foundational |

**Application to BU.**

- `[P2]` **D5 — Feynman-mode journal flow.** A guided flow on the write surface (and voice surface once P2 ships it): *name it → explain it plainly, from memory → mark where it broke → one-line analogy.* Four prompts, optional, never auto-fired; the output is an ordinary journal entry (evidence-grade USER_CONFIRMED). The flow is guidance at the edge — the page itself stays blank (§2.7's resolution).
- `[P0]` **One P0 sentence seeds the habit early.** The study-play card carries: "stuck? explain it to nobody, out loud, in plain words. the stuck part is the study." — self-explanation's mechanism in BU's register, zero UI cost.
- `[P2]` **Gap-embers are Feynman-mode's output format.** The "where it broke" line becomes a logged ember with elevated priority in the resurfacing queue — the technique's step 3 becomes a data structure.
- `[P3]` **Forge credits mechanisms, not mystique.** When the Forge suggests an explain-plainly play, it may say why in one clause ("explaining it plainly is retrieval + self-explanation — both verified") — model vocabulary, no hero-worship copy.
- `[P0]` **Attribution hygiene (copy ban-list extension).** "The Feynman Technique" never appears as a feature name in Lite. It is folk-named; BU's honesty brand (Phase 1 D18's spirit) cannot lean on a borrowed label.

### 2.7 Blank Page vs. Guided Journal: The Discovery Verdict

**Mechanism.** The constructivist instinct behind "just give them a blank page" has been tested at meta-analytic scale, twice over. Alfieri, Brooks, Aldrich and Tenenbaum (2011, *Journal of Educational Psychology* 103(1), 1–18) ran two meta-analyses (164 studies). First: **unassisted discovery underperforms explicit instruction, d ≈ −.38** — pure discovery is worse than being told. Second: **enhanced discovery outperforms other instruction, d ≈ +.30** — and the breakdown is exactly what BU needs: guided discovery with scaffolding or worked examples, d ≈ +.50; **elicited explanation** (make the learner explain their own ideas, with timely feedback on accuracy), d ≈ +.36; raw generation of rules without support, d ≈ −.15, not significant. Lazonder and Harmsen (2016, *Review of Educational Research* 86(3), 681–708) — 72 studies — converge: guidance *within* inquiry improves outcomes, d ≈ 0.50 `[REPLICATED]`. Furtak, Seidel, Iverson and Briggs (2012, *Review of Educational Research* 82(3), 300–329) — 37 studies — report mean effects ≈ 0.5 with teacher-guided conditions larger `[REPLICATED-consistent]`.

The mechanism story is working-memory-bounded: unguided discovery spends the learner's scarce attention on search and error rather than on the to-be-learned structure (Mayer, 2004, "Should there be a three-strikes rule against pure discovery learning?" *American Psychologist* 59(1), 14–19). Guidance does not mean telling — it means *the structure of the task carries the load the learner can't yet carry*: prompts, feedback on accuracy, worked examples, scaffolds that fade (§2.9's contingency rule).

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Alfieri et al. (2011), JEP 103(1), 1–18 | Unassisted discovery d ≈ −.38; enhanced d ≈ +.30; guided d ≈ +.50; elicited explanation d ≈ +.36; generation alone ns | `[REPLICATED]` |
| Lazonder & Harmsen (2016), RER 86(3), 681–708 | Guidance within inquiry d ≈ 0.50 (72 studies) | `[REPLICATED]` |
| Furtak et al. (2012), RER 82(3), 300–329 | Guided inquiry ≈ 0.5; teacher-led larger | `[REPLICATED-consistent]` |
| Mayer (2004), Am Psych 59(1), 14–19 | Why pure discovery fails: working-memory load | `[REPLICATED-consistent]` |

**Application to BU.**

- `[P0]` **D4 — the blank page stays; guidance lives at the edges.** The v6 write-only journal is untouched as a *surface* (the blank page is also a privacy and honesty statement). But the three edge surfaces — the study-play card (§2.1), the Evening Close micro-prompt (§2.5), and later Feynman-mode (§2.6) — are where Alfieri's "elicited explanation" lives. This is the formal resolution of the contradiction the master plan predicted: BU's journal is *unassisted discovery* only if the edges are empty; kept blank at the center with prompted edges, it is *enhanced discovery*.
- `[P0]` **Prompts are opt-in-adjacent, never forced.** The Evening Close micro-prompt is skippable in one tap; Feynman-mode is a user-selected mode. The d ≈ +.36 mechanism is elicited explanation — elicitation, not conscription (SDT continuity, Phase 1 §2.3).
- `[P2]` **Feedback on accuracy is what makes elicited explanation work.** Alfieri's moderator: explanations must be *checked* ("ensuring that these ideas are accurate by providing timely feedback"). At P2 this is manual (the user checks against their source); the Feynman-mode flow ends with "now check the part that felt shaky" — accuracy-checking is in the loop from the first shipped version.
- `[P3]` **Forge-suggested prompts inherit the evidence budget.** The Forge may propose one edge-prompt per day maximum; prompt-stuffing would convert enhanced discovery back into a lecture, and a controlling one (Phase 1's D7 register).
- `[P4]` **Free-draw canvas follows the same grammar.** The canvas (P3/P4) is a blank surface *with edge-guidance affordances* (a named concept, a logged ember to draw from) — never auto-decorated.

### 2.8 Deliberate Practice: The Honest Numbers

**Mechanism.** Ericsson, Krampe and Tesch-Römer (1993, *Psychological Review* 100(3), 363–406) defined deliberate practice: effortful, designed to improve a specific sub-skill, with immediate feedback and unlimited refinement — and reported that accumulated deliberate practice predicted expertise in music, chess, and sport. The pop pipeline (Gladwell's "10,000 hours") flattened this into a magic number that the original paper never contained.

**The audit.** Macnamara, Hambrick and Oswald (2014, *Psychological Science* 25(8), 1608–1618) meta-analyzed 88 studies: deliberate practice explained **12% of performance variance overall** — domain-dependently ≈ 26% (games), 21% (music), 18% (sports), 4% (education), <1% (professions) `[CONTESTED — verify exact percentages at next pass]`. Macnamara, Moreau and Hambrick (2016, *Perspectives on Psychological Science* 11(4), 508–516) updated the sports figure to ≈ 18% and drew the same conclusion. Ericsson's rebuttal (2016) argued the meta-analysis mismeasured deliberate practice in several domains; the productive resolution for a builder: *practice is necessary, structure matters, and no one can promise magnitude* — the remaining variance is age of onset, working memory, and other factors nobody can hand the user. Two field-relevant extras: the "10,000-hour" threshold has no special status (practice effects are continuous), and *quality* of practice (feedback richness, error-focus) does more work than raw hours at every skill level (a consistent finding across both camps).

**What this means for BU's promise surface.** BU may claim: structured, feedback-rich, repeated practice reliably improves the *practiced sub-skill*. BU may not claim: practice × time ⇒ expertise, or that BU's loop will make anyone elite. The fire records tendings; it does not forecast mastery.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Ericsson, Krampe & Tesch-Römer (1993), Psych Review 100(3), 363–406 | Deliberate practice defined; predicts expertise | foundational |
| Macnamara, Hambrick & Oswald (2014), Psych Science 25(8), 1608–1618 | Practice explains 12% of variance overall (26% games … <1% professions) | `[CONTESTED, verify %]` |
| Macnamara, Moreau & Hambrick (2016), PPS 11(4), 508–516 | Update; sports ≈ 18%; continuous effects | `[CONTESTED, verify %]` |
| Ericsson (2016), PPS 11(4), 524–530 | Rebuttal; measurement critique | controversy record |

**Application to BU.**

- `[P0]` **Mechanism-not-magnitude copy rule (D3).** Banned: "10,000 hours," "master anything," "unlock your potential." Allowed: "the loop that works: try, check, fix, repeat." This joins Phase 1's D18 ban-list as one lint entry.
- `[P3]` **Forge performance talk is sub-skill-scoped.** "Your integration-by-parts recall has held for 3 weeks" — claims ride on specific embers with specific histories, never on global aptitude (Phase 6's causal grammar will enforce the verbs).
- `[P4]` **No expertise forecasting.** The multi-year procedural fire (P4) reports what happened; it does not extrapolate "at this rate you'll be X in Y months." That sentence is banned in all states.
- `[P0]` **The Play's power comes from its structure, and the copy should say so.** One line for onboarding: "small, specific, checked, repeated — that's the whole trick."

### 2.9 The Founders in Pedagogy: Piaget, Vygotsky, Bruner, Montessori, Dewey

**Piaget — schema, assimilation, accommodation (Piaget, 1952, *The Origins of Intelligence in Children*; Piaget, 1970, *Genetic Epistemology* — T3 foundational).** Knowing is active: the learner constructs schemas, *assimilates* new input into existing ones, and *accommodates* — restructures the schema — when input resists. Equilibration (the drive to resolve that resistance) is the engine of development. What survives modern scrutiny: the adaptation mechanism is durable and maps cleanly onto error-driven learning. What does not: the strong stage model (fixed universal stages in order) — development is far more domain-specific and variable than Piaget's stages implied; the stage architecture is `[CONTESTED]` and modern accounts replace it with overlapping, task-dependent waves. BU implication: a Play should *open inside an existing schema* (start from what the user can already do — assimilation entry) and then apply controlled resistance (accommodation stretch = §2.4's success band). Onboarding's "what are you working toward?" (Phase 1 D3) is the schema inventory.

**Vygotsky — the ZPD and scaffolding (Vygotsky, 1978, *Mind in Society* — compilation, T3; operationalization: Wood, Bruner and Ross, 1976, *Journal of Child Psychology and Psychiatry* 17(2), 89–100).** The zone of proximal development: "the distance between the actual developmental level as determined by independent problem solving and the level of potential development as determined through problem solving under adult guidance or in collaboration with more capable peers." Wood, Bruner and Ross named the support *scaffolding* and specified six functions, from their tutoring study with young children on a pyramid puzzle: **recruitment** (enlist interest), **reduction in degrees of freedom** (simplify the task to match competence), **direction maintenance** (keep the goal salient), **marking critical features** (flag what matters, correct errors), **frustration control** (keep stress manageable without creating dependence), **demonstration** (model the parts beyond reach). The contingent-responsiveness principle (Wood and Middleton, 1978): effective tutors adjust help *contingently* on the learner's last attempt — more help after failure, less after success — which keeps the task inside the zone. **Replication status:** scaffolding as a phenomenon is bedrock; the 1976 study is small and observational in parts; a 2025 preregistered direct replication of the 1978 contingency experiment exists (Smit, de Kleijn, Wicherts and van de Pol, 2025, *Journal of Educational Psychology* 117(8), 1313–1329) — outcome to be read and logged at next pass `[verify outcome]`. The app-relevant caution from the replication line: *contingency is hard to get right*, and miscalibrated contingency (helping when not needed) measurably harms learning.

**Bruner — the spiral curriculum (Bruner, 1960, *The Process of Education* — T3) plus the guided-discovery lineage.** The famous claim: "any subject can be taught effectively in some intellectually honest form to any child at any stage of development" — the curriculum should *circle back*: the same fundamental structure revisited at increasing depth and complexity. Bruner was also co-author of scaffolding (1976) and the discovery-learning tradition's theorist — which §2.7 corrected: *guided*, not pure, discovery survives meta-analysis. BU implication: the ember system is a spiral curriculum in miniature — old entries return (spacing, §2.2) *at higher levels of use* (complexity ladder: recall it → apply it → explain it plainly — which is §2.6's step 4 as the spiral's top rung).

**Montessori — the prepared environment (Montessori's method; evidence: Lillard, 2017, *Montessori: The Science Behind the Genius*, 2nd ed. — T3; Lillard and Else-Quest, 2006, *Science* 313(5795), 1893–1894).** Key principles with evidence standing: **order** in the environment; **choice within limits** (the child chooses from a prepared, constrained set); **self-correcting materials** (feedback built into the task, not delivered by authority); **long protected work periods** (concentration as a protected resource); mixed-age community (out of scope for BU). The 2006 *Science* brief (quasi-experimental; Milwaukee samples; verify exact N at next pass) found Montessori-school 5-year-olds ahead on letter-word identification, math, executive function (card sorting), and social cognition (false belief), with more prosocial playground behavior; the 12-year-olds' essays were rated more creative and more prosocial `[CONTESTED-adjacent: quasi-experimental, selection cannot be excluded]`. A longitudinal preschool study (Lillard, Heise et al., 2017, *Frontiers in Psychology* 8, 1783) reported achievement and executive-function advantages that *equalized* outcomes across income lines `[EMERGING, verify at next pass]`. Montessori's own mystique (the "absorbent mind," sensitive periods as doctrine) is T3 narrative, not load-bearing. BU implication: the app is a prepared environment — order (stable layout, predictable states), choice within limits (three visible plays, not an infinite task list), self-correction (the fire and the evidence grade answer without judgment — Phase 1's D7), and a protected work window (the sealed play, Phase 1 D11).

**Dewey — reflective thought (Dewey, 1910, *How We Think* — T3, primary text verified this pass).** Chapter VI analyzes "a complete act of thought" into five steps, verbatim from the primary text: "(i) a felt difficulty; (ii) its location and definition; (iii) suggestion of possible solution; (iv) development by reasoning of the bearings of the suggestion; (v) further observation and experiment leading to its acceptance or rejection." Reflection, for Dewey, is not mood-contemplation; it is *inquiry with an exit condition*. Empirical bridge: reflection-as-intervention has modest but real support in applied settings (e.g., structured reflection after training improved later performance in field experiments — Di Stefano, Gino, Pisano and Staats, 2014, HBS working paper, "Learning by thinking" `[EMERGING, working paper]`), and the Evening Close already satisfies steps (i)–(iii) in 60 seconds (Phase 1 D2). BU implication: the Sunday Review is where steps (iv)–(v) belong — reasoning over the week's evidence, then one experiment queued (the N=1 queue, §5). Dewey's stages give Sunday Review its spine without adding nightly friction.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Piaget (1952; 1970) (T3) | Assimilation/accommodation/equilibration | mechanism retained; stages `[CONTESTED]` |
| Wood, Bruner & Ross (1976), JCPP 17(2), 89–100 | Scaffolding; six functions | foundational |
| Wood & Middleton (1978) | Contingent shifts keep task in the zone | `[verify Smit 2025 replication outcome]` |
| Smit et al. (2025), JEP 117(8), 1313–1329 | Preregistered direct replication of the 1978 contingency study | `[verify outcome at next pass]` |
| Bruner (1960), *The Process of Education* (T3) | Spiral curriculum | philosophy → operationalized via §2.2 |
| Lillard & Else-Quest (2006), Science 313(5795), 1893–1894 | Montessori outcomes: academics, EF, social cognition | `[CONTESTED-adjacent, quasi-experimental]` |
| Lillard, Heise et al. (2017), Frontiers in Psychology 8, 1783 | Preschool longitudinal: achievement + EF, equalizing | `[EMERGING]` |
| Dewey (1910), *How We Think*, Ch. VI (T3, primary text) | Five steps of reflective thought | philosophy → bridged by §2.8/OQ evidence |

**Application to BU.**

- `[P0]` **The Play template encodes assimilation → accommodation.** Each play starts from the current ember state ("last session you could do X") and adds one stretch element. In Lite this is one line the user writes themselves; in Forge (P3) it is the D7 sizing rule's stretch direction.
- `[P3]` **Scaffolding fade rule (D10): interventions lose degrees of freedom as competence grows.** Operationally: hint density and prompt frequency scale down as a play's historical success rate rises (contingent support, 1978's principle, formalized with Phase 3's automaticity estimate). The 2025 replication outcome will be checked before the fade rule is finalized.
- `[P0]` **Prepared-environment design language (D12) ships as a layout spec, not a feature:** stable screen order, three visible plays max, states that explain themselves, the sealed window visually protected. Montessori's evidence-backed principles, stripped of mystique.
- `[P2]` **The complexity ladder is explicit in Feynman-mode and resurfacing prompts:** recall (restate) → apply (work one) → teach (explain plainly). The spiral's top rung is §2.6's step 4.
- `[P4]` **Sunday Review keeps Dewey's five-step spine:** difficulty → locate → options → reasoning → one queued experiment. The output of every Sunday Review is exactly one N=1 experiment seed or zero — never a to-do list.

## 3 · Design Decisions

Every decision: Decision / Evidence / Confidence / Tag. P0 decisions are the build's homework for the 4-week Spark phase.

| # | Decision | Evidence | Confidence | Tag |
|---|---|---|---|---|
| D1 | **Study-play structure card** (retrieval-first recipe): dump from memory → check against source → re-retrieve the gaps → log one gap as tomorrow's seed; time-split ≈ 20/10/10/5 for a 45-min play; shipped as static copy on study-marked plays | §2.1 (Dunlosky 2013 high-utility; Yang 2021 g ≈ .499; Karpicke & Roediger 2008; Kornell 2009 pretesting) | High | P0 |
| D2 | **Evening Close calibration micro-prompt**: one optional line on study days — "what did you recall today without looking?" — logged with the close; answer (or absence) enters the ember record | §2.5 (retrieval-based JOLs; Karpicke et al. 2009) | High | P0 |
| D3 | **Copy rules extended**: no learning-styles talk, no "10,000 hours"/mastery-forecast framing, no confidence-graded inputs, no "mastered" badges; mechanism-not-magnitude register for all practice copy | §2.8 (Macnamara `[CONTESTED]`), §2.5 (fluency illusions), Pashler et al. 2008 (styles killed — §4) | High | P0 |
| D4 | **Blank page + edge guidance**: journal center stays blank/write-only; guidance confined to three edge surfaces (study-play card, Evening Close micro-prompt, opt-in Feynman-mode); prompts skippable, max one Forge-proposed prompt/day (P3) | §2.7 (Alfieri 2011: guided d ≈ +.50, elicited explanation d ≈ +.36, unassisted d ≈ −.38) | High | P0/P2/P3 |
| D5 | **Feynman-mode journal flow** (P2): name → explain plainly from memory → mark where it broke → one-line analogy; output = ordinary entry + gap-ember; ends with an accuracy check against source; honest attribution in docs, folk-name never a feature label | §2.6 (provenance chain; Chi 1994; Bisra 2018 `[verify g]`; Slamecka & Graf 1978) | High | P2 |
| D6 | **Ember resurfacing engine (Forge) = FSRS-class**: per-ember (D, S, R); power forgetting curve R(t,S) = (1 + factor·t/S)^(−w20), R(S,S) = 0.9; interval = inverted curve at target retention 0.90; parameters **pinned to a tagged FSRS release** (default table logged at build time); SM-2-class expanding rule is the P0/P1 floor; user may pin/defer any ember | §2.2 (Cepeda 2008 ridgeline), §2.3 (SM-2/FSRS spec; srs-benchmark direction) | High (model class) / Medium (params churn) | P0/P1/P3 |
| D7 | **ZPD difficulty targeting**: computed plays sized to hold historical success in the 75–90% band; shrink after failures, stretch only after demonstrated easy streak; below-band plays split; above-band embers graduate | §2.4 (retrieval-effort; 85% rule `[EMERGING]`; RoPL), §2.9 (ZPD contingency) | Medium | P3 |
| D8 | **Interleaved weeks, blocked sessions allowed**: multi-subject weeks rotate subjects day-to-day; a single new skill may block within one session; confusability is the activation condition | §2.4 (Rohrer 2014/2015; Brunmair & Richter moderator; Hausman & Kornell null) | High | P2 |
| D9 | **Spiral mapping for long-horizon arcs** (Constellation): foundational embers revisited on the year-scale band (5–10% of horizon), each revisit at higher complexity rung (recall → apply → teach); graduation visible | §2.2 (Cepeda 2008), §2.9 (Bruner), §2.4 (graduation rule) | Medium | P4 |
| D10 | **Scaffolding-fade rule (Forge)**: hint density and prompt frequency scale down with a play's historical success rate + automaticity estimate (cross-ref Phase 3 D2); contingent direction: help after failure, less after success; finalized only after Smit 2025 outcome is read | §2.9 (Wood & Middleton contingency; scaffolding functions), §2.7 (guidance budgets) | Medium | P3 |
| D11 | **Rereading warning, one line** in the study-play card only: "reading it again feels like studying. testing yourself *is* studying." No behavior policing beyond copy | §2.1 (Callender & McDaniel null), §2.5 (fluency) | High | P0 |
| D12 | **Prepared-environment layout spec**: stable screen order, ≤3 visible plays, self-explaining states, sealed window visually protected, beauty as information (Gorgeous gate consistent with Phase 1 D10) | §2.9 (Lillard 2006/2017), Phase 1 §2.3/§2.4 | Medium | P0/P1 |
| D13 | **Resurfacing cap**: ≤3 resurfaced embers per day surfaced into any morning/daily view (attention budget; cross-ref Phase 4's notification discipline); overdue embers queue, never multiply | §2.4 (success band depends on fresh attention), Phase 4 (bridge) | Medium | P3 |

**v6-doc amendments produced by this phase** (change log for `PLAN.md`): (1) the "Watcher and Forge as the More Knowledgeable Other" framing is narrowed — BU may act as MKO only via *contingent, fading* support (§2.9; D10), otherwise the 1978 literature says miscalibrated help harms; (2) the v0 draft's pedagogy sourcing is replaced by this phase's tables (Piaget stages `[CONTESTED]`, Montessori quasi-experimental status, Feynman folk-provenance now stated honestly in-app); (3) "the Play is situated learning" is operationalized as the D1 retrieval-first structure rather than a vibe; (4) mastery/expertise magnitude promises are banned (D3 joins Phase 1's D18 lint list).

## 4 · Contradiction Cards

**Card A — Blank page (v6 aesthetic) vs. guided learning (evidence).** Alfieri's metas say unassisted discovery *loses* (d ≈ −.38), yet v6's write-only blank journal is a load-bearing aesthetic and privacy choice (§2.7). *Working resolution:* the page stays blank at the center; all guidance is edge guidance — pre-play (study card), post-play (Evening Close micro-prompt), opt-in (Feynman-mode). This is "enhanced discovery" in Alfieri's taxonomy, the configuration that actually beats instruction. *Flip condition:* if the builder's N=1 logs show evening entries degenerating into mood notes with no recall content for 2+ weeks, the edges are failing and one in-page optional prompt is reconsidered — with the change logged as a v6 amendment, not a silent edit.

**Card B — Desirable difficulty vs. competence protection (SDT).** The learning optimum is "mostly succeed, but not comfortably" (§2.4), while Phase 1's SDT audit says perceived competence is a basic need and failure-heap can produce disengagement. *Working resolution:* difficulty lives *inside* the play; gentleness lives *around* it. Concretely: sizing shrinks after failures (D7 guards the band from below), struggle-copy follows Phase 1's D8 (effort+strategy, never essence), and the success band never goes below ~75% predicted. Frustration control is literally one of the six scaffolding functions (§2.9) — BU owns the wrap, the user owns the load. *Flip condition:* if low-band weeks show tending-rate collapse (not just difficulty complaints), the floor rises.

**Card C — Scheduling optimization vs. user agency.** FSRS-class scheduling quietly decides *when* the user meets their own past (D6), which sits oddly with Phase 1's autonomy findings. *Working resolution:* the scheduler proposes and explains ("due — last held 9 days ago"), pinning and deferring are one tap, and the display shows S in plain units. The optimization is a tool the user can see through, not a black box that owns the calendar. *Flip condition:* if override rates are extreme (>50% of resurfacing events overridden for 2+ weeks), the target retention or the cap (D13) is wrong — retune, don't remove the override (Phase 6 owns the analysis).

**Card D — The "Feynman" brand vs. honesty.** The name sells (it is the most-searched learning-technique term), and its provenance is folk (§2.6). *Working resolution:* technique shipped as "explain it plainly" mode with the attribution line in docs and onboarding ("named after Feynman; the name is folk — the mechanisms are measured"). The honesty brand (Phase 1 D18) outranks the search traffic. *Flip condition:* none — this card does not flip; the attribution is invariant.

## 5 · Open Questions + N=1 Experiment Queue

Single-case designs formalized in Phase 6; seeded here so the builder can start during P0.

- **OQ1 · Study-play completion cost.** Does the recall-first card (D1) reduce play completion (friction) without improving self-graded recall, or does it hold completion and improve recall? *Design:* completion + next-evening recall answer tracked by card exposure, 8 weeks. [P0-testable]
- **OQ2 · Calibration drift.** Does the Evening Close recall micro-prompt's answer predict next-week completion/retention at all? If not, it is decoration — cut or reword. *Design:* logistic association, weekly, from P0 data. [P0-data, P2-analysis]
- **OQ3 · Interleaved vs. blocked weeks.** Rotate two subjects: interleaved-week vs blocked-week blocks (ABAB), outcome = self-graded next-week recall. [P2-testable]
- **OQ4 · Resurfacing cap tolerance.** Compare 2 vs 4 resurfaced embers/day for abandonment and completion. [P3-testable]
- **OQ5 · Feynman-mode usage.** Do Feynman-mode entries run longer, recur more, and produce more gap-embers than free-form entries on matched days? [P2-testable]
- **OQ6 · Target retention.** R-target 0.90 vs 0.85: review load vs held-stability on the builder's real ember set. [P3-testable]
- **OQ7 · Prompt frequency.** One Forge edge-prompt/day vs zero: entry quality and opt-out rates. [P3-testable]

**Research debt (verified-number gaps for the next pass):** Rowland 2014 exact k/g; Adesope et al. 2017 g; Bisra et al. 2018 exact g; Macnamara et al. 2014/2016 exact domain percentages; Brunmair & Richter 2019 exact g; Murre & Dros 2015 DOI; Pashler et al. 2008 exact quote page; Lillard & Else-Quest 2006 exact N; Latimier et al. 2021 expanding-vs-fixed spacing meta (to settle expanding-schedule bonus); Smit et al. 2025 replication outcome (contingency tutoring); srs-benchmark current table values; Kornell, Hays & Bjork 2009 effect sizes; Bertsch et al. 2007 generation-effect g; Metcalfe RoPL canonical citations; Di Stefano et al. reflection paper's final publication status.

## 6 · Sources

**T1/T2 — opened or verified this pass.**

- Dunlosky, J., Rawson, K. A., Marsh, E. J., Nathan, M. J., & Willingham, D. T. (2013). Improving students' learning with effective learning techniques. *Psychological Science in the Public Interest*, 14(1), 4–58. doi 10.1177/1529100612453266
- Yang, C., Luo, L., Vadillo, M. A., Yu, R., & Shanks, D. R. (2021). Testing (quizzing) boosts classroom learning: A systematic and meta-analytic review. *Psychological Bulletin*, 147(4), 399–435. pubmed.ncbi.nlm.nih.gov/33683913
- Cepeda, N. J., Vul, E., Rohrer, D., Wixted, J. T., & Pashler, H. (2008). Spacing effects in learning: A temporal ridgeline of optimal retention. *Psychological Science*, 19(11), 1095–1102. doi 10.1111/j.1467-9280.2008.02209.x
- Cepeda, N. J., Pashler, H., Vul, E., Wixted, J. T., & Rohrer, D. (2006). Distributed practice in verbal recall tasks: A review and quantitative synthesis. *Psychological Bulletin*, 132(3), 354–380.
- Rohrer, D., Dedrick, R. F., & Burgess, K. (2014). The benefit of interleaved mathematics practice is not limited to superficially similar kinds of problems. *Psychonomic Bulletin & Review*, 21(6), 1323–1330. pubmed.ncbi.nlm.nih.gov/24578089
- Rohrer, D., Dedrick, R. F., & Stershic, S. (2015). Interleaved practice improves mathematics learning. *Journal of Educational Psychology*, 107(3), 712–720. eric.ed.gov/?id=EJ1071568
- Alfieri, L., Brooks, P. J., Aldrich, N. J., & Tenenbaum, H. R. (2011). Does discovery-based instruction enhance learning? *Journal of Educational Psychology*, 103(1), 1–18.
- Lazonder, A. W., & Harmsen, R. (2016). Meta-analysis of inquiry-based learning: Effects of guidance. *Review of Educational Research*, 86(3), 681–708.
- Furtak, E. M., Seidel, T., Iverson, H., & Briggs, D. C. (2012). Experimental and quasi-experimental studies of inquiry-based science teaching. *Review of Educational Research*, 82(3), 300–329.
- Karpicke, J. D., & Blunt, J. R. (2011). Retrieval practice produces more learning than elaborative studying with concept mapping. *Science*, 331(6018), 772–775.
- Karpicke, J. D., & Roediger, H. L. (2008). The critical importance of retrieval for learning. *Science*, 319(5865), 966–968.
- Roediger, H. L., & Karpicke, J. D. (2006). Test-enhanced learning. *Psychological Science*, 17(3), 249–255.
- Kornell, N., Hays, M. J., & Bjork, R. A. (2009). Unsuccessful retrieval attempts enhance subsequent learning. *JEP: Learning, Memory, and Cognition*, 35(4), 983–1003.
- Callender, A. A., & McDaniel, M. A. (2009). The limited benefits of rereading educational texts. *JEP: Learning, Memory, and Cognition*, 35(1), 82–95.
- Rowland, C. A. (2014). The effect of testing versus restudy on retention: A meta-analytic review of the testing effect. *Psychological Bulletin*, 140(6), 1439–1463. `[k/N verify]`
- Pyc, M. A., & Rawson, K. A. (2009). Testing the retrieval effort hypothesis. *Psychological Science*, 20(8), 1015–1022.
- Wilson, R. C., Shenhav, A., Straccia, M., & Cohen, J. D. (2019). The eighty five percent rule for optimal learning. *Nature Communications*, 10, 4646. `[EMERGING]`
- Chi, M. T. H., de Leeuw, N., Chiu, M.-H., & LaVancher, C. (1994). Eliciting self-explanations improves understanding. *Cognitive Science*, 18(4), 439–477.
- Bisra, K., Liu, Q., Nesbit, J. C., Salimi, F., & Winne, P. H. (2018). Inducing self-explanation: A meta-analysis. *Educational Psychology Review*, 30, 703–725. `[g verify]`
- Slamecka, N. J., & Graf, P. (1978). The generation effect: Delineation of a phenomenon. *JEP: Human Learning and Memory*, 4(6), 592–604.
- Simon, D. A., & Bjork, R. A. (2001). Metacognition in motor learning. *JEP: Learning, Memory, and Cognition*, 27(1), 90–103.
- Rhodes, M. G., & Castel, A. D. (2008). Memory predictions are influenced by perceptual information on the test. *JEP: Learning, Memory, and Cognition*, 34(4), 915–924.
- Karpicke, J. D., Butler, A. C., & Roediger, H. L. (2009). Metacognitive strategies in student learning. *Memory*, 17(4), 471–479.
- Hausman, H., & Kornell, N. (2014). Mixing topics while studying does not enhance learning. *JARMAC*, 3, 153–160.
- Rohrer, D. (2012). Interleaving helps students distinguish among similar concepts. *Educational Psychology Review*, 24, 355–367.
- Brunmair, M., & Richter, T. (2019). Similarity matters: A meta-analysis of interleaved learning. *Educational Psychology Review*, 31, 683–741. `[g verify]`
- Macnamara, B. N., Hambrick, D. Z., & Oswald, F. L. (2014). Deliberate practice and performance. *Psychological Science*, 25(8), 1608–1618. `[verify %]`
- Macnamara, B. N., Moreau, D., & Hambrick, D. Z. (2016). The relationship between deliberate practice and performance in sports. *Perspectives on Psychological Science*, 11(4), 508–516. `[verify %]`
- Ericsson, K. A. (2016). Summing up hours of any type of practice versus deliberate practice. *Perspectives on Psychological Science*, 11(4), 524–530.
- Ericsson, K. A., Krampe, R. T., & Tesch-Römer, C. (1993). The role of deliberate practice in the acquisition of expert performance. *Psychological Review*, 100(3), 363–406.
- Wood, D., Bruner, J. S., & Ross, G. (1976). The role of tutoring in problem solving. *Journal of Child Psychology and Psychiatry*, 17(2), 89–100.
- Smit, N., de Kleijn, R., Wicherts, J. M., & van de Pol, J. (2025). What it takes to tutor: A preregistered direct replication of the scaffolding experimental study by D. Wood et al. (1978). *Journal of Educational Psychology*, 117(8), 1313–1329. doi 10.1037/edu0000965 `[outcome verify]`
- Lillard, A. S., & Else-Quest, N. (2006). The early years: Evaluating Montessori education. *Science*, 313(5795), 1893–1894. `[N verify]`
- Lillard, A. S., Heise, M. J., Rileigh, E. M., Joseph, L., Best, E., & Ruvolo, M. (2017). Montessori preschool elevates and equalizes child outcomes. *Frontiers in Psychology*, 8, 1783. `[verify]`
- Mayer, R. E. (2004). Should there be a three-strikes rule against pure discovery learning? *American Psychologist*, 59(1), 14–19.
- Ye, J., Su, J., & Cao, Y. (2022). A stochastic shortest path algorithm for optimizing spaced repetition scheduling. *Proc. 28th ACM SIGKDD*, 4381–4390. doi 10.1145/3534678.3539081
- Su, J., Ye, J., Nie, L., Cao, Y., & Chen, Y. (2023). Optimizing spaced repetition schedule by capturing the dynamics of memory. *IEEE TKDE*. doi 10.1109/TKDE.2023.3251721
- Upadhyay, U., Lancashire, G., Moser, C., & Gomez-Rodriguez, M. (2021). Large-scale randomized experiments reveal that machine learning-based instruction helps people memorize more effectively. *npj Science of Learning*, 6. doi 10.1038/s41539-021-00105-8
- Wixted, J. T., & Ebbesen, E. B. (1991). On the form of forgetting. *Psychological Review*, 98(3), 404–415.
- Rubin, D. C., & Wenzel, A. E. (1996). One hundred years of forgetting: A quantitative description of retention. *Psychological Review*, 103(4), 734–760.
- Murre, J. M. J., & Dros, J. (2015). Replication and analysis of Ebbinghaus' forgetting curve. *PLoS ONE*. `[DOI verify]`
- Pashler, H., McDaniel, M., Rohrer, D., & Bjork, R. (2008). Learning styles: Concepts and evidence. *Psychological Science in the Public Interest*, 9(3), 105–119. `[quote page verify]`

**FSRS/SM-2 primary documentation (T4-code-docs — authoritative for the code, not peer-reviewed evidence).** open-spaced-repetition/fsrs4anki wiki, "The Algorithm" (FSRS-5/6 formulas and default weights, read this pass); open-spaced-repetition/srs-benchmark README (corpus and metric definitions); Wozniak (1990), *Optimization of Learning*, M.Sc. thesis, Poznań (SM-2); Expertium's technical FSRS walkthrough (semantics of SInc, D updates — T4).

**T3 — context only.** Gleick (1992), *Genius* (the notebook passage — provenance anchor); Young (2011 blog post; 2019, *Ultralearning*) — first known use of the technique's name; Feynman's own writings ("The Pleasure of Finding Things Out") for what he actually did; Bruner (1960), *The Process of Education*; Vygotsky (1978), *Mind in Society*; Piaget (1952; 1970); Lillard (2017), *Montessori: The Science Behind the Genius*, 2nd ed.; Dewey (1910), *How We Think* (primary text); Paivio (1986), *Mental Representations*; Bjork & Bjork (2011), "Making things hard on yourself, but in a good way"; Montessori's own works (narrative, not load-bearing).

**T4 — leads, not evidence.** fs.blog Feynman-technique explainer (representative 4-step adaptation); azri.app FSRS-vs-SM-2 comparison (benchmark numbers quoted only after cross-check against the repo — flagged); Expertium blog (benchmark/RMSE-bins explanation); Taalhammer FSRS explainer (Cepeda numbers echoed); scotthyoung.com Sept 2011 post (provenance anchor, dated); todoist/openculture Gleick quotations (verified against each other).

**Cross-references.** Play grammar and Evening Close structure → Phase 1 D1/D2 (this phase adds D1-study card and D2 micro-prompt to those surfaces). Consolidation/sleep window for why spacing works → Phase 3. Resurfacing caps and notification discipline → Phase 4. Engagement economics of quiz-style mechanics and streak framing → Phase 5. Evidence grades for self-graded recall (USER_CONFIRMED vs OBSERVED) and conformal display of R → Phase 6. Fire-state weighting by ember recency → Phase 7 seed function.

---

*End of Phase 2. Tracker updated in `RESEARCH-PLAN.md` Appendix C. Next: "Research Phase 3" produces `phase3-neuroscience.md` — the brain that tends the fire: Hebb post-1949 and BTSP status, dopamine timing, habit-control transfer, the real habit-formation distribution, Merzenich's plasticity conditions, sleep, and stress-habit shifts.*
