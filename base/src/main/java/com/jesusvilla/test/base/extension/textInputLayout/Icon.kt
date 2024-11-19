package com.jesusvilla.test.base.extension.textInputLayout

import android.text.InputFilter
import android.text.InputType
import android.view.View
import androidx.core.view.isGone
import com.jesusvilla.test.base.extension.InputValidator
import com.jesusvilla.test.base.extension.ValidationTrigger
import com.jesusvilla.test.base.extension.setInputValidators
import com.jesusvilla.test.designsystem.databinding.IncludeTextInputBinding

@Suppress("LongParameterList")
fun IncludeTextInputBinding.setAsIconInput(
    inputTitle: String,
    maxLength: Int,
    isNumber: Boolean = true,
    helperText: String? = null,
    titleIcon: Int? = null,
    startIcon: Int,
    titleIconFunction: (() -> Unit)? = null,
    inputValidators: List<InputValidator>,
    vararg validationTriggers: ValidationTrigger
) {
    this.inputEditText.id = View.NO_ID
    this.titleInput.text = inputTitle
    this.inputEditText.filters = arrayOf(InputFilter.LengthFilter(maxLength))
    this.fieldInfo.msgInfo.text = helperText
    this.fieldInfo.msgInfo.isGone = helperText == null
    this.input.setStartIconDrawable(startIcon)
    this.divider.visibility = View.VISIBLE
    this.iconTitleInput.visibility = View.VISIBLE
    if (titleIcon != null) {
        this.iconTitleInput.setImageResource(titleIcon)
    }
    this.iconTitleInput.setOnClickListener {
        titleIconFunction?.invoke()
    }

    if (helperText != null) {
        this.setInputValidators(
            inputValidators = inputValidators,
            validationTriggers = validationTriggers,
            helperText.isNotEmpty()
        )
    } else {
        this.setInputValidators(
            inputValidators = inputValidators,
            validationTriggers = validationTriggers
        )
    }

    if (isNumber) {
        this.inputEditText.inputType = InputType.TYPE_CLASS_NUMBER
    }

}
