package com.dayoff.manager.startup

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.FirebaseApp

/**
 *  Created by KyunghyunPark at 2025. 7. 14.

 */
class FirebaseInitializer: Initializer<FirebaseApp> {
    override fun create(context: Context): FirebaseApp {
        return FirebaseApp.initializeApp(context)!!
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}