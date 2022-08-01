package io.github.alirzaev.currencies.data.source.assets

import io.github.alirzaev.currencies.data.source.assets.dto.Currency

interface CurrenciesAssetsDataSource {
    fun getCurrencies(): List<Currency>
}