package ar.unq.desapp.grupob.backenddesappapi.model

import java.time.LocalDate

data class Price(val criptoCurrency: CryptoCurrency,
                 val priceTime: LocalDate,
                 val value: Float)