package com.jesusvilla.test.base.useCases

import com.google.gson.Gson
import com.jesusvilla.test.base.constants.BaseConstants.SUCCESS
import com.jesusvilla.test.base.models.HttpErrorResponse
import com.jesusvilla.test.base.models.NotificationEvent
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response

abstract class BaseUseCase<I, T> {
    protected val disposables = CompositeDisposable()
    private var eventSubject = BehaviorSubject.create<NotificationEvent<*>>()

    private fun notifyError(throwable: Throwable) {
        eventSubject.onNext(NotificationEvent<Any>(error = throwable))
        if (eventSubject.hasValue()) eventSubject = BehaviorSubject.create()
    }

    abstract fun action(params: I): Single<Response<T>>
    operator fun invoke(
        params: I
    ): Single<NotificationEvent<*>> {
        disposables.addAll(
            action(params).subscribe({ handleSuccess(it) }, ::notifyError)
        )
        return eventSubject.firstOrError()
    }

    private fun <T> handleSuccess(response: Response<T>) {
        when (response.code()) {
            SUCCESS -> {
                eventSubject.onNext(NotificationEvent(action = Unit, body = response.body()))
            }

            else -> {
                val httpError =
                    Gson().fromJson(response.errorBody()?.string(), HttpErrorResponse::class.java)
                notifyError(Throwable(message = httpError?.message ?: ""))
            }
        }
    }
}
