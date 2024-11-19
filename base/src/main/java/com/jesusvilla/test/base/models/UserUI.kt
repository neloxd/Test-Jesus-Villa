package com.jesusvilla.test.base.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserUI(
    var idEntity: Long = 0,
    var idUser: Long,
    val email: String,
    var password: String,
    var name: String = "",
    val paternalSurname: String = "",
    val maternalSurname: String = "",
    val active: Boolean = false,
    var phone: String,
    val verified: Boolean = false,
    val disabledRecharge: Boolean = false,
    val registrationDate: Long,
    var activeToken: Boolean = false,
    val activeDrive: Boolean = false,
    var phoneConfirmed: Boolean = false,
    var tokenOperation: String,
    val canPay: Boolean = false,
    val canRecharge: Boolean = false,
    val phoneTs: String,
    val tagSelected: Long
):Parcelable {
    val completeName: String = "$name $paternalSurname $maternalSurname"
}
