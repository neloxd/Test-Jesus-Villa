package com.jesusvilla.test.base.extension.textInputLayout

import android.text.InputFilter
import android.text.InputType
import android.view.View
import androidx.core.content.ContextCompat
import com.jesusvilla.test.base.extension.InputValidator
import com.jesusvilla.test.base.extension.ValidationTrigger
import com.jesusvilla.test.base.extension.setInputValidators
import com.jesusvilla.test.designsystem.databinding.IncludeTextInputBinding
@Suppress("LongParameterList")
fun IncludeTextInputBinding.setAsNumberInput(
    inputTitle: String,
    maxLength: Int,
    inputValidators: List<InputValidator>? = null,
    vararg validationTriggers: ValidationTrigger,
    isEnable: Boolean? = true,
    placeholder: String? = null,
    titleIcon: Int? = null,
    titleIconFunction: (() -> Unit)? = null,
) {
    this.inputEditText.id = View.NO_ID
    this.inputEditText.inputType = InputType.TYPE_CLASS_NUMBER
    this.titleInput.text = inputTitle
    this.inputEditText.filters = arrayOf(InputFilter.LengthFilter(maxLength))
    this.inputEditText.hint = placeholder

    if (titleIcon != null) {
        this.iconTitleInput.visibility = View.VISIBLE
        this.iconTitleInput.setImageResource(titleIcon)
        this.iconTitleInput.setOnClickListener {
            titleIconFunction?.invoke()
        }
    }

    if (isEnable == false) {
        this.input.isEnabled = isEnable
        this.input.error = null
        this.input.editText?.background = ContextCompat.getDrawable(
            input.context, com.jesusvilla.test.designsystem.R.drawable.input_text_disable
        )
    } else {
        inputValidators?.let {
            this.setInputValidators(
                inputValidators = it, validationTriggers = validationTriggers
            )
        }
    }
}
