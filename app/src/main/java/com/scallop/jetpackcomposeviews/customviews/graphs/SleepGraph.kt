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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Duration
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
    AWAKE(0, Color(0xFFD4A056), "Awake"),
    REM(1, Color(0xFF9575CD), "REM"),
    LIGHT(2, Color(0xFF5E35B1), "Light"),
    DEEP(3, Color(0xFF1A237E), "Deep")
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

    val stages = SleepStage.values()

    Column(modifier = modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            // Stage Labels
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                stages.forEach { stage ->
                    val stageDuration = periods
                        .filter { it.stage == stage }
                        .sumOf { it.endTime - it.startTime }
                    val durationText = formatDuration(stageDuration)
                    
                    Text(
                        text = "${stage.displayName} • $durationText",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black
                    )
                    if (stage != stages.last()) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp, bottom = 12.dp) // Adjust for labels
            ) {
                val width = size.width
                val height = size.height
                val stepHeight = height / (stages.size - 1)
                val cornerRadius = 6.dp.toPx()
                val barHeight = 12.dp.toPx()

                // Draw horizontal background lines
                stages.forEachIndexed { index, _ ->
                    val y = index * stepHeight
                    drawLine(
                        color = Color.LightGray.copy(alpha = 0.5f),
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = 2.dp.toPx()
                    )
                }

                // Draw vertical connectors and periods
                periods.forEachIndexed { index, period ->
                    val left = ((period.startTime - minTime).toFloat() / duration) * width
                    val right = ((period.endTime - minTime).toFloat() / duration) * width
                    val y = period.stage.level * stepHeight
                    val rectWidth = (right - left).coerceAtLeast(4.dp.toPx())

                    // Draw the period bar
                    drawRoundRect(
                        color = period.stage.color,
                        topLeft = Offset(left, y - barHeight / 2),
                        size = Size(rectWidth, barHeight),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )

                    // Draw vertical connector to next period
                    if (index < periods.size - 1) {
                        val nextPeriod = periods[index + 1]
                        val nextY = nextPeriod.stage.level * stepHeight
                        
                        if (y != nextY) {
                            drawLine(
                                color = period.stage.color.copy(alpha = 0.6f),
                                start = Offset(right, y),
                                end = Offset(right, nextY),
                                strokeWidth = 1.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
            // Mid time if possible
            val midTime = minTime + (maxTime - minTime) / 2
            Text(
                text = formatTime(midTime),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = formatTime(maxTime),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

private fun formatDuration(millis: Long): String {
    val duration = Duration.ofMillis(millis)
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    return if (hours > 0) "${hours}h ${minutes}m" else "${minutes}m"
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
        SleepPeriod(startTime, startTime + 20 * 60000, SleepStage.AWAKE),
        SleepPeriod(startTime + 20 * 60000, startTime + 25 * 60000, SleepStage.AWAKE),
        SleepPeriod(startTime + 25 * 60000, startTime + 60 * 60000, SleepStage.REM),
        SleepPeriod(startTime + 60 * 60000, startTime + 180 * 60000, SleepStage.LIGHT),
        SleepPeriod(startTime + 180 * 60000, startTime + 220 * 60000, SleepStage.DEEP),
        SleepPeriod(startTime + 220 * 60000, startTime + 260 * 60000, SleepStage.LIGHT),
        SleepPeriod(startTime + 260 * 60000, startTime + 300 * 60000, SleepStage.REM),
        SleepPeriod(startTime + 300 * 60000, startTime + 380 * 60000, SleepStage.DEEP),
        SleepPeriod(startTime + 380 * 60000, startTime + 420 * 60000, SleepStage.LIGHT),
        SleepPeriod(startTime + 420 * 60000, startTime + 480 * 60000, SleepStage.AWAKE)
    )

    MaterialTheme {
        SleepGraph(periods = periods)
    }
}
