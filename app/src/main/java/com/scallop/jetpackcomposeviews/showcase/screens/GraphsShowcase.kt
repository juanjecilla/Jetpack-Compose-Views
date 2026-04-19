package com.scallop.jetpackcomposeviews.showcase.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.scallop.jetpackcomposeviews.customviews.Graph
import com.scallop.jetpackcomposeviews.customviews.graphs.SleepGraph
import com.scallop.jetpackcomposeviews.customviews.graphs.SleepPeriod
import com.scallop.jetpackcomposeviews.customviews.graphs.SleepStage
import com.scallop.jetpackcomposeviews.customviews.graphs.StackedBarChart
import com.scallop.jetpackcomposeviews.showcase.ShowcaseSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphsShowcaseScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charts & Graphs") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(padding)
        ) {
            item {
                ShowcaseSection(title = "Graph — bezier line chart with Canvas") {
                    Graph(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        xValues = listOf(0, 1, 2, 3, 4, 5, 6),
                        yValues = listOf(0, 20, 40, 60, 80, 100),
                        points = listOf(10f, 75f, 40f, 90f, 55f, 30f, 85f),
                        paddingSpace = 16.dp,
                        verticalStep = 20
                    )
                }
            }

            item {
                ShowcaseSection(title = "SleepGraph — sleep timeline with stages") {
                    val start = System.currentTimeMillis() - 8 * 3600 * 1000
                    val periods = listOf(
                        SleepPeriod(start, start + 45 * 60000, SleepStage.AWAKE),
                        SleepPeriod(start + 45 * 60000, start + 150 * 60000, SleepStage.LIGHT),
                        SleepPeriod(start + 150 * 60000, start + 240 * 60000, SleepStage.DEEP),
                        SleepPeriod(start + 240 * 60000, start + 330 * 60000, SleepStage.REM),
                        SleepPeriod(start + 330 * 60000, start + 420 * 60000, SleepStage.LIGHT),
                        SleepPeriod(start + 420 * 60000, start + 480 * 60000, SleepStage.AWAKE)
                    )
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SleepGraph(periods = periods)
                    }
                }
            }

            item {
                ShowcaseSection(title = "StackedBarChart — animated horizontal stacked bars") {
                    StackedBarChart(
                        data = listOf(30f, 20f, 50f),
                        colors = listOf(
                            Color(0xFF6200EE),
                            Color(0xFF03DAC5),
                            Color(0xFFFF6F00)
                        )
                    )
                }
            }
        }
    }
}
