package ar.unq.desapp.grupob.backenddesappapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class BackendDesappApiApplication

fun main(args: Array<String>) {
	runApplication<BackendDesappApiApplication>(*args)
}
