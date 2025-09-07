package com.dayoff.data.di

import com.dayoff.data.util.DefaultDispatcherProvider
import com.dayoff.data.util.DispatcherProvider
import org.koin.dsl.module

/**
 *  Created by KyunghyunPark at 2025. 9. 4.

 */
val coroutineModule = module {
    single<DispatcherProvider> { DefaultDispatcherProvider() }
}