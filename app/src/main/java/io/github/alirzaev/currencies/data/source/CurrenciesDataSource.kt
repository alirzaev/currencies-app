package io.github.alirzaev.currencies.data.source

import io.github.alirzaev.currencies.data.source.assets.dto.Currency

interface CurrenciesDataSource {
    fun getCurrencies(): List<Currency>
}