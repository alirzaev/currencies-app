package io.github.alirzaev.currencies.data.source

import io.github.alirzaev.currencies.data.model.Currency
import io.github.alirzaev.currencies.data.source.assets.CurrenciesAssetsDataSource
import io.github.alirzaev.currencies.data.source.local.CurrenciesLocalDataSource
import io.github.alirzaev.currencies.data.source.remote.ExchangeRatesRemoteDataSource
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CurrenciesRepositoryImpl @Inject constructor(
    private val exchangeRatesRemoteDataSource: ExchangeRatesRemoteDataSource,
    private val currenciesLocalDataSource: CurrenciesLocalDataSource,
    private val currenciesAssetsDataSource: CurrenciesAssetsDataSource
) : CurrenciesRepository {
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getCurrencies(): List<Currency> {
        val currencies = runBlocking {
            currenciesAssetsDataSource.getCurrencies()
        }.associateBy { it.id }

        return exchangeRatesRemoteDataSource
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
            .toList()
    }

    override suspend fun getCachedCurrencies(): List<Currency> {
        return currenciesLocalDataSource.getCurrencies()
    }

    override suspend fun saveCurrencies(currencies: List<Currency>) {
        currenciesLocalDataSource.saveCurrencies(currencies)
    }
}