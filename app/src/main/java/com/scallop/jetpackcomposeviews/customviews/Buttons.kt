package com.scallop.jetpackcomposeviews.customviews

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * https://proandroiddev.com/creating-a-repeating-button-with-jetpack-compose-b39c4f559f7d
 */
@Composable
fun RepeatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    maxDelayMillis: Long = 1000,
    minDelayMillis: Long = 5,
    delayDecayFactor: Float = .20f,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier.repeatingClickable(
            interactionSource = interactionSource,
            enabled = enabled,
            maxDelayMillis = maxDelayMillis,
            minDelayMillis = minDelayMillis,
            delayDecayFactor = delayDecayFactor
        ) { onClick() },
        onClick = {},
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )
}

fun Modifier.repeatingClickable(
    interactionSource: InteractionSource,
    enabled: Boolean,
    maxDelayMillis: Long = 1000,
    minDelayMillis: Long = 5,
    delayDecayFactor: Float = .20f,
    onClick: () -> Unit
): Modifier = composed {

    val currentClickListener by rememberUpdatedState(onClick)

    pointerInput(interactionSource, enabled) {
        forEachGesture {
            coroutineScope {
                awaitPointerEventScope {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    val heldButtonJob = launch {
                        var currentDelayMillis = maxDelayMillis
                        while (enabled && down.pressed) {
                            currentClickListener()
                            delay(currentDelayMillis)
                            val nextMillis =
                                currentDelayMillis - (currentDelayMillis * delayDecayFactor)
                            currentDelayMillis = nextMillis.toLong().coerceAtLeast(minDelayMillis)
                        }
                    }
                    waitForUpOrCancellation()
                    heldButtonJob.cancel()
                }
            }
        }
    }
}

// https://semicolonspace.com/jetpack-compose-gradient-button/
@Composable
private fun GradientButton(
    gradientColors: List<Color>,
    cornerRadius: Dp
) {

    var clickCount by remember {
        mutableStateOf(0)
    }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = {
            clickCount++
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(cornerRadius)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Click $clickCount",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun GradientButtonDisable(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    disabledColors: List<Color> = listOf(Color.Transparent, Color.Transparent)
) {

    var enabled by remember { mutableStateOf(true) }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = {
            enabled = false
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        shape = RoundedCornerShape(cornerRadius),
        enabled = enabled,
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(colors = if (enabled) gradientColors else disabledColors),
                    shape = RoundedCornerShape(cornerRadius)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (enabled) "Disable Me" else "I'm Disabled!",
                fontSize = 20.sp,
                color = if (enabled) Color.White else Color.Black.copy(alpha = 0.6F)
            )
        }
    }
}

@Composable
private fun GradientButtonNoRipple(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    context: Context
) {

    // This is used to disable the ripple effect
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp)
            .background(
                brush = Brush.linearGradient(colors = gradientColors),
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(
                indication = null, // Assign null to disable the ripple effect
                interactionSource = interactionSource
            ) {
                Toast
                    .makeText(context, "No Ripple Click", Toast.LENGTH_SHORT)
                    .show()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No Ripple",
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }

}


//https://semicolonspace.com/jetpack-compose-button-gradient-border/


