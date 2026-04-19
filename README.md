# Jetpack Compose Views

A curated library of custom Jetpack Compose components demonstrating advanced UI patterns, animations, and interactive widgets for Android.

## Running the Showcase

Open the project in Android Studio and run the `app` module. The app launches a full **showcase** that lets you browse and interact with every component grouped by category.

## Component Categories

| Category | Components |
|---|---|
| [Animations & Effects](docs/animations.md) | Space (twinkling stars), staggered animations, particle effects |
| [Buttons](docs/buttons.md) | Gradient border, loading, swipe, repeating, click effects |
| [Text Effects](docs/text-effects.md) | Marquee, shimmer, ticker board, typewriter, dot-matrix, auto-spacing |
| [Forms & Input](docs/forms.md) | AppTextField, DebouncedTextField, SearchBar, AutoSizeTextField |
| [Indicators & Progress](docs/indicators.md) | Page dots, pulse indicator, loading button, stepped vertical progress |
| [Selectors](docs/selectors.md) | Clock time picker, date carousel, podcast-style slider, scroll bar |
| [Charts & Graphs](docs/graphs.md) | Bezier line graph, sleep timeline, animated stacked bar chart |
| [Layouts & Containers](docs/layouts.md) | Card stack, corner badge, jumpy row, shimmer layout, Chrome tabs |
| [Special Views](docs/special-views.md) | Credit card flip, FUT card, 7-segment display, mirror reflection |

## Project Structure

```
app/src/main/java/com/scallop/jetpackcomposeviews/
├── activities/          # Entry points (MainActivity is the launcher)
├── animations/          # Staggered animation utilities
├── customviews/         # All custom composables
│   ├── cards/           # FUT card
│   ├── dates/           # Date carousel
│   ├── graphs/          # Sleep graph, stacked bar chart
│   ├── layouts/         # JumpyRow
│   ├── progressbar/     # Progress button, vertical stepped bar
│   ├── selectors/       # Time picker, custom scroll bar
│   └── texts/           # Text effect composables
├── effects/             # Visual effects (flip, pulse, 3D, tube light)
├── layout/              # Layout composables (badges, shimmer, tabs)
├── shapes/              # Custom Shape implementations
├── showcase/            # Showcase navigation and screens
└── ui/theme/            # Material3 theme
```

## Tech Stack

- **Kotlin** — language
- **Jetpack Compose** — UI toolkit (Material 2 + Material 3)
- **ConstraintLayout Compose** — MotionLayout app bar
- **compose-shimmer** — shimmer loading effect
- **kotlinx-datetime** — date/calendar utilities
- **Min SDK 26**, **Target SDK 36**

## Adding a New Component

1. Create a `.kt` file in the appropriate `customviews/` sub-package.
2. Write a `@Composable` function following the existing naming conventions.
3. Add a showcase entry: open the relevant `showcase/screens/*Showcase.kt` and add a `ShowcaseSection` item.
4. Update the component's doc page in `docs/`.
