package com.scallop.jetpackcomposeviews.effects

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp

/**
 * Original code: https://proandroiddev.com/how-to-create-a-tubelight-effect-in-android-compose-2383befc47b1
 */
@Composable
fun TubeLightEffect(modifier: Modifier) {

    var clicked by remember { mutableStateOf(false) }

    val animProgress by animateFloatAsState(
        targetValue = if (clicked) 1f else 0f,
        animationSpec = tween(
            400,
            easing = FastOutSlowInEasing
        ),
        label = "click"
    )

    val animProgress2 by animateFloatAsState(
        targetValue = if (clicked) 1f else 0f,
        animationSpec = tween(
            700,
            easing = FastOutSlowInEasing
        ),
        label = "click"
    )

    var value by remember { mutableFloatStateOf(1.0f) }
    var value2 by remember { mutableFloatStateOf(1.0f) }


    val startY = 160f + 16 * animProgress
    val progress = animProgress2
    val tubeWidth = 300f * animProgress

    Box {

        Column(
            modifier = modifier
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Button(
                modifier = Modifier.padding(bottom = 32.dp),
                onClick = {
                    clicked = !clicked
                }
            ) {
                Text("Start")
            }

//            Slider(
//                value = value,
//                onValueChange = { value = it },
//                valueRange = 0f..1f,
//                modifier = Modifier
//                    .padding(16.dp)
//            )
//            Slider(
//                value = value2,
//                onValueChange = { value2 = it },
//                valueRange = 0f..1f,
//                modifier = Modifier
//                    .padding(16.dp)
//            )
        }

        Text(
            modifier = modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp),
            text = "Tubelight Effect",
            color = Color(0xFFFF9800),
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = modifier.fillMaxSize()
        ) {
            Light(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                flip = false,
                startY = startY,
                progress = progress,
                halfTubeWidth = tubeWidth
            )
            Light(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                startY = startY,
                progress = progress,
                halfTubeWidth = tubeWidth
            )
        }
    }

}

@Composable
@Preview
private fun Ineffectualness_Preview() {
    TubeLightEffect(Modifier)
}

@SuppressLint("RestrictedApi")
@Composable
fun Light(
    mainGlowColor: Color = Color(0xFFFF9800),
    glowColor: Color = Color(0xFFE91E63),
    flip: Boolean = true,
    startY: Float,
    progress: Float,
    modifier: Modifier,
    halfTubeWidth: Float,
) {
    val animationProgress = lerp(0.5f, 1f, progress)

    Box(
        modifier = modifier
            .alpha(progress)
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithCache {
                val centerOffset = Offset(
                    x = size.width - halfTubeWidth,
                    y = startY
                )

                val sweepGradient1 = Brush.sweepGradient(
                    colorStops = arrayOf(
                        0.0f to mainGlowColor,
                        animationProgress * 0.49f to Color.Transparent,
                        1.0f to Color.Transparent,
                    ),
                    center = centerOffset
                )

                val sweepGradient2 = Brush.sweepGradient(
                    colorStops = arrayOf(
                        0.0f to glowColor,
                        0.0f to glowColor.copy(alpha = animationProgress * 0.65f),
                        animationProgress * 0.35f to Color.Transparent,
                        1.0f to Color.Transparent,
                    ),
                    center = centerOffset
                )

                val start = 20.dp.toPx()
                val end = 450.dp.toPx()

                val mask = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0f to Color.White,
                        start / size.height to Color.White,
                        (start + (end - start) * 0.25f) / size.height to Color.White.copy(alpha = 0.7f),
                        (start + (end - start) * 0.55f) / size.height to Color.White.copy(alpha = 0.35f),
                        (start + (end - start) * 0.7f) / size.height to Color.White.copy(alpha = 0.15f),
                        end / size.height to Color.Transparent,
                        1f to Color.Transparent
                    )
                )

                onDrawBehind {
                    scale(
                        scaleX = if (flip) -1f else 1f,
                        scaleY = 1f
                    ) {
                        drawRect(
                            brush = sweepGradient1,
                            blendMode = BlendMode.Plus
                        )
                        drawRect(
                            brush = sweepGradient2,
                            blendMode = BlendMode.Plus
                        )
                    }

                    drawRect(
                        brush = mask,
                        blendMode = BlendMode.DstIn,
                        alpha = 0.98f
                    )
                }
            }
    )
}
