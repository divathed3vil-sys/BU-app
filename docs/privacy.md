# Privacy

> **The founding contract.** This document is the user's guarantee. It must ship on day one and never be weakened without explicit user consent.

BU is a discipline-first life operating system. To do its job, BU Full uses system-level Android services to observe what apps you use and when. This document says, in plain language, what BU sees, what stays on your device, what is never collected, and how to export or delete everything.

---

## What BU Lite sees

**Nothing.** BU Lite has no Watcher, no model, no system-level access. It is a planning and journaling app. It stores what you type (plays, journal entries, manual Life Log entries) on your device. That's it.

## What BU Full sees (only after you opt in)

When you opt in to BU Full, the Watcher activates. The Watcher sees:

1. **App foreground events** — which app is in the foreground, when, for how long. Source: `UsageStatsManager`. Polled every ~30 seconds.
2. **Screen on/off events** — when your screen turns on or off. Used to compute a sleep proxy (longest screen-off stretch).
3. **Unlock counts** — how many times you unlocked your phone. Source: `UsageStatsManager` (`KEYGUARD_HIDDEN` events).
4. **Notification source tracking** — which apps notified you, when, how often. Source: `NotificationListenerService` (opt-in, separate permission). **The content of notifications is NEVER read.** Only the source app and timestamp.
5. **Active play window** — when you have a sealed play, the Watcher knows to watch for drift specifically during that window.
6. **YouTube video title (optional, separately consented)** — when you drift into YouTube during a sealed Normal Focus play, the AccessibilityService reads ONLY the video title (not the page, not your data, not your search history) and passes it to the Forge for educational classification. This is gated behind a separate explicit consent and can be disabled without losing other Watcher features.

## What BU never sees

- **The content of your notifications** — only the source app and timestamp.
- **The content of your messages, emails, or chats** — never.
- **Your contacts, photos, files, or location** — never.
- **Your browsing history** — never.
- **The content of other apps** — except the single YouTube video title case above, which is opt-in and minimal.

## What stays on your device

**Everything.** All Watcher data, all Forge computations, all journal entries, all Life Log entries, all embers. Everything is stored locally in an encrypted ObjectBox database on your device. Nothing is uploaded to any server. BU has no server. BU has no cloud. BU has no account system.

The on-device model (Gemma 3 1B, 529MB) runs entirely on your phone's NPU/GPU/CPU. No inference happens in any cloud.

## What BU does not have

- **No account.** You never log in. There is no user database.
- **No cloud.** There is no BU server. The app talks to no backend.
- **No analytics.** BU does not phone home with usage data. BU does not track you.
- **No ads.** BU is not ad-supported and never will be.
- **No third-party SDKs.** No Firebase, no Crashlytics, no Sentry, no Amplitude, no Mixpanel. Nothing that exfiltrates data.

## How to export everything

Anytime, in Settings → Export: BU generates a Markdown bundle (your embers, Life Log, journal entries, fire history) plus a JSONL event log. You can save it anywhere. You can read it in any text editor. You can import it into another BU install.

## How to delete everything

Settings → Reset the fire: permanently deletes all data from the device. Cannot be undone. There is no backup because there is no cloud.

## The persistent notification

Android 14+ requires that any foreground service shows a persistent notification. BU Full's notification reads:

- When the fire is burning: *"BU is watching. The fire is burning."*
- When the fire is flickering: *"BU is watching. The fire is flickering."*
- When the fire is out: *"BU is waiting. Tap to relight."*

This notification cannot be dismissed without disabling BU Full. It is the cost of the discipline engine. If you do not want it, stay on BU Lite.

## The Accessibility service (opt-in, separately consented)

BU Full's smart-context feature uses AccessibilityService to read YouTube video titles during sealed Normal Focus plays, so the Forge can classify educational vs non-educational content. This service:

- Is **off by default**, even in BU Full.
- Requires a **separate explicit consent** beyond the BU Full opt-in.
- Reads **only the video title** of the foreground YouTube player. Nothing else.
- **Does not read** your search history, your watch history, your comments, your subscriptions, or any other YouTube data.
- **Does not transmit** the title anywhere — it stays on device, is classified by Gemma 3 1B locally, and is discarded after classification.
- Can be **disabled at any time** without losing other BU Full features.

Google Play scrutinizes Accessibility use. BU's use is narrowly scoped, clearly disclosed, and strictly for the user's own benefit (preventing false drift interventions during educational YouTube). It is not used for ad targeting, data collection, or any purpose other than the user's discipline.

## The model download

When you opt in to BU Full, BU downloads the Gemma 3 1B model (529MB, int4 quantized) directly to your device. The download:

- Comes from a fixed, pinned URL (Hugging Face or Google AI Edge CDN).
- Is **checksum-verified** before use.
- Is stored in your app's private storage.
- Is **not** uploaded anywhere.
- Can be deleted anytime (returns you to BU Lite).

## Open source

BU is GPL-3.0 licensed. The source code is at [github.com/divathed3vil-sys/BU-app](https://github.com/divathed3vil-sys/BU-app). Everything in this document is verifiable from the source. If you don't trust this document, read the code.

## Changes to this document

If this document ever changes, the change will be:

1. Disclosed in [docs/changelog.md](./changelog.md).
2. Surfaced in the app with a prompt requiring explicit consent.
3. Open-sourced in the repo's git history.

BU will never silently weaken this contract.

---

*Last updated: 2026-11-04. v1, ships with P0 Spark.*
