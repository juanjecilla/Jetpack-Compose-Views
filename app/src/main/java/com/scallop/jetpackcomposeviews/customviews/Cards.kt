package com.scallop.jetpackcomposeviews.customviews

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.Layout
import kotlin.random.Random

@Composable
fun CardStack(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        content,
        modifier,
    ) { measurables, constraints ->

        val placeables =
            measurables.map { measurable ->
                measurable.measure(constraints)
            }

        val height = if (placeables.isNotEmpty())
            placeables.first().height +
                    (CardStack.EXTRA_PADDING * placeables.size)
        else 0

        val width = if (placeables.isNotEmpty())
            placeables.first().width
        else 0

        layout(width = width, height = height) {
            placeables.mapIndexed { index, placeable ->
                placeable.place(
                    x = if (index % 2 == 0)
                        0
                    else
                        CardStack.X_POSITION,
                    y = CardStack.Y_POSITION * index,
                )
            }
        }
    }
}

@Composable
fun Example(){
    CardStack {
        (0..10).mapIndexed { index, id ->
            val degrees = remember {
                Random.nextInt(-2, 2).toFloat()
            }

            Card(
                modifier = Modifier.rotate(degrees),
            ) {

            }
       }
    }
}

object CardStack {
    const val EXTRA_PADDING = 10
    const val Y_POSITION = 5
    const val X_POSITION = 5
}