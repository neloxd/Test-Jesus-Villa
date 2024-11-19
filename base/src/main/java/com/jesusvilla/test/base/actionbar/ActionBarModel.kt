package com.jesusvilla.test.base.actionbar

import android.view.View

data class ActionBarModel(
    val title: CharSequence? = null,
    val onBackArrow: View.OnClickListener? = null,
    val onBackArrowIcon: Int? = null,
    val onClose: View.OnClickListener? = null,
    val showLeadingIcon: Boolean = false,
    val colorBack: Int? = null
)
