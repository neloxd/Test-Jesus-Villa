package com.jesusvilla.test.home.data.service

import com.jesusvilla.test.base.models.tags.TagAvailabilityResponse
import com.jesusvilla.test.home.network.models.TestPost
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApi {
    @GET("public/tags/{prefix}/{tagNumber}")
    suspend fun isTagAvailable(
        @Path("prefix") tagPrefix: String,
        @Path("tagNumber") tagNumber: String
    ): Response<TagAvailabilityResponse>

    @GET("public/tags/{prefix}/{tagNumber}/{firstQuartet}/{lastQuartet}")
    suspend fun verifyTagProperty(
        @Path("prefix") tagPrefix: String,
        @Path("tagNumber") tagNumber: String,
        @Path("firstQuartet") firstQuartet: String,
        @Path("lastQuartet") lastQuartet: String
    ): Response<Unit>

    @GET("posts")
    suspend fun getPosts(): Response<List<TestPost>>
}
