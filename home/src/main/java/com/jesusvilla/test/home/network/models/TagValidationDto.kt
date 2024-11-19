package com.jesusvilla.test.home.network.models

import com.google.gson.annotations.SerializedName

data class TagValidationDto(
    @SerializedName("prefijo")
    val tagPrefix: String,
    @SerializedName("numero")
    val tagNumber: String,
    @SerializedName("alias")
    val tagName: String
)
