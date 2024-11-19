package com.jesusvilla.test.core.network

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor() : Interceptor, Authenticator {

    private val cookieData: HashSet<String>
        get() = runBlocking {
            Session.session
        }

    override fun intercept(chain: Interceptor.Chain): Response {
        Timber.i("intercept Authentication -> cookieData:$cookieData")
        return if (cookieData.isNotEmpty()) {
            val newRequest = chain.request()
                .newBuilder()
            cookieData.forEach {
                newRequest.addHeader(COOKIE_HEADER, it)
            }
            val newBuilder = newRequest.build()
            val newResponse = chain.proceed(newBuilder)
            if (isUnauthorizedCode(newResponse.code)) {
                Session.invalidate()
            }
            newResponse
        } else {
            val newResponse = chain.proceed(chain.request())
            if (isUnauthorizedCode(newResponse.code)) {
                Session.invalidate()
            }
            newResponse
        }
    }

    override fun authenticate(route: Route?, response: Response): Request {
        if (isUnauthorizedCode(response.code)) {
            Session.invalidate()
            return response.request
        }

        return response.request.newBuilder()
            .removeHeader(COOKIE_HEADER)
            // .header(COOKIE_HEADER, cookieData)
            .build()
    }

    private fun isUnauthorizedCode(code: Int): Boolean {
        return when (code) {
            // ServerStatus.UNAUTHORIZED.value.toInt(),
            ServerStatus.UNAUTHORIZED.value.toInt() -> true
            else -> false
        }
    }

    companion object {
        const val COOKIE_HEADER = "Cookie"
        const val SET_COOKIE = "Set-Cookie"
        const val COOKIE_KEY = "COOKIE_KEY"

        const val REMEMBER_ME = "remember-me"
        const val JSESSIONID = "JSESSIONID"
        const val did = "did"
    }
}
