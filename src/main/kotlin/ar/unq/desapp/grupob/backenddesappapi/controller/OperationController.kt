package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.dtos.OperationDTO
import ar.unq.desapp.grupob.backenddesappapi.model.CryptoOperation
import ar.unq.desapp.grupob.backenddesappapi.model.OperationType
import ar.unq.desapp.grupob.backenddesappapi.model.Price
import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import ar.unq.desapp.grupob.backenddesappapi.service.CryptoOperationService
import ar.unq.desapp.grupob.backenddesappapi.service.PriceService
import ar.unq.desapp.grupob.backenddesappapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/operation")
class OperationController {

    @Autowired
    lateinit var operationService: CryptoOperationService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var priceService: PriceService

    @PostMapping("/notify_payout/{userId}/{postId}")
    fun notifyPayout(@PathVariable userId: String, @PathVariable postId: String): OperationDTO{
        val user = userService.findUserBYId(userId.toLong())
        val operation = operationService.payoutNotification(postId.toLong(), userId.toLong())
        val price = priceService.getLastPrice(operation.post!!.cryptoCurrency!!)
        val shippingAddress = when (operation.post!!.operationType) {
            OperationType.SALE -> user.cvu
            OperationType.PURCHASE -> user.walletAddress
            else -> ""
        }

        return OperationDTO(
            operation.post!!.cryptoCurrency!!,
            operation.post!!.amount!!,
            price.value!!,
            operation.post!!.price!!,
            "${user.name} ${user.surname}",
            user.successfulOperation,
            user.score,
            shippingAddress
        )
    }

    @PostMapping("/confirm/{userId}/{operationId}")
    fun confirm(@PathVariable userId: String, @PathVariable operationId: String): OperationDTO{
        val operation = operationService.confirmOperation(userId.toLong(), operationId.toLong())
        return buildOperationDTO(userId, operation)
    }
    @PostMapping("/notify_payout/{userId}/{operationId}")
    fun cancel(@PathVariable userId: String, @PathVariable operationId: String): OperationDTO{
        val operation = operationService.cancelOperation(userId.toLong(), operationId.toLong())
        return buildOperationDTO(userId, operation)
    }

    private fun buildOperationDTO(userId: String, operation: CryptoOperation): OperationDTO {
        val user = userService.findUserBYId(userId.toLong())
        val price = priceService.getLastPrice(operation.post!!.cryptoCurrency!!)
        return OperationDTO(
            operation.post!!.cryptoCurrency!!,
            operation.post!!.amount!!,
            price.value!!,
            operation.post!!.price!!,
            "${user.name} ${user.surname}",
            user.successfulOperation,
            user.score,
            null
        )
    }
}