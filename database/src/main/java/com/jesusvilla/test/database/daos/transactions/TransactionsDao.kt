package com.jesusvilla.test.database.daos.transactions

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jesusvilla.test.database.entities.transactions.TransactionEntity

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionEntity>): List<Long>

    @Query("SELECT * FROM TRANSACTIONS WHERE  id_tag = :tagId")
    suspend fun getTransactionsByTagId(tagId: Long): List<TransactionEntity>

    @Query("DELETE FROM TRANSACTIONS")
    suspend fun deleteAllTransaction()

    @Query("DELETE FROM TRANSACTIONS")
    suspend fun deleteAllTransactionData()
}
