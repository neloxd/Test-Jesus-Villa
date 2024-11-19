package com.jesusvilla.test.base.models

data class TokenDialogModel(
    val typeTokenOperation: Int = 0,
    val onSuccess: (() -> Unit)? = null,
    val onCancel: (() -> Unit)? = null,
    val navigationModel: NavigateFragmentModel? = null,
    val isExternal: Boolean = false
)
