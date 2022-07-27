package io.github.alirzaev.currencies.data.source

import io.github.alirzaev.currencies.data.model.Currency

interface CurrenciesRepository {
    suspend fun getCurrencies(force: Boolean = false): List<Currency>
}