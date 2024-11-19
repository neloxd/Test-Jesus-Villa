package com.jesusvilla.test.base.extension

import com.jesusvilla.test.base.constants.BaseConstants
import java.text.NumberFormat

fun Float.formatToMexicanPesosWithDecimal(): String {
    return try {
        val format = NumberFormat.getCurrencyInstance(locale)
        format.format(this)
    } catch (e: NumberFormatException) {
        BaseConstants.VALUE_INITIAL_PESOS
    }
}
fun Float.formatToMexicanPesosWithOutDecimal(): String {
    return  String.format("$%.0f", this)
}

fun Float?.orZero(): Float {
    return this ?: 0.0f
}
