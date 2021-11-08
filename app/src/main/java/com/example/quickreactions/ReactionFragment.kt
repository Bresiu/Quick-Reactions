package com.example.quickreactions

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quickreactions.databinding.ReactionFragmentBinding
import com.example.quickreactions.databinding.ReactionPopupBinding


class ReactionFragment : Fragment() {
    private lateinit var reactViewModel: ReactionViewModel
    private lateinit var binding: ReactionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        reactViewModel = ViewModelProvider(this).get(ReactionViewModel::class.java)
        return ReactionFragmentBinding.inflate(inflater, container, false).apply {
            reactionButton.setOnClickListener { showReactions(requireContext()) }
            reactionButton.reveal()
            binding = this
            refreshReactionsCount()
        }.root
    }

    private fun refreshReactionsCount() {
        reactViewModel.reactions.observe(this) {
            binding.reactionsContainer.removeAllViews()
            it.forEach { (code, count, isOwn) ->
                binding.reactionsContainer.addView(
                    ReactionCounterView.build(
                        layoutInflater,
                        code,
                        count,
                        isOwn,
                    ) { reactionCode ->
                        reactViewModel.addOwnReaction(reactionCode)
                    }
                )
            }
        }
    }

    private fun showReactions(context: Context) {
        fun addEmojisToContainer(
            container: ViewGroup,
            onReactionClickListener: (String) -> Unit
        ) {
            reactViewModel.getSupportedEmojis().forEach { emojiCode ->
                container.addView(
                    ReactionView.buildView(
                        context,
                        emojiCode,
                        onReactionClickListener = onReactionClickListener
                    )
                )
            }
        }

        val popupBinding = ReactionPopupBinding.inflate(layoutInflater)
        val popupWindow = PopupWindow(
            popupBinding.root,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        popupWindow.isOutsideTouchable = true
        addEmojisToContainer(popupBinding.reactionsContainer) { emojiCode ->
            reactViewModel.addOwnReaction(emojiCode)
            refreshReactionsCount()
            popupWindow.dismiss()
        }
        popupBinding.root.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        popupWindow.showAtLocation(
            binding.root,
            Gravity.CENTER_HORIZONTAL or Gravity.TOP,
            0,
            binding.reactionButton.y.toInt() - popupBinding.root.measuredHeight - reactionPopupMargin
        )
    }

    companion object {
        private const val reactionPopupMargin = 20
    }
}