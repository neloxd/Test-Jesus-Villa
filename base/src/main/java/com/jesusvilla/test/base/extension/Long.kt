package com.jesusvilla.test.base.extension

import java.time.Instant
import java.util.Date

fun Long.toDate(): Date {
    return Date.from(Instant.ofEpochMilli(this))
}

fun Long?.orZero(): Long = this ?: 0

fun Long?.toStringOrEmpty(): String {
    return this?.toString() ?: ""
}
