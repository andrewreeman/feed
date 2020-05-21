package com.stepwise.feed.ui.mainpage

import android.animation.AnimatorSet
import android.view.animation.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stepwise.feed.R


class FabAnimator {
    private val shrinkAnimation = ScaleAnimation(1f, 0.5f, 1f, 0.5f)
    private val rotateAnimation = RotateAnimation(
        30f, 90f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
    )
    private val rotateAnimationEnd = RotateAnimation(
        30f, 90f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
    )

    private val growAnimation =  ScaleAnimation(0.5f, 1f, 0.5f, 1f)

    private var onComplete: (() -> Unit)? = null

    init {
        listOf(shrinkAnimation, growAnimation, rotateAnimation).forEach {
            it.apply{
                fillBefore = true
                fillAfter = true
                isFillEnabled = true
                duration = 1000
                interpolator = OvershootInterpolator()
            }
        }

        rotateAnimation.duration = 200
        rotateAnimation.repeatCount = Animation.INFINITE

        rotateAnimationEnd.duration = 600
    }

    fun shrink(fab: FloatingActionButton, onComplete: (() -> Unit)?) {
        fab.setImageResource(R.drawable.baseline_add_white_18)
        val set = AnimationSet(true)
        set.addAnimation(rotateAnimation)
        set.addAnimation(shrinkAnimation)

        this.onComplete = onComplete
        fab.startAnimation(set)
    }

    fun grow(fab: FloatingActionButton) {
        fab.postOnAnimation {
            val set = AnimationSet(true)
            set.addAnimation(rotateAnimationEnd)
            set.addAnimation(growAnimation)

            onComplete?.let {_onComplete ->
                set.setAnimationListener( object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {}

                    override fun onAnimationEnd(animation: Animation?) {
                        _onComplete()
                    }

                    override fun onAnimationStart(animation: Animation?) {}
                })
            }

            fab.startAnimation(set)
        }


    }
}