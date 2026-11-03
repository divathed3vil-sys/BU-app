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
