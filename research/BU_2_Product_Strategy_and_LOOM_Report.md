# BU 2.0 --- Product Strategy, Research & LOOM UI/UX Specification

**Document type:** Product strategy and preliminary product requirements
report\
**Product:** BU 2.0\
**Proposed category:** Personal Life Operating System\
**Proposed interaction system:** LOOM --- Temporal Knowledge Interface\
**Platform strategy:** Android-first, local-first, desktop-compatible\
**AI runtime:** Needle 3, subject to device benchmarking and integration
validation\
**Status:** Strategic proposal; technical and market assumptions require
validation

------------------------------------------------------------------------

## Executive summary

BU 2.0 should not be developed as another task manager, habit tracker,
journal, or AI chatbot. Its ambition should be to become a **Personal
Life Operating System**: one coherent environment for thinking,
remembering, planning, acting, documenting, and learning from
experience.

The product combines five pillars:

1.  **Knowledge:** an Obsidian-grade personal knowledge environment.
2.  **Memory:** an automatically maintained, evidence-linked personal
    chronicle.
3.  **Planning:** adaptive management of goals, projects, tasks,
    schedules, and commitments.
4.  **Behaviour:** a personalised discipline and attention-management
    engine.
5.  **Intelligence:** an offline AI orchestration layer connecting the
    other systems.

The central product loop is:

> Intention → Understand → Organise → Plan → Act → Observe → Reflect →
> Adapt

The proposed LOOM interaction model treats time, meaning, and attention
as connected dimensions of one information environment. Rather than
forcing users to navigate between isolated notes, tasks, calendars,
habits, and journals, BU should present different perspectives over one
connected data model.

The product should be compelling because it understands the user's
context and reduces friction---not because it maximises screen time,
notifications, streak anxiety, or compulsive engagement.

------------------------------------------------------------------------

# 1. Product vision and positioning

## 1.1 Product definition

**BU is a local-first Personal Life Operating System that helps people
understand, organise, execute, and learn from their lives.**

It should provide:

-   A complete personal knowledge-management environment.
-   A structured journal that can be maintained automatically.
-   Goal, project, task, and calendar management.
-   Adaptive planning based on actual capacity and historical execution.
-   Focus and attention-management workflows.
-   Contextual reminders and carefully scoped Android automation.
-   An offline AI assistant for natural-language capture, extraction,
    retrieval, and tool orchestration.
-   A portable data model that does not lock the user's knowledge into a
    proprietary service.

## 1.2 Product principles

1.  **One connected system:** notes, tasks, goals, projects, events, and
    journal entries should be related objects, not isolated silos.
2.  **Local-first ownership:** core features must work without an
    account, cloud service, or internet connection.
3.  **Evidence before inference:** observed, inferred, and
    user-confirmed information must remain distinguishable.
4.  **Autonomy over coercion:** interventions must be understandable,
    configurable, reversible, and user-controlled.
5.  **Less management overhead:** the system should reduce the time
    spent maintaining the system itself.
6.  **Portable knowledge:** Markdown and documented export formats
    should preserve user ownership.
7.  **Deterministic execution:** schedules, deadlines, permissions, and
    high-impact actions should be handled by ordinary application logic,
    not improvised by an LLM.
8.  **Useful engagement:** make the product worth returning to because
    it creates clarity and value, not because it exploits attention.

## 1.3 What BU is not

BU should not become:

-   A conventional task manager with an AI chat window.
-   An Obsidian clone with a habit tracker attached.
-   A gamified streak application that punishes missed days.
-   An autonomous device-control agent with unrestricted permissions.
-   A social feed or infinite-scroll productivity platform.
-   A black-box system that silently edits a user's plans or personal
    history.

------------------------------------------------------------------------

# 2. Assessment of the existing research direction

The existing research's strongest conceptual principle is the closed
loop:

**Sense → Understand → Decide → Act → Learn.**

Its strongest proposed concepts include:

-   Tomorrow Contract: a deliberate end-of-day planning ritual.
-   Week Compass: a higher-level weekly planning and review mechanism.
-   Care Graph: a network of meaningful relationships between life
    objects.
-   Automatic journaling: turning relevant activity and events into a
    structured record.
-   Adaptive intervention: changing the type and intensity of support
    based on the user's context.
-   Reality-based planning: using actual task durations and completion
    history to improve future estimates.

These are promising foundations. However, several areas need expansion
or restraint.

## 2.1 Gaps and risks

### Activity data can be misleading

