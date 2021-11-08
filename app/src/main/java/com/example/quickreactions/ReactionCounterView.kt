package com.example.quickreactions

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import com.example.quickreactions.databinding.ReactionCounterButtonBinding

class ReactionCounterView {
    companion object {
        fun build(
            inflater: LayoutInflater,
            reactionCode: String,
            reactionCount: Int,
            ownHighlighted: Boolean,
            reactionSize: Float = REACTION_SIZE,
            onReactionClickListener: (String) -> Unit,
        ): View {
            return ReactionCounterButtonBinding.inflate(inflater).root.apply {
                if (ownHighlighted) {
                    backgroundTintList =
                        ColorStateList.valueOf(context.getColor(R.color.purple_50))
                }
                textSize = reactionSize
                text =
                    context.getString(
                        R.string.reaction_counter,
                        reactionCode.toEmoji(),
                        reactionCount
                    )
                setOnClickListener {
                    onReactionClickListener(reactionCode)
                }
            }
        }

        private const val REACTION_SIZE = 20F
    }
}