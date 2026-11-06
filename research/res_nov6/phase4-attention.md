# BU Deep Research Program — Phase 4: Attention, Focus & Drift — The Science of the Watcher

**Document** `research/phase4-attention.md` · **Version** 1.0 · **Date** 2026-11-05 · **Prepared for** Dhivakaran · BU (Galaxy A54 build)
**Scope** Master Plan §7 (`RESEARCH-PLAN.md` v1.0): interruption cost, notification and mere-presence effects, media multitasking, flow, reactance vs. friction, chronotype, situation modification.
**Owning build decisions** the Watcher's drift definition (P1 coarse / P2 full), the intervention ladder v1.1, BU's own notification policy, Hard Focus whitelist guidance, chronotype-based play windows.
**Serves** BU v6 "The Fire" and the P0 Spark build. Supersedes the v0 draft's attention section (its sourcing habits end here).
**Template** per Master Plan §2. Confidence vocabulary: **High** (T2 meta or multiple T1 replications) · **Medium** (consistent T1, no meta) · **Low** (single studies / `[EMERGING]`).

---

## 1 · TL;DR — What This Phase Locks

1. **The famous interruption numbers fail their precision audit — and BU stops using them.** "23 minutes to refocus" lives in press interviews (T4) and was revised to ~25 minutes in the researcher's own 2023 book (T3); no primary paper prints either. What the primary record supports: sub-second switch costs that grow with complexity (Monsell 2003; Rubinstein et al. 2001), attention residue from *unfinished* tasks (Leroy 2009), and minutes-scale working-sphere fragmentation in real work (Gonzalez & Mark 2004: ~3 min). BU prices a drift minute as *never zero, never 23 minutes* (D6).
2. **Notifications degrade performance without being touched.** A single unattended call or text during a sustained-attention task measurably increased errors (Stothart, Mitchum & Yehnert 2015, *JEP: HPP*). P0 therefore ships with zero scheduled interruptions except the Evening Close and user-created reminders; BU never pings mid-focus (D3).
3. **"Brain drain" is downgraded to `[CONTESTED]`.** Ward et al. (2017) failed at least two direct replications (Ruiz Pardo & Minda 2022, *Acta Psychologica*), while a 2023 meta-analysis (22 studies, 43 effects) still finds an overall negative effect of smartphone use *and* presence — with heavy heterogeneity. Placement guidance becomes an optional N=1 experiment card, never a claim (D4).
4. **The ladder earns its rungs from friction, not force.** The one sec RCT (Grüning et al. 2023, *PNAS*): 36% of attempts dismissed at the friction screen, 37% fewer attempts overall, 57% fewer openings after six weeks — with the *dismiss option*, not the delay, the strongest feature. Under reactance theory (Brehm 1966), BU's escalation order is silence → one soft nudge → friction overlay → block, and block exists only inside a window the user sealed (D2).
5. **"Before noon" is an assumption, not a finding.** Chronotype lateness peaks around age 20 (Roenneberg et al. 2004), and synchrony effects show inhibitory control tracks circadian peak, not clock virtue (May & Hasher 1998). P0 adds a chronotype question to onboarding and kills the hardcoded morning default (D5).

---

## 2 · Science

### §2.1 · The Anatomy of an Interruption — What a Drift Minute Actually Costs

**Mechanism.** An interruption is not one event but three distinguishable costs, and the literature that survives scrutiny prices them differently:

