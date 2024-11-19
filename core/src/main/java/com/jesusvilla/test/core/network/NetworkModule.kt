package com.jesusvilla.test.core.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jesusvilla.test.core.BuildConfig
import com.jesusvilla.test.core.network.connection.AuthenticationQualifier
import com.jesusvilla.test.core.network.connection.BaseURL
import com.jesusvilla.test.core.network.connection.BodyInterceptor
import com.jesusvilla.test.core.network.connection.BuilderQualifier
import com.jesusvilla.test.core.network.connection.CookiesQualifier
import com.jesusvilla.test.core.network.connection.GsonInjection
import com.jesusvilla.test.core.network.connection.NetworkConfiguration.TIMEOUT
import com.jesusvilla.test.core.network.connection.RetrofitApi
import com.jesusvilla.test.core.network.connection.RetrofitAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @BaseURL
    fun provideBaseUrl(): String = BuildConfig.TEST_BASE_URL

    @Provides
    @Singleton
    @GsonInjection
    fun provideGson() = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    @BodyInterceptor
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    @BuilderQualifier
    fun provideBuilderClient(
        @BodyInterceptor httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder
    }

    @Provides
    @Singleton
    @CookiesQualifier
    fun provideCookiesInterceptor() = CookiesInterceptor()

    @Provides
    @Singleton
    @AuthenticationQualifier
    fun provideAuthenticationInterceptor() = AuthenticationInterceptor()

    @Provides
    @Singleton
    @RetrofitApi
    fun provideRetrofitAuth(
        @BaseURL baseUrl: String,
        @GsonInjection gson: Gson,
        @BuilderQualifier builder: OkHttpClient.Builder,
        @AuthenticationQualifier authenticationInterceptor: AuthenticationInterceptor
    ): Retrofit {
        //builder.addInterceptor(TagInterceptor())
        //builder.addInterceptor(authenticationInterceptor)

        val client: OkHttpClient = builder.build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @RetrofitAuth
    fun provideRetrofitApi(
        @BaseURL baseUrl: String,
        @GsonInjection gson: Gson,
        @BuilderQualifier builder: OkHttpClient.Builder,
        @CookiesQualifier cookiesInterceptor: CookiesInterceptor,
    ): Retrofit {
        //builder.addInterceptor(TagInterceptor())
        //builder.addInterceptor(cookiesInterceptor)
        val client: OkHttpClient = builder.build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
