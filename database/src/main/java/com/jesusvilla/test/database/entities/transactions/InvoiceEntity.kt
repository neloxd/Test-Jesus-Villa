package com.jesusvilla.test.database.entities.transactions

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "invoices", indices = [Index(value = ["date", "id_tag", "rfc", "period"], unique = true)])
data class InvoiceEntity(
    @PrimaryKey(autoGenerate = true)
    val idEntity: Long = 0,
    @ColumnInfo(name = "id_tag")
    val idTag: Long,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "rfc")
    val rfc: String,
    @ColumnInfo("business_name")
    val businessName: String,
    @ColumnInfo("total")
    val total: String,
    @ColumnInfo("iva")
    val iva: Int,
    @ColumnInfo("date")
    val date: Long,
    @ColumnInfo("period")
    val period: String,
    @ColumnInfo("pdf")
    val pdf: String,
    @ColumnInfo("xml")
    val xml: String,
    @ColumnInfo("certificate")
    val certificate: String,
    @ColumnInfo("serie")
    val serie: String,
    @ColumnInfo("folio")
    val folio: String,
    @ColumnInfo("id")
    val id: Long
)
