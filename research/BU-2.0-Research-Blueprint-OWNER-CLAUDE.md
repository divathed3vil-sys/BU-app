# BU 2.0: Research Blueprint

Prepared 3 Nov 2026. Rebuilding BU as an adaptive, fully offline life engine for Android, with Cactus Needle 3 running on the device.

## 1. The short version

- **Needle 3 is not a chatbot.** It is a tiny (8–29 MB) on-device model that does three jobs: pick app functions and fill their arguments, extract structured fields from messy text, and turn text into embeddings. That is exactly what a life app needs, as long as it is the app’s nervous system rather than its voice.
- **Needle understands, rules decide, templates speak.** Your offline choice means the guidance layer is a template and rules engine fed by structured facts, with Needle turning your words into those facts.
- **The invention is the closed loop:** Sense (usage, journal, plan) → Understand (Needle) → Decide (rules + on-device bandit) → Act (notify, nudge, block) → Learn (what actually worked).
- **Two signature rituals:** the Tomorrow Contract (nightly) and the Week Compass (Sundays). Both correct the planning fallacy using your own history.
- **Control is an Adaptive Firmness Ladder** with ceilings you set in advance. It escalates only inside limits you consented to, and backs off when it detects rebound or low mood.
- **Every Mindful feature is buildable.** Mindful is GPL-2.0, so copying its code makes BU GPL-2.0. Reimplement unless you are happy to open-source BU.
- **Top risks:** no official Flutter binding for Needle (C API only), fragile background services on Android, Play Store policy on Accessibility/VPN/exact alarms, and scope (65–70 screens before the loop is proven). Spike Needle first.
- **On “never before”:** I cannot verify that nothing similar exists. The defensible novelty is the combination: offline, closed-loop, contract-based escalation, reality-calibrated planning.

## 2. What Needle 3 really gives you

From Cactus’s model card and guides:

- One file, 8–29 MB depending on depth (2–20 layers). The engine is under 1 MB per platform. Android arm64 and armv7 engines ship with a C API (needle\_init, needle\_complete, needle\_embed). Apache-2.0.
- **Tool calls:** you declare functions; it returns the right calls with arguments filled in. Two requests give two ordered calls. An off-topic request returns an empty list instead of a guess.
- **Extraction:** declare a shape, hand over messy text, get typed fields back. Decoding is grammar-constrained, so output always parses. Classification works through enums.
- **Embeddings:** the same model returns vectors for local semantic search.
- **Confidence:** every response carries a calibrated score. Cactus documents routing on it as act, confirm or refuse.
- **Customisable:** LoRA fine-tuning on the frozen base, then a build step slices a smaller subnetwork (for example 6–8 layers) into a 4-bit file.
- **Whistle:** a 16.9 MB speech-to-text model in seven languages on the same engine, able to go from speech straight to tool calls.
- Tool schemas share the model’s context window. With more than five tools declared, Needle retrieves only the relevant ones per turn.

Limits to design around:

- It cannot write paragraphs of guidance, hold a conversation, or reason over long histories.
- Tiny models are weakest on nuance: sarcasm, mixed emotions, code-switching. If you will journal in Sinhala or Tamil rather than English, test that on day one; I could not confirm language coverage for Needle or Whistle.
- Extraction quality on real journal text is unproven. Plan to fine-tune on synthetic data and evaluate on your own entries.
- No official Flutter binding. Integrate through the C API via Kotlin JNI (recommended) or Dart FFI.

## 3. The Life Loop

- **Sense:** app usage events, unlock counts, focus sessions, notification responses, journal entries, plan completion, a sleep proxy (longest screen-off stretch), optional 3-tap mood/energy check-ins.
- **Understand (Needle):** journal text → structured record; typed or spoken commands → tool calls; embeddings → semantic memory.
- **Decide (deterministic):** a rule engine over derived metrics and your pre-approved policies, plus a contextual bandit that picks message and timing.
- **Act:** notifications, friction screens, limits, blocks, plan adjustments.
- **Learn:** every action logs an outcome (opened, dismissed, complied, bypassed within N minutes). The bandit updates, and a weekly calibration refreshes your estimates.

