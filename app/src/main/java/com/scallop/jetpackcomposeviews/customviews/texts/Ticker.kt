package com.scallop.jetpackcomposeviews.customviews.texts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

// https://gist.githubusercontent.com/fvilarino/a99849319e495f834a55e59ff322bf46/raw/08d1e15012a0bb7afdfbad6df60c9f6ae3e5eaf4/ticker_final.kt
// https://fvilarino.medium.com/creating-a-ticker-board-in-jetpack-compose-dfbfe4358dd9
private val TickerCycleMillis = 150

private object AlphabetMapper {
    private val Alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789•".toList()

    val size: Int = Alphabet.size

    fun getLetterAt(index: Int): Char = Alphabet[index % size]

    fun getIndexOf(letter: Char, offset: Int = 0): TickerIndex {
        var index = Alphabet.indexOf(letter.uppercaseChar())
        index = if (index < 0) Alphabet.lastIndex else index
        val offsetIndex = if (index < offset) {
            index + (size * (offset / size + 1))
        } else {
            index
        }
        return TickerIndex(rawIndex = index, offsetIndex = offsetIndex)
    }
}

@JvmInline
value class TickerIndex private constructor(private val packedIndex: Int) {
    val index: Int
        get() = (packedIndex and 0xFFFF0000.toInt()) shr 16

    val offsetIndex: Int
        get() = packedIndex and 0x0000FFFF

    companion object {
        operator fun invoke(
            rawIndex: Int,
            offsetIndex: Int,
        ) = TickerIndex(
            ((rawIndex and 0x0000FFFF) shl 16) + (offsetIndex and 0x0000FFFF)
        )
    }
}

@Stable
class TickerStateHolder {
    private val animatable = Animatable(0f)

    val value: Float
        get() = animatable.value

    val index: Int
        get() = animatable.value.toInt()

    suspend fun animateTo(target: TickerIndex) {
        val currentIndex = animatable.value.toInt()
        val result = animatable.animateTo(
            targetValue = target.offsetIndex.toFloat(),
            animationSpec = tween(
                durationMillis = (target.offsetIndex - currentIndex) * TickerCycleMillis,
                easing = FastOutSlowInEasing,
            )
        )
        if (result.endReason == AnimationEndReason.Finished) {
            snapTo(target.index)
        }
    }

    private suspend fun snapTo(index: Int) {
        animatable.snapTo(index.toFloat())
    }
}

@Composable
fun TickerBoard(
    text: String,
    numColumns: Int,
    numRows: Int,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    backgroundColor: Color = Color.Black,
    fontSize: TextUnit = 96.sp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
) {
    val padded = text.padEnd(numColumns * numRows, ' ')
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
    ) {
        repeat(numRows) { row ->
            TickerRow(
                text = padded.substring(startIndex = row * numColumns),
                numCells = numColumns,
                horizontalArrangement = horizontalArrangement,
                textColor = textColor,
                backgroundColor = backgroundColor,
                fontSize = fontSize,
            )
        }
    }
}

@Composable
fun TickerRow(
    text: String,
    numCells: Int,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    backgroundColor: Color = Color.Black,
    fontSize: TextUnit = 96.sp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        repeat(numCells) { index ->
            Ticker(
                letter = text.getOrNull(index) ?: ' ',
                textColor = textColor,
                backgroundColor = backgroundColor,
                fontSize = fontSize
            )
        }
    }
}

@Composable
fun rememberTickerState() = remember {
    TickerStateHolder()
}

