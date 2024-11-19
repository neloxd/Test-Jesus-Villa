package com.jesusvilla.test.base.extension

import android.text.InputFilter
import android.text.Spanned

class RFCInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val filtered = StringBuilder()
        for (i in start until end) {
            val c = source?.get(i)
            if (c?.isLetter() == true || c?.isDigit() == true) {
                filtered.append(c)
            }
        }
        return filtered
    }
}

class CustomEmailInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val regex = Regex("^(?!.*[\\s])[a-zA-Z0-9.@_-]+\$")
        if (source != null && !regex.matches(source)) {
            return ""
        }
        return null
    }
}
