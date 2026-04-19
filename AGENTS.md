# Agent Instructions — Jetpack Compose Views

This document is the single source of truth for AI agents (Claude Code, GitHub Copilot, etc.) working on this project.

## What This Project Is

A showcase Android app containing 60+ custom Jetpack Compose components. The app itself is the catalogue: run it on a device or emulator to see every component in action. There is no production backend, no data layer, and no architecture framework — only UI code.

## Key Conventions

### Package Layout
| Package | Purpose |
|---|---|
| `customviews/` | All reusable composables. Flat files for single-topic components; sub-packages for multi-file topics |
| `showcase/` | Navigation host + per-category showcase screens; touch nothing here unless adding a new category |
| `layout/` | Layout-only composables (badges, shimmer wrapper, tabs) |
| `effects/` | Visual/interaction effects that are not UI components |
| `ui/theme/` | Material3 theme — change colours here, nowhere else |

### Coding Rules
- **No architecture overhead.** No ViewModels, no repositories, no DI. Components are self-contained composables.
- **Mixed Material versions are intentional.** Many components use `androidx.compose.material` (M2); the showcase shell uses `androidx.compose.material3` (M3). Do not migrate one to the other without a specific task.
- **No new dependencies without discussion.** The dependency list is deliberately small.
- **Showcase first.** Every new component must have a matching `ShowcaseSection` in the relevant `showcase/screens/*Showcase.kt`.

### Adding a Component
1. Create `customviews/<Category>.kt` (or a sub-package file).
2. Write a public `@Composable` function. Keep parameters minimal and provide sensible defaults.
3. Add a `ShowcaseSection` entry in `showcase/screens/<Category>Showcase.kt`.
4. Update `docs/<category>.md`.

### Modifying the Showcase Navigation
All routing lives in `activities/MainActivity.kt` → `ShowcaseNavHost`. The destination sealed class is `showcase/ShowcaseDestination.kt`. Add a new `object` to the sealed class and a new `when` branch in `ShowcaseNavHost`.

## Build & Run

```bash
# Debug build
./gradlew assembleDebug

# Run all tests (currently minimal)
./gradlew test
```

Min SDK: 26 | Target SDK: 36 | Kotlin JVM target: 11

## Do Not

- Do not change the LAUNCHER activity away from `MainActivity`.
- Do not add navigation-compose; the project uses state-based navigation intentionally.
- Do not break the `SleepGraphActivity` or `AppBarMainActivity` — they are kept for direct deep-link access.
- Do not commit generated files, `.idea/`, or build output.
