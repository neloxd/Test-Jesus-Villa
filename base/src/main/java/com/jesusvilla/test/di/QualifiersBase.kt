package com.jesusvilla.test.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PreferencesQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LoginManagerQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserManagerQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserModelQualifier
