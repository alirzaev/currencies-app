package io.github.alirzaev.currencies.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.alirzaev.currencies.data.source.assets.CurrenciesAssetsDataSource
import io.github.alirzaev.currencies.data.source.assets.CurrenciesAssetsDataSourceImpl
import io.github.alirzaev.currencies.data.source.local.CurrenciesLocalDataSource
import io.github.alirzaev.currencies.data.source.local.CurrenciesLocalDataSourceImpl
import io.github.alirzaev.currencies.data.source.remote.ExchangeRatesApi
import io.github.alirzaev.currencies.data.source.remote.ExchangeRatesRemoteDataSource
import io.github.alirzaev.currencies.data.source.remote.ExchangeRatesRemoteDataSourceImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideExchangeRatesRemoteDataSource(
        exchangeRatesRemoteDataSourceImpl: ExchangeRatesRemoteDataSourceImpl
    ): ExchangeRatesRemoteDataSource

    @Binds
    abstract fun provideCurrenciesDataSource(
        currenciesAssetsDataSourceImpl: CurrenciesAssetsDataSourceImpl
    ): CurrenciesAssetsDataSource

    @Binds
    abstract fun provideExchangeRatesLocalDataSource(
        exchangeRatesLocalDataSourceImpl: CurrenciesLocalDataSourceImpl
    ): CurrenciesLocalDataSource

    companion object {
        @Provides
        @Singleton
        fun provideExchangeRatesApi(): ExchangeRatesApi =
            Retrofit.Builder()
                .baseUrl("https://www.cbr-xml-daily.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ExchangeRatesApi::class.java)
    }
}