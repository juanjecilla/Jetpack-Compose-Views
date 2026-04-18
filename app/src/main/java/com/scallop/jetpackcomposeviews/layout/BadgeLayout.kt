package com.scallop.jetpackcomposeviews.layout

// https://medium.com/@kappdev/how-to-create-a-custom-corner-badge-in-jetpack-compose-acabd4cc04ca

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

/**
 * @param cornerRoundness A value between 0 and 1 indicating the curvature of the badge's corners.
 * - `0f` creates sharp corners.
 * - `1f` creates fully rounded, smooth corners.
 */
private class CornerBadgeShape(
    @FloatRange(0.0, 1.0)
    private val cornerRoundness: Float,
) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val (width, height) = size
        val cornerLength = computeAdjustedCornerLength(width, height, cornerRoundness)

        val path = Path().apply {
            moveTo(0f, height)

            lineTo(x = height - cornerLength / 2, y = cornerLength / 2)
            cubicTo(
                x1 = height, y1 = 0f,
                x2 = height + cornerLength / 3, y2 = 0f,
                x3 = height + cornerLength, y3 = 0f
            )

            lineTo(x = width - height - cornerLength, y = 0f)
            cubicTo(
                x1 = width - height - cornerLength / 3, y1 = 0f,
                x2 = width - height, y2 = 0f,
                x3 = width - height + cornerLength / 2, y3 = cornerLength / 2
            )

            lineTo(x = width, height)
            cubicTo(
                x1 = width - cornerLength / 2, y1 = height - cornerLength / 2,
                x2 = width - height * 0.66f, y2 = height - cornerLength / 2,
                x3 = width - height, y3 = height - cornerLength / 2
            )

            lineTo(x = height, y = height - cornerLength / 2)
            cubicTo(
                x1 = height * 0.66f, y1 = height - cornerLength / 2,
                x2 = cornerLength / 2, y2 = height - cornerLength / 2,
                x3 = 0f, y3 = height
            )

            close()
        }

        return Outline.Generic(path)
    }

    companion object {

        private const val BASE_CORNER_RATIO = 1f / 3f

        /**
         * Computes the effective corner curve length based on size constraints and roundness factor.
         *
         * This function ensures the curve used for shaping the badge corner:
         * - Scales proportionally with the height.
         * - Does not exceed the available corner space.
         *
         * @param width Total width of the shape.
         * @param height Total height of the shape.
         * @param cornerRoundness Roundness value from `0f` to `1f`.
         * @return The computed corner length constrained by [width], [height], and [cornerRoundness].
         */
        fun computeAdjustedCornerLength(width: Float, height: Float, cornerRoundness: Float): Float {
            val targetCornerLength = height * BASE_CORNER_RATIO * cornerRoundness.coerceIn(0f, 1f)
            val maxCornerLength = computeMaxCornerFitLength(width, height).coerceAtLeast(0f)
            return targetCornerLength.coerceAtMost(maxCornerLength)
        }

        /**
         * Computes the maximum length a corner curve can occupy without distortion.
         *
         * @param width Total width of the shape.
         * @param height Total height of the shape.
         * @return Maximum safe corner curve length.
         */
        private fun computeMaxCornerFitLength(width: Float, height: Float): Float {
            return (width - height * 2) / 2
        }
    }
}

/**
 * Represents corner positions for placing a badge.
 *
 * @see BadgeCorner.TopLeft
 * @see BadgeCorner.TopRight
 * @see BadgeCorner.BottomLeft
 * @see BadgeCorner.BottomRight
 */
sealed class BadgeCorner(
    val alignment: Alignment,
    val layoutScaleX: Float,
    val layoutScaleY: Float,
    val innerScaleX: Float,
    val innerScaleY: Float
) {
    object TopLeft: BadgeCorner(Alignment.TopStart, 1f, 1f, 1f, 1f)
    object TopRight: BadgeCorner(Alignment.TopEnd, -1f, 1f, -1f, 1f)
    object BottomLeft: BadgeCorner(Alignment.BottomStart, 1f, -1f, 1f, -1f)
    object BottomRight: BadgeCorner(Alignment.BottomEnd, -1f, -1f, -1f, -1f)
}

