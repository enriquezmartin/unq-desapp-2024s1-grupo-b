package ar.unq.desapp.grupob.backenddesappapi.dataInit

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.OperationType
import ar.unq.desapp.grupob.backenddesappapi.model.Post
import ar.unq.desapp.grupob.backenddesappapi.model.StatusPost
import ar.unq.desapp.grupob.backenddesappapi.repository.PostRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import ar.unq.desapp.grupob.backenddesappapi.service.PostService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.*

@Configuration
class PostDataLoader(private val postService: PostRepository,
                     private val userService: UserRepository) {

    fun <T> Optional<T>.toNullable(): T? = orElse(null)

    @Bean
    @Order(1)
    fun initPosts() = CommandLineRunner {
        val user = userService.findByEmail("foodlover53@aol.com")
        val post1 = Post(
            cryptoCurrency = CryptoCurrency.BTCUSDT,
            amount = 0.1f,
            price = 50000.0f,
            operationType = OperationType.PURCHASE,
            status = StatusPost.ACTIVE
        ).apply { this.user = user.toNullable() }

        postService.saveAll(listOf(post1))
    }
}