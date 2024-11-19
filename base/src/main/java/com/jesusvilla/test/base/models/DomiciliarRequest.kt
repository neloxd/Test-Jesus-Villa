package com.jesusvilla.test.base.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class DomiciliarRequest(
    @SerializedName("agruparCargos")
    val groupCases: Boolean = false,
    @SerializedName("tipo")
    val type: String,
    @SerializedName("banco")
    val bank: String,
    @SerializedName("numeroTarjeta")
    val cardNumber: String,
    @SerializedName("mesExpiracion")
    val expirationMonth: Int,
    @SerializedName("anoExpiracion")
    val expirationYear: Int,
    @SerializedName("claveDinamica")
    val token: String,
    @SerializedName("rfc")
    val rfc: String?,

    @SerializedName("id")
    @Expose(serialize = false)
    val id: String? = null,

    @SerializedName("nombre")
    @Expose(serialize = false)
    val name: String? = null,

    @SerializedName("apellido1")
    @Expose(serialize = false)
    val paternal: String? = null,

    @SerializedName("apellido2")
    @Expose(serialize = false)
    val maternal: String? = null,

    @SerializedName("email")
    @Expose(serialize = false)
    val email: String? = null,

    @SerializedName("telefono")
    @Expose(serialize = false)
    val phone: String? = null,

    @SerializedName("domicilioId")
    @Expose(serialize = false)
    val addressId: Long? = null,

    @SerializedName("modalidadPago")
    @Expose(serialize = false)
    val paymentMethod: String? = null,

    @SerializedName("codigoSeguridad")
    @Expose(serialize = false)
    val ccv: String? = null,

    @SerializedName("deviceInfo")
    val deviceInfo: DeviceInfo? = null,

    @SerializedName("montoRecarga")
    val amountRecharge: BigDecimal? = null
)
