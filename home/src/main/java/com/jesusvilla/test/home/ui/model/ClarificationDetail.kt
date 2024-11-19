package com.jesusvilla.test.home.ui.model

import java.util.Date

data class ClarificationDetail(
    val folio: String,
    val createdAt: Date,
    val status: String,
    val reason: String,
    val resolution: String,
    val resolvedAt: Date,
    val compensationPaidAt: Date,
    val compensation: String,
    val comment: String
)
