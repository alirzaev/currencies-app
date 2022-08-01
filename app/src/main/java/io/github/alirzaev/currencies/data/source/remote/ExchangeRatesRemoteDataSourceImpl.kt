package io.github.alirzaev.currencies.data.source.remote

import io.github.alirzaev.currencies.data.source.remote.dto.ExchangeRatesInfo
import io.github.alirzaev.currencies.di.DispatchersModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExchangeRatesRemoteDataSourceImpl @Inject constructor(
    private val exchangeRatesApi: ExchangeRatesApi,
    @DispatchersModule.IoCoroutineDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    ExchangeRatesRemoteDataSource {
    override suspend fun getExchangeRatesInfo(): ExchangeRatesInfo {
        return withContext(ioDispatcher) {
            exchangeRatesApi.getExchangeRatesInfo()
        }
    }
}