@Composable
fun GradientBorderButtonClick(
    colors: List<Color>,
    paddingValues: PaddingValues,
    widthFraction: Float
) {

    var clickCount by remember {
        mutableStateOf(0)
    }

    // To disable ripple
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(fraction = widthFraction)
            .border(
                width = 4.dp,
                brush = Brush.horizontalGradient(colors = colors),
                shape = RectangleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null // To disable ripple
            ) {
                clickCount++
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Click $clickCount",
            fontSize = 26.sp,
            modifier = Modifier.padding(paddingValues),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun GradientBorderButtonRound(
    colors: List<Color>,
    context: Context,
    paddingValues: PaddingValues,
    widthFraction: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(fraction = widthFraction)
            .border(
                width = 4.dp,
                brush = Brush.horizontalGradient(colors = colors),
                shape = RoundedCornerShape(percent = 50)
            )
            // To make the ripple round
            .clip(shape = RoundedCornerShape(percent = 50))
            .clickable {
                Toast
                    .makeText(context, "Round Button Click", Toast.LENGTH_SHORT)
                    .show()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Round Button",
            fontSize = 26.sp,
            modifier = Modifier.padding(paddingValues),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun GradientBorderButtonDisable(
    colors: List<Color>,
    widthFraction: Float,
    paddingValues: PaddingValues,
    disabledColor: Color = Color.Gray.copy(alpha = 0.3f),
    disabledColors: List<Color> = listOf(disabledColor, disabledColor)
) {
    var enabled by remember {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(fraction = widthFraction)
            .border(
                width = 4.dp,
                brush = Brush.horizontalGradient(colors = if (enabled) colors else disabledColors),
                shape = RectangleShape
            )
            .clickable(enabled = enabled) {
                enabled = false
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (enabled) "Disable Me" else "I'm Disabled",
            fontSize = 26.sp,
            modifier = Modifier.padding(paddingValues),
            fontWeight = FontWeight.Medium,
            color = if (enabled) Color.Black else disabledColor
        )
    }

}




// https://semicolonspace.com/android-jetpack-compose-button-onclick-animation/

@Composable
fun ButtonAnimation(
    animationDuration: Int = 100,
    scaleDown: Float = 0.9f
) {

    val interactionSource = MutableInteractionSource()

    val coroutineScope = rememberCoroutineScope()

    val scale = remember {
        Animatable(1f)
    }

    Box(
        modifier = Modifier
            .scale(scale = scale.value)
            .background(
                color = Color(0xFF35898F),
                shape = RoundedCornerShape(size = 12f)
            )
            .clickable(interactionSource = interactionSource, indication = null) {
                coroutineScope.launch {
                    scale.animateTo(
                        scaleDown,
                        animationSpec = tween(animationDuration),
                    )
                    scale.animateTo(
                        1f,
                        animationSpec = tween(animationDuration),
                    )
                }
            }
    ) {
        Text(
            text = "Button Animation",
            modifier = Modifier.padding(horizontal = 36.dp, vertical = 12.dp),
            fontSize = 26.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
fun HeartAnimation() {

    val interactionSource = MutableInteractionSource()

    val coroutineScope = rememberCoroutineScope()

    var enabled by remember {
        mutableStateOf(false)
    }

    val scale = remember {
        Animatable(1f)
    }

    Icon(
        imageVector = Icons.Outlined.Favorite,
        contentDescription = "Like the product",
        tint = if (enabled) Color.Red else Color.LightGray,
        modifier = Modifier
            .scale(scale = scale.value)
            .size(size = 56.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                enabled = !enabled
                coroutineScope.launch {
                    scale.animateTo(
                        0.8f,
                        animationSpec = tween(100),
                    )
                    scale.animateTo(
                        1f,
                        animationSpec = tween(100),
                    )
                }
            }
    )
}


// https://semicolonspace.com/android-compose-button-corner-style/
@Composable
fun ButtonStyle() {
    Column(
        modifier = Modifier.fillMaxSize(),
        // Gap between children = 32.dp
        verticalArrangement = Arrangement.spacedBy(32.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val gradientColors = listOf(Color(0xFF7b4397), Color(0xFFdc2430))
        val roundCornerShape = RoundedCornerShape(topEnd = 30.dp, bottomStart = 30.dp)

        ButtonCount(
            gradientColors = gradientColors,
            roundedCornerShape = roundCornerShape
        )

        ButtonDisable(
            gradientColors = gradientColors,
            roundedCornerShape = roundCornerShape
        )

        ButtonNoRipple(
            gradientColors = gradientColors,
            roundedCornerShape = roundCornerShape
        )
    }
}

@Composable
fun ButtonCount(
    gradientColors: List<Color>,
    roundedCornerShape: RoundedCornerShape
) {

    var clickCount by remember {
        mutableStateOf(0)
    }

    Box(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(colors = gradientColors),
                shape = roundedCornerShape
            )
            .clip(roundedCornerShape)
            .clickable {
                clickCount++
            }
            .padding(PaddingValues(horizontal = 60.dp, vertical = 16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Click $clickCount",
            fontSize = 26.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ButtonDisable(
    gradientColors: List<Color>,
    roundedCornerShape: RoundedCornerShape,
    disabledColors: List<Color> = listOf(
        Color.Gray.copy(alpha = 0.2f),
        Color.Gray.copy(alpha = 0.2f)
    )
) {

    var enabled by remember {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(colors = if (enabled) gradientColors else disabledColors),
                shape = roundedCornerShape
            )
            .clip(roundedCornerShape)
            .clickable(enabled = enabled) {
                enabled = false
            }
            .padding(PaddingValues(horizontal = 40.dp, vertical = 16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (enabled) "Disable Me!" else "I'm Disabled!",
            fontSize = 26.sp,
            color = if (enabled) Color.White else Color.Black.copy(alpha = 0.4f),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ButtonNoRipple(
    gradientColors: List<Color>,
    roundedCornerShape: RoundedCornerShape,
    context: Context = LocalContext.current.applicationContext
) {

    // To disable ripple effect
    val interactionSource = MutableInteractionSource()

    Box(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(colors = gradientColors),
                shape = roundedCornerShape
            )
            .clip(roundedCornerShape)
            .clickable(indication = null, interactionSource = interactionSource) {
                Toast
                    .makeText(context, "No Ripple", Toast.LENGTH_SHORT)
                    .show()
            }
            .padding(PaddingValues(horizontal = 46.dp, vertical = 16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No Ripple",
            fontSize = 26.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

// https://semicolonspace.com/android-compose-music-button/


//https://barros9.medium.com/custom-view-swipe-button-in-compose-78c8c74e3f9a
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    backgroundColor: Color = Color.White,
    borderStroke: BorderStroke = BorderStroke(2.dp, Color.Black),
    elevation: Dp = 8.dp,
    icon: @Composable () -> Unit,
    text: String,
    textStyle: TextStyle = TextStyle(Color.Black, 20.sp),
    onSwipe: () -> Unit
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val textAlpha by animateFloatAsState(
        if (swipeableState.offset.value > 10f) (1 - swipeableState.progress.fraction) else 1f
    )

    if (swipeableState.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                if (swipeableState.currentValue == 1) {
                    onSwipe()
                }
            }
        }
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        color = backgroundColor,
        border = borderStroke,
        elevation = elevation
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            var iconSize by remember { mutableStateOf(IntSize.Zero) }
            val maxWidth = with(LocalDensity.current) {
                this@BoxWithConstraints.maxWidth.toPx() - iconSize.width
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.End,
                text = text,
                style = textStyle.copy(color = textStyle.color.copy(alpha = textAlpha))
            )
            Box(
                modifier = Modifier
                    .onGloballyPositioned {
                        iconSize = it.size
                    }
                    .swipeable(
                        state = swipeableState,
                        anchors = mapOf(
                            0f to 0,
                            maxWidth to 1
                        ),
                        thresholds = { _, _ -> FractionalThreshold(0.9f) },
                        orientation = Orientation.Horizontal
                    )
                    .offset {
                        IntOffset(swipeableState.offset.value.roundToInt(), 0)
                    }
            ) {
                icon()
            }
        }
    }
}
