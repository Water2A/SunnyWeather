package com.cniao.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        //The token of SunnyWeather Api
        const val TOKEN = "1dDZ59e3KJDrbYVD"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}