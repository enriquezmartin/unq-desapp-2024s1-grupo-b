package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.dtos.OperationDTO
import ar.unq.desapp.grupob.backenddesappapi.dtos.ReportDTO
import ar.unq.desapp.grupob.backenddesappapi.model.CryptoOperation
import ar.unq.desapp.grupob.backenddesappapi.model.OperationType
import ar.unq.desapp.grupob.backenddesappapi.service.CryptoOperationService
import ar.unq.desapp.grupob.backenddesappapi.service.PriceService
import ar.unq.desapp.grupob.backenddesappapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalTime

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
        val user = userService.findUserById(userId.toLong())
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
    @PostMapping("/cancel/{userId}/{operationId}")
    fun cancel(@PathVariable userId: String, @PathVariable operationId: String): OperationDTO{
        val operation = operationService.cancelOperation(userId.toLong(), operationId.toLong())
        return buildOperationDTO(userId, operation)
    }

    @GetMapping("/{userId}")
    fun getReport(@PathVariable userId: String,
                  @RequestParam startDate: String,
                  @RequestParam endDate: String):ReportDTO{
        val start = LocalDate.parse(startDate).atStartOfDay()
        val end = LocalDate.parse(endDate).atTime(LocalTime.MAX)
        return operationService.getReport(userId.toLong(), start, end)
    }

    private fun buildOperationDTO(userId: String, operation: CryptoOperation): OperationDTO {
        val user = userService.findUserById(userId.toLong())
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