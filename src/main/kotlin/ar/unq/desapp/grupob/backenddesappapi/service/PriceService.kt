package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.Price

interface PriceService {
    fun updatePrices()
    fun getAllPrices(): List<Price>
    fun getPrices(cryptoCurrency: CryptoCurrency): List<Price>
    fun getLatestPrices(cryptoCurrency: CryptoCurrency): List<Price>
}