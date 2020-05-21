package com.stepwise.feed.ui.mainpage

import android.view.animation.OvershootInterpolator
import android.view.animation.ScaleAnimation
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FabAnimator {
    private val shrinkAnimation = ScaleAnimation(1f, 0f, 1f, 0f)
    private val growAnimation =  ScaleAnimation(0f, 1f, 0f, 1f)

    init {
        listOf(shrinkAnimation, growAnimation).forEach {
            it.apply{
                fillBefore = true
                fillAfter = true
                isFillEnabled = true
                duration = 300
                interpolator = OvershootInterpolator()
            }
        }
    }

    fun shrink(fab: FloatingActionButton) {
        fab.startAnimation(shrinkAnimation)
    }

    fun grow(fab: FloatingActionButton) {
        val anim = ScaleAnimation(0f, 1f, 0f, 1f)
        anim.fillBefore = true
        anim.fillAfter = true
        anim.isFillEnabled = true
        anim.duration = 400
        anim.interpolator = OvershootInterpolator()
        fab.startAnimation(anim)
    }
}