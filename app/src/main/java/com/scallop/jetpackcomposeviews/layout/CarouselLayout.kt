package com.scallop.jetpackcomposeviews.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// https://medium.com/mobilepeople/writing-carousel-pager-using-jetpack-compose-8b30eb87dd7b
/*
@Immutable
data class CarouselPagerData<T>(
    val items: List<T>,
)

@Composable
fun <T> CarouselPager(
    modifier: Modifier,
    data: State<CarouselPagerData<T>>,
    contentPadding: PaddingValues = PaddingValues(32.dp),
    txBase: Dp = 96.dp,
    content: @Composable BoxScope.(T) -> Unit,
) {
    val pagerState = rememberPagerState { data.value.items.size }
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        contentPadding = contentPadding,
    ) { index ->
        val item = data.value.items[index]
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(
                    if (pagerState.currentPage == index) 1f else 0f
                )
                .graphicsLayer {
                    val currentPage = pagerState.currentPage
                    // x2 because it is -0.5 ... 0.5 -> -1 ... 1
                    val fractionAbs = (pagerState.currentPageOffsetFraction * 2f)
                        .coerceIn(-1f, 1f)
                        .absoluteValue
                    val scale = if (currentPage == index) {
                        (1f - fractionAbs * 0.2f)
                    } else {
                        // Must not be more than the minimum above
                        // E.g. (0.7 + 0.1) == (1 - 0.2) at the moment
                        0.7f + fractionAbs * 0.1f
                    }.coerceIn(0f, 1f)
                    scaleX = scale
                    scaleY = scale
                    val txPx = (with(this) { txBase.toPx() }) * (1f - fractionAbs)
                    translationX = if (index > currentPage) {
                        -txPx
                    } else if (index < currentPage) {
                        txPx
                    }
                },
        ) {
            content(item)
        }
    }
}

 */