package com.jesusvilla.test.base.utils

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip

fun Chip.changeSelected(context: Context, selected: Boolean) {
    val textColorResource = if (selected) {
        com.jesusvilla.test.designsystem.R.color.white
    } else {
        com.jesusvilla.test.designsystem.R.color.primary_color
    }
    val backgroundColorResource = if (selected) {
        com.jesusvilla.test.designsystem.R.color.primary_color
    } else {
        com.jesusvilla.test.designsystem.R.color.white
    }
    apply {
        setChipCornerRadiusResource(com.jesusvilla.test.designsystem.R.dimen.dim_24)
        setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, textColorResource)))
        chipBackgroundColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, backgroundColorResource))
    }
}
