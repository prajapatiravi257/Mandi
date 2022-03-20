package com.planetnoobs.mandi.core

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InvestorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("InvestorApp", "onCreate")
    }
}