package com.dayoff.manager.startup

import android.content.Context
import androidx.startup.Initializer
import com.dayoff.manager.BuildConfig
import timber.log.Timber

/**
 *  Created by KyunghyunPark at 2025. 7. 14.

 */
class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
//        TOBE Firebase Crashlytics 연동
//        else {
//            Timber.plant(CrashlyticsTree())
//        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(FirebaseInitializer::class.java)
}