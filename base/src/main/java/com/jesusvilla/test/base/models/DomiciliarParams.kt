package com.jesusvilla.test.base.models

import com.jesusvilla.test.base.enums.BankType
import com.jesusvilla.test.base.enums.CreditType
import com.jesusvilla.test.base.enums.PaymentMethodType
import java.math.BigDecimal

data class DomiciliarParams(
    val id: String? = null,
    val accountId: Long,
    val prefix: String,
    val number: Int,
    val token: String,
    val type: CreditType,
    val bank: BankType,
    val cardNumber: String,
    val expirationDate: List<String>,
    val rfc: String? = null,
    val name: String? = null,
    val paternal: String? = null,
    val maternal: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val addressId: Long? = null,
    val paymentMethodType: PaymentMethodType? = null,
    val ccv: String? = null,
    val deviceInfo: DeviceInfo? = null,
    val amountRechage: BigDecimal? = null,
    val groupCases: Boolean = false
)
