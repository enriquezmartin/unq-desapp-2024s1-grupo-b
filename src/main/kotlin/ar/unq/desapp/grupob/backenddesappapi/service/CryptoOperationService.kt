package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.dtos.ReportDTO
import ar.unq.desapp.grupob.backenddesappapi.model.CryptoOperation
import java.time.LocalDateTime

interface CryptoOperationService {

    fun payoutNotification(postId: Long, userId: Long): CryptoOperation
    fun confirmOperation(ownerId: Long, operationId: Long): CryptoOperation
    fun cancelOperation(ownerId: Long, operationId: Long): CryptoOperation
    fun getReport(ownerId: Long, startDate: LocalDateTime, endDate: LocalDateTime): ReportDTO
}