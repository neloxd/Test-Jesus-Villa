package com.jesusvilla.test.base.repositories.mappers

import com.jesusvilla.test.base.models.UserUI
import com.jesusvilla.test.database.entities.test.PostEntity
import com.jesusvilla.test.database.entities.users.UserEntity

fun UserEntity.toUserUI(): UserUI {
    return UserUI(
        idEntity = this.idEntity,
        idUser = this.idUser,
        email = this.email,
        password = this.password,
        name = this.name,
        paternalSurname = this.paternalSurname,
        maternalSurname = this.maternalSurname,
        active = this.active,
        phone = this.phone,
        verified = this.verified,
        disabledRecharge = this.disabledRecharge,
        registrationDate = this.registrationDate,
        activeToken = this.activeToken,
        activeDrive = this.activeDrive,
        phoneConfirmed = this.phoneConfirmed,
        tokenOperation = this.tokenOperation,
        canPay = this.canPay,
        canRecharge = this.canRecharge,
        phoneTs = this.phoneTs,
        tagSelected = this.tagSelected,
    )
}
