# BU Research · Phase 1 — Self-Regulation & Discipline: The Science of the Fire

**Version** 1.0 · **Date** 2026-11-05 · **Status** Delivered — awaiting builder review
**Program** `research/RESEARCH-PLAN.md` v1.0 (Phase 1 of 7)
**Scope held** ~60% science / ~40% BU design implications · replication flags on load-bearing claims · P0-first bias · every "Application to BU" block tagged P0–P4
**Owner** Dhivakaran · reviewed by any LLM session that reads `MEMORY/` first

---

## 1 · TL;DR — The Eight Verdicts

1. **The willpower reservoir is dead.** The strength model of self-control failed its pre-registered multilab replication (23 labs, effect near zero) and its meta-analytic base collapses under bias correction `[FAILED]`. BU must never model discipline as a depletable fuel. What survives: effort feels costly, motivation shifts dynamically, and beliefs about willpower matter. The fire is an evidence ledger, not a battery.
2. **Delay-of-gratification traits explain far less than advertised** `[CONTESTED]`. After family-background controls, the marshmallow test's predictive power shrinks by two-thirds. BU cannot promise to "build willpower"; it can build the *conditions* — cues, environments, reliability — that actually separate disciplined people from undisciplined ones.
3. **Implementation intentions are the strongest verified lever in this phase**: ~94 studies, d ≈ .65 `[REPLICATED]` (a 2025 update across 642 tests reports d ≈ .27 for behavior change after bias correction — still real). The Play grammar — *when + what + how long* — is therefore the single highest-leverage P0 design decision in the entire app.
4. **Rewards and control backfire.** Expected tangible rewards undermine intrinsic motivation for interesting tasks `[REPLICATED]`; autonomy-supportive design outperforms controlling design. The fire must speak as information ("the fire is low"), never as punishment ("you failed") or bribe (points, streak tokens).
5. **Lapses cascade when blamed on character, and shrink when forgiven.** The abstinence violation effect is a core relapse mechanism; self-forgiveness for procrastination reduces future procrastination `[REPLICATED-consistent]`. Fire-death design: relight ritual, small plays, zero shame copy.
6. **Self-monitoring reliably moves behavior**: d ≈ .40 across ~138 tests `[REPLICATED]`. Evening Close is a mechanism, not a ritual. It must be daily, under a minute, and its answers must update the fire the same evening.
7. **Grit is mostly conscientiousness** (ρ ≈ .84; grit-achievement ρ ≈ .37) and **growth-mindset effects are weak on average but real for struggling users** `[CONTESTED]`. Identity framing ("that's who you are now") survives; trait-training claims do not.
8. **Self-efficacy is robust, and mastery experiences are its strongest source** `[REPLICATED]`. This is the deepest argument for the whole v6 architecture: every evidence-graded completed Play manufactures a mastery experience. The Gorgeous gate is 30 consecutive units of verified self-efficacy evidence.

**P0 haul (what this phase changes in the 4-week build):** Play grammar with anchor (D1) · Evening Close v2 with graded answers (D2) · onboarding v2 built on goal internalization + WOOP obstacle mapping (D3) · relight ritual with self-forgiveness copy (D5) · a hard copy ban-list covering shame, trait claims, and controlling language (D7, D18) · the sealed-play soft commitment made explicit (D11).

---

## 2 · Science

### 2.1 The Willpower Question: Ego Depletion and Its Collapse

**Mechanism (as originally claimed).** The strength model holds that self-control draws on a single global resource that depletes with use. Baumeister, Bratslavsky, Muraven and Tice (1998, JPSP) reported that people forced to resist fresh-baked cookies subsequently persisted ~17 minutes less on unsolvable puzzles — less than both no-resistance and no-food controls. The model generalized quickly: willpower as a muscle, fatigueable across domains, glucose as its fuel, and a popular-literature empire built on the analogy (Baumeister & Tierney, *Willpower*, 2011 — T3, historically important, scientifically superseded).

**The collapse.** Three blows landed in sequence. First, Carter, Kofler, Forster and McCullough (2015, JEP: General) re-ran the meta-analytic evidence with bias corrections and concluded self-control "does not seem to rely on a limited resource." Second, Hagger and Chatzisarantis (2016, Perspectives on Psychological Science) ran a pre-registered multilab registered replication report: 23 laboratories, over 2,000 participants, the canonical paradigm executed with maximal fidelity — the depletion effect came back near zero (reported d ≈ .04). Third, replicability audits of the full literature (429 studies, N ≈ 33,927) found the published record so saturated with publication bias that it provides "no credible evidence" for the effect as originally framed. A 2010 meta-analysis (Hagger et al.) had estimated a moderate-to-strong pooled effect; that estimate is now understood to be an artifact of small-study bias. **Flag: `[FAILED]` — this is the cleanest large-scale failure in modern social psychology.**

**What survived the wreck.** Four findings are robust and they matter more for BU than the model that died:

