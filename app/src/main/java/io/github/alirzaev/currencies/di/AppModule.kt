package io.github.alirzaev.currencies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.alirzaev.currencies.data.source.ExchangeRatesDataSource
import io.github.alirzaev.currencies.data.source.ExchangeRatesRepository
import io.github.alirzaev.currencies.data.source.DefaultExchangeRatesRepository
import io.github.alirzaev.currencies.data.source.remote.ExchangeRatesApi
import io.github.alirzaev.currencies.data.source.remote.ExchangeRatesRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideExchangeRatesApi(): ExchangeRatesApi =
        Retrofit.Builder()
            .baseUrl("https://www.cbr-xml-daily.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRatesApi::class.java)

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteExchangeRatesDataSource

    @Provides
    @RemoteExchangeRatesDataSource
    fun provideExchangeRatesRemoteDataSource(
        exchangeRatesApi: ExchangeRatesApi,
        @NetworkModule.IoCoroutineDispatcher ioDispatcher: CoroutineDispatcher
    ): ExchangeRatesDataSource {
        return ExchangeRatesRemoteDataSource(
            exchangeRatesApi,
            ioDispatcher
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class IoCoroutineDispatcher

    @Provides
    @IoCoroutineDispatcher
    fun provideIoDispatch() = Dispatchers.IO
}

@Module
@InstallIn(SingletonComponent::class)
class ExchangeRatesRepositoryModule {
    @Provides
    fun provideExchangeRatesRepository(
        @AppModule.RemoteExchangeRatesDataSource exchangeRatesDataSource: ExchangeRatesDataSource
    ): ExchangeRatesRepository {
        return DefaultExchangeRatesRepository(exchangeRatesDataSource)
    }
}