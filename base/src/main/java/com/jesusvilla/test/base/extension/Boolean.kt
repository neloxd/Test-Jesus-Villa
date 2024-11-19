package com.jesusvilla.test.base.extension

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}
