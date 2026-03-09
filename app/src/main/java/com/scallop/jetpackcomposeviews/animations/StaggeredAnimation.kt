package com.scallop.jetpackcomposeviews.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Add an [Modifier] configured with the given duration, direction, delay and easing curve.
 * This modifier adds the placement animation to the component. It animates just one time for
 * composition. This should be used for components that do not have state or the visibility
 * of this components is not controlled by the state. For example: Use it to make enterAnimation
 * of static element like button, image, etc.
 *
 * For state-base animations use helper object: [RetroTransition]
 *
 * @param animationDirection direction towards targetValue of animation
 * @param durationMillis the amount of time in milliseconds that animation of positioning takes
 * @param durationMillisAlpha the amount of time in milliseconds that animation of opacity takes
 * @param easing the easing curve that will be used for positioning animation (move)
 * @param alphaEasing the easing curve that will be used for opacity animation
 * If you want to apply negative walue, consider changing the [animationDirection]
 * @param offsetDp value of offset in density pixels.
 * @param placementDelayMillis the amount of time in milliseconds that animation waits before it starts.
 * It can be used to create beautiful staggering effect
 * @param onAnimationFinished is a lambda that is called when placement animation is finished. It takes the delay into account.
 */
fun Modifier.enterAnimation(
    animationDirection: AnimationDirection = AnimationDirection.DOWN,
    durationMillis: Int = 1000,
    durationMillisAlpha: Int = durationMillis / 2,
    easing: Easing = EaseOutBack,
    alphaEasing: Easing = LinearEasing,
    offsetDp: Dp = 12.dp,
    placementDelayMillis: Long = 0L,
    onAnimationFinished: () -> Unit = {}
): Modifier = composed {

    val scope = rememberCoroutineScope()
    val anim = remember { Animatable(1f) }
    val alphaAnim = remember { Animatable(0f) }
    var dimension by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    this
        .onPlaced {
            dimension = with(density) { offsetDp.roundToPx() }

            if (anim.value == 1f) {
                scope.launch {
                    delay(placementDelayMillis)
                    anim.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis, easing = easing)
                    )
                    onAnimationFinished()
                }
                scope.launch {
                    delay(placementDelayMillis)
                    alphaAnim.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillisAlpha, easing = alphaEasing)
                    )
                }
            }
        }
        .offset {
            when (animationDirection) {
                AnimationDirection.UP -> IntOffset(
                    x = 0,
                    y = (dimension * anim.value).toInt()
                )

                AnimationDirection.DOWN -> IntOffset(
                    x = 0,
                    y = -(dimension * anim.value).toInt()
                )

                AnimationDirection.LEFT -> IntOffset(
                    x = (dimension * anim.value).toInt(),
                    y = 0
                )

                AnimationDirection.RIGHT -> IntOffset(
                    x = -(dimension * anim.value).toInt(),
                    y = 0
                )

                AnimationDirection.SCALE_IN -> IntOffset.Zero
                AnimationDirection.SCALE_OUT -> IntOffset.Zero
            }
        }
        .graphicsLayer {
            val scale = when (animationDirection) {
                AnimationDirection.SCALE_IN -> 1f - anim.value
                AnimationDirection.SCALE_OUT -> anim.value
                else -> 1f
            }
            scaleX = scale
            scaleY = scale
            alpha = alphaAnim.value
        }
}

enum class AnimationDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    SCALE_IN,
    SCALE_OUT

}
