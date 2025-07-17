package com.dayoff.data.di

import com.dayoff.data.datasource.CalendarEventRemoteDataSource
import com.dayoff.data.datasource.CalendarEventRemoteDataSourceImpl
import org.koin.dsl.module

/**
 *  Created by KyunghyunPark at 2025. 7. 14.

 */
val dataModule = module {

    single<CalendarEventRemoteDataSource> {
        CalendarEventRemoteDataSourceImpl(
            calendarApi = get()
        )
    }
}