package com.scallop.jetpackcomposeviews.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.scallop.jetpackcomposeviews.showcase.ShowcaseDestination
import com.scallop.jetpackcomposeviews.showcase.ShowcaseHomeScreen
import com.scallop.jetpackcomposeviews.showcase.screens.AnimationsShowcaseScreen
import com.scallop.jetpackcomposeviews.showcase.screens.ButtonsShowcaseScreen
import com.scallop.jetpackcomposeviews.showcase.screens.FormsShowcaseScreen
import com.scallop.jetpackcomposeviews.showcase.screens.GraphsShowcaseScreen
import com.scallop.jetpackcomposeviews.showcase.screens.IndicatorsShowcaseScreen
import com.scallop.jetpackcomposeviews.showcase.screens.LayoutsShowcaseScreen
import com.scallop.jetpackcomposeviews.showcase.screens.SelectorsShowcaseScreen
import com.scallop.jetpackcomposeviews.showcase.screens.SpecialViewsShowcaseScreen
import com.scallop.jetpackcomposeviews.showcase.screens.TextEffectsShowcaseScreen
import com.scallop.jetpackcomposeviews.ui.theme.JetpackComposeViewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeViewsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShowcaseNavHost()
                }
            }
        }
    }
}

@Composable
fun ShowcaseNavHost() {
    var currentScreen: ShowcaseDestination by remember { mutableStateOf(ShowcaseDestination.Home) }

    BackHandler(enabled = currentScreen != ShowcaseDestination.Home) {
        currentScreen = ShowcaseDestination.Home
    }

    when (currentScreen) {
        ShowcaseDestination.Home -> ShowcaseHomeScreen(
            onNavigate = { currentScreen = it }
        )
        ShowcaseDestination.Animations -> AnimationsShowcaseScreen(
            onBack = { currentScreen = ShowcaseDestination.Home }
        )
        ShowcaseDestination.Buttons -> ButtonsShowcaseScreen(
            onBack = { currentScreen = ShowcaseDestination.Home }
        )
        ShowcaseDestination.TextEffects -> TextEffectsShowcaseScreen(
            onBack = { currentScreen = ShowcaseDestination.Home }
        )
        ShowcaseDestination.Forms -> FormsShowcaseScreen(
            onBack = { currentScreen = ShowcaseDestination.Home }
        )
        ShowcaseDestination.Indicators -> IndicatorsShowcaseScreen(
            onBack = { currentScreen = ShowcaseDestination.Home }
        )
        ShowcaseDestination.Selectors -> SelectorsShowcaseScreen(
            onBack = { currentScreen = ShowcaseDestination.Home }
        )
        ShowcaseDestination.Graphs -> GraphsShowcaseScreen(
            onBack = { currentScreen = ShowcaseDestination.Home }
        )
        ShowcaseDestination.Layouts -> LayoutsShowcaseScreen(
            onBack = { currentScreen = ShowcaseDestination.Home }
        )
        ShowcaseDestination.SpecialViews -> SpecialViewsShowcaseScreen(
            onBack = { currentScreen = ShowcaseDestination.Home }
        )
    }
}
