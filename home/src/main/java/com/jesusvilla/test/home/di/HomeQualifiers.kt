package com.jesusvilla.test.home.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HomeRepositoryQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HomeApiQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TagAvailabilityUseCaseQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class VerifyTagPropertyUseCaseQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AddTagUseCaseQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GetPostUseCaseQualifier
