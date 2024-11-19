package com.jesusvilla.test.database.repository

import com.jesusvilla.test.database.daos.transactions.TransactionsDao
import com.jesusvilla.test.database.di.qualifiers.TransactionsDaoQualifier
import com.jesusvilla.test.database.entities.transactions.TransactionEntity
import com.jesusvilla.test.database.resources.DataBaseResource
import javax.inject.Inject

class TransactionsRepository @Inject constructor(
    @TransactionsDaoQualifier private val transactionsDao: TransactionsDao
) {

    @Suppress("TooGenericExceptionCaught")
    suspend fun addTrasactionsByTagId(transactions: List<TransactionEntity>): DataBaseResource<List<Long>> {
        return try {
            val user = transactionsDao.insertTransactions(transactions)
            DataBaseResource.Success(user)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun getTransactionsByTagId(tagId: Long): DataBaseResource<List<TransactionEntity>> {
        return try {
            val query = transactionsDao.getTransactionsByTagId(tagId)
            DataBaseResource.Success(query)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun deleteAllTransactions(): DataBaseResource<Boolean> {
        return try {
            transactionsDao.deleteAllTransaction()
            DataBaseResource.Success(true)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }
}
