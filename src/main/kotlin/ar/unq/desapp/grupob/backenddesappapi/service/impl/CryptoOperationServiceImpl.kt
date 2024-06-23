package ar.unq.desapp.grupob.backenddesappapi.service.impl

import ar.unq.desapp.grupob.backenddesappapi.dtos.OperationReportDTO
import ar.unq.desapp.grupob.backenddesappapi.dtos.ReportDTO
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

    override fun getReport(userId: Long, startDate: LocalDateTime, endDate: LocalDateTime): ReportDTO {
        val operations = operationRepository.getByOwnerBetweenDates(userId, startDate, endDate)
        val dollarPrice = priceRepository.findFirstByCryptoCurrencyOrderByPriceTimeDesc(CryptoCurrency.USDAR)
        val actives = mutableListOf<OperationReportDTO>()
        var totalInDollars = 0f
        var totalInArs = 0f
        operations.forEach {
            val priceInArs = it.post!!.price!! * dollarPrice.value!!
            val active = OperationReportDTO(
                it.post!!.cryptoCurrency!!,
                it.post!!.amount!!,
                it.post!!.price!!,
                priceInArs
            )
            actives.add(active)
            totalInDollars += it.post!!.price!!
            totalInArs += priceInArs
        }
        return ReportDTO(LocalDateTime.now(), totalInDollars, totalInArs, actives)
    }
}