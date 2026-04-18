package com.scallop.jetpackcomposeviews.customviews.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Sources:
 * https://proandroiddev.com/sleep-timeline-graph-in-compose-15c99f9a4af0
 * https://github.com/vitoksmile/Sleep-timeline-graph
 */

enum class SleepStage(val level: Int, val color: Color, val displayName: String) {
    AWAKE(0, Color(0xFFFFCC00), "Awake"),
    REM(1, Color(0xFF42A5F5), "REM"),
    LIGHT(2, Color(0xFF26C6DA), "Light"),
    DEEP(3, Color(0xFF5C6BC0), "Deep")
}

data class SleepPeriod(
    val startTime: Long,
    val endTime: Long,
    val stage: SleepStage
)

@Composable
fun SleepGraph(
    periods: List<SleepPeriod>,
    modifier: Modifier = Modifier
) {
    if (periods.isEmpty()) return

    val minTime = periods.minOf { it.startTime }
    val maxTime = periods.maxOf { it.endTime }
    val duration = maxTime - minTime

    Column(modifier = modifier.padding(16.dp)) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            val width = size.width
            val height = size.height
            val stepHeight = height / 4
            val cornerRadius = 4.dp.toPx()

            periods.forEach { period ->
                val left = ((period.startTime - minTime).toFloat() / duration) * width
                val right = ((period.endTime - minTime).toFloat() / duration) * width
                val top = period.stage.level * stepHeight
                val rectWidth = right - left

                drawRoundRect(
                    color = period.stage.color,
                    topLeft = Offset(left, top + 10f), // Small padding
                    size = Size(rectWidth, stepHeight - 20f),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Time labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(minTime),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = formatTime(maxTime),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Legend
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SleepStage.values().forEach { stage ->
                LegendItem(stage)
            }
        }
    }
}

@Composable
private fun LegendItem(stage: SleepStage) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(stage.color, RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = stage.displayName, fontSize = 12.sp)
    }
}

private fun formatTime(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        .format(formatter)
}

@Preview(showBackground = true)
@Composable
fun SleepGraphPreview() {
    val startTime = System.currentTimeMillis() - 8 * 3600 * 1000
    val periods = listOf(
        SleepPeriod(startTime, startTime + 30 * 60000, SleepStage.AWAKE),
        SleepPeriod(startTime + 30 * 60000, startTime + 120 * 60000, SleepStage.LIGHT),
        SleepPeriod(startTime + 120 * 60000, startTime + 180 * 60000, SleepStage.DEEP),
        SleepPeriod(startTime + 180 * 60000, startTime + 240 * 60000, SleepStage.REM),
        SleepPeriod(startTime + 240 * 60000, startTime + 300 * 60000, SleepStage.LIGHT),
        SleepPeriod(startTime + 300 * 60000, startTime + 360 * 60000, SleepStage.DEEP),
        SleepPeriod(startTime + 360 * 60000, startTime + 420 * 60000, SleepStage.REM),
        SleepPeriod(startTime + 420 * 60000, startTime + 480 * 60000, SleepStage.AWAKE)
    )

    MaterialTheme {
        SleepGraph(periods = periods)
    }
}
