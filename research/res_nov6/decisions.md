# MEMORY/decisions.md

> Repo-mirror note: this file mirrors `MEMORY/decisions.md` in the `bu-app/` repository.
> It accumulates one compact block per delivered phase. Full rationale lives in the phase documents; this file is the load-bearing summary any future session reads first.
> Phases 1–2: decision rationale lives in `research/phase1-discipline.md` §3 and `research/phase2-learning.md` §3 (18 and 13 decisions respectively); tracker summaries in `research/RESEARCH-PLAN.md` Appendix C.

---

## Phase 3 — Neuroscience (2026-11-05, `phase3-neuroscience.md`)

| # | Decision (compact) | Tag |
|---|---|---|
| D1 | Fire reward-timing: RPE-scaled brightness at the confirming act (≤2 s), clipped band, expected outcomes get acknowledgment not celebration, no fabricated variance, no badge economy | P1 |
| D2 | Automaticity estimator `a(t) = A(1 − e^(−t/τ))` with forgetting factor → hint density fades across bands (<30% / 30–70% / >70% of plateau); Evening Close never fades; estimator never a grindable % | P1 |
| D3 | Sleep-aware Evening Close: optional hours-slept + rested-tap line; tomorrow's play prior ×1.0 / ×0.75 / ×0.5 at ≥7h / 5–7h / <5h; suggest-only | P0 |
| D4 | Forge sleep→pattern claims: causal-humble observational grammar over the user's own counts, N-stamped; screen-off proxy = trend only, ±45-min-class error bounds | P3 |
| D5 | Exercise: abstain from nudging/rewards/streaks, ever; P4 passive declared log → recovery copy only | P4 |
| D6 | Context-stack rule: user-chosen anchor per play, positionally stable daily surface, anchor changes logged as events, re-anchoring invitation (never push) on declared life changes | P0/P1 |
| D7 | Plasticity checklist on study plays: chosen by you · one thing at a time · closed with an answer · repeated here tomorrow (static copy P0, enforced flow P2, programmatic gate on Forge proposals P3) | P0/P2/P3 |
| D8 | Stress-window mode: P0 optional pressure line; P2 declared windows hold anchors, floor-sized plays, novelty suspended, feedback muted | P0 seed / P2 |
| D9 | Copy ban-list extension: no "neurons that fire together," no neuroplasticity/rewiring/brain-training claims, no "21 days," no countdown rings / "habit complete," no dopamine-talk, no sleep-scolding, no stress verdicts | P0 |
| D10 | Omission (missed close) dims by the mirror delta and offers the one-line relight play in the same screen; chronic-omission weeks → smaller plays, never louder prompts | P1 |

**v6-doc amendments logged this phase:** (1) v0 draft's neuroscience sourcing replaced wholesale; (2) BTSP primary citation corrected (Bittner, Milstein, Grienberger, Romani & Magee, 2017, *Science* 357, 1033–1036 — not Cell Reports); (3) "21 days"-class framing replaced by distribution language; (4) sleep claims scoped to meta-analyses; (5) "43%" claim restated in precise form; (6) "the play is in the body" operationalized as cue-initiated + outcome-insensitive + bracketed.

---

## Phase 4 — Attention (2026-11-05, `phase4-attention.md`)

| # | Decision (compact) | Tag |
|---|---|---|
| D1 | Drift operational definition v1: app-class × duration × play-context matrix; never-counts (calls, OTP, declared tool, BU, system UI); 60 s grace = "touch"; P1 coarse form = any non-whitelisted app-open during sealed play; accumulator math + rung state machine | P2 (P1 coarse) |
| D2 | Ladder v1.1: silence → 1 soft nudge (≥20 min or ≥2 min sustained) → friction overlay (≥30 min; one sec-style with dismiss option; 30-min cool-down after 3 dismissals) → block (Hard-Focus-declared only, active from seal, override asked+logged; 2 override-abandoned sessions → Hard Focus pauses itself). 20/30/60 declared set-and-tested; stress windows cap at rung 1 (Phase 3 D8 interlock) | P2 |
| D3 | BU notification policy: P0 = two classes only (Evening Close at user-set time + user-created reminders); boundary delivery; never mid-seal; queue during seals; P1 persistent-notification content rules (no streak pressure/urgency) | P0 |
| D4 | Hard Focus whitelist: calls + ≤5 user-declared apps default-none; placement guidance = 2-week N=1 experiment card, never a claim (brain drain `[CONTESTED]`); rationale line: situation modification is legitimate self-control | P2 |
| D5 | Chronotype onboarding question ("when does your head work best?" + optional wake anchor) joins Phase 1 D3; answer sets suggested play-window default only; windows adaptive at P1 (propose-and-confirm); no chronotype labels; 21:00 close fixed in P0, adjustable P1 | P0 |
| D6 | Watcher copy rules: drift lines report, never diagnose ("12 min in Social", no percentages, no fire pricing); ban-list extension: "23 minutes", goldfish, brain-drain assertions, multitasking-ruins-brain, addiction diagnostics, flow promises, attention-training promises, chronotype labels; observation reactivity disclosed at consent | P1 (P0 seed) |
| D7 | Educational exception (P3): exempt only user-declared per-play tool apps (+ one-tap retro-tag); title-only auto-classification banned; unlabeled educational-looking apps count as drift | P3 |
| D8 | Drift events = OBSERVED evidence, never fire-verdicts: drift dims nothing directly (fire weight rides Phase 7 scoring over tendings); Evening Close adds optional "what pulled you away?" line → WOOP obstacle+plan (Phase 1 D3) | P0 (question) / P1 (evidence class) |

