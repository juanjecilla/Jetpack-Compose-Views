package com.scallop.jetpackcomposeviews.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout

@Composable
fun ShimmerLayout(
    loading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Track the y co-ord we have placed children up to
            var yPosition = 0

            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}

fun Modifier.takeHalfParentWidthAndCenter(): Modifier =
    this.layout { measurable, constraints ->
        val maxWidthAllowedByParent = constraints.maxWidth
        val placeable = measurable.measure(
            constraints.copy(minWidth = maxWidthAllowedByParent / 2)
        )

        layout(placeable.width, placeable.height) {
            placeable.placeRelative(
                maxWidthAllowedByParent / 2 - placeable.width / 2,
                0
            )
        }
    }