package com.scallop.jetpackcomposeviews.customviews.texts

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.core.graphics.get
import kotlin.math.min
import androidx.core.graphics.scale


/**
 * Original code: https://medium.com/@nikhil.here/dotify-rendering-retro-style-text-in-compose-with-bitmaps-3035bffacebe
 */
@Composable
fun DottedText(
    modifier: Modifier,
    text: String,
    textScale: Float
) {
    BoxWithConstraints(modifier) {
        //Create a Bitmap with text
        val bitmap = remember(text, textScale) {
            renderTextToBitmap(text, textScale, this.maxWidth.value, this.maxHeight.value)
        }

        //Create a Dot Matrix
        val dotMatrix = remember(bitmap) { bitmapToDotMatrix(bitmap, 30, 30) }

        //Render Dot Matrix
        DotMatrixDisplay(
            modifier = Modifier.fillMaxSize(),
            dotMatrix = dotMatrix,
            dotSize = 10.dp
        )
    }
}

fun renderTextToBitmap(
    renderText: String,
    renderTextScale: Float,
    width: Float,
    height: Float
): Bitmap {
    val bitmap = createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = (min(width, height) * renderTextScale)
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }
    val x = width / 2f
    val y = (height / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f)
    canvas.drawText(renderText, x, y, textPaint)
    return bitmap
}

fun bitmapToDotMatrix(bitmap: Bitmap, rows: Int, cols: Int): List<List<Int>> {
    //scale bitmap based on the number of rows and cols we want
    val scaledBitmap = bitmap.scale(cols, rows)
    val result = mutableListOf<List<Int>>()
    for (y in 0 until rows) {
        val row = mutableListOf<Int>()
        for (x in 0 until cols) {
            val pixel = scaledBitmap[x, y]
            val red = Color.red(pixel)
            val green = Color.green(pixel)
            val blue = Color.blue(pixel)
            val alpha = Color.alpha(pixel)
            print(" $alpha ")
            row.add(alpha)
        }
        println()
        result.add(row)
    }
    return result
}

@Composable
fun DotMatrixDisplay(
    modifier: Modifier = Modifier,
    dotMatrix: List<List<Int>>,
    dotSize: Dp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        dotMatrix.forEach { row ->
            Row {
                row.forEach { alpha ->
                    val targetColor = if (alpha > 0) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.background
                    }
                    Box(
                        modifier = Modifier
                            .size(dotSize)
                            .padding(1.dp)
                            .background(
                                color = targetColor,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun DottedText_Preview() {
    MaterialTheme {
        DottedText(
            modifier = Modifier,
            text = "1234567890",
            textScale = 0.5f
        )
    }
}
