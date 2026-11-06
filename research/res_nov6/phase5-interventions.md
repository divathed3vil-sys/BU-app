# BU Deep Research Program — Phase 5: Behavior-Change Engineering — The Science of the Ladder

**Document** `research/phase5-interventions.md` · **Version** 1.0 · **Date** 2026-11-05 · **Prepared for** Dhivakaran · BU (Galaxy A54 build)
**Scope** Master Plan §8 (`RESEARCH-PLAN.md` v1.0): app-effectiveness and attrition evidence, the JITAI formalization of the intervention ladder, the COM-B/BCW/BCTTv1 audit of BU v6, streaks and loss framing, gamification verdicts, goal-setting/goal-gradient/fresh-start mechanics, FBM and Tiny Habits, temptation bundling and social accountability, and the ethics line (dark patterns; the goes-dark mechanic).
**Owning build decisions** the ladder-as-JITAI decision table (P2), BU's notification and Evening Close copy guide (P0), the engagement-decay countermeasure plan (P0), streak/fire framing rules (P3, P0 seed), temptation-bundling and social-commitment feature verdicts (P4), the BCT audit of v6 (all phases), and the goes-dark ethics amendment (P0).
**Serves** BU v6 "The Fire" and the P0 Spark build. Supersedes the v0 draft's engagement and gamification sections (their sourcing habits end here).
**Template** per Master Plan §2. Confidence vocabulary: **High** (T2 meta or multiple T1 replications) · **Medium** (consistent T1, no meta) · **Low** (single studies / `[EMERGING]`).

---

## 1 · TL;DR — What This Phase Locks

1. **The biggest threat to BU is not drift — it is abandonment.** The median popular mental-health app has a **4.0% daily open rate and 3.3% 30-day retention** (Baumel et al. 2019, *JMIR*, 93 apps); pooled dropout in app interventions is **43%** (Meyerowitz-Katz et al. 2020). BU's countermeasure is structural, not mechanical: a low-novelty daily ritual (Play + Evening Close), a relight ritual for the failure case, and **no re-engagement machinery** (D3).
2. **The ladder becomes a formal JITAI.** Nahum-Shani et al.'s six-component frame (decision points, tailoring variables, intervention options, decision rules, proximal/distal outcomes) maps onto Phase 4's ladder v1.1 almost cell for cell; D1 delivers the full decision table, including the receptivity rule — never intervene during calls, inside stress windows beyond rung 1, or after the dismissal cool-down arms.
3. **The fire is not a streak, and Phase 5 bans everything that would make it one.** Loss aversion (λ ≈ 2.25, Tversky & Kahneman 1992) explains why streaks and dimming fire both bite; identity framing (Bryan et al. 2011, *PNAS*) is why "You've shown up 4 of last 7 days" can stay. Numeric counters, freeze tokens, countdown rings, and loss-urgency copy are banned (D4): loss is used *structurally*, never *rhetorically*.
4. **The 93-technique audit shows v6 already carries the high-utility techniques and deliberately lacks the low-utility ones.** Self-monitoring (2.3), action planning (1.4), prompts/cues (7.1), habit formation (8.3), antecedent control (12.1/12.3) are present; badges, points, and leaderboards are absent by design (D6) — the one lever BU refused is the one the reward-undermining literature (Deci, Koestner & Ryan 1999) says to refuse.
5. **"The app goes dark" survives the ethics review with one amendment.** What dims is the *coaching layer*; the user's own record — journal, Life Log, exports — is never withheld, return is one tap plus one play, and shame copy is banned (D7). The v6 doc's ladder copy and block semantics are amended to match Phase 4.

---

## 2 · Science

### §2.1 · Do Behavior-Change Apps Work — and Does Anything Survive the Attrition Cliff?

**Mechanism.** A behavior-change app is a deliverable wrapper around interventions of varying quality. The literature decomposes its failure into two independent questions: *efficacy* (does the wrapped intervention change behavior under supported conditions?) and *survival* (does anyone still open the app by the time the intervention could have worked?). Conflating them is the field's most common error, and it is BU's biggest strategic risk: BU can be effective and still die, because the wrapper is what users abandon.

**The efficacy record — positive but modest.** For app-based interventions, the best current T2 anchor is Linardon et al. (2019, *World Psychiatry* 18:325–336): a meta-analysis of **66 RCTs** of app-supported smartphone interventions for mental health problems, finding apps significantly outperform control conditions on depressive symptoms (**g ≈ 0.28**, 54 comparisons) and generalized anxiety (**g ≈ 0.30**, 39 comparisons) `[REPLICATED-consistent; T2]`. That is a real but small-to-small-moderate effect — and these trials measured *supported* use with structured follow-up. For broader behavior-change apps, systematic reviews of physical-activity and lifestyle apps report effects in the same small range with heavy heterogeneity `[T2-class; per-review numbers debt-listed]`. Nothing in this record supports the marketing claim of transformation; everything in it supports *modest average effects, conditional on sustained use*.

**The survival record — where apps actually die.** Three independent lines quantify the cliff:

- **Eysenbach's law of attrition** (2005, *JMIR* 7(1):e11 — the foundational statement): "in any eHealth trial a substantial proportion of users drop out before completion or stop using the application." Eysenbach separates **dropout attrition** (lost to follow-up) from **nonusage attrition** (still answering surveys, no longer using the app) — the distinction matters for BU because an app's *install base* and its *active-tending base* are different populations. His data points: 12 of 1,161 (~1%) completed a full 12-week program in an open setting; 97 of 19,607 (0.5%) completed all five modules of an open-enrollment intervention; even in a trial setting only 41 of 182 (22.5%) completed all modules `[T1-venue]`. His prescription: treat attrition as the primary object of study — survival curves, usage half-life, "usability efficacy" — not as noise.
- **Real-world usage panels.** Baumel, Muench, Edan & Kane (2019, *JMIR* 21(9):e14567) analyzed 93 popular mental-health Android apps (median 100,000 installs) from an independent usage panel: **median daily open rate 4.0%** (IQR 4.7%); **median 15-day retention 3.9%** (IQR 10.3%); **median 30-day retention 3.3%** (IQR 6.2%). Technique category predicted survival — tracker apps (median 6.3% daily open) and peer-support apps (17.0%) beat breathing-exercise apps (1.6%), and on day 30 breathing apps sat at **0.0% median retention** `[T2-grade panel data]`. Two structural facts for BU hide in that table: *self-tracking mechanics outperform relaxation-content mechanics*, and *usage peaks in the evening* for most technique classes — the exact window BU's Evening Close occupies.
- **Intervention-trial dropout.** Meyerowitz-Katz et al. (2020, *JMIR* 22(9):e20283) pooled 17 app-based chronic-disease studies: **pooled dropout 43%** (95% CI 29–57), higher in real-world observational settings (49%) than controlled RCTs (40%), with extreme heterogeneity (I² > 99%) `[T2]`. Their qualitative synthesis ties dropout to burden, relevance, and feedback quality — not to insufficient gamification.

**The engagement question.** Whether *more* engagement causes *more* outcome is not settled — heavily engaged users differ from dropouts in unmeasurable ways, and the field has no clean causal identification for "engagement → benefit" in self-directed use `[CONTESTED; treatment-effect identification unresolved]`. What is settled is the reverse dependency: no sustained use, no intervention at all. For BU this inverts the usual engineering priority — survival is the gating constraint, efficacy the design inside it.

**What this means for BU.**

- `[P0]` **BU's countermeasure is a ritual, not a retention feature** (D3). The two-touch daily loop (write the Play, close the evening) is deliberately the *lowest-novelty* design in its category: no novelty decay because there is no novelty to decay. The blank-page journal and the five-state manual fire are constant; their meaning is what deepens.
- `[P0]` **BU never adds re-engagement machinery.** No win-back pushes, no streak-rescue tokens, no "we miss you" copy. The v6 position — "Free apps chase you. BU waits." — is not bravado; it is the only stance consistent with the ban-list this program has accumulated (Phases 1/3/4) and with the finding that push-pressure mechanics do not fix the survival curve, they rent it.
- `[P1]` **Survival metrics are a first-class instrument**, per Eysenbach's prescription: close-completion rate, relight conversion after OUT, days-between-opens distribution. These are the numbers that tell BU whether it is dying; drift-per-session tells it whether the ladder works. Both ship in P1 analytics (D3).
- `[all]` **The 4-week P0 build horizon is honest about this data.** Most users of most apps are gone in a month; BU's P0 user is one builder who has already decided. The app's design cost of that honesty is higher churn among non-builders — accepted explicitly (D7, Card B).

