package com.jesusvilla.test.base.extension

import android.graphics.Paint
import android.widget.TextView

object TextViewExtensions {
    fun TextView.showAsLink(asButton: Boolean) {
        val textAppearance = if (asButton) {
            isClickable = true
            com.jesusvilla.test.designsystem.R.style.TextAppearance_App_Bold_Link
        } else {
            paintFlags = paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
            isClickable = false
            com.jesusvilla.test.designsystem.R.style.TextAppearance_App_TextView
        }
        setTextAppearance(textAppearance)
    }
}
