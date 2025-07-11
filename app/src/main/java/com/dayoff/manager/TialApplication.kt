package com.dayoff.manager

import android.app.Application
import org.jetbrains.annotations.NotNull
import timber.log.Timber

/**
 *  Created by KyunghyunPark at 2025. 7. 11.

 */
class TialApplication: Application() {
    override fun onCreate() {
        super.onCreate()

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