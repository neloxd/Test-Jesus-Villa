package com.jesusvilla.test.core.network.data

import android.os.Looper
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.jesusvilla.test.core.BuildConfig
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

// Network Response Constant
const val ERROR_SERVICE_RESPONSE = "Error in service"

data class BodyError(
    val code: String,
    val message: String
)

private fun <RequestType> getErrorMessage(apiResponse: Response<RequestType>): BodyError {
    return try {
        Gson().fromJson(apiResponse.errorBody()?.string(), BodyError::class.java)
    } catch (e: JsonParseException) {
        Timber.e("getErrorMessage", e.message.orEmpty())
        getGeneralErrorMessage(exception = e)
    }
}

private fun getGeneralErrorMessage(
    errorBody: ResponseBody? = null,
    exception: Exception? = null,
    httpException: HttpException? = null,
    message: String? = null,
    code: String? = null
): BodyError {
    var codeComplete = "001"
    if (BuildConfig.DEBUG) {
        errorBody?.let {
            Timber.d("ERROR BODY:", it.string())
        }
        exception?.let {
            Timber.d("ERROR exception:", it.message.orEmpty())
        }
        message?.let {
            Timber.d("ERROR message:", it)
        }
    }
    code?.let {
        if (BuildConfig.DEBUG)
            Timber.d("ERROR CODE:", it)
        codeComplete = it
    }
    exception?.let {
        Timber.d("ERROR CODE:", it.javaClass.name)
        codeComplete = it.javaClass.name
    }
    httpException?.let {
        if (BuildConfig.DEBUG)
            Timber.d("DEBUG httpException:", it.message!!)
        codeComplete = it.code().toString()
    }
    return BodyError(codeComplete, "Al parecer se produjo un error inesperado. Por favor vuelve a intentarlo.")
}

/**
 * The processedNetworkResource is in charge of executing the call to an API,
 * and then processing the response, leaving the process of both response
 * errors and exceptions unified.
 *
 * @param ResponseType: It is the response model of the API
 * @param ResultType: It is the response model after processing the API response
 * @param createCall: Function that makes the API call
 * @param processResponse: Function that processes the success response
 *
 * @return Response<RequestType>
 */
@Suppress("TooGenericExceptionCaught")
suspend fun <ResponseType, ResultType> processedNetworkResource(
    createCall: suspend () -> Response<ResponseType>,
    processResponse: (response: ResponseType) -> ResultType
): Resource<ResultType> {
    try {
        val apiResponse = createCall()
        return when {
            apiResponse.isSuccessful -> {
                Resource.success(apiResponse.body()?.let { processResponse(it) })
            }
            apiResponse.errorBody() != null -> {
                val error = getErrorMessage(apiResponse)
                Resource.error(error.message, error.code, apiResponse.code())
            }
            else -> {
                val error = getGeneralErrorMessage(
                    message = apiResponse.message(),
                    code = apiResponse.code().toString()
                )
                Resource.error(error.message, error.code, apiResponse.code())
            }
        }
    } catch (e: Exception) {
        Timber.e("processedNetRes() Exception", e.message.orEmpty())
        val error = getGeneralErrorMessage(exception = e)
        return Resource.error(error.message, error.code, isNetworkRelated = e.isNetworkRelated())
    }
}

/**
 * Subscribe when no wait for entity
 */
fun subscribe(
    completable: Completable,
    scheduler: Scheduler? = null
): Completable {
    return scheduler?.let { completable.subscribeOn(scheduler) }
        ?: run {
            if (Looper.myLooper() == Looper.getMainLooper()) completable.subscribeOn(Schedulers.io())
            else completable
        }
}

/**
 * Subscribe when an entity is waiting for
 */
fun <T> subscribe(single: Single<T>, scheduler: Scheduler? = null): Single<T> {
    return scheduler?.let { single.subscribeOn(scheduler) }
        ?: run {
            if (Looper.myLooper() == Looper.getMainLooper()) single.subscribeOn(Schedulers.io())
            else single
        }
}