1. **Effort is subjectively costly anyway** — the *effort paradox* (Inzlicht, Shenhav and Olivola, 2018, TiCS): people consistently avoid effort even when it pays nothing, yet also seek it out; mental effort carries a real, measurable cost signal even without any resource running down.
2. **Motivation and attention shift, they don't drain** — the *process model* (Inzlicht and Schmeichel, 2012): after demanding tasks, motivation shifts *away* from the demanding activity and *toward* gratification (rest, reward, novelty). The subjective experience is "I can't"; the mechanism is "I now want something else."
3. **Beliefs about willpower change behavior** — Job, Dweck and Walton (2010, Psych Science): people who *believe* willpower is a limited resource show worse performance on demanding sequences; people who believe it is non-limited do not. The belief, not the fuel, does part of the work.
4. **Fatigue states are real and measurable** — sleep loss and time-on-task degrade regulation performance; the failure is in the resource-*metaphor*, not in the observation that tired people act differently (sleep's role is owned by Phase 3).

**Operational math.** There is no resource budget to compute — that is the point. What BU can legitimately model is: state variables that *predict* completion (sleep proxy, time of day, play size, historical completion rates) and the motivational-shift structure of a day (after heavy sessions, motivation for more demanding work is lower — schedule accordingly). Concretely: `expected_completion(play) ≈ f(play_size, sleep_proxy, time_of_day, historical_rate)` — a calibration function, not a fuel gauge.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Baumeister et al. (1998), JPSP 74(5), 1252–1265 | Original depletion paradigm; cookie resistance → less puzzle persistence | origin of model |
| Carter et al. (2015), JEP: General 144(4), 796–815 | Meta-analytic tests with bias correction: no limited-resource support | `[CONTESTED→FAILED]` |
| Hagger & Chatzisarantis (2016), PPS 11(4), 546–573 | 23-lab pre-registered RRR: effect ≈ 0 (d ≈ .04) | `[FAILED]` |
| Inzlicht & Schmeichel (2012), PPS 7(2), 162–176 | Process model: motivation/attention shift, not depletion | `[REPLICATED-consistent]` |
| Inzlicht et al. (2018), TiCS 22(4), 334–345 | Effort paradox: effort costly yet valued | `[REPLICATED-consistent]` |
| Job, Dweck & Walton (2010), Psych Science 21(10), 1473–1478 | Willpower *beliefs* moderate post-demand performance | `[REPLICATED-consistent]` |

**Application to BU.**

- `[P0]` **No fuel gauges.** The fire is never rendered or described as a willpower battery. It is an evidence ledger: it reports what you did, weighted by how well the evidence verifies it. This distinction goes into the design language document.
- `[P0]` **Evening Close "no" path bans resource blame.** Banned: "you ran out," "willpower depleted." Required framing: "the plan didn't fit the day" or "the cue was missing" — attribution to plan and situation, which the literature says is both true and more productive (see 2.7).
- `[P2]` **Low-energy plays are mechanistic, not motivational.** When the sleep proxy is bad, the play sizes down *silently* (or with one calm line: "short night. today's play is small."). No lectures about energy or willpower; the sizing rule is `f(sleep_proxy, historical_completion)` — evidence-based calibration.
- `[P2]` **Hard Focus is never pitched as "conserve willpower."** It is situation modification — removing the option (see 2.6 and Phase 4). The pitch line writes itself from the evidence: "you don't resist the phone. you remove it."
- `[P0]` **Onboarding sets the belief correctly** (Job et al. 2010 implies this matters): one line in first-open — "discipline is a system, not a fuel" — inoculates against the depletion mindset that predicts worse real-world persistence.
- `[P3]` **Forge speech is state-reporting, never resource-talk.** "After nights under 6h, your plays complete less often. Today's play is sized down." — descriptive, causal-humble (grammar enforced in Phase 6).

### 2.2 Delay of Gratification After the Replication

**Mechanism (what Mischel actually found).** The original paradigm (Mischel, Ebbesen and Zeiss, 1972, JPSP) is routinely misremembered. The variable that mattered was not grit but *attention strategy*: children who covered the treat, turned away, or reframed it ("pretend it's a cloud, not a marshmallow") waited far longer; children who stared at the reward waited less — the rewards themselves increased frustration and *decreased* delay. Delay was engineered by situation and cognition, not by raw trait strength. The follow-up (Shoda, Mischel and Peake, 1990, Developmental Psychology) found preschool wait time correlated with adolescent competence — in a modest, non-representative sample, bivariantly.

**The replication.** Watts, Duncan and Quan (2018, Psychological Science 29(7), 1159–1177) re-ran the association in the NICHD SECCYD sample (N > 900, more diverse, mothers without college degrees as the focal subsample) `[CONTESTED]`. Results: an additional minute waited at age 4 predicted about one-tenth of a standard deviation of age-15 achievement — **half the size of the original bivariate correlations** — and the association **fell by two-thirds after controls** for family background, early cognitive ability, and home environment. Most of the achievement signal came from whether the child could wait *at all* (≥ ~20 seconds) versus not. Behavioral-outcome associations were much smaller and rarely significant. Complementary work (Kidd, Palmeri and Aslin, 2013, Cognition) shows waiting behavior tracks *environmental reliability*: children who had just watched an adult break a promise waited far less `[EMERGING, single study]`. Together: delay behavior reflects environment and expectations at least as much as it reflects the child.

**Operational math.** Attenuation structure: `r_bivariate ≈ 2 × r_adjusted` in this literature; with controls, the delay-achievement association lands around r ≈ .05–.10 class. Design consequence: any feature premised on "training the trait" should expect effect sizes in this range at best; features that change *situations* (cues, environments, deadlines) operate through entirely different mechanisms (2.5, 2.6) with much larger effects (d ≈ .40–.65).

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Mischel, Ebbesen & Zeiss (1972), JPSP 21(2), 204–218 | Attention/distraction strategies drive waiting; reward-salience hurts it | foundational |
| Shoda, Mischel & Peake (1990), Dev Psych 26(6), 978–986 | Preschool delay ↔ adolescent competence (small, bivariate) | foundational |
| Watts, Duncan & Quan (2018), Psych Science 29(7), 1159–1177 | Effect halved bivariantly; −2/3 with controls; behavior outcomes ≈ 0 | `[CONTESTED]` |
| Kidd, Palmeri & Aslin (2013), Cognition 126(1), 109–114 | Environmental reliability shifts waiting | `[EMERGING]` |

**Application to BU.**

- `[P0]` **No trait promises, ever.** Onboarding and store copy may not say "build iron willpower." The honest promise the evidence supports: "make showing up easier by building the cue, the plan, and the record." This is D18.
- `[P0]` **BU itself must be a reliable environment** (Kidd 2013 implies the cost of unreliability). The fire's math, the evening-close time, and the relight rule must be transparent and stable — the user must be able to *learn the rule*. Random-feeling punishment trains impatience, not discipline.
- `[P0]` **Situation-strategy hints ship inside P0 plays.** When the user writes a play, one optional hint line teaches the adult version of Mischel's findings: "phone in another room," "study away from the bed," "same desk, same time." These are environment edits — the mechanism that actually works.
- `[P1]` **Fire computation is explainable.** P1's fire-state function must be simple enough that a user who reads the docs can predict the state from their week. Opacity would make BU an unreliable environment and erode the very behavior it depends on.
- `[P4]` **No deterministic biography.** The Life Log and Forge may never write "you're just not a morning person" as a verdict. States and patterns, not essences (grammar rules from Phase 6).

### 2.3 Self-Determination Theory: Autonomy, Competence, Relatedness

**Mechanism.** Self-Determination Theory (SDT; Deci and Ryan; canonical overview: Ryan and Deci, 2000, *American Psychologist* 55(1), 68–78) holds that human motivation has a *quality* as well as a quantity. Three basic psychological needs — **autonomy** (volition; the behavior is one's own), **competence** (effectiveness; growing mastery), and **relatedness** (connection) — determine where a person sits on the internalization spectrum: external (reward/punishment) → introjected (guilt/ego) → identified (personal value) → integrated (identity) → intrinsic (inherently enjoyable). The spectrum matters because motivation quality predicts persistence better than motivation quantity: identified/integrated motives survive setbacks; external motives evaporate when the contingency disappears.

**The reward warning.** Deci, Koestner and Ryan (1999, *Psychological Bulletin* 125(6), 627–668) meta-analyzed 128 experiments: **expected tangible rewards undermine free-choice persistence on interesting tasks** — the classic overjustification/crowding-out result `[REPLICATED, with boundary conditions]`. Verbal rewards (praise/feedback) did not show the same undermining, and contingent-on-performance feedback can support competence. The design lesson is precise: *exchangeable* rewards bribe; *informational* feedback feeds. Points, redeemable tokens, and prize mechanics sit on the dangerous side; state reporting and progress visibility sit on the supportive side.

**Operational math.** SDT's operational content for BU is a *copy and affordance matrix*, not an equation: every user-facing behavior classifies as autonomy-supportive (choice, rationale, acknowledgment), structure-providing (clear expectation, stable rule), or controlling (pressure, surveillance-without-choice, shame). The design rule: structure is allowed, control is not — "strong expectations, weak pressure."

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Ryan & Deci (2000), Am Psych 55(1), 68–78 | Three needs; internalization spectrum predicts persistence | foundational |
| Deci, Koestner & Ryan (1999), Psych Bulletin 125(6), 627–668 | Expected tangible rewards undermine intrinsic motivation (128 experiments) | `[REPLICATED]` |
| SDT-based health-app design literature | Autonomy-supportive features sustain engagement in digital health | `[locate primary at next pass]` |

**Application to BU.**

- `[P0]` **The Play is always the user's in Lite.** Write it, anchor it, swap it — max autonomy. In Full (P3), the Forge proposes and the user confirms or edits: *choice architecture*, not prescription. Prescribing without exit is the controlling pattern SDT predicts will backfire.
- `[P0]` **Copy ban-list (D7)**, enforced across all five fire states: banned — "must," "you failed," "disappointed in you," "no excuses for you." Required register — "the fire is dim. smallest way to feed it: [play]." The fire reports; it never scolds.
- `[P0]` **Structure with chosen anchors.** Evening Close time is user-set; play time is user-anchored (2.5). Strong expectations + user-chosen parameters is the autonomy-supportive-structure combination the health-behavior literature favors.
- `[P3]` **The Gorgeous state is competence feedback, not a prize.** Nothing is redeemable; nothing is a currency. The fire's beauty *is* the reward because it is information about what the user did — this is the design defense against crowding-out (2.3's reward warning applied to the fire itself).
- `[P1]` **Watcher consent is autonomy-framed.** "You choose what BU may see. Everything stays on the device. You can turn it off — the fire goes back to manual." Consent flows that hide scale or use pre-checked momentum are exactly the controlling pattern this literature warns about (full consent spec: Phase 6).
- `[P0]` **Rationale transparency.** Wherever BU constrains (seal, ladder, block), one line of *why* ships with it. SDT finds rationale-giving moves perceived locus from external to internal — cheap to build, evidence-backed.

### 2.4 The Traits Audit: Grit, Growth Mindset, Self-Efficacy

**Grit.** Duckworth, Peterson, Matthews and Kelly (2007, JPSP 92(6), 1087–1101) defined grit as perseverance + consistency of interest for long-term goals and reported prediction of West Point retention, spelling-bee survival, and educational attainment. The meta-analytic audit (Credé, Tynan and Harms, 2017, JPSP 113(3), 492–511; 88 studies, N = 13,141 for the achievement association) `[CONTESTED]`: grit-achievement correlation ρ ≈ .37 (real, moderate); grit correlates **very strongly with conscientiousness (ρ ≈ .84)**, so its incremental validity over the existing Big-Five construct is questionable; the **perseverance-of-effort facet carries most of the predictive weight**, while consistency-of-interest is weaker and psychometrically wobbly (its items half-negate, inflating correlations); no credible evidence that grit is readily *trainable* as a trait. What survives for BU: long-horizon consistency is a real behavioral signal; "passion + persistence" is a description, not an installable trait.

**Growth mindset.** Dweck's implicit-theories program (Blackwell, Trzesniewski and Dweck, 2007; Dweck 2006 — T3) claims that believing ability is malleable improves persistence and achievement. The meta-analytic verdict (Sisk et al., 2018, Psychological Science 29(4), 571–585) `[CONTESTED]`: first meta (k = 273, N = 365,915) — mindset-achievement association weak; second meta (k = 43, N = 57,155) — intervention effects on achievement weak overall; **but** the theory's targeted claim holds: low-SES and academically at-risk students benefit more. The national experiment (Yeager et al., 2019, *Nature* 573, 364–369) `[REPLICATED-in-part]` — 12,490 ninth-graders, 65 schools, individually randomized, two short online sessions: lower-achieving students earned significantly higher core-course GPA, and both achievement groups enrolled in more advanced math; effects were largest where peer norms supported challenge. Mechanism inside the intervention: *attribution retraining* (your brain grows; struggle is not a verdict) **plus concrete strategy rehearsal** — not cheerleading.

**Self-efficacy.** Bandura (1977, *Psychological Review* 84(2), 191–215; 1997 book — T3): beliefs about one's capability to execute courses of action, built from four sources in order of power: **mastery experiences** (done it before — strongest), vicarious models, verbal persuasion, and physiological state. Meta-analytic support is broad: self-efficacy correlates with academic performance at moderate strength (ρ ≈ .38 in Multon, Brown and Lent, 1991, J. Counseling Psychology 38(1), 30–38 `[verify ρ at next pass]`) and with health-behavior adoption across dozens of domains `[REPLICATED]`. Self-efficacy is also *task-specific and evidence-sensitive* — which makes it the one trait-adjacent construct an app can legitimately move, because the app can manufacture evidence.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Duckworth et al. (2007), JPSP 92(6), 1087–1101 | Grit predicts retention/achievement beyond IQ in several domains | foundational |
| Credé et al. (2017), JPSP 113(3), 492–511 | grit-achievement ρ ≈ .37; grit ↔ conscientiousness ρ ≈ .84; perseverance facet does the work | `[CONTESTED]` |
| Sisk et al. (2018), Psych Science 29(4), 571–585 | Two metas: mindset effects weak overall; at-risk/low-SES benefit | `[CONTESTED]` |
| Yeager et al. (2019), Nature 573, 364–369 | National RCT: GPA gains for lower achievers; advanced-math uptake; norms moderate | `[REPLICATED-in-part]` |
| Bandura (1977), Psych Review 84(2), 191–215 | Four sources of self-efficacy; mastery strongest | foundational |
| Multon et al. (1991), J Couns Psych 38(1), 30–38 | Self-efficacy ↔ performance meta | `[REPLICATED]` |

**Application to BU.**

- `[P3]` **Identity streaks, not numeric streaks** (D9). Forge/user-facing framing: "You've shown up 4 of the last 7 days. That's who you are now." — evidence-summary + identity attribution, never a Duolingo-style "47-day streak" number as the core display (the loss-side hazards of numeric streaks are owned by Phase 5).
- `[P0]` **Process-praise rules (D8)**, applied to every state transition and evening-close response: praise the *behavior and the strategy* ("you started before noon — that's the pattern that works for you"), never the essence ("you're so naturally disciplined").
- `[P0]` **Struggling-user targeting is where mindset copy earns its keep.** Yeager 2019 says generic mindset messaging is weak but targeted messaging works. BU's equivalent of "at-risk": after a "no" close or fire drop. That's exactly when the play-hint line shifts to effort+strategy framing: "hard ≠ you can't. try the 15-minute version." Default-state copy stays neutral-informative; intervention-grade copy fires only on struggle signals.
- `[P1]` **The fire is a mastery ledger (D10).** Bandura's source #1 operationalized: completed, evidence-graded tendings are the user's visible proof-of-capability history. The Gorgeous gate (30 verified days) is the maximal mastery artifact — earned evidence, not a purchased badge. This is also the cleanest SDT-compatible reward in the app (2.3).
- `[P0]` **Onboarding Q1 elicits identified goals.** "What are you working toward?" gains an optional "why does it matter?" follow-up: the answer becomes the internalization anchor the fire and Forge reference ("the fire that feeds [stated goal]"). Identified/integrated motives survive lapses; external ones don't.
- `[P0]` **Banned self-efficacy poison: comparative ranking.** No leaderboards, no percentiles vs other users — vicarious comparison in an unbounded pool damages competence perception; the user's only valid comparison is their own history (also consistent with the privacy architecture).

### 2.5 Implementation Intentions and the Play Grammar

**Mechanism.** An implementation intention is a pre-loaded plan in *if-then* form: "When situation X arises, I will do Y." Gollwitzer's program (1999, *American Psychologist*; meta: Gollwitzer and Sheeran, 2006, *Advances in Experimental Social Psychology* 38, 69–119) shows the plan **binds the cue and the response into a single memory representation**, so that when the cue occurs, initiation is delegated to the environment — *strategic automaticity*. The behavior starts without a fresh deliberative act, which is why implementation intentions keep working under cognitive load, fatigue, and stress — precisely the states where willpower-style strategies fail (and where 2.1's process model says motivation has shifted away from demand). Meta-analytic size: **94 studies, ~8,000+ participants, d ≈ .65** `[REPLICATED]`. A 2025 update meta-analyzing 642 tests reports a smaller behavior-change effect (d ≈ .27) after publication-bias correction `[verify primary at next pass]` — still a real, cheap, reliable effect.

