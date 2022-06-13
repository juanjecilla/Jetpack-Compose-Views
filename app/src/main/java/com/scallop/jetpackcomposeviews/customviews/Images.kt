package com.scallop.jetpackcomposeviews.customviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

//https://proandroiddev.com/mirror-effect-with-jetpack-compose-78db11b5c30b
@Composable
fun Mirror(content: @Composable () -> Unit) {
    Column {
        content()
        Box(modifier = Modifier
            .graphicsLayer {
                alpha = 0.99f
                rotationZ = 180f
            }
            .drawWithContent {
                val colors = listOf(Color.Transparent, Color.White)
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(colors),
                    blendMode = BlendMode.DstIn
                )
            }
            .blur(radiusX = 1.dp, radiusY = 3.dp, BlurredEdgeTreatment.Unbounded)
            .clip(
                HalfSizeShape
            )
        ) {
            content()
        }
    }
}

object HalfSizeShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(Offset(0f, size.height / 2), Size(size.width, size.height)))
    }
}