package ar.unq.desapp.grupob.backenddesappapi.service.logging

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import kotlin.system.measureTimeMillis

@Aspect
@Component
@Order(2)
class LoggingAspect {
    private val logger : Logger = LoggerFactory.getLogger(LoggingAspect::class.java)
    @Around("execution(* ar.unq.desapp.grupob.backenddesappapi.controller..*(..))")

    fun logServiceMethods(joinPoint: ProceedingJoinPoint) : Any?{
        val startTime = LocalDateTime.now()
        val user = SecurityContextHolder.getContext().authentication?.name ?: "anonymous" //no se si esto anda
        val operationType = joinPoint.signature.toShortString()
        val parameters = joinPoint.args.joinToString(", ") { it.toString() }

        var result: Any? = null
        val executionTime = measureTimeMillis {
            result = joinPoint.proceed()
        }

        logger.info("TimeStamp: $startTime, " +
                "User: $user, " +
                "Operation: $operationType, " +
                "Execution time: $executionTime, " +
                "Parameters: $parameters")
        return result
    }
}