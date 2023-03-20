package com.scallop.jetpackcomposeviews.customviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

// https://proandroiddev.com/remove-ripple-effect-from-clickable-and-toggleable-widget-in-jetpack-compose-16b154265283
fun Modifier.noRippleToggleable(
    value: Boolean,
    onValueChange: (Boolean) -> Unit
): Modifier = composed {
    toggleable(
        value = value,
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onValueChange = onValueChange
    )
}

inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
