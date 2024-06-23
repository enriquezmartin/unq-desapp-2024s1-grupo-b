package ar.unq.desapp.grupob.backenddesappapi.service.impl

import ar.unq.desapp.grupob.backenddesappapi.model.*
import ar.unq.desapp.grupob.backenddesappapi.repository.CryptoOperationRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.PostRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.PriceRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import ar.unq.desapp.grupob.backenddesappapi.service.CryptoOperationService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
@Transactional
class CryptoOperationServiceImpl: CryptoOperationService {

    @Autowired
    lateinit var priceRepository: PriceRepository

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var operationRepository: CryptoOperationRepository
    override fun  payoutNotification(postId: Long, userId: Long): CryptoOperation {
        var post: Post = postRepository.findById(postId).get()
        var user: UserEntity = userRepository.findById(userId).get()
        var lastPrice: Price = priceRepository.findFirstByCryptoCurrencyOrderByPriceTimeDesc(post.cryptoCurrency!!)

        var operation = CryptoOperation.initOperation(post, lastPrice, user)
        operationRepository.save(operation)

        return operation
    }

    override fun confirmOperation(userId: Long, operationId: Long): CryptoOperation {
        var user: UserEntity = userRepository.findById(userId).get()
        var operation: CryptoOperation = operationRepository.findById(operationId).get()
        operation = user.confirm(operation)
        return operation
    }

    override fun cancelOperation(userId: Long, operationId: Long): CryptoOperation {
        var user: UserEntity = userRepository.findById(userId).get()
        var operation: CryptoOperation = operationRepository.findById(operationId).get()
        operation = user.cancel(operation)
        return operation
    }
}