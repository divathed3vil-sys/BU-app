# BU Deep Research Program — Master Plan

**Version** 1.0 · **Date** 2026-11-05 · **Prepared for** Dhivakaran · BU (Galaxy A54 build)
**Status** Locked plan. Phases run one at a time, on request.
**Drop-in location** `research/RESEARCH-PLAN.md` in the `bu-app/` repository, with a pointer added to `MEMORY/context.md` so any future LLM session finds it.
**Supersedes** the brief draft *"Architecting Human Potential: A Unified Framework for the BU Life Management System"* — replaced phase by phase. Its good intuitions survive; its sourcing habits do not. Every number in the new documents must come from a primary source that has actually been opened.
**Serves** BU v6 "The Fire" (vision doc) and the P0 Spark build.

---

## 0 · Locked Scope (2026-11-05 planning session)

| Dimension | Locked decision |
|---|---|
| Format | Markdown, repo-ready (`research/phaseN-<slug>.md`) |
| Per-phase output | ~10,000 words: ~60% science deep-dive, ~40% direct BU design implications |
| Technicality | Mechanisms **and** the math **and** the evidence, with replication-status flags on every load-bearing claim |
| Domains (beyond the ten founders) | Habit/drive science · focus/attention science · learning-tech algorithms (FSRS, SM-2, JITAI) · self-tracking/measurement science |
| Feynman | Technique only: true provenance, the 4 steps, wiring into BU's journal surfaces. No broader Feynman biography or philosophy |
| App scope | Full BU v6 vision; every actionable item tagged with the build phase (P0–P4) that can first use it |
| Phase order | 1 → 7 (order delegated; rationale in §4) |
| Cadence | Plan only, today. Each phase starts on request with the kickoff prompt in Appendix B |

**P0-first bias.** P0 Spark (Lite, no AI) is what gets built in the next 4 weeks. Wherever a design question can be answered for P0 cheaply, the phase document must state the P0 answer explicitly. P0 tags are the scarcest output of this program and the plan hunts for them first.

---

## 1 · Mission — What "Decision-Grade" Means Here

BU makes a set of implicit scientific claims: that a fire that dims on drift will discipline better than a streak that counts; that one sentence per day beats a task list; that watching produces honesty; that interventions at 20/30/60 minutes help rather than harm; that 30 verified days means something. Today these claims are metaphors. The mission of this program is to convert each one into one of three states: **(a) a citation chain to primary evidence, (b) a marked unknown, or (c) an N=1 experiment queued for the builder to run on himself.** By the end of Phase 7, every load-bearing mechanic in BU v6 must sit in one of those three buckets. That is the standard this plan enforces.

A finding is *decision-grade* when it can settle a design argument. "Spaced retrieval beats rereading" is decision-grade because it dictates what a study session inside BU should look like. "The brain is plastic" is not, because no design choice changes either way. Depth therefore means: mechanisms (why it works), math (how to compute it), evidence tables (how strongly we know it, with effect sizes), replication status (whether it survived contact with replication), and the BU decision it locks. Metaphor-level mapping like "the fire leverages the habit loop" is banned as a final statement — the phase documents must end at the level of thresholds, formats, copy rules, and scoring functions.

Non-goals: no academic padding; no feature brainstorming that evidence does not drive; no restating the v6 vision back to itself; no citation of blogs as evidence (blogs are leads, tracked at tier T4).

### 1.1 The three tests every claim must pass

