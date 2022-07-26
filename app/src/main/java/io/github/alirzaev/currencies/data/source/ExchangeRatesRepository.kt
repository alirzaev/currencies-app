package io.github.alirzaev.currencies.data.source

import io.github.alirzaev.currencies.data.source.remote.dto.ExchangeRate

interface ExchangeRatesRepository {
    suspend fun getExchangeRates(): List<ExchangeRate>
}