package com.jesusvilla.test.base.extension

import android.text.Editable
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.jesusvilla.test.base.R
import com.jesusvilla.test.base.constants.BaseConstants
import com.jesusvilla.test.base.constants.BaseConstants.EMPTY_TAG
import com.jesusvilla.test.base.constants.BaseConstants.SIXTEEN
import com.jesusvilla.test.base.constants.BaseConstants.TEN
import com.jesusvilla.test.base.utils.UiText
import com.jesusvilla.test.designsystem.databinding.IncludeTextInputBinding
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar

val DEFAULT_PASSWORD_REGEX = Regex("^(?=.*[A-Za-z0-9])(?=.*[^A-Za-z0-9]).{8,}$")
val DEFAULT_PHONE_REMOVAL_REGEX = Regex("\\s|-")
val DEFAULT_REGEX_EXPIRATION_TDC_REGEX =
    Regex("^((0?[1-9])|(1[0-2]))[./]((1?[6-9])|([2-9]?[0-9]))$")
val DEFAULT_CCV_REGEX = Regex("\\d{3}")
const val DEFAULT_DATE_TDC = "dd/MM/yy"

val DEFAULT_EMPTY_VALIDATOR = InputValidator(
    errorMessage = UiText.StringResource(R.string.defaults_field_required_error),
    errorCondition = { it.isNullOrEmpty() }
)

val DEFAULT_LENGTH_CARD_NUM =
    InputValidator(
        errorMessage = UiText.StringResource(R.string.defaults_field_required_error_card),
        errorCondition = { it.toString().length < BaseConstants.FOUR }
    )

val DEFAULT_POSTAL_CODE_VALIDATOR =
    InputValidator(
        errorMessage = UiText.StringResource(R.string.defaults_field_required_postal_code),
        errorCondition = { it.toString().length < BaseConstants.FIVE }
    )

val DEFAULT_EXT_NUMBER_ADDRESS_VALIDATOR =
    InputValidator(
        errorMessage = UiText.StringResource(R.string.defaults_field_required_ext_number),
        errorCondition = { !it.toString().hasExternalNumberInputFormat() }
    )

val DEFAULT_STREET_VALIDATOR =
    InputValidator(
        errorMessage = UiText.StringResource(R.string.defaults_field_required_ext_number),
        errorCondition = { !it.toString().hasStreetInputFormat() }
    )


val DEFAULT_TAG_NUMBER_VALIDATOR =
    InputValidator(
        errorMessage = UiText.StringResource(R.string.defaults_field_invalid_tag_number),
        errorCondition = { it.toString().length < BaseConstants.NINE }
    )

val DEFAULT_EMAIL_VALIDATOR =
    InputValidator(
        errorMessage = UiText.StringResource(R.string.defaults_email_bad_formatted_error),
        errorCondition = { !it.toString().hasValidEmailFormat() }
    )

val DEFAULT_EMAIL_VALIDATOR_SING_UP =
    InputValidator(
        errorMessage = UiText.StringResource(R.string.defaults_email_bad_formatted_error_sing_up),
        errorCondition = { !it.toString().hasValidEmailFormat() }
    )

val DEFAULT_PHONE_VALIDATOR = InputValidator(
    errorMessage = UiText.StringResource(R.string.defaults_invalid_phone_error),
    errorCondition = {
        it.toString()
            .replace(
                regex = DEFAULT_PHONE_REMOVAL_REGEX,
                replacement = EMPTY_TAG
            ).length != BaseConstants.MAX_LENGTH_NUM_PHONE && it.toString().isNotEmpty()
    }
)

val DEFAULT_PASSWORD_VALIDATOR =
    InputValidator(
        errorMessage = UiText.StringResource(R.string.defaults_invalid_password_entry),
        errorCondition = {
            it.toString().matches(DEFAULT_PASSWORD_REGEX).not()
        }
    )

val DEFAULT_TDC_VALIDATOR =
    InputValidator(
        errorMessage = UiText.StringResource(R.string.defaults_invalid_tdc_field_error),
        errorCondition = {
            it.toString().length < SIXTEEN
        }
    )

val DEFAULT_RFC_VALIDATOR =
    InputValidator(
        errorMessage = UiText.StringResource(R.string.defaults_invalid_rfc_field_error),
        errorCondition = {
            it.toString().length < TEN
        }
    )

val DEFAULT_TDC_EXPIRATION_DATE_VALIDATOR =
    InputValidator(
        errorMessage = UiText.StringResource(R.string.defaults_invalid_expiration_date_field_error),
        errorCondition = {
            try {
                if (it.toString().matches(DEFAULT_REGEX_EXPIRATION_TDC_REGEX)) {
                    val format = SimpleDateFormat(DEFAULT_DATE_TDC, locale)
                    format.set2DigitYearStart(Calendar.getInstance().time)
                    val cal = Calendar.getInstance()
                    cal[Calendar.DAY_OF_MONTH] = 1
                    val dateStr = "01/" + it.toString()
                    format.parse(dateStr)?.after(cal.time)
                }
                false
            } catch (e: ParseException) {
                Timber.e(e)
                false
            }
        }
    )

val DEFAULT_CCV_VALIDATOR =
    InputValidator(
        errorMessage = UiText.StringResource(R.string.defaults_invalid_ccv_field_error),
        errorCondition = {
            it.toString().matches(DEFAULT_CCV_REGEX).not()
        }
    )

