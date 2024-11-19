package com.jesusvilla.test.base.models

data class UserModel(
    val id: Long,
    val email: String,
    var password: String,
    var name: String,
    val paternalSurname: String,
    val maternalSurname: String,
    val address: DomiciliationAddressModel,
    val active: Boolean,
    var phone: String,
    val verified: Boolean,
    val disabledRecharge: Boolean,
    val registrationDate: Long,
    var tagPrincipal: Tag?,
    var tags: List<Tag> = emptyList(),
    var activeToken: Boolean = false,
    val activeDrive: Boolean = false,
    var phoneConfirmed: Boolean = false,
    var tokenOperation: String,
    val canPay: Boolean,
    val canRecharge: Boolean,
    val phoneTs: String
)
