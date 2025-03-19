package com.scallop.jetpackcomposeviews.effects

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircularImageCarousel(gradients: List<Color>) {
    val listState = rememberLazyListState()
    rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 32.dp),
            flingBehavior = rememberSnapFlingBehavior(listState) // Makes scrolling smooth
        ) {
            itemsIndexed(listOf(1..10)) { index, imageRes ->
                val actualIndex = index + 1   // Map index to original list

                Flippable { x, y, z ->

                    if (y < 90f) {
                        // Front Side with Gradient
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    gradients[actualIndex]
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Card ${actualIndex + 1}",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Flip Icon Overlay
                        Icon(
                            imageVector = Icons.Filled.List, // Flip icon
                            contentDescription = "Flip Card",
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(12.dp)
                                .size(32.dp),
                            tint = Color.White.copy(alpha = 0.8f) // Transparent white
                        )
                    } else {
                        // Back Side with Image
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Cyan),
                        ) {
                            Text("Hola")
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Flippable(
    modifier: Modifier = Modifier,
    content: @Composable (rotationX: Float, rotationY: Float, rotationZ: Float) -> Unit
) {
    var selected by remember { mutableStateOf(false) }

    // Zoom and Flip animation
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.2f else 1f,
        animationSpec = tween(durationMillis = 500)
    )

    val rotationXaxis by animateFloatAsState(
        targetValue = if (selected) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    val rotationYaxis by animateFloatAsState(
        targetValue = if (selected) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    val rotationZaxis by animateFloatAsState(
        targetValue = if (selected) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = modifier
            .size(200.dp)
            .zIndex(if (selected) 1f else 0f) // Ensure selected card is on top
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                rotationX = rotationXaxis
                rotationY = rotationYaxis
                rotationZ = rotationZaxis
                cameraDistance = 16 * density // Better 3D effect
            }
            .clickable {
                selected = !selected
            }
    ) {
        content(rotationXaxis, rotationYaxis, rotationZaxis)
    }
}

@Preview
@Composable
fun Preview() {
    CircularImageCarousel(listOf(Color.White, Color.Black, Color.Cyan))
}