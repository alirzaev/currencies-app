package io.github.alirzaev.currencies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.alirzaev.currencies.data.source.CurrenciesRepository
import io.github.alirzaev.currencies.data.source.CurrenciesRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrenciesRepositoryModule {
    @Binds
    abstract fun provideCurrenciesRepository(
        currenciesRepositoryImpl: CurrenciesRepositoryImpl
    ): CurrenciesRepository
}