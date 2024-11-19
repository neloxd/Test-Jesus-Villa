package com.jesusvilla.test.base.extension

import com.jesusvilla.test.base.constants.BaseConstants.EMPTY_TAG
import com.jesusvilla.test.base.constants.BaseConstants.LOCALE_COUNTRY
import com.jesusvilla.test.base.constants.BaseConstants.LOCALE_LANGUAGE
import com.jesusvilla.test.base.constants.BaseConstants.SIX
import com.jesusvilla.test.base.constants.BaseConstants.TEN
import com.jesusvilla.test.base.constants.BaseConstants.TWO
import com.jesusvilla.test.base.constants.BaseConstants.ZERO
import java.util.Locale

val locale = Locale(LOCALE_LANGUAGE, LOCALE_COUNTRY)
fun String.hasValidEmailFormat(): Boolean {
    return (
            this.matches(
                "^[0-9]{10}$".toRegex()
            ) || this.matches(
                "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$".toRegex()
            ) && !this.contains(
                "\\.\\.".toRegex()
            )
            )
}

fun String.hasExternalNumberInputFormat(): Boolean {
    return (
            this.matches(
                """^[-,.a-zA-Z0-9\s]*$""".toRegex()
            )
            )
}

fun String.hasStreetInputFormat(): Boolean {
    return (
            this.matches(
                """^[a-zA-Z0-9\s]*$""".toRegex()
            )
            )
}

fun Int.normalizeTagNumber(): String {
    val normalize = "000000000$this"
    return normalize.substring(normalize.length - 8)
}

fun String.hasEightCharacters(): Boolean {
    return this.length >= 8
}

fun String.hasUpperCaseAndLowerCase(): Boolean {
    var hasUpperCase = false
    var hasLowerCase = false

    for (char in this) {
        if (char.isUpperCase()) {
            hasUpperCase = true
        } else if (char.isLowerCase()) {
            hasLowerCase = true
        }

        if (hasUpperCase && hasLowerCase) {
            return true
        }
    }
    return false
}

fun String.hasNumber(): Boolean {
    for (char in this) {
        if (char.isDigit()) {
            return true
        }
    }
    return false
}

fun String.hasSpecialCharacter(): Boolean {
    val specialCharacters = setOf('!', '@', '#', '$', '&', '*')
    for (char in this) {
        if (specialCharacters.contains(char)) {
            return true
        }
    }
    return false
}

fun String?.formatPhoneNumber(): String {
    if (this == null || this.length != TEN) {
        return EMPTY_TAG
    }
    return "${substring(ZERO, TWO)} ${substring(TWO, SIX)} ${substring(SIX)}"
}
