package io.github.alirzaev.currencies.data.source

import io.github.alirzaev.currencies.data.source.remote.dto.Currency
import javax.inject.Inject

class DefaultCurrencyRepository @Inject constructor(
    private val currencyDataSource: CurrencyDataSource
) : CurrencyRepository {
    override suspend fun getCurrencies(): List<Currency> {
        return currencyDataSource
            .getCurrenciesInfo()
            .valute
            .values
            .asSequence()
            .sortedWith { a, b ->
                a.name.compareTo(b.name)
            }
            .toList()
    }
}