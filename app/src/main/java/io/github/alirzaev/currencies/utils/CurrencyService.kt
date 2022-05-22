package io.github.alirzaev.currencies.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CurrencyService {
    private const val BASE_URL = "https://www.cbr-xml-daily.ru";

    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    val currencyApi: CurrencyApi = retrofit.create(CurrencyApi::class.java)
}