**Boundary conditions (where plans fail).** Three documented failure modes: (1) weak underlying goal commitment — the plan presupposes motivation, it does not create it; (2) **vague cues** — "when I have time" has nothing to bind; concrete times, places, and preceding events work; (3) **multi-step complexity** — the behavior must collapse into a single triggered response, else decompose.

**Complement: mental contrasting.** Oettingen's MCII/WOOP (Wish, Outcome, Obstacle, Plan; Oettingen 2014 — T3): visualize the wish and outcome *briefly*, then dwell on the internal obstacle, then form the if-then plan against that obstacle. Contrasting beats positive fantasizing, which alone *reduces* energy for action. The Obstacle step is what turns "what pulls you off track" from a confession into engineering input.

**Operational math — the Play grammar.**

```
PLAY := WHEN(anchor) + WILL(single verb-phrase) + FOR(duration) + [AT/WITH(context)]
anchor     ∈ { time-of-day ("before noon"), event ("right after lunch"),
              place-condition ("at the desk") }          ← concrete, learnable cue
action     =  one verb phrase, startable within 2 minutes ← no multi-step chains
duration   ∈  [15, 90] min, default 45                    ← sized by calibration (2.1)
```

Examples that pass: "Calculus II — 45 min — right after breakfast, at the desk." · "Call Aisha — 10 min — at 8pm." · "Short night: Organic Chem — 15 min — after first class."
Examples that fail: "Study more" (no cue, no action) · "Do homework, revise notes, and call Aisha" (not single) · "When I feel motivated" (vague cue).

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Gollwitzer & Sheeran (2006), Adv Exp Soc Psych 38, 69–119 | 94 studies, d ≈ .65; effects hold under load | `[REPLICATED]` |
| Gollwitzer (1999), Am Psych 54(7), 493–503 | If-then planning theory; strategic automaticity | foundational |
| Oettingen (2014), Rethinking Positive Thinking (T3) | Mental contrasting + implementation intentions (MCII/WOOP) | T3-supported |
| 2025 implementation-intentions meta (642 tests) | Behavior-change d ≈ .27 after bias correction | `[verify primary at next pass]` |

