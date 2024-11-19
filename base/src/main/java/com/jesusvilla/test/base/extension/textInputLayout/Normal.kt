package com.jesusvilla.test.base.extension.textInputLayout

import android.text.InputFilter
import android.view.View.NO_ID
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import com.jesusvilla.test.base.extension.InputValidator
import com.jesusvilla.test.base.extension.ValidationTrigger
import com.jesusvilla.test.base.extension.setInputValidators
import com.jesusvilla.test.designsystem.databinding.IncludeTextInputBinding

@Suppress("LongParameterList")
fun IncludeTextInputBinding.setAsNormalInput(
    inputTitle: String,
    maxLength: Int = 255,
    inputValidators: List<InputValidator>? = null,
    vararg validationTriggers: ValidationTrigger,
    isEnable: Boolean? = true,
    placeholder: String? = null,
    helperText: String? = null,
) {
    this.inputEditText.id = NO_ID
    this.titleInput.text = inputTitle
    this.inputEditText.filters = arrayOf(InputFilter.LengthFilter(maxLength))
    this.fieldInfo.msgInfo.text = helperText
    this.fieldInfo.msgInfo.isGone = helperText == null

    if (placeholder != null) {
        this.input.editText?.hint = placeholder
    }

    if (isEnable == false) {
        this.input.isEnabled = isEnable
        this.input.error = null
        this.input.editText?.background = ContextCompat.getDrawable(
            input.context, com.jesusvilla.test.designsystem.R.drawable.input_text_disable
        )
    } else {
        if (inputValidators != null) {
            this.input.error = null
            this.setInputValidators(
                inputValidators = inputValidators, validationTriggers = validationTriggers
            )
        }
    }
}

fun IncludeTextInputBinding.setIdleState() {
    this.input.editText?.background = ContextCompat.getDrawable(
        input.context, com.jesusvilla.test.designsystem.R.drawable.input_text_disable
    )
}
