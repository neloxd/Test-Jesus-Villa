package com.jesusvilla.test.core.network

import com.jesusvilla.test.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TagInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
            .header("User-Agent", "TuTag-Android")
            .header("Content-Type", "application/json; charset=utf-8")
            .header("Accept", "application/json")
            .header("X-Api-Version", BuildConfig.TUTAG_API_VERSION)
            .method(original.method, original.body)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}
