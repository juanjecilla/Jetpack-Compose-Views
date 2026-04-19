# Layouts & Containers

## CardStack
**File:** `customviews/Cards.kt`

Custom `Layout` that stacks child cards with a small Y and X offset, creating a physical deck effect. Children are typically `Card` composables with a random rotation.

```kotlin
CardStack(modifier = Modifier.fillMaxWidth()) {
    repeat(4) { Card(modifier = Modifier.fillMaxWidth().height(80.dp)) { /* … */ } }
}
```

---

## CornerBadgeBox
**File:** `layout/BadgeLayout.kt`

Places a ribbon/badge in any corner of a container. Supports solid colour or `Brush`, configurable corner roundness, and four positions via `BadgeCorner`.

```kotlin
CornerBadgeBox(
    badgeColor = Color(0xFF6200EE),
    corner = BadgeCorner.TopRight,
    badgeContent = { Text("NEW", color = Color.White) }
) {
    Card(modifier = Modifier.size(150.dp)) { /* … */ }
}
```

---

## JumpyRow
**File:** `customviews/layouts/JumpyLayout.kt`

Custom `Layout` that offsets children vertically using a Gaussian wave function over an `InfiniteTransition`, creating a continuous wave animation.

```kotlin
JumpyRow(waveWidth = 200.dp, waveHeight = 25.dp) {
    repeat(8) { Box(modifier = Modifier.size(32.dp)) { /* … */ } }
}
```

---

## ShimmerLayout
**File:** `layout/ShimmerLayout.kt`

Wraps content in a `Layout` that can be combined with the `compose-shimmer` library for loading skeleton effects.

```kotlin
ShimmerLayout(loading = isLoading) {
    /* skeleton or real content */
}
```

---

## TabsExample
**File:** `layout/Tabs.kt`

Chrome-inspired custom tab bar. Tabs have an angled trapezoidal shape and support close buttons. `TabItem` holds id, title, icon, and content composable.

```kotlin
TabsExample()   // self-contained demo with Home/Mailbox/Shop tabs
```

---

## MotionLayoutAppBar
**File:** `customviews/AppBar.kt`

Collapsing toolbar built with `ConstraintLayout` and `MotionLayout`. The header image and title animate in/out as the user scrolls.

---

## DynamicHeightTopAppBar
**File:** `customviews/toolbar/DynamicHeightTopAppBar.kt`

`TopAppBar` whose height responds to a `TopAppBarScrollBehavior`. Includes a drag handle for manual resizing.

---

## CarouselLayout
**File:** `layout/CarouselLayout.kt`

`HorizontalPager`-based carousel placeholder. Currently a scaffold — extend with real page content.

---

## CircularList / CircularRow
**File:** `customviews/Carousels.kt`

Horizontal drag-based circular list where items scale with distance from the centre using `Modifier.graphicsLayer`.
