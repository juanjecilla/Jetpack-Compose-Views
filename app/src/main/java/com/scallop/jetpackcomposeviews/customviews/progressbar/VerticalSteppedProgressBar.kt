package com.scallop.jetpackcomposeviews.customviews.progressbar

// https://proandroiddev.com/building-a-custom-vertical-progress-bar-in-jetpack-compose-433a387998ef

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalTextApi::class)
@Composable
fun CustomVerticalProgressBar(
    currentState: ProgressState,
    requestedContent: String,
    pendingContent: String,
    completedContent: String,
    requestedContentColor: Color = Color.Black,
    pendingContentColor: Color = Color.Black,
    completedContentColor: Color = Color.Black,
    requestedStateStepColor: Color = Color(0xFF037921),
    pendingStateStepColor: Color = Color(0xFFFACA0E),
    completedStateStepColor: Color = Color(0xFF037921),
    pendingStatePathColor: Color = Color.LightGray,
    completedStatePathColor: Color = Color(0xFF037921),
) {
    val requestedTextMeasure = rememberTextMeasurer()

    val pendingTextMeasure = rememberTextMeasurer()

    val completedTextMeasure = rememberTextMeasurer()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val progressStepRadius = 6.dp.toPx()
        val progressBarX = 16.dp.toPx()
        val pathLineThickness = 1.dp.toPx()

        val withdrawToPendingPathColor =
            if (currentState == ProgressState.REQUESTED) pendingStatePathColor else completedStatePathColor
        val withdrawToPendingPathEffect =
            if (currentState == ProgressState.REQUESTED) PathEffect.dashPathEffect(
                floatArrayOf(10f, 10f), 0f
            ) else null

        val pendingToCompletedPathColor =
            if (currentState == ProgressState.COMPLETED) completedStatePathColor else pendingStatePathColor
        val pendingToCompletedPathEffect =
            if (currentState == ProgressState.COMPLETED) null else PathEffect.dashPathEffect(
                floatArrayOf(10f, 10f), 0f
            )

        val firstStepPosition = Offset(16.dp.toPx(), 24.dp.toPx())
        val secondStepPosition = Offset(16.dp.toPx(), 90.dp.toPx())
        val thirdStepPosition = Offset(16.dp.toPx(), 160.dp.toPx())

        drawLine(
            color = withdrawToPendingPathColor,
            pathEffect = withdrawToPendingPathEffect,
            start = Offset(progressBarX, 24.dp.toPx()),
            end = Offset(progressBarX, 90.dp.toPx()),
            strokeWidth = pathLineThickness
        )

        drawLine(
            color = pendingToCompletedPathColor,
            pathEffect = pendingToCompletedPathEffect,
            start = Offset(progressBarX, 90.dp.toPx()),
            end = Offset(progressBarX, 160.dp.toPx()),
            strokeWidth = pathLineThickness
        )

        // First Step
        drawCircle(
            color = requestedStateStepColor,
            center = firstStepPosition,
            radius = progressStepRadius
        )

        // Second Step
        if (currentState != ProgressState.PENDING) {
            drawCircle(
                color = if (currentState == ProgressState.REQUESTED) Color.LightGray else requestedStateStepColor,
                center = secondStepPosition,
                radius = progressStepRadius
            )
        } else {
            drawCircle(
                color = pendingStateStepColor,
                center = secondStepPosition,
                radius = progressStepRadius
            )
        }

        // Third Step
        drawCircle(
            color = if (currentState == ProgressState.COMPLETED) completedStateStepColor else Color.LightGray,
            center = thirdStepPosition,
            radius = progressStepRadius
        )

        // Draw text labels
        drawText(
            textMeasurer = requestedTextMeasure,
            text = requestedContent,
            topLeft = Offset(48.dp.toPx(), 16.dp.toPx()),
            style = TextStyle(
                fontSize = 14.sp,
                color = requestedContentColor,
                fontWeight = if (currentState == ProgressState.REQUESTED) FontWeight.Bold else FontWeight.Normal
            )
        )

        drawText(
            textMeasurer = pendingTextMeasure,
            text = pendingContent,
            topLeft = Offset(48.dp.toPx(), 80.dp.toPx()),
            style = TextStyle(
                fontSize = 14.sp,
                color = pendingContentColor,
                fontWeight = if (currentState == ProgressState.PENDING) FontWeight.Bold else FontWeight.Normal
            )
        )

        drawText(
            textMeasurer = completedTextMeasure,
            text = completedContent,
            topLeft = Offset(48.dp.toPx(), 152.dp.toPx()),
            style = TextStyle(
                fontSize = 14.sp,
                color = completedContentColor,
                fontWeight = if (currentState == ProgressState.COMPLETED) FontWeight.Bold else FontWeight.Normal
            )
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewRequestedCustomVerticalProgressBar() {
    CustomVerticalProgressBar(currentState = ProgressState.REQUESTED,
        requestedContent = "Requested",
        pendingContent = "Pending",
        completedContent = "Completed"
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewPendingCustomVerticalProgressBar() {
    CustomVerticalProgressBar(currentState = ProgressState.PENDING,
        requestedContent = "Requested",
        pendingContent = "Pending",
        completedContent = "Completed"
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewCompletedCustomVerticalProgressBar() {
    CustomVerticalProgressBar(currentState = ProgressState.COMPLETED,
        requestedContent = "Requested",
        pendingContent = "Pending",
        completedContent = "Completed"
    )
}



enum class ProgressState{
    REQUESTED,
    PENDING,
    COMPLETED
}