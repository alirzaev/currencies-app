package io.github.alirzaev.currencies.data.source

import io.github.alirzaev.currencies.data.source.remote.dto.CurrenciesInfo

interface CurrencyDataSource {
    suspend fun getCurrenciesInfo(): CurrenciesInfo
}
