@file:OptIn(ExperimentalMaterialApi::class)

package com.scallop.jetpackcomposeviews.showcase.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scallop.jetpackcomposeviews.customviews.AnimationType
import com.scallop.jetpackcomposeviews.customviews.ButtonAnimation
import com.scallop.jetpackcomposeviews.customviews.ButtonStyle
import com.scallop.jetpackcomposeviews.customviews.GradientBorderButtonClick
import com.scallop.jetpackcomposeviews.customviews.HeartAnimation
import com.scallop.jetpackcomposeviews.customviews.LoadingButton
import com.scallop.jetpackcomposeviews.customviews.SwipeButton
import com.scallop.jetpackcomposeviews.customviews.bounceClick
import com.scallop.jetpackcomposeviews.showcase.ShowcaseSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsShowcaseScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buttons") },
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
                ShowcaseSection(title = "GradientBorderButton — gradient border with click counter") {
                    GradientBorderButtonClick(
                        colors = listOf(Color(0xFF6200EE), Color(0xFF03DAC5)),
                        paddingValues = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
                        widthFraction = 1f
                    )
                }
            }

            item {
                ShowcaseSection(title = "ButtonAnimation — scale on press") {
                    ButtonAnimation()
                }
            }

            item {
                ShowcaseSection(title = "HeartAnimation — heart icon bounce") {
                    HeartAnimation()
                }
            }

            item {
                ShowcaseSection(title = "ButtonStyle — corner style variants") {
                    ButtonStyle()
                }
            }

            item {
                ShowcaseSection(title = "LoadingButton — animated loading indicator") {
                    var loading by remember { mutableStateOf(false) }
                    LoadingButton(
                        onClick = { loading = !loading },
                        loading = loading,
                        animationType = AnimationType.Bounce,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = if (loading) "Loading…" else "Toggle Loading", fontSize = 16.sp)
                    }
                }
            }

            item {
                ShowcaseSection(title = "SwipeButton — swipe to confirm") {
                    var swiped by remember { mutableStateOf(false) }
                    com.scallop.jetpackcomposeviews.customviews.SwipeButton(
                        text = if (swiped) "Confirmed!" else "Swipe to confirm",
                        isComplete = swiped,
                        onSwipe = { swiped = true }
                    )
                }
            }

            item {
                ShowcaseSection(title = "bounceClick — scale down on press (tap the box)") {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(120.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .bounceClick()
                    ) {
                        Text("Tap me!", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            }
        }
    }
}
