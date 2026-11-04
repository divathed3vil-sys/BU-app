# BU v6 — The Fire · Supporting Notes

This folder contains the v6 vision document and supporting notes. The canonical reference is the PDF; this file is for things that didn't make it into the PDF or that emerged after the doc was locked.

## Files

- `BU_v6_The_Fire.pdf` — the 28-page locked vision document. Read this first.
- `notes.md` — this file. Supporting notes, open questions, follow-ups.

## How this version was synthesized

v6 emerged from a 4-message conversation:

1. **Owner's research dump** — owner shared Google research on local AI models (Gemma 3, Qwen, Llama, Gemini Nano) and the hybrid architecture pattern (router model + worker model + vector DB + Android APIs). Owner proposed "an ever-living fire" as the central concept.

2. **LLM reanalysis** — LLM did web research to validate the owner's research (Gemma 3 1B confirmed as the right pick at 529MB int4 with 2585 tok/s prefill; Gemini Nano confirmed as device-limited, not a savior; ObjectBox 4.0 with HNSW confirmed as the right local vector DB; UsageStatsManager confirmed as requiring custom state machine; AccessibilityService confirmed as fragile but viable).

3. **6 design questions** — LLM asked 6 sharp questions about concept, discipline feel, front surface, journaling, backend depth, aesthetic. Owner answered: Black Box concept → Coach sharp + Silent mirror → One ring → Swipe-right journaling → All of the backend (patterns + predict + identity + causal) → Quiet luxury + Apple + Material 3 + playful.

4. **Owner's 6 free-text additions** — owner sent 6 numbered points covering: escalating intervention with smart context (Accessibility reads YT title), three-question first open, three-surface journaling (write + voice + free-draw), the Play as Lite vs Full distinction (two-tier architecture), the Life Log (auto + manual), and the multi-year growth animation (Egg, Inc style, never ending).

5. **LLM synthesis** — LLM synthesized the v6 vision document, locking the concept.

## Open questions for P0 planning

These need to be resolved when planning P0 Spark in detail:

1. **ObjectBox vs Room for the Ember layer.** ObjectBox is the right call for HNSW vector search, but Room is more familiar and easier to learn. For P0 (BU Lite, no AI), do we need ObjectBox yet, or can we start with Room and migrate later?
   - **Recommendation:** Start with Room for P0 (simpler, familiar). Migrate to ObjectBox in P1 when the Forge needs HNSW. The Bedrock abstraction makes this swap a one-module change.

2. **Compose Multiplatform vs pure Jetpack Compose.** KMP would let us share code with a future desktop reader. Pure Compose is simpler and more documented.
   - **Recommendation:** Pure Compose for v1. KMP later if desktop reader becomes real.

3. **Min SDK.** Galaxy A54 runs Android 14 (API 34). Min SDK 29 (Android 10) covers ~95% of users and gives us Foreground Service types, UsageStatsManager, NotificationListenerService. Min SDK 30 (Android 11) gives us stricter scoping but drops ~5% more users.
   - **Recommendation:** Min SDK 29, Target SDK 35.

4. **Web prototype first vs straight to Android.** A web prototype would validate the fire ring UX before committing to Android. But the web can't simulate the Watcher meaningfully, and the owner has confirmed they want to build on Android.
   - **Recommendation:** Straight to Android. Skip the web prototype for v6.

5. **Android Studio version + Kotlin plugin version pin.** Need to pin for reproducibility.
   - **Recommendation:** Android Studio Ladybug (2024.2) + Kotlin 2.0.21 + Compose Compiler 2.0.21. Pin in `gradle/libs.versions.toml`.

6. **The fire ring animation library.** Custom Compose Canvas + Android View system? Or a library like Lottie?
   - **Recommendation:** Custom Compose Canvas for the procedural fire (Lottie can't do procedural). Use `Animatable` + `LaunchedEffect` for the breathing/glow. Particle system via `Canvas` with offset/drawIn.

## Things the PDF doesn't cover (intentionally)

- **Monetization.** The owner said "free app will never give this for free" — but did not specify whether BU will ever charge. The PDF assumes free + open source. If the owner wants to charge later (e.g., for the model download bandwidth cost), this is a future decision.
- **Multi-user / shared gardens.** Out of v1 scope. The owner is the only user. Multi-user would require a server, which violates the founding principle.
- **iOS port.** Out of v1 scope. Android-only. The architecture (pure Kotlin domain layer) makes iOS possible later via KMP, but not v1.
- **Wear OS / watch face.** Tempting for the fire ring (always-visible on wrist), but out of v1 scope. Post-v1 backlog.
- **Widget.** A home-screen fire widget is in scope for P0 Spark. Will design during P0 planning.

## Follow-ups for the owner

- [ ] Revoke the GitHub PAT (single-use pattern).
- [ ] Clone the repo to local PC.
- [ ] Initialize the Android project scaffold (P0 Spark — next session).
- [ ] Decide on the 6 open questions above (or accept the recommendations).
