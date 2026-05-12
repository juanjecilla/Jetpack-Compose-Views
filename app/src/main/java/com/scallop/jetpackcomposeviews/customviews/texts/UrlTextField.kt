package com.scallop.jetpackcomposeviews.customviews.texts

import android.util.Patterns
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * [OutlinedUrlTextField] is a custom Jetpack Compose component designed to handle URLs within a text field
 * by providing dynamic highlighting and clickability.
 *
 * Based on: https://proandroiddev.com/outlinedurltextfield-in-jetpack-compose-86cd1c6f0325
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedUrlTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onUrlClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    urlSpanStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.primary,
        textDecoration = TextDecoration.Underline
    )
) {
    if (enabled && !readOnly) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            prefix = prefix,
            suffix = suffix,
            supportingText = supportingText,
            isError = isError,
            visualTransformation = UrlTransformation(urlSpanStyle),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )
    } else {
        val interactionSourceReadOnly = remember { MutableInteractionSource() }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSourceReadOnly,
            decorationBox = { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = {
                        Text(
                            text = buildAnnotatedStringWithUrlHighlighting(
                                text = value,
                                onUrlClick = onUrlClick,
                                urlSpanStyle = urlSpanStyle
                            ),
                            style = textStyle,
                            modifier = Modifier.padding(top = 8.dp) // Fix for label alignment
                        )
                    },
                    enabled = enabled,
                    singleLine = singleLine,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSourceReadOnly,
                    label = label,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    supportingText = supportingText,
                    isError = isError,
                    colors = colors,
                    contentPadding = OutlinedTextFieldDefaults.contentPadding(),
                    container = {
                        OutlinedTextFieldDefaults.Container(
                            enabled = enabled,
                            isError = isError,
                            interactionSource = interactionSourceReadOnly,
                            colors = colors,
                            shape = shape,
                        )
                    }
                )
            }
        )
    }
}

@Composable
fun buildAnnotatedStringWithUrlHighlighting(
    text: String,
    onUrlClick: (String) -> Unit,
    urlSpanStyle: SpanStyle
): AnnotatedString {
    return buildAnnotatedString {
        append(text)
        val matcher = Patterns.WEB_URL.matcher(text)
        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            val url = text.substring(start, end)

            addStyle(
                style = urlSpanStyle,
                start = start,
                end = end
            )

            // LinkAnnotation.Clickable is available in Compose 1.7+
            addLink(
                clickable = LinkAnnotation.Clickable(
                    tag = url,
                    linkInteractionListener = { onUrlClick(url) }
                ),
                start = start,
                end = end
            )
        }
    }
}

private class UrlTransformation(private val urlSpanStyle: SpanStyle) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            text = buildAnnotatedString {
                append(text.text)
                val matcher = Patterns.WEB_URL.matcher(text.text)
                while (matcher.find()) {
                    addStyle(
                        style = urlSpanStyle,
                        start = matcher.start(),
                        end = matcher.end()
                    )
                }
            },
            offsetMapping = OffsetMapping.Identity
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOutlinedUrlTextField() {
    MaterialTheme {
        var text by remember { mutableStateOf("Visit https://google.com or check http://example.org") }
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedUrlTextField(
                value = text,
                onValueChange = { text = it },
                onUrlClick = { /* Handle click */ },
                label = { Text("URL Text Field") },
                placeholder = { Text("Enter text with URLs") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Rounded.Link, contentDescription = null) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOutlinedUrlTextFieldReadOnly() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedUrlTextField(
                value = "Click this: https://proandroiddev.com",
                onValueChange = {},
                onUrlClick = { /* Handle click */ },
                label = { Text("Read-Only URL") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOutlinedUrlTextFieldDisabled() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedUrlTextField(
                value = "Disabled: https://kotlinlang.org",
                onValueChange = {},
                onUrlClick = {},
                label = { Text("Disabled URL") },
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
