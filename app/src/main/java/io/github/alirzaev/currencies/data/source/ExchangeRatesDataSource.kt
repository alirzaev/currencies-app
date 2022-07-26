package io.github.alirzaev.currencies.data.source

import io.github.alirzaev.currencies.data.source.remote.dto.ExchangeRatesInfo

interface ExchangeRatesDataSource {
    suspend fun getExchangeRatesInfo(): ExchangeRatesInfo
}
