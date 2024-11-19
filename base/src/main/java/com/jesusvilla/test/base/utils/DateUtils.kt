package com.jesusvilla.test.base.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.format(pattern: String = "yyyy-MM-dd"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault(Locale.Category.FORMAT))
    return sdf.format(this)
}

fun Date.formatDateMovFact(): String {
    return SimpleDateFormat("EEE d '@' hh:mm a", DateUtils.locale).format(this)
        .replace("\\.".toRegex(), "")
}

fun Date.formatDateTransaction(): String {
    return SimpleDateFormat("hh:mm", DateUtils.locale).format(this).replace("\\.".toRegex(), "")
}

object DateUtils {
    val locale = Locale("es", "MX")
}
