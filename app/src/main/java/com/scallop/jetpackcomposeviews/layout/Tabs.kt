package com.scallop.jetpackcomposeviews.layout

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// https://medium.com/@kappdev/how-to-create-chrome-inspired-custom-tabs-in-jetpack-compose-c4cb8aa33e91

class TabShape(
    private val cornerShape: RoundedCornerShape
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        // Convert corner dimensions to pixels
        val topStart = cornerShape.topStart.toPx(size, density)
        val topEnd = cornerShape.topEnd.toPx(size, density)
        val bottomEnd = cornerShape.bottomEnd.toPx(size, density)
        val bottomStart = cornerShape.bottomStart.toPx(size, density)

        // If all corners are 0, fallback to rectangle
        if (topStart + topEnd + bottomEnd + bottomStart == 0.0f) {
            return Outline.Rectangle(size.toRect())
        }

        // Adjust corners based on layout direction
        val topLeft = if (layoutDirection == LayoutDirection.Ltr) topStart else topEnd
        val topRight = if (layoutDirection == LayoutDirection.Ltr) topEnd else topStart
        val bottomRight = if (layoutDirection == LayoutDirection.Ltr) bottomEnd else bottomStart
        val bottomLeft = if (layoutDirection == LayoutDirection.Ltr) bottomStart else bottomEnd

        val (width, height) = size

        val tabPath = Path().apply {
            // Move to the beginning of the top side
            moveTo(x = topLeft, y = 0f)

            // 1: Top side
            lineTo(x = width - topRight, 0f)

            // 2: Top-right curve
            quadraticTo(
                x1 = width, y1 = 0f,
                x2 = width, y2 = topRight
            )

            // 3: Right side
            lineTo(x = width, height - bottomRight)

            // 4: Bottom-right curve
            quadraticTo(
                x1 = width, y1 = height,
                x2 = width + bottomRight, y2 = height
            )

            // 5: Bottom side
            lineTo(x = -bottomLeft, height)

            // 6: Bottom-left curve
            quadraticTo(
                x1 = 0f, y1 = height,
                x2 = 0f, y2 = height - bottomLeft
            )

            // 7: Left side
            lineTo(x = 0f, topLeft)

            // 8: Top-left curve
            quadraticTo(
                x1 = 0f, y1 = 0f,
                x2 = topLeft, y2 = 0f
            )
            close()
        }

        return Outline.Generic(tabPath)
    }
}

@Composable
fun Tab(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    cornerShape: RoundedCornerShape = RoundedCornerShape(16.dp),
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(containerColor),
    onClose: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .background(
                color = containerColor,
                // Apply the custom tab shape for a tab-like appearance
                shape = TabShape(cornerShape)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = title,
                maxLines = 1,
                fontSize = 16.sp
            )
            IconButton(
                onClick = onClose
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close tab",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

data class TabItem(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val content: @Composable () -> Unit
)

@Preview
@Composable
fun TabsExample(){
    // Define the list of tabs
    val tabs = remember { mutableStateListOf(
        TabItem(0, "Home", Icons.Rounded.Home) { Text("Home Content") },
        TabItem(1, "Mailbox", Icons.Rounded.Email) { Text("Mailbox Content") },
        TabItem(2, "Shop", Icons.Rounded.ShoppingCart) { Text("Shop Content") },
        TabItem(3, "Settings", Icons.Rounded.Settings) { Text("Settings Content") }
    )}
// Track the currently selected tab (or null if none)
    var selectedTab by remember { mutableStateOf<TabItem?>(tabs.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            // Prevent clipping of outward tab shape edges with horizontal padding
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(tabs, key = { it.id }) { tab ->
                val isActive = (tab.id == selectedTab?.id)

                Tab(
                    icon = tab.icon,
                    title = tab.title,
                    containerColor = if (isActive) {
                        MaterialTheme.colorScheme.surface
                    } else {
                        Color.Transparent
                    },
                    contentColor = if (isActive) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    },
                    onClose = {
                        val closedIndex = tabs.indexOf(tab)
                        if (tabs.remove(tab)) {
                            if (isActive) {
                                selectedTab = tabs.getOrNull(closedIndex.coerceAtMost(tabs.lastIndex))
                            }
                        }
                    },
                    onClick = {
                        selectedTab = tab
                    }
                )
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            selectedTab?.content()
        }
    }
}