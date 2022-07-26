package io.github.alirzaev.currencies.data.source

import io.github.alirzaev.currencies.data.source.remote.dto.ExchangeRate
import javax.inject.Inject

class DefaultExchangeRatesRepository @Inject constructor(
    private val exchangeRatesDataSource: ExchangeRatesDataSource
) : ExchangeRatesRepository {
    override suspend fun getExchangeRates(): List<ExchangeRate> {
        return exchangeRatesDataSource
            .getExchangeRatesInfo()
            .exchangeRates
            .values
            .asSequence()
            .sortedWith { a, b ->
                a.name.compareTo(b.name)
            }
            .toList()
    }
}