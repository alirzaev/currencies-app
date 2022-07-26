package io.github.alirzaev.currencies.data.source.remote

import io.github.alirzaev.currencies.data.source.remote.dto.ExchangeRatesInfo
import retrofit2.http.GET

interface ExchangeRatesApi {
    @GET("/daily_json.js")
    suspend fun getExchangeRatesInfo(): ExchangeRatesInfo
}