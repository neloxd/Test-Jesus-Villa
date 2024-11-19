package com.jesusvilla.test.base.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.Locale

class UpperCaseTextWatcher(private val editText: EditText) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Empty Function
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Empty Function
    }

    override fun afterTextChanged(s: Editable?) {
        s?.let {
            if (it.isNotEmpty() && it.toString() != it.toString().uppercase(Locale.ROOT)) {
                editText.removeTextChangedListener(this)
                editText.setText(it.toString().uppercase(Locale.ROOT))
                editText.setSelection(it.length)
                editText.addTextChangedListener(this)
            }
        }
    }
}
