package io.github.alirzaev.currencies.data.source.local

import io.github.alirzaev.currencies.data.model.Currency

interface CurrenciesLocalDataSource {
    fun getCurrencies(): List<Currency>

    fun saveCurrencies(currencies: List<Currency>)
}