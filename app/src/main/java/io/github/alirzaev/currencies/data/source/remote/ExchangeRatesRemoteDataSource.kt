package io.github.alirzaev.currencies.data.source.remote

import io.github.alirzaev.currencies.data.source.remote.dto.ExchangeRatesInfo

interface ExchangeRatesRemoteDataSource {
    suspend fun getExchangeRatesInfo(): ExchangeRatesInfo
}