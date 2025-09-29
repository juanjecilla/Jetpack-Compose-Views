package com.scallop.jetpackcomposeviews.customviews.selectors

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Original code: https://proandroiddev.com/how-i-made-my-own-scrollbar-in-android-compose-with-drag-support-7fd492e308fc#9326
 */
@Composable
fun MediumScrollView() {
    val scrollState = rememberScrollState()
    var viewSize by remember { mutableStateOf(0f) }

    Box {
        Column(
            modifier = Modifier
                .onGloballyPositioned {
                    viewSize = it.size.height.toFloat()
                }
                .verticalScroll(scrollState)
        ) {
            repeat(100) {
                Text(
                    "🍽️ Menu Item #${it + 1}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .background(Color(0xffd7f7ff), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                )
            }
        }
        GesturedScrollBar(
            state = scrollState,
            viewSize = viewSize,
            direction = Direction.Vertical
        )
    }
}

@Composable
private fun BoxScope.GesturedScrollBar(
    state: ScrollState,
    direction: Direction,
    viewSize: Float,
    thickness: Dp = ScrollViewDefaults.scrollBarThickness,
    minLength: Dp = ScrollViewDefaults.scrollBarMinLength,
    color: Color = ScrollViewDefaults.scrollBarColor
) {
    val coroutineScope = rememberCoroutineScope()
    var isDraggingScrollbar by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (state.isScrollInProgress || isDraggingScrollbar) 1f else 0f,
        animationSpec = tween(
            400,
            delayMillis = if (state.isScrollInProgress || isDraggingScrollbar) 0 else 700
        ),
        label = "ScrollBarAlphaAnimation"
    )

    if (viewSize <= 0f) return
    val contentSize = state.maxValue + viewSize
    if (contentSize <= viewSize) return

    val scrollbarSize = with(LocalDensity.current) {
        (viewSize * (viewSize / contentSize)).coerceIn(minLength.toPx()..viewSize)
    }
    val variableZone = viewSize - scrollbarSize

    val scrollOffset = (state.value.toFloat() / state.maxValue) * variableZone

    val isVertical = direction == Direction.Vertical
    val modifier = if (isVertical) {
        Modifier
            .fillMaxHeight()
            .width(thickness)
            .align(Alignment.CenterEnd)
    } else {
        Modifier
            .fillMaxWidth()
            .height(thickness)
            .align(Alignment.BottomCenter)
    }
    Box(
        modifier = modifier
            .pointerInput(state) {
                val onStart: (Offset) -> Unit = { isDraggingScrollbar = true }
                val onDrag: (PointerInputChange, Float) -> Unit = { _, dragAmount ->
                    val deltaScroll = (dragAmount / variableZone) * state.maxValue.toFloat()
                    coroutineScope.launch {
                        state.scrollTo(
                            (state.value + deltaScroll).coerceIn(
                                0f,
                                state.maxValue.toFloat()
                            ).toInt()
                        )
                    }
                }
                val onEnd = { isDraggingScrollbar = false }
                if (isVertical) {
                    detectVerticalDragGestures(
                        onDragStart = onStart,
                        onVerticalDrag = onDrag,
                        onDragEnd = onEnd,
                        onDragCancel = onEnd
                    )
                } else {
                    detectHorizontalDragGestures(
                        onDragStart = onStart,
                        onHorizontalDrag = onDrag,
                        onDragEnd = onEnd,
                        onDragCancel = onEnd
                    )
                }
            }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            if (isVertical) {
                drawRoundRect(
                    topLeft = Offset(size.width - thickness.toPx(), scrollOffset),
                    size = Size(thickness.toPx(), scrollbarSize),
                    cornerRadius = CornerRadius(thickness.toPx() / 2),
                    color = color,
                    alpha = alpha
                )
            } else {
                drawRoundRect(
                    topLeft = Offset(scrollOffset, size.height - thickness.toPx()),
                    size = Size(scrollbarSize, thickness.toPx()),
                    cornerRadius = CornerRadius(thickness.toPx() / 2),
                    color = color,
                    alpha = alpha
                )
            }
        }
    }
}

private enum class Direction {
    Vertical,
    Horizontal
}

private object ScrollViewDefaults {
    val scrollBarThickness = 8.dp
    val scrollBarMinLength = 50.dp
    val scrollBarColor = Color.LightGray
}


@Preview
@Composable
fun MediumScrollView_Preview() {
    MediumScrollView()
}
