package com.dayoff.data.di

import com.dayoff.data.datasource.CalendarEventLocalDatasource
import com.dayoff.data.datasource.CalendarEventRemoteDataSource
import com.dayoff.data.datasource.CalendarEventRemoteDataSourceImpl
import com.dayoff.data.repository.CalendarRepository
import com.dayoff.core.network.api.CalendarApi
import org.koin.dsl.module

/**
 *  Created by KyunghyunPark at 2025. 7. 14.
 *
 */
val dataModule = module {
    single<CalendarApi> { CalendarApi() }

    single<CalendarEventRemoteDataSource> {
        CalendarEventRemoteDataSourceImpl(
            calendarApi = get()
        )
    }

    single<CalendarEventLocalDatasource> { CalendarEventLocalDatasource() }

    single { CalendarRepository(remote = get(), local = get()) }
}