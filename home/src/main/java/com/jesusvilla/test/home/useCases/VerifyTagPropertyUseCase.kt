package com.jesusvilla.test.home.useCases

import com.jesusvilla.test.base.models.tags.TagOwnershipModel
import com.jesusvilla.test.core.network.data.Resource
import com.jesusvilla.test.home.data.repository.IHomeRepository
import com.jesusvilla.test.home.di.HomeRepositoryQualifier
import javax.inject.Inject

class VerifyTagPropertyUseCase @Inject constructor(
    @HomeRepositoryQualifier private val homeRepository: IHomeRepository
) {
    suspend operator fun invoke(tagOwnershipModel: TagOwnershipModel): Resource<Unit> {
        return homeRepository.verifyTagProperty(
            tagPrefix = tagOwnershipModel.tagPrefix,
            tagNumber = tagOwnershipModel.tagNumber,
            firstQuartet = tagOwnershipModel.firstQuartetNumbers,
            lastQuartet = tagOwnershipModel.lastQuartetNumbers
        )
    }
}
