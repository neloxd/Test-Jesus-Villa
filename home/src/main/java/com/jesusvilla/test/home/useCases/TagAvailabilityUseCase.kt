package com.jesusvilla.test.home.useCases

import com.jesusvilla.test.base.models.tags.TagAvailabilityResponse
import com.jesusvilla.test.core.network.data.Resource
import com.jesusvilla.test.home.data.repository.IHomeRepository
import com.jesusvilla.test.home.di.HomeRepositoryQualifier
import javax.inject.Inject

class TagAvailabilityUseCase @Inject constructor(
    @HomeRepositoryQualifier private val homeRepository: IHomeRepository
) {

    suspend operator fun invoke(
        tagPrefix: String,
        tagNumber: String
    ): Resource<TagAvailabilityResponse> {
        return homeRepository.isTagAvailable(tagPrefix, tagNumber)
    }
}
