package com.scallop.jetpackcomposeviews.customviews.graphs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


// https://freedium.cfd/https://jyotimoykashyap.medium.com/building-an-animated-stacked-bar-chart-in-jetpack-compose-9ad2b2acc5e1
@Composable
fun StackedBarChart(
    data: List<Float>,
    colors: List<Color>,
) {
    val proportions = data.map { it.div(data.sum()) }.filter { it > 0 }.toMutableStateList()
    val oldValues = rememberSaveable { mutableListOf<Float>() }
    val animatedProportions = List(proportions.size) { remember { Animatable(0f) } }
    val visibility = remember { mutableStateOf(false) }

    LaunchedEffect(proportions) {
        visibility.value = true
        animatedProportions.forEachIndexed { index, animate ->
            launch {
                if (oldValues.getOrNull(index) == null) {
                    oldValues.add(index, 0f)
                }
                val startValue = oldValues.getOrElse(index) { 0f }
                animate.snapTo(startValue)
                animate.animateTo(
                    targetValue = proportions[index],
                    animationSpec = tween(
                        durationMillis = 3000,
                        easing = FastOutSlowInEasing
                    )
                )

                oldValues[index] = proportions[index]
            }
        }
    }

    Canvas(
        modifier = Modifier
            .height(15.dp)
            .fillMaxWidth(),
    ) {
        var startX = 0f
        val cornerRadius = size.height.div(2).dp.toPx()

        drawRoundRect(
            color = Color.LightGray,
            topLeft = Offset(0f, 0f),
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
            style = Stroke(width = 0.5.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        animatedProportions.forEachIndexed { index, proportion ->
            val segmentWidth = size.width * proportion.value
            val isFirst = index == 0
            val isLast = index == proportions.size - 1

            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            offset = Offset(startX, 0f),
                            size = Size(segmentWidth, size.height)
                        ),
                        topLeft = if (isFirst) CornerRadius(
                            cornerRadius,
                            cornerRadius
                        ) else CornerRadius.Zero,
                        bottomLeft = if (isFirst) CornerRadius(
                            cornerRadius,
                            cornerRadius
                        ) else CornerRadius.Zero,
                        topRight = if (isLast) CornerRadius(
                            cornerRadius,
                            cornerRadius
                        ) else CornerRadius.Zero,
                        bottomRight = if (isLast) CornerRadius(
                            cornerRadius,
                            cornerRadius
                        ) else CornerRadius.Zero
                    )
                )
            }
            drawPath(path, color = colors[index])
            startX += segmentWidth
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        proportions.forEachIndexed { _, proportion ->
            Box(
                modifier = Modifier
                    .weight(proportion)
                    .height(22.dp)
                    .padding(start = 4.dp)
            ) {
                this@Row.AnimatedVisibility(
                    visible = visibility.value,
                    enter = fadeIn(animationSpec = tween(durationMillis = 3000))
                ) {
                    Text(
                        text = "${(proportion * 100).toInt()}%",
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}