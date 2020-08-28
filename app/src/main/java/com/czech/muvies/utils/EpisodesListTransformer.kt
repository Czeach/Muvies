package com.czech.muvies.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class EpisodesListTransformer : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {

        val absPos = abs(position)
        page.apply {
            translationY = absPos * 200f
            translationX = absPos * 400f
        }

        when {
            position < -1 ->
                page.alpha = 0.1f
            position <= 1 -> {
                page.alpha = 0.2f.coerceAtLeast(1 - absPos)
            }
            else -> page.alpha = 0.1f
        }
    }

}