package com.example.jettrips

import android.app.Application
import com.example.jettrips.utils.NotificationUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JetTripsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationUtils.createNotificationChannel(this)
    }
}