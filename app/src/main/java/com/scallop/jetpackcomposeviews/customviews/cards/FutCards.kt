package com.scallop.jetpackcomposeviews.customviews.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy.Companion.Offscreen
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.scallop.jetpackcomposeviews.R

@Composable
fun FUTLikeCardUI() {
    val image = ImageBitmap.imageResource(R.drawable.img_card_shape)
    val Cardwidth = with(LocalDensity.current) {
        200.dp.roundToPx()
    }
    val cardHeight = with(LocalDensity.current) {
        256.dp.roundToPx()
    }

    Box(
        modifier = Modifier
            .width(200.dp)
            .height(256.dp),
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer
                { compositingStrategy = Offscreen }
                .drawWithContent {
                    drawContent()
                    drawImage(
                        image = image,
                        dstSize = IntSize(
                            width = Cardwidth,
                            height = cardHeight,
                        ),
                        blendMode = BlendMode.DstIn,
                        colorFilter = ColorFilter.tint(Color.Black),
                    )
                },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Image(painter = painterResource(R.drawable.ic_launcher_foreground), contentDescription = "")
                // Insert the img_user_profile_picture
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            // Place the img_card_border
        }
    }
}

@Preview
@Composable
private fun FutCards(){
    FUTLikeCardUI()
}
