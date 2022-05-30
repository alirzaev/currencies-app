package io.github.alirzaev.currencies.data.source.remote

import io.github.alirzaev.currencies.data.source.CurrencyDataSource
import io.github.alirzaev.currencies.data.source.remote.dto.CurrenciesInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRemoteDataSource @Inject constructor(
    private val currencyApi: CurrencyApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    CurrencyDataSource {
    override suspend fun getCurrenciesInfo(): CurrenciesInfo {
        return withContext(ioDispatcher) {
            currencyApi.getCurrenciesInfo()
        }
    }
}