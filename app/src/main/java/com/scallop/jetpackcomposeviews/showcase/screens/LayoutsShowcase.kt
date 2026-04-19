package com.scallop.jetpackcomposeviews.showcase.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.scallop.jetpackcomposeviews.customviews.CardStack
import com.scallop.jetpackcomposeviews.customviews.layouts.JumpyRow
import com.scallop.jetpackcomposeviews.layout.BadgeCorner
import com.scallop.jetpackcomposeviews.layout.CornerBadgeBox
import com.scallop.jetpackcomposeviews.layout.TabsExample
import com.scallop.jetpackcomposeviews.showcase.ShowcaseSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutsShowcaseScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Layouts & Containers") },
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
                ShowcaseSection(title = "CardStack — stacked cards with rotation offset") {
                    CardStack(modifier = Modifier.fillMaxWidth()) {
                        repeat(4) { index ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                                    .rotate((-3 + index * 2).toFloat())
                            ) {
                                Box(
                                    modifier = Modifier.padding(16.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Text("Card ${index + 1}", style = MaterialTheme.typography.titleMedium)
                                }
                            }
                        }
                    }
                }
            }

            item {
                ShowcaseSection(title = "CornerBadgeBox — corner ribbon badge") {
                    CornerBadgeBox(
                        badgeColor = Color(0xFF6200EE),
                        corner = BadgeCorner.TopRight,
                        badgeContent = {
                            Text(
                                "NEW",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    ) {
                        Card(modifier = Modifier.size(150.dp)) {
                            Box(
                                modifier = Modifier.padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Content", modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                }
            }

            item {
                ShowcaseSection(title = "JumpyRow — wave animation row") {
                    JumpyRow(
                        modifier = Modifier.fillMaxWidth(),
                        waveWidth = 200.dp,
                        waveHeight = 20.dp
                    ) {
                        repeat(8) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Card(modifier = Modifier.size(24.dp)) {}
                            }
                        }
                    }
                }
            }

            item {
                ShowcaseSection(title = "Tabs — Chrome-inspired custom tabs") {
                    TabsExample()
                }
            }
        }
    }
}