**Application to BU.**

- `[P0]` **D1: the Play input is grammar-shaped.** BU Lite's one sentence becomes a structured micro-form (three fields or a smart single-line that parses): *anchor / action / duration*. The v6 examples ("Calculus II. 45 min. Before noon.") already match the grammar — now it's enforced. This is the highest-leverage P0 change this phase produces.
- `[P0]` **The play is a plan, not a wish.** Evening Close (or morning Spark) attaches a concrete anchor to tomorrow's play. "Study sometime today" is rejected with one line: "when exactly? pick the trigger."
- `[P0]` **Onboarding Q2 becomes the Obstacle step (D3).** "What pulls you off track?" feeds WOOP directly: the answer seeds play anchors ("after dinner — when the YouTube pull is strongest — go to the desk first"). The obstacle is the cue-source for the plan.
- `[P2]` **The sealed focus session is the WHEN realized.** Sealing a play creates the enforced cue window; drift detection (Phase 4) operates inside it. The grammar's anchor and the Watcher's window are the same variable — one data model.
- `[P3]` **Forge proposals always carry anchors** (D14). A computed play without a concrete when/where is a wish; the chooser must output the full grammar or not propose.
- `[P0]` **Swap = re-commit (D15).** Swapping the play is allowed (autonomy, 2.3) but the replacement must be re-formed in the grammar and re-sealed. The plan stays intact; the authorship stays with the user.

### 2.6 Commitment Devices, Deadlines, and Present Bias

**Mechanism.** Present bias is formalized in the quasi-hyperbolic (β-δ) model: utility over time is discounted exponentially at rate δ, *except* that all immediate outcomes get an extra discount factor β < 1. The gap between β and δ is why "future me" agrees with the plan and "now me" defects. Commitment devices work by restricting the future choice set — Ulysses contracts — so that defection carries a pre-agreed cost. Whether commitment helps depends on whether the person is *naive* (unaware of their present bias — commitment strongly helps) or *partially sophisticated* (aware — hard commitments can hurt if they lock out flexibility the person will legitimately need; the literature favors soft or optional-commitment designs for this group; Bryan, Karlan and Nelson, 2010, *Annual Review of Economics* 2, 671–698).

