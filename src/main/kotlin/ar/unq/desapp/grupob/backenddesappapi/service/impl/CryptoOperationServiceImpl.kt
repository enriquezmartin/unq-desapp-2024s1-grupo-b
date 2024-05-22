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
    override fun payoutNotification(postId: Long, userId: Long): CryptoOperation {
        var post: Post = postRepository.findById(postId).get()
        var user: UserEntity = userRepository.findById(userId).get()
        var lastPrice: Price = priceRepository.findFirstByCryptoCurrencyOrderByPriceTimeDesc(post.cryptoCurrency!!)
        var operation: CryptoOperation = CryptoOperation()
        operation.post = post
        operation.client = user
        if(post.operationType == OperationType.PURCHASE && post.price!! < lastPrice.value!!){
            post.status = PostStatus.ACTIVE
            operation.status = OperationStatus.CANCELLED
        }
        else{
            post.status = PostStatus.IN_PROGRESS
            operation.status = OperationStatus.IN_PROGRESS
        }
        operationRepository.save(operation)
        return operation
    }
}