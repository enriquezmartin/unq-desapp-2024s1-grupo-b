package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoOperation
import ar.unq.desapp.grupob.backenddesappapi.model.Post

interface CryptoOperationService {

    fun payoutNotification(postId: Long, userId: Long): CryptoOperation
}