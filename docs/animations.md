# Animations & Effects

## Space
**File:** `customviews/Annimations.kt`

Renders 1000 twinkling stars using `Canvas` and a sine-wave alpha animation driven by `InfiniteTransition`.

```kotlin
Space(modifier = Modifier.fillMaxSize())
```

Place over a dark background for best results.

---

## Staggered Animation
**File:** `animations/StaggeredAnimation.kt`

Utility for applying offset-delayed entry animations to a list of items.

---

## Effects (visual)
**Directory:** `effects/`

| File | Effect |
|---|---|
| `3DBoxEffect.kt` | 3D rotation / perspective transform |
| `FlipEffect.kt` | Card flip transition |
| `ParticleExplotion.kt` | Particle burst on interaction |
| `PulseEffect.kt` | Expanding ring pulse |
| `TubeLightEffect.kt` | Neon tube flicker/glow |
| `nofeedback/` | Custom `Indication` that suppresses ripple feedback |