/**
 * A composable that places a badge in a specified corner of a container.
 *
 * @param badgeColor The [Color] used to fill the main visible badge strip.
 * @param modifier The [Modifier] to apply to the outer container.
 * @param backBadgeColor The [Color] used for the decorative back strips. Defaults to a darker version of [badgeColor].
 * @param corner The corner of the container where the badge is placed. See [BadgeCorner].
 * @param contentPadding The padding between the container edges and the badge.
 * @param stripThickness The thickness of the badge strips. Note that the visual thickness may appear smaller depending on [cornerRoundness].
 * @param cornerPadding The distance from the corner to where the badge begins.
 * @param cornerRoundness A value between `0f` (sharp) and `1f` (fully rounded) that controls the roundness of the badge.
 * @param badgeContent A composable lambda that defines the content inside the badge.
 * @param content A composable lambda that defines the main content inside the container.
 */
@Composable
fun CornerBadgeBox(
    badgeColor: Color,
    modifier: Modifier = Modifier,
    backBadgeColor: Color = CornerBadgeUtil.darkenColor(badgeColor),
    corner: BadgeCorner = DefaultBadgeCorner,
    contentPadding: Dp = DefaultContentPadding,
    stripThickness: Dp = DefaultStripThickness,
    cornerPadding: Dp = DefaultCornerPadding,
    cornerRoundness: Float = DefaultCornerRoundness,
    badgeContent: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    CornerBadgeBox(
        badgeBrush = SolidColor(badgeColor),
        backBadgeBrush = SolidColor(backBadgeColor),
        modifier = modifier,
        corner = corner,
        contentPadding = contentPadding,
        stripThickness = stripThickness,
        cornerPadding = cornerPadding,
        cornerRoundness = cornerRoundness,
        badgeContent = badgeContent,
        content = content
    )
}

/**
 * A composable that places a badge in a specified corner of a container.
 *
 * @param badgeBrush The [Brush] used to draw the main visible badge strip.
 * @param backBadgeBrush The [Brush] used to draw the decorative back strips.
 * @param modifier The [Modifier] to apply to the outer container.
 * @param corner The corner of the container where the badge is placed. See [BadgeCorner].
 * @param contentPadding The padding between the container edges and the badge.
 * @param stripThickness The thickness of the badge strips. Note that the visual thickness may appear smaller depending on [cornerRoundness].
 * @param cornerPadding The distance from the corner to where the badge begins.
 * @param cornerRoundness A value between `0f` (sharp) and `1f` (fully rounded) that controls the roundness of the badge.
 * @param badgeContent A composable lambda that defines the content inside the badge.
 * @param content A composable lambda that defines the main content inside the container.
 */
