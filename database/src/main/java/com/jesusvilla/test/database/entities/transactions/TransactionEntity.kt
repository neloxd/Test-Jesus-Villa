package com.jesusvilla.test.database.entities.transactions

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "transactions", indices = [Index(value = ["date", "id_tag", "type", "track"], unique = true)])
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val idEntity: Long = 0,
    @ColumnInfo(name = "id_tag")
    val idTag: Long,
    @ColumnInfo(name = "track")
    val track: String,
    @ColumnInfo(name = "shed")
    val shed: String,
    @ColumnInfo(name = "kind")
    val kind: String,
    @ColumnInfo(name = "id_concept")
    val idConcept: Int,
    @ColumnInfo(name = "consecar")
    val consecar: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "detail1")
    val detail1: String,
    @ColumnInfo(name = "detail2")
    val detail2: String,
    @ColumnInfo(name = "third_parking")
    val thirdParking: Boolean,
    @ColumnInfo(name = "invoicing_status")
    val invoicingStatus: String,
    @ColumnInfo(name = "invoiced")
    val invoiced: Boolean,
    @ColumnInfo(name = "invoice_pass")
    val invoicePass: Boolean,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "application_date")
    val applicationDate: String,
    @ColumnInfo(name = "transaction_date_hour")
    val transactionDateHour: String,
    @ColumnInfo(name = "transaction_date")
    val transactionDate: String,
    @ColumnInfo(name = "pay_date")
    val payDate: Long,
    @ColumnInfo(name = "folio_pre_invoice")
    val folioPreInvoice: Int,
    @ColumnInfo(name = "folio_clarification")
    val folioClarification: String,
    @ColumnInfo(name = "transaction_hour")
    val transactionHour: String,
    @ColumnInfo(name = "track_id")
    val trackId: String,
    @ColumnInfo(name = "shed_id")
    val shedId: String,
    @ColumnInfo(name = "id_corredor")
    val passageId: Int,
    @ColumnInfo(name = "iva")
    val iva: Int,
    @ColumnInfo(name = "amount")
    val amount: String,
    @ColumnInfo(name = "track_name")
    val trackName: String,
    @ColumnInfo(name = "shed_name")
    val shedName: String,
    @ColumnInfo(name = "passage_name")
    val passageName: String,
    @ColumnInfo(name = "nom_track")
    val nomTrack: String,
    @ColumnInfo(name = "paid")
    val paid: Boolean,
    @ColumnInfo(name = "sequential")
    val sequential: String,
    @ColumnInfo(name = "type")
    val type: Int,
    @ColumnInfo(name = "stretch")
    val stretch: String,

)