**Hard rule:** Needle may propose actions, but only the rule engine, under policies you sealed in advance, may restrict you. A small model’s mistake must never become a lockout.

## 4. Mindful parity map

Features from Mindful’s README, the Android mechanism that typically delivers them, and the BU twist. Read Mindful’s android/ folder before reimplementing; I have not audited its code.

- **Focus Mode** (Study/Work/Creative, countdown or stopwatch, session timeline). Foreground service, timer, session log. Twist: sessions attach to today’s Main Front and end with a 20-second “what did I do?” note that Needle extracts.
- **Screen time limits** (per app, groups, shared limits, short-form like Reels/Shorts, Invincible Mode). UsageStatsManager aggregation plus an enforcement overlay. Short-form detection usually needs an AccessibilityService reading view IDs, which break when apps update, so keep the rules in an updatable file. Twist: limits are rungs on the ladder, not fixed numbers.
- **Usage insights** (screen time, app usage, data). UsageStats plus network stats. Twist: show Drift (actual vs plan), not raw totals.
- **App and internet blocking, adult filter.** Mindful uses a local VPN to cut per-app internet, which is why its manifest declares INTERNET. A DNS blocklist inside the same VPN covers adult filtering.
- **Notification management** (batch, schedule, mute). NotificationListenerService plus digest windows. Twist: digests land at moments the bandit learns you respond to.
- **Bedtime mode** (pause apps, Do Not Disturb). Notification policy access plus enforcement. Twist: it starts when you seal the Night Contract, and winds down before that.
- **Parental controls.** Skip the feature, borrow the tamper resistance (biometric lock, settings locked while a limit is active) for your own Contract Lock.
- **Privacy-first, offline, no ads.** Keep as a founding principle. It also fits your offline choice.

**Licence:** Mindful is GPL-2.0. Reuse of its code obliges BU to be GPL-2.0 too.

**Distribution:** Accessibility and VPN APIs are scrutinised on Google Play and need prominent disclosure and declarations. Exact-alarm rules are also restricted: the install-time USE\_EXACT\_ALARM permission is meant for alarm and calendar-type apps, otherwise the user must grant SCHEDULE\_EXACT\_ALARM. The sources I found on these policies are older, so re-check current Play policy before planning a Play release. Mindful itself ships on both Play and GitHub releases, which is a fallback path.

## 5. Journaling system

**Capture:** quick text, voice (if Whistle covers your language), Stoic prompts, and a 3-tap check-in (mood, energy, one word).

**On save** (background worker, a second or two, writing is never blocked):

- **Extraction** into a JournalRecord: mood and energy (enums), emotions, themes, people, commitments (what + when, as written), worries, wins, which of your eight virtues it touched, trigger (app or situation), gratitude flag.
- **Embedding** stored for semantic search and “days like this one” recall.
- **Promise detection:** a sentence like “I’ll call X on Friday” becomes a create\_reminder tool call, routed by confidence: act silently if high, show a confirm chip if medium, ignore if low.
- **Care Graph update:** people, projects and worries that recur in three or more entries are proposed as things you care about. You approve them.
- **Derived stats:** mood trend, energy by hour, theme frequency, virtue balance.

The point: the journal is not an archive. It feeds the planner (carry-overs, worries become pre-mortems) and the notifier.

## 6. Planning inventions

### The Tomorrow Contract (nightly, about 6 minutes)

Built on the Stoic evening review, implementation intentions (Gollwitzer and Sheeran’s meta-analysis found medium-to-large effects), Masicampo and Baumeister (2011) showing that a concrete plan reduces the pull of unfinished goals, and the planning fallacy.

