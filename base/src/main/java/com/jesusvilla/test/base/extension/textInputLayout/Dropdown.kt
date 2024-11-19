package com.jesusvilla.test.base.extension.textInputLayout

import android.view.View
import androidx.core.content.ContextCompat
import com.jesusvilla.test.designsystem.databinding.IncludeDropdownBinding
import com.jesusvilla.test.designsystem.databinding.IncludeTextInputBinding

fun IncludeDropdownBinding.setAsDropdown(
    inputTitle: String,
    placeholder: String
) {
    this.editDropdown.id = View.NO_ID
    this.titleDropdown.text = inputTitle
    this.inputDropdown.editText?.hint = placeholder
}
fun IncludeTextInputBinding.setDisableState() {
    this.input.isEnabled = false
    this.input.error = null
    this.input.editText?.background = ContextCompat.getDrawable(
        input.context, com.jesusvilla.test.designsystem.R.drawable.input_text_disable_with_border
    )
}
fun IncludeDropdownBinding.setDisableState() {
    this.inputDropdown.isEnabled = false
    this.inputDropdown.error = null
    this.inputDropdown.editText?.background = ContextCompat.getDrawable(
        inputDropdown.context,
        com.jesusvilla.test.designsystem.R.drawable.input_text_disable_with_border
    )
}
fun IncludeDropdownBinding.setEnableStyle(
    placeholder: String
) {
    this.inputDropdown.editText?.hint = placeholder
    this.inputDropdown.isEnabled = true
    this.inputDropdown.editText?.background = ContextCompat.getDrawable(
        inputDropdown.context, com.jesusvilla.test.designsystem.R.drawable.input_text_border_status
    )
}
