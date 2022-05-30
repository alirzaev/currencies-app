package io.github.alirzaev.currencies.data.source

import io.github.alirzaev.currencies.data.source.remote.dto.Currency

interface CurrencyRepository {
    suspend fun getCurrencies(): List<Currency>
}