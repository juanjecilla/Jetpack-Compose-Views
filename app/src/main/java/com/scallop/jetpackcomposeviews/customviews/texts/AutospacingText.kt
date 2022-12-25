package com.scallop.jetpackcomposeviews.customviews.texts

import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

// https://medium.com/make-apps-simple/auto-spaced-text-in-jetpack-compose-d1e9409e2dbc

operator fun TextUnit.plus(other: TextUnit): TextUnit {
    return (this.value + other.value).sp
}

operator fun TextUnit.minus(other: TextUnit): TextUnit {
    return (this.value - other.value).sp
}

@Composable
fun AutoSpacedText(
    text: String,
    modifier: Modifier = Modifier,
    minimumLetterSpacing: Int = 2,
    maximumLetterSpacing: Int = 200,
    letterSpacingStep: Int = 2,
) {
    var letterSpacingCalculated by remember(text) {
        mutableStateOf(false)
    }
    var letterSpacing: TextUnit by remember(text) {
        mutableStateOf(minimumLetterSpacing.sp)
    }
    Text(
        text = text,
        modifier = modifier
            .requiredWidth(260.dp)
            .drawWithContent {
                if (letterSpacingCalculated) {
                    drawContent()
                }
            },
        letterSpacing = letterSpacing,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Visible,
        maxLines = 1,
        onTextLayout = {
            if (!letterSpacingCalculated) {
                if (letterSpacing.value.roundToInt() < maximumLetterSpacing && !it.hasVisualOverflow) {
                    letterSpacing += letterSpacingStep.sp
                } else {
                    letterSpacingCalculated = true
                    letterSpacing -= letterSpacingStep.sp
                }
            }
        },
    )
}
