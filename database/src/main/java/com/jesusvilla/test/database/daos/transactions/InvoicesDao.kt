package com.jesusvilla.test.database.daos.transactions

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jesusvilla.test.database.entities.transactions.InvoiceEntity

@Dao
interface InvoicesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvoices(invoices: List<InvoiceEntity>): List<Long>

    @Query("SELECT * FROM INVOICES WHERE  id_tag = :tagId")
    fun getInvoicesByTagId(tagId: Long): List<InvoiceEntity>
}
