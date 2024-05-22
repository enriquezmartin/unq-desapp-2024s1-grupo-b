package ar.unq.desapp.grupob.backenddesappapi.controller.Advicer

import ar.unq.desapp.grupob.backenddesappapi.utils.UserCannotBeRegisteredException
import ar.unq.desapp.grupob.backenddesappapi.utils.UsernameAlreadyTakenException
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GeneralControllerAdvicer {

    val LOGGER = LoggerFactory.getLogger(this.javaClass)

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
    protected fun error(status: HttpStatus, e: RuntimeException): ResponseEntity<String> {
        LOGGER.error(e.message)
        return ResponseEntity.status(status).body(e.message)
    }
}