package com.scallop.jetpackcomposeviews.customviews

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class Star(
    var x: Float,
    var y: Float,
    var alpha: Float,
) {
    private val initialAlpha = alpha

    fun update(value: Float) {
        val x = (value - initialAlpha).toDouble()
        val newAlpha = 0.5f + (0.5f * Math.sin(x).toFloat())
        alpha = newAlpha
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun Space(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val infinitelyAnimatedFloat = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(10000),
            repeatMode = RepeatMode.Restart
        )
    )
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        val width = maxWidth.toPx()
        val height = maxHeight.toPx()
        val stars = remember {
            buildList {
                repeat(1000) {
                    val x = (Math.random() * width).toFloat()
                    val y = (Math.random() * height).toFloat()
                    val alpha = (Math.random() * 2.0 * Math.PI).toFloat()
                    add(Star(x, y, alpha))
                }
            }
        }
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            for (star in stars) {
                star.update(infinitelyAnimatedFloat.value)
                drawCircle(
                    color = Color.White,
                    center = Offset(star.x, star.y),
                    radius = 2f,
                    alpha = star.alpha,
                )
            }
        }
    }
}
