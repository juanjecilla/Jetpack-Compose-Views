package com.scallop.jetpackcomposeviews.customviews.texts

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Original code: https://medium.com/@paritasampa95/auto-scrolling-text-in-jetpack-compose-smooth-horizontal-marquee-for-android-60b20f1e8198
 */
@Composable
fun AutoScrollText(
    text: String,
    scrollDuration: Duration = 1.seconds,
    initialDelay: Duration = 1.seconds,
    endDelay: Duration = 1.seconds,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(text) {
        delay(initialDelay)
        while (true) {
            scrollState.scrollTo(0)
            scrollState.animateScrollTo(
                scrollState.maxValue,
                animationSpec = tween(
                    durationMillis = scrollDuration.inWholeMilliseconds.toInt(),
                    easing = LinearEasing
                )
            )
            delay(endDelay)
        }
    }

    Row(
        modifier = modifier
            .horizontalScroll(scrollState),
    ) {
        Text(
            text = text,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Clip
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AutoScrollText_Preview() {
    AutoScrollText(LoremIpsum().values.joinToString(" "))
}
