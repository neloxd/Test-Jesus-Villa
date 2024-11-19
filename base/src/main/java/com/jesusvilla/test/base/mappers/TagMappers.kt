package com.jesusvilla.test.base.mappers

import com.jesusvilla.test.base.constants.BaseConstants
import com.jesusvilla.test.base.models.Tag
import com.jesusvilla.test.base.models.TagPrefix
import com.jesusvilla.test.base.models.TypeOfPayment
import com.jesusvilla.test.base.models.tags.TagAvailabilityResponse

fun TagAvailabilityResponse.toTag(
    tagName: String,
    insertedNumber: String = BaseConstants.EMPTY_TAG,
    insertedAlias: String = BaseConstants.EMPTY_TAG
): Tag {
    return Tag(
        verificationDigit = verificationDigit,
        name = tagName,
        number = number,
        prefix = TagPrefix.valueOf(prefix),
        typeOfPayment = TypeOfPayment.find(paymentType),
        paymentType = paymentType,
        brand = brand,
        isNewTag = isAvailable,
        isUber = isUber,
        paymentStyle = "",
        balance = 0.0f,
        insertedNumber = insertedNumber,
        insertedAlias = insertedAlias
    )
}
