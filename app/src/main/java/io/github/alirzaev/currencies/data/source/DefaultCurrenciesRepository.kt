package io.github.alirzaev.currencies.data.source

import io.github.alirzaev.currencies.data.model.Currency
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DefaultCurrenciesRepository @Inject constructor(
    private val exchangeRatesDataSource: ExchangeRatesDataSource,
    private val currenciesDataSource: CurrenciesDataSource
) : CurrenciesRepository {
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getCurrencies(force: Boolean): List<Currency> {
        val localCached = cachedCurrencies
        if (!force && localCached != null) {
            return localCached
        }

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
            .map {
                Currency(
                    it.id,
                    it.numCode,
                    it.charCode,
                    it.nominal,
                    currencies[it.id]?.name ?: it.name,
                    it.value,
                    it.previous
                )
            }
            .toList().also {
                cachedCurrencies = it
            }
    }

    companion object {
        private var cachedCurrencies: List<Currency>? = null
    }
}