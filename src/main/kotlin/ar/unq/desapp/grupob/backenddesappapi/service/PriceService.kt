package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.model.Price

interface PriceService {
    fun updatePrices()
    fun getAllPrices(): List<Price>
}