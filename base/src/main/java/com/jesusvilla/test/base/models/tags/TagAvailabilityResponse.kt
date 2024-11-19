package com.jesusvilla.test.base.models.tags

import com.google.gson.annotations.SerializedName

data class TagAvailabilityResponse(
    @SerializedName("prefijo")
    val prefix: String,
    @SerializedName("numero")
    val number: Int,
    @SerializedName("tagUp")
    val tagUp: String?,
    @SerializedName("tipoCobro")
    val paymentType: String,
    @SerializedName("modalidadPago")
    val paymentMethod: String,
    @SerializedName("marca")
    val brand: String,
    @SerializedName("virgen")
    val isAvailable: Boolean,
    @SerializedName("activo")
    val active: Boolean,
    @SerializedName("uber")
    val isUber: Boolean,
    @SerializedName("digitoVerificador")
    val verificationDigit: Int
)
