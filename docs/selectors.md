# Selectors

## TimerPicker
**File:** `customviews/selectors/TimePicker.kt`

Circular clock-face time picker with a toggle between `Hour` and `Minute` modes. Renders Roman numeral and 24-hour variants.

```kotlin
TimerPicker()
```

The composable manages its own state internally.

---

## CarouselCalendar / DialerWeekCalendar
**File:** `customviews/dates/DateCarousel.kt`

Horizontal lazy-row date carousel that scales items in 3D perspective based on their distance from the centre. `CarouselCalendar` generates a full-year date list automatically.

```kotlin
CarouselCalendar()
```

---

## PodcastSlider
**File:** `customviews/Sliders.kt`

Speed/value selector inspired by the Google Podcasts playback speed wheel. Drag left/right; items snap to integer values.

```kotlin
val state = rememberPodcastSliderState(currentValue = 12f, range = 5..30)
PodcastSlider(state = state, modifier = Modifier.fillMaxWidth())
```

---

## MediumScrollView / GesturedScrollBar
**File:** `customviews/selectors/CustomScrollBar.kt`

`LazyColumn` with a custom draggable scrollbar thumb on the right edge.

```kotlin
MediumScrollView(items = myList) { item ->
    Text(item.name)
}
```
