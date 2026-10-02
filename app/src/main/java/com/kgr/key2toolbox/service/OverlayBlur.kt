package com.kgr.key2toolbox.service

import android.animation.ValueAnimator
import android.view.View
import android.view.WindowManager

/**
 * Keeps a blur-behind overlay window's blur in step with its fade-out.
 *
 * The blur is applied by the compositor to the whole window, not by the view tree: fading the root view's alpha
 * leaves the window's blur radius at full strength until the window is removed, and then it vanishes in one frame,
 * which reads as the blur "sticking" and then popping. Ramping `blurBehindRadius` to 0 alongside the fade avoids it.
 * Does nothing when the window has no blur or animations are off ([ms] <= 0).
 */
object OverlayBlur {
    fun fadeOut(wm: WindowManager?, v: View, lp: WindowManager.LayoutParams?, ms: Long) {
        if (wm == null || lp == null || lp.blurBehindRadius <= 0 || ms <= 0L) return
        ValueAnimator.ofInt(lp.blurBehindRadius, 0).apply {
            duration = ms
            addUpdateListener {
                try {
                    lp.blurBehindRadius = it.animatedValue as Int
                    wm.updateViewLayout(v, lp)
                } catch (_: Exception) {
                    cancel() // window already gone
                }
            }
            start()
        }
    }
}
