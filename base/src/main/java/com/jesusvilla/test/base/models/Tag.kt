@file:Suppress("EnumNaming")

package com.jesusvilla.test.base.models

import android.os.Parcelable
import com.jesusvilla.test.base.constants.BaseConstants.EMPTY_TAG
import com.jesusvilla.test.base.extension.normalizeTagNumber
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag(
    var id: Long = 0,
    val index: Int = 0,
    val other: Boolean = false,
    val prefix: TagPrefix,
    val promotionTDU: Boolean = false,
    val isPaypalEnabled: Boolean = false,
    val number: Int,
    val name: String,
    val paymentType: String,
    val paymentStyle: String,
    val brand: String,
    val isNewTag: Boolean = false,
    val balance: Float,
    val isActive: Boolean = false,
    val isSelected: Boolean = false,
    val hasDebts: Boolean = false,
    val isUber: Boolean = false,
    val verificationDigit: Int,
    val typeOfPayment: TypeOfPayment? = null,
    var tokendynamic: String? = null,
    var insertedNumber: String = EMPTY_TAG,
    var insertedAlias: String = EMPTY_TAG
) : Parcelable {

    fun isPrepaid(): Boolean {
        return typeOfPayment != null && (
            typeOfPayment == TypeOfPayment.DOMICILED_PREPAID ||
                typeOfPayment == TypeOfPayment.CASH_PREPAID ||
                typeOfPayment == TypeOfPayment.CORPORATE_PREPAID
            )
    }

    fun getTagInfoFormatted(): String {
        return prefix.name + " " + number.normalizeTagNumber() + "-" + verificationDigit
    }

    fun getTag() = prefix.name + " " + number
}

enum class TagPrefix { IMDM, CPFI, `005`, `006` }

enum class TypeOfPayment(val type: String, val alias: String) {
    CORPORATE_PREPAID("CORPORATE_PREPAID", "Prepago"),
    CASH_PREPAID("PREPAGO_EFECTIVO", "Prepago"),
    DOMICILED_PREPAID("PREPAGO_DOMICILIADO", "Prepago"),
    CORPORATE("CORPORATIVO", "Corporativo"),
    POSTPAID("POSPAGO", "Pospago"),
    NO_PAYMENT("NO_PAYMENT", "si forma de pago");

    companion object {
        fun find(value: String?): TypeOfPayment? = entries.find { it.type == value }
    }
}
