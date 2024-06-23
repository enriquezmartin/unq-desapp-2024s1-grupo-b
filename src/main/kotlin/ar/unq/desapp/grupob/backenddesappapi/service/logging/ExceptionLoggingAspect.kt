package ar.unq.desapp.grupob.backenddesappapi.service.logging

import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class ExceptionLoggingAspect {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    @Pointcut("execution(* ar.unq.desapp.grupob.backenddesappapi.controller..*(..))")
    fun controllerMethodPointcut() {
    }

    @AfterThrowing(pointcut = "controllerMethodPointcut()", throwing = "e")
    fun logExceptionThrowing(e: Throwable){
        logger.error("Exception: ${e.javaClass.simpleName} - Message : ${e.message}")
    }
}