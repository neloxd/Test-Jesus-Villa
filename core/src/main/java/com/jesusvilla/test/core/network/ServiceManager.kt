package com.jesusvilla.test.core.network

import com.google.gson.Gson
import com.jesusvilla.test.core.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

const val TIME_OUT: Long = 120

@Singleton
class ServiceManager @Inject constructor(private val gson: Gson) {
    private var retrofit: Retrofit = getRetrofitInstance()
    private val retrofitLogin = provideService(getClientCookie())

    private val servicesHashMap = HashMap<Any, Any?>()

    @Suppress("UNCHECKED_CAST")
    fun <T> getMapOfServices(classContent: Class<T>): T {
        return servicesHashMap[classContent]?.let { it as T }
            ?: run {
                val services: T = retrofit.create(classContent)
                servicesHashMap[classContent::class.java] = services
                services
            }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getMapWithOutCookieOfServices(classContent: Class<T>): T {
        return servicesHashMap[classContent]?.let { it as T }
            ?: run {
                val services: T = retrofitLogin.create(classContent)
                servicesHashMap[classContent::class.java] = services
                services
            }
    }

    private fun getClient(): OkHttpClient {

        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logForDegug = HttpLoggingInterceptor()
            logForDegug.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(logForDegug)
        }

        clientBuilder.addInterceptor(TagInterceptor())

        clientBuilder.addInterceptor(AuthenticationInterceptor())
        clientBuilder.authenticator(AuthenticationInterceptor())

        return clientBuilder.build()
    }

    private fun getRetrofitClient(httpClient: OkHttpClient): Retrofit {
        val clientRetrofit = Retrofit.Builder()
        clientRetrofit.client(httpClient)
        val baseUrl = BuildConfig.BASE_URL.plus("/sp-web/api/")
        clientRetrofit.baseUrl(baseUrl)
        clientRetrofit.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        clientRetrofit.addConverterFactory(GsonConverterFactory.create(gson))
        return clientRetrofit.build()
    }

    private fun getRetrofitInstance(): Retrofit {
        return getRetrofitClient(getClient())
    }

    //region Login
    private fun getClientCookie(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logForDegug = HttpLoggingInterceptor()
            logForDegug.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(logForDegug)
        }

        clientBuilder.addInterceptor(TagInterceptor())

        clientBuilder.addInterceptor(CookiesInterceptor())

        return clientBuilder.build()
    }
    //endregion

    private fun provideService(
        okhttpClient: OkHttpClient
    ): Retrofit {
        return getRetrofitClient(okhttpClient)
    }
}
