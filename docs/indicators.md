# Indicators & Progress

## PageIndicator / PageIndicatorView
**File:** `customviews/PageIndicators.kt`

Animated dot indicator for paged content. The selected dot expands to a pill shape.

```kotlin
PageIndicator(
    numberOfPages = 5,
    selectedPage = currentPage,
    defaultRadius = 10.dp,
    selectedLength = 24.dp,
    space = 8.dp,
    animationDurationInMillis = 300
)
```

`PageIndicatorView` is the single-dot primitive used internally.

---

## PulseIndicator
**File:** `customviews/PulseIndicator.kt`

Three concentric expanding rings with a central icon image. Rings animate with staggered offsets.

```kotlin
PulseIndicator(icon = R.drawable.your_icon)
```

---

## LoadingButton / LoadingIndicator
**File:** `customviews/Loadings.kt`

Button that hides its content and shows a 3-dot animation while `loading = true`.

```kotlin
LoadingButton(onClick = { }, loading = isLoading, animationType = AnimationType.LazyBounce) {
    Text("Pay Now")
}
```

Animation types: `Bounce`, `LazyBounce`, `Fade`.

---

## ProgressButton
**File:** `customviews/progressbar/Progress.kt`

Button with a `LinearProgressIndicator` overlay. The progress bar height matches the button height dynamically.

```kotlin
ProgressButton(progress = 0.6f, onClick = { })
```

---

## CustomVerticalProgressBar
**File:** `customviews/progressbar/VerticalSteppedProgressBar.kt`

Three-step vertical timeline showing `REQUESTED → PENDING → COMPLETED` states with coloured step circles and connecting lines.

```kotlin
CustomVerticalProgressBar(
    currentState = ProgressState.PENDING,
    requestedContent = "Order placed",
    pendingContent = "Processing",
    completedContent = "Delivered"
)
```
