package com.jesusvilla.test.home.network.models

import com.google.gson.annotations.SerializedName

data class ClarificationDto(
    @SerializedName("folio") val folio: String,
    @SerializedName("corredor") val generalLocationName: String,
    @SerializedName("idCaseta") val idLocation: String,
    @SerializedName("idCarril") val idLocationLane: String,
    @SerializedName("fechaCruce") val crossingDate: Long,
    @SerializedName("fechaMovimiento") val movementDate: String,
    @SerializedName("horaMovimiento") val movementTime: String,
    @SerializedName("fechaRecepcion") val receptionDate: Long,
    @SerializedName("monto") val amount: Double,
    @SerializedName("saldo") val balance: Double,
    @SerializedName("motivo") val reason: ClarificationReasonDto,
    @SerializedName("fechaAclaracion") val clarificationDate: Long,
    @SerializedName("montoAclaracion") val clarificationAmount: Double,
    @SerializedName("status") val status: String,
    @SerializedName("devolucion") val refund: Double
)