Opening a mathematics application does not prove that the user studied
mathematics. Screen time is not equivalent to meaningful work.

BU must distinguish:

-   **Observed:** an application was opened or a focus timer was
    started.
-   **Inferred:** activity may have been related to studying.
-   **User-confirmed:** the user explicitly reported completing a study
    session.
-   **Outcome-confirmed:** a defined deliverable or result was recorded.

The distinction should be preserved in analytics and journal provenance.

### Adaptive machine learning is premature for the first release

A contextual bandit or similar adaptive model requires meaningful
observations, a defensible reward signal, sufficient sample size, and
safeguards against optimising notification clicks instead of useful
outcomes.

Start with transparent rules. Later, learn which interventions improve
task completion, recovery, and planning accuracy.

### Emotion inference requires restraint

Do not let an AI model diagnose a user's mood or mental state from
ambiguous journal text. Explicit check-ins and user-defined preferences
should govern low-mood or reduced-capacity behaviour.

### The knowledge environment is underdeveloped

Markdown storage and semantic search alone do not replace Obsidian. BU
needs a full editor, backlinks, properties, templates, graph navigation,
Canvas, structured databases, search, attachments, version history,
import/export, and an extension strategy.

### The product needs a unified information model

A task, journal entry, goal, project, person, idea, and event should be
linkable through typed relationships. A universal object model is more
important than adding more disconnected screens.

------------------------------------------------------------------------

# 3. Product research: patterns to learn from existing applications

BU should study successful products as examples of interaction patterns,
not copy their entire interfaces or business models.

## 3.1 Instagram --- attention and engagement

Useful mechanisms to study:

-   **Low interaction cost:** a small gesture produces a new experience.
-   **Personal relevance:** content is selected using observed interests
    and behaviour.
-   **Variable rewards:** users encounter a changing stream of content
    and feedback.
-   **Social reinforcement:** recognition and responses create reasons
    to return.
-   **Accumulated investment:** saved items, relationships, and history
    create personal value.

BU should borrow the useful interaction principles while rejecting
endless engagement loops.

  -----------------------------------------------------------------------
  Existing mechanism                  BU alternative
  ----------------------------------- -----------------------------------
  Endless feed                        Finite daily and weekly experiences

  Variable content discovery          Relevant, evidence-grounded daily
                                      insight

  Social validation                   Private evidence of personal
                                      progress

  Frequent notifications              Contextual reminders with a
                                      notification budget

  Streak anxiety                      Forgiving continuity and recovery

  Personalised recommendations        Personalised next actions

  Saved content                       Accumulating personal knowledge

  Infinite engagement                 A satisfying completion state
  -----------------------------------------------------------------------

**Design principle:** BU should be compelling because it understands the
user's context better over time, not because it manufactures reasons to
open the app repeatedly.

## 3.2 Obsidian --- knowledge ownership

Patterns to study:

-   Local Markdown files.
-   Wiki links and backlinks.
-   Properties and metadata.
-   Graph view.
-   Canvas.
-   Daily notes and templates.
-   Search and extensibility.
-   User control over the knowledge base.

BU must match the core knowledge workflows before claiming to replace
Obsidian.

## 3.3 Sunsama --- intentional planning

Patterns to study:

-   Guided daily planning.
-   Workload review.
-   Timeboxing.
-   Shutdown rituals.
-   Plan review and user approval.

BU should use guided planning without forcing the user through a lengthy
ritual every time.

## 3.4 Day One --- journaling

Patterns to study:

-   Writing-first interaction.
-   Rich entries and attachments.
-   Audio and media capture.
-   Contextual metadata.
-   A pleasant, low-friction capture experience.

BU must preserve the distinction between a personal reflection and an
automatically generated activity record.

## 3.5 Structured --- temporal visualisation

Patterns to study:

-   A clear daily timeline.
-   Tasks represented in time.
-   Easy movement between commitments.
-   Visual distinction between current and upcoming work.

BU should make planned time, actual time, available capacity, and
recovery periods visually distinguishable.

## 3.6 Opal and one sec --- attention management

Patterns to study:

-   Scheduled focus sessions.
-   User-selected restrictions.
-   Contextual friction before opening distracting apps.
-   A deliberate pause that gives the user a choice.

A field study of one sec reported reduced attempts to open target
applications and a substantial share of attempted openings being
dismissed. This supports testing deliberate interruption as an
intervention, not assuming that aggressive blocking is always better.

## 3.7 Duolingo --- continuity and progress

Patterns to study:

