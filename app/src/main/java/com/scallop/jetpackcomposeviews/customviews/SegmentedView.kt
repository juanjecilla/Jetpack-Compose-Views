package com.scallop.jetpackcomposeviews.customviews

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Composable function that renders a customizable Seven-Segment View to display a numeric value.
 *
 * @param number The numeric value to be displayed.
 * @param modifier Modifier for adjusting layout and appearance of the view.
 * @param activeColor Color used for displaying active segments of the Seven-Segment View.
 * @param inactiveColor Color used for displaying inactive segments of the Seven-Segment View.
 * @param digitsNumber Number of digits to be shown in the Seven-Segment View.
 * @param digitsSpace Spacing between individual digits.
 * @param segmentWidth Width of the segments within the Seven-Segment View.
 * @param segmentsSpace Spacing between segments within each digit.
 * @throws IllegalArgumentException if [digitsNumber] is less than or equal to 0 or if [number] is negative.
 */
@Composable
fun SevenSegmentView(
    number: Int,
    modifier: Modifier,
    activeColor: Color,
    inactiveColor: Color = activeColor.copy(0.16f),
    digitsNumber: Int = 1,
    digitsSpace: Dp = 4.dp,
    segmentWidth: Dp = 4.dp,
    segmentsSpace: Dp = 0.dp
) {
    require(digitsNumber > 0) { "Digits number should be greater than 0" }
    require(number >= 0) { "The number has to be positive" }

    var digits = remember(number) { number.splitToDigits() }

    if (digits.size > digitsNumber) {
        digits = digits.takeLast(digitsNumber)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(digitsSpace),
        verticalAlignment = Alignment.CenterVertically
    ) {
        digits.padToStart(digitsNumber, null).forEach { digit ->
            val state = digit?.getSegmentsState() ?: SegmentsState()
            SingleSevenSegment(
                state = state,
                modifier = Modifier.fillMaxHeight(),
                activeColor = activeColor,
                inactiveColor = inactiveColor,
                segmentWidth = segmentWidth,
                segmentsSpace = segmentsSpace
            )
        }
    }
}

@Composable
private fun SingleSevenSegment(
    state: SegmentsState,
    modifier: Modifier,
    activeColor: Color,
    inactiveColor: Color,
    segmentWidth: Dp,
    segmentsSpace: Dp
) {
    Canvas(
        modifier = modifier.aspectRatio(0.5f, matchHeightConstraintsFirst = true)
    ) {
        val halfViewHeight = (size.height / 2)
        val halfWidth = (segmentWidth.toPx() / 2)

        val rightEdge = (size.width - halfWidth)
        val bottomEdge = (size.height - halfWidth)

        val segmentData = listOf(
            SegmentData(state.a, isVertical = false, halfWidth, rightEdge, halfWidth, halfWidth),
            SegmentData(state.b, isVertical = true, rightEdge, rightEdge, halfWidth, halfViewHeight),
            SegmentData(state.c, isVertical = true, rightEdge, rightEdge, halfViewHeight, bottomEdge),
            SegmentData(state.d, isVertical = false, halfWidth, rightEdge, bottomEdge, bottomEdge),
            SegmentData(state.e, isVertical = true, halfWidth, halfWidth, halfViewHeight, bottomEdge),
            SegmentData(state.f, isVertical = true, halfWidth, halfWidth, halfWidth, halfViewHeight),
            SegmentData(state.g, isVertical = false, halfWidth, rightEdge, halfViewHeight, halfViewHeight)
        )

        segmentData.forEach { data ->
            drawSegment(
                color = if (data.isActive) activeColor else inactiveColor,
                data = data.addSpacing(segmentsSpace.toPx()),
                halfWidth = halfWidth,
            )
        }
    }
}

private fun DrawScope.drawSegment(
    color: Color,
    data: SegmentData,
    halfWidth: Float
) {
    val segmentPath = Path().apply {
        with(data) {
            moveTo(startX, startY)
            if (isVertical) {
                lineTo(startX + halfWidth, startY + halfWidth)
                lineTo(startX + halfWidth, endY - halfWidth)
                lineTo(endX, endY)
                lineTo(startX - halfWidth, endY - halfWidth)
                lineTo(startX - halfWidth, startY + halfWidth)
            } else {
                lineTo(startX + halfWidth, startY - halfWidth)
                lineTo(endX - halfWidth, startY - halfWidth)
                lineTo(endX, endY)
                lineTo(endX - halfWidth, startY + halfWidth)
                lineTo(startX + halfWidth, startY + halfWidth)
            }
            close()
        }
    }

    drawPath(segmentPath, color)
}

private data class SegmentsState(
    val a: Boolean = false,
    val b: Boolean = false,
    val c: Boolean = false,
    val d: Boolean = false,
    val e: Boolean = false,
    val f: Boolean = false,
    val g: Boolean = false
)

private data class SegmentData(
    val isActive: Boolean,
    val isVertical: Boolean,
    val startX: Float,
    val endX: Float,
    val startY: Float,
    val endY: Float
)

private val SevenSegmentNumbers = mapOf(
    0 to SegmentsState(a = true, b = true, c = true, d = true, e = true, f = true),
    1 to SegmentsState(b = true, c = true),
    2 to SegmentsState(a = true, b = true, d = true, e = true, g = true),
    3 to SegmentsState(a = true, b = true, c = true, d = true, g = true),
    4 to SegmentsState(b = true, c = true, f = true, g = true),
    5 to SegmentsState(a = true, c = true, d = true, f = true, g = true),
    6 to SegmentsState(a = true, c = true, d = true, e = true, f = true, g = true),
    7 to SegmentsState(a = true, b = true, c = true),
    8 to SegmentsState(a = true, b = true, c = true, d = true, e = true, f = true, g = true),
    9 to SegmentsState(a = true, b = true, c = true, d = true, f = true, g = true),
)

private fun <T> List<T>.padToStart(size: Int, value: T): List<T> {
    require(size >= 0) { "Size should not be negative" }
    return if (this.size < size) {
        List(size - this.size) { value } + this
    } else {
        this
    }
}

private fun SegmentData.addSpacing(space: Float): SegmentData {
    return when {
        isVertical -> this.copy(
            startY = (this.startY + space),
            endY = (this.endY - space)
        )
        else -> this.copy(
            startX = (this.startX + space),
            endX = (this.endX - space)
        )
    }
}

private fun Int.getSegmentsState(): SegmentsState {
    return SevenSegmentNumbers.getOrElse(this) {
        throw IllegalArgumentException("The digit must be in the range from 0 to 9")
    }
}

private fun Int.splitToDigits(): List<Int> {
    return this.toString().map(Char::digitToInt)
}
