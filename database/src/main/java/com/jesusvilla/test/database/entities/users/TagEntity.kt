package com.jesusvilla.test.database.entities.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo("id_user_tag")
    var idUserEntity: Long,
    @ColumnInfo("id_tag")
    val idTag: Long,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("prefix")
    val prefix: String,
    @ColumnInfo("number")
    val number: Int,
    @ColumnInfo("balance")
    val balance: Float,
    @ColumnInfo("payment_type")
    val paymentType: String,
    @ColumnInfo("payment_method")
    val paymentMethod: String,
    @ColumnInfo("payment_style")
    val paymentStyle: String,
    @ColumnInfo("brand")
    val brand: String,
    @ColumnInfo("is_new_tag")
    val isNewTag: Boolean,
    @ColumnInfo("is_active")
    val isActive: Boolean,
    @ColumnInfo("is_uber")
    val isUber: Boolean,
    @ColumnInfo("has_debts")
    val hasDebts: Boolean,
    @ColumnInfo("verification_digit")
    val verificationDigit: Int,
    @ColumnInfo("is_promotion_tdu")
    val isPromotionTDU: Boolean,
    @ColumnInfo("is_paypal_enabled")
    val isPaypalEnabled: Boolean,
    @ColumnInfo("tdc_brand")
    val tdcBrand: String,
    @ColumnInfo("tdc_number_ended")
    val tdcNumberEnded: String,
    @ColumnInfo(name = "car_selected_id")
    val carSelectedId: Int
)
