package com.jesusvilla.test.base.useCases

import com.jesusvilla.test.base.constants.BaseConstants
import com.jesusvilla.test.base.models.NotificationEvent
import retrofit2.Response

open class BaseUseCaseList<I> {
    fun handleSuccess(response: Response<List<I>>): NotificationEvent<*> {
        return if (response.code() == BaseConstants.SUCCESS) NotificationEvent(
            action = Unit,
            body = response.body()
        ) else NotificationEvent(Throwable(message = response.message()))
    }
}
