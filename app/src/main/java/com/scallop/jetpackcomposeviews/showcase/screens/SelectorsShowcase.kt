@file:OptIn(ExperimentalTime::class)

package com.scallop.jetpackcomposeviews.showcase.screens

import kotlin.time.ExperimentalTime
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scallop.jetpackcomposeviews.customviews.PodcastSlider
import com.scallop.jetpackcomposeviews.customviews.dates.CarouselCalendar
import com.scallop.jetpackcomposeviews.customviews.rememberPodcastSliderState
import com.scallop.jetpackcomposeviews.customviews.selectors.TimerPicker
import com.scallop.jetpackcomposeviews.showcase.ShowcaseSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorsShowcaseScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selectors") },
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
                ShowcaseSection(title = "TimerPicker — clock-face time selector") {
                    TimerPicker()
                }
            }

            item {
                ShowcaseSection(title = "CarouselCalendar — horizontal 3D date carousel") {
                    CarouselCalendar()
                }
            }

            item {
                ShowcaseSection(title = "PodcastSlider — Google Podcasts-style speed selector") {
                    val state = rememberPodcastSliderState(currentValue = 10f, range = 5..20)
                    PodcastSlider(
                        state = state,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