-   Visible progress.
-   Clear recurring commitments.
-   Learning rituals.
-   Reminders and continuity cues.

BU should avoid brittle streak mechanics. A missed day should trigger
recovery and replanning, not shame.

## 3.8 Research interpretation

These products demonstrate useful design patterns, but their success
does not prove that copying an individual feature will improve BU. Every
adopted mechanism should be tested against BU's own outcomes: task
completion, planning accuracy, recovery, retrieval quality, user
autonomy, and reduced administration overhead.

------------------------------------------------------------------------

# 4. LOOM --- the proposed Temporal Knowledge Interface

## 4.1 Core concept

**LOOM is a proposed interaction system in which time, meaning, and
attention are connected dimensions of one personal information
environment.**

The user's life is not naturally divided into separate folders called
Tasks, Notes, Habits, Projects, and Goals. These objects overlap.

A journal entry may contain a commitment. That commitment belongs to a
project. The project supports a goal. The goal shapes a schedule. The
schedule produces activity. The activity becomes part of the journal.

LOOM should let the user move between these perspectives without
duplicating the underlying information.

## 4.2 Three dimensions

### Time

When something was planned, scheduled, performed, delayed, or completed.

### Meaning

What an object relates to: goals, projects, notes, people, decisions,
concepts, and events.

### Attention

What matters now, what deserves focus, and what should be deferred.

These dimensions should be visible across the product rather than
isolated in separate modules.

## 4.3 Four primary environments

### NOW --- the living cockpit

Purpose: help the user understand and act on the present.

Contents:

-   Main Front: the most important current commitment.
-   Current or next task.
-   Remaining-day timeline.
-   Upcoming commitments.
-   Schedule drift.
-   Relevant contextual reminders.
-   A small number of useful insights.

The Main Front should dominate visually. Avoid overwhelming the user
with charts, habit rings, and long lists of incomplete tasks.

### PLAN --- the temporal workspace

Purpose: organise commitments across time.

Views:

-   Day timeline.
-   Three-day overview.
-   Week Compass.
-   Month overview.
-   Project timeline.

Visual distinctions:

-   Planned time.
-   Actual recorded time.
-   Available capacity.
-   Unscheduled commitments.
-   Breaks and recovery periods.

Tasks should move between time slots without creating duplicates. The
system should warn about overloaded schedules rather than silently
accepting impossible plans.

### JOURNAL --- the living chronicle

Purpose: provide both an automatic record and a genuine writing
environment.

Two modes:

1.  **Chronicle:** chronological entries, activity records, events,
    reflections, and summaries.
2.  **Deep Write:** a distraction-free writing environment.

Required capabilities:

-   Markdown-compatible editing.
-   Rich text editing.
-   Headings and nested lists.
-   Tables and code blocks.
-   Images, attachments, and audio.
-   Internal links and backlinks.
-   Embeds and transclusions.
-   Tags and properties.
-   Block references.
-   Templates.
-   Search.
-   Version history.

Automatically generated text must remain editable. AI-generated drafts
must never silently overwrite original reflections.

### ATLAS --- the personal knowledge universe

Purpose: explore and organise the user's accumulated knowledge.

Contents:

-   Knowledge base.
-   Projects.
-   Long-term goals.
-   Research.
-   People and relationships.
-   Ideas.
-   Decisions.
-   References.
-   Important life events.

Three interchangeable views:

  View       Purpose
  ---------- ----------------------------------------------------
  Graph      Discover relationships and connections
  Canvas     Organise information spatially
  Database   Filter, sort, group, and manage structured objects

The graph must support contextual exploration. Opening a project should
reveal its relevant notes, goals, tasks, and events rather than dumping
thousands of unrelated nodes onto the screen.

## 4.4 Universal capture

A persistent capture action should be available throughout the
application.

Example input:

> I need to research how quantum computers perform error correction.
> Also, remind me to study it this weekend.

Needle should identify the distinct objects:

-   Knowledge note: Quantum error correction.
-   Task or commitment: Study quantum error correction.
-   Time constraint: This weekend.
-   Relationship: Link to quantum computing research.

The interface should present a structured preview with Accept and Edit
actions. Ambiguous dates, priorities, or relationships should be
clarified rather than invented.

## 4.5 LOOM interaction vocabulary

  Interaction   Meaning
  ------------- --------------------------------------------------------
  Capture       Turn a thought into structured information
  Promote       Turn a thought or note into an actionable commitment
  Link          Connect two life objects
  Fold time     Move between daily, weekly, and long-term perspectives
  Focus lens    Expand an object into its execution environment
  Reconcile     Compare intentions with actual activity
  Close day     Finish the day's record and prepare tomorrow

