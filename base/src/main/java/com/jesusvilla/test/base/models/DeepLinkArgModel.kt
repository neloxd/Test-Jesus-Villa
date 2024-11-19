package com.jesusvilla.test.base.models

import com.jesusvilla.test.base.extension.toJson

interface ArgParcelable

data class DeepLinkArgModel<T>(
    val key: String,
    val value: T
)

fun <T> List<DeepLinkArgModel<T>>.buildQueryString(): String {
    return if (isEmpty()) {
        ""
    } else {
        "?" + joinToString("&") { it.buildRequest() }
    }
}

fun <T> DeepLinkArgModel<T>.buildRequest(): String {
    return when (value) {
        is ArgParcelable -> {
            "$key=${value.toJson()}"
        }

        else -> {
            "$key=$value"
        }
    }
}
