package com.jesusvilla.test.base.useCases

import com.google.gson.Gson
import com.jesusvilla.test.base.constants.BaseConstants.SUCCESS
import com.jesusvilla.test.base.models.HttpErrorResponse
import com.jesusvilla.test.base.models.NotificationEvent
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException

@Suppress("SwallowedException")
open class BaseUseCaseV2<I> {

    @Suppress("all")
    fun handleSuccess(response: Response<I>): NotificationEvent<*> {
        return try {
            if (response.code() == SUCCESS) NotificationEvent(
                action = Unit, body = response.body()
            ) else {
                val httpError =
                    Gson().fromJson(response.errorBody()?.string(), HttpErrorResponse::class.java)
                val defaulterrror =
                    httpError?.message ?: response.errorBody()?.string() ?: "defaulterror"
                Timber.e("BaseUseCaseV2 -> $defaulterrror")
                NotificationEvent(
                    action = null,
                    error = Throwable(
                        message = httpError?.message ?: response.errorBody()?.string() ?: ""
                    ),
                    message = httpError?.message ?: response.errorBody()?.string() ?: ""
                )
            }
        } catch (e: Exception) {
            Timber.e("BaseUseCaseV2 Exception-> $e")
            val message = when (e) {
                is UnknownHostException -> "Unknown host!"
                is ConnectException -> "No internet!"
                else -> "Unknown exception!"
            }
            NotificationEvent(
                action = null,
                error = Throwable(
                    message = message
                ),
                message = message
            )
        }
    }
}
