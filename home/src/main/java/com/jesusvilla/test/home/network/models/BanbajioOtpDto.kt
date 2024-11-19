package com.jesusvilla.test.home.network.models

import com.google.gson.annotations.SerializedName

data class BanbajioOtpDto(
    @SerializedName("domiciliacionId")
    val domiciliacionId: String,
    @SerializedName("codigoConfirmacion")
    val confirmationCode: String
)
