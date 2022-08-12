package com.scallop.jetpackcomposeviews.customviews

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//https://medium.com/@banmarkovic/autosize-textfield-in-android-jetpack-compose-7d2a601636f5

private const val TEXT_SCALE_REDUCTION_INTERVAL = 0.9f

@ExperimentalMaterialApi
@Composable
fun AutoSizeTextField(
    modifier: Modifier = Modifier,
    inputValue: String,
    fontSize: TextUnit = 72.sp,
    lineHeight: TextUnit = 80.sp,
    inputValueChanged: (String) -> Unit,
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        var shrunkFontSize = fontSize
        val calculateIntrinsics = @Composable {
            ParagraphIntrinsics(
                text = inputValue,
                style = TextStyle(
                    fontSize = shrunkFontSize,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = lineHeight,
                    textAlign = TextAlign.Center
                ),
                density = LocalDensity.current,
                fontFamilyResolver = createFontFamilyResolver(LocalContext.current)
            )
        }

        var intrinsics = calculateIntrinsics()
        with(LocalDensity.current) {
            // TextField and OutlinedText field have default horizontal padding of 16.dp
            val textFieldDefaultHorizontalPadding = 16.dp.toPx()
            val maxInputWidth = maxWidth.toPx() - 2 * textFieldDefaultHorizontalPadding

            while (intrinsics.maxIntrinsicWidth > maxInputWidth) {
                shrunkFontSize *= TEXT_SCALE_REDUCTION_INTERVAL
                intrinsics = calculateIntrinsics()
            }
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputValue,
            onValueChange = { inputValueChanged(it) },
            textStyle = TextStyle(
                fontSize = shrunkFontSize,
                fontWeight = FontWeight.SemiBold,
                lineHeight = lineHeight,
                textAlign = TextAlign.Center
            ),
            singleLine = true,
        )
    }
}