1. **Source test.** Traceable to a named primary source (paper with DOI/PMCID, or a researcher's own book). No "studies show."
2. **Survival test.** Replication status assigned: has it been replicated, contested, failed, or is it single-study? Load-bearing claims carry the flag inline.
3. **Action test.** Ends in a tagged design decision (Decision / Evidence / Confidence / P0–P4 tag). A finding with no decision attached is either placed in Open Questions or cut.

### 1.2 Source tiers

| Tier | What | Role |
|---|---|---|
| T1 | Peer-reviewed primary papers (DOI/PMCID) | The only tier allowed as evidence for load-bearing claims |
| T2 | Meta-analyses and registered replications | Highest authority for "does X work"; preferred over single studies wherever both exist |
| T3 | Books by the researchers themselves (Dweck, Duckworth, Oettingen, Ericsson, Fogg, Merzenich…) | Context, nuance, mechanism narrative; not sufficient alone for a claim |
| T4 | Practitioner blogs, vendor research (Duolingo engineering posts), journalism | Leads and industry data points only; never cited as evidence |

The v0 draft's habits of citing content farms and a CDN-hosted PDF of the vision doc as evidence end here. Where the prior draft's claims are correct, they are re-grounded in T1/T2; where they are not, they are corrected in the phase document that owns the topic.

### 1.3 Replication flags

| Flag | Meaning | Example |
|---|---|---|
| `[REPLICATED]` | Survived meta-analysis or direct replication | Retrieval practice effect |
| `[CONTESTED]` | Real but smaller/narrower than popular version | Growth mindset; grit; marshmallow test |
| `[FAILED]` | Pre-registered replication failure or retraction-level doubt | Ego depletion (Hagger et al. RRR) |
| `[EMERGING]` | Single or few studies; promising, not load-bearing yet | BTSP; some brain-drain follow-ups |

### 1.4 The numbers rule

Every quantitative statement (effect size, day count, percentage, threshold) is written with its source and sample at research time: author, year, N, and the exact number as printed in the paper. Approximations are marked "approx." and verified before the phase document ships. The famous pop numbers get checked too — the "23 minutes to refocus" and "66 days to form a habit" figures both receive a precision audit in their owning phases, because BU sets real thresholds from them.

### 1.5 Contradiction cards

Where evidence genuinely conflicts (hard blocking vs. autonomy; quantification vs. enjoyment; morning defaults vs. chronotype), the owning phase writes a **Contradiction Card**: claim A + evidence, claim B + evidence, what is actually disputed, the working resolution for BU, and what would make us flip it. Cards roll up into Phase 7's resolution log.

---

## 2 · The Per-Phase Document Template

Every phase document (`research/phaseN-<slug>.md`) follows this skeleton, with word budgets that sum to ~10,000:

| # | Section | Budget | Content |
|---|---|---|---|
| 1 | TL;DR | ≤300 w | Decision-first summary; the 5 most important things this phase establishes |
| 2 | Science sections | ~6,500 w | One section per topic block. Each: mechanism → the math (if any) → key studies table (authors/year/finding/effect size/flag) → "What this means for BU" |
| 3 | Design Decisions | ~2,000 w | Numbered table: Decision / Evidence / Confidence / Tag (P0–P4). Minimum 5 per phase |
| 4 | Contradiction Cards | 0–500 w | Per §1.5, where relevant |
| 5 | Open Questions + N=1 queue | ~300 w | What the literature cannot settle; experiments the builder could run |
| 6 | Sources | ~400 w | T1/T2 list with DOI/PMCID links; T3/T4 marked as such |

Confidence vocabulary, used identically in every phase: **High** (T2 meta-analysis or multiple T1 replications) · **Medium** (consistent T1, no meta) · **Low** (single studies or [EMERGING]).

---

## 3 · The P0–P4 Tag System

| Tag | Build phase | What exists then |
|---|---|---|
| P0 | Spark (4 wk) | Onboarding 3 questions · manual fire (5 states, "MANUAL") · one-sentence Play · Evening Close · blank-page write journal |
| P1 | Ember (6 wk) | Model download moment · Watcher v1 (UsageStats + FGS) · Forge v1 (fire state computation, computed Play) · Life Log auto-capture |
| P2 | Flame (8 wk) | Intervention ladder (no smart context) · Hard Focus whitelist · voice journal · photo proof v1 · manual Life Log entries |
| P3 | Forge (12 wk) | Accessibility smart context (educational exception) · free-draw canvas · causal chains · prediction · Gorgeous state · growth milestones |
| P4 | Constellation | Multi-year procedural fire · desktop reader · backup · long-tail features |

Rule: a finding is tagged with the **earliest** build phase that can act on it. P0 is the priority target — the plan treats "this changes what ships in 4 weeks" as the highest-value outcome a phase can produce.

---

## 4 · Phase 1 — Self-Regulation & Discipline: The Science of the Fire

**Thesis.** The fire is BU's core mechanic, and its semantics are a set of psychological claims: that visible decay disciplines, that loss motivates more than gain, that a dead fire can be rebuilt without shame. This phase audits those claims against modern self-regulation science — including the parts that failed replication — and locks the fire's feedback rules, the Play's format, and the relight ritual. Runs first because P0 (manual fire, Play, onboarding, evening close) is entirely inside this phase's blast radius.

**Core questions.**

1. Is willpower a depletable resource? Strength model (Baumeister) vs. the failed pre-registered replication vs. process/motivation models — decides what BU may assume about "bad days" and how plays are sized.
2. What does the marshmallow test actually predict after the 2018 replication? Delay ability vs. SES confound — decides what BU may claim to train and what it must not.
3. Which of grit, growth mindset, and self-efficacy survive meta-analysis — and which *parts* are actionable in an app? (Perseverance facet; targeted mindset interventions; the four sources of self-efficacy.)
4. Does autonomy-supportive design outperform controlling design? SDT, and the crowding-out of intrinsic motivation by extrinsic rewards — decides fire copy: "the fire dims" vs. "you failed," and whether the fire may ever feel like a punisher.
5. What is the verified effect size of implementation intentions, and can the Play be phrased as one? — produces the Play grammar.
6. Do commitment devices help or backfire — especially hard commitments for optimally-biased users? — decides how much of P2's Hard Focus is science vs. vibe.
7. What happens psychologically after a lapse? What-the-hell effect, abstinence violation effect, self-forgiveness and self-compassion studies — decides the fire-death experience and the 3-day relight rule.
8. Does self-monitoring alone move behavior? The monitoring meta-analysis — the evidence base for Evening Close as a mechanism, not a ritual.

**Topic map & source targets** (each opened at research time; flags assigned there).

- *Willpower & depletion:* Baumeister et al. (1998, JPSP) original; Hagger et al. (2016, Perspectives on Psych Science) registered replication report `[FAILED]`; Inzlicht & Schmeichel (2012) process model; Inzlicht, Shenhav & Olivola (2018, TiCS) effort paradox; the glucose model's collapse.
- *Delay of gratification:* Mischel, Ebbesen & Zeiss (1972, JPSP); Shoda, Mischel & Peake (1990); Watts, Duncan & Quan (2018, Psych Science) `[CONTESTED]`.
- *Motivation:* Ryan & Deci (2000, American Psychologist) SDT; Deci, Koestner & Ryan (1999, Psych Bulletin) rewards meta `[REPLICATED, with boundary conditions]`.
- *Trait audit:* Duckworth et al. (2007, JPSP) grit; Credé, Tynan & Harms (2017, JPSP) grit critique; Sisk et al. (2018, Psych Science) mindset meta-analyses; Yeager et al. (2019, Nature) national experiment; Bandura (1977 Psych Review; 1997 book) self-efficacy.
- *Action tools:* Gollwitzer & Sheeran (2006, Adv. Exp. Soc. Psych.) implementation-intentions meta (d ≈ .65); Oettingen (2014) WOOP/mental contrasting; Ariely & Wertenbroch (2002, Psych Science) pre-committed deadlines; Giné, Karlan & Zinman (2010, AEJ: Applied) hard commitment field experiment.
- *Failure & recovery:* Marlatt & Gordon (1985) relapse prevention / abstinence violation effect; Herman & Polivy boundary model (what-the-hell effect); Breines & Chen (2012, PSPB) self-compassion increases self-improvement motivation; Wohl, Pychyl & Bennett (2010, PSPB) self-forgiveness reduces subsequent procrastination.
- *Monitoring:* Harkin et al. (2016, Psych Bulletin) progress-monitoring meta (d ≈ .40) — cross-referenced in Phase 5.

**Specs & math to deliver.**

- **Play grammar (P0):** implementation-intention template — `When [time/context anchor], I will [single verb-phrase action] for [duration] at [place/tool].` — with sizing rules and 10 worked examples replacing the v6 free-form examples.
- **Fire feedback rules table (P1):** event × evidence grade × allowed copy class (signal vs. shame), constrained by SDT + self-compassion findings.
- **Relight ritual spec (P0):** the 3-day relight rule examined against re-initiation and automaticity research; minimal viable ritual defined.
- **Evening Close v2 (P0):** question order and wording from self-monitoring evidence; the optional reflection line specified.

**Design deliverables (tagged).**

- D1 `[P0]` Play sentence grammar + examples (Lite keeps user-written plays; grammar makes them implementation intentions).
- D2 `[P0]` Evening Close question set & wording.
- D3 `[P0]` Onboarding 3-questions v2: goal-setting theory (specific + challenging) × SDT (autonomy framing) × one WOOP-style contrast step ("what pulls you off track" becomes obstacle+plan, not confession).
- D4 `[P1]` Fire state semantics: what brightens/dims it; evidence-grade weighting seeds (full function in Phase 7).
- D5 `[P0]` Fire-death & relight copy rules — no shame language; relight = choice + plan, per self-forgiveness evidence.
- D6 `[P2]` Low-energy play sizing rule grounded in process models (not the failed glucose model).

**Controversies to resolve.** Ego depletion (what BU assumes instead); hard commitments for biased users; whether grit adds anything beyond conscientiousness; morning-default bias (cross-ref Phase 4 chronotype).

**Expected tags.** D1, D2, D3, D5 land in P0 — deliberate: P0 must be evidence-based from day one, with zero AI.

---

## 5 · Phase 2 — Learning & Memory: The Science of the Play

**Thesis.** BU's Play and journal are learning interventions in disguise. This phase converts them into explicit ones: a study play is structured by the techniques that survive meta-analysis, the journal's surfaces are wired to the mechanisms that make "explain it simply" work, and the future Forge inherits a real memory model instead of vibes. Also the owning phase for the Feynman Technique (technique-only scope) and for the founders who live in pedagogy (Piaget, Vygotsky, Bruner, Montessori, Dewey).

**Core questions.**

1. Which study techniques survive the strongest utility review (Dunlosky et al. 2013)? Practice testing and distributed practice = high utility; rereading, highlighting, summarization = low — defines what a "study play" actually contains.
2. What is the mathematical model of forgetting, and should the Forge later adopt FSRS's three-component (Difficulty/Stability/Retrievability) model for scheduling plays and resurfacing embers? Includes SM-2 lineage.
3. What is the optimal spacing gap (Cepeda temporal ridgeline) — how revisit intervals get set as a function of retention horizon.
4. Interleaving vs. blocking (Rohrer): how multi-subject weeks get structured.
5. Desirable difficulties (Bjork): what error/success rate is optimal during retrieval, and how hard a Play should be — the ZPD calibration target.
6. Metacognitive illusions: fluency, biased judgments of learning — why BU grades evidence instead of trusting feelings; which journal prompts force calibration.
7. **Feynman Technique (technique-only):** true provenance (folk-attributed to Feynman, modern form popularized via Scott Young; Feynman's own accounts are secondary), the 4 steps, and the verified mechanisms underneath — generation effect, self-explanation (Chi et al. 1994), retrieval practice, simplification as elaboration. Then: wiring into write/voice/draw surfaces.
8. Guided vs. pure discovery (Alfieri et al. 2011 meta): resolves the "blank page vs. prompted journal" tension without violating the v6 aesthetic.
9. Deliberate practice — honest numbers (Ericsson et al. 1993 vs. Macnamara et al. 2014/2016: practice explains domain-dependent fractions of performance variance, far less than pop versions claim): what BU may and may not promise.
10. Spiral curriculum (Bruner): the ember-resurfacing engine spec — when old entries come back, at what complexity.
11. ZPD & scaffolding (Vygotsky; Wood, Bruner & Ross 1976): contingent support + fading rule — the scientific core of "interventions fade as competence grows."

**Topic map & source targets.**

- Dunlosky, Rawson, Marsh, Nathan & Willingham (2013, Psych Science in the Public Interest) — the utility table.
- Roediger & Karpicke (2006, Psych Science); Karpicke & Blunt (2011, Science) — retrieval practice `[REPLICATED]`.
- Cepeda, Pashler, Vul, Wixted & Rohrer (2006, Psych Bulletin) spacing meta; Cepeda et al. (2008, Psych Science) optimal-gap ridgeline.
- Rohrer & Taylor (2007, Instructional Science); Rohrer, Dedrick & Stershic (2015, JEP: Applied) — interleaving.
- Bjork (1994); Bjork & Bjork (2011) — desirable difficulties, storage vs. retrieval strength.
- Chi, de Leeuw, Chiu & LaVancher (1994, Cognitive Science) — self-explanation; Paivio (1986) — dual coding.
- Ericsson, Krampe & Tesch-Römer (1993, Psych Review); Macnamara, Hambrick & Oswald (2014, Psych Science) — deliberate practice debate.
- Chase & Simon (1973, Cognitive Psychology) chunking; Ericsson & Kintsch (1995) long-term working memory.
- Wood, Bruner & Ross (1976, JCPP) scaffolding; Vygotsky (1978, Mind in Society); Bruner (1960, The Process of Education); Alfieri et al. (2011, JEP) discovery meta.
- Dewey (1910, How We Think) reflective-thinking stages; Lillard (2017, Montessori: The Science Behind the Genius; T3) + Lillard & Else-Quest (2006, Science) Montessori outcomes; Piaget (1952, The Origins of Intelligence in Children) schema/adaptation.
- FSRS: open-source algorithm documentation + current release paper/preprint `[verify parameters at research time]`; Wozniak (1990) SM-2 description.
- Feynman provenance: Feynman's own writing (T3) for what he actually did; Young (2019, Ultralearning; T3) for the modern technique form.

**Specs & math to deliver.**

- FSRS equation summary with current default parameters, and the case for/against adopting it for ember resurfacing `[verify current release at research time]`.
- Forgetting-curve family comparison (exponential vs. power-law) with implications for scheduling.
- Play difficulty calibration: target success band from the desirable-difficulty literature, expressed as a sizing rule the Forge (P3) can compute.
- Resurfacing scheduler draft: spacing-driven rules for when old embers/journal entries resurface (spiral curriculum operationalized).

**Design deliverables (tagged).**

- D1 `[P0]` Study-play structure card: a retrieval-first session recipe shipped inside P0's journal/play hints.
- D2 `[P0]` Calibration micro-prompts for Evening Close ("what did you actually recall today?"-class questions).
- D3 `[P2]` Feynman-mode journal spec: 4-step guided flow across write/voice surfaces.
- D4 `[P3]` Ember resurfacing engine spec (Forge).
- D5 `[P3]` ZPD difficulty targeting for computed plays.
- D6 `[P4]` Spiral mapping for long-horizon knowledge arcs.

**Controversies to resolve.** Learning-styles myth (killed formally, once); rereading habit; Ericsson-vs-Macnamara framing; FSRS parameter churn.

---

## 6 · Phase 3 — Neuroscience: The Brain That Tends the Fire

**Thesis.** The v6 doc's honesty claims rest on brain science, and the v0 draft's neuroscience section is the right skeleton with the wrong sourcing (content farms, a magazine piece, a JWT-laden PDF link). This phase rebuilds it on primary literature, corrects the BTSP citation line, and turns mechanisms into four BU-relevant rules: how fast habits consolidate, when dopamine rewards should land, when scaffolding should fade, and what sleep debt does to discipline.

**Core questions.**

1. What does Hebbian plasticity actually mean post-1949 — LTP/LTD, NMDA dependence, STDP (tens-of-milliseconds window), BTSP (seconds-scale, single-experience)? Corrected primary citations; BTSP stays `[EMERGING]`.
2. What does dopamine compute? Schultz's reward-prediction-error work and Berridge's wanting-vs-liking — sets the fire's reward timing: immediate, small, variable feedback beats delayed, large, fixed.
3. How does control transfer from prefrontal cortex to striatum as habits form (Yin & Knowlton; Graybiel)? — defines when scaffolding should fade and what "the play is in the body" means operationally.
4. How long does habit formation take — the real distribution (Lally et al. 2010: median ~66 days, range 18–254, asymptotic automaticity curve)? — grounds the fire's multi-year arc and tells BU when to step back.
5. What fraction of daily behavior is already habitual and context-bound (Wood, Quinn & Kashy 2002, ~43%)? — BU competes with existing context-cued habits; design must use context, not fight it.
6. What are Merzenich's conditions for adult plasticity — attention, reward signaling, repetition, salience (Kilgard & Merzenich 1998; Jenkins et al. 1990)? — the "plasticity checklist" every Play must satisfy.
7. What does sleep do to learning and discipline — consolidation (Rasch & Born 2013), deprivation costs to executive function (Lim & Dinges 2010 meta)? — grounds the sleep proxy and low-energy plays; popular-book exaggerations (Walker controversies) are flagged and bypassed in favor of meta-analyses.
8. How does stress shift control toward habits (Schwabe & Wolf)? — exam-season design: in high-stress windows, routines protect performance more than plans.
9. What is exercise's plasticity lever (BDNF; Erickson et al. 2011 hippocampus RCT)? — optional nudge decision for P4.

**Topic map & source targets.**

- Hebb (1949, The Organization of Behavior); Bi & Poo (2001, Physiological Reviews) Hebb's postulate revisited; Markram et al. (1997, Science) coincidence detection; Bittner, Milstein, Chen & Magee (2017, Cell Reports) BTSP `[EMERGING]`.
- Schultz, Dayan & Montague (1997, Science) RPE; Berridge & Robinson wanting-vs-liking reviews.
- Yin & Knowlton (2006, Nat. Rev. Neurosci.); Graybiel (2008, Annu. Rev. Neurosci.).
- Lally, van Jaarsveld, Potts & Wardle (2010, EJSP); Wood, Quinn & Kashy (2002, JPSP); Wood & Rünger (2016, Annu. Rev. Psych.).
- Jenkins, Merzenich, Ochs, Allard & Guç-Robles (1990, J. Neurophysiol.); Kilgard & Merzenich (1998, Science); Tallal/Merzenich (1996, Science) Fast ForWord; Merzenich (2013, Soft-Wired; T3).
- Rasch & Born (2013, Physiological Reviews); Lim & Dinges (2010, Psych Bulletin) meta; Yoo et al. (2007, Current Biology) sleep loss & emotional reactivity.
- Schwabe & Wolf (2009) stress-habit shift `[verify exact journal at research time]`.
- Erickson et al. (2011, PNAS); Cotman & Berchtold (2002, Trends in Neurosciences).
- Posner & Petersen (1990, Annu. Rev. Neurosci.) attention networks; Smallwood & Schooler (2006, Psych Bulletin) mind-wandering — bridge to Phase 4.

**Specs & math to deliver.**

- Automaticity curve model: per-play automaticity estimate (asymptotic fit), driving the scaffolding-fade rule.
- RPE-style feedback schedule for fire brightening (what magnitude/timing of visual reward keeps the signal alive without hedonic adaptation).
- Sleep-window detection refinement: screen-off proxy vs. actigraphy literature; error bounds stated.

**Design deliverables (tagged).**

- D1 `[P1]` Fire reward-timing rules (immediacy, granularity, variability bounds).
- D2 `[P1]` Automaticity estimate → scaffolding-fade rule (interventions quiet as a play becomes automatic).
- D3 `[P0]` Sleep-aware Evening Close copy: Lite gets a manual sleep check-in that sizes tomorrow's play priors.
- D4 `[P3]` Forge sleep→next-day pattern claims: causal-humble grammar, cross-ref Phase 6.
- D5 `[P4]` Exercise-nudge decision (add or abstain).

**Controversies to resolve.** "21/66-day" pop myth vs. the real distribution; Why-We-Sleep-style claims vs. meta-analytic reality; BTSP's single-study status.

---

## 7 · Phase 4 — Attention, Focus & Drift: The Science of the Watcher

**Thesis.** The Watcher's thresholds — 20/30/60 minutes, soft nudge → aggressive nudge → overlay + block — are currently arbitrary. This phase grounds the drift definition, the true cost of interruption, when interventions work vs. provoke reactance, and the one assumption v6 never checked: that "before noon" is universally the right time to study. Output: a defensible drift operationalization, a v1.1 ladder decision table, and BU's own notification discipline.

**Core questions.**

1. What does an interruption actually cost? Attention residue (Leroy 2009), task-switching costs (Monsell 2003; Rubinstein, Meyer & Evans 2001), resumption lag in real work settings (Mark et al. 2005/2008 — the "~20+ minutes" figure gets a precision audit). These numbers set what a drift minute is worth.
2. Do notifications alone degrade performance — even without touching the phone (Stothart et al. 2015)? Decides BU's own notification rules and the Watcher's passivity during focus.
3. Does mere phone presence impair cognition (Ward et al. 2017 "brain drain")? Replication status resolved here — at least one later direct replication reported null results `[verify at research time]`. Decides whether Hard Focus guidance includes physical placement.
4. Is heavy media multitasking actually harmful (Ophir, Nass & Wagner 2009), or is the effect narrower than the pop version? 
5. What are flow's operating conditions (Csikszentmihalyi): clear goals, immediate feedback, challenge-skill balance? Maps the sealed focus session to those conditions; notes ESM measurement limits.
6. When do interventions provoke psychological reactance (Brehm 1966)? Blocking can backfire — copy and escalation rules must carry guardrails.
7. Does friction work? The one sec RCT (Grüning, Riedel & Lorenz-Spreen 2023, PNAS) — evidence that small frictions cut problem-app opens, relevant to P0/P2 soft interventions.
8. Chronotype and time-of-day: synchrony effects (May & Hasher) and chronotype science (Roenneberg) — young adults skew evening-peaked. "Before noon" may be the wrong default for the builder himself; defaults become per-user, not hardcoded.
9. Situation modification vs. willpower: high-trait self-control people succeed through habits and environment design, not resistance (Galla & Duckworth 2015; Duckworth, Gendler & Gross 2016) — Hard Focus whitelists are legitimate self-control, not cheating.

**Topic map & source targets.**

- Leroy (2009, OBHDP); Monsell (2003, Trends Cog Sci); Rubinstein, Meyer & Evans (2001, JEP:HIP); Mark, Gudith & Klocke (2008, CHI); Mark et al. (2005, CHI).
- Stothart, Mitchum & Yehnert (2015, JEP:HIP); Ward, Duke, Gneezy & Bos (2017, JACR) + replication check; Ophir, Nass & Wagner (2009, PNAS).
- Csikszentmihalyi (1990, Flow; T3) + Nakamura & Csikszentmihalyi (2009) flow handbook chapter; Mrazek et al. (2013, Psych Science) mindfulness-training effects — attention trainability decision.
- Brehm (1966) reactance; Grüning, Riedel & Lorenz-Spreen (2023, PNAS) one sec RCT.
- May & Hasher (1998, Psychology and Aging) synchrony effects; Roenneberg (2012, Internal Time; T3) chronotypes.
- Galla & Duckworth (2015, JPSP); Duckworth, Gendler & Gross (2016, Perspectives on Psych Science); Newport (2016, Deep Work; T3, practitioner framing only).
- Posner & Petersen (1990); Smallwood & Schooler (2006) — bridged from Phase 3.

**Specs & math to deliver.**

- Drift operational definition: app-category × duration × play-context table (what counts as drift, what never counts, grace periods).
- Ladder decision table v1.1: thresholds with rationale per rung, reactance guardrails, and an explicit statement of which thresholds are evidence-driven vs. set-and-tested.
- BU notification policy: never mid-focus; boundary delivery; the persistent-notification content rules (P1).
- Chronotype personalization: onboarding question (P0) + personalized play-window defaults (P1/P3).

**Design deliverables (tagged).**

- D1 `[P2]` Drift operational definition (Watcher consumes it in P1 in coarse form: app-open-during-sealed-play).
- D2 `[P2]` Ladder v1.1 decision table with guardrails.
- D3 `[P0]` Notification policy for BU itself (Lite: only evening close + explicit user-set reminders).
- D4 `[P2]` Hard Focus whitelist guidance (calls + N apps; placement guidance conditional on brain-drain verdict).
- D5 `[P0]` Chronotype question added to onboarding; morning default made adaptive.

**Controversies to resolve.** Brain-drain replication status; the 23-minute figure's precision; multitasking-harm generalization; whether "educational exception" (P3) can rely on title-only classification.

---

## 8 · Phase 5 — Behavior-Change Engineering: The Science of the Ladder

**Thesis.** BU's intervention ladder is a Just-in-Time Adaptive Intervention (JITAI) without knowing it, and the fire is a streak wearing a costume. Both inherit 15 years of mobile health behavior-change evidence — including why most behavior apps die within weeks. This phase formalizes the ladder in JITAI terms, audits BU v6 against the 93-technique taxonomy, extracts what actually sustains engagement, and draws the ethical line BU will not cross.

**Core questions.**

1. Do behavior-change apps work? Systematic reviews and their effect sizes; engagement decay curves; Eysenbach's law of attrition. Decides BU's abandonment countermeasures — the app that goes dark must first survive long enough to matter.
2. What is the JITAI frame (Nahum-Shani et al. 2018): decision points, tailoring variables, decision rules? The ladder gets rewritten as an explicit JITAI decision table.
3. What does the COM-B model + Behaviour Change Wheel + BCTTv1 (93 techniques, Michie et al.) say BU v6 already has and lacks? A technique-by-technique audit of the v6 feature set, by build phase.
4. What is known about streaks? Loss aversion (Kahneman & Tversky 1979), streak mechanics in practice (Duolingo's published streak research — T4 vendor data, treated as leads), streak freezes, and the difference between numeric streaks and BU's identity framing ("You've shown up 4 of last 7 days. That's who you are now.").
5. What does gamification evidence say — what motivates, what crowds out (Sailer et al. 2017; Hamari et al. 2014; Deci, Koestner & Ryan 1999)? The fire must reward, not bribe; badges/points verdicts here.
6. Goal-setting theory (Locke & Latham 2002), goal gradients (Kivetz et al. 2006), fresh-start effect (Dai, Milkman & Riis 2014) — onboarding moments and play difficulty decisions.
7. Temptation bundling (Milkman, Minson & Volpp 2014) — optional pairing mechanic, P4 decision.
8. Minimal social accountability — does committing to even one person help? Optional P4 feature decision for a privacy-first app.
9. FBM (B = MAP) and Tiny Habits (Fogg): what to adopt (anchor/prompt design) and what lacks evidence (FBM is a framework, not a tested model).
10. Ethics: dark-pattern list BU will never use; autonomy-preserving persuasive design grounded in SDT; the app-withholds-value mechanic gets an explicit ethical defense or amendment.

**Topic map & source targets.**

- Fogg (2009, "A behavior model for persuasive design"); Fogg (2021, Tiny Habits; T3).
- Michie, van Stralen & West (2011, Implementation Science) Behaviour Change Wheel; Michie et al. (2013, Annals of Behavioral Medicine) BCTTv1.
- Nahum-Shani et al. (2018, Annals of Behavioral Medicine) JITAI framework.
- Eysenbach (2005, JMIR) law of attrition; recent systematic reviews of behavior-change app effectiveness (2019–2025) `[locate best available at research time]`.
- Harkin et al. (2016, Psych Bulletin) monitoring meta (d ≈ .40); Locke & Latham (2002, American Psychologist); Kivetz, Urminsky & Zheng (2006, JMR); Dai, Milkman & Riis (2014, Management Science); Milkman, Minson & Volpp (2014, Management Science).
- Kahneman & Tversky (1979, Econometrica); Hamari, Koivisto & Sarsa (2014, HICSS); Sailer, Hense, Mayr & Mandl (2017, Computers in Human Behavior); Deci, Koestner & Ryan (1999, Psych Bulletin).
- Marlatt & Gordon (1985) — relapse prevention (bridged from Phase 1).
- Duolingo streak publications (T4; leads only) + any peer-reviewed streak studies located.
- SDT-informed app design literature `[locate at research time]`.

**Specs & math to deliver.**

- **Ladder-as-JITAI table (the phase's centerpiece):** decision points (foreground events during sealed play) × tailoring variables (drift app category, minutes elapsed, play subject, user history) × decision rules (rungs) × rationale/evidence per cell — the direct implementation spec for P2.
- Engagement-decay countermeasure plan mapped to attrition findings (what BU does at day 3, day 14, day 30 — the known danger zones).
- Streak/fire framing guide: allowed and banned phrasings, from loss-aversion + identity evidence.
- BCT audit table: v6 feature ↔ BCT code(s) ↔ phase ↔ verdict.

**Design deliverables (tagged).**

- D1 `[P2]` Ladder-as-JITAI full decision table.
- D2 `[P0]` Notification copy guide (Lite).
- D3 `[P0]` Engagement-decay countermeasure plan (P0 onboarding & rhythm already need it).
- D4 `[P3]` Streak/fire framing rules for Forge speech.
- D5 `[P4]` Temptation-bundling and social-commitment feature decisions (add or abstain).
- D6 `[all]` BCT audit of BU v6 with gaps flagged per phase.

**Controversies to resolve.** Gamification crowding-out vs. engagement; blocking vs. reactance (cross-ref Phase 4); vendor streak data quality; whether the "app goes dark" mechanic survives the ethics review.

---

## 9 · Phase 6 — Measurement & Self-Knowledge: The Science of the Life Log

**Thesis.** BU claims research-grade provenance in a consumer app: four evidence grades on every entry, predictions with percentages, causal-sounding Forge sentences. This phase makes those claims true or makes them honest. It quantifies the self-report gap, defines grade criteria precisely, sets the rules for what BU may predict and how it must speak, and builds the N=1 methodology the builder will use on himself throughout the year.

**Core questions.**

1. How big is the self-report vs. logged-behavior gap (Parry et al. 2021, Nature Human Behaviour meta: weak-to-moderate correspondence)? Justifies the Watcher; and defines exactly when USER_CONFIRMED may count — self-report is a different instrument, not a weak version of observation.
2. Why do self-trackers lapse? Personal informatics stage model (Li, Dey & Forlizzi 2010) and lived-informatics studies (Epstein et al.) — the Life Log gets a friction budget and its manual-entry design.
3. When does quantifying help vs. hurt (Etkin 2016: measurement increases doing but decreases enjoyment)? Produces BU's "never quantify" list — journal joy, relationships, rest stay unmeasured.
4. What memory biases operate at Evening Close recall (peak-end rule — Redelmeier & Kahneman 1996; Day Reconstruction Method — Kahneman et al. 2004)? Validating that same-evening recall is the right instrument horizon.
5. How can BU predict honestly? Calibrated uncertainty via conformal prediction (Angelopoulos & Bates 2021) over simple sequence baselines (app-usage Markov chains) — "34% chance you do this tomorrow" must ship with honest intervals or not at all.
6. Correlation vs. causation in Forge speech: single-case experimental designs (Kratochwill et al. standards), interrupted time series, and a enforced causal grammar ("associated with" vs. "caused") — Sunday Review claims get rules.
7. What do data-quality/provenance standards imply for the four evidence grades (OBSERVED / INFERRED / USER_CONFIRMED / OUTCOME_CONFIRMED)? Operational criteria per grade: what each requires, what downgrades it.
8. Privacy and consent: privacy calculus (Acquisti, Brandimarte & Loewenstein 2015, Science), consent-fatigue findings, on-device trust signals — the "Light the fire" consent moment gets designed as an informed moment, not a dark pattern.
9. Digital phenotyping practice (Torous/Onnela line of work) — what passive phone data can and cannot indicate; keeps Watcher claims humble.

**Topic map & source targets.**

- Li, Dey & Forlizzi (2010, CHI); Epstein et al. lived-informatics/lapsed-tracker papers (CHI 2015–2017) `[locate exact set at research time]`.
- Parry et al. (2021, Nature Human Behaviour); Etkin (2016, JMR); Redelmeier & Kahneman (1996); Kahneman, Krueger, Schkade, Schwarz & Stone (2004, Science) DRM.
- Angelopoulos & Bates (2021, arXiv) conformal prediction gentle introduction; app-usage prediction literature (sequence/Markov baselines) `[locate at research time]`.
- Kratochwill et al. (2010/2013) single-case design standards; interrupted-time-series methodology references.
- Acquisti, Brandimarte & Loewenstein (2015, Science); Torous/Onnela digital phenotyping reviews `[locate at research time]`.
- Wood, Quinn & Kashy (2002) — bridged from Phase 3 for context-cue logging.

**Specs & math to deliver.**

- Evidence-grade operational criteria: per-grade definition, acquisition rule, downgrade triggers, and how grades propagate into fire weighting.
- Life Log schema v1: fields, grade field, gap-handling rules for manual entries (the "what were you doing 2pm–5pm?" flow).
- Prediction output spec: model class (baselines first), conformal interval wrapper, confidence-language table (which percentages may be shown, with which wording).
- Forge causal grammar: enforced vocabulary rules for pattern claims, with example sentences that pass and fail.
- N=1 experiment playbook: single-case designs (ABAB, interrupted time series) sized for one user, with 5 seeded experiments for the builder.

**Design deliverables (tagged).**

- D1 `[P1]` Evidence-grade operational criteria (Watcher + Life Log land in P1).
- D2 `[P1]` Life Log schema v1.
- D3 `[P3]` Prediction output spec (Forge).
- D4 `[P3]` Forge causal grammar enforcement.
- D5 `[P0]` N=1 playbook for the builder (usable during the P0 build itself).
- D6 `[P1]` Consent-flow copy rules for the model-download/Watcher moment.

**Controversies to resolve.** How far passive data may be interpreted; whether INFERRED entries earn any fire weight at all; quantification backlash boundaries.

---

## 10 · Phase 7 — Synthesis: BU Design Specification & the Evidence Charter

**Thesis.** Consolidation. Every decision from Phases 1–6 gets a final home, contradictions get resolved explicitly, and the program's output becomes two artifacts the build actually consumes: the **BU Design Specification v1** and the **BU Evidence Charter**.

**Contents.**

- **BU Design Specification v1.** The consolidated decision log: fire semantics, Play grammar + chooser rules, ladder JITAI table, journal surface specs (write/voice/draw), Life Log schema, onboarding script v2, notification policy, relight ritual, Gorgeous-gate definition (what "no cheating the app" means, operationally, per evidence grade).
- **Fire-state scoring function, defended draft.** Seed to be justified, parameterized, or replaced from Phase 1/3/5 evidence — not pre-approved:

```
fire(t) = Σ over tendings e in last 7 days of  w(grade_e) · decay(d_e)
  w: OUTCOME_CONFIRMED 1.0 · OBSERVED 0.9 · USER_CONFIRMED 0.5 · INFERRED 0.3
  decay(d) = exp(−d / τ),  τ ≈ 3 days   (τ resolved in Phase 7)
state = threshold(fire) → OUT < EMBERS < FLICKERING < BURNING < BLAZING
  (+ Gorgeous gate: 30-day verified-tending streak, per evidence-grade rules)
```

- **Contradiction resolution log.** Every Contradiction Card from Phases 1–6, resolved with rationale (expected cards: hard blocking vs. autonomy; quantification vs. enjoyment; morning default vs. chronotype; blank page vs. guided prompts; app-goes-dark vs. attrition risk).
- **The BU Evidence Charter.** ~10 invariants the app must never violate, each with its citation chain — e.g., no shame-based feedback (self-compassion evidence); calibrated claims only (conformal prediction); scaffolding fades (automaticity research); autonomy support (SDT); user owns embers (personal informatics); no dark patterns (ethics audit).
- **N=1 experiment queue.** Prioritized personal experiments with single-case designs, drawn from all phases' Open Questions.
- **Research debt list.** Questions the literature cannot settle — earmarked as BU's own future experiments or accepted unknowns, stated in public.
- **P0 punch list.** The bridge to the build: every P0-tagged decision across all phases, sequenced into the P0 Spark 4-week plan, so research and build hand off cleanly.

---

## Appendix A · Founder Coverage Matrix

Where the ten pioneers (and Feynman) land, what BU takes from each, and the honest status check each receives in their owning phase.

| Figure | Phase | Load-bearing contribution to BU | Status check at research time |
|---|---|---|---|
| Jean Piaget | 2 | Assimilation/accommodation as the schema-update model — how a Play should stretch existing mental structures | Stage model contested; adaptation mechanism retained as core |
| Lev Vygotsky | 2 | ZPD → Play difficulty calibration; scaffolding → intervention fading rule | Foundational; the operationalization (computing a zone from behavior) is the research task |
| Jerome Bruner | 2 | Spiral curriculum → ember resurfacing; co-originator of scaffolding | Guided (not pure) discovery survives meta-analysis; spiral revisiting maps to spacing |
| Maria Montessori | 2 | Prepared environment → digital environment design (order, choice, beauty, self-correcting feedback) | Mixed-but-positive controlled evidence (Lillard); principles adopted, mystique discarded |
| John Dewey | 2, 7 | Reflective-thinking stages → Evening Close + Sunday Review structure | Philosophy (T3), bridged to empirical reflection literature |
| Carol Dweck | 1 | Growth-mindset framing of fire and Forge copy | Small average effects; targeted interventions work for struggling students (Sisk 2018; Yeager 2019) |
| Albert Bandura | 1 | Self-efficacy via mastery experiences — the daily Play as an evidence engine for "I am someone who executes" | Robust; four sources map cleanly to BU surfaces |
| Angela Duckworth | 1 | Perseverance-of-effort facet → identity-streak framing | Grit ≈ conscientiousness (Credé 2017); the perseverance facet is the usable part |
| Donald Hebb | 3 | Co-activation → consolidation rationale for cue-routine-reward structure | Core; refined by STDP/BTSP with corrected citations |
| Michael Merzenich | 3 | Adult plasticity conditions (attention, reward, repetition, salience) → the Play design checklist | Core lab evidence; commercial claims (T4) excluded |
| Richard Feynman | 2 | Technique only: 4-step explain-simply loop wired into journal surfaces | Mechanism credit honestly reassigned to generation/self-explanation/retrieval literature; provenance traced |

---

## Appendix B · Execution Protocol

**Per-phase run (repeat for each phase).**

1. **Kickoff.** User sends the kickoff prompt (below). Session reads `research/RESEARCH-PLAN.md` first, then the relevant phase section.
2. **Research pass.** Web-search and open the T1/T2 targets in that phase's topic map. Every load-bearing number gets read from its source, not quoted from a summary. Flags `[REPLICATED]/[CONTESTED]/[FAILED]/[EMERGING]` assigned during this pass.
3. **Draft.** Write `research/phaseN-<slug>.md` per the §2 template; hold the 60/40 split and the ~10k-word budget.
4. **Self-check.** (a) every load-bearing claim flagged and sourced; (b) every number carries author/year; (c) ≥5 design decisions with tags; (d) no T4-as-evidence; (e) Contradiction Cards written where evidence conflicts; (f) P0 answers stated wherever they exist.
5. **Deliver.** Save the phase document; update the Appendix C tracker; append locked decisions to `MEMORY/decisions.md`; note surprises in `MEMORY/lessons-learned.md`.
6. **Review.** User reviews, then kicks off the next phase.

**Kickoff prompt template.**

> Research Phase N of `research/RESEARCH-PLAN.md` (v1.0). Produce `research/phaseN-<slug>.md` per the template in §2 and the scope in §0. Open the primary sources in that phase's topic map; flag every load-bearing claim; finish with the Design Decisions table (≥5 decisions, P0–P4 tags) and update the Appendix C tracker.

**Phase slugs.** `phase1-discipline` · `phase2-learning` · `phase3-neuroscience` · `phase4-attention` · `phase5-interventions` · `phase6-measurement` · `phase7-synthesis`.

---

## Appendix C · Status Tracker

| Phase | Document | Status | Date | Key decisions locked |
|---|---|---|---|---|
| 1 · Discipline | `research/phase1-discipline.md` | **done** | 2026-11-05 | D1 Play grammar · D2 Evening Close v2 · D3 Onboarding v2 · D5 Relight ritual · D7 copy ban-list · D11 sealed play · D18 no-trait-claims (+11 more, §3 of the phase doc; 3 v6-doc amendments logged) |
| 2 · Learning | `research/phase2-learning.md` | **done** | 2026-11-05 | D1 study-play card · D2 Evening Close recall micro-prompt · D3 copy rules (styles/hours/mastered bans) · D4 blank-page+edge-guidance · D5 Feynman-mode journal · D6 FSRS-class ember engine (pinned params; SM-2 floor for P0/P1) · D7 ZPD 75–90% success band · D8 interleaved weeks · D9 spiral mapping · D10 scaffolding-fade rule · D11 rereading warning · D12 prepared-environment layout · D13 resurfacing cap (+4 v6-doc amendments logged) |
| 3 · Neuroscience | `research/phase3-neuroscience.md` | **done** | 2026-11-05 | D1 fire reward-timing rules (RPE-scaled, capped, ≤2 s) · D2 automaticity estimator + scaffolding-fade rule (joins Phase 2 D10) · D3 sleep-aware Evening Close (manual line → play-size prior) · D4 Forge causal-humble sleep grammar · D5 exercise nudge: abstain · D6 context-stack rule · D7 plasticity checklist on study plays · D8 stress-window mode (P0 seed, P2 mode) · D9 copy ban-list extension (pop-neuro/21-days/brain-training bans) · D10 omission = information + smallest relight step. BTSP citation line corrected (primary = Science 357, 1033–1036). 6 v6-doc amendments logged |
| 4 · Attention | `research/phase4-attention.md` | **done** | 2026-11-05 | D1 drift operational definition (matrix + accumulator; P1 coarse form) · D2 ladder v1.1 (silence→nudge→friction→block; Hard-Focus-only block; 20/30/60 declared set-and-tested; stress-window interlock) · D3 BU notification policy (P0: two classes only, boundary delivery) · D4 Hard Focus whitelist (calls + ≤5; placement = N=1 experiment card) · D5 chronotype onboarding question + adaptive windows (morning default killed) · D6 Watcher copy rules + ban-list extension ("23 minutes"/brain-drain/flow/attention-training bans) · D7 educational exception = user-declared tool lists (title-only banned) · D8 drift = OBSERVED evidence, never fire-verdicts. "23-min" figure precision-audited (T4/T3); brain drain → `[CONTESTED]`; May & Hasher journal corrected (JEP: HPP, not Psychology and Aging). 5 v6-doc amendments logged |
| 5 · Interventions | `research/phase5-interventions.md` | **done** | 2026-11-05 | D1 ladder-as-JITAI decision table v1 (centerpiece; consumes Phase 4 D2) · D2 notification/Evening-Close copy guide (two classes, fire-once, real landmarks only) · D3 engagement-decay countermeasure plan (day 3/14/30; zero re-engagement machinery; survival metrics P1) · D4 streak/fire framing rules (identity grammar kept; numeric streaks/freezes/countdowns/loss-urgency banned) · D5 temptation bundling + social commitment: abstain (bundling via Play grammar; Gollwitzer announcement risk logged) · D6 BCT audit of v6 (high-utility techniques present; points/badges/leaderboards = permanent abstentions) · D7 goes-dark ethics amendment (coaching layer dims; user's record never withheld; dark-pattern ban-list extension). Attrition numbers pinned (Eysenbach; Baumel 4.0%/3.3%; Meyerowitz-Katz 43%); Linardon g=0.28; temptation bundling 51/29/61. 5 v6-doc amendments logged; Bryan 2011 citation corrected (PNAS, not Psych Science) |
| 6 · Measurement | `research/phase6-measurement.md` | **done** | 2026-11-05 | D1 evidence-grade operational criteria (assignment by acquisition channel; no silent upgrades; INFERRED never fires a state transition alone; Gorgeous gate = verified tendings only; grades survive export) · D2 Life Log schema v1 (grade as column; GAP as first-class entry type; ≤30 s/day friction budget; gap-flow once/day inside Evening Close; no completeness metric) · D3 prediction output spec (bigram + conformal intervals, 28-day rolling calibration, abstention rules; percentages only inside intervals; confidence-language table at P1) · D4 Forge causal grammar (3 claim classes with evidence bars; "BU doesn't know why" required companion; v6 sentences pass/fail table) · D5 N=1 playbook (ABAB/alternating-treatments/changing-criterion/declared-ITS cards + 5 seeded experiments E1–E5) · D6 "Light the fire" consent-flow rules (five facts, one screen, no dark patterns; P0 storage architecture rule) · D7 Watcher interpretation caps + never-quantify list (Etkin-grounded). Etkin journal corrected (JCR, not JMR); Parry DOI corrected (10.1038/s41562-021-01117-5); "5 grades (SCHEDULED→OUTCOME_CONFIRMED)" v6 inconsistency amended (4 grades; SCHEDULED = entry status). 2 v6-doc amendments logged |
| 7 · Synthesis | `research/phase7-synthesis.md` | **done** | 2026-11-05 | D1 fire scoring function v1 locked (two ledgers; w=1.0/0.9/0.5/0.3; τ=3 d pinned seed, E5 owns final; thresholds 2.0/1.0/0.4/0.1 declared; partial=0.5×; size never discounts; omission dims emergent; BLAZING verification-only) · D2 P0 state-meaning table frozen (v6 verbatim + MANUAL tag) · D3 build gate (Charter clause + BCT + evidence per mechanic; master ban-list ships as lint file) · D4 N=1 master queue (12 prioritized; E1–E5 skeleton; instrumentation in P0 schema) · D5 Evidence Charter v1 ratified (C1–C12 with citation chains + operational tests) · D6 Sunday Review v1 (Dewey structure; P1 frequency-only, P3 grammar-bound patterns) · D7 research debt stated in public · D8 P0→P1 handoff spec (grade/kind columns day one; MANUAL retires with designed relight moment). Consolidation audit: 15 mechanics bucketed (11 chains / 3 hybrids / 1 bet); 21 contradiction cards resolved (18 confirmed, 2 merged, 1 superseded) + 2 new; BCT badge code corrected to 10.4 *Social reward*; Wixted & Ebbesen 1991 corrected to *Psych Science* 2:409–415; Murre & Dros 2015 resolved P2's debt item. 6 v6-doc amendments logged |

