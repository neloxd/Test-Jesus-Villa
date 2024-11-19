package com.jesusvilla.test.home.repositories.mappers

import com.jesusvilla.test.base.extension.toDate
import com.jesusvilla.test.home.network.models.ClarificationDto
import com.jesusvilla.test.home.ui.model.Clarification
import com.jesusvilla.test.home.ui.model.ClarificationDetail

fun ClarificationDto.toClarificationDetail(): ClarificationDetail {
    return ClarificationDetail(
        folio = folio,
        createdAt = clarificationDate.toDate(),
        status = status,
        reason = reason.description,
        resolution = "",
        resolvedAt = receptionDate.toDate(),
        compensationPaidAt = receptionDate.toDate(),
        compensation = refund.toString(),
        comment = ""
    )
}

fun List<ClarificationDto>.toClarifications(): List<Clarification> {
    return this.map { clarificationDto ->
        Clarification(
            folio = clarificationDto.folio,
            createdAt = clarificationDto.clarificationDate.toDate(),
            status = clarificationDto.status
        )
    }
}
