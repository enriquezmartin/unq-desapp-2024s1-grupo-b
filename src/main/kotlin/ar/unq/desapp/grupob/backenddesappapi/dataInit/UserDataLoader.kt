package ar.unq.desapp.grupob.backenddesappapi.dataInit

import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import ar.unq.desapp.grupob.backenddesappapi.service.AuthService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Configuration
class UserDataLoader(private val authService: AuthService) {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    //se creara un cmd line runner por cada entidad, y se debe tener en cuenta el orden en que se ejecutan
    fun initUsers()= CommandLineRunner {
        val user0 = UserEntity(
            "foodlover53@aol.com",
            "P4ss*_w0rd",
            "Homero",
            "Simpson",
            "Evergreen avenue 742",
            "2613984763127892345314",
            "87654321"
        )

        val user1 = UserEntity(
            "goat10@messi.com",
            "Messi_4life!",
            "Lionel",
            "Messi",
            "Rosario street 10",
            "1234567890123456789012",
            "10101010"
        )

        val user2 = UserEntity(
            "spidey@dailybugle.com",
            "Web*_l1nger23",
            "Peter",
            "Parker",
            "20 Ingram Street",
            "2345678901234567890123",
            "20202020"
        )

        val user3 = UserEntity(
            "saiyan.kakarot@dbz.com",
            "Dr@g0n%BallZ",
            "Goku",
            "Son",
            "Mount Paozu",
            "3456789012345678901234",
            "30303030"
        )

        val user4 = UserEntity(
            "wizarding.world@hogwarts.com",
            "Magic!_W4nd",
            "Harry",
            "Potter",
            "4 Privet Drive",
            "4567890123456789012345",
            "40404040"
        )
        authService.registerAll(listOf(user0, user1, user2, user3, user4))
    }
}