data class InputValidator(
    val errorMessage: UiText,
    val errorCondition: (Editable?) -> Boolean
)

enum class ValidationTrigger { AFTER_TEXT_CHANGE, ON_LOST_FOCUS }

fun TextInputLayout.setInputValidator(
    inputValidator: InputValidator,
    vararg validationTriggers: ValidationTrigger
) {
    if (validationTriggers.contains(ValidationTrigger.AFTER_TEXT_CHANGE)) {
        editText?.addTextChangedListener {
            error = if (inputValidator.errorCondition(it)) {
                isErrorEnabled = true
                inputValidator.errorMessage.asString(context)
            } else {
                isErrorEnabled = false
                null
            }
        }
    }

    if (validationTriggers.contains(ValidationTrigger.ON_LOST_FOCUS)) {
        editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                error = if (inputValidator.errorCondition(editText?.text)) {
                    isErrorEnabled = true
                    inputValidator.errorMessage.asString(context)
                } else {
                    isErrorEnabled = false
                    null
                }
            } else {
                isErrorEnabled = false
                error = null
            }
        }
    }
}

fun TextInputLayout.setInputValidator(
    inputValidators: List<InputValidator>,
    vararg validationTriggers: ValidationTrigger
) {
    if (validationTriggers.contains(ValidationTrigger.AFTER_TEXT_CHANGE)) {
        editText?.addTextChangedListener {
            inputValidators.forEach { inputValidator ->
                if (inputValidator.errorCondition(it)) {
                    isErrorEnabled = true
                    error = inputValidator.errorMessage.asString(context)
                    return@forEach
                }
                isErrorEnabled = false
                error = null
            }
        }
    }

    if (validationTriggers.contains(ValidationTrigger.ON_LOST_FOCUS)) {
        editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                inputValidators.forEach { inputValidator ->
                    if (inputValidator.errorCondition(editText?.text)) {
                        isErrorEnabled = true
                        error = inputValidator.errorMessage.asString(context)
                        return@setOnFocusChangeListener
                    }

                    isErrorEnabled = false
                    error = null
                }
            } else {
                isErrorEnabled = false
                error = null
            }
        }
    }
}

fun TextInputLayout.isFieldValid(inputValidator: InputValidator): Boolean {
    return if (inputValidator.errorCondition(editText?.text)) {
        isErrorEnabled = true
        error = inputValidator.errorMessage.asString(context)
        false
    } else {
        error = null
        isErrorEnabled = false
        true
    }
}

fun TextInputLayout.isFieldValid(inputValidators: List<InputValidator>): Boolean {
    inputValidators.forEach { inputValidator ->
        if (inputValidator.errorCondition(editText?.text)) {
            return false
        } else {
            isErrorEnabled = false
        }
    }
    return true
}

fun TextInputLayout.getText(clean: Boolean = true): String {
    var value = this.editText?.text?.toString()
    if (clean) {
        value = value?.trim()?.trim().toString().replace(" ", "")
    }
    return value.toString()
}

fun IncludeTextInputBinding.setInputValidators(
    inputValidators: List<InputValidator>,
    vararg validationTriggers: ValidationTrigger,
    hasHelperText: Boolean = false
) {

    val borderDrawableError = ContextCompat.getDrawable(
        input.context, com.jesusvilla.test.designsystem.R.drawable.input_text_border_error
    )
    val borderDrawableNormal = ContextCompat.getDrawable(
        input.context, com.jesusvilla.test.designsystem.R.drawable.input_text_border_status
    )

    if (validationTriggers.contains(ValidationTrigger.AFTER_TEXT_CHANGE)) {
        input.editText?.addTextChangedListener {
            inputValidators.forEach { inputValidator ->
                if (inputValidator.errorCondition(it)) {
                    this.input.apply {
                        input.editText?.background = borderDrawableError
                        fieldError.msgError.text =
                            inputValidator.errorMessage.asString(input.context)
                        fieldError.msgError.isVisible = true
                        return@forEach
                    }
                }
                this.input.apply {
                    fieldError.msgError.text = ""
                    fieldError.msgError.isVisible = false
                    editText?.background = borderDrawableNormal
                }
            }
        }
    }

    if (validationTriggers.contains(ValidationTrigger.ON_LOST_FOCUS)) {
        input.editText?.setOnFocusChangeListener { _, hasFocus ->

            if (!hasFocus) {
                inputValidators.forEach { inputValidator ->
                    if (inputValidator.errorCondition(inputEditText.text)) {
                        input.editText?.background = borderDrawableError
                        this.fieldError.msgError.text =
                            inputValidator.errorMessage.asString(input.context)
                        this.fieldError.msgError.isVisible = true
                        if (hasHelperText) {
                            this.fieldInfo.msgInfo.isGone = true
                        }
                        return@setOnFocusChangeListener
                    }
                    this.fieldError.msgError.text = ""
                    this.fieldError.msgError.isVisible = false
                    input.editText?.background = borderDrawableNormal
                }
            } else {
                if (hasHelperText) {
                    this.fieldInfo.msgInfo.isGone = false
                }
                this.fieldError.msgError.text = ""
                this.fieldError.msgError.isVisible = false
                input.editText?.background = borderDrawableNormal
            }
        }
    }
}
