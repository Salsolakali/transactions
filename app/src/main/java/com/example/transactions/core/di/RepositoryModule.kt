package com.example.transactions.core.di

import com.example.transactions.core.data.APIService
import com.example.transactions.features.home.response.DataRepositoryImpl
import com.example.transactions.features.home.domain.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providesBeersRepository(apiservice: APIService): DataRepository {
        return DataRepositoryImpl(apiservice)
    }
}