**Key studies — attrition and efficacy.**

| Study | Design | Finding | Flag |
|---|---|---|---|
| Eysenbach (2005, *JMIR* 7(1):e11) | Foundational review/argument, eHealth usage data | Law of attrition; nonusage ≠ dropout; ~0.5–22.5% completion across settings; survival analysis demanded | `[T1-venue, foundational]` |
| Linardon et al. (2019, *World Psychiatry* 18:325–336) | Meta-analysis, 66 RCTs | Apps beat controls: g ≈ 0.28 (depression, n=54), g ≈ 0.30 (GAD, n=39) | `[REPLICATED; T2]` |
| Baumel et al. (2019, *JMIR* 21(9):e14567) | Panel-based usage analysis, 93 apps | Median daily open 4.0%; 30-day retention 3.3%; trackers > content apps; evening peak | `[T2-grade panel]` |
| Meyerowitz-Katz et al. (2020, *JMIR* 22(9):e20283) | Systematic review + meta, 17 studies | Pooled dropout 43% (CI 29–57); worse in real-world settings | `[T2]` |
| Harkin et al. (2016, *Psych Bulletin*) | Meta-analysis, 138 studies | Progress monitoring d ≈ .40 — the efficacy engine Evening Close is built on (Phase 1 §2.7) | `[REPLICATED; T2; bridged]` |

---

### §2.2 · The JITAI Frame — The Ladder, Formalized

**Mechanism.** A just-in-time adaptive intervention provides "the right type/amount of support, at the right time, by adapting to an individual's changing internal and contextual state" (Nahum-Shani et al., *Annals of Behavioral Medicine* 52(6):446–462; online 2017; PMC5364076). The framework specifies **six components** (definitions verbatim from the paper's Table 1):

1. **Decision points** — "Points in time at which an intervention decision must be made." They may occur (a) at pre-specified time intervals, (b) at specific times of day or week, or (c) following random prompts; in practice also at *event* triggers.
2. **Tailoring variables** — "Information concerning the individual that is used for individualization (i.e., to decide when and/or how to intervene)." Can be near-term (current context) or distal (history, traits).
3. **Decision rules** — "A way to operationalize the adaptation by specifying which intervention option to offer, for whom, and when… link the intervention options and tailoring variables in a systematic way."
4. **Intervention options** — "Array of possible treatments/actions that might be employed at any given decision point" (types, sources, modes, amounts).
5. **Proximal outcomes** — the short-term targets each intervention option aims to move (mediators or intermediate measures).
6. **Distal outcomes** — the ultimate goal the JITAI exists to serve.

The paper's worked example (FOCUS, a smartphone intervention for schizophrenia) is structurally a decision table: decision points = three daily prompts; tailoring variables = status in five domains; decision rules = if engaged & difficulty → self-management strategies, if engaged & no difficulty → feedback and reinforcement, **if prompt ignored → no intervention**. That last rule is the one most apps violate and BU v1.1 already honors (Phase 4 D2's rung-0 silence, one-nudge cap).

**The receptivity principle.** Nahum-Shani et al. emphasize providing support "only when the person is receptive… a function of both internal (e.g., mood) and contextual (e.g., location) factors." A JITAI that fires regardless of receptivity is a spammer with better math. This gives Phase 4's guardrails a formal home: the never-counts list, the stress-window cap, and the dismissal cool-down are *receptivity constraints*, not UX courtesy.

**Evidence status — honest.** JITAIs are a young literature. The framework paper is a design methodology, not an efficacy proof; randomized evidence for adaptive-over-static superiority in consumer apps is thin, and the field's own reviews say so (a 2026 Annual Review of Psychology assessment of JITAIs, Nahum-Shani et al., exists and is the current state-of-the-art summary `[T2-venue lead; page-level check debt-listed]`). The methodological standard for evaluating a JITAI is the **micro-randomized trial** (randomize each decision-point occasion), which BU cannot run on N=1 — its honest N=1 analogue is the within-user A/B of Phase 6's playbook. BU therefore adopts the JITAI *structure* (it forces every intervention to justify its decision point and carry its decision rule) without claiming JITAI *efficacy* — and it keeps every threshold labeled set-and-tested exactly as Phase 4 D2 did.

**What this means for BU.**

- `[P2]` **The ladder is specified as a decision table, not as escalation vibes** (D1): every rung gets a decision-point definition, tailoring variables, rule, proximal outcome, and copy class. This is what makes the ladder auditable — any future cell change (thresholds, copy) edits a table row, not code folklore.
- `[P0]` **Evening Close and play reminders are decision points too** — the only two BU ships in P0 (Phase 4 D3). Their rules are as formal as the ladder's: fire once at the boundary, never mid-seal, never repeat.
- `[P3]` **The educational-exception flow (v6 §7) is a JITAI decision rule with a tailoring variable** (title classification × user confirmation) and enters the table at P3 exactly as Phase 4 D7 constrained it: user-declared tool lists only, title-only auto-classification banned.
- `[all]` **Interventions BU will never put in the options array** (from this phase's evidence): streak-freeze tokens, milestone-bribery, loss-urgency copy, social comparison, re-engagement pushes. The options array is bounded by the ban-lists of Phases 1, 3, 4, and 5 — the ladder can only ever grow *calmer*.
---

### §2.3 · The Audit Lens — COM-B, the Behaviour Change Wheel, and 93 Techniques

**Mechanism.** Three linked instruments from the UCL behaviour-change group give BU a common vocabulary for auditing itself.

- **COM-B** (Michie, van Stralen & West 2011, *Implementation Science* 6:42): behavior (B) requires **C**apability (physical, psychological), **O**pportunity (physical, social), and **M**otivation (reflective, automatic) — all three, not just motivation. The Behaviour Change Wheel wraps COM-B in nine intervention functions (education, persuasion, incentivisation, coercion, training, restriction, environmental restructuring, modeling, enablement) and seven policy categories. COM-B's diagnostic claim is BU-relevant beyond audit: a failed play is diagnosed as *capability* ("I don't know how"), *opportunity* ("my room is full of feeds"), or *motivation* ("I didn't care today") — three different problems with three different remedies, only one of which is "push harder" `[T2-grade framework; structure cited from the paper]`.
- **BCTTv1** (Michie et al. 2013, *Annals of Behavioral Medicine* 45(1):125–135): a consensual taxonomy of **93 behavior change techniques in 16 clusters** (Goals and planning; Feedback and monitoring; Social support; Shaping knowledge; Natural consequences; Comparison of behaviour; Associations; Repetition and substitution; Comparison of outcomes; Reward and threat; Regulation; Antecedents; Identity; Scheduled consequences; Self-belief; Covert learning), built from 40 taxonomies with international expert consensus and reliability testing `[T2; cluster/code structure per the standard taxonomy — page-level re-verification debt-listed]`. Its purpose is precise labeling of what an intervention actually contains — which is exactly what a BCT audit of BU v6 needs.

**Why audit instead of imitate.** The taxonomy's own discipline cuts both ways: it labels techniques; it does not certify them. The technique set an app *contains* is a design statement — most consumer behavior apps cluster in the Reward and Threat cluster because it is cheap to build, not because it works. The meta-analytic record (Phase 1 for rewards; Harkin 2016 for monitoring; this phase for gamification) says the high-yield clusters are the unglamorous ones: Goals and planning, Feedback and monitoring, Antecedents, Repetition and substitution. A technique-by-technique audit of v6 against BCTTv1 therefore does two jobs at once: it confirms BU's existing techniques are the evidence-favored ones, and it makes BU's *absences* explicit and principled (D6).

**What this means for BU.**

- `[all]` **The audit table (D6) is the permanent artifact**: v6 feature ↔ BCT code(s) ↔ build phase ↔ verdict (keep / modify / abstain). Gaps are flagged with the phase that could act on them.
- `[P0]` **COM-B language enters the builder's own debugging**: when a play fails, the Evening Close's optional lines already let the user record why — and Phase 1 D3's obstacle+plan (WOOP) is the remedy path. What COM-B adds is the discipline to distinguish a *sizing* problem (capability), an *environment* problem (opportunity — fixable by the ladder/whitelist), and a *meaning* problem (motivation — fixable by identity framing, not urgency).
- `[P1+]` **Coercion is a named intervention function BU refuses.** The BCW permits it; BU's charter does not (Phase 1 D7's ban-list; D7 below). Naming it is what makes the refusal auditable.

