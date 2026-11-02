# BU v2 · Research Part 7 — The Blend
### Owner's Claude blueprint × approved v2 mockup → **BU v3 unified specification**

Date: 3 Nov 2026 · Status: delivered for owner review · This is the working contract for mockup v3 and all build phases.

---

## 0 · What survives from earlier phases (locked decisions, unchanged)

From research parts 1–3 (approved; archives lost in a workspace reset — conclusions preserved here as the constitution):

- **The BU Loop:** CAPTURE → PLAN → DO → CLOSE → GROW.
- **No preset tracks, ever.** Everything user-built (demo-fill exists only as an explicit button).
- **Big Three** (≤3 daily priorities with time slots) · **Blocks** (time-boxed calendar pieces) · **Sparks** (tiny tasks/ideas, capture-anywhere) · **Carry** (cooldown-based deferral, never red) · **Momentum** (7-day rolling ratio curve, unbreakable) · **Constellation** (growth graph).
- **Rituals:** Dusk Close (≤90 s), Dawn Push, Sunday Review (≤3-week anchor), missed-recommitment escalation gentle→firm→resize.
- **Design dogma:** near-black + subtle gray + subtle mint; bounce-spring doctrine; 12 motion rules; haptic grammar; dynamic blurred background (optimized); no red except destructive; serif only in journal; mono tabular for data.
- **Guardian:** dual-plane with Identity plane, fully custom reminder engine (channels, exact-alarm health, per-reminder control), Needle adaptive push, Focus inherits user tracks, ACS exam dates customizable with mandatory reminders, hours 5:00–23:00 default-adjustable, INTERNET accepted for later VPN block.
- From the v1 BUILD-GUIDE (kept where not superseded): atomic+checksummed vault writes, capability manifest with "Sees / Never / payload", perf budgets, fallback-parser-always rule, red-only-destructive, empty states are instructions, ≤2-tap law, PROGRESS.md discipline.

> Note: parts 1–3 original files were lost to a sandbox reset; this section is the canonical record of their conclusions. The v1 guide archive + owner's blueprint are stored alongside this research.

---

## 1 · Vocabulary reconciliation (one system, no synonyms)