- **Review (60 s):** an auto-filled Day Receipt: planned vs done, drift, biggest time sinks, mood, plus one reflective prompt.
- **Offload (60 s):** brain-dump by text or voice. Needle extracts tasks, worries and commitments. Anything unfinished gets parked with a when-I-will.
- **Triage carry-overs:** each unfinished task must become Do (reschedule), Shrink (smaller version) or Kill. A task moved three times forces a decision.
- **Commit:** exactly one Main Front, up to two Supports, and a Minimum Viable Day: the one thing that makes tomorrow a success if everything else collapses.
- **Pre-mortem and if-then:** “What will most likely derail tomorrow?” Needle classifies the obstacle (phone, fatigue, interruption, ambiguity, people) and the template engine proposes an if-then plan you accept or edit.
- **Seal:** set the wake plan, the first action and tomorrow’s firmness ceiling, then press and hold to sign. That is the commitment device. Wind-down begins.

### The Week Compass (Sundays, about 15 minutes)

- **Week in review** by area, with a **Reality Multiplier**: your actual-to-estimated time ratio per task type over the last four weeks. If a type of task usually takes 1.5× your estimate, new estimates are scaled automatically.
- **Fronts:** because you run several parallel tracks, each day gets one Main Front so tracks take turns instead of competing. Cap at three Fronts per week by default.
- **Capacity map:** fixed commitments, energy-weighted blocks (from your energy-by-hour data), and a 20% buffer.
- **Rules of the week:** three if-then rules and a firmness ceiling per day.
- **Seed the daily contracts:** Monday to Sunday Main Fronts are prefilled, so each night only adjusts.
- **Letter to Friday-you:** one sentence, shown on Friday.

**Data model:** Front → Outcome → Task → Block. Tasks carry estimate, adjusted estimate, moved\_count and energy requirement.

## 7. Notification engine: the Care Graph

Every Care Item has a type: **Anchor** (fixed time), **Contextual** (when I open X, when I arrive), **Relational** (a person), **Value** (a virtue), **Deadline**, **Habit**.

- **Time-critical delivery:** AlarmManager exact alarms, rescheduled after reboot. Everything else uses WorkManager flexible windows.
- **Notification budget:** a daily cap on interruptive notifications (default about five); the rest go into digests. Nothing during sleep or focus unless the ladder is high and you approved it.
- **Contextual bandit (Thompson sampling, on-device)** chooses time window × template × channel, rewarded when you act within 30 minutes. Research on just-in-time adaptive interventions supports this approach: a small feasibility study of reinforcement-learning-timed reminders saw 83% of adaptive-moment reminders answered within 50 minutes.
- **Habituation:** bandit research (the ROGUE framework) models effectiveness dropping with repeated use and recovering with rest. So rotate templates and give each message type rest days.
- **Escalating form:** an unanswered critical item changes shape (notification → full-screen → friction gate at your next app open) rather than just repeating.
- **Explainable:** every notification has a “Why now?” line.

## 8. Behaviour analysis and the Adaptive Firmness Ladder

**Metrics:** Drift Index (planned vs actual Main Front minutes), Distraction Load (short-form minutes + unlocks + late-night use), Recovery (sleep proxy and wake time vs plan), Mood trend, Contract compliance, Bypass rate. A rolling three-day **Slip Score** combines them with decay.

**The ladder:**

- **L0 Observe:** data only.
- **L1 Nudge:** a notification or reflective prompt.
- **L2 Pause:** one sec-style friction: 5–10 seconds, a breath, “What are you here for?”, and an easy exit button.
- **L3 Soft limit:** blocked after the limit; a 5-minute override needs a typed reason (reasons become data).
- **L4 Hard limit:** blocked until a set time; override only after a cooldown.
- **L5 Contract Lock:** available only for rules you sealed the night before; no override except an emergency path.

**Rules:** escalate one rung when the Slip Score crosses its threshold twice in 48 hours. De-escalate after three compliant days. Never exceed the day’s sealed ceiling. An always-allowed list (calls, maps, banking, emergency contacts) is never touched.

**Safety valves:**

- **Low-mood guard:** if mood trends down for several days, cap at L2 and switch to supportive templates. If entries suggest acute distress, drop restrictions, tone everything down, and invite contact with a trusted person or a local helpline. Treat Needle as one signal here, never the only one.
- **Reactance guard:** a rising bypass rate triggers de-escalation and a check-in prompt. Large-scale one sec data shows people take breaks from interventions and rebound quickly, so heavy-handed control gets uninstalled.