**Evidence.** Ariely and Wertenbroch (2002, Psychological Science 13(3), 219–224) — 60 students proofreading three texts with costly late penalties: self-imposed (staggered) deadlines improved performance versus a single end-deadline, but students with **evenly spaced externally imposed deadlines performed best** on error detection, punctuality, and earnings `[REPLICATED-directionally]` — self-control by precommitment works, but people space their own deadlines suboptimally. Giné, Karlan and Zinman (2010, AEJ: Applied 2(4), 213–235; the CARES commitment-savings RCT in the Philippines) — smokers deposited money forfeited if they failed a 6-month nicotine test: those offered the account were much more likely to quit (roughly a third versus ~6% among controls at the 6-month mark `[verify exact rates at next pass]` via the J-PAL summary), with effects fading by 12 months. Stakes work; their persistence needs maintenance. A 2026 replication attempt of Ariely & Wertenbroch has appeared `[verify outcome at next pass]`.

**Where BU sits in this space.** BU deliberately contains no money. Its commitment stack, cheapest to strongest: (1) *sealed play* — a stated plan with a stated anchor (verbal commitment + implementation intention); (2) *evening close* — a scheduled self-accountability point; (3) *the fire itself* — a non-monetary, loss-framed object whose value was built by the user's own behavior (the forfeit is the fire's brightness, not cash); (4) *Hard Focus* (P2) — the one true hard commitment, whitelisting apps during sealed sessions. The fire is what makes BU's commitment differ from a to-do app: the cost of defection is visible, accumulated, and personal — but it is information-shaped, not punishment-shaped (2.3), which is the resolution of the deepest tension in this section (Contradiction Card C).

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Ariely & Wertenbroch (2002), Psych Science 13(3), 219–224 | Self-imposed deadlines beat none, lose to evenly-spaced external ones | `[REPLICATED-directionally]` |
| Giné, Karlan & Zinman (2010), AEJ: Applied 2(4), 213–235 | Monetary commitment contracts raise 6-month quit rates; fade at 12 months | `[REPLICATED]` |
| Bryan, Karlan & Nelson (2010), Ann Rev Economics 2, 671–698 | Commitment device theory; naifs benefit most; hard commitments can hurt the partially sophisticated | framework |
| 2026 replication of Ariely & Wertenbroch (Psych Science) | exists; outcome to be read | `[verify at next pass]` |

**Application to BU.**

