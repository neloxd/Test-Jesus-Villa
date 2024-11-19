package com.jesusvilla.test.base.extension

import com.jesusvilla.test.base.constants.BaseConstants.SIXTY

@Suppress("ImplicitDefaultLocale")
fun Int.toMinutesAndSeconds(): String {
    val minutes = this / SIXTY
    val seconds = this % SIXTY
    return String.format("%02d:%02d", minutes, seconds)
}
