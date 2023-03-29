package com.scallop.jetpackcomposeviews.effects

import android.content.res.Resources
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.*
import kotlin.math.pow

// https://proandroiddev.com/creating-a-particle-explosion-animation-in-jetpack-compose-4ee42022bbfa
@Composable
fun ControlledExplosion() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var progress by remember { mutableStateOf(0f) }

        Explosion(progress)

        Slider(
            modifier = Modifier.width(200.dp),
            value = progress,
            onValueChange = {
                progress = it
            }
        )
    }
}

@Composable
fun Explosion(progress: Float) {
    val sizeDp = 200.dp
    val sizePx = sizeDp.toPx()
    val sizePxHalf = sizePx / 2
    val particles = remember {
        List(150) {
            Particle(
                color = Color(listOf(0xffea4335, 0xff4285f4, 0xfffbbc05, 0xff34a853).random()),
                startXPosition = sizePxHalf.toInt(),
                startYPosition = sizePxHalf.toInt(),
                maxHorizontalDisplacement = sizePx * randomInRange(-0.9f, 0.9f),
                maxVerticalDisplacement = sizePx * randomInRange(0.2f, 0.38f)
            )
        }
    }
    particles.forEach { it.updateProgress(progress) }

    Canvas(
        modifier = Modifier
            .border(width = 1.dp, color = Color(0x26000000))
            .size(sizeDp)
    ) {
        drawLine(
            color = Color.Black,
            start = Offset(sizePxHalf, 0f),
            end = Offset(sizePxHalf, sizePx),
            strokeWidth = 2.dp.toPx()
        )
        drawLine(
            color = Color.Black,
            start = Offset(0f, sizePxHalf),
            end = Offset(sizePx, sizePxHalf),
            strokeWidth = 2.dp.toPx()
        )
        particles.forEach { particle ->
            drawCircle(
                alpha = particle.alpha,
                color = particle.color,
                radius = particle.currentRadius,
                center = Offset(particle.currentXPosition, particle.currentYPosition),
            )
        }
    }
}

class Particle(
    val color: Color,
    val startXPosition: Int,
    val startYPosition: Int,
    val maxHorizontalDisplacement: Float,
    val maxVerticalDisplacement: Float
) {
    val velocity = 4 * maxVerticalDisplacement
    val acceleration = -2 * velocity
    var currentXPosition = 0f
    var currentYPosition = 0f

    var visibilityThresholdLow = randomInRange(0f, 0.14f)
    var visibilityThresholdHigh = randomInRange(0f, 0.4f)

    val initialXDisplacement = 10.dp.toPx() * randomInRange(-1f, 1f)
    val initialYDisplacement = 10.dp.toPx() * randomInRange(-1f, 1f)

    var alpha = 0f
    var currentRadius = 0f
    val startRadius = 2.dp.toPx()
    val endRadius = if (randomBoolean(trueProbabilityPercentage = 20)) {
        randomInRange(startRadius, 7.dp.toPx())
    } else {
        randomInRange(1.5.dp.toPx(), startRadius)
    }

    fun updateProgress(explosionProgress: Float) {
        val trajectoryProgress =
            if (explosionProgress < visibilityThresholdLow || (explosionProgress > (1 - visibilityThresholdHigh))) {
                alpha = 0f; return
            } else (explosionProgress - visibilityThresholdLow).mapInRange(
                0f,
                1f - visibilityThresholdHigh - visibilityThresholdLow,
                0f,
                1f
            )
        alpha = if (trajectoryProgress < 0.7f) 1f else (trajectoryProgress - 0.7f).mapInRange(
            0f,
            0.3f,
            1f,
            0f
        )
        currentRadius = startRadius + (endRadius - startRadius) * trajectoryProgress
        val currentTime = trajectoryProgress.mapInRange(0f, 1f, 0f, 1.4f)
        val verticalDisplacement =
            (currentTime * velocity + 0.5 * acceleration * currentTime.toDouble()
                .pow(2.0)).toFloat()
        currentYPosition = startXPosition + initialXDisplacement - verticalDisplacement
        currentXPosition =
            startYPosition + initialYDisplacement + maxHorizontalDisplacement * trajectoryProgress
    }
}

fun Float.mapInRange(inMin: Float, inMax: Float, outMin: Float, outMax: Float): Float {
    return outMin + (((this - inMin) / (inMax - inMin)) * (outMax - outMin))
}

fun Int.dpToPx() = toFloat().dpToPx()
fun Dp.toPx() = value.dpToPx()

fun Float.dpToPx() = this * Resources.getSystem().displayMetrics.density

private val random = Random()
fun Float.randomTillZero() = this * random.nextFloat()
fun randomInRange(min: Float, max: Float) = min + (max - min).randomTillZero()
fun randomBoolean(trueProbabilityPercentage: Int) = random.nextFloat() < trueProbabilityPercentage / 100f
