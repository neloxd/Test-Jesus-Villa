package com.jesusvilla.test.home.useCases

import com.jesusvilla.test.home.data.repository.IHomeRepository
import com.jesusvilla.test.home.di.HomeRepositoryQualifier
import javax.inject.Inject

class AddTagUseCase @Inject constructor(
    @HomeRepositoryQualifier private val homeRepository: IHomeRepository
) {

}
