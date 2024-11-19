package com.jesusvilla.test.base.extension

object ExtensionsNumeric {
    @JvmStatic
    fun Int?.orZero(): Int = this ?: 0
}
