package com.example.beersmvvm.core.di

import android.app.Application
import android.content.Context
import com.example.transactions.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    companion object {
        const val nameApp = "nameApp"
    }

    @Provides
    @Singleton
    fun providesContext(context: Application): Context {
        return context.applicationContext
    }

    @Provides
    @Singleton
    @Named(nameApp)
    fun provideNameApp(context: Context): String {
        return context.getString(R.string.app_name)
    }
}