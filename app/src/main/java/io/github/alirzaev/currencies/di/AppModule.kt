package io.github.alirzaev.currencies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.alirzaev.currencies.data.source.CurrencyDataSource
import io.github.alirzaev.currencies.data.source.CurrencyRepository
import io.github.alirzaev.currencies.data.source.DefaultCurrencyRepository
import io.github.alirzaev.currencies.data.source.remote.CurrencyApi
import io.github.alirzaev.currencies.data.source.remote.CurrencyRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideCurrencyApi(): CurrencyApi =
        Retrofit.Builder()
            .baseUrl("https://www.cbr-xml-daily.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)

    @Provides
    fun provideIoDispatch() = Dispatchers.IO

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteCurrencyDataSource

    @Provides
    @RemoteCurrencyDataSource
    fun provideCurrencyRemoteDataSource(
        currencyApi: CurrencyApi,
        ioDispatcher: CoroutineDispatcher
    ): CurrencyDataSource {
        return CurrencyRemoteDataSource(
            currencyApi,
            ioDispatcher
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
class CurrencyRepositoryModule {
    @Provides
    fun provideCurrencyRepository(
        @AppModule.RemoteCurrencyDataSource currencyDataSource: CurrencyDataSource
    ): CurrencyRepository {
        return DefaultCurrencyRepository(currencyDataSource)
    }
}