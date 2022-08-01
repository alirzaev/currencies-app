package io.github.alirzaev.currencies.data.source

import io.github.alirzaev.currencies.data.model.Currency

interface CurrenciesRepository {
    suspend fun getCurrencies(): List<Currency>

    suspend fun getCachedCurrencies(): List<Currency>

    suspend fun saveCurrencies(currencies: List<Currency>)
}