package com.jesusvilla.test.base.mappers

import com.jesusvilla.test.base.models.DomiciliarParams
import com.jesusvilla.test.base.models.DomiciliarRequest

fun DomiciliarParams.toDomiciliarDto(): DomiciliarRequest {
    return DomiciliarRequest(
        type = type.name,
        bank = bank.name,
        cardNumber = cardNumber,
        expirationMonth = expirationDate[0].toInt(),
        expirationYear = expirationDate[1].toInt(),
        token = token,
        rfc = rfc,
        id = id,
        name = name,
        paternal = paternal,
        maternal = maternal,
        email = email,
        phone = phone,
        addressId = addressId ?: 0,
        paymentMethod = paymentMethodType?.name,
        ccv = ccv,
        deviceInfo = deviceInfo,
        amountRecharge = amountRechage,
        groupCases = groupCases
    )
}