These concepts should behave consistently across all environments.

------------------------------------------------------------------------

# 5. Obsidian replacement strategy: the Personal Knowledge Management Engine

## 5.1 Required feature parity

  Capability         BU implementation
  ------------------ ------------------------------------------
  Markdown editor    Native Markdown-compatible editor
  Wiki links         `[[Note Name]]`
  Backlinks          Automatically indexed references
  Block references   Links to individual paragraphs or blocks
  Graph view         Interactive relationship graph
  Canvas             Infinite spatial workspace
  Databases          Filterable structured collections
  Templates          Reusable note and workflow templates
  Daily notes        Automatic chronological notes
  Properties         YAML frontmatter and typed metadata
  Search             Full-text and semantic search
  Command palette    Universal keyboard and action search
  Attachments        Images, PDFs, audio, and other documents
  Version history    Local snapshots and recovery
  Import/export      Existing Obsidian vault compatibility
  Extensions         Documented plugin and automation API

## 5.2 Universal object model

Every important item should be represented as an object with a stable
identifier.

Initial object types:

-   Note.
-   Journal entry.
-   Goal.
-   Project.
-   Task.
-   Event.
-   Focus session.
-   Habit.
-   Person.
-   Idea.
-   Decision.
-   Reference.
-   Automation rule.
-   Activity event.

Relationships should be typed, for example:

-   `supports`
-   `belongs_to`
-   `blocks`
-   `references`
-   `derived_from`
-   `scheduled_for`
-   `related_to`
-   `completed_by`
-   `involves`

A physics concept note could connect to a learning goal, a project, a
study session, a journal reflection, a task, and an examination date.

## 5.3 Markdown compatibility

Markdown should remain a first-class, readable content format.

Example:

``` markdown
---
id: bu_0193
type: concept
created: 2026-11-03
tags:
  - physics
  - quantum-computing
---

# Quantum Error Correction

Quantum computers use error correction
to protect quantum information.

## Related concepts

- [[Quantum Decoherence]]
- [[Qubits]]
- [[Quantum Gates]]

## Learning tasks

- [ ] Study stabilizer codes
- [ ] Understand syndrome measurement

## Reflections

See [[2026-11-03]].
```

Recommended storage responsibilities:

-   Markdown files: canonical content representation for human-authored
    notes.
-   SQLite: structured tasks, schedules, relationships, activity events,
    and indexes.
-   Derived graph indexes and embeddings: rebuildable data.
-   Stable IDs: persistent identity across renames and links.
-   Version history: recovery from accidental or automated changes.

## 5.4 Desktop interoperability

A mobile-only application cannot fully replace Obsidian for intensive
research, coding, long-form writing, and large knowledge vaults.

Long-term platform strategy:

-   **Android:** capture, planning, activity automation, and journaling.
-   **Desktop:** full knowledge editor, research environment, graph,
    canvas, bulk management, and keyboard-driven workflows.
-   **Shared format:** Markdown and portable structured metadata.

Design the data model for desktop from day one, even if Android is the
first implementation.

------------------------------------------------------------------------

# 6. Behavioural Feedback Engine

## 6.1 Core behavioural loop

**Intention → Behaviour → Outcome → Feedback → Adaptation**

BU should understand more than whether a habit was checked off. It
should understand what was intended, what happened, what changed, and
what should be adjusted.

## 6.2 Distinguish discipline problems

  -----------------------------------------------------------------------
  Problem                 Likely cause            Appropriate response
  ----------------------- ----------------------- -----------------------
  Procrastination         Delayed initiation      Reduce starting
                                                  friction

  Overplanning            Too many commitments    Recalculate capacity

  Distraction             Attention interruption  Introduce contextual
                                                  friction

  Ambiguity               Task is unclear         Break into concrete
                                                  actions

  Fatigue                 Insufficient available  Adjust workload
                          energy                  

  Avoidance               Repeatedly postponing a Explore the obstacle
                          task                    

  Poor estimation         Actual duration exceeds Improve duration
                          estimate                estimates

  Inconsistency           Irregular execution     Create reliable cues
                                                  and routines
  -----------------------------------------------------------------------

Two missed sessions can have completely different causes. BU should not
send the same generic motivational message in both cases.

