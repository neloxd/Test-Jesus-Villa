package com.jesusvilla.test.core.network

import com.jesusvilla.test.core.network.AuthenticationInterceptor.Companion.SET_COOKIE
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CookiesInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.isSuccessful && Session.session.isEmpty() && originalResponse.headers(SET_COOKIE).isNotEmpty()) {
            val cookies = HashSet<String>()
            val builder = StringBuilder()
            originalResponse.headers(SET_COOKIE).forEach {
                cookies.add(it)
                builder.append(it)
            }
            Session.session = cookies
        }
        return originalResponse
    }
}
