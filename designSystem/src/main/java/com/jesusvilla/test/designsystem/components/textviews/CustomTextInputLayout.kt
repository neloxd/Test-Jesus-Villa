package com.jesusvilla.test.designsystem.components.textviews

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.annotation.Nullable
import com.google.android.material.R
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.theme.overlay.MaterialThemeOverlay

class CustomTextInputLayout : TextInputLayout {

    companion object {
        val DEF_STYLE_RES: Int =
            com.jesusvilla.test.designsystem.R.style.MaterialPaseTheme_TextInputEditText_TextInputLayout
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.textInputStyle
    )

    constructor(
        context: Context,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES),
        attrs,
        defStyleAttr
    )
}
fun TextInputLayout.setCustomFocus() {
    if (TextUtils.isEmpty(this.editText!!.text)) {
        this.requestFocus()
    }
}
