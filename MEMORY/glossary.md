# MEMORY · Glossary

> The vocabulary of BU. One system, no synonyms. Use these terms exactly.

---

## The Fire (the central metaphor)

| Term | Meaning |
|---|---|
| **Fire** | The central metaphor. The literal functional state of the app. Lit = sharp, present, watching. Dead = dark, silent. |
| **Fire state** | One of five: Blazing / Burning / Flickering / Embers / Out. Computed from 7-day rolling evidence (Full) or marked manually (Lite). |
| **Blazing** | Solid, bright, full. You are becoming who you said you'd be. |
| **Burning** | Steady, warm. You are holding course. |
| **Flickering** | Slight wobble, dimmer. You are slipping, slowly. |
| **Embers** | Dim, half-empty. You have stopped. |
| **Out** | Dark, thin. You are losing ground. Tap to relight. |
| **Gorgeous** | The state above Blazing. Halo + gold + floating embers. Earned only through 30 honest days. Photo-proof gated. |
| **Growth arc** | The fire grows over months and years — never ending, procedural, milestone-driven. Spark → Flame → Hearth → Forge → Constellation → Galaxy. |
| **Tending** | The act of doing the Play. Marks the fire brighter. |
| **Drift** | Opening a distracting app (e.g. YouTube) during a sealed Normal Focus session. Triggers the intervention ladder. |
| **Relight** | The act of tapping the fire after it has gone Out, doing the play, and starting a new streak. |

## The Tiers

| Term | Meaning |
|---|---|
| **BU Lite** | The default tier. Plan/execute/focus/journal, no model, no Watcher. The fire is dim (manual). |
| **BU Full** | The opt-in tier. User downloads Gemma 3 1B → Watcher activates → Forge intelligence comes alive. The fire is honest. |
| **Opt-in moment** | The single decision point where the user crosses from Lite to Full. Triggered by a "Light the fire" prompt. |

## The Five Layers

| Term | Meaning |
|---|---|
| **Flame** | UI layer (Jetpack Compose). The fire ring, the Play, journaling surfaces, the Life Log browser. |
| **Ember** | State + memory layer. ObjectBox 4.0 with HNSW vector index. Every action is an ember. |
| **Watcher** | Android APIs layer. UsageStats, FGS, NotificationListener, Accessibility (opt-in). |
| **Forge** | Intelligence layer. Gemma 3 1B + Qwen 0.6B + RAG over embers. |
| **Bedrock** | The 3 invariants + persistence + security. |

## The Daily Cycle

| Term | Meaning |
|---|---|
| **Morning Spark** | 30-second morning ritual. Open BU, see the fire, tap, get the Play, commit, close. |
| **Tending** | The day itself. The Watcher watches (Full) or doesn't (Lite). |
| **Evening Close** | 60-second evening ritual. "Did you do it?" Three taps: yes/partial/no. One optional line of reflection. |
| **Sunday Review** | 5-minute Sunday ritual. The Forge speaks: grew / withered / pattern. You plant one intention. |

## The Play

| Term | Meaning |
|---|---|
| **Play** | The one-sentence action that matters today. Lite: you write it. Full: Forge computes it. |
| **Sealed Play** | A play you've committed to for today. The Watcher watches for drift during sealed plays (Full only). |

## The Intervention System

| Term | Meaning |
|---|---|
| **Normal Focus** | The default focus mode. You seal a play. The Watcher watches for drift. The intervention ladder activates if you drift. Educational YouTube is exempt via smart context. |
| **Hard Focus** | The nuclear option. Whitelist (calls + 2-3 apps). Everything else blocked for the session. |
| **Intervention ladder** | 3-stage escalation: soft nudge (0-20 min drift) → aggressive nudge (20-30 min) → overlay + 30-min block (30-60 min). Only inside sealed Normal Focus. |
| **Smart context** | AccessibilityService reads the YT video title → Gemma 3 1B classifies educational vs not → if educational + user confirms → no punishment. |
| **Confirm chip** | The UI prompt during smart context. "Looks like you're studying. Continue without intervention?" |

## The Memory

