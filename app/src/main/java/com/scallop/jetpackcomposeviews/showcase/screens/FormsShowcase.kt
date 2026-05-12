package com.scallop.jetpackcomposeviews.showcase.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Person
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scallop.jetpackcomposeviews.customviews.AppTextField
import com.scallop.jetpackcomposeviews.customviews.MySearchBar
import com.scallop.jetpackcomposeviews.customviews.texts.DebouncedTextField
import com.scallop.jetpackcomposeviews.customviews.texts.OutlinedUrlTextField
import com.scallop.jetpackcomposeviews.showcase.ShowcaseSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormsShowcaseScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Forms & Input") },
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
                ShowcaseSection(title = "OutlinedUrlTextField — highlights URLs and makes them clickable") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        var text by remember { mutableStateOf("Check out https://proandroiddev.com for more!") }
                        var lastClickedUrl by remember { mutableStateOf("") }
                        
                        OutlinedUrlTextField(
                            value = text,
                            onValueChange = { text = it },
                            onUrlClick = { lastClickedUrl = it },
                            label = { Text("URL Field") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        if (lastClickedUrl.isNotEmpty()) {
                            Text(
                                text = "Last clicked: $lastClickedUrl",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        var readOnlyText by remember { mutableStateOf("Read-only with links: https://google.com") }
                        OutlinedUrlTextField(
                            value = readOnlyText,
                            onValueChange = { readOnlyText = it },
                            onUrlClick = { lastClickedUrl = it },
                            label = { Text("Read-Only URL Field") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            item {
                ShowcaseSection(title = "AppTextField — outlined text field") {
                    var text by remember { mutableStateOf("") }
                    AppTextField(
                        text = text,
                        placeholder = "Enter your name…",
                        onChange = { text = it },
                        leadingIcon = {
                            Icon(
                                Icons.Rounded.Person,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                ShowcaseSection(title = "DebouncedTextField — fires onChange with 500ms debounce") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        var debouncedValue by remember { mutableStateOf("") }
                        DebouncedTextField(
                            debounceTime = 500L,
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = { debouncedValue = it }
                        )
                        if (debouncedValue.isNotEmpty()) {
                            Text(
                                text = "Debounced: $debouncedValue",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            item {
                ShowcaseSection(title = "MySearchBar — search bar with clear and mic icons") {
                    var query by remember { mutableStateOf("") }
                    MySearchBar(
                        text = query,
                        onTextChange = { query = it },
                        placeHolder = "Search…",
                        onCloseClicked = { query = "" },
                        onSearchClicked = {},
                        onMicClicked = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
