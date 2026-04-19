# Charts & Graphs

## Graph
**File:** `customviews/Graph.kt`

Bezier curve line chart drawn entirely with `Canvas`. Supports labelled X/Y axes and configurable padding and vertical step.

```kotlin
Graph(
    modifier = Modifier.fillMaxWidth().height(200.dp),
    xValues = listOf(0, 1, 2, 3, 4, 5, 6),
    yValues = listOf(0, 20, 40, 60, 80, 100),
    points = listOf(10f, 75f, 40f, 90f, 55f, 30f, 85f),
    paddingSpace = 16.dp,
    verticalStep = 20
)
```

---

## SleepGraph
**File:** `customviews/graphs/SleepGraph.kt`

Horizontal sleep-stage timeline. Each `SleepPeriod` is a time range + stage (`AWAKE`, `LIGHT`, `DEEP`, `REM`). The graph renders coloured bars proportional to duration.

```kotlin
SleepGraph(
    periods = listOf(
        SleepPeriod(startMs, endMs, SleepStage.DEEP),
        SleepPeriod(endMs, laterMs, SleepStage.REM),
        // …
    )
)
```

---

## StackedBarChart
**File:** `customviews/graphs/StackedBar.kt`

Animated horizontal stacked bar. Bars fade in with `Animatable` on first composition.

```kotlin
StackedBarChart(
    data = listOf(30f, 20f, 50f),
    colors = listOf(Color.Blue, Color.Green, Color.Red)
)
```