**v6-doc amendments logged this phase:** (1) "~20+ minutes to refocus" restated precisely — pop figures are T4/T3 researcher summaries, fact-printing banned; (2) May & Hasher (1998) journal corrected: *JEP: HPP* 24(2), 363–379, not *Psychology and Aging*; (3) mere-presence claim class downgraded to `[CONTESTED]` (Ruiz Pardo & Minda 2022 failed replication; Böttger et al. 2023 meta, 22 studies/43 effects, high heterogeneity); (4) "educational exception" specified as user-declared tool lists; (5) notification default corrected to Stothart-grounded boundary delivery.

---

## Phase 5 — Interventions (2026-11-05, `phase5-interventions.md`)

| # | Decision (compact) | Tag |
|---|---|---|
| D1 | Ladder-as-JITAI decision table v1: every rung/scheduled touch = decision point × tailoring variables × rule × option × proximal outcome × copy class; receptivity constraints formal (never-counts, stress-window cap, cool-down); thresholds remain Phase 4's declared set-and-tested priors | P2 (P0/P1 rows land earlier) |
| D2 | Notification & Evening Close copy guide: two classes only, fire-once, boundary delivery; relight line evidence-not-urgency; real temporal landmarks only; FBM = internal vocabulary (facilitator/spark/signal), never user-facing | P0 |
| D3 | Engagement-decay countermeasure plan: ritual-not-retention; day 3/14/30 danger zones mapped (what BU does / never does / measures); zero re-engagement machinery ever; survival metrics (close-completion, relight conversion, days-between-opens) at P1 | P0 / P1 metrics |
| D4 | Streak/fire framing rules: identity-statement grammar kept under evidence-grade truth rule; banned: numeric streaks, freeze tokens, countdown rings, milestone-bribery, earned/unlocked copy, percentiles, "don't break the chain", loss-urgency; loss aversion structural only | P3 / P0 seed |
| D5 | Temptation bundling & social commitment: both abstain as features; bundling lives in the Play grammar's `at [place/tool]`; no reward-gating ever; social abstains through P4 (Gollwitzer announcement risk + privacy-first) | P0 grammar / P4 verdict |
| D6 | BCT audit of v6 (feature ↔ BCT code ↔ phase ↔ verdict): high-utility techniques present; points/badges/leaderboards/comparison/win-back = permanent abstentions; every future mechanic must name its BCT + evidence + pattern-family clearance | all |
| D7 | Goes-dark ethics amendment: coaching layer dims, user's own record (journal/Life Log/embers/exports) never withheld, return = one tap + one play; dark-pattern ban-list extended (urgency timers, confirmshaming, win-back pushes, fake landmarks, slot-machine schedules with RPE carve-out) | P0 |