## 6.3 Adaptive intervention states

  -----------------------------------------------------------------------
  State                               System response
  ----------------------------------- -----------------------------------
  L0 --- Observe                      Understand patterns quietly

  L1 --- Remind                       Send contextual reminders

  L2 --- Interrupt                    Offer a brief pause and intention
                                      check

  L3 --- Protect                      Apply user-approved focus limits

  L4 --- Commit                       Apply preconfigured restrictions
                                      during a focus session

  L5 --- Recover                      Reduce friction and help the user
                                      reset
  -----------------------------------------------------------------------

L5 is a recovery state, not a punishment state.

Every intervention should be:

-   User-configurable.
-   Reversible.
-   Explainable.
-   Limited by quiet hours and notification budgets.
-   Designed around explicit commitments.
-   Easy to override.

## 6.4 Reality-based planning

BU should compare estimated and actual durations by task category.

Illustrative data:

  Task type       Estimate   Actual average   Adjustment
  ------------- ---------- ---------------- ------------
  Reading           30 min           35 min        1.17×
  Mathematics       45 min           65 min        1.44×
  Writing           60 min           90 min        1.50×
  Programming       90 min          130 min        1.44×

Use robust averages, sample-size thresholds, and uncertainty estimates.
A handful of sessions should not become an inflexible personal rule.

## 6.5 Consistency without shame

Recommended mechanisms:

-   Grace days rather than brittle streaks.
-   Minimum viable actions for difficult days.
-   Automatic reduction of unrealistic workloads.
-   Weekly consistency measurements.
-   Contextual reminders.
-   Recovery after interruptions.
-   Private historical progress.

Example response to a missed plan:

> Your original plan exceeded your usual available capacity. You
> completed one of three main commitments. Let's reduce tomorrow's
> workload and preserve mathematics revision as the first priority.

------------------------------------------------------------------------

# 7. Android automation and platform constraints

BU should use supported Android APIs and avoid making its core product
dependent on privileged or policy-sensitive automation.

## 7.1 Recommended integrations

  -----------------------------------------------------------------------
  Capability                          Preferred implementation
  ----------------------------------- -----------------------------------
  App usage collection                UsageStatsManager

  Scheduled reminders                 AlarmManager / WorkManager

  Notification processing             NotificationListenerService

  Focus notifications                 Android notification and DND APIs

  Authorized app blocking             Carefully scoped, user-approved
                                      integration

  Network blocking                    Optional local VPN, subject to
                                      policy

  Device automation                   Explicit Android intents and
                                      supported APIs

  Advanced UI integration             Optional, separately evaluated
                                      Accessibility feature
  -----------------------------------------------------------------------

## 7.2 Important distribution constraints

Google Play policy restricts use of Accessibility Services for
autonomous planning and execution of actions or decisions, and prohibits
bypassing Android security controls or preventing users from
uninstalling applications.

Therefore:

-   Do not make unrestricted Accessibility automation the foundation of
    BU.
-   Prefer UsageStatsManager for application usage.
-   Use explicit Android intents for supported actions.
-   Treat advanced UI automation as optional and separately evaluated.
-   Make all permissions understandable and revocable.
-   Do not silently collect private notification content.
-   Provide exclusions for sensitive applications and data.

`USE_EXACT_ALARM` is restricted to qualifying core alarm and calendar
use cases. Investigate `SCHEDULE_EXACT_ALARM` and user-granted precise
scheduling where appropriate.

The application must also account for Android background execution
limits. Critical reminders and event processing cannot depend on the UI
process remaining alive.

------------------------------------------------------------------------

# 8. Technical architecture

## 8.1 Proposed architecture

``` text
                 LOOM USER EXPERIENCE
       NOW | PLAN | JOURNAL | ATLAS | CAPTURE
                         |
             APPLICATION DOMAIN LAYER
                         |
       +-----------------+------------------+
       |                 |                  |
  Knowledge Engine  Life Engine      Behaviour Engine
       |                 |                  |
       +-----------------+------------------+
                         |
                  MEMORY ENGINE
           Journal | Context | Retrieval
                         |
                    NEEDLE 3
       Intent | Extraction | Tool Routing | Embeddings
                         |
              NATIVE ANDROID SERVICES
       Scheduling | Notifications | Usage | Focus
                         |
                   LOCAL DATA
       Markdown | SQLite | Event Log | Embeddings
```

