package com.jesusvilla.test.home.data.repository

import com.jesusvilla.test.base.models.tags.TagAvailabilityResponse
import com.jesusvilla.test.core.network.data.Resource
import com.jesusvilla.test.home.network.models.TestPost

interface IHomeRepository {
    @Throws(Exception::class)
    suspend fun isTagAvailable(
        tagPrefix: String,
        tagNumber: String
    ): Resource<TagAvailabilityResponse>

    @Throws(Exception::class)
    suspend fun verifyTagProperty(
        tagPrefix: String,
        tagNumber: String,
        firstQuartet: String,
        lastQuartet: String
    ): Resource<Unit>

    @Throws(Exception::class)
    suspend fun retrievePosts(
    ): Resource<List<TestPost>>
}
