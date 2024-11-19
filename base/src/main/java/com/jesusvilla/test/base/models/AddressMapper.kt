package com.jesusvilla.test.base.models

import com.jesusvilla.test.base.extension.orZero

fun AddressesResponseModel.convertToModel() = DomiciliationAddressModel(
    id = this.id.orZero(),
    street = this.street.orEmpty(),
    externalNumber = this.extNum.orEmpty(),
    internalNumber = this.insNum.orEmpty(),
    zipCode = this.pc.orEmpty(),
    state = this.state.orEmpty(),
    townHall = this.municipality.orEmpty(),
    city = this.city.orEmpty(),
    colony = this.cologne.orEmpty()
)