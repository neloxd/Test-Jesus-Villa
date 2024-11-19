package com.jesusvilla.test.base.extension

import com.google.gson.Gson

fun Any.toJson(): String {
    return Gson().toJson(this)
}
