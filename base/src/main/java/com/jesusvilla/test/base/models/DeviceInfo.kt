package com.jesusvilla.test.base.models

import com.google.gson.annotations.SerializedName

data class DeviceInfo(
    @SerializedName("userAgent")
    val userAgent: String? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("width")
    val width: Int? = null,
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("utcOffset")
    val utcOffset: Int? = null
)
