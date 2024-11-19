package com.jesusvilla.test.designsystem.components.textviews

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import com.google.android.material.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.theme.overlay.MaterialThemeOverlay

class CustomTextInputEditText : TextInputEditText {
    companion object {
        val DEF_STYLE_RES: Int = com.jesusvilla.test.designsystem.R.style.MaterialPaseTheme_CustomTextEditText
    }
    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, R.attr.editTextStyle)

    constructor(
        context: Context,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        MaterialThemeOverlay.wrap(
            context,
            attrs,
            defStyleAttr,
            DEF_STYLE_RES
        ),
        attrs,
        defStyleAttr
    )
}