| Term | Meaning |
|---|---|
| **Ember** (data) | Any action/event/entry stored in ObjectBox with an embedding. Examples: a tending, a journal entry, a drift event, a play outcome. |
| **Life Log** | The complete record of the user's day. Auto (Watcher) + Manual (user-entered off-phone time). |
| **Auto entry** | A Life Log entry the Watcher captured (app foreground event with timestamp + duration). |
| **Manual entry** | A Life Log entry the user authored (title + description + duration for off-phone time). |
| **Evidence grade** | One of: OBSERVED / INFERRED / USER_CONFIRMED / OUTCOME_CONFIRMED. Preserved on every claim. |
| **Promise** | A commitment extracted from a journal entry ("I'll call Aisha Friday") → confirm chip → reminder created. |
| **Pattern** | A behavior the Forge has detected (e.g. "you skip study every Thursday"). Surfaced at Sunday Review. |
| **Causal chain** | A correlation the Forge has detected (e.g. "when you tend Calculus in the morning, you tend Aisha that evening 78% of the time"). Surfaced only when confidence is high. |
| **Identity streak** | A statement of becoming, not a count: "You've shown up 4 of last 7 days. That's who you are now." Never "don't break the chain." |

## The Journaling

| Term | Meaning |
|---|---|
| **Blank page** | Swipe-right journaling surface. Pure text. No template. |
| **Voice journal** | Swipe-left journaling surface. Hold to record, 30 sec max. Whisper-tiny transcribes on-device. |
| **Free-draw canvas** | Swipe-up journaling surface. Excalidraw-style. Pen, shapes, arrows, text. Stored as SVG + embedding. |

## The Proof System

| Term | Meaning |
|---|---|
| **Passive proof** | The Watcher sees you open the study app for ≥20 min during the play window. Confidence 0.7. |
| **Time proof** | You tap "done" and the play had a timer running. Confidence 0.85. |
| **Photo proof** | The Forge asks "show me?" → you snap a photo → MobileNet v3 embeds it (1280-dim) → compared to historical embeddings for novelty → if novel, marked OUTCOME_CONFIRMED. Confidence 0.95. Raw photo deleted after embedding. |
| **Outcome proof** | A measurable result (exam score, assignment grade, fitness PR) entered manually, retroactively tags related plays as OUTCOME_CONFIRMED. |
| **Honest tending** | A tending that passes at least one proof layer. Counts toward the 30-day Gorgeous state. |
| **Cheating the app** | A tending that fails proof (e.g. tapping done without the Watcher seeing anything). Does NOT count toward Gorgeous. |

## The Three Invariants (Bedrock)

| Term | Meaning |
|---|---|
| **Tap survives** | Once BU acknowledges a user action, it survives process death, reboot, and app kill. Write-ahead-logged persistence. |
| **Fire honest** | Evidence grades preserved on every claim. A model guess never silently becomes a fact. |
| **You own your embers** | Full Markdown export anytime. No account. No cloud. No lock-in. |

## The Forge Components

| Term | Meaning |
|---|---|
| **Forge** | The intelligence layer. Reads embers, computes fire state, chooses the Play, detects patterns, classifies drift. |
| **Chooser** | The Forge subsystem that picks today's Play from the ember history. |
| **Classifier** | The Forge subsystem that classifies drift (e.g. educational vs non-educational YouTube). |
| **RAG** | Retrieval-Augmented Generation. The Forge embeds a query, retrieves top-K relevant embers from ObjectBox HNSW, uses them as context for Gemma 3 1B. |

## Terms we DON'T use (killed vocabulary)

These terms are from v4/v5 and are explicitly forbidden. Don't use them. Use the v6 equivalent.

| Forbidden | Use instead |
|---|---|
| Bloom (v5) | Play |
| Garden (v5) | (no equivalent — the metaphor is dead) |
| Soil (v5) | Ember |
| Keeper (v5) | Intervention ladder |
| Voice (v5) | Forge |
| Stone (v5) | Manual Life Log entry |
| Plant / Bird / Stream / Wildflower / Season (v5) | (no equivalent — no kind taxonomy in v6) |
| LOOM (v4) | (no equivalent — no separate interaction system) |
| NOW / PLAN / JOURNAL / ATLAS / SHIELD (v4) | (no equivalent — single fire ring surface) |
| BTK (v4) | Bedrock (the invariants layer) |
| Main Front / Big Three / Minimum Day (v4) | The Play |
| Dusk Close (v4) | Evening Close |
| Dawn Push (v4) | Morning Spark |
| Week Compass (v4) | Sunday Review |
| Slip Score / Drift Index (v4) | Fire state |
| Adaptive Firmness Ladder L0-L5 (v4) | Intervention ladder (3 stages) |
| Pebble / Arc-Dial / Tally Trio (v4) | (no equivalent — gestures are invisible, not branded) |
| Track / Spark / Block / Care Item (v4) | (no equivalent — no kind taxonomy) |