1. **Switch cost (sub-second to seconds).** Reconfiguring between task sets is an executive process. Monsell (2003, *Trends in Cognitive Sciences* 7(3), 134–140) summarizes the lab standard: switch costs of tens to hundreds of milliseconds, reducible by preparation but never fully abolished — the *residual cost* persists even with warning cues, and grows with task-set strength conflict. Rubinstein, Meyer & Evans (2001, *JEP: HPP* 27(4), 763–797), in four experiments on switching between tasks of varying complexity and familiarity, found the costs are asymmetric and complexity-dependent: rule reactivation takes longer when switching from a familiar to an unfamiliar task than the reverse `[REPLICATED]` (the paper's own framing of time costs is seconds-scale, growing with complexity; the "up to 40% of productive time" press line is an interview extrapolation and is not used here).
2. **Attention residue (the unfinished-task cost).** Leroy (2009, *OBHDP* 109(2), 168–181) coined the term for what carries over when a first task is not finished: across three studies, attention paid to an unfinished task — amplified under time pressure — degrades performance on the task that follows `[T1, per-study Ns debt-listed]`. The mechanism is goal activation: the unfinished task keeps claiming working-memory resources (the Zeigarnik-class effect operationalized for modern work). This matters for BU because a drift event that *starts* something new (opens a feed, begins a video) leaves residue that a drift event that *resolves* something (checks a time, answers a call) does not.
3. **Resumption lag (the real-world cost, in minutes).** Field observation of knowledge workers, not the lab, produces the minutes-scale numbers. Gonzalez & Mark (2004, ACM CHI'04, pp. 113–120) observed information workers and found an average of **about 3 minutes** on a working sphere, and slightly more than 2 minutes on any single electronic tool, before switching (figure as cited in Zeldes et al. 2007, *First Monday*) `[REPLICATED-consistent across this research line]`. Iqbal & Horvitz (2007, ACM CHI'07, 10.1145/1240624.1240730) logged desktop work and showed suspended tasks are typically resumed minutes later, not seconds — resumption in real computing contexts is minutes-scale. Mark, Gudith & Klocke (2008, ACM CHI'08, 10.1145/1357054.1357207) add the twist that interrupted work proceeds *faster* but at the cost of higher stress and effort `[T1-venue]`.

**The precision audit — "23 minutes."** The figure BU's v6 inherited ("~20+ minutes to refocus") does not survive sourcing:

| Claim | Where it actually lives | Grade |
|---|---|---|
| "47 seconds average attention on a screen" | Mark's logging studies 2016–2020 (three studies averaging ~44–50 s → 47 s; median ~40 s; ~75 s by 2012; ~2.5 min in the early-2000s stopwatch era) | T3 book (*Attention Span*, 2023, Hanover Square Press) + T4 interviews; **not a primary paper** |
| "23 minutes 15 seconds to return to a task" | Press interviews (c. 2016–2018); absent from Mark et al. 2005/2008 papers | T4 — **banned as evidence** |
| "~25 minutes to return to an interrupted project (after ~10.5 min on it)" | Mark's own 2023 book, restating her field data | T3 — usable as an order-of-magnitude researcher summary only |
| ~3 min per working sphere; ~2 min per electronic tool | Gonzalez & Mark 2004, CHI'04 (peer-reviewed) | T1-venue — the anchoring real-work number |

**Verdict:** the pop numbers are researcher summaries that drift (47 s vs. 2.5 min across a decade of her own data). What is *stable* across grades: interruptions in real knowledge work arrive every few minutes, resumption takes tens-of-minutes-class time to fully return (book-level summary), and the lab-verified hard costs are seconds-scale with residue from unfinished work. BU therefore prices a drift minute honestly:

> **Drift-cost model (v1).** For a drift event of nominal duration *d* inside a sealed play: effective cost ≈ *d* + switch reconfiguration (seconds-class, complexity-dependent) + residue (*r* > 0 only when the drift *starts* something new — a feed, a video — vs. resolves something) + elevated resumption risk. The Watcher v1 (P1) measures only the binary and the minutes; the model exists so copy never over-claims a single number.
>
> **Worked example (illustrative; bounds stated).** A sealed 40-minute study play; at minute 15 a social app is opened for 8 minutes. Nominal cost: 8 min. Switch reconfiguration: two switches, seconds-class each even with complexity asymmetry — call it <0.5 min combined. Residue: this drift *started* a feed, so task-irrelevant thought plus a prospective-memory demand are in play; Stothart's design proves a measurable lapse cost exists, but the literature provides **no per-event minutes figure**, so BU does not print one. Resumption: minutes-class in field data; the 23-minute-class pop numbers are not used. The honest total: "8 minutes off the play, plus an unmeasured residue-and-recovery margin." The Watcher's display uses exactly that structure — nominal minutes plus "plus recovery" — and never multiplies, converts to percentages, or prices drift against the fire.

**What this means for BU.**

- `[P0]` **The "23 minutes" figure is banned from all BU copy and docs** (D6). If BU ever cites a refocus number it cites "minutes-class, person-dependent" or its own N=1 data.
- `[P1]` **Watcher v1 counts drift as events and minutes, never as character verdicts.** The science says cost depends on what the drift *started* (residue logic); v1's honest proxy is app-open-during-sealed-play with duration.
- `[P2]` **Drift events that resolve (call, OTP, map-check) are priced differently from drift events that start feeds** — the full operational matrix in D1 encodes this distinction as the app-category × duration table.
- `[P2]` **Ladder thresholds are set-and-tested, not evidence-derived.** No literature pins 20/30/60 minutes; they are design priors to be tuned against relight-vs-abandonment data in P2 field use (D2 states this explicitly, per the plan's honesty requirement).

**Key studies — interruption cost.**

| Study | Design | Finding | Flag |
|---|---|---|---|
| Monsell (2003, *TiCS*) | Review of task-switching literature | Switch costs tens–hundreds ms; residual cost persists with preparation | `[REPLICATED]` |
| Rubinstein, Meyer & Evans (2001, *JEP: HPP* 27(4):763–797) | 4 experiments, task alternation, complexity/familiarity varied | Switching costs seconds-scale, grow with complexity; asymmetric (familiar→unfamiliar slower) | `[REPLICATED]` |
| Leroy (2009, *OBHDP* 109(2):168–181) | 3 studies; unfinished tasks, time pressure | Attention residue from unfinished tasks degrades next-task performance; time pressure amplifies | `[T1; Ns debt]` |
| Gonzalez & Mark (2004, CHI'04) | Field observation, information workers | ~3 min mean working sphere; ~2 min per electronic tool | `[REPLICATED-consistent]` |
| Mark, Gudith & Klocke (2008, CHI'08) | 25 workers, 3.5 days, logging | Interrupted work runs faster but with more stress/effort | `[T1-venue]` |
| Iqbal & Horvitz (2007, CHI'07) | Field logging of suspended desktop tasks | Resumption is minutes-scale in real computing work | `[T1-venue]` |
| Mark (2023, *Attention Span*) | 20-year observational program, book summary | 47 s mean screen time (2016–20); ~10.5 min per project stint; ~25 min to return | T3 — researcher's own book; summary-grade |

### §2.2 · Passive Degradation — Notifications, Mere Presence, and the Multitasker Question

**Mechanism 1: the notification alone.** Stothart, Mitchum & Yehnert (2015, *JEP: HPP* 41(6), DOI 10.1037/xhp0000100) ran the clean test BU's policy question needs: participants performed the Sustained Attention to Response Task (SART — respond to numbers, withhold on 3) in two 360-trial blocks. Block 1 was notification-free (baseline). In Block 2, participants randomly assigned to a call group received 4 real incoming calls, the text group 4 real texts, spaced 90 trials apart; the control group received none. Participants who touched their phone were excluded outright. Result: **both notification groups showed a significantly larger increase in commission errors (lapses) from Block 1 to Block 2 than controls**, and the call group additionally showed more anticipations (responses fired before stimulus recognition). Anyone who looked at or handled the phone was excluded — so the effect is the *notification itself*: a noticed-but-unanswered alert creates task-irrelevant thought (who is it? is it urgent?) plus a *prospective memory demand* ("remember to check later"), both of which tax attention while the hands stay still `[REPLICATED-consistent; magnitude comparable to active-use studies per the paper]`. The authors flag one caution: the text group's baseline was lower, so its error-rate increase needs the control comparison to interpret `[read from paper]`.

**Mechanism 2: mere presence ("brain drain").** Ward, Duke, Gneezy & Bos (2017, *JACR* 2(2), DOI 10.1086/691462) proposed that one's own smartphone, visible and reachable, occupies limited-capacity attention through inhibitory effort — so cognitive capacity measured on working-memory and fluid-intelligence tasks is lower when the phone sits on the desk (face up worst, face down intermediate) than in another room, with phone-dependence moderating the deficit (Study 1, N = 548 undergraduates `[approx. — page-level re-read queued, debt-listed]`).

The replication record is the story:

| Evidence | Result | Flag |
|---|---|---|
| Ward et al. (2017, *JACR*) | Presence effect on WM and fluid intelligence, moderated by phone dependence | original T1 |
| Ruiz Pardo & Minda (2021 preprint; 2022, *Acta Psychologica*, DOI 10.1016/j.actpsy.2022.103717) | Direct replication of the Study-2 design: **"the 'brain drain' effect did not replicate: no difference between smartphone location conditions"** | `[FAILED direct replication]` |
| Böttger, Poschik & Zierer (2023, *Behavioral Sciences* 13(9):751, PMC10525686) | Meta-analysis, **22 studies, 43 effect sizes**: significant overall negative effect of smartphone *use and presence* on memory/attention/general cognition — **but high heterogeneity**, domain-specific subgroups, and study-origin moderators | `[CONTESTED — overall effect, unreliable magnitude]` |

**Verdict for BU:** the mere-presence effect is not a settled fact BU can assert; it is a plausible, heterogeneous, replication-contested effect. The *use* effect (active distraction) is strong; the *presence* effect is the contested one. Design consequence: placement guidance ships as an **experiment invitation, not a claim** (D4), and no copy may say "your phone nearby is making you dumber."

**Mechanism 3: the chronic multitasker.** Ophir, Nass & Wagner (2009, *PNAS* 106(37), 15583–15587, DOI 10.1073/pnas.0903620106) built a trait **Media Multitasking Index (MMI)** — weighted hours across media streams — split heavy (HMMs) from light (LMMs) media multitaskers, and compared them on cognitive-control tasks. Findings: HMMs were **more susceptible to interference from irrelevant environmental stimuli and from irrelevant representations in memory**, and — the headline surprise — performed **worse on a test of task-switching ability**, plausibly because reduced filtering lets the irrelevant task set intrude `[correlational — MMI is a trait measure; no causal claim]`. The pop version ("multitasking destroys your brain") outran the paper, which found *no* difference on several of its tasks and is correlational throughout. The field's later narrowing is summarized in Uncapher & Wagner (2018, *PNAS* 115(40), 9889–9896): the most consistent thread is **breadth-biased attention** (wider, less-filtered attentional distribution) in HMMs; other effects (memory, creativity, empathy) are mixed `[review; page-level re-read queued, debt-listed]`.

**The baseline BU must respect: mind-wandering is the default.** Killingsworth & Gilbert (2010, *Science* 330(6006), 932–936, DOI 10.1126/science.1192427): 2,250 adults, ~250,000 random-interval samples via a smartphone ESM app — people reported **mind-wandering 46.9% of waking hours** (≥30% in every activity except one), and mind-wandering predicted unhappiness better than what they were doing (it accounted for ~10.8% of happiness variance vs. ~4.6% for activity; time-lag analyses suggested wandering *preceded* the unhappiness). Two design consequences, both load-bearing: (a) drift detection is watching a mind that drifts half its waking life — the Watcher must expect drift, not treat it as aberration; (b) BU never claims to eliminate wandering; it *brackets* it (sealed windows reduce opportunity and add a recovery path).

**Measurement honesty (what the A54 can actually see).** UsageStatsManager provides per-package foreground event times — app-level only, no in-app content. Android's "app categories" are developer-declared metadata, not measured behavior, which is why D1's category layer ships as *user labels + defaults* rather than trusted platform data. Notification interception (NotificationListener) could count pings arriving during a seal, but D3's queueing rule makes it unnecessary for v1. And screen-off is not attention (Phase 3's sleep-proxy lesson transfers directly): the Watcher measures *opportunity structure*, not cognition. Every drift number BU shows is therefore a lower bound on cost, and copy says so once, at consent, not after every event (D6).

**What this means for BU.**

- `[P0]` **BU's notification policy is evidence-first (D3):** Stothart shows an unattended ping is not a neutral ping. P0 ships with **no scheduled interruptions** except the Evening Close (user-set time) and explicit user-created reminders. No streak pings, no motivational push, no "your fire is dimming" alerts.
- `[P1]` **The Watcher is silent during sealed sessions.** If a sealed play is active, BU queues its own notifications until the seal ends. The one app that must never interrupt focus is BU itself.
- `[P1]` **Drift copy stays neutral by design** (D6): the baseline rate (46.9%) is the statistical rebuttal to any "what's wrong with you" framing. Drift lines report ("12 min in Social during the play"), never diagnose.
- `[P2]` **Hard Focus placement guidance is an experiment card, not a claim** (D4): "Try phone-in-another-room for two weeks of evening plays; BU will show you your own drift comparison." Framed per the meta as personal evidence with unknown magnitude for this person.
- `[all]` **Copy bans extended** (D6, joining Phase 1 D7/D18 and Phase 3 D9): no "brain drain" assertions, no "23 minutes," no "multitasking ruins your brain," no "notifications make you 10 IQ points dumber"-class claims, no goldfish-attention comparisons.

**Key studies — passive degradation.**

| Study | Design | Finding | Effect | Flag |
|---|---|---|---|---|
| Stothart, Mitchum & Yehnert (2015, *JEP: HPP* 41(6)) | SART × 2 blocks × 360 trials; 4 calls / 4 texts / none; touchers excluded | Unattended notifications increase lapse errors vs. control | magnitude ≈ active-use studies (paper's own comparison) | `[REPLICATED-consistent]` |
| Ward, Duke, Gneezy & Bos (2017, *JACR* 2(2)) | N = 548 `[approx.]`; phone in other room vs. pocket vs. desk face-down/up; WM + fluid-intelligence items | Presence deficit, worst face-up; moderated by dependence | d-class moderate between extreme conditions `[approx.]` | `[CONTESTED]` |
| Ruiz Pardo & Minda (2022, *Acta Psychologica*) | Direct replication of Ward Study 2 design | No difference between location conditions | ~0 | `[FAILED direct replication]` |
| Böttger, Poschik & Zierer (2023, *Behav Sci* 13(9):751) | Meta: 22 studies, 43 effects | Overall negative effect of use + presence; high heterogeneity; domain-specific | significant, heterogeneous | `[CONTESTED]` |
| Ophir, Nass & Wagner (2009, *PNAS* 106(37)) | MMI trait split; cognitive-control battery | HMMs: worse filtering (environment + memory intrusions); worse task-switching | correlational | `[REPLICATED-narrowed: breadth-bias thread]` |
| Uncapher & Wagner (2018, *PNAS* 115(40)) | Review of the media-multitasking literature | Most consistent: breadth-biased attention; other claims mixed | — | `[review]` |
| Killingsworth & Gilbert (2010, *Science* 330:932) | 2,250 adults, ~250k ESM samples | Mind-wandering 46.9% of waking hours; predicts unhappiness (cause per time-lag) | 46.9%; ~10.8% happiness variance | `[REPLICATED-consistent]` |

<!-- CONTINUED -->
### §2.3 · Flow and the Sealed Session — What BU Can Promise and What It Cannot

**Mechanism.** Csikszentmihalyi's flow research (T3: *Flow*, 1990; synthesized in Nakamura & Csikszentmihalyi 2009, *Oxford Handbook of Positive Psychology*) describes the phenomenological state of full absorption in an activity, defined by three operating conditions — **clear goals, immediate feedback, challenge–skill balance** — plus the merger of action and awareness and a distorted sense of time. The channels model maps the state space by perceived challenge vs. perceived skill: anxiety when challenge outruns skill, boredom when skill outruns challenge, and the flow channel between them, reached from arousal (high challenge, growing skill) or control (high skill, rising challenge) channels. The critical claim for BU is directional: **the three conditions are environmental variables an app can arrange** — whereas flow itself is a self-report state an app cannot directly observe.

**Measurement honesty.** Flow's evidence base is experience-sampling: the ESM pings people at random moments and asks what they feel and whether they are doing something worth doing; flow studies in the ESM tradition are therefore correlational snapshots, and the method's own authors note that random interruption degrades the very state being sampled. In-app proxies (session length, app-switch rate, completion) measure the *conditions* of flow, not the state. BU will not display "flow detected" — it lacks both the instrument and the warrant. This is the same evidence-grade discipline Phase 6 formalizes; it is asserted here first as copy law (D6).

**Where the science helps the sealed session.** The sealed play (Phase 1 D11) is not an attempt to *induce* flow; it is an attempt to **arrange flow's preconditions** and remove flow's known enemies:

| Flow condition | BU surface | Already locked by |
|---|---|---|
| Clear goal | The Play sentence grammar: one verb-phrase action, duration, place/tool | Phase 1 D1 |
| Immediate feedback | Retrieval-first study card closes with an answer; fire brightening on tending | Phase 2 D1; Phase 3 D1 |
| Challenge–skill balance | ZPD target band 75–90% success on retrieval attempts | Phase 2 D7 |
| No interruptions | Sealed window + Watcher passivity + BU notification policy | Phase 1 D11; this phase D3 |
| Challenge calibration under low energy | Play-size priors (sleep-aware), floor-sized plays in stress windows | Phase 3 D3, D8 |

That table is the honest mapping of the v6 claim "sealed sessions create focus" to the literature: BU arranges conditions; whether flow occurs is the user's report to write in the journal, not BU's claim to make.

**Attention trainability — the decision BU must make explicitly.** Can attention itself be trained, the way strength is? The candidate evidence is Mrazek, Franklin, Phillips, Baird & Schooler (2013, *Psychological Science* 24(5), 776–781, DOI 10.1177/0956797612459659): 48 college students randomized to a **2-week mindfulness class vs. a nutrition class** (45 minutes, four times per week); the mindfulness group showed **improved working-memory capacity, improved GRE reading comprehension ("the equivalent of a 16 percentile-point boost on the GRE, on average"), and reduced mind-wandering** during the tasks `[SINGLE STUDY, N = 48 — [EMERGING]]`. It is a clean design with a striking number, and it is one small sample from one lab. Mindfulness-training meta-analyses exist in adjacent literatures but do not rescue this specific claim to decision-grade. **Decision: BU does not ship "attention training" as a feature or promise (copy ban, D6).** What P0–P2 legitimately do is *reduce environment-side cost* (notifications, friction, drift detection) and *structure retrieval practice* — the same distinction Phase 3 drew for brain training via the plasticity checklist. The builder's own N=1 mindfulness trial stays in the Open Questions queue, not the roadmap.

Newport's *Deep Work* (2016, T3, practitioner framing only) supplies no evidence this program may cite, but its practitioner advice — schedule focus, embrace boredom, quit attention-switching at fixed boundaries — is consistent with what the T1 record supports, and its scheduling vocabulary ("deep work blocks") maps cleanly onto the sealed play. Used as framing only.

**The channels model, operationalized.** The classic two-axis plot (perceived challenge × perceived skill, each relative to the person's own average) yields the flow channel along the diagonal where both are high and matched. Its usable arithmetic is a ratio heuristic: when the challenge/skill ratio pushes well above ~1 with skill low, the state reported is anxiety; well below ~1 with challenge low, boredom; flow concentrates near 1 with both above the personal average. The precision here is low (self-report axes, person-relative anchors — the model is a map of verbal reports, not a measurement device), but it gives BU's difficulty calibration its target: Phase 2 D7's **75–90% success band** on retrieval attempts is exactly a challenge–skill ratio held near (slightly above) 1, computed from behavior instead of asked. The Play is a flow-shaped environment by construction: one clear goal (grammar), one feedback close (retrieval answer), difficulty pinned to the band, door sealed.

**Evidence table — flow.**

| Source | Design | Finding | Flag |
|---|---|---|---|
| Csikszentmihalyi (1990, *Flow*) + ESM program | Experience sampling across occupations | Flow reported more *at work* than in leisure (the "paradox of work"); conditions: clear goals, immediate feedback, challenge–skill balance | T3 + T1-venue ESM line |
| Nakamura & Csikszentmihalyi (2009, handbook) | Review | Conditions model; channels model; flow as attentional state | `[framework]` |
| Mrazek et al. (2013, *Psych Science* 24(5):776–781) | RCT, N = 48, 2-week mindfulness vs. nutrition class | WMC ↑, GRE reading ↑ (16-percentile equivalent), mind-wandering ↓ | `[EMERGING — single study]` |

**What this means for BU.**

- `[P0]` **The sealed play is sold as protection, not as a state guarantee.** Copy class: "20 minutes with one door closed" — never "get into flow." (D6)
- `[P0]` **Evening Close never asks "were you in flow?"** It asks observable questions (recall, completion, pull-aways) per Phase 1 D2; flow reports belong to the optional free-write line.
- `[P2]` **The drift overlay copy carries the flow-conditions frame:** name what was arranged ("clear goal, one tool, a finish line") rather than promising a feeling.
- `[P3]` **If Forge ever narrates patterns, it narrates conditions** ("your completions cluster in sealed evening windows with 0–1 drift events"), never states ("you were in flow").

### §2.4 · When Interventions Backfire — Reactance, Friction, and the Ladder's Guardrails

**Mechanism 1: reactance.** Brehm (1966, *A Theory of Psychological Reactance*, Academic Press) proposed that a perceived threat to freedom — being told what to do, or feeling blocked — motivates *restoring* the freedom: doing the forbidden thing, devaluing the persuader, or feeling anger. The construct survives as one of persuasion research's workhorses, and its modern measurement is Dillard & Shen (2005, *Communication Monographs* 72(2), 144–168, DOI 10.1080/03637750500203872): a meta-analysis in which reactance operates as a **combination of anger and negative cognitions**, and reactance consistently *reduces* persuasion and can boomerang behavior `[meta-analytic; effect magnitudes page-level re-read queued, debt-listed]`. For an intervention ladder that escalates from nudge to block, this is the binding constraint: **each rung increases perceived freedom threat, so each rung must either be user-installed, reversible, or rare — ideally all three.** The self-nudge framing is the escape hatch: the same mechanism that triggers reactance when imposed becomes acceptable when the user configured it, because the threat-to-freedom appraisal requires an external agent.

**Mechanism 2: friction (the evidence BU's soft rungs were waiting for).** Grüning, Riedel & Lorenz-Spreen (2023, *PNAS* 120(8), e2213114120, DOI 10.1073/pnas.2213114120) studied **one sec**, a self-nudge app that inserts a brief breathing-screen delay (and a dismiss option) before selected apps open:

- In the observational field data, users **dismissed their consumption attempt in 36% of opens** of target apps once the friction screen appeared (1 in 3 attempts dies at the decision point).
- Users **attempted to open target apps 37% less often** overall — the friction changes *impulse formation*, not just impulse outcomes.
- In the controlled experiment, **actual openings of target apps fell 57% after six consecutive weeks**, and self-reported life satisfaction improved (PubMed abstract, read this pass).
- The mechanism dissection (controlled experiment): **displaying the dismiss option was the most effective feature; the time delay itself also contributed.** The intervention works as a *decision point between intention and action*, not merely as a pause `[field study + RCT components; vendor-affiliated data — flagged: lead scientist of the study is the app's lead scientist; treated as T1-venue with interest note]`.

Two properties make this the right template for BU's ladder. First, **small, early, informational** interventions beat large, late, coercive ones on the evidence: the dismissal happens *before* the feed loads. Second, **the effect survived six weeks** — friction did not fully habituate away in the study window (habituation beyond six weeks is an Open Question).

**The gradient BU adopts.** Ordered by increasing freedom threat, with evidence where each rung stands:

1. **Silence** (default; the Watcher watches, says nothing). Evidence: Stothart — *BU's own pings are not neutral*; the least-intervention principle.
2. **One soft nudge per session** (dismissible, no guilt copy, no repeat). Evidence: self-monitoring salience (Phase 1, Harkin et al. 2016 d ≈ .40) + the friction finding that a decision point at the right moment does the work.
3. **Friction overlay** (one sec-style interstitial on drift-app opens inside the sealed window: "The play is sealed. One tap to continue anyway, one tap to go back."). Evidence: 36% dismiss / 37% fewer attempts / 57% fewer openings; dismiss-option primacy.
4. **Overlay + block** — **only** inside a session the user pre-declared as Hard Focus, never for calls, never platform-wide. Evidence: self-nudge literature + reactance constraint; the block is user-installed, which changes its psychological class.

**What this means for BU.**

- `[P2]` **The ladder is friction-first and consent-stamped (D2).** Rung 4 exists only for windows the user sealed with Hard Focus declared; every rung's copy is choice-language ("continue anyway / go back"), never verdict-language.
- `[P2]` **Rung counts are capped.** One soft nudge per session; the friction overlay fires on drift-app opens but the aggressive rung requires both accumulated drift minutes *and* repeated events, not one bad minute. Escalation lives in D2's decision table.
- `[P0]` **The P0 seed of this rule costs nothing:** BU has no Watcher in P0, but the copy system already obeys the gradient — no shame, no push, no verdicts (Phase 1 D5/D7). The ladder inherits a culture, not just rules.
- `[P2]` **The "app goes dark" mechanic (v6) survives ethics review only as a friction, not a punishment.** The overlay is informational; withholding the fire's own value is Phase 5's debate (cross-ref), but the *intervention* side is settled here: no forced lockouts outside user-declared Hard Focus.

**Key studies — reactance & friction.**

| Study | Design | Finding | Effect | Flag |
|---|---|---|---|---|
| Brehm (1966, Academic Press) | Theory + experiments | Threat to freedom → restoration motivation (boomerang) | — | `[foundational, replicated paradigm]` |
| Dillard & Shen (2005, *Comm Monographs* 72(2):144–168) | Meta-analysis | Reactance = anger + negative cognitions; reduces persuasion | negative, consistent `[magnitudes debt]` | `[T2-class meta]` |
| Grüning, Riedel & Lorenz-Spreen (2023, *PNAS* 120(8)) | Field data + controlled experiment, 6 weeks | 36% dismissals at friction screen; 37% fewer attempts; 57% fewer openings after 6 weeks; dismiss option > delay | large | `[T1-venue; vendor-affiliated — interest noted]` |

<!-- CONTINUED -->
### §2.5 · When: Chronotype, Synchrony, and the Death of the Morning Default

**Mechanism.** Circadian arousal is not a fixed trait of character but a phase parameter of the internal clock, and it varies systematically across the population and across the lifespan. Roenneberg, Wirz-Justice & Merrow (2003, *J. Biological Rhythms* 18(4), 328–337) operationalized chronotype with the **Munich ChronoType Questionnaire (MCTQ)**, using sleep behavior on work-free days to estimate individual clock phase. The developmental trajectory is the fact that breaks BU's v6 assumption: chronotype becomes progressively later through adolescence, **reaching its maximum lateness around age 20** (Roenneberg et al. 2004, *Current Biology* 14(24), R1038–R1039, DOI 10.1016/j.cub.2004.11.039), then drifts earlier with age `[REPLICATED-consistent across MCTQ samples; the underlying distributions are large-N]`. The misalignment between social schedules and biological phase is **social jetlag** (Wittmann et al. 2006, *Chronobiology International*), and late chronotypes carry more of it — which matters because BU's builder is a young adult, precisely the population at the evening end of the distribution.

**Synchrony effects.** May & Hasher (1998, *JEP: HPP* 24(2), 363–379, DOI 10.1037/0096-1523.24.2.363 — **journal corrected this pass; the master plan's "Psychology and Aging" attribution is wrong**) tested college students (18–23) and older adults (60–75) at their self-identified peak vs. off-peak times of day. Performance on tasks demanding inhibitory control was **better at peak time than off-peak**, in both age groups — with peaks in opposite directions (older adults morning-peaked, young adults evening-skewed). The effect is not "people are useless at their bad hour"; it is that **inhibitory control — precisely the machinery BU's plays ask for — rides circadian phase**. The practical consequence for scheduling is directional, not deterministic: peak-time scheduling is a prior, not a guarantee, and the effect sizes from the synchrony literature are modest and task-dependent `[Medium confidence; single-lab line + replications in adjacent tasks]`.

**What the v6 assumption actually was.** "Before noon" as the universal study window is a morningness default wearing the costume of productivity advice. Against the age-20 lateness peak, the builder's own likely chronotype (a young adult building BU) sits evening-side; a morning default could be quietly fighting biology rather than building habit. But the correction must run both ways: **chronotype science does not license "I'm not a morning person" as an escape hatch either.** The claim BU adopts is symmetry: *play windows should track the person's phase, not the clock's folklore, in either direction.*

**The Evening Close itself is a chronotype hypothesis.** For a late chronotype (the modal young adult), a fixed 21:00 close sits *inside* the biological evening peak — which the evidence favors — while an early-morning wake anchor is where social jetlag accumulates (Wittmann et al. 2006): the late-typed student forced to 06:30 lives in a permanent phase debt that no productivity method repays. Two boundaries follow. First, chronotype science is a *placement* science, not a sleep-hygiene lecture: BU's only output is window placement (D5); any sleep-quality claims stay under Phase 3 D4's causal-humble grammar. Second, the symmetry rule: BU never celebrates early rising and never licenses lateness — "the window that matches your phase" is the whole claim, in either direction.

**What this means for BU.**

- `[P0]` **Onboarding gains a chronotype question (D5), joining Phase 1 D3's onboarding v2.** Wording class: "When does your head work best?" — Morning / Midday / Evening / It varies — plus an optional wake-time anchor line (feeds Phase 3's sleep-aware priors). The answer sets the *default suggestion* for play windows; the default is a suggestion, swappable like everything else in BU.
- `[P1]` **Play windows become adaptive:** Forge watches Evening Close completion times and actual tend-timing; if the user's successful tends cluster outside the stated window, BU proposes (never silently moves) a window shift — propose-and-confirm per Phase 1's architecture.
- `[P0]` **No chronotype identity labels, ever.** "You're a bear/owl/lark" is a trait claim (banned by Phase 1 D18) with no action implication; the only output of the question is a suggested window.
- `[P2]` **The Evening Close at 21:00 is itself a chronotype hypothesis.** v6 fixed 21:00; the evidence says boundary delivery at a stable time matters (context anchoring, Phase 3 D6), but 21:00 vs. 22:30 is the user's to keep or move. P0 ships the fixed default; P1 makes it adjustable without guilt copy.
- `[P3]` **Pattern claims about timing use the Phase 6 causal grammar.** "Your tendings land more often in the 20:00–22:00 window" is OBSERVED-grade and allowed; "you're smarter at night" is banned.

**Key studies — chronotype & synchrony.**

| Study | Design | Finding | Effect | Flag |
|---|---|---|---|---|
| Roenneberg, Wirz-Justice & Merrow (2003, *J Biol Rhythms* 18(4):328–337) | MCTQ instrument paper | Chronotype measured from work-free sleep; population distributions | — | `[instrument, REPLICATED across samples]` |
| Roenneberg et al. (2004, *Curr Biol* 14(24):R1038) | Large-N MCTQ cross-section | Chronotype lateness peaks ~age 20, then advances | trajectory | `[REPLICATED-consistent]` |
| May & Hasher (1998, *JEP: HPP* 24(2):363–379) `[journal corrected]` | Young (18–23) & older (60–75) adults tested at peak/off-peak | Inhibitory control better at peak in both groups; young adults evening-skewed | modest, task-dependent | `[Medium; lab line + adjacent replications]` |
| Wittmann et al. (2006, *Chronobiol Int*) | MCTQ-based social jetlag work | Late chronotypes carry larger social jetlag | — | `[REPLICATED-consistent]` |
| Roenneberg (2012, *Internal Time*) | Researcher's own book | Narrative of chronotype science | — | T3 context |

### §2.6 · The Self-Control Paradox — Situation Modification Is the Strong Move

**Mechanism.** The replicable portrait of people who score high on trait self-control is *not* "they resist temptation better." Galla & Duckworth (2015, *JPSP* 109(5), 769–781, DOI 10.1037/a0038471; author-accepted text PMC4731333) ran **six studies (total N = 2,274)** across eating, exercise, sleep, and study domains: trait self-control predicted positive outcomes, but the mediation ran through **beneficial habits** — automaticity of the good behavior — and *not* through effortful in-the-moment inhibition. From Study 1: the indirect effect of self-control on outcomes through habit measures was reliable for exercising (β_indirect = −.12 toward less difficulty, p < .001), healthy snacking (β = −.05, p < .001), and bedtime (β = −.04, p = .011); exercise *automaticity* carried the largest share (β = .13, p < .001). Conclusion sentence, as printed: "Collectively, these results suggest that beneficial habits—perhaps more so than effortful inhibition—are an important factor linking self-control with positive life outcomes" `[REPLICATED-consistent; multi-domain, cross-sectional mediation — directionality caveats noted by authors]`.

Duckworth, Gendler & Gross (2016, *Perspectives on Psychological Science* 11(4), 565–571, DOI 10.1177/1745691616620247) supply the process model that explains *why*: self-control tactics differ in **where they intercept the impulse generation process**. Situational strategies (choosing or modifying the situation *before* the cue arrives — putting the phone in a drawer, uninstalling the feed) intercept earliest and cheapest; attentional, cognitive-change, and response-inhibition strategies engage progressively later, costlier, and less reliable stages. The strategic implication, from the process model's own ordering: **arrange the situation so the fight never starts** `[framework paper; the ordering claim is consistent with the habit-mediation evidence above and with Phase 3's 43% context-cued behavior figure]`.

**The paradox resolved for BU.** Galla & Duckworth's finding closes the loop with Phase 1's identity claims and Phase 3's context world: the person who "wins" at self-control is the one whose environment makes winning the default. Two design inversions follow, and both reverse pop-product instincts:

1. **The whitelist is not cheating.** Hard Focus whitelisting (calls + a handful of apps during sealed windows) is textbook situation modification — the same class of move as not keeping cookies on the counter. BU's copy must say so, because users will *feel* it as weakness ("I need the app to block me"). Reframe: "the strong arrange; the arrangement is the strength" — in BU's actual copy voice, something like "You sealed the door. That's the discipline." (D4)
2. **Drift is an environment report, not a character report.** A drift event means a cue outran a context — the 43% world (Phase 3) reasserting itself. The correct response chain is: log (OBSERVED evidence) → offer friction or window edit → let the user adjust the environment. "You were weak today" is banned copy twice over: once by Phase 1's shame ban, and now by the mediation evidence (trait verdicts do nothing; environment edits and habit automaticity do the work).

**The tie back to Phase 1: why BU never built a willpower gym.** Phase 1's verdict on ego depletion stands (`[FAILED]` — Hagger et al. 2016 registered replication): there is no fuel tank to train, so "resistance reps" is not a product. The situation-modification literature supplies what the strength model could not: the reason high-trait people look strong is that their environments spare them the fight (Galla & Duckworth 2015; Duckworth, Gendler & Gross 2016). Phase 1 D6's low-energy play sizing now has its mechanism — floor-sized plays on bad days are environment design (removing the need for inhibition), not lowered standards. Across all four phases the theory is consistent and it is not a willpower app: it is a world arranged so willpower is rarely the tool being tested.

**What this means for BU.**

- `[P0]` **Onboarding obstacle-mapping stays environmental.** Phase 1 D3's WOOP step asks "what pulls you off track" — Phase 4 adds the follow-through: the answer should surface as an environment edit (friction suggestion, window shift), not a resolution to try harder. `[P0: the copy question; P2: the mechanical suggestion]`
- `[P2]` **Hard Focus ships with the situation-modification rationale line** (D4) and default-on respect for calls.
- `[P2]` **Every ladder rung pairs with an environment offer.** After any rung fires, one line: "Want me to make this harder to open next time?" — the ladder's job is to convert drift into design, session after session.
- `[P1]` **Forge pattern language stays environmental** ("your drift concentrates 10–15 min into evening plays — before the first retrieval close"), which is actionable; trait language ("distractible") is banned and, per the evidence, useless.

**Key studies — situation modification.**

| Study | Design | Finding | Effect | Flag |
|---|---|---|---|---|
| Galla & Duckworth (2015, *JPSP* 109(5):769–781) | 6 studies, N = 2,274; mediation models | Beneficial habits (not effortful inhibition) mediate self-control's benefits | β_indirect .13 (exercise automaticity) class | `[REPLICATED-consistent]` |
| Duckworth, Gendler & Gross (2016, *PPS* 11(4):565–571) | Process-model framework | Situational strategies intercept impulses earliest/cheapest; inhibition last | — | `[framework; consistent with above]` |
| Wood, Quinn & Kashy (2002, *JPSP*) — bridged from Phase 3 | Diary studies | ~43% of daily behavior context-cued/habitual | 43% | `[bridged]` |

<!-- CONTINUED -->
## 3 · Design Decisions

**Decision table.** Evidence = the section above that carries the citation chain. Confidence per the program's three-level vocabulary. Tag = earliest build phase that can act.

| # | Decision (compact) | Evidence | Confidence | Tag |
|---|---|---|---|---|
| D1 | **Drift operational definition v1**: app-category × duration × play-context matrix (below); never-counts list; 60 s grace; P1 coarse form = any non-whitelisted app-open during a sealed play | §2.1 (residue logic; field-data minutes-scale) | High (definition), Medium (category weights) | P2 (matrix) / P1 (coarse form) |
| D2 | **Ladder v1.1**: silence → one soft nudge → friction overlay → overlay+block (Hard Focus only); guardrails: 1 nudge/session cap, dismiss-option always present, block never for calls, auto-revert after 2 override-abandoned Hard Focus sessions; thresholds 20/30/60 declared set-and-tested | §2.4 (one sec 36/37/57; Brehm; Dillard & Shen); §2.2 (Stothart) | High for ordering & guardrails; Low for the 20/30/60 priors (declared) | P2 |
| D3 | **BU notification policy**: P0 ships exactly two classes (Evening Close at user-set time; user-created reminders); boundary delivery only; never mid-seal; queue during sealed sessions; persistent-notification content rules at P1 (no streak pressure, no "fire dying" urgency) | §2.2 (Stothart 2015); §2.3 (interruption breaks flow conditions) | High | P0 |
| D4 | **Hard Focus whitelist guidance**: default = calls + none; suggest ≤5 user-declared apps; placement advice ships as a two-week experiment card (N=1 comparison), never as a claim; rationale line: situation modification is legitimate self-control | §2.2 (Ward `[CONTESTED]`, Ruiz Pardo & Minda `[FAILED]`, Böttger meta); §2.6 (Galla & Duckworth; Duckworth, Gendler & Gross) | High for whitelist-as-sitmod; Low for presence claims | P2 |
| D5 | **Chronotype question in onboarding** ("When does your head work best?" + optional wake anchor) sets the default play-window suggestion; morning default killed; windows adaptive at P1 via propose-and-confirm; no chronotype identity labels; 21:00 Evening Close stays fixed in P0, user-adjustable from P1 | §2.5 (Roenneberg 2004 age-20 peak; May & Hasher 1998 synchrony) | Medium | P0 |
| D6 | **Watcher copy rules + ban-list extension**: drift lines report, never diagnose; banned: "23 minutes," goldfish, brain-drain assertions, "multitasking ruins your brain," addiction diagnostics, flow promises, attention-training promises, chronotype labels; observation-reactivity disclosed at Watcher consent | §2.1 (precision audit); §2.2 (46.9% baseline); §2.3 (Mrazek `[EMERGING]`); §2.4 | High | P1 (P0 seed: the bans cost nothing) |
| D7 | **Educational exception (P3)**: accessibility context may exempt only user-declared per-play tool apps; title-only auto-classification banned; unlabeled educational-looking apps count as drift; post-session one-tap retro-tag upgrades the entry | §2.2 (category unreliability; Ophir narrowing); Master Plan §7 controversy | Medium | P3 |
| D8 | **Drift events are OBSERVED evidence, never fire-verdicts**: a drift event dims nothing by itself (fire weight rides the Phase 7 scoring function over tendings); Evening Close adds optional "what pulled you away?" line feeding obstacle+plan (WOOP link); no drift percentages or "-X% fire" displays | §2.6 (environment report, not character report); Phase 1 D4 seeds; Phase 6 grades | High | P0 (question line) / P1 (evidence class) |

**D1 — Drift operational definition v1.** The Watcher (P1: UsageStats + foreground service) can see app-foreground events; what it *means* by "drift" is this phase's product. v1 matrix:

| App class | Counts as drift? | Rules |
|---|---|---|
| Phone/call screen | never | also never interrupts the fire's semantics (a call is life, not drift) |
| System UI, authenticator/OTP, camera | never | ≤60 s class; priced as "touch," logged, no rung |
| The play's declared tool app | never | declared at seal time; swap = re-seal (Phase 1 D15) |
| BU itself | never | BU never counts as drift; it also never interrupts (D3) |
| Maps/navigation | conditional | while geographically moving (P3+); stationary scrolling in maps counts |
| Communication (chat) | conditional | first reply ≤60 s grace ("touch"); sustained use ≥2 min counts toward drift minutes |
| Social feeds, video, games, shopping, news | always | foreground minutes accumulate; "start-class" drifts carry residue weight in copy that ever needs it |

- **P1 coarse form:** without category data, any non-whitelisted app-foreground during a sealed play = one drift event; duration recorded. Categories ship P2 with user labels + platform defaults (an imperfect mapping, labeled as such).
- **Accumulator (P2 implementation math):** `drift_minutes(session) = Σ foreground minutes over counting-class apps`; a single app-open < 60 s logs as a *touch* (event, no minutes, no rung). Rung state: `(drift_minutes, sustained_open, nudge_used, dismissals_in_row, hard_focus_declared)` — **R1** fires iff `(drift_minutes ≥ 20 ∨ sustained_open ≥ 2 min) ∧ ¬nudge_used`; **R2** arms iff `drift_minutes ≥ 30`, firing per drift-app open, with a 30-min cool-down after 3 consecutive dismissals; **R3** is *active from seal time* iff `hard_focus_declared` (blocking is the declared state, not a drift-triggered surprise), with one-tap override that asks and logs.
- **Never hidden:** the user can always see what the Watcher counted and correct a label — misclassification is treated as a data bug, not user error (Phase 6's grade discipline applies).

**D2 — Ladder v1.1 decision table with guardrails.** (Phase 5 formalizes this as the JITAI table; what is locked here is the rung logic, ordering, and guardrails.)

| Rung | Fires when | Intervention | Copy class | Guardrail |
|---|---|---|---|---|
| 0 · Silence | default; drift < threshold | none | — | the Watcher's silence is evidence-based (§2.2), not neglect |
| 1 · Soft nudge | ≥20 min accumulated drift (prior) OR first ≥2 min sustained drift-app open | one dismissible nudge | "Still here? The play is sealed until 20:40. Back anytime." | **max 1/session**; no guilt, no streaks, no fire theatrics |
| 2 · Friction overlay | ≥30 min accumulated drift (prior); each drift-app open inside the seal | one sec-class interstitial with **explicit dismiss option** | "One tap back to the play. One tap to continue anyway." | after 3 dismissals-in-a-row, 30-min cool-down (reactance brake) |
| 3 · Overlay + block | **only** if Hard Focus was declared at seal time (active from seal, not triggered by drift) | block non-whitelist apps; calls always pass; one-tap override exists | "You sealed this window. End the seal?" | override logged + asked (never silent); 2 override-abandoned sessions in a row → Hard Focus pauses itself, floor-sized play offered instead |
| 3-fallback · early close (no Hard Focus) | 60 min accumulated drift in a normal seal | no block — offer a graceful close: one-tap retrieval line + "try Hard Focus next time" | "60 minutes drifted. Want to close the play with one line of recall?" | never blocks, never shames; the offer repeats only next session |

- **Evidence-driven vs. set-and-tested (stated per the plan's honesty requirement):** the *ordering and guardrails* are evidence-driven (Stothart for silence; one sec for friction-before-block and for the dismiss option; Brehm/Dillard & Shen for caps and user-installation). The *numbers* — 20/30/60 minutes, 60 s grace, 2-min sustained trigger, 30-min cool-down — are **set-and-tested design priors**, explicitly not literature-derived, to be tuned against P2 field metrics (drift→relight conversion up; session abandonment flat).
- **Stress-window interlock (Phase 3 D8):** during declared stress windows the ladder holds at rung 1 maximum — floor-sized plays and muted feedback were Phase 3's decision; this phase honors it.

**D3 — BU's own notification policy (P0).** The one app that must never interrupt focus is BU itself. P0: exactly two notification classes — Evening Close at the user-set time, and user-created play reminders (zero by default). Boundary delivery only: notifications fire at window boundaries, never inside a sealed session; anything queued waits until the seal ends. P1 adds the persistent fire-state notification with content rules (a status line, never urgency copy, never streak pressure — full copy guide is Phase 5 D2). Rationale chain: Stothart 2015 (unattended pings cost), flow conditions (§2.3), Phase 1 D17 (autonomy-framed consent).

**D4 — Hard Focus whitelist guidance (P2).** Default whitelist = calls + none; the user may declare ≤5 apps (suggested: OTP/authenticator, the play's companion tool). Placement guidance ships as a card, not a claim: "Experiment — phone in another room (or face-down, drawer) for two weeks of evening plays. BU will show you your own drift comparison." The card cites *nothing* as fact because the presence effect is `[CONTESTED]`; it offers personal evidence instead. The rationale line ships with the whitelist UI: situation modification is the *strong* move (§2.6) — "You sealed the door. That's the discipline."

**D5 — Chronotype question (P0) and adaptive windows (P1).** The question joins Phase 1 D3's onboarding v2 as its fourth item: "When does your head work best?" — Morning / Midday / Evening / It varies — plus one optional line: "Around when do you usually wake?" (feeds Phase 3 D3's sleep priors; not required). Output discipline: the answer only sets a *suggested default window* for plays; it produces no label, no score, no explanation of character. P1: if successful tendings cluster outside the stated window, Forge proposes a window shift (propose-and-confirm, rationale attached). The 21:00 Evening Close remains fixed in P0 and becomes user-adjustable in P1 without guilt copy.

**D6 — Watcher copy rules (P1; P0 seed).** Ban-list extensions to the Phase 1 D7/D18 and Phase 3 D9 lists: "23 minutes" (any refocus-time fact-print), goldfish-attention comparisons, "brain drain" assertions, "your phone is making you dumb," "multitasking ruins your brain," addiction diagnostics ("you're addicted to X"), flow promises ("enter flow"), attention-training promises ("train your focus like a muscle"), chronotype identity labels ("you're an owl"). Drift copy class = report, never diagnose: PASS — "Drift: 12 min in Social during the sealed play." FAIL — "You lost 12 minutes of focus (that's ~9% of your day's productivity)." Observation-reactivity is disclosed at Watcher consent, not hidden: "watching changes behavior — that's the point" (Phase 1 D17 consent frame).

**D7 — Educational exception spec (P3).** When BU gains accessibility-based context reading (P3), the v6 "educational exception" (learning apps don't count as drift during study plays) may run **only on user-declared per-play tool lists** — declared at seal time or added by one-tap retro-tag after the session ("was this for the play?"). Title-only auto-classification ("looks like a learning app") is banned: category signals are unreliable (§2.2's narrowing, Ophir's task-dependence), misclassification punishes honest use, and the exception would otherwise become a loophole the drift definition can't defend. Unlabeled educational-looking apps count as drift, with the retro-tag as the correction path.

**D8 — Drift events as evidence, not verdicts (P0 question / P1 class).** Drift events enter the record as OBSERVED-grade evidence (Phase 6's criteria will operationalize the grade). They **never** directly dim the fire: fire weight is a function of tendings (Phase 7's scoring function, seeded by Phase 1 D4), so drift dims the fire only insofar as it displaces tendings — which is the honest causal chain, and it is *information*, not punishment. Evening Close (P0) gains one optional line in the pull-aways slot: "What pulled you away?" — the answer maps to obstacle+plan (Phase 1 D3's WOOP step), closing the loop from observation to environment edit (§2.6). No drift percentages, no "-X% fire" displays, no "focus score," ever.

---

## 4 · Contradiction Cards

**Card A — Hard blocking vs. autonomy.** *Claim A:* external structure works — self-imposed deadlines help less than external ones (Phase 1 §2.6, Ariely & Wertenbroch 2002), and Hard Focus is BU's one true hard commitment (Phase 1 D12). *Claim B:* reactance theory says imposed blocking provokes restoration behavior (Brehm 1966; Dillard & Shen 2005). *What is actually disputed:* the magnitude of reactance under **self-installed** constraints — the literature supports both claims under different conditions, and the boundary between them (installed-by-whom) is exactly what BU's design controls. *Working resolution:* block exists only inside user-declared Hard Focus windows; the override always exists but is logged and asked ("End the seal?"); friction-first ordering keeps rung 3 rare. The cage/user-installed-scaffold distinction is Phase 1 Card A's, extended down the ladder. *Flip condition:* P2 field data — override rate > ~30% of Hard Focus sessions (mirroring Phase 1 Card A's swap-rate threshold) → drop rung 3 to friction-only and re-examine.

**Card B — Mere presence: guidance vs. contested evidence.** *Claim A:* the meta-analysis finds an overall negative effect of smartphone presence on cognition (Böttger et al. 2023), and placement advice is nearly free to give. *Claim B:* the flagship direct replication failed (Ruiz Pardo & Minda 2022) and heterogeneity is high — asserting a presence claim would violate the program's Evidence Charter. *What is actually disputed:* whether the presence effect is robust enough to advise on, vs. a publication-fragile lab phenomenon. *Working resolution:* the experiment card (D4) offers the test, BU's own N=1 comparison supplies the verdict, and no copy asserts the effect. *Flip condition:* future registered replications settling null → card dissolves (remove the card entirely); precise confirmation with bounds → upgrade to a default suggestion at P3.

**Card C — The flow ideal vs. attention's natural ebbs.** *Claim A:* BU v6's aesthetics treat the sealed session as sacred focus time, implying continuous absorption. *Claim B:* mind-wandering occupies ~46.9% of waking hours (Killingsworth & Gilbert 2010), flow is rare for knowledge work (Mark 2023, T3, framing only), and BU has no instrument to detect flow anyway. *What is actually disputed:* whether "focus" is a state BU can expect or an environment BU can arrange. *Working resolution:* the seal promises *protection from costly interruption*, not continuous attention; drift is expected and priced honestly (§2.1); copy sells the door, not the trance (D6). *Flip condition:* N=1 data (OQ3/OQ5) showing sealed sessions produce no completion advantage for the builder → re-examine the seal's role rather than the copy.

---

## 5 · Open Questions & the N=1 Queue

- **OQ1 · Nudge habituation.** Does rung 1's soft nudge lose its effect over weeks (alert-fatigue class)? *N=1:* 4-week ABAB (soft-nudge on/off) during evening plays; metrics: drift events/week, drift→relight conversion. `[P2-data]`
- **OQ2 · Placement (the builder's brain-drain trial).** Phone in drawer vs. on desk during evening study plays, 2-week ABAB. Metrics: drift events per sealed session, completion. This is the personal experiment the `[CONTESTED]` literature cannot perform *for* him. `[P0-runnable manually; P1-quantified]`
- **OQ3 · Chronotype × completion.** 3×/day energy taps (P0, manual) for 4 weeks; correlate play completion with time-of-day; test the onboarding answer against actual tend timing. `[P0]`
- **OQ4 · Threshold tuning.** The 20/30/60 + grace priors face their P2 field test; success = drift→relight conversion up with session abandonment flat. `[P2]`
- **OQ5 · Friction inside a sealed context.** one sec's 36% dismissal ran on *app-opening impulses*; does the dismiss-option overlay reproduce comparable behavior against *drift inside a chosen session* (a different motivational state — the user already "chose" the play)? P2 A/B. `[P2]`
- **OQ6 · Observation reactivity (hand-off to Phase 6).** How much does being watched change drift rates? Compare Watcher-active vs. Watcher-off weeks at P1; the answer calibrates every drift-based pattern claim. `[P1]`

---

## 6 · Sources

**T1/T2 (primary evidence).**
- Monsell, S. (2003). Task switching. *Trends in Cognitive Sciences* 7(3), 134–140. doi:10.1016/j.tics.2003.09.012
- Rubinstein, J. S., Meyer, D. E., & Evans, J. E. (2001). Executive control of cognitive processes in task switching. *JEP: HPP* 27(4), 763–797. doi:10.1037/0096-1523.27.4.763
- Leroy, S. (2009). Why is it so hard to do my work? The challenge of attention residue when switching between work tasks. *OBHDP* 109(2), 168–181. doi:10.1016/j.obhdp.2009.04.003
- Gonzalez, V. M., & Mark, G. (2004). "Constant, constant, multi-tasking craziness": Managing multiple working spheres. *ACM CHI'04*, 113–120.
- Mark, G., Gonzalez, V. M., & Harris, J. (2005). No task left behind? Examining the nature of fragmented work. *ACM CHI'05*. doi:10.1145/1099203.1099329
- Mark, G., Gudith, J., & Klocke, U. (2008). The cost of interrupted work: More speed and stress. *ACM CHI'08*. doi:10.1145/1357054.1357207
- Iqbal, S. T., & Horvitz, E. (2007). Disruption and recovery of computing tasks: Field study, analysis, and directions. *ACM CHI'07*. doi:10.1145/1240624.1240730
- Stothart, C., Mitchum, A., & Yehnert, C. (2015). The attentional cost of receiving a cell phone notification. *JEP: HPP* 41(6). doi:10.1037/xhp0000100
- Ward, A. F., Duke, K., Gneezy, A., & Bos, M. W. (2017). Brain drain: The mere presence of one's own smartphone reduces available cognitive capacity. *JACR* 2(2), 140–154. doi:10.1086/691462
- Ruiz Pardo, A. C., & Minda, J. P. (2022). Reexamining the "brain drain" effect: A replication of Ward et al. (2017). *Acta Psychologica*. doi:10.1016/j.actpsy.2022.103717
- Böttger, T., Poschik, M., & Zierer, K. (2023). Does the brain drain effect really exist? A meta-analysis. *Behavioral Sciences* 13(9), 751. doi:10.3390/bs13090751 (PMC10525686)
- Ophir, E., Nass, C., & Wagner, A. D. (2009). Cognitive control in media multitaskers. *PNAS* 106(37), 15583–15587. doi:10.1073/pnas.0903620106
- Uncapher, M. R., & Wagner, A. D. (2018). Minds and brains of media multitaskers: Current findings and future directions. *PNAS* 115(40), 9889–9896. doi:10.1073/pnas.1720732115
- Killingsworth, M. A., & Gilbert, D. T. (2010). A wandering mind is an unhappy mind. *Science* 330(6006), 932–936. doi:10.1126/science.1192427
- Mrazek, M. D., Franklin, M. S., Phillips, D. T., Baird, B., & Schooler, J. W. (2013). Mindfulness training improves working memory capacity and GRE performance while reducing mind wandering. *Psychological Science* 24(5), 776–781. doi:10.1177/0956797612459659
- Smallwood, J., & Schooler, J. W. (2006). The restless mind. *Psychological Bulletin* 132(6), 946–958. doi:10.1037/0033-2909.132.6.946 (bridged from Phase 3)
- Brehm, J. W. (1966). *A theory of psychological reactance*. Academic Press.
- Dillard, J. P., & Shen, L. (2005). On the nature of reactance and its role in persuasive health communication. *Communication Monographs* 72(2), 144–168. doi:10.1080/03637750500203872
- Grüning, D. J., Riedel, F., & Lorenz-Spreen, P. (2023). Directing smartphone use through the self-nudge app one sec. *PNAS* 120(8), e2213114120. doi:10.1073/pnas.2213114120
- May, C. P., & Hasher, L. (1998). Synchrony effects in inhibitory control over thought and action. *JEP: HPP* 24(2), 363–379. doi:10.1037/0096-1523.24.2.363
- Roenneberg, T., Wirz-Justice, A., & Merrow, M. (2003). Life between clocks: Daily temporal patterns of human chronotypes. *J. Biological Rhythms* 18(4), 328–337. doi:10.1177/0748730403251984
- Roenneberg, T., Kuehnle, T., et al. (2004). A marker for the end of adolescence. *Current Biology* 14(24), R1038–R1039. doi:10.1016/j.cub.2004.11.039
- Wittmann, M., Dinich, J., Merrow, M., & Roenneberg, T. (2006). Social jetlag: Misalignment of biological and social time. *Chronobiology International* 23(1–2), 497–509.
- Galla, B. M., & Duckworth, A. L. (2015). More than resisting temptation: Beneficial habits mediate the relationship between self-control and positive life outcomes. *JPSP* 109(5), 769–781. doi:10.1037/a0038471 (PMC4731333)
- Duckworth, A. L., Gendler, T. S., & Gross, J. J. (2016). Situational strategies for self-control. *Perspectives on Psychological Science* 11(4), 565–571. doi:10.1177/1745691616620247
- Nakamura, J., & Csikszentmihalyi, M. (2009). Flow theory and research. In *Oxford Handbook of Positive Psychology*.

**T3 (researchers' own books — context, not sufficient for claims).**
- Csikszentmihalyi, M. (1990). *Flow: The Psychology of Optimal Experience*. Harper & Row.
- Mark, G. (2023). *Attention Span*. Hanover Square Press — the precision audit's summary-grade source; 47 s / ~25 min figures live here and in interviews, not in primary papers.
- Roenneberg, T. (2012). *Internal Time*. Harvard University Press.
- Newport, C. (2016). *Deep Work*. Grand Central — practitioner framing only.

**T4 (leads only, never evidence).**
- Mark's press interviews (the 23m15s figure's provenance); one sec app research page (vendor mirror of the PNAS study).

**v6-doc amendments logged this phase:** (1) the "~20+ minutes to refocus" claim restated precisely — pop figures are T4/T3 researcher summaries; BU copy bans fact-printing them; (2) May & Hasher (1998) journal corrected: *JEP: HPP* 24(2), 363–379, not *Psychology and Aging*; (3) mere-presence ("brain drain") claim class downgraded to `[CONTESTED]` with the replication record attached; (4) "educational exception" specified as user-declared tool lists, not title-based classification; (5) notification default corrected from ambient-ping product norms to Stothart-grounded boundary delivery.

**Research debt (page-level items not re-opened this pass, flagged rather than rounded):** Leroy 2009 per-study Ns; Ward 2017 exact Ns and effect sizes (Study 1 N = 548 printed as `[approx.]`); Stothart 2015 N; one sec RCT arm sizes; Ophir 2009 N; Dillard & Shen 2005 pooled effect sizes; Uncapher & Wagner 2018 review detail; Duckworth, Gendler & Gross 2016 page-level check. None is load-bearing at current decision weight; all queued for Phase 7's charter pass.

**Cross-references.** *Phase 1:* D11's sealed window is the drift stage (the grammar's anchor and the Watcher's window are the same variable — one data model); D12 Hard Focus inherits D2/D4; D17 consent inherits D6's disclosure; D18's trait-claim ban extends to drift and chronotype copy; D3's WOOP step receives D8's "what pulled you away?" answer. *Phase 2:* D1's single-task study card is the behavior D3 protects; D7's 75–90% band is the challenge-skill calibration flow conditions require. *Phase 3:* the 43% context world (§2.6) is what the Watcher actually watches; D8 stress-window mode caps the ladder at rung 1; D7's plasticity checklist "one thing at a time" is operationalized by the sealed session + drift definition. *Phase 5:* D1/D2 are consumed by the JITAI centerpiece; the "app goes dark" mechanic resolves here as friction, not punishment; streak/fire framing inherits D6's bans. *Phase 6:* OQ6 and D8's evidence-class assignment land in its measurement designs; observation reactivity calibrates all drift-based claims. *Phase 7:* the fire scoring function consumes D8's non-punishing drift chain; the Evidence Charter gains the notification invariant ("BU never interrupts focus") and the friction-before-force ladder invariant.

*End of Phase 4. Tracker updated in `RESEARCH-PLAN.md` Appendix C. Next: "Research Phase 5" produces `phase5-interventions.md` — the ladder as JITAI, the BCT audit of v6, streak evidence, engagement decay, and the ethics line.*
