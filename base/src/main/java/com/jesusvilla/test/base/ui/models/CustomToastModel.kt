package com.jesusvilla.test.base.ui.models

class CustomToastModel(
    val text: String,
    val type: CustomToastType = CustomToastType.ERROR
)

enum class CustomToastType {
    ERROR,
    SUCCESS,
    WARNING
}
