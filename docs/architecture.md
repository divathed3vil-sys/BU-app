# Architecture

> The five-layer architecture of BU v6. Public, open-source, verifiable.

This document is the canonical architecture reference. For the full vision, see [`research/v6-fire/BU_v6_The_Fire.pdf`](../research/v6-fire/BU_v6_The_Fire.pdf). For the locked plan, see [`PLAN.md`](../PLAN.md).

---

## The Five Layers

```
┌─────────────────────────────────────────────────┐
│  THE FLAME (UI layer — Jetpack Compose)          │
│  Fire ring · The Play · Journaling surfaces      │
│  Life Log browser · Settings                     │
└────────────────────┬────────────────────────────┘
                     │ reads, never writes directly
┌────────────────────▼────────────────────────────┐
│  THE EMBER (state + memory layer)                │
│  ObjectBox 4.0 with HNSW vector index            │
│  Every action is an ember — logged, embedded,    │
│  retrievable by semantic search                  │
└────────┬───────────────────────┬────────────────┘
         │                       │
┌────────▼────────┐    ┌────────▼─────────────────┐
│  THE WATCHER     │    │  THE FORGE (intelligence) │
│  (Android APIs)  │    │  Gemma 3 1B (router)      │
│  UsageStats      │    │  + Qwen 0.6B (JSON tools) │
│  + FGS poller    │    │  + RAG over embers        │
│  + Notification  │    │  + Chooser / Classifier   │
│    Listener      │    │  + Pattern / Predict      │
│  + Accessibility │    │  + Photo proof embedding  │
│    (opt-in)      │    │  + Gemini Nano (optional) │
└────────┬─────────┘    └────────┬─────────────────┘
         │                       │
         └───────────┬───────────┘
                     │
            ┌────────▼────────┐
            │  THE BEDROCK     │
            │  (invariants)    │
            │  1. Tap survives │
            │  2. Fire honest  │
            │  3. You own      │
            │     your embers  │
            └─────────────────┘
```

## Layer responsibilities

### The Flame (UI)
- **Tech:** Kotlin + Jetpack Compose, Material 3.
- **Job:** Render the fire ring, the Play, the three journaling surfaces (write/voice/draw), the Life Log browser, the settings.
- **Constraint:** Pure UI. No business logic. Reads from the Ember, never writes directly. All writes go through the Bedrock's command gateway.
- **Modules:** `src/app/` (shell, navigation, DI), `src/flame/` (UI components).

### The Ember (state + memory)
- **Tech:** ObjectBox 4.0 with HNSW vector index. SQLCipher for encryption at rest.
- **Job:** Single source of truth. Every action, journal entry, drift event, play outcome is an ember — logged, embedded, retrievable by semantic search.
- **Constraint:** The Ember is the only writer. The Flame reads, the Watcher reports, the Forge proposes — but all writes go through the Bedrock to the Ember.
- **Modules:** `src/ember/`.

### The Watcher (Android APIs)
- **Tech:** Kotlin. UsageStatsManager, Foreground Service, NotificationListenerService, AccessibilityService (opt-in).
- **Job:** Observe the user's phone activity in real time. Report events to the Ember. Watch for drift during sealed Normal Focus plays.
- **Constraint:** The Watcher never owns truth. It reports to the Ember, which owns truth. The Watcher can be killed by the OS and restarted; the Ember's state is unchanged.
- **Modules:** `src/watcher/`.

### The Forge (intelligence)
- **Tech:** Gemma 3 1B (529MB int4, MediaPipe LLM Inference API), Qwen 2.5 0.6B (quantized, for JSON tool calls), MobileNet v3 (1.8MB, for photo proof embedding), ObjectBox HNSW (for RAG). Gemini Nano as fast path if present (AICore, runtime-detected).
- **Job:** Compute the fire state, choose the daily Play, detect patterns, classify drift (educational vs not), verify photo proof, surface predictions and causal chains.
- **Constraint:** The Forge never writes directly. It proposes; the Bedrock disposes. Confidence routing on every output: high → act (low-risk only), medium → confirm chip, low → refuse + deterministic fallback. The whole app works without the Forge loaded (BU Lite).
- **Modules:** `src/forge/`, `native/` (JNI bridges, model hosting).

