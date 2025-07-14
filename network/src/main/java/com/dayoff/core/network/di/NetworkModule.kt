package com.dayoff.core.network.di

/**
 *  Created by KyunghyunPark at 2025. 7. 14.

 */
import com.dayoff.core.network.api.CalendarApi
import org.koin.dsl.module


val networkModule = module {
    single { CalendarApi() }
}