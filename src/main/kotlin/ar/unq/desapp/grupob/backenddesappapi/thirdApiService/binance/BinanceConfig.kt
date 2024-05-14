package ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
@Configuration
class BinanceConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}