- `[P0]` **D11: the seal is explicit.** When a play is committed, it enters a visible "sealed" state (small lock line under the play: "sealed for today — swap to change"). Verbal commitment + anchor + visible state = the soft commitment layer, grounded in Ariely-Wertenbroch's self-imposed-deadline result.
- `[P0]` **Anchored deadlines beat fuzzy ones.** The grammar's anchor (2.5) is the deadline. Evidence says self-set deadlines are under-spaced — so the play hint offers *specific* times ("before noon" → offer "9:30 / 11:00 / right after lunch") rather than vague windows.
- `[P2]` **Hard Focus is the honest external-deadline analogue** (D12): the one place BU provides external, evenly-spaced structure the data says outperforms self-imposed. Ship it with the SDT rationale line ("you sealed this") and soft-first defaults (reactance: Phase 4).
- `[P4]` **D13: no monetary stakes, ever.** CARES shows stakes work, but money introduces legal, ethical, and motivational-crowding risks the fire was designed to avoid. The fire is the stake. This is a locked product decision grounded in the evidence but resolved by ethics (Contradiction Card C in Phase 5's ethics audit).
- `[P1]` **Fire death = the pre-agreed cost, made visible.** The commitment logic requires the cost to actually occur: the fire dims and dies on evidence, no exception, no streak-freeze. What happens *next* is governed by 2.7, not by a bailout mechanic.
- `[P0]` **12-month fade lesson (CARES)**: single commitments decay. BU's rhythm (daily seal + evening close + Sunday review) is a *re-commitment cadence* by design — the app re-upps the contract weekly. This is why the Sunday Review is load-bearing, not decorative.

### 2.7 Lapse Psychology: What Happens After You Break

**Mechanism.** Three converging literatures describe the post-lapse moment:

1. **Abstinence violation effect** (Marlatt and Gordon, 1985, *Relapse Prevention* — T3/classic): a single lapse triggers an *internal, stable, global* attribution — "I have no self-control" — and the distress of that attribution drives full collapse. The lapse isn't the problem; the *story about the lapse* is.
2. **The what-the-hell effect** (Herman and Polivy's boundary model of eating): crossing a self-imposed boundary removes the perceived reason to stay inside it ("the day is ruined anyway"), so consumption *accelerates* past where it would have been with no rule at all.
3. **Mood repair**: lapses produce negative affect, and the most available repair behavior is usually the very behavior being restrained — a self-reinforcing loop.

**The counter-mechanism.** Wohl, Pychyl and Bennett (2010, *Personality and Individual Differences* 48(7), 803–808): students who **forgave themselves** for procrastinating before their first exam procrastinated less before the second exam — self-forgiveness broke the loop where guilt perpetuated avoidance `[REPLICATED-consistent]`. Breines and Chen (2012, PSPB 38(9), 1130–1143): across three studies, **self-compassion after failure produced *greater* motivation to improve** than self-esteem-boosting or mood-repairing alternatives — the counter-intuitive core finding: people improve faster after gentle verdicts than harsh ones `[REPLICATED-consistent]`. On the opportunity side, the **fresh-start effect** (Dai, Milkman and Riis, 2014, Management Science 60(5), 1316–1334): temporal landmarks (Mondays, month starts, birthdays) reliably increase goal initiation — aspiration surges are real and *schedulable*. Industry telemetry (Duolingo streak data — T4, leads only) shows streak loss produces disengagement spikes, which is the commercial version of the AVE.

**Operational math — the post-lapse copy matrix.**

| Moment | Banned (attribute to character) | Required (attribute to plan/situation) |
|---|---|---|
| Evening "no" | "You broke your word." | "The plan didn't fit today. What got in the way?" |
| Fire death | "You let it die. Weak." | "The fire went out. Fires do. Relight: 3 small days." |
| Post-lapse next morning | "Make up for yesterday — double play." | "Fresh day. Smallest version: 15 min." |
| Sunday review withered | "You skipped Thursday again. 6 weeks." → keep the fact, strip the verdict | "Thursday skipped — 6 weeks running. The Forge sees it. Smallest fix: one Thursday, 15 min." |

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Marlatt & Gordon (1985), Relapse Prevention (T3) | Abstinence violation effect: attribution drives collapse | classic |
| Herman & Polivy, boundary model (T3) | What-the-hell effect after boundary crossing | classic `[canonical ref at next pass]` |
| Wohl, Pychyl & Bennett (2010), PAID 48(7), 803–808 | Self-forgiveness → less subsequent procrastination | `[REPLICATED-consistent]` |
| Breines & Chen (2012), PSPB 38(9), 1130–1143 | Self-compassion after failure → more self-improvement motivation | `[REPLICATED-consistent]` |
| Dai, Milkman & Riis (2014), Mgmt Science 60(5), 1316–1334 | Fresh-start effect at temporal landmarks | `[REPLICATED]` |

**Application to BU.**

- `[P0]` **D5: the relight ritual is a designed product moment, not a string.** Tap the dead fire → the app offers the smallest possible play (15-min class) → three small tendings rebuild it. Re-initiation is engineered to be *easier* than maintenance, the opposite of v6's "the rebuild is harder" claim — which 2.7's evidence does not support and 2.1's process model actively contradicts. (This is a v6-doc amendment: relight is *sheltered*, not punitive.)
- `[P0]` **Copy matrix above ships in P0** (D7). Every fire-state transition and evening-close response uses the "required" column. The harshness the v6 doc wants ("speaks the truth") lives in the *facts* ("skipped Thursday — 6 weeks"), never in the *character verdicts* ("weak").
- `[P0]` **"Partial" is load-bearing (D2).** The yes/partial/no triad is not politeness — it is AVE prevention. A binary close forces every imperfect day into the lapse bucket and hands the user the what-the-hell cascade. Partial keeps imperfect days inside the ledger as partial evidence (fire weight 0.5, Phase 7 function).
- `[P2]` **Post-"no" sizing rule**: the next day's suggested play auto-scales down (the opposite of compensatory mega-plans, which fail and deepen the loop). "Fresh day. Smallest version: 15 min."
- `[P0]` **Fresh-start scheduling**: the relight button carries landmark framing when available ("New week. Relight?" on Mondays, month starts). Cheap, evidence-backed, and it gives BU's forgiveness mechanic a *trigger* rather than an apology.
- `[P3]` **Sunday Review "what withered" uses the matrix's Sunday row**: fact + smallest fix, zero verdict language. The v6 line "The Forge sees it" is retained — surveillance honesty — with the emotional register set by 2.7's evidence.
- `[P4]` **Streak-freeze analog is rejected for the fire but the door stays open for *planned pauses*** (travel, illness): a declared pause is honest (evidence grade: USER_CONFIRMED) while a silent freeze is not. Open question OQ5.

### 2.8 Self-Monitoring: The Evening Close Mechanism

**Mechanism.** Monitoring goal progress reliably increases goal attainment: Harkin et al. (2016, *Psychological Bulletin* 142(2), 198–229) meta-analyzed 138 tests (~19,951 participants) — prompting people to monitor their progress increased attainment (d ≈ .40) `[REPLICATED]`, with the effect strengthened when monitoring included **reporting or physical recording**, and when progress was made more salient. The mechanism is discrepancy identification: a monitoring prompt forces a comparison between current state and goal state, which either motivates regulation or (rarely, when goal commitment is weak) disengagement. Frequency matters: more frequent monitoring → larger effects. Two caveats: monitoring without any feedback can slightly *hurt* (people misread their own progress), and being observed changes behavior in itself (observation reactivity — relevant to the Watcher, owned by Phases 4/6).

**Operational math.** The Evening Close is a daily monitoring prompt with built-in recording (the answer is written to the ember ledger) and built-in feedback (the fire updates in the same session). All three strengthening conditions — frequency, recording, salient feedback — are satisfied by construction. The only design variable is friction: the effect survives at minimal prompt cost, so the 60-second constraint is evidence-compatible.

**Key studies.**

| Study | Finding | Flag |
|---|---|---|
| Harkin et al. (2016), Psych Bulletin 142(2), 198–229 | Progress monitoring → attainment, d ≈ .40; recording & salience strengthen | `[REPLICATED]` |
| Michie et al. (2013), BCTTv1 | Self-monitoring is among the most effective single behavior-change techniques | `[REPLICATED, Phase 5 audit]` |

**Application to BU.**

- `[P0]` **D2: Evening Close v2 = one question + optional line, ≤60 seconds, every day.** "Did you do it? — did it / partial / no." The daily cadence is the mechanism; skipping must not be free: a no-close evening is *logged* and the fire decays accordingly (D16 — honesty invariant, 2.2's reliability principle).
- `[P0]` **Same-evening feedback, not next-morning.** The fire visibly updates the moment the answer lands. Monitoring without salient feedback is the known-weak configuration; BU closes the loop within the same 60 seconds.
- `[P0]` **No nightly guilt inventory.** One question. Monitoring works by discrepancy identification, not by judgment volume; multi-question nightly interrogations add friction and controlling register (2.3) for no documented gain.
- `[P1]` **The close answer's evidence grade is USER_CONFIRMED** — bridge to Phase 6: the fire weights it accordingly (0.5 in the seed function), and the Life Log preserves the distinction forever. The evening close is honest *about being self-report*.
- `[P0]` **The optional reflection line stays optional, every night.** Forcing reflection nightly converts a monitoring tool into a burden (attrition risk — Phase 5); the literature supports prompting, not compelled reporting.

## 3 · Design Decisions

Every decision: Decision / Evidence / Confidence / Tag. P0 decisions are the build's homework for the 4-week Spark phase.

| # | Decision | Evidence | Confidence | Tag |
|---|---|---|---|---|
| D1 | **Play grammar enforced**: input shaped as WHEN(anchor) + WILL(action) + FOR(duration); vague cues rejected with one-line repair ("when exactly?"); concrete-time suggestions offered | §2.5 (Gollwitzer & Sheeran d ≈ .65; boundary conditions) | High | P0 |
| D2 | **Evening Close v2**: daily, single question (did it / partial / no), optional one-line reflection, ≤60 s; answer updates the fire in the same session | §2.8 (Harkin d ≈ .40; salience), §2.7 (partial = AVE guard) | High | P0 |
| D3 | **Onboarding v2**: Q1 gains "why does it matter?" (internalization anchor); Q2 reframed as WOOP obstacle → seeds play anchors; one belief line "discipline is a system, not a fuel" | §2.3, §2.4 (Yeager targeted framing), §2.5 (MCII), §2.1 (Job et al.) | High | P0 |
| D4 | **Fire = evidence ledger semantics**: never a willpower/battery metaphor; states report verified behavior weighted by evidence grade; computation explainable by users | §2.1 (depletion `[FAILED]`), §2.2 (reliability) | High | P1 |
| D5 | **Relight ritual**: dead fire → smallest play offered (15-min class) → 3 small tendings rebuild; fresh-start framing on landmarks; zero shame copy | §2.7 (Wohl; Breines & Chen; Dai fresh-start), §2.1 (process model) | High | P0 |
| D6 | **Low-energy play sizing** is mechanistic: `size = f(sleep_proxy, historical_completion)`; delivered with one calm line, no energy talk | §2.1 (process models; no resource) | Medium | P2 |
| D7 | **Copy ban-list ships as a lint file**: banned — character verdicts, resource blame, controlling imperatives ("must", "no excuses"), comparative ranking; the post-lapse copy matrix is normative | §2.3, §2.4, §2.7 | High | P0 |
| D8 | **Process-praise rules**: praise behavior + strategy ("started before noon — that's the pattern that works"); struggle-triggered effort+strategy copy after "no" closes or fire drops | §2.4 (Yeager: targeted works, generic weak) | Medium | P0/P3 |
| D9 | **Identity-streak framing** replaces numeric streak display: "You've shown up 4 of the last 7 days. That's who you are now." | §2.4 (Credé: perseverance signal real; numeric-streak hazards → Phase 5) | Medium | P3 |
| D10 | **Gorgeous gate = mastery ledger milestone**: 30 *verified* days; nothing redeemable; visible history of evidence-graded tendings is the reward surface | §2.4 (Bandura mastery #1), §2.3 (no crowding-out) | High | P3 |
| D11 | **Sealed-play state is explicit** ("sealed for today — swap to change"): verbal commitment + anchor + visible lock state | §2.6 (Ariely & Wertenbroch self-imposed deadlines help) | High | P0 |
| D12 | **Hard Focus soft-first**: ship as opt-in external structure with SDT rationale line; not the default discipline tool | §2.6 (external structure outperforms; hard commitments hurt the partially sophisticated), §2.3 | Medium | P2 |
| D13 | **No monetary stakes, ever**: the fire is the forfeit; CARES-class money contracts rejected | §2.6 (stakes work but ethics/crowding) | Medium | P4 |
| D14 | **Forge proposals always carry the full grammar** — no anchor, no proposal (a computed wish is a bug) | §2.5 (cue-concreteness requirement) | High | P3 |
| D15 | **Swap = re-commit**: replacement re-formed in grammar and re-sealed; authorship stays with user | §2.3 (autonomy), §2.5 (plan integrity) | High | P0/P3 |
| D16 | **Skipped closes are logged honestly**: no-close evening → fire decays; no silent forgiveness mechanics | §2.8 (monitoring needs recording), §2.2 (BU as reliable environment) | Medium | P0/P1 |
| D17 | **Watcher consent autonomy-framed**: explicit scope choice, rationale, reversible, "fire returns to manual" path visible | §2.3 (control backfires) | High | P1 |
| D18 | **No trait-change promises** anywhere user-facing (store listing, onboarding, fire copy): promise conditions (cue, plan, record), not trait rewrites | §2.1, §2.2 (both replication verdicts) | High | P0 |

**v6-doc amendments produced by this phase** (change log for `PLAN.md`): (1) relight is sheltered, not harder (D5, reverses §3's "the rebuild is harder than maintaining"); (2) the harsh tone is constrained to facts, never character verdicts (D7); (3) the Play input becomes grammar-shaped rather than free-form (D1).

## 4 · Contradiction Cards

**Card A — Structure vs. autonomy.** External, evenly-spaced deadlines beat self-imposed ones (§2.6), yet SDT says controlling structure backfires (§2.3). *Working resolution:* BU plays the external role only inside windows the user sealed — propose-and-confirm architecture, swap always available, rationale always attached. The external structure is user-installed, which is the difference between a scaffold and a cage. *Flip condition:* if N=1 data shows swaps are habitual escape hatches (swap rate > ~30% of days), tighten re-commit friction and revisit.

**Card B — The app goes dark vs. support.** v6 withholds the app's value when the fire dies; SDT's relatedness need and standard retention science warn abandonment reads as rejection and accelerates churn. *Working resolution:* dark ≠ silent contempt. Even OUT-state: the journal, the Life Log, and the relight path remain one tap away (the embers are always the user's — Bedrock invariant), and the persistent notification (P1) states the truth without contempt ("BU is waiting. Tap to relight."). What goes dark is the *coaching*, not the *ownership*. *Flip condition:* Phase 5's attrition evidence if BU-class churn fires early.

**Card C — Loss-framed fire vs. shame risk.** Loss aversion is real, and v6's fire is a loss-framed object; but self-attributed loss triggers the AVE cascade (§2.7). *Working resolution:* loss attaches to the *object* (the fire dims — visible, factual), never to the *self* ("you are a failure" banned); the relight path is always visible at the moment of loss. The user sees what was lost and the smallest step that returns it. *Flip condition:* if fire-death weeks show worse next-week completion than fire-high weeks (OQ4), the loss framing is failing despite the copy rules — re-examine state thresholds, not just copy.

**Card D — "Speaks the truth" branding vs. willpower mythology.** The discipline-market tone ("no excuses," "grind") sells, but its psychology is precisely what failed replication (§2.1) and what produces AVE collapse (§2.7). *Working resolution:* harsh register is permitted toward the *system* ("the app does not negotiate; the fire does not freeze"), soft register toward the *person* ("fires do. relight: 3 small days."). The truth BU speaks is evidence honesty, not emotional harshness.

## 5 · Open Questions + N=1 Experiment Queue

Single-case designs formalized in Phase 6; seeded here so the builder can start during P0.

- **OQ1 · Fire loss-framing.** Does loss-framed fire feedback increase next-week tending vs. neutral framing? *Design:* ABAB across weeks (copy variants), outcome = tending rate. [P0-testable]
- **OQ2 · Graded honesty.** Does the partial option increase honest closes (less gaming) without reducing completion? *Design:* interruptible time series on self-report distribution after enabling partial. [P0-testable]
- **OQ3 · Grammar compliance.** Do grammar-valid plays complete more often than free-form ones? *Design:* track completion by grammar-validity flag over 8 weeks. [P0-testable]
- **OQ4 · Relight effectiveness.** After fire death, does the ritual (small play + landmark framing) reach BLAZING-within-14-days more often than ad-hoc return? *Design:* interrupted time series across fire-death events. [P2-testable]
- **OQ5 · Planned pauses.** Do declared pauses (USER_CONFIRMED) protect the next-week tending rate vs. silent gaps? *Design:* within-user comparison of post-pause vs. post-gap weeks. [P4-testable]
- **OQ6 · Chronotype interaction.** Does the completion rate of "before noon" anchored plays hold after controlling for the user's actual sleep window? *Design:* completion by anchor-time vs. sleep proxy; feeds Phase 4. [P0-data, P2-analysis]

**Research debt (verified-number gaps for the next pass):** RRR exact effect size and N; CARES exact quit-rate percentages; Multon et al. ρ; Harkin et al. exact k and N as printed; the 2025 implementation-intentions meta primary; the 2026 Ariely–Wertenbroch replication outcome; Herman & Polivy canonical citation; SDT health-app design primary.

## 6 · Sources

**T1/T2 — opened or verified this pass.**

- Baumeister, R. F., Bratslavsky, E., Muraven, M., & Tice, D. M. (1998). Ego depletion: Is the active self a limited resource? *JPSP*, 74(5), 1252–1265. doi 10.1037/0022-3514.74.5.1252
- Carter, E. C., Kofler, L. M., Forster, D. E., & McCullough, M. E. (2015). A series of meta-analytic tests of the depletion effect. *JEP: General*, 144(4), 796–815. doi 10.1037/xge0000083
- Hagger, M. S., & Chatzisarantis, N. L. D. (2016). A multilab preregistered replication of the ego-depletion effect. *Perspectives on Psychological Science*, 11(4), 546–573. doi 10.1177/1745691616652873
- Watts, T. W., Duncan, G. J., & Quan, H. (2018). Revisiting the marshmallow test. *Psychological Science*, 29(7), 1159–1177. doi 10.1177/0956797618761661 · pmc.ncbi.nlm.nih.gov/articles/PMC6050075
- Mischel, W., Ebbesen, E. B., & Zeiss, A. R. (1972). Cognitive and attentional mechanisms in delay of gratification. *JPSP*, 21(2), 204–218.
- Shoda, Y., Mischel, W., & Peake, P. K. (1990). Predicting adolescent cognitive and self-regulatory competencies from preschool delay of gratification. *Developmental Psychology*, 26(6), 978–986.
- Kidd, C., Palmeri, H., & Aslin, R. N. (2013). Rational snacking. *Cognition*, 126(1), 109–114. `[EMERGING]`
- Ryan, R. M., & Deci, E. L. (2000). Self-determination theory and the facilitation of intrinsic motivation, social development, and well-being. *American Psychologist*, 55(1), 68–78.
- Deci, E. L., Koestner, R., & Ryan, R. M. (1999). A meta-analytic review of experiments examining the effects of extrinsic rewards on intrinsic motivation. *Psychological Bulletin*, 125(6), 627–668.
- Duckworth, A. L., Peterson, C., Matthews, M. D., & Kelly, D. R. (2007). Grit: Perseverance and passion for long-term goals. *JPSP*, 92(6), 1087–1101.
- Credé, M., Tynan, M. C., & Harms, P. D. (2017). Much ado about grit. *JPSP*, 113(3), 492–511. doi 10.1037/pspp0000102 · pubmed.ncbi.nlm.nih.gov/27845531
- Sisk, V. F., Burgoyne, A. P., Sun, J., Butler, J. L., & Macnamara, B. N. (2018). To what extent and under which circumstances are growth mind-sets important to academic achievement? Two meta-analyses. *Psychological Science*, 29(4), 571–585. doi 10.1177/0956797617739704
- Yeager, D. S., et al. (2019). A national experiment reveals where a growth mindset improves achievement. *Nature*, 573, 364–369. doi 10.1038/s41586-019-1466-y
- Bandura, A. (1977). Self-efficacy: Toward a unifying theory of behavioral change. *Psychological Review*, 84(2), 191–215.
- Multon, K. D., Brown, S. D., & Lent, R. W. (1991). Relation of self-efficacy beliefs to academic outcomes. *J. Counseling Psychology*, 38(1), 30–38. `[ρ verify]`
- Gollwitzer, P. M., & Sheeran, P. (2006). Implementation intentions and goal achievement: A meta-analysis of effects and processes. *Advances in Experimental Social Psychology*, 38, 69–119.
- Gollwitzer, P. M. (1999). Implementation intentions: Strong effects of simple plans. *American Psychologist*, 54(7), 493–503.
- Ariely, D., & Wertenbroch, K. (2002). Procrastination, deadlines, and performance: Self-control by precommitment. *Psychological Science*, 13(3), 219–224. doi 10.1111/1467-9280.00441 · pubmed.ncbi.nlm.nih.gov/12009041
- Giné, X., Karlan, D., & Zinman, J. (2010). Put your money where your butt is: A commitment contract for smoking cessation. *AEJ: Applied Economics*, 2(4), 213–235. doi 10.1257/app.2.4.213
- Bryan, G., Karlan, D., & Nelson, S. (2010). Commitment devices. *Annual Review of Economics*, 2, 671–698.
- Wohl, M. J. A., Pychyl, T. A., & Bennett, S. H. (2010). I forgive myself, now I can study. *Personality and Individual Differences*, 48(7), 803–808.
- Breines, J. G., & Chen, S. (2012). Self-compassion increases self-improvement motivation. *PSPB*, 38(9), 1130–1143.
- Harkin, B., et al. (2016). Does monitoring goal progress promote goal attainment? A meta-analysis. *Psychological Bulletin*, 142(2), 198–229. pubmed.ncbi.nlm.nih.gov/26479070
- Dai, H., Milkman, K. L., & Riis, J. (2014). The fresh start effect. *Management Science*, 60(5), 1316–1334.
- Inzlicht, M., & Schmeichel, B. J. (2012). What is ego depletion? *Perspectives on Psychological Science*, 7(2), 162–176.
- Inzlicht, M., Shenhav, A., & Olivola, C. Y. (2018). The effort paradox. *Trends in Cognitive Sciences*, 22(4), 334–345.
- Job, V., Dweck, C. S., & Walton, G. M. (2010). Ego depletion — Is it all in your head? Implicit theories about willpower. *Psychological Science*, 21(10), 1473–1478.

**T3 — context only.** Baumeister & Tierney (2011), *Willpower*; Dweck (2006), *Mindset*; Duckworth (2016), *Grit*; Bandura (1997), *Self-Efficacy*; Oettingen (2014), *Rethinking Positive Thinking*; Marlatt & Gordon (1985), *Relapse Prevention*; Herman & Polivy boundary model writings `[canonical ref at next pass]`.

**T4 — leads, not evidence.** J-PAL CARES evaluation page (povertyactionlab.org); APS Observer marshmallow summary (psychologicalscience.org, June 2018); replicability-index.com ego-depletion report (2016); APA EurekAlert release on Harkin et al. (2016); implementation-intentions explainer citing the 2025 642-test meta (yukaichou.com) `[primary to locate]`; Duolingo streak engineering posts `[locate for Phase 5]`.

**Cross-references.** Fire-state scoring function and evidence-grade weights → Phase 7 (seed already uses this phase's partial=0.5 / self-report weighting). Intervention thresholds and drift → Phase 4. Ladder-as-JITAI, streak economics, gamification ethics → Phase 5. Evidence-grade operational criteria → Phase 6. Learning-side of the Play (retrieval-first study sessions) → Phase 2.

---

*End of Phase 1. Tracker updated in `RESEARCH-PLAN.md` Appendix C. Next: "Research Phase 2" produces `phase2-learning.md` — the science of the Play's content: retrieval practice, spacing, the FSRS memory model, the Feynman Technique, and the ZPD calibration for difficulty.*


