# Special Views

## AddCreditCard
**File:** `customviews/CreditCards.kt`

A credit card UI with a 3D flip animation. Tapping toggles between the card front and back face. Uses `graphicsLayer { rotationY = … }` with `cameraDistance`.

```kotlin
AddCreditCard(
    backgroundColor = Color(0xFF1A237E),
    cardNumber = "**** **** **** 1234",
    cardHolder = "John Doe",
    cardExpiry = "12/26"
)
```

---

## FUTLikeCardUI
**File:** `customviews/cards/FutCards.kt`

Replicates the visual style of a FIFA Ultimate Team player card. Uses `BlendMode` and `ImageBitmap` masking to composite a player photo over the card shape (`img_card_shape.png`).

```kotlin
FUTLikeCardUI()
```

---

## SevenSegmentView
**File:** `customviews/SegmentedView.kt`

Draws a digital 7-segment display using `Canvas` paths. Supports configurable digit count, active/inactive segment colours, and spacing.

```kotlin
SevenSegmentView(
    number = 42,
    modifier = Modifier.size(width = 100.dp, height = 60.dp),
    activeColor = Color(0xFF00FF00),
    digitsNumber = 2
)
```

---

## Mirror
**File:** `customviews/Images.kt`

Renders content twice — once normally, once flipped with a vertical gradient and blur to simulate a reflective surface.

```kotlin
Mirror {
    Image(painter = painterResource(R.drawable.my_image), contentDescription = null)
}
```

---

## AutoScrollingLazyRow
**File:** `customviews/LazyRows.kt`

A `LazyRow` that continuously scrolls its items without user interaction. Items are duplicated internally to create a seamless loop.

---

## DateCarousel / DialerWeekCalendar
See [Selectors](selectors.md#carouselcalendar--dialerweekCalendar).

---

## DottedShape
**File:** `customviews/DottedLine.kt`

A `Shape` that draws a dotted/dashed outline. Use it as a `border` shape or background.
