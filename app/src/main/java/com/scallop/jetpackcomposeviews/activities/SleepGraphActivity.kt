package com.scallop.jetpackcomposeviews.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scallop.jetpackcomposeviews.customviews.graphs.SleepGraph
import com.scallop.jetpackcomposeviews.customviews.graphs.SleepPeriod
import com.scallop.jetpackcomposeviews.customviews.graphs.SleepStage

class SleepGraphActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                SleepGraphShowcase()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepGraphShowcase() {
    val startTime = System.currentTimeMillis() - 8 * 3600 * 1000
    val periods = listOf(
        SleepPeriod(startTime, startTime + 45 * 60000, SleepStage.AWAKE),
        SleepPeriod(startTime + 45 * 60000, startTime + 150 * 60000, SleepStage.LIGHT),
        SleepPeriod(startTime + 150 * 60000, startTime + 210 * 60000, SleepStage.DEEP),
        SleepPeriod(startTime + 210 * 60000, startTime + 280 * 60000, SleepStage.REM),
        SleepPeriod(startTime + 280 * 60000, startTime + 340 * 60000, SleepStage.LIGHT),
        SleepPeriod(startTime + 340 * 60000, startTime + 400 * 60000, SleepStage.DEEP),
        SleepPeriod(startTime + 400 * 60000, startTime + 460 * 60000, SleepStage.REM),
        SleepPeriod(startTime + 460 * 60000, startTime + 480 * 60000, SleepStage.AWAKE)
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Sleep Timeline Graph") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Text(
                text = "Last Night's Sleep",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )

            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                SleepGraph(periods = periods)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Summary", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Total sleep: 7h 15m", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Deep sleep: 1h 20m", style = MaterialTheme.typography.bodyMedium)
                Text(text = "REM sleep: 1h 30m", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