## 8.2 Technology recommendations

  Layer                Recommendation
  -------------------- --------------------------------------------------
  UI                   Kotlin + Jetpack Compose
  Domain logic         Pure Kotlin modules
  Needle integration   C/C++ library + JNI
  Database             Room / SQLite
  Knowledge storage    Markdown + YAML
  Search               SQLite FTS5 + Needle embeddings
  Encryption           Android Keystore + encrypted database
  Scheduling           WorkManager + AlarmManager
  Preferences          DataStore
  Native automation    Kotlin Android services
  Future desktop       Compose Multiplatform or separate desktop client

## 8.3 Why Kotlin-first

Flutter with Dart domain logic and Kotlin native services is viable if
cross-platform UI is an immediate priority.

However, an Android-first product with extensive native scheduling,
background services, database integration, and JNI inference has a
strong case for Kotlin and Jetpack Compose.

Keep domain logic independent of UI lifecycle. Background operations
must not depend on a Flutter or Compose screen remaining active.

## 8.4 Needle 3 responsibilities

Needle should be a local interpreter and orchestration component, not
the authority for every decision.

Suitable responsibilities:

-   Intent classification.
-   Structured extraction.
-   Tool selection.
-   Entity and relationship extraction.
-   Journal draft generation.
-   Semantic embedding and retrieval, where supported.
-   Natural-language task creation.

Deterministic Kotlin logic should handle:

-   Deadline calculations.
-   Scheduling constraints.
-   Task dependencies.
-   Reminder timing.
-   Habit records.
-   Duration statistics.
-   Permission checks.
-   Risk classification.
-   Execution and audit logging.

## 8.5 Tool execution safety

Every tool should have:

-   A typed schema.
-   Input validation.
-   Permission requirements.
-   Risk classification.
-   Idempotency where practical.
-   An execution record.
-   An undo or recovery strategy where possible.

Example tool catalogue:

``` text
createTask()
completeTask()
rescheduleTask()
createGoal()
createProject()
createHabit()
startFocusSession()
endFocusSession()
getCurrentSchedule()
getOverdueTasks()
createJournalDraft()
searchJournal()
getActivitySummary()
scheduleReminder()
launchApplication()
```

Example policy:

  -----------------------------------------------------------------------
  Action                              Default
  ----------------------------------- -----------------------------------
  Create a clear task                 Automatic

  Create a journal draft              Automatic

  Search local notes                  Automatic

  Reschedule a low-risk task          Propose or execute under user rules

  Delete a journal                    Confirmation

  Send a message                      Confirmation

  Change a recurring routine          Confirmation

  Change sensitive system settings    Explicit approval and supported API
  -----------------------------------------------------------------------

Model confidence is not proof of safety. Risk and permission checks must
remain independent.

------------------------------------------------------------------------

# 9. Journal and memory architecture

## 9.1 Four memory categories

  Memory type   Example
  ------------- --------------------------------------------
  Episodic      What happened on a particular day
  Semantic      Long-term goals, preferences, and concepts
  Prospective   What the user intends to do
  Procedural    How a recurring routine is performed

## 9.2 Journal provenance

Every automatically generated journal statement should be traceable to
its source.

Recommended fields:

``` text
JournalEntry
  id
  date
  content
  creation_mode
  source_event_ids
  confidence
  user_edited
  created_at
  updated_at
```

A journal draft may combine:

-   Confirmed focus sessions.
-   User-entered reflections.
-   Scheduled commitments.
-   Observed activity.
-   Inferred context.

The UI should distinguish these sources. Inferred statements must not be
presented as verified facts.

## 9.3 Storage and search

Recommended components:

-   Room for relational records.
-   Markdown for human-readable journal and knowledge documents.
-   SQLite FTS5 for full-text search.
-   Local embeddings for semantic retrieval.
-   Android Keystore for encryption key management.
-   Encrypted exports and backup.

## 9.4 Privacy requirements

-   No mandatory account.
-   No mandatory cloud AI.
-   No external analytics by default.
-   Local processing for core functionality.
-   Explicit permission requests.
-   Sensitive-app exclusions.
-   User-controlled retention.
-   Encrypted backup and restore.
-   Clear export and deletion mechanisms.

Needle's documented telemetry settings must be verified in the Android
build. Do not claim complete offline privacy until network behaviour has
been tested.

------------------------------------------------------------------------

# 10. Development roadmap

The following is a staged product-development proposal, not a guaranteed
delivery estimate.

## Phase 0 --- Research and technical validation

-   Benchmark Needle 3 on target Android hardware.
-   Test English, Tamil, and Sinhala inputs, including code-switching.
-   Prototype LOOM interactions.
-   Validate Android permissions and distribution constraints.
-   Define the universal object model.