**Evidence:** the 2023 PNAS field study of one sec (280 people, six weeks) found users closed the target app after 36% of interventions and attempted to open target apps 37% less by week six; its three ingredients were friction, a deliberation prompt, and an option to dismiss. The vendor’s marketing quotes 57%, so prefer the paper’s numbers. Nudge effects in general are smaller than early headlines suggested.

## 9. A guidance voice without an LLM

Message = facts + situation + tone + firmness level + virtue.

- **Facts** come from metrics: “You planned 90 minutes for your Main Front and have done 25.”
- **Template bank:** roughly 400–600 templates tagged by situation, firmness, virtue and tone. A combinatorial grammar (opening × fact line × reframe × action prompt) yields thousands of variants, which defeats habituation. Stoic lines come from public-domain translations (Long’s Marcus Aurelius, Gummere’s Seneca, Epictetus).
- **Selection:** Needle embeddings match the current situation against each template’s “when” description. The bandit picks among the top few by what you actually respond to.
- **Authoring:** use Claude at build time to draft and test the bank, then review tone by hand, especially for L3 and above. Runtime stays offline.
- **Voice principles:** specific, not generic; asks before it tells; never shames (shame drives avoidance); autonomy-supportive (“you chose this contract last night”), following self-determination theory.

## 10. UX and psychology

**What addictive products do, and how BU inverts it:**

- **Variable reward** (Instagram’s feed, pull-to-refresh as slot machine) → variable *insight*: a daily card from your own data showing a pattern you had not noticed. Finite, never endless.
- **Infinite scroll, no stopping cues** → everything has an end. A Day Closed screen finishes the Contract.
- **Streaks and loss aversion** (Snapchat, Duolingo) → forgiving consistency: weekly rate and grace days, so one miss never erases progress.
- **Social proof and FOMO** → none. At most, an optional export to one accountability partner.
- **Red badges** → no badges; calm, sanctioned notifications only.
- **Gentle gamification** (Forest’s tree that dies is a light commitment device; Finch ties care to a pet) → your eight virtues as a constellation that fills in over time: identity progress, not points.
- **Planning apps:** Sunsama’s guided daily ritual is the closest cousin to the Night Contract. Motion and Reclaim auto-schedule on cloud calendars. BU differs by being offline, behaviour-coupled and contract-based.
- **Blockers:** Opal, one sec and Mindful are the reference points for the ladder.
- **Journaling:** Daylio is the benchmark for fast mood logging; Rosebud and Reflectly use cloud AI. BU’s angle is offline structure that feeds the planner.

**Frameworks to apply:** Fogg’s B=MAP (make the ritual easy, anchor prompts to existing routines); the Hooked model used ethically, where the “investment” is your growing journal and Care Graph; self-determination theory (autonomy via ceilings, competence via visible progress, relatedness via the optional partner); the peak-end rule (end the Night ritual on a warm seal moment); a warm low-luminance dusk theme at night and a cooler, brighter one in the morning.

**Bookend UX:** two deliberate touchpoints. Morning Launch (60 seconds: Main Front, first action, if-then) and the Night Contract (6 minutes). Everything else is ambient: widgets, lock-screen, notifications. **Success metric:** minutes in BU per day should go *down* over time.

**Screen scope:** prune the existing 65–70 screen design to a core of about nine: Today, Night Contract, Week Compass, Journal, Care Graph, Focus, Limits and Ladder, Insights (Drift), Settings and Contracts. Map the existing design system onto these and defer the rest.

## 11. Architecture

