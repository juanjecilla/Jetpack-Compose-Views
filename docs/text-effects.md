# Text Effects

## MarqueeText
**File:** `customviews/texts/Marquee.kt`

Continuously scrolling text with gradient fade on both edges.

```kotlin
MarqueeText(
    text = "Your very long scrolling message goes here  ",
    fontSize = 18.sp,
    gradientEdgeColor = Color.White
)
```

Also exposes `Marquee { ... }` for wrapping arbitrary content.

---

## ShimmeringText
**File:** `customviews/texts/ShimmerText.kt`

Applies a linear gradient shimmer sweep over text using `ShaderBrush` and `InfiniteTransition`.

```kotlin
ShimmeringText(
    text = "Shimmering",
    shimmerColor = Color(0xFF6200EE),
    textStyle = TextStyle(fontSize = 24.sp, color = Color.DarkGray)
)
```

---

## TickerBoard
**File:** `customviews/texts/Ticker.kt`

Flip-board style counter/display. Each character animates through the alphabet.

```kotlin
TickerBoard(text = "HELLO", numColumns = 5, numRows = 1, fontSize = 32.sp)
```

Also exposes `TickerRow` (single row) and `Ticker` (single cell).

---

## Typewriter
**File:** `customviews/texts/TypewriterText.kt`

Animated typing effect. A static `baseText` is combined with a cycling list of `parts` that type and erase. Optionally underlines a `highlightedText` span.

```kotlin
Typewriter(
    baseText = "I build ",
    highlightedText = "build",
    parts = listOf("Android apps", "Compose UIs", "great experiences")
)
```

---

## AutoSpacedText
**File:** `customviews/texts/AutospacingText.kt`

Adjusts letter spacing so text fills the full container width.

```kotlin
AutoSpacedText(text = "COMPOSE", modifier = Modifier.fillMaxWidth())
```

---

## DottedText / DotMatrixDisplay
**File:** `customviews/texts/DottedText.kt`

Renders text as a retro dot-matrix bitmap. Converts the text to a `Bitmap`, samples it into a grid, then draws coloured circles.

```kotlin
DottedText(
    modifier = Modifier.fillMaxWidth().height(100.dp),
    text = "HI",
    textScale = 3f
)
```

---

## AutoScrollText
**File:** `customviews/texts/AutoScrollText.kt`

Single-line text that auto-scrolls horizontally using the built-in `basicMarquee` modifier (API 23+).

---

## DebouncedTextField
**File:** `customviews/texts/DebouncedTextField.kt`

`TextField` that debounces `onValueChange` to avoid excessive recompositions on fast typing.

```kotlin
DebouncedTextField(debounceTime = 500L) { value -> /* ... */ }
```
