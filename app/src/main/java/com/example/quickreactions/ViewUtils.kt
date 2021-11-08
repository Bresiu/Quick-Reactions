package com.example.quickreactions

import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.widget.ImageView
import kotlin.math.hypot

fun ImageView.reveal() {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            // Ensure you call it only once :
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            val cx = width / 2
            val cy = height / 2
            // get the final radius for the clipping circle
            val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
            // create the animator for this view (the start radius is zero)
            val delayAnimator =
                ViewAnimationUtils.createCircularReveal(this@reveal, cx, cy, 0f, finalRadius)
            // make the view visible and start the animation
            visibility = View.VISIBLE
            delayAnimator.start()
        }
    })
}