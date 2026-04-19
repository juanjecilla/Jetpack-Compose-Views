package com.scallop.jetpackcomposeviews.showcase.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scallop.jetpackcomposeviews.customviews.texts.AutoSpacedText
import com.scallop.jetpackcomposeviews.customviews.texts.DottedText
import com.scallop.jetpackcomposeviews.customviews.texts.MarqueeText
import com.scallop.jetpackcomposeviews.customviews.texts.ShimmeringText
import com.scallop.jetpackcomposeviews.customviews.texts.TickerBoard
import com.scallop.jetpackcomposeviews.customviews.texts.Typewriter
import com.scallop.jetpackcomposeviews.showcase.ShowcaseSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextEffectsShowcaseScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Text Effects") },
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
                ShowcaseSection(title = "MarqueeText — continuously scrolling text") {
                    MarqueeText(
                        text = "Jetpack Compose custom views — scrolling marquee text example with gradient edges  ",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            item {
                ShowcaseSection(title = "ShimmeringText — shimmer gradient animation") {
                    ShimmeringText(
                        text = "Shimmering Text Effect",
                        shimmerColor = Color(0xFF6200EE),
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A1A)
                        )
                    )
                }
            }

            item {
                ShowcaseSection(title = "TickerBoard — flip-style counter display") {
                    TickerBoard(
                        text = "HELLO",
                        numColumns = 5,
                        numRows = 1,
                        fontSize = 32.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                ShowcaseSection(title = "Typewriter — animated typing with highlights") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1A1A2E))
                            .padding(16.dp)
                    ) {
                        Typewriter(
                            baseText = "I build ",
                            highlightedText = "build",
                            parts = listOf("Android apps", "Compose UIs", "great experiences")
                        )
                    }
                }
            }

            item {
                ShowcaseSection(title = "AutoSpacedText — letter spacing fills width") {
                    AutoSpacedText(
                        text = "COMPOSE",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                ShowcaseSection(title = "DottedText — retro dot-matrix text") {
                    DottedText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        text = "HI",
                        textScale = 3f
                    )
                }
            }
        }
    }
}
