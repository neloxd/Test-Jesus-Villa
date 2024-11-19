package com.jesusvilla.test.base.models

import com.google.gson.annotations.SerializedName

data class AddressesResponseModel(
    @SerializedName("id") var id: Long?,
    @SerializedName("calle") var street: String?,
    @SerializedName("numeroExterior") var extNum: String?,
    @SerializedName("numeroInterior") var insNum: String?,
    @SerializedName("cp") var pc: String?,
    @SerializedName("estado") var state: String?,
    @SerializedName("municipio") var municipality: String?,
    @SerializedName("ciudad") var city: String?,
    @SerializedName("colonia") var cologne: String?
)
