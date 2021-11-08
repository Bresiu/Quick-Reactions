package com.example.quickreactions

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.widget.AppCompatTextView

class ReactionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    companion object {
        private const val REACTION_SIZE = 34F
        fun buildView(
            context: Context,
            reactionCode: String,
            reactionSize: Float = REACTION_SIZE,
            onReactionClickListener: (String) -> Unit,
        ) = ReactionView(context).apply {
            setOnClickListener {
                it.animate()
                    .alpha(0.3f)
                    .scaleX(2.5f)
                    .scaleY(2.5f)
                    .setInterpolator(AccelerateInterpolator())
                    .setDuration(200)
                    .withEndAction {
                        onReactionClickListener(reactionCode)
                    }
                    .start()
            }
            textSize = reactionSize
            text = reactionCode.toEmoji()
        }
    }
}