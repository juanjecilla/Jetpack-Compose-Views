package com.scallop.jetpackcomposeviews.showcase

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.LinearScale
import androidx.compose.material.icons.rounded.Style
import androidx.compose.material.icons.rounded.TextFields
import androidx.compose.material.icons.rounded.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class CategoryItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val destination: ShowcaseDestination
)

val showcaseCategories = listOf(
    CategoryItem(
        "Animations & Effects",
        "Space, twinkling stars, staggered",
        Icons.Rounded.AutoAwesome,
        ShowcaseDestination.Animations
    ),
    CategoryItem(
        "Buttons",
        "Gradient border, swipe, repeating, click effects",
        Icons.Rounded.TouchApp,
        ShowcaseDestination.Buttons
    ),
    CategoryItem(
        "Text Effects",
        "Marquee, shimmer, ticker, typewriter, dot matrix",
        Icons.Rounded.TextFields,
        ShowcaseDestination.TextEffects
    ),
    CategoryItem(
        "Forms & Input",
        "Text field, debounced, search bar",
        Icons.Rounded.Edit,
        ShowcaseDestination.Forms
    ),
    CategoryItem(
        "Indicators & Progress",
        "Page dots, pulse, loading button, stepped progress",
        Icons.Rounded.LinearScale,
        ShowcaseDestination.Indicators
    ),
    CategoryItem(
        "Selectors",
        "Clock time picker, date carousel, podcast slider",
        Icons.Rounded.DateRange,
        ShowcaseDestination.Selectors
    ),
    CategoryItem(
        "Charts & Graphs",
        "Line graph, sleep timeline, stacked bar",
        Icons.Rounded.BarChart,
        ShowcaseDestination.Graphs
    ),
    CategoryItem(
        "Layouts & Containers",
        "Card stack, corner badge, jumpy row, tabs",
        Icons.Rounded.Dashboard,
        ShowcaseDestination.Layouts
    ),
    CategoryItem(
        "Special Views",
        "Credit card flip, FUT card, 7-segment, mirror",
        Icons.Rounded.Style,
        ShowcaseDestination.SpecialViews
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowcaseHomeScreen(onNavigate: (ShowcaseDestination) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jetpack Compose Views") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(padding)
        ) {
            items(showcaseCategories) { category ->
                CategoryCard(
                    category = category,
                    onClick = { onNavigate(category.destination) }
                )
            }
        }
    }
}

@Composable
fun CategoryCard(category: CategoryItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(category.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    category.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Rounded.ArrowForwardIos,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ShowcaseSection(title: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        HorizontalDivider()
        content()
    }
}
