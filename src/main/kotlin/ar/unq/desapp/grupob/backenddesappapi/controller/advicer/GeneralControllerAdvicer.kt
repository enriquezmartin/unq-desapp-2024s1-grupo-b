package ar.unq.desapp.grupob.backenddesappapi.controller.advicer

import ar.unq.desapp.grupob.backenddesappapi.utils.PriceOutOfRangeException
import ar.unq.desapp.grupob.backenddesappapi.utils.UserCannotBeRegisteredException
import ar.unq.desapp.grupob.backenddesappapi.utils.UsernameAlreadyTakenException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GeneralControllerAdvicer {

    val logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(UsernameAlreadyTakenException::class)
    fun handleRunTimeException(e: RuntimeException): ResponseEntity<String> {
        return this.error(CONFLICT, e)
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleNotFoundException(e: RuntimeException): ResponseEntity<String> {
        return this.error(NOT_FOUND, e)
    }

    @ExceptionHandler(UserCannotBeRegisteredException::class)
    fun handleWrongUserRegister(e: RuntimeException): ResponseEntity<String> {
        return this.error(BAD_REQUEST, e)
    }

    @ExceptionHandler(PriceOutOfRangeException::class)
    fun handleOutOfPriceException(e: RuntimeException): ResponseEntity<String> {
        return this.error(FORBIDDEN, e)
    }
    protected fun error(status: HttpStatus, e: RuntimeException): ResponseEntity<String> {
        logger.error(e.message)
        return ResponseEntity.status(status).body(e.message)
    }
}