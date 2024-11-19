package com.jesusvilla.test.designsystem.components.textviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.annotation.Nullable
import androidx.core.content.withStyledAttributes
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jesusvilla.test.designsystem.R
import com.jesusvilla.test.designsystem.databinding.CustomTextInputLayoutBinding

@Suppress("TooManyFunctions")
class CustomTextInput : FrameLayout {

    private lateinit var binding: CustomTextInputLayoutBinding

    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        binding = CustomTextInputLayoutBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.custom_text_input_layout, this)
        )

        setDefaultConfiguration()
        context.withStyledAttributes(attrs, R.styleable.CustomTextInput) {
            binding.textInputLayout.isHintEnabled =
                getBoolean(R.styleable.CustomTextInput_hintEnabled, true)
            setHintText(getString(R.styleable.CustomTextInput_android_hint).orEmpty())
            binding.textInputLayout.isErrorEnabled =
                getBoolean(R.styleable.CustomTextInput_errorEnabled, false)
            binding.textInputLayout.isCounterEnabled = false
            binding.textInputLayout.isErrorEnabled =
                getBoolean(R.styleable.CustomTextInput_passwordToggleEnabled, false)
            val icon = getDrawable(R.styleable.CustomTextInput_passwordToggleDrawable)
            icon?.let { enablePasswordModeWithIcon(icon) }
        }

        this.init()
    }

    private fun init() {
        setEditTextMode()
    }

    private fun setDefaultConfiguration() {
        binding.textInputLayout.isHintEnabled = true
        binding.textInputLayout.hint = resources.getString(R.string.custom_text_input_hint)
        binding.textInputLayout.isErrorEnabled = false
        binding.textInputLayout.isCounterEnabled = false
        binding.textInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
    }

    fun setEditTextMode() {
        enableBoxStroke()

        binding.textInputEditText.isEnabled = true
        binding.textInputEditText.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        binding.textInputEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.textInputEditText.setImeActionLabel(
            resources.getString(R.string.custom_text_input_done),
            EditorInfo.IME_ACTION_DONE
        )
    }

    fun setReadOnlyMode() {
        disableBoxStroke()

        binding.textInputEditText.isEnabled = false
        binding.textInputEditText.inputType = InputType.TYPE_NULL
    }

    fun setHintText(hint: String) {
        binding.textInputLayout.hint = hint
    }

    fun setText(text: String) {
        binding.textInputEditText.setText(text)
    }

    fun getText(): String {
        return binding.textInputEditText.text.toString()
    }

    fun getTextBasic(): TextInputEditText? {
        return binding.textInputEditText
    }

    fun getTextLayout(): TextInputLayout? {
        return binding.textInputLayout
    }

    fun enablePasswordMode() {
        binding.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.textInputEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        binding.textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
    }

    fun enablePhoneModeInput() {
        binding.textInputEditText.inputType = InputType.TYPE_CLASS_PHONE
    }

    fun enableEmailModeInput() {
        binding.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    }

    fun enableCounter(@Nullable counterMaxLength: Int?) {
        binding.textInputLayout.isCounterEnabled = true
        counterMaxLength?.run {
            binding.textInputLayout.counterMaxLength
        }
    }

    fun enableError(@Nullable errorText: String?) {
        binding.textInputLayout.isErrorEnabled = true
        errorText?.run {
            binding.textInputLayout.error = errorText
        }
    }

    fun disableError() {
        binding.textInputLayout.isErrorEnabled = false
    }

    fun disableBoxStroke() {
        binding.textInputEditText.isEnabled = false
    }

    fun enableBoxStroke() {
        binding.textInputEditText.isEnabled = true
    }

    fun setCustomEndIconMode(drawable: Drawable, listener: () -> Unit) {
        binding.textInputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
        binding.textInputLayout.endIconDrawable = drawable
        binding.textInputLayout.setEndIconOnClickListener { listener() }
    }

    fun enablePasswordModeWithIcon(icon: Drawable) {
        this.enablePasswordMode()
        binding.textInputLayout.endIconDrawable = icon
    }

    fun enableAddressMode() {
        binding.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS
    }
}
