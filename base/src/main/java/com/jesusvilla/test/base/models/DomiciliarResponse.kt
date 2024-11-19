package com.jesusvilla.test.base.models

import com.google.gson.annotations.SerializedName

data class DomiciliarResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("html")
    val html: String? = null,
    @SerializedName("version3ds")
    val version3ds: String? = null,
    @SerializedName("folio")
    val folio: String? = null
)
