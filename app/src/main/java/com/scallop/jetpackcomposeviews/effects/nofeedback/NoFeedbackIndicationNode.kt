package com.scallop.jetpackcomposeviews.effects.nofeedback

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode

/**
 * A modifier node that implements no-feedback indication behavior.
 * This node draws content without any visual indication effects.
 *
 * Unlike default indication nodes that add ripples, highlights, or other visual feedback,
 * this node simply passes through the content drawing without any modifications.
 *
 * Original code: https://narendranathchatterjee.medium.com/disabling-click-indications-app-wide-in-jetpack-compose-4a7b39f900f4
 */
private class NoFeedbackIndicationNode(
    private val interactionSource: InteractionSource
) : Modifier.Node(), DrawModifierNode {
    override fun ContentDrawScope.draw() {
        // Simply draw the content without any indication effects
        // This is the key: we don't add any visual layers or effects
        drawContent()
    }

    // No additional setup needed for this indication type
    override fun onAttach() {
        super.onAttach()
        // Intentionally empty - no indication effects to set up
        // We don't need to listen to interaction source events
        // since we're not providing any visual feedback
    }

    override fun onDetach() {
        super.onDetach()
        // Intentionally empty - no indication effects to clean up
    }
}
