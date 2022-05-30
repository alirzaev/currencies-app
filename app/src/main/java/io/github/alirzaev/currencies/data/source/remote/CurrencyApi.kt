package io.github.alirzaev.currencies.data.source.remote

import io.github.alirzaev.currencies.data.source.remote.dto.CurrenciesInfo
import retrofit2.http.GET

interface CurrencyApi {
    @GET("/daily_json.js")
    suspend fun getCurrenciesInfo(): CurrenciesInfo
}