**Exit criterion:** feasible, tested architecture.

## Phase 1 --- Knowledge foundation

-   Markdown editor.
-   Notes and wiki links.
-   Backlinks.
-   Properties and tags.
-   Daily notes.
-   Search and attachments.
-   Obsidian vault import/export.
-   Basic Canvas and relationship exploration.

**Exit criterion:** a genuinely useful offline knowledge vault.

## Phase 2 --- Life management engine

-   Goals and life areas.
-   Projects and hierarchical tasks.
-   Daily and weekly planning.
-   Calendar and timeboxing.
-   Tomorrow Contract.
-   Week Compass.

**Exit criterion:** complete offline planning workflow.

## Phase 3 --- Needle intelligence

-   Native JNI integration.
-   Natural-language task creation.
-   Journal extraction.
-   Promise detection.
-   Contextual knowledge retrieval.
-   Confidence-based tool execution.

**Exit criterion:** natural-language management works reliably offline.

## Phase 4 --- Living journal and activity observer

-   Usage event collection.
-   Focus sessions.
-   Automatic timeline.
-   Daily journal synthesis.
-   Evidence-linked entries.
-   Personal memory graph.

**Exit criterion:** the journal maintains itself from approved sources.

## Phase 5 --- Behavioural feedback

-   Reality Multiplier.
-   Schedule drift and capacity analysis.
-   Adaptive reminders.
-   Intervention system.
-   Distraction friction.
-   Weekly behavioural reports.

**Exit criterion:** measurable improvement in planning and execution.

## Phase 6 --- Advanced ecosystem

-   Advanced focus integrations.
-   Widgets and contextual capture.
-   Extension API.
-   Desktop companion.
-   Encrypted local synchronisation.
-   Backup, restoration, and migration tools.

**Exit criterion:** complete personal productivity ecosystem.

------------------------------------------------------------------------

# 11. Success metrics: Life Yield

BU should not optimise for:

-   Daily screen time.
-   Number of application opens.
-   Number of notifications clicked.
-   Number of notes created.
-   Artificial streak length.

Instead, measure whether the system helps users achieve meaningful
outcomes.

  -----------------------------------------------------------------------
  Metric                              Definition
  ----------------------------------- -----------------------------------
  Commitment conversion               Proportion of planned commitments
                                      completed

  Planning accuracy                   Difference between estimated and
                                      actual durations

  Recovery time                       Time required to resume after
                                      disruption

  Capture friction                    Time and effort required to record
                                      useful information

  Journal completeness                Meaningful activity recorded with
                                      reliable provenance

  Reminder usefulness                 Whether reminders improve outcomes
                                      without excessive interruption

  Knowledge retrieval                 Ability to find relevant
                                      information when needed

  Autonomy                            Ability to use the system without
                                      dependence on constant prompts

  Administration overhead             Time spent maintaining BU itself
  -----------------------------------------------------------------------

**Primary success signal:** users spend less time managing BU while
becoming more effective at managing their actual lives.

------------------------------------------------------------------------

# 12. Product risks and validation requirements

  -----------------------------------------------------------------------
  Risk                                Mitigation
  ----------------------------------- -----------------------------------
  Needle extraction errors            Benchmark, validate, clarify
                                      ambiguous input

  Hallucinated journal records        Provenance and explicit evidence
                                      categories

  Overloaded schedules                Capacity constraints and realistic
                                      estimates

  Notification fatigue                Notification budgets and quiet
                                      hours

  Excessive intervention              User-controlled levels and recovery
                                      state

  Android background restrictions     Event-driven design and supported
                                      scheduling

  Accessibility policy violations     Do not depend on unrestricted
                                      Accessibility automation

  Markdown incompatibility            Import/export tests and stable IDs

  Data corruption                     Versioning, backups, and recovery
                                      tests

  Battery drain                       On-demand inference and
                                      event-driven processing

  Privacy leakage                     Local-first design and network
                                      auditing

  Scope explosion                     Stage development around testable
                                      exit criteria
  -----------------------------------------------------------------------

Before implementing the advanced system, validate:

1.  Needle 3's actual latency and memory use on the target phone.
2.  Extraction quality for the user's languages.
3.  Android behaviour after process death and reboot.
4.  Markdown round-trip compatibility with Obsidian.
5.  Journal provenance and correction workflows.
6.  Battery consumption under realistic monitoring.
7.  Reliability of reminders under Doze and battery optimisation.
8.  User experience through repeated real-world use.

------------------------------------------------------------------------

# 13. Decisions to establish before implementation

