package com.dayoff.manager.startup

import android.content.Context
import androidx.startup.Initializer
import com.dayoff.di.AppModules
import com.dayoff.manager.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 *  Created by KyunghyunPark at 2025. 7. 14.

 */
class KoinInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        startKoin {
            androidContext(androidContext = context)
            // TODO viewModelModule 임시
            modules(modules = AppModules.all.toMutableList().also {
                it.add(viewModelModule)
            })
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(
        FirebaseInitializer::class.java
    )
}