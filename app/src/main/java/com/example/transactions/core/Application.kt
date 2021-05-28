package com.example.transactions.core

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
    }
}