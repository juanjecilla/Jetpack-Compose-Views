package com.scallop.jetpackcomposeviews.customviews.texts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlin.time.Duration


@Composable
fun DebouncedTextField(
    debounceTime: Long,
    modifier: Modifier = Modifier,
    initialValue: String = DefaultInitialValue,
    onValueChange: (String) -> Unit
) {
    BaseDebouncedTextField(
        modifier = modifier,
        debounceTime = debounceTime,
        onValueChange = onValueChange,
        initialValue = initialValue,
    )
}

@Composable
fun DebouncedTextField(
    debounceTime: Duration,
    modifier: Modifier = Modifier,
    initialValue: String = DefaultInitialValue,
    onValueChange: (String) -> Unit
) {
    BaseDebouncedTextField(
        modifier = modifier,
        debounceTime = debounceTime.inWholeMilliseconds,
        onValueChange = onValueChange,
        initialValue = initialValue,
    )
}

@OptIn(FlowPreview::class)
@Composable
private fun BaseDebouncedTextField(
    debounceTime: Long,
    modifier: Modifier,
    initialValue: String,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialValue) }

    LaunchedEffect(Unit) {
        snapshotFlow { text }
            .debounce { debounceTime }
            .collect {
                onValueChange(it)
            }
    }

    TextField(
        value = text,
        modifier = modifier,
        onValueChange = {
            text = it
        }
    )
}

@Preview
@Composable
private fun DebouncedTextField_Preview() {
    Box(modifier = Modifier.fillMaxSize()) {
        BaseDebouncedTextField(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center),
            debounceTime = 1000,
            initialValue = "Initial Value",
            onValueChange = {
                println("OnValueChange: $it")
            }
        )
    }
}

private const val DefaultInitialValue = ""
