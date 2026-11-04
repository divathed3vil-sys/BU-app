# BU v2 · Research Part 5 — Zero-Resistance UX
### Advanced behavioural psychology + real-world UI/UX application, aimed at one target: **resistance to opening and using BU → 0**

Date: 3 Nov 2026 · Status: delivered for owner review · Precedes mockup v3 and the v3 unified spec (Part 7).

> **The one-sentence thesis.** People don't resist planning apps because they're lazy; they resist because every existing app charges a hidden *tax* — decisions, guilt, typing, navigation, waiting, and shame — and BU's design goal is to drive that cumulative tax to zero while *raising* the felt payoff of each visit.

---

## 0 · Why this research exists (owner mandate)

Owner directive: *"redo the research again but in extra real world application of the ui and ux design and advanced psychological ways to making the resistance to use the app to 0"* and *"think like a senior designer and psychological professional to make the app ui 10 times better than anything out there."*

This document has five loads:

1. **§1–§3** — the psychology of resistance: what actually creates friction at the neurocognitive level, and the exact levers that remove it.
2. **§4–§5** — real-world applied patterns: how the best products alive solve each friction class, what we adopt, what we invert, what we reject (with reasons).
3. **§6** — the friction audit of the approved v2 mockup: every defect found, including the owner-reported ones (intro sliders, animation bug).
4. **§7–§9** — the BU v3 UX prescriptions: gesture system, motion system v3, zero-resistance patterns, measurable resistance budget.
5. **§10** — evidence table (what claims rest on which literature).

---

## 1 · The anatomy of resistance

### 1.1 The resistance equation

Behaviour = (Motivation × Prompt quality) / (Effort + Ambiguity + Threat + Delay-to-payoff)

This is Fogg's B=MAP rebuilt with the four things users actually experience as "resistance":

| Term | What it feels like | Where apps create it | Where BU removes it |
|---|---|---|---|
| **Effort** | "this takes work" | typing, tapping through hierarchies, forms | 1-action capture, auto-filled rituals, gesture nav |
| **Ambiguity** | "what am I supposed to do here?" | blank screens, unlabelled states, choice overload | every screen answers "what now?" in one glance; smart defaults everywhere |
| **Threat** | "this will judge me" | red badges, streak breaks, overdue shaming, guilt copy | no-red doctrine, graphite cooling, carry-not-fail, zero guilt copy |
| **Delay-to-payoff** | "what do I get out of this tap?" | dashboards that inform but don't act | every visit returns something usable (today's move, tomorrow's draft, one insight) |

**Design law derived:** any screen or flow that raises more than one of the four terms at once is broken by definition.

### 1.2 The three resistance peaks in a life app's day

Empirically (and from our own v2 test), resistance is not uniform — it spikes at three moments:

1. **The open moment** (thumb over the icon): the app competes with every other icon. Fix = ambient surfaces (widget, persistent NOW strip notification, lock-screen) so the *open* isn't even required; and when it is required, cold start must feel instant (Doherty threshold: <400 ms feedback, <1 s interactive).
2. **The input moment** (about to type/tap something): this is where native controls betray us — the v2 intro sliders are the perfect specimen (§6.2). Fix = purpose-built controls with snap, haptics, and visible consequence (the value changes *and the arc moves*).
3. **The confession moment** (recording what actually happened — "I didn't do it"): the highest-resistance instant in the entire category. Every competitor makes this worse (red overdue rows, broken streaks). BU's whole emotional architecture (§3.4) exists for this instant: confession must cost one tap and feel like *relief*, not failure.

### 1.3 Cognitive cost is the silent killer

- **Hick's law:** every additional visible choice adds decision latency logarithmically. The v2 Day screen shows ~7 interactive elements — good. The v3 doctrine: **no screen ever presents more than 3 primary decisions simultaneously**; everything else is progressive disclosure.
- **Sweller's cognitive load theory:** working memory holds ~4 chunks. A planning screen that requires holding "track + time + plan + evidence" in mind simultaneously is over budget. BU renders those as *one composite visual* (the Arc + card) instead of four fields.
- **Tesler's law (conservation of complexity):** complexity can't be destroyed, only moved. BU's stance: move it from the user to the machine — the system pre-fills, pre-drafts, pre-computes; the user only *confirms or edits*. This is exactly the owner's Claude-blueprint principle "Needle understands, rules decide, templates speak," restated as a UX law: **the user edits, never authors from zero** (except when they want to).