The following should become the product's architectural commitments:

1.  Build an integrated knowledge-management engine, not a journal
    attached to a task manager.
2.  Adopt LOOM as the proposed unified interaction system.
3.  Use one universal object and relationship model.
4.  Preserve Markdown compatibility and Obsidian vault import/export.
5.  Make the journal an evidence-linked, automatically maintained
    chronicle.
6.  Use Needle 3 as an on-device interpreter, not a general-purpose
    reasoning authority.
7.  Implement deterministic behavioural rules before adaptive machine
    learning.
8.  Design discipline interventions around autonomy, recovery, and
    explicit commitments.
9.  Keep Android native services independent of the UI process.
10. Design for desktop interoperability from the beginning.

Additional validation is required for:

-   Licensing implications of reusing any third-party code, including
    Mindful-related components.
-   Needle 3 model quality and language support.
-   Android distribution and permission policies.
-   Device-specific inference performance.
-   Storage, backup, and encryption behaviour.

------------------------------------------------------------------------

# 14. Final product definition

## BU --- A Personal Life Operating System

**LOOM --- Temporal Knowledge Interface**

A local-first, intelligent environment for understanding, organising,
experiencing, and improving life.

### Five pillars

  Pillar         Product capability
  -------------- --------------------------------------------------
  Knowledge      An Obsidian-grade personal knowledge environment
  Memory         An automatically maintained personal chronicle
  Planning       Adaptive life and project management
  Behaviour      Personalised discipline and attention management
  Intelligence   Offline AI orchestration connecting everything

### Two signature rituals

**Morning Launch**

Understand the day, identify the Main Front, review relevant
commitments, and begin.

**Tomorrow Contract**

Review reality, reconcile unfinished commitments, reflect on the day,
and establish tomorrow's priorities.

### Underlying philosophy

-   Everything is interconnected.
-   Every commitment has context.
-   Every significant activity can become a record.
-   Every record can inform future planning.
-   Every intervention should have a measurable purpose.
-   Every important action remains under the user's control.

### The defining experience

1.  The user expresses an intention naturally.
2.  BU understands it and creates appropriate knowledge objects.
3.  It connects the intention to goals and projects.
4.  It proposes a realistic schedule.
5.  It helps the user execute the commitment.
6.  It records what actually happened.
7.  The journal updates with traceable evidence.
8.  Tomorrow's plan learns from today's reality.

When this loop works reliably and feels effortless, BU has a coherent
foundation for its wider feature set.

**BU should not make people addicted to productivity. It should become
an environment they value returning to because it gives them clarity,
remembers what matters, and makes the next meaningful action easier.**

------------------------------------------------------------------------

## Research and reference notes

The following are starting points for verification and continued
research. Product features, policies, and technical documentation can
change; verify current versions before implementation.

### Product references

-   Obsidian: https://obsidian.md/
-   Sunsama: https://www.sunsama.com/
-   Day One: https://dayoneapp.com/
-   Structured: https://structured.app/
-   Opal: https://opal.so/
-   one sec: https://one-sec.app/
-   Duolingo: https://www.duolingo.com/

### Technical references

-   Android UsageStatsManager:
    https://developer.android.com/reference/android/app/usage/UsageStatsManager
-   Android WorkManager:
    https://developer.android.com/topic/libraries/architecture/workmanager
-   Android AlarmManager:
    https://developer.android.com/reference/android/app/AlarmManager
-   Android Accessibility:
    https://developer.android.com/guide/topics/ui/accessibility/service
-   Google Play Accessibility policy:
    https://support.google.com/googleplay/android-developer/answer/10964491
-   Android notification listener:
    https://developer.android.com/reference/android/service/notification/NotificationListenerService
-   Shizuku: https://shizuku.rikka.app/
-   Needle: https://cactuscompute.com/needle

### Evidence and research caveats

The product comparisons and behavioural mechanisms in this report are
design inputs, not proof that BU will achieve the same outcomes. The
reported one sec field-study results and social-media reward-learning
research should be reviewed in their original publications before being
used in external marketing or scientific claims.

The proposed LOOM system, BU data model, behavioural engine, product
architecture, and development sequence are product-design
recommendations. They are not established industry standards or
validated product-market-fit findings.

------------------------------------------------------------------------

**Document status:** Strategic product specification --- proposed\
**Next deliverable:** Detailed BU 2.0 Product Requirements Document
(PRD), LOOM interaction specification, screen-by-screen wireframes, and
implementation-ready database schema.
