package com.jesusvilla.test.base.extension.textInputLayout

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.jesusvilla.test.base.extension.InputValidator
import com.jesusvilla.test.base.extension.ValidationTrigger
import com.jesusvilla.test.base.extension.hasEightCharacters
import com.jesusvilla.test.base.extension.hasNumber
import com.jesusvilla.test.base.extension.hasSpecialCharacter
import com.jesusvilla.test.base.extension.hasUpperCaseAndLowerCase
import com.jesusvilla.test.base.extension.setInputValidators
import com.jesusvilla.test.designsystem.databinding.IncludeTextInputBinding
@Suppress("all", "TooManyFunctions")
fun IncludeTextInputBinding.setAsPasswordInput(
    showPasswordIconResId: Int,
    hidePasswordIconResId: Int,
    inputTitle: String,
    hasValidators: Boolean = false,
    maxLength: Int,
    inputValidators: List<InputValidator>,
    vararg validationTriggers: ValidationTrigger
) {
    val context = this.root.context
    this.inputEditText.id = View.NO_ID
    this.titleInput.text = inputTitle
    this.input.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
    this.inputEditText.transformationMethod = PasswordTransformationMethod.getInstance()
    this.input.endIconDrawable = AppCompatResources.getDrawable(context, hidePasswordIconResId)

    if (hasValidators) {
        input.editText?.addTextChangedListener {
            inputAddTextChangedClickListener(this, it!!)
        }
    }

    this.input.setEndIconOnClickListener {
        setEndIconClick(this, context, showPasswordIconResId, hidePasswordIconResId)
    }
    this.inputEditText.filters = arrayOf(InputFilter.LengthFilter(maxLength))
    this.setInputValidators(
        inputValidators = inputValidators, validationTriggers = validationTriggers
    )
}
private fun inputAddTextChangedClickListener(
    view: IncludeTextInputBinding,
    editable: Editable
) {
    view.containerWithValidations.visibility = View.VISIBLE
    view.ivValidationOne.setImageResource(getValidationImageResource(editable.toString().hasEightCharacters()))
    view.ivValidationTwo.setImageResource(getValidationImageResource(editable.toString().hasUpperCaseAndLowerCase()))
    view.ivValidationThree.setImageResource(getValidationImageResource(editable.toString().hasNumber()))
    view.ivValidationFour.setImageResource(getValidationImageResource(editable.toString().hasSpecialCharacter()))
}
private fun getValidationImageResource(validationResult: Boolean): Int {
    return if (validationResult) {
        com.jesusvilla.test.designsystem.R.drawable.ic_check_circle
    } else {
        com.jesusvilla.test.designsystem.R.drawable.ic_uncheck_circle
    }
}
private fun setEndIconClick(
    view: IncludeTextInputBinding,
    context: Context,
    showPasswordIconResId: Int,
    hidePasswordIconResId: Int
) {
    if (view.input.editText?.transformationMethod == PasswordTransformationMethod.getInstance()) {
        view.input.editText?.transformationMethod =
            HideReturnsTransformationMethod.getInstance()
        view.input.endIconDrawable =
            AppCompatResources.getDrawable(context, showPasswordIconResId)
    } else {
        view.input.editText?.transformationMethod = PasswordTransformationMethod.getInstance()
        view.input.endIconDrawable =
            AppCompatResources.getDrawable(context, hidePasswordIconResId)
    }
}
