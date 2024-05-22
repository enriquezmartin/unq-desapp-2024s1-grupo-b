package ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.RestTemplate
@Configuration
@EnableScheduling
class BinanceConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}