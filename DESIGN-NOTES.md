# BU · DESIGN NOTES

Inventions and decisions made while implementing the locked plan. The plan never changes;
these notes record how its laws were honoured when reality pushed back.

---

## P1 — The Loop

### The floating nav (owner decision, folded in)
The LOOM nav detached from the screen bottom: a 64 dp pill, 24 dp side margins, 14 dp bottom
clearance, `CircleShape`, quiet outline, shadowElevation 18. It overlays the pager (content
glides beneath it); every tab's scroll column carries a fixed 148 dp bottom clearance, and the
Pebble + snackbar paddings were re-tuned so nothing ever hides behind the pill. On devices with
gesture nav the pill rides `navigationBarsPadding()` — it never collides with the system bar.

### Tally Trio: swipe + buttons, colour as intent
- Swipe right past ~90 px → **Done** (mint wash while dragging). Swipe left → **Carry**
  (cooling-grey wash). Buttons (✓ ◐ →) stay visible for discoverability and accessibility —
  the ≤2-tap law beats gesture purity.
- Carry is cooldown, never shame: a carried row keeps `OPEN`, gains a "carried N× · cooling"
  line, and the carry ledger records the move for P2's moved-3 rule. No red anywhere.
- Vertical "swipe down = Partial" (§4.3) is **deferred to P7 polish**: a vertical drag inside a
  vertical scroll list fights the scroll container; shipping it half-working would violate the
  animation-bug ban. Partial is one visible tap away in the meantime.

### Day Arc semantics (NOW)
`progress` = today's landed ratio (tallied ÷ Big Three), `nowProgress` = real clock position
inside the owner's day window (read from `settings`, default 5:00 → 23:00). The label under the
arc states the window and says "live" only when both inputs are real.

### Block placement
Blocks need a day + start (Arc-Dial, 15-min snap) + duration (chips 15/25/45/60/90 — every chip
is validator-legal, so the kernel can never bounce a chip choice). Overlap returns
`OVERLAP_BLOCK`, surfaced as "Another block already lives there." — honest, kind, no blame.

### Interpretation honesty (P1 parser)
- Confident: relative ("in 20 min"), explicit clock ("6pm", "17:30"), tonight/tomorrow/today,
  weekdays (next occurrence, same-day only if still in the future).
- Honest ambiguity: "at 5" resolves to nothing — the task is created **timeless**, never guessed.
- The raw capture text is never altered; the task title strips only the matched time phrase and
  intent prefixes. The capture keeps a `derived_from` relationship to its task (provenance from
  day one).

### Care honesty chain
`alarmExact` is written back by the scheduler after arming: `true` = exact, `false` = the OS
forced a 10-minute window, `null` = the outbox hasn't applied it yet. PLAN shows all three
states as different chips. An inexact alarm never poses as exact (I9).

### Focus on the monotonic clock
Start records `SystemClock.elapsedRealtime()`; end recomputes from the same clock. A reboot rolls
elapsed under the recorded start ⇒ `INTERRUPTED_OR_UNKNOWN` with a null duration — recorded,
never invented. The live timer is wall-clock-derived for display only; truth is monotonic.

### The NOW widget
A pure projection: `WIDGET_DIRTY` outbox effects repaint it from the ledger; its own 30-minute
update period re-reads the ledger too. Glyphs: ▲ Main Front, ✓ landed, ◐ partial, ○ open —
zero-guilt symbols only. The widget stores nothing.

### Onboarding
One decision per screen: Begin → day window (two Arc-Dials: opens 0:00–12:00, closes 12:00–23:45,
4 h floor enforced in UI *and* kernel) → notification permission (ask once, explain, "Later"
works — I9: a missing permission degrades delivery, never the ledger). The gate reads
`onboarded` from `settings` on every cold start; there is no second truth.

### Widget/AI/i18n note
The interpreter is zone-locked to Asia/Colombo until settings expose travel zones (matches the
kernel's `DEFAULT_ZONE`). Revisit with the Care engine in P2.

---

## P2 — Rituals & reminders

### The seal is one command, not five
Dusk Close *feels* like five steps but commits as exactly one `SealTomorrow` command: mood on
the closing day's contract, Big Three + Minimum Day + If-Then + ceiling + `sealedAtMs` on
tomorrow's. A crash mid-ritual leaves the ledger untouched, never half-sealed (I4). Triage
decisions (carry/shrink/kill) are the exception — they fire immediately, so a triaged item is
already moved in the ledger before the seal; the seal's draft simply remembers their titles.

### Dusk belongs to the day being closed, not to the clock's date
Run Dusk Close at 00:40 and it closes *yesterday* (before 4 am = yesterday). The receipt, the
triage field, and the target date all derive from that; the ritual never lies about which day
it sealed.

### The rest day is legal
`SealTomorrow` with zero slots commits happily — an empty Big Three is a plan too. The
validator enforces "exactly one Front" only when lines exist. Kindness is encoded in the
kernel, not just the copy.

### The moved-3 law lives in the kernel and in the thumbs
The kernel refuses the fourth carry (`CARRY_LIMIT_REACHED`) — but the UI also closes the lane
early: no carry chip colour on drag, no → button, and the row names the three remaining exits.
A law you can see is a law that gets obeyed. Shrink and Kill stay open at the limit; they are
the point.

### Why-now is a UI law and a notification law
Every care row and every care notification carries its why-now line computed from the care
type (pure `RitualHealth.whyNow`). The notification cannot exist without it — the receiver
composes it, so the ring always answers "why now?" before the owner asks.

### The budget bounds interruptions, never knowledge
Budget overflow does not hide the item: it still lands DUE in the ledger, shows in PLAN with
its waiting chain, and one quiet `DIGEST_QUIET` notification per day mentions the waiting
count. `DIGEST_QUIET` rows in the notification log keep the digest at one per day even when
five items overflow — the log is the dedupe memory, not a second truth.

### Recurring care re-arms inside the committing transaction
Completing an ANCHOR/HABIT item inserts tomorrow's round and enqueues its alarm in the same
transaction that marks the old one DONE. A crash cannot strand a habit without its next
round (I4/I5). One-shot types simply end — silence is their success.

### Escalation with no teeth of its own
`RitualHealth.duskNudge` only changes what NOW says. It cannot fire notifications, cannot
raise the Shield, cannot nag (I11). The word "missed" is banned by a test — zero-guilt copy
is enforced, not aspirational.
