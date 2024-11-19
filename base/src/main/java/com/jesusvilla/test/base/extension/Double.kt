package com.jesusvilla.test.base.extension

fun Double?.orZero(): Double {
    return this ?: 0.0
}
