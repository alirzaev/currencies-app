package io.github.alirzaev.currencies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.alirzaev.currencies.data.source.CurrenciesDataSource
import io.github.alirzaev.currencies.data.source.CurrenciesRepository
import io.github.alirzaev.currencies.data.source.DefaultCurrenciesRepository
import io.github.alirzaev.currencies.data.source.ExchangeRatesDataSource
import io.github.alirzaev.currencies.data.source.assets.CurrenciesAssetsDataSource
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

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AssetsCurrenciesDataSource

    @Provides
    @AssetsCurrenciesDataSource
    fun provideCurrenciesDataSource(
        @ApplicationContext context: Context
    ): CurrenciesDataSource {
        return CurrenciesAssetsDataSource(context)
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
class CurrenciesRepositoryModule {
    @Provides
    fun provideCurrenciesRepository(
        @AppModule.RemoteExchangeRatesDataSource exchangeRatesDataSource: ExchangeRatesDataSource,
        @AppModule.AssetsCurrenciesDataSource currenciesDataSource: CurrenciesDataSource
    ): CurrenciesRepository {
        return DefaultCurrenciesRepository(exchangeRatesDataSource, currenciesDataSource)
    }
}