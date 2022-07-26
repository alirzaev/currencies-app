package io.github.alirzaev.currencies.data.source

import io.github.alirzaev.currencies.data.source.remote.dto.ExchangeRate
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DefaultExchangeRatesRepository @Inject constructor(
    private val exchangeRatesDataSource: ExchangeRatesDataSource,
    private val currenciesDataSource: CurrenciesDataSource
) : ExchangeRatesRepository {
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getExchangeRates(): List<ExchangeRate> {
        val currencies = runBlocking {
            currenciesDataSource.getCurrencies()
        }.associateBy { it.id }

        return exchangeRatesDataSource
            .getExchangeRatesInfo()
            .exchangeRates
            .values
            .asSequence()
            .sortedWith { a, b ->
                a.name.compareTo(b.name)
            }
            .map { it.copy(name = currencies[it.id]?.name ?: it.name) }
            .toList()
    }
}