### 1.4 Fitts's law and the thumb map

Hoober's large-sample studies of real mobile interaction: ~49% one-thumb, ~36% one-finger-cradled; the natural thumb arc covers the bottom ~⅓ of the screen richly and the top corners poorly. Applied to BU:

- All **high-frequency actions** (capture, tally, start/stop, swipe targets) live in the **bottom 40%** of every screen.
- The **Pebble FAB** (draggable capture pebble) is the ultimate Fitts optimisation: the user *moves the button to their thumb*, and it remembers. v3 adds magnetic edge-docking and a lift-scale so the drag feels physical.
- Top-of-screen real estate is reserved for *glanceable state* (dates, arcs, receipts), never for controls.

---

## 2 · The psychological engine (what makes people *want* to return)

### 2.1 Reward architecture: variable insight, not variable jackpot

Slot machines hook via variable rewards on a rapid loop. We invert it: **variable *insight*** — once per day, the Dusk Close (v3: Tomorrow Contract) reveals one pattern surfaced from the user's own data ("your study sessions after 8 PM run 34% longer than afternoon ones"). Finite, personal, non-addictive. This keeps the dopamine *anticipation* (which is the motivational part — Berridge's wanting/liking split) while removing the treadmill.

### 2.2 The Zeigarnik advantage — deliberately harvested

Unfinished tasks occupy working memory (Zeigarnik effect; Masicampo & Baumeister 2011 showed a *concrete plan* releases that tension). This is the deepest "why" behind the nightly offload + commit ritual: **BU is a Zeigarnik-relief machine.** The UX must make the release *felt*: the seal animation of the Night Contract is the moment tension visibly leaves — that's why v3 makes the seal the peak-end moment (§3.5).

### 2.3 Self-Determination Theory as a design compiler

SDT's three needs (Deci & Ryan) compile directly into features:

| SDT need | Feature compile |
|---|---|
| **Autonomy** | The user builds every track; ceilings and ladder limits are *pre-consented* (the Firmness Ladder seals); nothing is ever imposed; every suggestion is dismissible |
| **Competence** | Evidence-based tallies (not judgement), Momentum as a 7-day rolling curve (never a broken streak), visible estimate calibration ("your estimates are getting accurate — 92% this month") |
| **Relatedness** | Deliberately minimal in a single-user app: the Care Graph surfaces *people* who recur in the journal; optional one-tap share to a chosen human; no leaderboards ever |

### 2.4 Reactance: the reason most blockers get uninstalled

Psychological reactance (Brehm): perceived threat to freedom → doing the forbidden thing *more*. Field evidence: one sec's PNAS study (Grüning et al. 2023) works precisely because it *invites deliberation* rather than forbids — and heavy-handed blockers (Opal hard-mode stories, unremovable blocks) show uninstall/rebound patterns. BU's Firmness Ladder L0→L5 with owner-sealed ceilings, typed-reason overrides, and the Reactance Guard (bypass-rate trigger → de-escalate + check-in) is reactance management as a *system*, not a setting.

### 2.5 Identity-based habit formation

Behaviour change that survives bad weeks is identity-based ("I'm someone who closes their day") not outcome-based. The v3 Constellation is the identity surface: it renders **who you're becoming** (virtues, recurring themes, people, projects as a living graph), not what you scored. No points, no levels — the graph thickens and connects over months; that *is* the progress display.

### 2.6 Fresh Start Effect & temporal landmarks

Katy Milkman's work: people initiate change at temporal landmarks (new week, month, birthday). BU operationalises: the Sunday Review is anchored to the week boundary; the Dawn Push is anchored to the day boundary (day-start hour, default 5:00 — owner's locked decision); the app treats *every* missed period as a fresh start ("new day starts at 5:00, nothing before it is 'late'").

### 2.7 Temptation bundling & friction asymmetry

Milkman's temptation bundling: bind "want" activities to "should" activities. BU's version is structural, not gimmicky: audio/podcasts are *allowed* during workout blocks (always-allowed by rule type); the "wind-down" block is paired with journaling because it precedes it. Friction asymmetry law: **make the good action 1 tap; make the distracting detour ≥2 taps + a breath** — never impossible, just asymmetric.

