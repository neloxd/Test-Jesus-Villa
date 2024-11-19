package com.jesusvilla.test.home.useCases

import com.jesusvilla.test.core.network.data.Resource
import com.jesusvilla.test.home.data.repository.IHomeRepository
import com.jesusvilla.test.home.di.HomeRepositoryQualifier
import com.jesusvilla.test.home.network.models.TestPost
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    @HomeRepositoryQualifier private val homeRepository: IHomeRepository
) {
    suspend operator fun invoke(
    ): Resource<List<TestPost>> {
        return homeRepository.retrievePosts()
    }
}