- **Flutter UI (Dart):** screens, state management, planner UI, charts.
- **Dart domain:** plan and contract logic, Care Graph, template engine, rule engine. Pure Dart, unit-testable.
- **Kotlin native services:** usage collector (WorkManager + UsageEvents), enforcement (overlay, Accessibility, VPN), notification scheduler (AlarmManager + WorkManager), notification listener, boot receiver, and the Needle host (JNI).
- **Intelligence:** libneedle for android-arm64 plus the needle model file, and optionally Whistle. Load lazily, run the batch, release memory.
- **Data:** encrypted SQLite (SQLCipher) with an append-only event log (usage, actions, outcomes) plus derived tables. Embeddings are stored as float16 BLOBs; brute-force cosine similarity is fine up to tens of thousands of entries.
- **Key rule:** native services must work without the Flutter engine alive. Enforcement and reminders cannot depend on the UI. Share state through the database, not platform channels.

**Needle integration spec:**

- A tools file with roughly 25 tools, one per action, named the way you would say them: add\_task, schedule\_block, log\_mood, create\_reminder, start\_focus, set\_app\_limit, snooze, reschedule\_task, log\_habit, create\_care\_item, and so on.
- Extraction schemas for JournalRecord and ObstacleClass.
- Confidence routing everywhere: act, confirm, refuse.
- Fine-tune a 6–8 layer subnetwork on a few thousand synthetic journal entries and commands (generated offline with Claude), then evaluate on a hand-labelled set of your real entries.
- Keep rule-based fallbacks for low-confidence cases.

**Android realities:**

- Phone makers’ battery managers kill background services. Use a foreground service only while focus or enforcement is active, and add an onboarding step for battery-optimisation exemption.
- Request permissions progressively, only when the feature is first switched on: Usage Access, Notifications, Overlay, Accessibility, VPN, Do Not Disturb access, Exact Alarm, Battery exemption.
- Record event logs so you can replay weeks of simulated behaviour to tune the ladder and bandit before living with them.

## 12. Roadmap

- **Phase 0, Needle spike (days):** a throwaway Kotlin app on your real phone. Load the model, run 50 journal extractions, measure latency, RAM and accuracy, test embeddings, choose layer depth. Go or no-go.
- **Phase 1, vertical slice:** Night Contract, Today screen, Care Items with exact reminders, journal capture with rule-based extraction.
- **Phase 2, Mindful core:** usage collection, limits, focus, bedtime, notification digests.
- **Phase 3, Needle in:** extraction, embeddings, promise detection, quick-add tool calls, voice.
- **Phase 4, the loop:** Slip Score, ladder, bandit, template engine, Week Compass.
- **Phase 5, polish:** widgets, insights, theming, backup and export.

Why this order: validating the loop on yourself daily comes before building 65 screens.

## 13. Risks and guardrails

- **Over-control backfires.** Ceilings, always-allowed list, reactance guard.
- **Small-model errors.** Needle never restricts; confidence routing; human-confirmable actions.
- **Wellbeing.** Low-mood guard; never present the app as treatment; supportive tone over firm tone when mood is low.
- **Privacy.** Everything stays on the device, encrypted, with export and delete. This is also your best differentiator.
- **Policy and licence.** Play policy review, GPL decision made before writing code.
- **Scope creep.** Core nine screens first.

## 14. Unverified items and next steps

Not verified: Needle’s extraction quality on real journal text; whether Whistle or Needle cover the language you journal in; current Play policy details; Mindful’s exact enforcement code.

Three things that would sharpen the next pass: the language you will journal in, your phone model (for the Needle spike), and whether this is for personal use or a public release (it changes the GPL and Play decisions).

## Sources

- Cactus Needle 3 model card (Hugging Face, Cactus-Compute/needle3) and Cactus guide “What Devices Are Supported on Needle”
- Mindful README (GitHub, akaMrNagar/Mindful)
- Grüning, Riedel and Lorenz-Spreen (2023), Directing smartphone use through the self-nudge app one sec, PNAS 120(8)
- Haag et al. 2024 and Lei, Tewari and Murphy (2017) on JITAIs and contextual bandits; Mintz et al. (2020) ROGUE bandits; a 2021 IJERPH feasibility study on reinforcement-learning reminders
- Gollwitzer and Sheeran (2006); Masicampo and Baumeister (2011)
- Android developer documentation and press coverage on exact alarms and Accessibility policy (older; re-check)