### The Bedrock (invariants)
- **Tech:** Pure Kotlin. Enforced at compile time (types) and test time (unit tests).
- **Job:** Guarantee the three invariants: (1) every tap survives process death, (2) the fire is honest (evidence grades preserved), (3) you own your embers (Markdown export anytime).
- **Constraint:** Every write path goes through the Bedrock. The Bedrock is the only writer to the Ember. No exceptions.
- **Modules:** `src/bedrock/`.

## The data flow

```
User action (tap)
    │
    ▼
Flame (UI) ──dispatch──▶ Bedrock (command gateway)
                              │
                              ▼
                          validate + apply invariants
                              │
                              ▼
                          write to Ember (ObjectBox, transactional)
                              │
                              ├──▶ append to event log (write-ahead)
                              ├──▶ embed if relevant (Forge, async)
                              └──▶ notify Flame (reactive UI update)
```

## The model loading

- BU Lite: no model loaded. App works fully.
- BU Full: Gemma 3 1B loaded lazily on first Forge request. Evicted on memory pressure. Re-loaded on next request.
- Qwen 0.6B loaded lazily on first JSON tool call. Same eviction pattern.
- MobileNet v3 loaded once, kept resident (1.8MB, tiny).
- Gemini Nano (if present): accessed via AICore, no loading needed.

## The persistence model

- **Ember:** ObjectBox, encrypted with SQLCipher. Write-ahead log for durability. Every write is transactional.
- **Attachments:** Photo proof raw photos are deleted after embedding. Only the embedding (1280-dim float vector, 5KB) is stored.
- **Voice journals:** Audio is deleted after Whisper-tiny transcription. Only the transcription text is stored.
- **Free-draw canvases:** Stored as SVG (vector, tiny, lossless) + embedding. No raster images.
- **Model file:** Stored in app's private storage. Checksum-verified. Can be deleted (returns to Lite).

## The export model

Settings → Export generates:
- `embers.md` — every ember as Markdown (timestamp, kind, payload, evidence grade).
- `lifelog.md` — the complete Life Log, auto + manual, as a dated Markdown journal.
- `journal.md` — every journal entry (write + voice transcription + canvas SVG).
- `events.jsonl` — the raw event log (every domain event with idempotency key).
- `manifest.json` — content hashes, export version, schema version.

Restorable on any BU install.

## The module structure (Gradle)

```
:app                ← UI shell, navigation, DI wiring, services host
:flame              ← UI components (fire ring, play, journaling)
:ember              ← ObjectBox schema, repositories, queries
:watcher            ← Android API adapters (UsageStats, FGS, Accessibility)
:forge              ← Gemma host, chooser, classifier, pattern engine
:bedrock            ← Command gateway, invariants, persistence, security
:designsystem       ← Tokens, motion, haptics (leaf module, no deps)
```

Dependency law: `:app → {all}` · `:flame → {:ember, :designsystem}` · `:ember → {:bedrock}` · `:watcher → {:bedrock}` · `:forge → {:ember, :bedrock}` · `:bedrock → {}` · `:designsystem → {}`. The Bedrock and Designsystem are leaf modules — no internal deps. Everything else depends on them.

## The testing strategy

- **Unit tests (JVM):** Bedrock invariants, Ember queries, Forge chooser logic, Forge classifier logic. Pure Kotlin, no Android.
- **Integration tests (Android):** Watcher → Ember → Forge flow. Real ObjectBox, simulated Watcher events.
- **UI tests (Compose):** Flame navigation, fire ring interactions, journaling surfaces.
- **Lifecycle tests:** Process death, reboot, Doze, battery saver, revoked permissions, clock/timezone changes.
- **Migration tests:** ObjectBox schema migrations from every shipped version.

---

*Last updated: 2026-11-04. v1, ships with P0 Spark. Will evolve as the codebase grows.*
