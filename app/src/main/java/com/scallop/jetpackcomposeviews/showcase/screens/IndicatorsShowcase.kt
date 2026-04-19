package com.scallop.jetpackcomposeviews.showcase.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scallop.jetpackcomposeviews.R
import com.scallop.jetpackcomposeviews.customviews.AnimationType
import com.scallop.jetpackcomposeviews.customviews.LoadingButton
import com.scallop.jetpackcomposeviews.customviews.PageIndicator
import com.scallop.jetpackcomposeviews.customviews.PulseIndicator
import com.scallop.jetpackcomposeviews.customviews.progressbar.CustomVerticalProgressBar
import com.scallop.jetpackcomposeviews.customviews.progressbar.ProgressState
import com.scallop.jetpackcomposeviews.showcase.ShowcaseSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndicatorsShowcaseScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Indicators & Progress") },
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
                ShowcaseSection(title = "PageIndicator — animated dot page indicator") {
                    var selectedPage by remember { mutableIntStateOf(0) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PageIndicator(
                            numberOfPages = 5,
                            selectedPage = selectedPage,
                            defaultRadius = 10.dp,
                            selectedLength = 24.dp,
                            space = 8.dp,
                            animationDurationInMillis = 300,
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                    ) {
                        repeat(5) { index ->
                            androidx.compose.material3.TextButton(
                                onClick = { selectedPage = index }
                            ) {
                                Text("$index")
                            }
                        }
                    }
                }
            }

            item {
                ShowcaseSection(title = "PulseIndicator — pulsing ring effect") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        PulseIndicator(icon = R.drawable.ic_launcher_background)
                    }
                }
            }

            item {
                ShowcaseSection(title = "LoadingButton — three animation types") {
                    var loadingBounce by remember { mutableStateOf(false) }
                    var loadingFade by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LoadingButton(
                            onClick = { loadingBounce = !loadingBounce },
                            loading = loadingBounce,
                            animationType = AnimationType.Bounce,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Bounce", fontSize = 14.sp)
                        }
                        LoadingButton(
                            onClick = { loadingFade = !loadingFade },
                            loading = loadingFade,
                            animationType = AnimationType.Fade,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Fade", fontSize = 14.sp)
                        }
                    }
                }
            }

            item {
                ShowcaseSection(title = "CustomVerticalProgressBar — 3-state stepped progress") {
                    CustomVerticalProgressBar(
                        currentState = ProgressState.PENDING,
                        requestedContent = "Order placed",
                        pendingContent = "In preparation",
                        completedContent = "Delivered"
                    )
                }
            }
        }
    }
}
