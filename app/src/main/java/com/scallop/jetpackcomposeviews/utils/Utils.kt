package com.scallop.jetpackcomposeviews.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.underscore(width: Int, color: Color): Modifier {
    return this.drawBehind {
        val borderSize = width.dp.toPx()
        val y = this.size.height - borderSize / 2
        drawLine(
            color = color,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = borderSize
        )
    }
}