### 2.8 Emotional weather — the shame killer protocol

Research consensus (and one sec's authors' own caveats): shame predicts avoidance, not behaviour change. BU's emotional protocol:

- **Missed never red.** Graphite (cool gray-green) is the only "absence" colour. Red exists solely for destructive confirmations.
- **Language doctrine.** No "failed/missed/overdue/broken." Canonical replacements: "carried", "cooling", "parked", "still waiting". The word "missed" is banned from every string in the app (enforced in design review).
- **The carry is the hero.** Carry-with-cooldown (from v2) is psychologically the *pardon*: the task visibly relaxes (grayscale slide-in animation) — the system says "I moved it for you" instead of "you failed."
- **The Low-Mood Guard** (from owner's blueprint, adopted): sustained downward mood trend → system caps firmness at L2, switches template voice to supportive, and surfaces a human-contact path. The app must behave like a friend who noticed, not a manager who found a KPI drop.

---

## 3 · Perception, motion, and feel — the neuroscience layer

### 3.1 The performance-psychology link

Resistance is partly *perceived* latency. Key thresholds (Nielsen/Doherty/RAIL):

| Event | Budget | Feeling |
|---|---|---|
| Press feedback (visual + haptic) | ≤ 100 ms | instant, controlled |
| Screen transition | 220–380 ms | fluid, alive |
| Cold start → interactive | < 1 s | "this app respects me" |
| Any animation > 500 ms on a frequent path | forbidden | "this app is slow/fancy" |

**BU v3 law:** no animation on any frequent path may exceed 500 ms; entry stagger total ≤ 600 ms; the only infinite animations are the breathing dot, the ambient wash drift, and the blob field — all transform/opacity-only (compositor), never layout.

### 3.2 Spring physics as emotional register

Bounce-spring motion (owner doctrine) is not decoration — it encodes personality: slightly-overshooting springs read as *alive, friendly, confident*; linear eases read as *mechanical*. v3 codifies four named springs and *when each is emotionally correct* (see Part 7 §D-MOTION): `snappy` (controls), `bouncy` (elements entering/docking), `screen` (navigation), `soft` (calm, mourning, low-mood mode — springs *literally soften* when the Low-Mood Guard is active: the entire app's physics relaxes).

### 3.3 Haptic grammar as a language

Vibration patterns are a vocabulary users feel but never consciously read. v2's grammar (tap 8ms / tick [12] / lock [18,40,26] / win [10,30,10,30,46] / warn [30,60,30]) is kept and extended with usage rules: one haptic per cause, never on scroll, never during drags except liftoff/touchdown, and the *win* pattern is reserved for Dusk-seal and momentum-milestones only — scarcity keeps it meaningful.

### 3.4 The peak-end rule, operationalised

Kahneman: memories of experiences ≈ peak + end. The nightly ritual is engineered as a **positive peak-end**: the *peak* is the Day Receipt reveal (your day, auto-compiled, mostly pre-filled — the "I did more than I thought" effect) and the *end* is the physical seal (press-and-hold, ring fills under the thumb, haptic `win`, tomorrow visibly drafts itself, screen dims to warm dusk). The app *ends the day for you* — a stopping cue no competitor offers.

### 3.5 Aesthetic-usability effect, spent deliberately

Users rate beautiful UI as more usable (Kurosu & Kashimura; Tractinsky) — beauty is a *trust loan*. BU spends it on: the Arc (the one hero visual), glass depth, the mint accent, grain texture. It never spends it on: fake gradients on data, decorative charts, or animation for its own sake ("a control room should not fidget" — retained).

---

## 4 · Real-world pattern library — what the best products alive teach

Each pattern: source product → mechanism → BU adoption decision.

### 4.1 Capture & input

| Product | Pattern | Verdict |
|---|---|---|
| **Things 3** | The Magic Plus: hover a list edge → a light "+" appears exactly where your cursor is | **Adopt (adapted):** the Pebble already does this physically; v3 adds contextual "+" at list-ends while scrolling |
| **Bear** | One global note; zero setup to first word | Adopt: capture sheet is *always one field + one send*; structure (task/idea/spark) is chosen *after* by a segmented chip with smart default from content ("remind me…" → task) |
| **Daylio** | 2-tap mood log (icon grid → done) | Adopt: the 3-tap check-in (mood/energy/one-word) as the *fastest* journal path; full typing optional |
| **Google Clock / iOS timers** | Direct-manipulation dials instead of number pads | Adopt (as replacement for our sliders): **the Arc-Dial time picker** — drag the arc itself, or tap ±5m steppers; native slider deleted everywhere (§6.2) |
| **Sunsama** | Guided daily planning ritual with auto-pull from backlog | Adopt: Dusk/Day planning pulls *unfinished* items forward automatically; never makes the user re-enter |
| **Siri/Assistant quick phrases** | One-line natural language → structured action | Adopt via Needle tool-calls + deterministic fallback parser; both behind one input |

### 4.2 Navigation & structure

| Product | Pattern | Verdict |
|---|---|---|
| **Material 3 (system-wide)** | Predictive back gesture, edge-swipe back | Adopt: edge-swipe back on every pushed screen; predictive peek on the Dusk ritual (drag back = cancel, no data loss) |
| **iOS tab controllers / Instagram** | Horizontal swipe between adjacent tabs | **Adopt: swipe horizontally anywhere on tab screens moves between Day ⇄ Graph ⇄ Journal ⇄ Shield ⇄ You** (order = thumb-arc priority); indicator pill physically *slides* (shared-element) rather than popping |
| **Notion / Obsidian** | Command palette (⌘K) for power paths | Already adopted (palette, ≤2-tap law) |
| **Telegram / WhatsApp** | Pull-to-refresh with rubber-band + settle | Adopt on Day/Journal: pull past top → gentle sync/pulse (in v3 it "re-compiles today's arc" — a functional refresh) |
| **Google Photos** | Long-press → multi-select context | Adopt: long-press a plan card → quick actions (carry / shrink / evidence / delete) without leaving the screen |
| **YouTube / TikTok** | Double-tap = primary affection action | Adopt (inverted): double-tap a *done* card = "pin as win" (goes into Dusk highlights). One deliberate positive action per gesture |

### 4.3 Trust, calm, and long-term retention

| Product | Pattern | Verdict |
|---|---|---|
| **one sec** | Breathing friction before opening distraction apps | Adopt inside Guardian L2 (Pause rung): one breath, "what are you here for?", easy exit |
| **Finch** | Caring metaphor instead of scoring | *Partially* adopt: the Constellation is the "pet" — but it is *data-born*, never decorative-guilt-born |
| **Duolingo** | Streaks + streak freeze | **Reject the streak; adopt the mercy:** Momentum = 7-day rolling ratio with grace built into the math — a bad day bends the curve, never breaks a chain |
| **Opal / Jomo** | Session commitments ("lock phone for 25 min") | Adopt as Focus-with-contract: sealing a focus session is a mini-contract visible on the NOW strip |
| **Rise / Amie (sleep & energy)** | Energy curve rendered as the schedule's background | Adopt: energy-by-hour data tints the Day Arc background (subtle, from 3-tap check-ins) |
| **Forest** | Death-if-fail commitment device | Reject the death; keep the *visible commitment*: focus session shows a growing moss-ring that stops growing (never dies) if abandoned early |

### 4.4 Onboarding

| Product | Pattern | Verdict |
|---|---|---|
| **Arc browser** | Personality-filled, progress-revealing onboarding | Adopt tone; **reject length** — BU onboarding ≤ 90 s to first value |
| **Notion** | "Start from scratch vs templates" | Invert (locked decision): **no presets, ever** — the wizard asks and builds |
| **Headspace** | One decision per screen, enormous type | Adopt as the onboarding v3 layout law (fixes the cramped v2 wizard) |
| **Mindful (GPL reference)** | Capability manifest with honest payloads | Already adopted — v3 adds live "what one event looks like" JSON to every card |

---

## 5 · The Zero-Resistance Doctrine (v3 laws)

1. **The 3-Second Contract.** Any core action (capture, tally, start/stop track, defer, open any screen) completes in ≤ 3 s from intent, cold.
2. **One decision per screen-state.** At any instant the UI presents exactly one primary decision; everything else is context or disclosure.
3. **The user edits, never authors from zero** (machine pre-fills; Needle proposes; templates speak).
4. **Zero guilt language** — enforced string-level ban: failed, missed, overdue, broken, behind, lazy.
5. **Every visit pays.** No screen is purely informational; each returns one actionable or one insight.
6. **The app ends the day.** Dusk seal is a stopping cue; after it, BU goes visibly quiet (dimmed accents, "day closed" ambience).
7. **Thumb-arc supremacy.** Frequency-ranked controls live bottom-40%; nothing critical top-corners.
8. **No native controls on designed surfaces.** Sliders, pickers, switches in BU are purpose-built with haptics + snap + live consequence.
9. **Motion ≤ 500 ms on frequent paths; springs carry emotion** (soft mode literally relaxes physics).
10. **Ambient before app.** If a thing can be shown on the widget / NOW strip / lock screen, it never requires opening the app.

---

## 6 · Friction audit of the approved v2 mockup (defect register)

### 6.1 Owner-reported

| # | Defect | Root cause found in code | Severity |
|---|---|---|---|
| A1 | **"Sliders from the intro section don't feel good at all"** | `Setup` wizard uses raw `<input type="range">` (Boot.tsx `Slider`): 15-min steps across up to 1440-min range → thumb moves in ~1.5% increments, feels mushy and imprecise; no value bubble; no live arc response beyond label text; `haptic(4)` fires on *every* change event → vibration spam during drag; native thumb has no rest→active affordance; ExamCard days-slider same disease | **High** — replaced by Arc-Dial picker + engineered slider in v3 |
| A2 | **"A little bug in animation"** | Multiple concrete defects found: (1) `.bu-hint` runs two keyframe chains (`bu-fade` delayed 2.4s `forwards` + `bu-float` delayed 3s `infinite`) — between 2.4–3.0s the hint *fades in while transform-animating*, producing a visible pop; (2) duplicate `@keyframes bu-dash` injected twice into the DOM (Boot.tsx ArcPreview + kit.tsx Sparkline) — collisions when both remount; (3) TabBar active-pill uses `key={active}` inside a `map` — on rapid tab switching two pills can coexist for a frame (both `on` true during React commit gap), causing a flicker; (4) pebble ghost-trail is a 54px div with `filter: blur(18px)` animating transform every frame — GPU-expensive, stutters on integrated GPUs (the owner's "little bug" most likely seen here or in (3)) | **Medium** — all fixed in v3 motion system |

### 6.2 Found by this audit (owner didn't name these, but they are resistance)

| # | Defect | Where | v3 fix |
|---|---|---|---|
| A3 | Dead v1 files in tree (`Dial.tsx`, `store.ts`, `ui.tsx`, `Overlays.tsx`, `screens/Now|Threads|onboarding|Misc`) — `Dial.tsx` imports non-existent exports; confusing, lint-risk, bloat | `src/components/bu/` | Delete all v1 leftovers |
| A4 | Setup wizard steps *jump* (`bu-step-in` slide-in only; no exit animation; back button replays the *forward* slide) | Boot.tsx | Directional transition (forward slides L→R, back slides R→L), 260 ms |
| A5 | Wizard has no progress affordance beyond 4 tiny dots; user can't see "how much left" (resistance = unknown length) | Boot.tsx | Step dots + "step X of 4" mono label + estimated seconds per step |
| A6 | Welcome screen is static after entry; the hero mark floats but the loop concept (BU's whole identity) is unexplained | Boot.tsx | Animated BU-loop micro-diagram (Capture→Plan→Do→Close→Grow ring, 6s loop, transform-only) |
| A7 | No gesture navigation at all: no swipe between tabs, no edge-back on pushed screens, no pull-to-refresh, no long-press quick actions | Shell/screens | Full gesture system (§7) |
| A8 | Capture sheet default kind is manually chosen; no content-based smart default | Journal.tsx | Keyword heuristic ("remind me/to/at" → task; "?" → spark idea; default journal fragment) with one-tap override |
| A9 | Day screen's "now" awareness is a demo constant (`DEMO_NOW_MIN`); in v3 the arc highlight must be *time-true* (minute of the real clock) so the user trusts it | theme.ts | Real clock + frozen-demo toggle for reviewers |
| A10 | Toggle switches animate `left` (layout property — main-thread) instead of `transform: translateX` | kit.tsx | transform-based toggle |
| A11 | `.bu-range` sliders lack keyboard accessibility (no focus ring visible on dark bg) | bu.css | focus-visible ring token |
| A12 | Manifest cards toggle instantly with no state-change choreography (border color only); granting a capability should *feel* like granting power | Boot.tsx | grant animation: card border mint-bloom + icon tile scale-pop + haptic `lock` |
| A13 | Screen entry replays full stagger on every tab return — feels slow on repeat visits | Shell.tsx | First-visit full stagger; return-visit fast path (fade 140 ms) — respects "repeat visits must feel faster" |
| A14 | Notification banner can overlap the NOW strip and pebble; z-warrior patching | Shell.tsx | Banner slot reserved; pebble auto-docks away from banner for its lifetime |
| A15 | Palette results don't support arrow-key navigation (desktop) | Shell.tsx | ↑↓ + enter, focus trap |

### 6.3 Verdict

The v2 bones are strong (information architecture, colour doctrine, no-guilt language, pebble concept). Every defect above is *surface* — which is exactly why a v3 pass (not a rebuild) is correct: keep the IA and screens; rebuild the input layer (sliders→arc-dial), motion layer (v3 spec), gesture layer (§7), and blend in the Claude-blueprint features (Part 7).

---

## 7 · BU v3 gesture system (complete map)

Design constraints: single-hand reach, Galaxy A54 (120 Hz, good haptics), no gesture conflicts with system back, discoverability via affordance + first-run hints.

### 7.1 Navigation gestures

| Gesture | Surface | Action | Feedback |
|---|---|---|---|
| **Horizontal swipe** (anywhere, ≥48 px, velocity-gated) | All 5 tab screens | Next/prev tab (Day→Graph→Journal→Shield→You) | Content follows finger 1:1; tab pill slides (shared element); haptic `tick` on settle |
| **Edge-swipe back** (left or right edge) | Pushed screens (Dusk, Dawn, Sunday, Focus, Reminders, ReminderEdit) | Back | Predictive peek: previous screen slides in from edge at 60% parallax; commit past 40% width or 600 px/s |
| **Pull down** (past top, rubber-band) | Day, Journal | Refresh/re-compile (arc re-sweeps, day receipt re-renders) | Overshow of a mint pulse ring; haptic `tick` at trigger |
| **Long-press card** | Plan/Big Three/Spark cards | Quick actions sheet: Carry / Shrink / Evidence / Edit / Delete | Card lifts (scale 1.02 + shadow bloom); haptic `lock` |
| **Double-tap card (done)** | Any completed item | Pin as today's win (added to Dusk highlights) | Card emits one mint bloom; haptic `win` reserved |
| **Drag pebble** | Anywhere | Reposition; edge-dock with magnetic snap | Squash-stretch + tilt + ghost trail (transform-only); haptics on liftoff & dock only |
| **Two-finger swipe down** | Anywhere (power user) | Open command palette | Subtle screen dim; palette rises |
| **Swipe-to-dismiss** (any direction from origin) | Notification banner | Dismiss | Banner follows finger, fades past 24 px |

### 7.2 Input gestures (the slider replacements)

- **Arc-Dial time picker** (replaces every time slider, incl. Setup "day starts/ends", Dawn/Dusk times, Exam days): the arc *is* the control. Drag the arc handle around the dial; a mono value bubble rides the thumb; ±5 min stepper chips sit under the dial for precision; snap-to-15-min with a soft magnetic pull in the last 4° before a snap point; haptic `tick` on each snap; the arc *fills live* under the finger (consequence visible). Falls back to steppers-only for accessibility ( VoiceOver/keyboard: two buttons + value announcement).
- **Tally Trio** (Done/Partial/Carry): three-zone horizontal swipe on a card — swipe right = Done, left = Carry (cooldown), down = Partial — with colour intent revealed *while dragging* (mint fills right, graphite fills left). Tap still works for discoverability. This makes the nightly confession ≤ 1 s.
- **Slip-to-shrink:** on a Big Three card dragged left *and held*, a "shrink" affordance appears (halve the scope, keep the block). This operationalises "Shrink" from the contract triage as a gesture.

### 7.3 Discoverability protocol (gestures die if undiscovered)

1. First-run coach marks: 3 max, auto-dismiss on first successful gesture (never re-shown).
2. Every gesture has a visible-button twin on first encounter of its screen (tap-learners are not punished).
3. After the gesture is used once, the button twin demotes to overflow.

---

## 8 · The resistance budget (how we measure "0")

Zero is a direction, but it must be *measured*. Instrumented in the real app (mockup approximates):

| Metric | Definition | Target |
|---|---|---|
| **Time-to-log** | intent → evidence recorded | ≤ 3 s (P50), ≤ 5 s (P95) |
| **Time-to-capture** | intent → inbox item saved | ≤ 2 s |
| **Ritual completion rate** | Dusk seals started vs completed | ≥ 90% (a started ritual that quits mid-way = resistance spike to fix) |
| **Ritual duration drift** | median Dusk duration over weeks | must *fall* toward 60–90 s |
| **Open friction** | cold start → first meaningful pixel | < 1 s |
| **Return velocity** | median hours between open events | tracked, never pushed at (shown only in Sunday Review) |
| **Carry latency** | time a carried task sits before triage | ≤ 1 ritual (auto-drafted anyway) |
| **Words per confession** | evidence text length distribution | falling median = good (fewer words, same truth) |

The dashboard for these lives in *You → System* — the owner watches resistance fall in their own data. That's the meta-loop: the app measures how easy it is to use the app.

---

## 9 · What "10× better than anything out there" concretely means

Against the field (Sunsama, Things 3, Daylio, Finch, one sec, Opal, Rise, Motion, Mindful):

1. **Rituals that finish themselves.** Sunsama makes you plan; BU *drafts tomorrow from evidence and confession* — the user only edits. That single difference is the moat.
2. **A confession that forgives.** No competitor has Carry-with-cooldown + graphite semantics + zero-guilt string ban. The category's emotional floor is guilt; we remove it.
3. **One screen that *is* the day.** The Arc (time), Big Three (intent), Sparks (capture), NOW strip (present), Momentum (continuity) — competitors need 4 apps to say what Day says in one glance.
4. **Controls engineered like instruments.** Arc-dial pickers, tally-swipes, magnetic pebble — the tactile layer competitors don't have because they ship native controls.
5. **Motion that has a spine.** Named springs with emotional mapping (including the soft-mode physics relaxation) — personality through physics, not through mascot.
6. **Guardian that consents first.** The sealed Firmness Ladder + Reactance Guard + Low-Mood Guard is ethically and mechanically ahead of Opal/one sec hard modes.
7. **The loop is legible.** Welcome explains Capture→Plan→Do→Close→Grow in one 6-second animation; every screen then *shows which phase it serves* (a tiny phase-dot in each header). The user always knows where they are in their own system.

---

## 10 · Evidence table

| Claim | Source |
|---|---|
| B=MAP; simplicity drives behaviour | Fogg, *Tiny Habits* (2019) |
| Concrete plans release goal tension | Masicampo & Baumeister (2011), *JPSP* |
| Implementation intentions effects (medium-to-large) | Gollwitzer & Sheeran (2006), *Adv. Exp. Soc. Psych.* |
| one sec field RCT: 36% closure, 37% less app-opening by wk 6; friction + deliberation + dismissal | Grüning, Riedel & Lorenz-Spreen (2023), *PNAS* 120(8) |
| Reactance → doing forbidden things more; heavy control backfires | Brehm (1966); self-determination literature (Deci & Ryan) |
| Shame predicts avoidance | Tangney et al. (self-conscious emotions literature) |
| Variable rewards & wanting/liking | Berridge & Robinson incentive-salience work |
| Zeigarnik relief via planning | Masicampo & Baumeister (2011) |
| Fresh-start effects | Milkman, Riis & Dai (2014) |
| Temptation bundling | Milkman, Minson & Volpp (2014) |
| One-thumb usage ~49%; thumb-zone reachability | Hoober (2013), *Designing Interfaces for one-handed use* |
| Response-time perception budgets (0.1 s / 1 s / 10 s) | Nielsen & Doherty thresholds; RAIL model |
| Aesthetic-usability effect | Kurosu & Kashimura (1995); Tractinsky (1997) |
| JIT adaptive reminders (bandit-timed) beat fixed reminders | Haag et al. (2024); IJERPH feasibility study (2021) |
| Habituation of interventions, recovery with rest | ROGUE bandit framework (Mintz et al., 2020) |
| Peak-end rule | Kahneman et al. (1993) |
| Hick's law / cognitive load | Hick (1952); Sweller (1988) |

*All literature cites retained from research parts 1–3 + owner's Claude blueprint; nothing here depends on unreleased or unverifiable sources.*
