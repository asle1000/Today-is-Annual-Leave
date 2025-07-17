package com.dayoff.manager.startup

import android.content.Context
import androidx.startup.Initializer
import com.dayoff.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 *  Created by KyunghyunPark at 2025. 7. 14.

 */
class KoinInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        startKoin {
            androidContext(androidContext = context)
            modules(modules = AppModules.all)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(
        FirebaseInitializer::class.java
    )
}