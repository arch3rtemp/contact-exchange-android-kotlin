package com.sweeftdigital.contactsexchange.util.custom_segregation

import android.animation.Animator

interface AnimatorListenerOnAnimationStart : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator)

    override fun onAnimationCancel(animation: Animator) {

    }

    override fun onAnimationRepeat(animation: Animator) {

    }

    override fun onAnimationEnd(animation: Animator) {}
}