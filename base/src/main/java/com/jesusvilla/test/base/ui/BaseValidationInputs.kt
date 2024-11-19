package com.jesusvilla.test.base.ui

interface BaseValidationInputs {
    fun setupValidators()
    fun performValidate()

    fun areFieldsValid(): Boolean
}
