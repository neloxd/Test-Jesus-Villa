package com.jesusvilla.test.base.models.tags

data class TagOwnershipModel(
    val tagPrefix: String,
    val tagNumber: String,
    var firstQuartetNumbers: String,
    var lastQuartetNumbers: String
)
