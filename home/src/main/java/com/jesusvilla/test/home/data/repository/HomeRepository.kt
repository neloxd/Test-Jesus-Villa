package com.jesusvilla.test.home.data.repository

import com.jesusvilla.test.base.models.tags.TagAvailabilityResponse
import com.jesusvilla.test.core.network.data.Resource
import com.jesusvilla.test.core.network.data.processedNetworkResource
import com.jesusvilla.test.home.data.service.HomeApi
import com.jesusvilla.test.home.di.HomeApiQualifier
import com.jesusvilla.test.home.network.models.TestPost
import javax.inject.Inject

class HomeRepository @Inject constructor(
    @HomeApiQualifier private val homeApi: HomeApi
) : IHomeRepository {

    override suspend fun isTagAvailable(
        tagPrefix: String,
        tagNumber: String
    ): Resource<TagAvailabilityResponse> {
        return processedNetworkResource(
            {
                homeApi.isTagAvailable(
                    tagPrefix,
                    tagNumber
                )
            },
            { response ->
                response
            }
        )
    }

    override suspend fun verifyTagProperty(
        tagPrefix: String,
        tagNumber: String,
        firstQuartet: String,
        lastQuartet: String
    ): Resource<Unit> {
        return processedNetworkResource(
            {
                homeApi.verifyTagProperty(
                    tagPrefix,
                    tagNumber,
                    firstQuartet,
                    lastQuartet
                )
            },
            { response -> response }
        )
    }

    override suspend fun retrievePosts(): Resource<List<TestPost>> {
        return processedNetworkResource(
            {
                homeApi.getPosts()
            },
            { response -> response }
        )
    }
}
