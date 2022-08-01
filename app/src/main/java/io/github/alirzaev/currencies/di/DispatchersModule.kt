package io.github.alirzaev.currencies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class IoCoroutineDispatcher

    @Provides
    @IoCoroutineDispatcher
    fun provideIoDispatch() = Dispatchers.IO
}