package io.github.alirzaev.currencies.utils

import io.github.alirzaev.currencies.utils.dto.CurrenciesInfo
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyApi {
    @GET("/daily_json.js")
    fun getCurrenciesInfo(): Call<CurrenciesInfo>
}