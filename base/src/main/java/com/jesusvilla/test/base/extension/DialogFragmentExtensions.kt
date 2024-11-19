package com.jesusvilla.test.base.extension

import android.graphics.Color
import android.os.Build
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import com.jesusvilla.test.base.constants.BaseConstants.MAX_LENGTH
import com.jesusvilla.test.base.constants.BaseConstants.ONE
import com.jesusvilla.test.base.constants.BaseConstants.POINT_FIVE
import com.jesusvilla.test.base.constants.BaseConstants.POINT_FIVE_HUNDRED_AND_EIGHTY_SEVEN
import com.jesusvilla.test.base.constants.BaseConstants.POINT_ONE_HUNDRED_FOURTEEN
import com.jesusvilla.test.base.constants.BaseConstants.POINT_TWO_HUNDRED_NINETY_NINE
import com.jesusvilla.test.base.constants.BaseConstants.ZERO

fun DialogFragment.changeStatusAndIconBar(colorRes: Int) {
    requireActivity().window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val color = ContextCompat.getColor(context, colorRes)
        statusBarColor = color
        val win = this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = WindowCompat.getInsetsController(win, win.decorView)
            controller.isAppearanceLightStatusBars = color == Color.WHITE
        } else if (!isColorDark(color)) {
            decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility = ZERO
        }
    }
}

fun isColorDark(color: Int): Boolean {
    val darkness: Double =
        ONE - (
            POINT_TWO_HUNDRED_NINETY_NINE * Color.red(color) + POINT_FIVE_HUNDRED_AND_EIGHTY_SEVEN * Color.green(
                color
            ) + POINT_ONE_HUNDRED_FOURTEEN * Color.blue(color)
            ) / MAX_LENGTH
    return darkness >= POINT_FIVE
}
