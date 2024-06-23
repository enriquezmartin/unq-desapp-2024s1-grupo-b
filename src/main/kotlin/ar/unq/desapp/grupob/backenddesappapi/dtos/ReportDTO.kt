package ar.unq.desapp.grupob.backenddesappapi.dtos

import java.time.LocalDateTime

data class ReportDTO(
    val dateTime: LocalDateTime,
    val totalInDollars: Float,
    val totalInArs: Float,
    val actives: List<OperationReportDTO>
)
