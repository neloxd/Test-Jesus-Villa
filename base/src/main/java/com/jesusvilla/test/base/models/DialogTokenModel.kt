package com.jesusvilla.test.base.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

const val ACTIVATE_TOKEN = 0
const val ACTIVATE_TOKEN_TO_COMPLETE = 1
const val ACTIVATE_TOKEN_TO_INIT_CONFIG = 2
const val ACTIVATE_TOKEN_TO_COMPLETE_CONFIG = 3
const val OPERATION_TOKEN_TO_ACTION = 4
const val ACTIVATE_TOKEN_OUT_ACCOUNT = 5
const val CHANGE_PHONE_IN_ACCOUNT = 6

@Parcelize
data class DialogTokenModel(
    var typeTokenOperation: Int = OPERATION_TOKEN_TO_ACTION,
    var isActivo: Boolean = false,
    var isBiometricsActive: Boolean = false,
    var onSuccess: (() -> Unit)? = null,
    var onCancel: (() -> Unit)? = null,
    var statusBarColor: Int? = null
) : Parcelable
