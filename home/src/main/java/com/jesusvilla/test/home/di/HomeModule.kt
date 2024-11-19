package com.jesusvilla.test.home.di

import com.jesusvilla.test.core.network.connection.RetrofitApi
import com.jesusvilla.test.home.data.repository.HomeRepository
import com.jesusvilla.test.home.data.repository.IHomeRepository
import com.jesusvilla.test.home.data.service.HomeApi
import com.jesusvilla.test.home.useCases.AddTagUseCase
import com.jesusvilla.test.home.useCases.GetPostsUseCase
import com.jesusvilla.test.home.useCases.TagAvailabilityUseCase
import com.jesusvilla.test.home.useCases.VerifyTagPropertyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    @HomeApiQualifier
    fun provideHomeServices(@RetrofitApi retrofit: Retrofit): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }

    @Provides
    @Singleton
    @HomeRepositoryQualifier
    fun provideHomeRepository(@HomeApiQualifier homeApi: HomeApi): IHomeRepository {
        return HomeRepository(homeApi)
    }

    @Provides
    @Singleton
    @TagAvailabilityUseCaseQualifier
    fun providesGetTagAvailabilityUseCase(
        @HomeRepositoryQualifier homeRepository: IHomeRepository
    ): TagAvailabilityUseCase {
        return TagAvailabilityUseCase(homeRepository)
    }

    @Provides
    @Singleton
    @VerifyTagPropertyUseCaseQualifier
    fun provideVerifyTagPropertyUseCase(
        @HomeRepositoryQualifier homeRepository: IHomeRepository
    ): VerifyTagPropertyUseCase {
        return VerifyTagPropertyUseCase(homeRepository)
    }

    @Provides
    @Singleton
    @AddTagUseCaseQualifier
    fun providesAddTagUseCase(
        @HomeRepositoryQualifier homeRepository: IHomeRepository
    ): AddTagUseCase {
        return AddTagUseCase(homeRepository)
    }

    @Provides
    @Singleton
    @GetPostUseCaseQualifier
    fun providesGetPostsUseCase(
        @HomeRepositoryQualifier homeRepository: IHomeRepository
    ): GetPostsUseCase {
        return GetPostsUseCase(homeRepository)
    }
}
