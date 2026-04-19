# Buttons

## GradientBorderButtonClick / Round / Disable
**File:** `customviews/Buttons.kt`

Buttons with a gradient-painted border. Three variants: clickable counter, rounded, and disabled state.

```kotlin
GradientBorderButtonClick(
    colors = listOf(Color(0xFF6200EE), Color(0xFF03DAC5)),
    paddingValues = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
    widthFraction = 0.8f
)
```

## ButtonAnimation
Scale-down animation on press via `animateFloatAsState`.

## HeartAnimation
Heart icon with a scale/alpha pulse using `InfiniteTransition`.

## ButtonStyle
Column showing multiple corner-style variants side by side.

## ButtonCount / ButtonDisable / ButtonNoRipple
Convenience wrappers for count-tracking, disabled state, and ripple-free click handling.

---

## LoadingButton
**File:** `customviews/Loadings.kt`

Button that swaps content for a 3-dot loading indicator. Three animation modes: `Bounce`, `LazyBounce`, `Fade`.

```kotlin
LoadingButton(
    onClick = { /* trigger */ },
    loading = isLoading,
    animationType = AnimationType.Bounce
) {
    Text("Submit")
}
```

---

## SwipeButton (SwipeButtons.kt)
**File:** `customviews/SwipeButtons.kt`

Horizontal swipe-to-confirm button with a completion state.

```kotlin
SwipeButton(
    text = "Swipe to confirm",
    isComplete = confirmed,
    onSwipe = { confirmed = true }
)
```

## SwipeButton (Buttons.kt)
A simpler swipe variant with a leading icon and customisable shape/elevation.

---

## RepeatingButton
**File:** `customviews/Buttons.kt`

Button that fires `onClick` repeatedly while held down, with exponential delay decay.

---

## Click Effect Modifiers
**File:** `customviews/Clicks.kt`

| Modifier | Effect |
|---|---|
| `Modifier.bounceClick()` | Scales to 70% on press |
| `Modifier.pressClickEffect()` | Translates up on press |
| `Modifier.shakeClickEffect()` | Horizontal shake on press |
| `Modifier.clickableSingle()` | Prevents double-tap within 300 ms |
| `Modifier.bouncingClickable()` | Spring-physics bounce |
| `Modifier.clickableNoRipple()` | Clickable without ripple |

**File:** `customviews/ClickEffectsExtensions.kt`

| Modifier | Effect |
|---|---|
| `Modifier.noRippleClickable()` | Ripple-free clickable |
| `Modifier.noRippleToggleable()` | Ripple-free toggleable |