**v6-doc amendments logged this phase:** (1) §7 ladder replaced by Phase 4 D2 ladder v1.1 (soft/aggressive-nudge names retired; block = Hard-Focus-only); (2) §7 drift copy "The fire is dimming" retired — drift lines report, never price the fire (Phase 4 D6); (3) "withholds its own value" amended per D7 — BU's computed coaching value dims, the user's own record never; (4) Gorgeous gate display-less (extends Phase 3 D9's countdown ban); (5) identity-streak line ratified with D4 rules. **Citation correction:** Bryan et al. (2011) is *PNAS* 108(31):12653–12656, not Psych Science.

---

## Phase 6 — Measurement & Self-Knowledge (2026-11-05, `phase6-measurement.md`)

| # | Decision (compact) | Tag |
|---|---|---|
| D1 | Evidence-grade operational criteria: assignment by acquisition channel (OBSERVED = Watcher-class capture · USER_CONFIRMED = user-authored · INFERRED = displayable rule · OUTCOME_CONFIRMED = external verification event); plausibility checks downgrade-and-flag, never upgrade; Watcher suspension → gap-marking (no inference over dead sensors); Phase 7 seed weights stand with two hard constraints: INFERRED never fires a state transition alone, Gorgeous gate counts only OBSERVED/OUTCOME_CONFIRMED; grades survive export as bracket tags | P1 |
| D2 | Life Log schema v1: `id · t_start · t_end · kind · grade · title · rule_id · verification · place · anchor · day_type · note`; GAP is a first-class entry type rendered "not tracked", never a zero; manual entries optional, ≤30 s/day friction budget (declared set-and-tested); gap-flow = one optional question inside Evening Close (≥2 h gap, once/day, one-tap skip, USER_CONFIRMED); no completeness metric, ever; P0 ships no Life Log surface (friction budget met by construction) | P1 (P0 constraint) |
| D3 | Prediction output spec: bigram tending model + recency/context features → split-conformal interval (28-day rolling calibration, daily re-split, width = drift alarm) → confidence-language table → display; abstention rules (<21 calibrated days, width > 40 pts, calibration drift, uninformative interval); percentages only inside intervals or as true frequency counts ("1 in 3"); counterfactual uplift claims banned → D4; language table ships with Forge v1 at P1 | P3 (P1 table) |
| D4 | Forge causal grammar: 3 claim classes — frequency (any grade) / co-occurrence (both cells n ≥ 5, association verbs, base rates shown, "BU doesn't know why" required) / change (declared change point + 14-day post-period + segmented pre/post + no competing changes); mechanism language and counterfactuals rejected at the formatter; confound checklist (regression to mean, selection, shared cause, confirmation sampling); v6 sentences pass/fail table delivered (generalizes Phase 3 D4's sleep grammar) | P3 (P1 forms) |
| D5 | N=1 experiment playbook: 4 designs (ABAB · alternating treatments · changing criterion · declared-change-point ITS) as fill-in cards with pre-declared outcome, ≥14-day phases, stable baseline, no-competing-changes; honest note — WWC standards are for multi-case synthesis, BU self-imposes the quality indicators; 5 seeded experiments E1–E5 (play-window, Watcher reactivity, whitelist content, close question order, fire-decay τ) | P0 |
| D6 | "Light the fire" consent-flow rules: one screen, five facts (what's collected — app class + timestamps, never content · what stays on device — everything incl. 529 MB model · what it costs — v6's ~3–5%/day battery, verbatim · how to stop — one toggle · what you keep — journal/fire/export per Phase 5 D7); no pre-selected defaults, no urgency, no dismissal shaming; consent once, granular later (P2 per-stream toggles), revocable always; declining = Lite with zero loss; P0 build rule: journal is plain-file local storage + exportable from day one | P1 (P0 storage) |
| D7 | Watcher interpretation caps + never-quantify list: Watcher reports its own counts (app-class, timestamps, drift minutes) — never states, traits, mood, or diagnoses even where phenotyping research explores them `[EMERGING]`; never-quantify: journal (no words/counts/sentiment), relationships (no contact metrics/comparison), rest (no scores/charts; coarse band = play-sizing input only), affect (no rating scales feeding any surface), fire (five states, never a percentage); drift minutes remain the boundary case under Phase 4 D6 copy rules | P1 (P0 surfaces already conform) |

**v6-doc amendments logged this phase:** (1) Etkin (2016) journal corrected — *Journal of Consumer Research* 42(6):967–984, not "JMR"; (2) the comparison-table line "5 grades (SCHEDULED → OUTCOME_CONFIRMED)" amended — BU has **four** evidence grades; SCHEDULED is an entry *status*, never an evidence grade; (3) "34% chance you do this tomorrow. Here's what raises it to 71%" re-specified as a banned copy form (naked point prediction + unsupported counterfactual) with pass-form replacements in D3/D4; (4) "complete record of your life" amended to *complete by construction, never by coverage* (gap-markers are the honest completeness); (5) the "Light the fire" consent prompt extended with the five-facts screen. **Citation corrections:** Parry et al. (2021) DOI is 10.1038/s41562-021-01117-5 (Nature Human Behaviour 5:1535–1547); Epstein et al. (2015) lived-informatics model is *UbiComp '15* pp. 731–742, doi 10.1145/2750858.2804250 (not a CHI paper); Andrews et al. (2015) is PLoS ONE 10(10):e0139004 (not e0139003); Wand & Wang (1996) is CACM 39(2):86–95.

---

## Phase 7 — Synthesis (2026-11-05, `phase7-synthesis.md`)

| # | Decision (compact) | Tag |
|---|---|---|
| D1 | Fire scoring function v1 locked: two ledgers (state excludes INFERRED by construction; coaching keeps 0.3 + displays its rule); w = OUTCOME 1.0 / OBSERVED 0.9 / USER 0.5 / INFERRED 0.3; decay exp(−d/τ), τ = 3 d pinned seed (E5 owns final); window 7 d (edge weight 0.097 ≈ EMBERS floor → seamless); thresholds 2.0/1.0/0.4/0.1 declared set-and-tested; partial = 0.5×; play size never discounts; omission dims emergent (P3 D10's mirror arithmetic superseded, copy rules unchanged); BLAZING verification-only (v6 amendment); relight arithmetic reproduces the 3-day rule exactly (3 × USER = 1.12 crosses 1.0) | P1 (P0 manual semantics) |
| D2 | P0 state-meaning table frozen: five v6 state descriptions verbatim as manual-fire copy; MANUAL tag; state updates only at Evening Close; fire never a number | P0 |
| D3 | Build gate: no mechanic ships without naming (a) Charter clause, (b) BCT code + evidence, (c) pattern-family clearance; master copy ban-list (8 families, owning decisions cited) ships as the normative lint file | P0 / all |
| D4 | N=1 master queue: 35 phase OQs → 12 prioritized experiments (E4, E1, relight survival, grammar compliance, partial honesty, day 3/14/30, sleep association, E5 τ, E2 reactivity, E3 whitelist, threshold tuning, gap-flow contamination); instrumentation lands in P0 schema | P0 |
| D5 | Evidence Charter v1 ratified: C1 no shame/character verdicts · C2 no fuel metaphors · C3 user-installed constraints only · C4 scaffolding fades on evidence · C5 grades on everything, INFERRED never moves state · C6 calibrated claims only · C7 record never withheld, return = one tap · C8 no dark patterns (badges = 10.4 Social reward) · C9 behaviors measured, experiences never · C10 gaps marked, never zeroed · C11 informed/revocable consent, on-device default · C12 real landmarks only | all |
| D6 | Sunday Review v1 (the Dewey deliverable): withered → pattern → grew → one intention, mapped to reflective-thinking stages; P1 frequency-only forms; P3 pattern class under causal grammar; no shame register | P1 / P3 |
| D7 | Research debt stated in public: 10 accepted unknowns (decay-vs-streak display, goes-dark, Gorgeous's 30, ladder minutes, identity-line effect, N=1 conformal coverage, passive-data interpretability, multi-year fire value, photo-embedding anti-cheat, pause-vs-Gorgeous chain) + per-phase citation debt carried | P0 |
| D8 | P0→P1 handoff: schema carries kind/grade columns from day one; no-close lines recorded; MANUAL tag retires at P1 switch-on with the designed relight moment ("BU can see now. Show it three days."); computed fire starts from an honest empty ledger | P0 |

**v6-doc amendments logged this phase:** (1) BLAZING is a verification-only state; (2) P3 D10's mirror-delta arithmetic superseded by emergent decay dims; (3) SCHEDULED "grade" swept (four grades; SCHEDULED = entry status at most); (4) partial-day (0.5×) and size-never-discounts rules join the fire's definition; (5) Gorgeous's "consecutive" breaks on GAP and partial days; (6) "4 of last 7" identity grammar confirmed as the source of the 7-day window. **Citation corrections:** Wixted & Ebbesen 1991 is *Psychological Science* 2(6):409–415 ("On the Form of Forgetting"), not Psychological Review; BCTTv1 badge code is **10.4 Social reward** (not "stimulus reward" — verified against two independent secondary listings; official 2013 Table 2 PDF remains debt). **Debt resolved:** Murre & Dros 2015 (PLOS ONE 10:e0120644) closes Phase 2's Ebbinghaus-replication item; Parry 2021 pinned at abstract level (106 effect sizes, moderate correspondence; exact pooled r still owed).
