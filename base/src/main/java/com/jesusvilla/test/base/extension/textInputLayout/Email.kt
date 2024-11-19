package com.jesusvilla.test.base.extension.textInputLayout

import android.text.InputFilter
import android.text.InputType
import android.view.View
import com.jesusvilla.test.base.extension.CustomEmailInputFilter
import com.jesusvilla.test.base.extension.InputValidator
import com.jesusvilla.test.base.extension.ValidationTrigger
import com.jesusvilla.test.base.extension.setInputValidators
import com.jesusvilla.test.designsystem.databinding.IncludeTextInputBinding

fun IncludeTextInputBinding.setAsEmailInput(
    inputTitle: String,
    maxLength: Int,
    inputValidators: List<InputValidator>,
    vararg validationTriggers: ValidationTrigger
) {
    this.inputEditText.id = View.NO_ID
    this.inputEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    this.titleInput.text = inputTitle
    this.inputEditText.filters =
        arrayOf(InputFilter.LengthFilter(maxLength), CustomEmailInputFilter())

    this.setInputValidators(
        inputValidators, validationTriggers = validationTriggers
    )
}