@Composable
fun Ticker(
    letter: Char,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    backgroundColor: Color = Color.Black,
    fontSize: TextUnit = 96.sp,
    contentPadding: PaddingValues = PaddingValues(all = 8.dp),
    state: TickerStateHolder = rememberTickerState(),
) {
    LaunchedEffect(key1 = letter) {
        val currentIndex = state.index
        val index = AlphabetMapper.getIndexOf(letter = letter, offset = currentIndex)
        state.animateTo(index)
    }
    val fraction = state.value - state.value.toInt()
    val rotation = -180f * fraction
    val currentLetter = AlphabetMapper.getLetterAt(state.index)
    val nextLetter = AlphabetMapper.getLetterAt(state.index + 1)
    Box(
        modifier = modifier
    ) {
        BackgroundLetter(
            currentLetter = currentLetter,
            nextLetter = nextLetter,
            textColor = textColor,
            backgroundColor = backgroundColor,
            fontSize = fontSize,
            contentPadding = contentPadding,
        )
        Box(
            modifier = Modifier
                .graphicsLayer {
                    rotationX = rotation
                    cameraDistance = 6f * density
                    transformOrigin = TransformOrigin(.5f, 1f)
                }
        ) {
            if (fraction <= .5f) {
                TopHalf {
                    CenteredText(
                        letter = currentLetter,
                        contentPadding = contentPadding,
                        textColor = textColor,
                        backgroundColor = backgroundColor,
                        fontSize = fontSize,
                    )
                }
            } else {
                BottomHalf(
                    modifier = Modifier.graphicsLayer {
                        rotationX = 180f
                    }
                ) {
                    CenteredText(
                        letter = nextLetter,
                        contentPadding = contentPadding,
                        textColor = textColor,
                        backgroundColor = backgroundColor,
                        fontSize = fontSize,
                    )
                }
            }
        }
    }
}

@Composable
private fun BackgroundLetter(
    currentLetter: Char,
    nextLetter: Char,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    backgroundColor: Color = Color.Black,
    fontSize: TextUnit = 96.sp,
    contentPadding: PaddingValues = PaddingValues(all = 8.dp),
) {
    Column(
        modifier = modifier,
    ) {
        TopHalf {
            CenteredText(
                letter = nextLetter,
                textColor = textColor,
                backgroundColor = backgroundColor,
                fontSize = fontSize,
                contentPadding = contentPadding
            )
        }
        BottomHalf {
            CenteredText(
                letter = currentLetter,
                textColor = textColor,
                backgroundColor = backgroundColor,
                fontSize = fontSize,
                contentPadding = contentPadding
            )
        }
    }
}

@Composable
private fun TopHalf(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    HalfChild(
        modifier = modifier,
        topHalf = true,
        content = content,
    )
}

@Composable
private fun BottomHalf(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    HalfChild(
        modifier = modifier,
        topHalf = false,
        content = content,
    )
}

@Composable
private fun HalfChild(
    modifier: Modifier = Modifier,
    topHalf: Boolean = true,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier.clipToBounds(),
        content = content,
    ) { measurables, constraints ->
        require(measurables.size == 1) { "This composable expects a single child" }

        val placeable = measurables.first().measure(constraints)
        val height = placeable.height / 2

        layout(
            width = placeable.width,
            height = height,
        ) {
            placeable.placeRelative(
                x = 0,
                y = if (topHalf) 0 else -height,
            )
        }
    }
}

@Composable
private fun CenteredText(
    letter: Char,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    backgroundColor: Color = Color.Black,
    fontSize: TextUnit = 96.sp,
    contentPadding: PaddingValues = PaddingValues(all = 8.dp),
) {
    var ascent by remember {
        mutableStateOf(0f)
    }
    var middle by remember {
        mutableStateOf(0f)
    }
    var baseline by remember {
        mutableStateOf(0f)
    }
    var top by remember {
        mutableStateOf(0f)
    }
    var bottom by remember {
        mutableStateOf(0f)
    }
    val delta: Float by remember {
        derivedStateOf {
            ((bottom - baseline) - (ascent - top)) / 2f
        }
    }

    val direction = LocalLayoutDirection.current
    val startPadding = contentPadding.calculateStartPadding(direction)
    val endPadding = contentPadding.calculateEndPadding(direction)
    Text(
        text = letter.toString(),
        color = textColor,
        fontFamily = FontFamily.Monospace,
        fontSize = fontSize,
        modifier = modifier
            .background(backgroundColor)
            .padding(paddingValues = contentPadding)
            .drawBehind {
                drawLine(
                    textColor,
                    Offset(x = -startPadding.value * density, y = center.y),
                    Offset(
                        x = size.width + (startPadding + endPadding).value * density,
                        y = center.y
                    ),
                    strokeWidth = 2f * density,
                )
            }
            .offset {
                IntOffset(x = 0, y = delta.roundToInt())
            },
        onTextLayout = { textLayoutResult ->
            val layoutInput = textLayoutResult.layoutInput
            val fontSizePx = with(layoutInput.density) { layoutInput.style.fontSize.toPx() }
            baseline = textLayoutResult.firstBaseline
            top = textLayoutResult.getLineTop(0)
            bottom = textLayoutResult.getLineBottom(0)
            middle = bottom - top
            ascent = bottom - fontSizePx
        }
    )
}