---

### §2.4 · Streaks, Loss, and Identity — Why the Fire Bites and the Streak Rots

**Mechanism 1 — loss aversion.** Prospect theory's central quantitative fact: losses loom larger than equivalent gains. In the canonical estimation (Tversky & Kahneman 1992, *Journal of Risk and Uncertainty* 5:297–323), the median loss-aversion coefficient is **λ ≈ 2.25** — losing something hurts roughly twice as much as gaining it pleases `[REPLICATED as a robust regularity across estimation studies; exact-population variation debt-listed]`. Any mechanic that lets the user accumulate a visible count converts *every future day* into a potential loss — that is the psychological engine of streaks, and it works. The engineering question is not "does it motivate" but "what does it motivate, at what fragility, and what does it do to the user's identity when it breaks" — the questions Duolingo-class products answer with freeze tokens, and the questions BU answers by never installing a numeric count in the first place.

**Mechanism 2 — streaks as pre-installed losses.** A streak is a *commitment device the app holds in trust*, and its break is a guaranteed loss event. The vendor record (Duolingo's engineering blog posts; T4) credits streaks — plus freezes, weekend protection, and "Streak Society" status tiers — with measurable retention gains, and there is no reason to doubt the vendor's internal numbers as far as they go `[T4 vendor data; leads only]`. What the vendor record cannot answer: the peer-reviewed streak literature is thin to absent for self-directed behavior apps — this phase's searches located no strong direct-replication base for streak mechanics in non-mandated settings `[research-debt: streak-specific peer-reviewed studies not located this pass]`. BU's position is therefore not "streaks don't work" (they plausibly do, as loss aversion predicts) but "streaks work by installing a fragile loss the app then sells protection against — a mechanic BU declines to run."

**Mechanism 3 — identity framing beats counting.** Bryan, Walton, Rogers & Dweck (2011, *PNAS* 108(31):12653–12656) showed in three randomized experiments that subtle linguistic cues invoking the self — "to be a voter" (noun) versus "to vote" (verb) — increased reported intention, with a large-scale field experiment assessing actual turnout `[T1; effect magnitude and later replication status debt-listed — treat as promising, not settled]`. The mechanism: noun framing makes the action a *claim about who you are*, which people defend. This is the evidence class behind v6's identity line — "You've shown up 4 of last 7 days. That's who you are now." — and it is why the line survived the audit while "don't break the chain" cannot: the first asserts accumulated evidence about identity; the second threatens a count.

**Mechanism 4 — resets are double-edged.** Dai (2018, *OBHDP* 148:12–29) shows that resetting a performance metric helps strugglers (a clean slate) but demotivates high performers (their accumulated momentum is erased) `[T1-venue; page-level check debt-listed]`. Applied to BU: the fire's 7-day rolling window *is* a soft reset — every week, the state is recomputed from tendings, not from history. That is exactly right for a struggling week and exactly honest for a strong one (Blazing is never "banked"). It also means BU must never manufacture fake fresh starts *on top of* the rolling window — Card C.

**What this means for BU.**

- `[P0]` **The manual fire already is the loss mechanic — kept structural, kept quiet.** Five states, dims on drift, relights on tendings. No number attached (v6: "the state is the verdict"), no freeze, no token. The manual 5-state fire is honest precisely because it cannot count.
- `[P1+]` **The identity line ships only with rules** (D4): frequency claims must be true per evidence grades, phrased as statements about showing up ("4 of last 7"), never as counts under threat ("day 5 — protect it"). No percentile, no comparison, no milestone countdown.
- `[all]` **No streak-freeze-class mechanic will ever ship** — not at any phase tag. The freeze is the tell: it reveals the mechanic as fragile-loss-then-insurance. The fire's honest version of a freeze is the relight ritual (Phase 1 D5): you can always come back, and coming back costs a play, not a token.

---

### §2.5 · The Onboarding Math — Goal-Setting, Goal Gradients, Fresh Starts

**Goal-setting theory.** Locke & Latham (2002, *American Psychologist* 57(9):705–717), summarizing their 35-year program: **specific, difficult goals reliably outperform "do your best" goals** across hundreds of laboratory and field studies, with the effect mediated by direction, effort, persistence, and strategy development `[REPLICATED; T2-grade review program; per-meta effect sizes debt-listed]`. The boundary conditions matter as much as the headline: goals must be *accepted*, feedback must exist (the monitoring meta-analysis closes that loop — Harkin 2016, d ≈ .40), and the person needs goal-relevant ability and commitment. The famous caveat — Ordóñez, Schweitzer, Galinsky & Bazerman (2009, *Academy of Management Perspectives* 23(1):6–16, "Goals gone wild") — documents the side effects of over-prescribed stretch goals: narrowed attention, unethical shortcuts, reduced intrinsic interest `[T1-venue; page-level check debt-listed]`. BU's design sits deliberately on the safe side of that line: one specific play per day ("Calculus II. 45 minutes. Before noon." — v6), difficulty *sized down* on bad days (Phase 3 D3/D8), and no goal system beyond the single sentence.

**Goal gradients.** Kivetz, Urminsky & Zheng (2006, *Journal of Marketing Research* 43(4):529–539) demonstrated the goal-gradient hypothesis in real reward programs: **effort accelerates as the reward approaches** — purchase velocity rises with proximity to the free item `[T1-venue; per-study magnitudes debt-listed]`. Its sibling, the **endowed progress effect** (Nunes & Drèze 2006, *Journal of Consumer Research* 33(3):303–311): car-wash customers given a 10-stamp card with 2 pre-stamped stamps (8 purchases still required) completed at **34% versus 19%** for the plain 8-stamp card — artificial progress accelerates completion `[T1-venue; numbers consistent across independent secondary reports; primary page-level check debt-listed]`. BU's exposure: the 7-day fire window creates a real gradient (Sunday is closer to a full week than Monday), and Phase 7's scoring function will make tendings accumulate *visibly*. The endowed-progress trap is available too — BU could pre-stamp onboarding ("your fire starts at Flickering"). Verdict: the fire starts at its evidence-graded truth, whatever that is; pre-stamping installs a lie the user must later unlearn (D7's honesty invariant). The gradient BU keeps is the honest one: tendings accumulate, the week fills, the state rises.

**Fresh starts.** Dai, Milkman & Riis (2014, *Management Science* 60(10):2563–2582), across three archival field studies (Google "diet" searches, gym attendance, goal-setting site visits): aspirational behavior increases immediately following **temporal landmarks** — New Year's, birthdays, month starts, Mondays — because landmarks create a psychological fresh-slate accounting `[T2-grade; magnitudes vary by study and are debt-listed]`. The effect is an *initiation* lever, not a *maintenance* one — the boost decays within the new period (their own framing; consistent with the follow-up literature) `[T2-class]`. And Dai (2018, above) adds the warning: engineered resets backfire on the successful.

**What this means for BU.**

- `[P0]` **Onboarding v2 (Phase 1 D3 + Phase 4 D5) already builds the specific-goal mechanics**: the Play is one sentence, specific, self-chosen, with a window. Nothing in this section changes it; this section explains why it works and forbids upgrades: no milestone map, no 30-day challenge calendar, no pre-filled progress.
- `[P0]` **Real temporal landmarks only** (D2 copy timing, Card C): Monday and month-start lines are permitted ("New week, same fire — what's the play?"); fabricated landmarks ("Day 1 of your journey!") are banned. The fire's rolling 7-day window is the only reset BU has, and it is not experienced as a reset because it never displays as zero.
- `[P3]` **Gorgeous's 30-day gate is the one place a countdown could live — and it stays display-less** (D4/D6): Phase 3 D9 already banned countdown rings and "habit complete"; this phase extends the ban to "N days to Gorgeous" in any copy. The gate is *recognized*, never *chased*: at 30 verified days the state simply becomes Gorgeous, the way a threshold is crossed, not a prize claimed.
---

### §2.6 · Gamification Verdicts — Rewards, Badges, Leaderboards

**Mechanism.** Gamification applies game-design elements (points, badges, leaderboards, avatars, narratives) to non-game contexts. The two best-known empirical assessments bracket the honest conclusion:

- **Hamari, Koivisto & Sarsa (2014, HICSS, 10.1109/HICSS.2014.377)**, reviewing the peer-reviewed empirical literature: "gamification provides positive effects, however, the effects are greatly dependent on the context in which the gamification is being implemented, as well as on the users using it" `[T1-venue review; study count debt-listed]`. Positive-on-average, context-fragile, implementation-sensitive — and the field's effect sizes are rarely large.
- **Sailer, Hense, Mayr & Mandl (2017, *Computers in Human Behavior* 69:371–380)**, an online experiment (N = 1,382 `[N from the paper record; per-cell statistics debt-listed]`) assigning specific game elements: **badges and leaderboards increased competence need satisfaction**; avatars, meaningful stories, and teammates increased social relatedness; effects on autonomy were absent or negative. The pattern matches self-determination theory's prediction exactly: these elements feed *competence and relatedness* — the feedback and belonging channels — and do nothing for (and can crowd out) *autonomy*.

**The crowding-out record.** Deci, Koestner & Ryan (1999, *Psychological Bulletin* 125(6):627–668), the meta-analysis BU already leans on (Phase 1 §2.4): tangible, expected rewards significantly undermine free-choice intrinsic motivation — the strongest undermining occurs for tangible rewards contingent on task engagement or completion, and for performance-contingent rewards that become surveillance `[REPLICATED; T2]`. Translated to app mechanics: badges are tangible expected rewards; leaderboards convert competence into surveillance; both risk pricing the activity in tokens. The risk is not hypothetical — it is the standard critique the gamification field levels at itself, and the reason Hamari's "context-dependent" caveat matters: elements work where they inform competence and fail where they become the point.

**The BU translation.** The fire is already a competence-feedback channel: it is informational (state = evidence-graded verdict), immediate (RPE-scaled brightness at the confirming act, Phase 3 D1), and non-transferable (no tokens, no shop, no economy). That is the gamification BU keeps. What it refuses — points, badges, leaderboards, tier shops — are the mechanics that would convert the fire's honest signal into a token economy with an undermining half-life.

**What this means for BU.**

- `[all]` **Permanent verdict: no points, no badges, no leaderboards, no unlock tiers** (D6 audit row; extends Phase 3 D1's "no badge economy"). The fire's brightness is the only reward channel, and it prices evidence, not engagement.
- `[P1]` **Competence feedback stays informational**: state changes announce *what happened* ("third tending this week — the fire is holding"), never *what you earned*. Earned-thing language ("you unlocked") is banned copy (D4).
- `[P3+]` **If Forge ever speaks patterns, they speak as evidence** ("Mornings have been your strong window — 6 of 9 tendings"), which is competence information, not social comparison. Percentiles, peer comparisons, and "users like you" are banned permanently.

---

### §2.7 · FBM and Tiny Habits — Adopt the Anchor, Discard the Physics

**Mechanism.** Fogg's Behavior Model (Fogg 2009, *Proc. Persuasive '09*; DOI 10.1145/1541948.1541949 `[DOI debt-listed]`) states B = MAP: a behavior occurs when **Motivation, Ability, and a Trigger** converge at the same moment. Its practical taxonomy of triggers is the useful part: a **spark** (motivation-boosting trigger for low-motivation/high-ability moments), a **facilitator** (an ability-simplifying trigger for high-motivation/low-ability moments), and a **signal** (a mere prompt when both are present). Fogg's later practitioner system (Fogg 2021, *Tiny Habits*; T3) operationalizes anchor design: attach the new behavior to an existing routine anchor, make it tiny, celebrate immediately.

**The honest status.** FBM is a *framework*, not a tested model: there is no psychometric validation of B=MAP as a predictive model, its terms are not independently operationalized, and its evidentiary base is practitioner case work plus the trigger taxonomy's face validity `[T3-class; not load-bearing]`. What survives scrutiny is the design vocabulary — especially the facilitator concept, which is what BU's Play already is (motivation is assumed — the user sealed the window; ability is what BU simplifies: one pre-chosen action, one tap to start).

**What this means for BU.**

- `[P0]` **Anchor/prompt design adopts the vocabulary, never the brand** (D2): the Evening Close notification is facilitator-class ("one line for the fire" — the ability is one sentence); the relight line is spark-class but sized to a *reminder of evidence*, not a motivation speech (Phase 3 D10's semantics: omission = information + smallest step). Tiny-habits celebration is already covered and bounded by Phase 3 D1's RPE rules — acknowledgment, not confetti.
- `[all]` **B=MAP is never cited to the user and never drives claims.** It is internal design vocabulary (T3), the way Phase 2 treats Fogg-adjacent practitioner material: useful heuristics, no evidentiary weight.

---

### §2.8 · Bundling, Social Commitment, and the Announcement Trap

**Temptation bundling.** Milkman, Minson & Volpp (2014, *Management Science* 60(2):283–299): a field experiment bundling instantly-gratifying audiobooks ("want" experiences) with gym attendance ("should" behavior). Full-binding treatment (gym-only iPod access) produced **51% more gym visits than control** initially; intermediate condition (encouragement to restrict) **29% more**; **treatment effects declined over time** (notably after Thanksgiving); and **61% of participants opted to pay** for the gym-only restriction afterward — revealed demand for the commitment device `[T1-venue RCT; verified from the paper's abstract and PMC record]`. Two facts matter for BU: the effect is real but decays (it rents engagement, like every novelty lever), and the binding itself is the product — users paid for restriction, not for audio. BU's architecture already supports the honest version at the user level: the Play grammar's `at [place/tool]` slot (Phase 1 D1) lets a user write "read while the coffee brews" — a self-built bundle, no app mechanic required. An in-app bundling feature (locking a chosen reward app behind plays) would be rung-3-class restriction applied outside seals — a different animal with a reactance history (Phase 4 Card A).

**Social commitment.** The evidence for *other people* in behavior apps: social-support BCTs (3.1) are real techniques, and commitment devices to another person exist in the economics literature (Phase 1's Giné, Karlan & Zinman deposit contracts; stickK-class). But the announcement literature adds a warning BU cannot ignore: Gollwitzer et al. (2009, *Psychological Science* 20(5):612–618, "When intentions go public"): announcing identity-relevant goal intentions *reduced* subsequent effort — the announcement delivers premature social reality, and the identity credit is spent before the work `[T1-venue; page-level numbers debt-listed]`. For a privacy-first, single-user product whose whole architecture is "the app watches, not the world," social features would trade the product's soul for an engagement channel the announcement literature says can backfire.

**What this means for BU.**

- `[P0]` **Bundling lives in the Play grammar, not in the app** (D5): the builder who wants to pair a study play with a coffee ritual writes it into the play sentence. The app never gates a reward app behind plays.
- `[P4]` **Social commitment: abstain** (D5): no sharing, no partners, no group streaks through P4. If ever revisited, the design floor is: manual, opt-in, one named human, no feed, no counts — and the Gollwitzer finding on the card so the next decision starts honest.
- `[P1+]` **The Life Log is BU's real social feature** — a record for the future self. That is the audience v6 names ("see your life back, years later"), and it needs no other humans to work.

---

### §2.9 · Ethics — Dark Patterns, Autonomy, and the Goes-Dark Verdict

**The dark-pattern baseline.** The HCI literature's operational definition: interfaces that trick or pressure users into choices against their own interests. Mathur et al. (2019, *ACM CSCW*) found dark patterns *pervasive* at scale (11K shopping sites; most sites and most patterns) `[T1-venue; page-level check debt-listed]`; Gray et al. (2018, *ACM CHI*) trace how they arise from normal design practice `[T1-venue; page-level check debt-listed]`. The pattern families relevant to BU's genre: **artificial scarcity and urgency** (timers, "ends tonight"), **confirmshaming** (guilt-coded opt-outs), **forced continuity**, **roach motels** (easy in, hard out), **variable-reward loops on random schedules** (slot-machine class), and **social-pressure mechanics** (comparison, FOMO). SDT-based design ethics (Peters, Calvo & Ryan 2018, *Frontiers in Psychology* 9:797) gives the constructive test: design is autonomy-supportive when it provides rationales, offers choice, acknowledges perspective, and avoids controlling language and pressure `[T1-venue]`. BU has been accumulating its version of this test since Phase 1 (D7 ban-list, D17 consent frames); this phase extends it (D7).

**The goes-dark verdict.** v6's mechanic: "When the fire is dead, BU goes dark and silent. The app withholds its own value when you neglect it… BU goes dark. You want it back? Tend it." Audited against the pattern families:

- **Is it artificial scarcity?** No — the scarcity is real and self-generated: the Forge's value genuinely depends on tended evidence; a dead fire has nothing honest to compute. No timer, no manufactured deadline.
- **Is it punishment or shame?** The risk is real; the defense is copy discipline (already built across Phases 1–4): no "you failed" (Phase 1 D5), no fire-death theatrics (Phase 1 D7), omission = information + smallest relight step (Phase 3 D10), the OUT state is quiet, not accusatory.
- **Does it trap?** The return path is one tap plus one play (v6 §3: "tap the fire, do the play, repeat for 3 days" per the relight rule). No re-onboarding, no interstitials, no guilt gate. Exit (Lite, or uninstall) is never friction-added — roach-motel ban.
- **Does it withhold what the user produced?** This is the one real danger, and it is the amendment (D7): **the journal, the Life Log, the embers, and every export are always accessible at every fire state.** What dims is the *coaching layer* — computed plays, patterns, predictions, insights — because those are BU's own services, and their withdrawal is the honest semantic of "the app's value depends on your tending." The user's own record is never collateral.
- **Is the user's consent to this architecture real?** Yes by construction: Lite is the permanent tier where the fire is honest about being cosmetic, and the "Light the fire" moment is separately consented (v6 §2; Phase 1 D17). The goes-dark contract is opt-in and disclosed before it ever runs.

With the amendment, goes-dark is not a dark pattern: it is a **self-installed commitment device** (Phase 1 D12's category — the user sealed the window; here the user lit the fire), with a return path cheaper than the abandonment it counters. It also passes the SDT test in one specific way no retention mechanic does: it *respects the user's no*. BU waits (Card B).

**What this means for BU.**

- `[P0]` **The amendment is build-cheap**: journal and data access are unconditional from P0 (they already are, structurally — the blank page is the app's floor). D7 locks it as invariant and seeds the Evidence Charter entry for Phase 7: *"BU never withholds the user's own record, and never adds cost to return."*
- `[P0]` **The dark-pattern ban list extends the accumulated lists** (D7): artificial urgency timers, confirmshaming, "we miss you" pushes, fake landmarks, milestone countdowns, comparison bait, identity threats, and slot-machine reward schedules on random timing — banned by name, with the RPE brightness carve-out stated precisely (rewards are fixed to the confirming act, never to random schedules: informational, Phase 3 D1).
- `[all]` **The audit's meta-rule**: any future mechanic must name its BCT (D6's vocabulary), its evidence (flags), and its pattern-family clearance (this section). Three signatures or it doesn't ship.
---

## 3 · Design Decisions

**Decision table.** Evidence = the section above that carries the citation chain. Confidence per the program's three-level vocabulary. Tag = earliest build phase that can act.

| # | Decision (compact) | Evidence | Confidence | Tag |
|---|---|---|---|---|
| D1 | **Ladder-as-JITAI decision table v1** (below): every rung and scheduled touch specified as decision point × tailoring variables × decision rule × intervention option × proximal outcome × copy class; receptivity constraints formal; thresholds remain set-and-tested | §2.2 (Nahum-Shani 2018 verbatim components); Phase 4 D1/D2/D6; Phase 3 D8/D10 | High for structure; Low for threshold priors (declared) | P2 (P0/P1 rows land earlier per table) |
| D2 | **Notification & Evening Close copy guide (Lite)**: two classes only, boundary delivery, fire-once; facilitator-class Evening Close, spark-class relight line, no urgency; real temporal landmarks only; FBM vocabulary internal-only | §2.1 (evening peak); §2.2 (receptivity); §2.7 (FBM status); §2.5 (fresh starts); Phase 4 D3; Phase 1 D2/D7 | High | P0 |
| D3 | **Engagement-decay countermeasure plan**: ritual-not-retention design locked; day 3/14/30 danger zones mapped to what BU does and never does; survival metrics (close-completion, relight conversion, days-between-opens) instrumented at P1; zero re-engagement machinery ever | §2.1 (Eysenbach; Baumel 4.0%/3.3%; Meyerowitz-Katz 43%); v6 "BU waits" | High for diagnosis; Medium for the ritual's specific efficacy (N=1 testable) | P0 (stance + no-features), P1 (metrics) |
| D4 | **Streak/fire framing rules**: identity-statement grammar kept under evidence-grade truth rule; numeric streaks, freeze tokens, countdown rings, milestone-bribery, earned/unlocked copy, percentiles, "don't break the chain", loss-urgency — all banned; loss aversion structural only | §2.4 (TK 1992 λ≈2.25; Bryan 2011 PNAS `[flagged]`; Dai 2018); Phase 3 D1/D9; Phase 1 D7/D18 | High for bans; Medium for identity-line effect (single flagged study) | P3 (Forge speech) / P0 seed (manual-fire copy) |
| D5 | **Temptation bundling & social commitment: abstain as features.** Bundling lives in the Play grammar's `at [place/tool]` slot; no reward-gating mechanic ever; no social features through P4 (announcement risk + privacy-first architecture); revisit conditions stated | §2.8 (Milkman 2014 51/29/61; Gollwitzer 2009); Phase 1 D1; Phase 4 Card A | Medium | P0 (grammar) / P4 (final verdicts) |
| D6 | **BCT audit of BU v6** (table below): v6 feature ↔ BCT code(s) ↔ phase ↔ verdict; gaps flagged per phase; permanent abstentions recorded (points/badges/leaderboards/comparison); every future mechanic must name its BCT + evidence + pattern-family clearance | §2.3 (Michie 2011; 2013); §2.6 (Hamari; Sailer; DKR bridged) | Medium (audit), High (abstentions) | all |
| D7 | **Goes-dark ethics amendment + dark-pattern ban-list extension**: coaching layer dims; user's own record (journal/Life Log/embers/exports) never withheld; return = one tap + one play; ban-list names urgency timers, confirmshaming, win-back pushes, fake landmarks, slot-machine schedules (RPE carve-out stated) | §2.9 (Mathur; Gray; Peters/Calvo/Ryan); Phase 1 D7/D17; Phase 3 D9/D10 | High | P0 |

**D1 — Ladder-as-JITAI decision table v1 (the phase's centerpiece).** Components per Nahum-Shani et al. (2018): the ladder's six JITAI parts are — *decision points*: (a) scheduled boundaries (seal start; Evening Close at user-set time), (b) event triggers (drift-app foreground during a seal; omission), (c) state transitions (OUT reached); *tailoring variables*: app class (Phase 4 D1 matrix), accumulated drift minutes, sustained-open, nudge-used, dismissals-in-row, hard-focus-declared, stress-window flag, fire state, tending history, declared tool list; *intervention options*: silence, status, soft nudge, friction overlay, block, graceful close, relight line, floor-sized play, educational confirm chip; *decision rules*: below; *proximal outcomes*: drift→return conversion, session completion, close completion, next-day relight; *distal outcomes*: tendings/day and the 7-day fire-state trajectory (Phase 7 scoring; drift is never a direct verdict — Phase 4 D8).

| Decision point | Tailoring variables | Decision rule | Option (copy class) | Proximal outcome | Evidence | Tag |
|---|---|---|---|---|---|---|
| Seal start | declared window; stress-window | seal silently; no banner, no gamified start | silence + P1 status line | friction-free start | §2.2 receptivity; Phase 1 D12 | P0 (manual) / P2 (seal) |
| Evening Close boundary | fire state; today's play outcome; optional sleep line (P0: manual) | fire once at user-set time; never repeat same night; never mid-seal (queue) | close notification + question set (Phase 1 D2) | close completion | Harkin d≈.40; §2.1 evening peak; Phase 4 D3 | P0 |
| Play reminder (user-created) | play sentence; window | fire at window start; zero by default; once | "Play: {sentence}. {window}." | play initiation | §2.2; Phase 4 D3 | P0 |
| Drift-app foreground during seal | app class; minutes; sustained_open; nudge_used; dismissals_in_row; hard_focus; stress-window | <60 s → touch (log only) · **R1** iff (≥20 min ∨ sustained ≥2 min) ∧ ¬nudge_used ∧ ¬stress-window · **R2** iff ≥30 min (per drift-app open; 30-min cool-down after 3 dismissals) · **R3** active from seal iff hard_focus (block whitelist; one-tap logged override) · 60 min normal-seal → graceful close offer · never-counts never trigger | R1 soft nudge, dismissible · R2 one-sec-class overlay with explicit dismiss · R3 block ("You sealed this window. End the seal?") · close offer with one retrieval line | drift→return; session completion | Phase 4 D1/D2 (one sec 36/37/57; Brehm); Stothart; §2.2 | P2 (P1 coarse form) |
| Stress window declared | declared flag | ladder capped at R1; floor-sized plays; feedback muted | supportive-quiet copy only | protect habit line under load | Phase 3 D8 | P0 seed / P2 |
| Omission (day closed without a close) | mirror delta; tending history | no re-ping, ever; next surface offers the one-line relight play in the same screen | relight line (spark-class, evidence-not-shame) | next-day relight | Phase 3 D10; §2.4 (loss used structurally) | P0 |
| OUT state reached | tending history; days since last tending | app enters OUT semantics (quiet); relight path = one tap + one play, 3-day rule; **journal and data fully accessible** (D7); no win-back push | one static line ("The fire is low. One play relights it.") | relight within 7 days | v6 §3; Phase 1 D5; §2.1 (attrition reality) | P0 (manual fire) / P1 (computed) |
| Real temporal landmark (Mon, month start, Jan 1, declared life event) | calendar; user-declared events | permitted as copy timing inside Evening Close/reminders; never fabricated milestones, never resets | optional fresh-start line ("New week, same fire — what's the play?") | initiation boost, honest | §2.5 (Dai 2014); Card C | P0 |
| Educational exception | user-declared tool list; user confirm chip | only user-declared tools exempt; title-only auto-classification banned; unlabeled educational-looking apps count as drift; one-tap retro-tag | confirm chip ("Looks like you're studying. Continue without intervention?") | correct tool-use, no false punishments | Phase 4 D7 | P3 |

- **Honesty notes, carried forward:** the 20/30/60 + grace + cool-down numbers remain Phase 4 D2's declared set-and-tested priors (no literature pins them); the JITAI formalization adds structure and auditability, not evidentiary support for the numbers. The ladder's evaluation standard is MRT-class; BU's honest N=1 analogue (within-user A/B of rung exposure) lives in Phase 6's playbook.
- **Copy classes reference the accumulated ban-lists** (Phases 1 D7, 3 D9, 4 D6, this phase D4/D7). The v6 §7 card copy ("YouTube for 18 min. The fire is dimming.") is **retired**: drift lines report app-foreign minutes, never fire pricing (Phase 4 D6) — logged as a v6 amendment below.

**D2 — Notification & Evening Close copy guide (Lite, P0).** Scope: exactly the two P0 notification classes (Phase 4 D3) plus every string those surfaces render.

- **Evening Close (single, user-set time, default 21:00):** title "Evening Close"; body "One line for the fire before the day ends." Fires once; a missed close is Phase 3 D10's domain (next surface dims, relight line offered), never a re-ping.
- **User-created play reminder (zero default):** "Play: {sentence}. {window}." Fires at window start, once, user-editable.
- **Relight line (rendered inside surfaces, not pushed):** "The fire is low. One play relights it." — evidence, not urgency; no fire-death copy ("dying/dead/last chance" banned).
- **Real landmarks only:** Monday/month-start/New Year/declared life events may time a line; no "Day 1!" fabrications, no journey-milestone counters (Card C; D4).
- **FBM adoption verdict (internal):** Evening Close = *facilitator*-class (motivation assumed at boundary; ability reduced to one sentence); relight = *spark*-class but evidence-shaped; P1 persistent status = *signal*-class (present, silent). B=MAP is design vocabulary only — never cited in-app, never drives claims (§2.7's status).
- **Banned in all notification copy:** streak/chain language, countdowns, "we miss you", comparison, milestone-bribery, urgency timers, fire-death theatrics, "you failed"-class shame (Phase 1 D5), "your streak expires" — the full accumulated list (Phases 1 D7, 3 D9, 4 D6) applies.

**D3 — Engagement-decay countermeasure plan (P0 stance; P1 metrics).** The literature says the failure mode is abandonment (§2.1: 3.3% median 30-day retention; 43% pooled dropout), and that the toolkit most apps use against it (push ramping, streaks, badges) rents engagement it cannot keep. BU's plan is deliberately structural:

| Danger zone | What the literature shows | What BU does | What BU never does | Metric (P1) |
|---|---|---|---|---|
| Day 3 (novelty cliff) | usage curves already collapsing (Eysenbach's program; Baumel's panel) | nothing artificial — the ritual is the design; low novelty by construction | celebrate opens, add tips, ramp pushes | close-completion rate |
| Day 14 ("why am I doing this") | dropout tied to burden/relevance, not missing rewards (Meyerowitz-Katz synthesis) | the fire + journal answer the question with evidence (identity, §2.4); WOOP obstacle+plan re-offered (Phase 1 D3) | add a motivational quote system, milestones, badges | tendings/day trend |
| Day 30 (efficacy window) | most apps are empty rooms by now (3.3% median retention) | the honest state is shown; Gorgeous gate exists only with proof layers (P1+) and is recognized, never chased | fake fresh starts, reward unlocks, streak-society tiers | relight conversion after OUT (7-day) |
| Any day (relapse) | lapse is the norm, relapse prevention is learned (Marlatt & Gordon, bridged Phase 1) | relight ritual: one tap + one play, 3-day rule; shame banned | win-back campaigns, "we miss you", freeze tokens | days-between-opens distribution |

- **"BU waits" is the strategy**, not the absence of one: BU competes on ritual, identity, and honesty, and accepts higher churn among non-builders as a design cost the v6 doc already owns ("Most users who install BU and don't opt in will leave — and that is the point."). The builder's own P0 runs are the first survival dataset (OQ2).

**D4 — Streak/fire framing rules (P3 Forge speech; P0 seed for manual-fire copy).**

- **Kept, with rules:** the identity-statement grammar — "You've shown up 4 of last 7 days. That's who you are now." and "This is becoming you." — provided (a) every frequency claim is computed from evidence-graded tendings (Phase 6 grades; P0's manual fire carries its MANUAL tag honestly), (b) the phrasing states evidence about identity, never a count under threat, (c) expected outcomes get acknowledgment, not celebration (Phase 3 D1).
- **Banned permanently:** numeric streak counters; streak freezes/rescue tokens; countdown rings and "N days to Gorgeous"; milestone-bribery ("only 2 days to unlock"); earned/unlocked language; percentiles and "users like you"; "don't break the chain"; loss-urgency ("your fire dies tonight"); streak-society-class status clubs.
- **Principle:** loss aversion is honored *structurally* — the fire visibly dims, and that is the whole loss display. No copy amplifies the loss rhetorically. The evidence base for identity framing is single-flagged (Bryan 2011, `[T1; replication status open]`), so the grammar is a design commitment, not a settled-science claim — and the ban-list does not depend on it.

**D5 — Temptation bundling and social commitment (feature verdicts).**

- **Bundling: abstain as a mechanic.** The user-level bundle rides the Play grammar (`at [place/tool]`): "Read while the coffee brews. 20 min." The app never gates a reward behind plays — reward-gating would extend rung-3 restriction outside seals and import the reactance history of Phase 4 Card A. If the builder wants the Milkman effect, it is an N=1 experiment (OQ3), not a feature.
- **Social commitment: abstain through P4.** Rationale stack: the announcement literature (Gollwitzer 2009) shows identity-goal announcements can *reduce* effort; the product's architecture is single-user, on-device, privacy-first; social features would add a feed the v6 doc's soul refuses. Revisit conditions (if ever): manual, opt-in, one named human, no feed, no counts — and the Gollwitzer finding stays on the card.

**D6 — BCT audit of BU v6.** Codes per BCTTv1 (Michie et al. 2013) `[codes cited from the standard taxonomy; page-level re-verification debt-listed]`.

| v6 feature | BCT code(s) | Phase | Verdict |
|---|---|---|---|
| Onboarding 3+1 questions (goal, obstacle+plan, chronotype, sleep anchor) | 1.1, 1.2 (WOOP), 1.4 | P0 | keep |
| One-sentence Play (user-written; Forge-computed later) | 1.4 action planning | P0 / P1 | keep |
| Manual fire, 5 states | 2.2 feedback on behaviour; 2.3 self-monitoring | P0 | keep; no 10.x ever |
| Evening Close question set | 2.3 (+ monitoring meta) | P0 | keep |
| Blank-page write journal | deliberately un-instrumented (no BCT) | P0 | keep; never gamify |
| Relight ritual (3-day) | 8.3-adjacent + 1.2 | P0 | keep |
| Watcher / Life Log auto-capture | 2.1 monitoring by others w/o feedback | P1 | keep (Phase 6 grades govern) |
| Drift events | 2.3 self-monitoring | P1/P2 | keep as OBSERVED evidence; no punishment (14.x abstained) |
| Ladder rungs | 7.1 prompts/cues; 12.1/12.3 antecedent control | P2 | keep with guardrails (Phase 4 D2) |
| Hard Focus whitelist | 12.1, 12.3 (+ BCW restriction, self-installed) | P2 | keep (Phase 4 D4) |
| Educational exception | 2.2 accuracy (data hygiene) | P3 | keep as constrained (Phase 4 D7) |
| Forge patterns/predictions | 1.2 support; 2.2 | P3 | keep under Phase 6 grammar |
| Identity streak line | 13.4, 13.5 identity | P1+ | keep under D4 rules |
| Gorgeous 30-day verified gate | 1.5 review behavior goal; 13.5 | P1+ | keep; display-less (D4) |
| Points/badges/leaderboards | 10.3/10.4; 6.2 | — | **abstain, permanent** |
| Social commitment features | 3.1 | — | **abstain through P4** (D5) |
| Win-back pushes / urgency timers | 7.1-abuse | — | **abstain, permanent** (D7) |

**Gaps flagged:** 15.3 (focus on past success) is unused — Sunday Review could carry one evidence-graded "what worked" line at P1+; 4.1 (instruction) is covered inside the study-play card (Phase 2 D1); 9.2 (pros/cons) is partially covered by WOOP. Everything else absent is absent on purpose, and the audit table is where the absences are defended.

**D7 — Goes-dark ethics amendment + dark-pattern ban-list extension (P0).**

- **The invariant (Evidence Charter candidate for Phase 7):** *"BU never withholds the user's own record, and never adds cost to return."* Operationally: journal, Life Log, embers, and all exports are accessible at every fire state, including OUT; the relight path is one tap plus one play; no re-onboarding, no guilt gate, no uninstall friction ever added.
- **What dims is the coaching layer** — computed plays, patterns, predictions, insights — because its honest value genuinely depends on tended evidence. This is the amendment to v6's "withholds its own value": value *BU computes* is withheld; value *the user produced* never is.
- **Dark-pattern ban-list extension** (extends Phases 1 D7, 3 D9, 4 D6): artificial urgency timers; confirmshaming; win-back/"we miss you" pushes; fake landmarks and fabricated "Day 1" resets; milestone countdowns; comparison bait; identity threats; variable-reward schedules on random timing (the RPE brightness carve-out: reward signal is fixed to the *confirming act*, ≤2 s, informational — Phase 3 D1 — never a random schedule, never a slot machine); forced continuity (moot — no accounts, no subscriptions); roach motels (exit/Lite is always one clean path).
- **The defense, in one paragraph for the record:** goes-dark is a self-installed commitment device (the user lit the fire, knowingly, in a separately consented moment — Phase 1 D17), its scarcity is real rather than manufactured, its return path is cheaper than the abandonment it counters, and its severity is bounded by copy discipline that four phases of evidence have already built. Under the SDT test (Peters, Calvo & Ryan 2018) it is autonomy-supportive in the rarest way: it accepts the user's no and stays waitable. What it may never become — and D7 exists to prevent — is a guilt engine: no shame copy, no hostage data, no theatrical death.

---

## 4 · Contradiction Cards

**Card A — Engagement engineering vs. honesty.** *Claim A:* game mechanics retain users — the vendor record (Duolingo streak/freeze data, T4) and Hamari et al. 2014's "positive effects" both point that way. *Claim B:* the same mechanics are the token economy the undermining meta-analysis warns against (DKR 1999), and the retention they buy decays on the same attrition curve as everything else (Baumel; Meyerowitz-Katz). *What is actually disputed:* whether gamified engagement is a bridge to durable behavior or a rental that expires — the field has no clean long-horizon causal answer in self-directed populations. *Working resolution:* BU keeps the evidence-favored channels (monitoring, identity, informational feedback) and abstains from tokens permanently (D4/D6); higher churn is accepted and measured (D3) rather than rented back. *Flip condition:* peer-reviewed evidence that badge/streak mechanics sustain behavior ≥90 days beyond novel-visibility in self-directed (non-mandated) settings without intrinsic-motivation cost → reopen D6's abstention rows.

**Card B — Goes-dark vs. attrition.** *Claim A:* the mechanic is BU's soul — the discipline pressure "no ring or streak can match" (v6 §1). *Claim B:* abandonment is the category's killer (§2.1), and darkening the app stacks on top of natural nonusage attrition exactly when the user is least attached. *What is actually disputed:* whether goes-dark increases relight probability or total loss — no literature tests the mechanic; it is a design bet. *Working resolution:* the D7 amendment bounds the downside (user's record never hostage; return = one tap + one play), Lite remains the honest exit, and P1 instruments the bet (relight-within-7-days after OUT is a first-class metric, D3). *Flip condition:* relight conversion <10% across the builder's own OUT episodes → soften OUT's visuals and silence (never add notifications), and re-examine whether OUT should render as "dim" rather than "dark".

**Card C — Fresh-start engineering vs. authenticity.** *Claim A:* temporal landmarks reliably boost initiations (Dai 2014) — free energy for onboarding and re-engagement moments. *Claim B:* manufactured landmarks are manipulation, and Dai 2018 shows engineered resets help strugglers while erasing high performers' momentum — the Blazing user is the one BU would hurt. *What is actually disputed:* whether the fresh-start effect survives being *industrialized* (fake milestones, forced resets) or depends on being real. *Working resolution:* real landmarks only (D2); the fire's 7-day rolling window is the only reset BU has, and it never displays as zero (Phase 7 scoring over tendings); no fabricated "Day 1" ever (D4/D7). *Flip condition:* OQ5 — if landmark clustering is absent in the builder's own P1 data, the Monday lines are retired as copy clutter.

---

## 5 · Open Questions & the N=1 Queue

- **OQ1 · Relight survival.** Does the 3-day relight ritual actually get completed after real gaps? *N=1:* log every OUT episode from P0 onward; record relight completion and time-to-relight. This is Card B's primary datum. `[P0-runnable manually]`
- **OQ2 · The builder's own day-3/14/30.** Count closes and tendings per day from install; do the literature's danger zones appear in a single motivated builder? Expectation: muted (the builder is the atypical user), but the record calibrates D3's assumptions. `[P0]`
- **OQ3 · Bundling self-trial.** Pair the evening study play with a fixed coffee ritual for two weeks (ABAB). Metrics: play initiation within window; completion. Milkman's effect at N=1, honestly interpreted. `[P0-runnable manually]`
- **OQ4 · Does the identity line land?** From P1, when the Forge first speaks: one weekly self-report line on whether "4 of last 7" framing feels like evidence or like surveillance. Qualitative, but it gates the grammar's expansion. `[P1]`
- **OQ5 · Landmark clustering.** Do tendings cluster after Mondays/month starts in P1 data? Decides whether D2's landmark copy lines stay, move, or retire (Card C's flip condition). `[P1]`

---

## 6 · Sources

**T1/T2 (primary evidence).**
- Nahum-Shani, I., Smith, S. N., Spring, B. J., Collins, L. M., Witkiewitz, K., Tewari, A., & Murphy, S. A. (2018). Just-in-Time Adaptive Interventions (JITAIs) in Mobile Health: Key Components and Design Principles for Ongoing Health Behavior Support. *Annals of Behavioral Medicine* 52(6), 446–462. doi:10.1007/s12160-016-9830-8 (PMC5364076) — *full text opened; component definitions verbatim.*
- Eysenbach, G. (2005). The Law of Attrition. *JMIR* 7(1), e11. doi:10.2196/jmir.7.1.e11 — *full text opened.*
- Baumel, A., Muench, F., Edan, S., & Kane, J. M. (2019). Objective User Engagement With Mental Health Apps: Systematic Search and Panel-Based Usage Analysis. *JMIR* 21(9), e14567. doi:10.2196/14567 (PMC6785720) — *abstract + PMC record opened.*
- Meyerowitz-Katz, G., Ravi, S., Arnolda, L., Feng, X., Maberly, G., & Astell-Burt, T. (2020). Rates of Attrition and Dropout in App-Based Interventions for Chronic Disease. *JMIR* 22(9), e20283. doi:10.2196/20283 (PMC7556375) — *abstract + PMC record opened.*
- Linardon, J., Cuijpers, P., Carlbring, P., Messer, M., & Fuller-Tyszkiewicz, M. (2019). The efficacy of app-supported smartphone interventions for mental health problems: a meta-analysis of randomized controlled trials. *World Psychiatry* 18(3), 325–336. doi:10.1002/wps.20673 — *PubMed record opened (66 RCTs; g = 0.28/0.30).*
- Milkman, K. L., Minson, J. A., & Volpp, K. G. M. (2014). Holding The Hunger Games Hostage at the Gym: An Evaluation of Temptation Bundling. *Management Science* 60(2), 283–299. doi:10.1287/mnsc.2013.1784 (PMC4381662) — *abstract + PMC record opened (51%/29%; 61% pay).*
- Dai, H., Milkman, K. L., & Riis, J. (2014). The Fresh Start Effect: Temporal Landmarks Motivate Aspirational Behavior. *Management Science* 60(10), 2563–2582. doi:10.1287/mnsc.2014.1901 — *citation record verified; magnitudes debt-listed.*
- Dai, H. (2018). A Double-Edged Sword: How and Why Resetting Performance Metrics Affects Motivation and Performance. *OBHDP* 148, 12–29 — *citation verified at abstract-class level; page-level debt-listed.*
- Bryan, C. J., Walton, G. M., Rogers, T., & Dweck, C. S. (2011). Motivating voter turnout by invoking the self. *PNAS* 108(31), 12653–12656. doi:10.1073/pnas.1103343108 — *record opened; journal corrected from the plan's working note (not Psych Science).*
- Tversky, A., & Kahneman, D. (1992). Advances in prospect theory: Cumulative representation of uncertainty. *Journal of Risk and Uncertainty* 5(4), 297–323. doi:10.1007/BF00122574 — *record verified (λ ≈ 2.25 canonical estimate).*
- Michie, S., van Stralen, M. M., & West, R. (2011). The behaviour change wheel: A new method for characterising and designing behaviour change interventions. *Implementation Science* 6, 42. doi:10.1186/1748-5908-6-42 — *record verified.*
- Michie, S., et al. (2013). The behavior change technique taxonomy (v1) of 93 hierarchically clustered techniques. *Annals of Behavioral Medicine* 45(1), 125–135 (PMID 23512568) — *existence and 93/16 structure verified; codes cited from the standard taxonomy, page-level debt-listed.*
- Hamari, J., Koivisto, J., & Sarsa, H. (2014). Does Gamification Work? — A Literature Review of Empirical Studies on Gamification. *HICSS 2014*, 3025–3034. doi:10.1109/HICSS.2014.377 — *abstract opened (context-dependence verdict verbatim).*
- Sailer, M., Hense, J. U., Mayr, S. K., & Mandl, H. (2017). How gamification motivates: An experimental study of the effects of specific game design elements on psychological need satisfaction. *Computers in Human Behavior* 69, 371–380. doi:10.1016/j.chb.2016.12.033 — *record verified; N and per-cell stats debt-listed.*
- Deci, E. L., Koestner, R., & Ryan, R. M. (1999). A meta-analytic review of experiments examining the effects of extrinsic rewards on intrinsic motivation. *Psychological Bulletin* 125(6), 627–668 — *record verified; bridged from Phase 1's opened copy.*
- Locke, E. A., & Latham, G. P. (2002). Building a practically useful theory of goal setting and task motivation: A 35-year odyssey. *American Psychologist* 57(9), 705–717 — *record verified; per-meta effect sizes debt-listed.*
- Kivetz, R., Urminsky, O., & Zheng, Y. (2006). The Goal-Gradient Hypothesis Resurrected. *Journal of Marketing Research* 43(4), 529–539 — *citation verified; magnitudes debt-listed.*
- Nunes, J. C., & Drèze, X. (2006). The Endowed Progress Effect: How Artificial Advancement Increases Effort. *Journal of Consumer Research* 33(3), 303–311 — *numbers (34% vs 19%) consistent across independent secondary reports; primary page-level debt-listed.*
- Gollwitzer, P. M., Sheeran, P., Michalski, V., & Seifert, A. E. (2009). When intentions go public: Does social reality widen the behavioral gap? *Psychological Science* 20(5), 612–618 — *citation verified; page-level numbers debt-listed.*
- Ordóñez, L. D., Schweitzer, M. E., Galinsky, A. D., & Bazerman, M. H. (2009). Goals Gone Wild: The Systematic Side Effects of Over-Prescribing Goal Setting. *Academy of Management Perspectives* 23(1), 6–16 — *citation verified; page-level debt-listed.*
- Fogg, B. J. (2009). A behavior model for persuasive design. *Proc. Persuasive '09*, Article 40 — *DOI debt-listed; T3-class framework use only.*
- Peters, D., Calvo, R. A., & Ryan, R. M. (2018). Designing for Motivation, Engagement and Wellbeing in Digital Experience. *Frontiers in Psychology* 9, 797. doi:10.3389/fpsyg.2018.00797 — *record verified.*
- Mathur, A., et al. (2019). Dark Patterns at Scale: Findings from a Crawl of 11K Shopping Websites. *ACM CSCW* — and Gray, C. M., et al. (2018). The Dark (Patterns) Side of UX Design. *ACM CHI* — *venues verified as T1-venue; page-level checks debt-listed.*
- Nahum-Shani, I., et al. (2026). Just-in-Time Adaptive Interventions: Where Are We Now? *Annual Review of Psychology* — *existence verified at record level; T2-venue lead for the JITAI evidence-status claim.*
- Bridged from prior phases: Harkin et al. (2016) monitoring meta (Phase 1 §2.7); Giné, Karlan & Zinman (2010) commitment field experiment (Phase 1); Marlatt & Gordon (1985) relapse prevention (Phase 1); Wood, Quinn & Kashy (2002) context-cued behavior (Phase 3).

**T3 (researcher/practitioner books — context, never load-bearing alone).**
- Fogg, B. J. (2021). *Tiny Habits*. — anchor/celebration practitioner system; adopted as vocabulary only (§2.7).
- Mark, G. (2023). *Attention Span*. — cited by Phase 4; not used here.

**T4 (leads only — never evidence).**
- Duolingo engineering blog streak/freeze/leaderboard posts (incl. the widely-cited growth retrospective) — vendor claims about streak retention; page-level fetch debt-listed this pass; used only as the motivation for D4's bans, never as support for any BU claim.

**Research debt (page-level gaps this pass, listed per the program's honesty rule).** BCTTv1 full-text re-verification (cluster/code list); Sailer 2017 exact N and per-cell statistics; Hamari 2014 reviewed-study count; Bryan 2011 effect magnitudes and later replication status; Nunes & Drèze primary page; Kivetz 2006 magnitudes; Gollwitzer 2009 numbers; Ordóñez 2009 page; Fogg 2009 DOI; Dai 2014 per-study magnitudes; Dai 2018 page; Mathur 2019 / Gray 2018 page checks; Duolingo post page. *Process note:* the web-search/fetch budget died mid-phase (15-call cap); the surviving verification path was bulk DOI resolution via the Semantic Scholar and Crossref APIs from the sandbox (verified: BCW, Sailer, Locke & Latham, DKR, TK 1992, Dai, Linardon, Peters/Calvo/Ryan, Sailer) — a workaround future phases should adopt *before* the browser-budget phase, not after it.
