package com.jesusvilla.test.base.utils

import com.jesusvilla.test.base.constants.BaseConstants.SPACE_BLANK
import com.jesusvilla.test.base.constants.BaseConstants.TWENTY
import com.jesusvilla.test.base.extension.locale
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern

val tdcPattern: Pattern = Pattern.compile("(\\d{4})(\\d{0,4})(\\d{0,4})(\\d{0,4})")
const val CLARIFICATION_DETAIL_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
private const val REGEX_RAW_NUMBER = "[^0-9]"

private const val SPACER = SPACE_BLANK

fun BigDecimal?.formatPesos(): String {
    this?.let {
        val format = DecimalFormat.getCurrencyInstance()
        format.roundingMode = RoundingMode.HALF_EVEN
        return format.format(this)
    }
    return ""
}

fun formatTDC(rawNumber: String): String {
    val m = tdcPattern.matcher(rawNumber.replace(REGEX_RAW_NUMBER.toRegex(), ""))
    if (!m.matches()) {
        return rawNumber
    }

    val formattedNumber = StringBuilder(TWENTY)
    formattedNumber.append(m.group(1))
    val g2 = m.group(2)
    if (!g2.isNullOrEmpty()) {
        formattedNumber.append(SPACER).append(g2)
        val g3 = m.group(3)
        if (!g3.isNullOrEmpty()) {
            formattedNumber.append(SPACER).append(g3)
            val g4 = m.group(4)
            if (!g4.isNullOrEmpty()) {
                formattedNumber.append(SPACER).append(g4)
            }
        }
    }
    return formattedNumber.toString()
}

fun formatExpirationDate(rawExpirationDate: String): String {
    val value = rawExpirationDate.replace(REGEX_RAW_NUMBER.toRegex(), "")
    if (value.length < 2) {
        return value
    }
    val expirationDateFromatted = StringBuilder(value)
    expirationDateFromatted.insert(2, "/")
    return expirationDateFromatted.toString()
}

fun formatDateUTF8TZ(date: Date): String {
    val sdfExtend = SimpleDateFormat(CLARIFICATION_DETAIL_PATTERN, locale)
    return sdfExtend.format(date)
}

fun formatDateHeader(date: Date?): String {
    val sdfMovimientoFacturaHeader = SimpleDateFormat("MMMM yyyy", DateUtils.locale)
    val formattedDate = sdfMovimientoFacturaHeader.format(date ?: Date())
    return formattedDate.replace(".", "").uppercase(DateUtils.locale)
}


