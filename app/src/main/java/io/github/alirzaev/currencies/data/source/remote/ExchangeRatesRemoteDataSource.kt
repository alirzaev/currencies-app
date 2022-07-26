package io.github.alirzaev.currencies.data.source.remote

import io.github.alirzaev.currencies.data.source.ExchangeRatesDataSource
import io.github.alirzaev.currencies.data.source.remote.dto.ExchangeRatesInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExchangeRatesRemoteDataSource @Inject constructor(
    private val exchangeRatesApi: ExchangeRatesApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    ExchangeRatesDataSource {
    override suspend fun getExchangeRatesInfo(): ExchangeRatesInfo {
        return withContext(ioDispatcher) {
            exchangeRatesApi.getExchangeRatesInfo()
        }
    }
}