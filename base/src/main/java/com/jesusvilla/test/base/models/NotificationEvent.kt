package com.jesusvilla.test.base.models

import com.jesusvilla.test.base.constants.BaseConstants.EMPTY_TAG

data class NotificationEvent<T>(
    val action: T? = null,
    val error: Throwable? = null,
    val message: String? = EMPTY_TAG,
    val body: T? = null,
)
