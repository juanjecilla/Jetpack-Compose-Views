---
name: Showcase architecture
description: How the showcase app is structured and navigated
type: project
---

MainActivity is the LAUNCHER activity. It hosts ShowcaseNavHost which uses a simple `var currentScreen: ShowcaseDestination` state variable (no navigation-compose dependency) to switch between screens.

**Why:** Avoids adding a nav-compose dependency to a pure component library demo.
**How to apply:** To add a new showcase category, add an `object` to `ShowcaseDestination.kt`, add a `when` branch in `MainActivity.kt`, create `showcase/screens/<Name>Showcase.kt`, and add a `CategoryItem` to the list in `ShowcaseHomeScreen.kt`.
