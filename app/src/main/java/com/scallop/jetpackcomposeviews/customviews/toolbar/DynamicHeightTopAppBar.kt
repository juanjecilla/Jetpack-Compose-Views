package com.scallop.jetpackcomposeviews.customviews.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicHeightTopAppBar(
    minHeight: Dp,
    maxHeight: Dp,
    scrollBehavior: TopAppBarScrollBehavior?,
    modifier: Modifier = Modifier,
    content: @Composable (offsetDp: Dp, statusBarHeight: Dp) -> Unit
) {
    val density = LocalDensity.current
    val maxHeightPx: Float
    val minHeightPx: Float

    with(density) {
        maxHeightPx = maxHeight.toPx()
        minHeightPx = minHeight.toPx()
    }

    // Set the app bar's height offset limit to collapse from maxHeight to minHeight
    SideEffect {
        if (scrollBehavior?.state?.heightOffsetLimit != minHeightPx - maxHeightPx) {
            scrollBehavior?.state?.heightOffsetLimit = minHeightPx - maxHeightPx
        }
    }

    // Set up support for resizing the top app bar when vertically dragging the bar itself
    val appBarDragModifier =
        if (scrollBehavior != null && !scrollBehavior.isPinned) {
            Modifier.draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    scrollBehavior.state.heightOffset += delta
                },
                onDragStopped = { velocity ->
                    /*   settleAppBar(
                           scrollBehavior.state,
                           velocity,
                           scrollBehavior.flingAnimationSpec,
                           scrollBehavior.snapAnimationSpec
                       )

                     */
                }
            )
        } else {
            Modifier
        }

    // Calculate current offset in Dp to pass to content
    val currentHeightOffset = scrollBehavior?.state?.heightOffset ?: 0f
    val offsetDp = with(density) { currentHeightOffset.absoluteValue.toDp() }

    // Get status bar height
    val statusBarHeight = with(density) {
        WindowInsets.statusBars.getTop(density).toDp()
    }

    // Calculate total height
    val currentHeight = maxHeight + statusBarHeight + with(density) { currentHeightOffset.toDp() }

    Surface(modifier = modifier.then(appBarDragModifier), color = Color.Transparent) {
        Box(
            modifier = Modifier
                .clipToBounds()
                .height(currentHeight)
        ) {
            content(offsetDp, statusBarHeight)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(modifier: Modifier = Modifier, scrollBehavior: TopAppBarScrollBehavior) {
    DynamicHeightTopAppBar(
        scrollBehavior = scrollBehavior,
        modifier = modifier.fillMaxWidth(),
        minHeight = 56.dp,
        maxHeight = 160.dp
    ) { offsetDp, statusBarHeight ->

        // 1. Calculate Progress (0.0 -> Collapsed, 1.0 -> Expanded)
        val maxOffset = 104.dp // (maxHeight - minHeight)
        val progress = 1f - (offsetDp.value / maxOffset.value).coerceIn(0f, 1f)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = statusBarHeight) // Respect status bar
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 2. Dynamic Avatar Size
                val avatarSize = (20.dp.value + (50.dp.value - 20.dp.value) * progress).dp

                /*
                UserAvatar(
                    borderSize = (1f + progress).dp,
                    backgroundColor = Color.LightGray,
                    avatarSize = avatarSize
                )

                 */

                // 3. Dynamic Font Size
                val fontSize = (14f + (24f - 14f) * progress).sp

                Text(
                    text = "John Doe",
                    fontSize = fontSize,
                    modifier = Modifier.weight(1f)
                )

                // 4. Fade out actions
                // Fade out quickly: 0dp to 30dp scroll
                val actionAlpha = (1f - (offsetDp.value / 30f)).coerceIn(0f, 1f)

                Row(
                    modifier = Modifier.alpha(actionAlpha),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Action Icons...
                }
            }
        }
    }
}
