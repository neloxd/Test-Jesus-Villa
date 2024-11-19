package com.jesusvilla.test.designsystem.components.utils

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import com.jesusvilla.test.designsystem.components.utils.SilverAlpha.ACTIVE_ALPHA
import com.jesusvilla.test.designsystem.components.utils.SilverAlpha.INACTIVE_ALPHA
import com.jesusvilla.test.designsystem.components.utils.SilverAlpha.INACTIVE_ALPHA_IMAGE

object SilverAlpha {
    const val ACTIVE_ALPHA = 1.0F
    const val INACTIVE_ALPHA = 0.5F
    const val INACTIVE_ALPHA_IMAGE = 0.0F
}

fun View.activate(active: Boolean) {
    val alpha = if (active) ACTIVE_ALPHA else INACTIVE_ALPHA

    when(this){
        is ImageView -> {
            val grayscaleMatrix = ColorMatrix()
            grayscaleMatrix.setSaturation(if (active) ACTIVE_ALPHA else INACTIVE_ALPHA_IMAGE)
            val grayscaleFilter = ColorMatrixColorFilter(grayscaleMatrix)
            this.colorFilter = grayscaleFilter
        }
        else-> this.alpha = alpha
    }

}

