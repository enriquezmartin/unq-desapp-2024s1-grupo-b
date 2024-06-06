package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoOperation

interface CryptoOperationService {

    fun payoutNotification(postId: Long, userId: Long): CryptoOperation
    fun confirmOperation(ownerId: Long, operationId: Long): CryptoOperation
}