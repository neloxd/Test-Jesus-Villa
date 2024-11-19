package com.jesusvilla.test.home.network.models

import com.google.gson.annotations.SerializedName

data class ClarificationReasonDto(
    @SerializedName("id") val id: Int,
    @SerializedName("descripcion") val description: String
)
