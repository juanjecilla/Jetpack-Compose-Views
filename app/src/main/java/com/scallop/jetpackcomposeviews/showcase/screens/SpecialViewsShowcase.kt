package com.scallop.jetpackcomposeviews.showcase.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.scallop.jetpackcomposeviews.customviews.AddCreditCard
import com.scallop.jetpackcomposeviews.customviews.Mirror
import com.scallop.jetpackcomposeviews.customviews.SevenSegmentView
import com.scallop.jetpackcomposeviews.customviews.cards.FUTLikeCardUI
import com.scallop.jetpackcomposeviews.showcase.ShowcaseSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialViewsShowcaseScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Special Views") },
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
                ShowcaseSection(title = "AddCreditCard — flip card animation on tap") {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        AddCreditCard(
                            backgroundColor = Color(0xFF1A237E),
                            cardNumber = "**** **** **** 1234",
                            cardHolder = "John Doe",
                            cardExpiry = "12/26"
                        )
                    }
                }
            }

            item {
                ShowcaseSection(title = "FUTLikeCardUI — FIFA Ultimate Team-style card") {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        FUTLikeCardUI()
                    }
                }
            }

            item {
                ShowcaseSection(title = "SevenSegmentView — digital 7-segment display") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SevenSegmentView(
                            number = 42,
                            modifier = Modifier.size(width = 100.dp, height = 60.dp),
                            activeColor = Color(0xFF00FF00),
                            digitsNumber = 2
                        )
                        SevenSegmentView(
                            number = 99,
                            modifier = Modifier.size(width = 100.dp, height = 60.dp),
                            activeColor = Color(0xFFFF6600),
                            digitsNumber = 2
                        )
                    }
                }
            }

            item {
                ShowcaseSection(title = "Mirror — image with reflection effect") {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Mirror {
                            Text(
                                text = "REFLECT",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
