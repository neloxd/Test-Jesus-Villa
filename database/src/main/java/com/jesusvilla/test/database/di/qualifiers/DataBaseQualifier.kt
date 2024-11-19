package com.jesusvilla.test.database.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UseRepositoryQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PostRepositoryQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserDaoQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TagDaoQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AddressDaoQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TransactionsDaoQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class InvoicesDaoQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PostDaoQualifier