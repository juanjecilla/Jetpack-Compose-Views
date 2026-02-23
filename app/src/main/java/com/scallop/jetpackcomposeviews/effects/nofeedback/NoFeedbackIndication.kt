package com.scallop.jetpackcomposeviews.effects.nofeedback

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.node.DelegatableNode

/**
 * Original code: https://narendranathchatterjee.medium.com/disabling-click-indications-app-wide-in-jetpack-compose-4a7b39f900f4
 */


/**
 * An IndicationNodeFactory that creates indication nodes without any visual feedback.
 * Useful for clickable elements that should not show ripple effects or other indications.
 *
 * This factory creates nodes that simply draw content without any additional visual effects,
 * effectively disabling all click animations and feedback while preserving functionality.
 */
class NoFeedbackIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return NoFeedbackIndicationNode(interactionSource)
    }
    override fun hashCode(): Int = -1
    override fun equals(other: Any?): Boolean = other is NoFeedbackIndication
}
