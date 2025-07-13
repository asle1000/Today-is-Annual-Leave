package com.dayoff.manager

import android.app.Application
import com.dayoff.di.AppModules
import org.jetbrains.annotations.NotNull
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 *  Created by KyunghyunPark at 2025. 7. 11.

 */
class TialApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TialApplication)
            AppModules.all
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return String.format(
                        "Class:%s: Line: %s, Method: %s",
                        super.createStackElementTag(element),
                        element.lineNumber,
                        element.methodName
                    )
                }
            })
        }
    }
}