| Owner's blueprint term | BU v3 term (approved lineage) | Notes |
|---|---|---|
| Life Loop (Sense→Understand→Decide→Act→Learn) | **BU Loop** (Capture→Plan→Do→Close→Grow) | Same loop; BU names are user-facing |
| Tomorrow Contract (nightly) | **Dusk Close** — internally "the Night Contract" | Blue print's 6-step structure ADOPTED inside our 90 s shell (see §3.2) |
| Week Compass (Sundays) | **Sunday Review** | Blueprint's Reality Multiplier, capacity map, seeding ADOPTED |
| Main Front | **Big Three #1** — the day's front | "Main Front" becomes the *label of the first Big Three slot*, with exactly-one rule |
| Minimum Viable Day (MVD) | **Minimum Day** | One thing that makes tomorrow a success if all else collapses; new field on the contract |
| Day Receipt | **Day Receipt** | Adopted as-is: auto-filled planned-vs-done + drift + sinks + mood |
| Pre-mortem + if-then | **If-Then shield** | One obstacle → classified (phone/fatigue/interruption/ambiguity/people) → one if-then plan accepted/edited |
| Reality Multiplier | **Reality Multiplier** | 4-week actual/estimate ratio per task type; auto-scales new estimates |
| Care Graph / Care Items | **Constellation** + **Reminder engine** | People/themes that recur in 3+ entries proposed as constellations nodes; Care Item types adopted for reminders |
| Firmness Ladder L0–L5 | **Shield rungs 0–5** (Observe/Nudge/Pause/Soft/Hard/Contract Lock) | Adopted fully, incl. ceilings sealed nightly, Reactance Guard, Low-Mood Guard |
| Slip Score / Drift Index / Distraction Load | **Slip Score / Drift / Pull** | "Distraction Load" renamed **Pull** (short-form minutes + unlocks + late-night) |
| Adaptive bandit (Thompson) | **The Adapter** | On-device bandit choosing window × template × channel; reward = acted ≤30 min |
| Template voice engine | **The Voice** | 400–600 tagged templates; combinatorial grammar; never shames; autonomy-supportive |
| Invincible Mode | **Contract Lock (rung 5)** | Only for rules sealed the night before; emergency path only |
| Ghost fact | **Ghost stamp** | Machine-offered observation awaiting one-tap accept (AI never writes user's words) |
| Promise detection | **Promises** | "I'll call X Friday" → reminder confirm-chip |
| Stoic prompts / virtues | **Virtues** (8, user-editable) | Constellation renders virtues as identity, not points |

**Rejected from blueprint (with reasons):** Mood-as-gatekeeping everywhere (mood is *data*, never a lock); "Letter to Friday-you" (folded into Sunday Review as an optional line — scope); "six Fronts cap" language (we already have Big Three); any feature that increases daily minutes-in-app.

---

## 2 · The nine screens (final)

| # | Screen | Serves | Absorbs |
|---|---|---|---|
| 1 | **Day** | Capture · Plan · Do | Arc, Big Three, Blocks, Sparks, NOW strip, Day Receipt summary, ghost stamps, momentum head-line, Exam horizon chip |
| 2 | **Dusk Close** | Close | Tomorrow Contract 5 steps: Receipt → Offload → Triage (Do/Shrink/Kill) → Commit (Big Three + Minimum Day + If-Then + ceiling) → Seal; auto-drafts tomorrow |
| 3 | **Dawn Push** | Plan | Yesterday's wins + today's front + if-then recall + first move |
| 4 | **Sunday Review** | Grow | Week scorecard, Reality Multiplier, Drift, constellation growth, week rules + ceilings seeding, anchors re-commit |
| 5 | **Constellation** | Grow | Zettelkasten graph: threads/tracks/people/virtues as nodes; orphans surfaced kindly; Care proposals |
| 6 | **Journal** | Capture · Grow | Threads, 3-tap check-ins, prompts, promises, semantic "days like this", ghost stamps inline |
| 7 | **Shield** | Do (protection) | Ladder rungs 0–5, focus sessions (inherit tracks), app budgets, digests, always-allowed list, rung health card |
| 8 | **Reminders** | Plan | Care Items (anchor/contextual/relational/value/deadline/habit), per-reminder control, escalation chain, exact-alarm health |
| 9 | **You** | Grow | Capabilities (Sees/Never/payload), tracks, The Voice tone preview, data (export/trash), resistance budget dashboard, soft mode |

Overlays: Capture sheet, Command palette, Guardian block overlay (L2 pause / interstitial), Notification banner.

---

## 3 · The rituals, precisely (blueprint structure inside BU shell)

### 3.1 Dawn Push (≤60 s)
Yesterday's wins (2 lines) → today's Big Three (already drafted) → if-then shield recall (1 line) → first move (one tap to start it). Ends with the day's front visible on the Arc.

### 3.2 Dusk Close (target 60–90 s; hard ceiling 6 min)
1. **Receipt** (15 s): auto-filled planned vs done, drift %, biggest Pull source, mood chip. Pre-filled = zero typing.
2. **Offload** (20 s, optional): brain-dump one line or voice; Needle/fallback extracts tasks+worries; unfinished get parked with when-I-will.
3. **Triage** (20 s): each unfinished item → **Do / Shrink / Kill** via swipe or tap; moved-3-times forces the decision.
4. **Commit** (25 s): Big Three (max), **Minimum Day**, **If-Then shield** (obstacle classified → one plan), tomorrow's **ceiling rung** (pre-consented max firmness), wake plan.
5. **Seal** (5 s): press-and-hold; ring fills under thumb; haptic `win`; tomorrow drafts itself visibly; app goes quiet (dimmed accents, "day closed").
*Evidence pre-fill (v2 doctrine) does the heavy lifting: tallies are already suggested from Focus/usage; the user confirms with one tap each.*

### 3.3 Sunday Review (≤15 min, anchor ≤3 weeks)
Week scorecard by track → **Reality Multiplier** reveal + estimate corrections → Drift + Pull trends → Constellation growth (new nodes?) → week rules (3 if-thens) + daily ceilings → seed Monday–Sunday fronts (each night then only adjusts) → one-line "to Friday-me".

---

## 4 · Feature blend matrix (everything the blueprint adds, dispositioned)

| Blueprint feature | Disposition | Lands in |
|---|---|---|
| Needle as nervous system (tools/extraction/embeddings/confidence) | **Adopt** (Part 6 §5) | P6 |
| Tomorrow Contract structure | **Adopted into Dusk Close** | P2 |
| Week Compass + Reality Multiplier + capacity map | **Adopted into Sunday Review** | P7 |
| Main Front / MVD / if-then pre-mortem | **Adopt** as Big-Three#1 / Minimum Day / If-Then shield | P1/P2 |
| Firmness Ladder L0–L5 + sealed ceilings + Reactance + Low-Mood guards | **Adopt** = Shield rungs | P4/P5 |
| Care Graph notification types + budget + digests + "Why now?" | **Adopt** = Reminder engine v3 | P2 |
| Adaptive bandit + habituation rest days (ROGUE) | **Adopt** = The Adapter | P5 |
| Template voice bank (400–600) + grammar | **Adopt** = The Voice | P5 |
| Ghost stamps / promise detection / semantic recall | **Adopt** | P3/P6 |
| 3-tap check-ins (mood/energy/word) | **Adopt** | P3 |
| Energy-by-hour tint on Day Arc | **Adopt** (subtle) | P3 |
| Drift everywhere instead of raw screen time | **Adopt** | P4 |
| Stoic template sources (public-domain translations) | **Adopt** | P5 |
| Sleep proxy (screen-off stretch) | **Adopt** | P4 |
| Fresh-start day boundary | **Adopt**: day-start hour (default 5:00) governs "yesterday" | P1 |
| Flutter UI | **Reject** (Part 6 §1) | — |
| 65–70 screen scope | **Reject** — 9 screens | — |
| Always-allowed list | **Adopt** (calls/maps/banking/2FA/health never touched) | P4 |
| Weekly export reminder (once) | **Adopt** | P7 |
| GPL decision | **Adopt**: BU stays closed → reimplement Mindful concepts, never its code (already doctrine) | constant |

---

## 5 · UX overhaul plan (from Part 5) — what mockup v3 implements

1. **Motion v3:** fix audit defects A1/A2/A4/A10/A12/A13 (hint pop, dash collisions, tab flicker, blur-jank, directional wizard steps, transform toggle, grant choreography, return-visit fast path). Add 6s BU-loop diagram on Welcome (A6). Springs named `snappy/bouncy/screen/soft`.
2. **Input layer:** native sliders **deleted**. Arc-Dial time picker everywhere (Setup day hours, Dawn/Dusk times, exam days, reminder times, focus length): drag the arc, value bubble rides thumb, ±5m steppers, snap-15 with haptic tick, live consequence.
3. **Gestures:** horizontal swipe between 5 tabs (indicator slides); edge-swipe back with predictive peek on pushed screens; pull-down refresh on Day/Journal; long-press card quick actions; double-tap done card = pin as win; pebble reposition with magnetic edge dock; two-finger swipe down = palette; swipe-to-dismiss banner. Coach marks: 3 max.
4. **Tally Trio swipe:** right = Done, left = Carry, down = Partial, with colour intent while dragging.
5. **Onboarding v3:** one decision per screen, giant type, ≤90 s; capability cards get live "one event" payloads + grant choreography; wizard gets step labels + directional transitions + Arc-Dial hours.
6. **Zero-guilt string sweep** across all mockup copy ("missed" → "carried/cooling" etc.).
7. **Screen additions (blend):** Day gets Minimum Day line + If-Then chip + ghost stamps; Dusk Close rebuilt to 5-step contract; Sunday gets Reality Multiplier panel; Shield gets rung ladder visual 0–5 with sealed ceiling; Reminders get Care types + "Why now?"; You gets resistance budget + Voice preview.
8. **Accessibility:** focus-visible rings, 48dp targets, content descriptions, reduced-motion respected (kept), VoiceOver labels on Arc-Dial (steppers as accessible path).

## 6 · Mockup v3 acceptance list (what the owner will test)

1. Welcome explains the loop in one 6 s animation; Begin → manifest → setup flows with directional steps and Arc-Dial hours — sliders feel *good* (snap + haptic + bubble).
2. Swiping between all 5 tabs works with sliding indicator; edge-back works on Dusk/Dawn/Sunday/Focus/Reminders.
3. Pebble drags, tilts, docks magnetically, remembers position, and never triggers capture after a drag.
4. Day: Big Three with Main Front label + Minimum Day line; tally swipe right/left/down works; carry shows cooling grayscale; no red anywhere.
5. Dusk Close: full 5-step contract completable ≤ 90 s with pre-filled evidence; seal press-and-hold feels earned; tomorrow appears drafted.
6. Sunday: Reality Multiplier visible and editable; week seeds.
7. Shield: rung ladder 0–5 with ceiling; focus session inherits a track; block overlay shows one-breath pause.
8. Reminders: Care types with per-reminder control + Why-now line + escalation chain.
9. You: capabilities with payloads; resistance budget dashboard; soft mode relaxes motion.
10. No console errors; no animation jank on the ghost trail; reduced-motion honoured; footer/phone frame intact on mobile widths.

---

## 7 · Carry-forward items from owner feedback (do not lose)

- **Pebble "+" must stay lively/moveable and stay distinct from the Sparks capture button** (pebble = global capture; Sparks' own button = capture-as-spark directly). Both retained in v3; pebble gets magnetism + squash.
- Owner is "not fully satisfied" with overall layout — after v3 ships, run a specific tweak round: collect the exact screens/elements before touching IA again.
- English-only everywhere (UI strings, docs, commits).