@Composable
fun CornerBadgeBox(
    badgeBrush: Brush,
    backBadgeBrush: Brush,
    modifier: Modifier = Modifier,
    corner: BadgeCorner = DefaultBadgeCorner,
    contentPadding: Dp = DefaultContentPadding,
    stripThickness: Dp = DefaultStripThickness,
    cornerPadding: Dp = DefaultCornerPadding,
    cornerRoundness: Float = DefaultCornerRoundness,
    badgeContent: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val badgeShape = remember(cornerRoundness) { CornerBadgeShape(cornerRoundness) }

    val stripRoundnessPadding = remember(stripThickness, cornerRoundness, contentPadding, cornerPadding) {
        val badgeSideLength = squareDiagonal(stripThickness)
        val badgeWidth = squareDiagonal(contentPadding + cornerPadding + badgeSideLength)
        CornerBadgeShape.computeAdjustedCornerLength(badgeWidth.value, stripThickness.value, cornerRoundness).dp / 2
    }

    Layout(
        modifier = modifier,
        content = {
            Box(
                Modifier
                    .layoutId(LayoutId.SideBackBadge)
                    .scale(corner.layoutScaleX, corner.layoutScaleY)
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(0f, 1f)
                        rotationZ = 45f
                        scaleY = -1f
                    }
                    .background(backBadgeBrush, badgeShape)
            )
            Box(
                Modifier
                    .layoutId(LayoutId.TopBackBadge)
                    .scale(corner.layoutScaleX, corner.layoutScaleY)
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(0f, 1f)
                        rotationZ = 45f
                    }
                    .background(backBadgeBrush, badgeShape)
            )
            Box(
                modifier = Modifier.layoutId(LayoutId.Content),
                content = content
            )
            Box(
                content = badgeContent,
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .layoutId(LayoutId.Badge)
                    .scale(corner.layoutScaleX, corner.layoutScaleY)
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(0f, 1f)
                        rotationZ = -45f
                    }
                    .background(badgeBrush, badgeShape)
                    .clip(badgeShape)
                    .padding(bottom = stripRoundnessPadding)
                    .scale(corner.innerScaleX, corner.innerScaleY)
            )
        }
    ) { measurables, constraints ->
        val contentPaddingPx = contentPadding.roundToPx()
        val stripThicknessPx = stripThickness.roundToPx()
        val cornerPaddingPx = cornerPadding.roundToPx()

        val badgeSideLength = squareDiagonal(stripThicknessPx.toFloat())
        val badgeWidth = squareDiagonal(contentPaddingPx + cornerPaddingPx + badgeSideLength)

        val stripRoundnessPadding = CornerBadgeShape.computeAdjustedCornerLength(badgeWidth, stripThicknessPx.toFloat(), cornerRoundness) / 2
        val backBadgeCornerPadding = contentPaddingPx + cornerPaddingPx + squareDiagonal(stripRoundnessPadding)

        val contentPlaceable = measurables.first { it.layoutId == LayoutId.Content }.measure(constraints)
        val badgePlaceable = measurables.first { it.layoutId == LayoutId.Badge }.measure(
            Constraints.fixed(badgeWidth.toInt(), stripThicknessPx)
        )

        val sideBackBadgePlaceable = measurables.first { it.layoutId == LayoutId.SideBackBadge }.measure(
            Constraints.fixed(badgeWidth.toInt(), stripThicknessPx)
        )
        val topBackBadgePlaceable = measurables.first { it.layoutId == LayoutId.TopBackBadge }.measure(
            Constraints.fixed(badgeWidth.toInt(), stripThicknessPx)
        )

        val layoutWidth = contentPlaceable.width
        val layoutHeight = contentPlaceable.height

        layout(layoutWidth, layoutHeight) {

            val badgePosition = corner.alignment.align(
                size = IntSize(badgePlaceable.width, badgePlaceable.height),
                space = IntSize(layoutWidth + contentPaddingPx * 2, layoutHeight + contentPaddingPx * 2),
                layoutDirection = LayoutDirection.Ltr
            ) - IntOffset(contentPaddingPx, contentPaddingPx)

            // Place the side back badge
            sideBackBadgePlaceable.placeWithLayer(badgePosition) {
                translationY = (-stripThicknessPx + backBadgeCornerPadding) * corner.layoutScaleY
            }

            // Place the top back badge
            topBackBadgePlaceable.placeWithLayer(badgePosition) {
                translationY = -stripThicknessPx * corner.layoutScaleY
                translationX = backBadgeCornerPadding * corner.layoutScaleX
            }

            // Place the main content
            contentPlaceable.placeRelative(0, 0)

            // Place the foreground badge
            badgePlaceable.placeWithLayer(badgePosition) {
                val offset = contentPaddingPx + cornerPaddingPx + badgeSideLength
                translationY = (-stripThicknessPx + offset) * corner.layoutScaleY
            }
        }
    }
}

private enum class LayoutId {
    SideBackBadge,
    TopBackBadge,
    Badge,
    Content
}

object CornerBadgeUtil {

    /**
     * Returns a darker version of the given [Color].
     *
     * @param color The original color to darken.
     * @param darkenBy The fraction to darken the color by, between 0 and 1.
     * @return A darker [Color] based on the input.
     */
    fun darkenColor(color: Color, @FloatRange(0.0, 1.0) darkenBy: Float = 0.3f): Color = with(color) {
        val factor = (1f - darkenBy)
        return copy(
            red = red * factor,
            green = green * factor,
            blue = blue * factor,
            alpha = alpha
        )
    }
}

/**
 * Calculates the diagonal length of a square given its side length in [Dp].
 *
 * @param side The length of the square's side.
 * @return The diagonal length as [Dp].
 */
private fun squareDiagonal(side: Dp): Dp {
    return squareDiagonal(side.value).dp
}

/**
 * Calculates the diagonal length of a square given its side length as a [Float].
 *
 * @param side The length of the square's side.
 * @return The diagonal length as a [Float].
 */
private fun squareDiagonal(side: Float): Float {
    return side * sqrt(2f)
}

private val DefaultBadgeCorner = BadgeCorner.TopRight
private val DefaultContentPadding = 4.dp
private val DefaultStripThickness = 48.dp
private val DefaultCornerPadding = 48.dp
private const val DefaultCornerRoundness = 0.5f