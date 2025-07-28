package com.dayoff.data.di

import com.dayoff.data.datasource.CalendarEventLocalDatasource
import com.dayoff.data.datasource.CalendarEventRemoteDataSource
import com.dayoff.data.datasource.CalendarEventRemoteDataSourceImpl
import com.dayoff.data.repository.CalendarEventRepositoryImpl
import com.dayoff.core.network.api.CalendarApi
import com.dayoff.data.datasource.YearManagementLocalDataSource
import com.dayoff.data.datasource.YearManagementLocalDataSourceImpl
import com.dayoff.data.repository.CalendarEventRepository
import com.dayoff.data.repository.YearManagementRepository
import com.dayoff.data.repository.YearManagementRepositoryImpl
import org.koin.dsl.module

/**
 *  Created by KyunghyunPark at 2025. 7. 14.
 *
 */
val dataModule = module {
    single<CalendarApi> { CalendarApi() }

    single<CalendarEventRemoteDataSource> { CalendarEventRemoteDataSourceImpl(calendarApi = get()) }

    single<CalendarEventLocalDatasource> { CalendarEventLocalDatasource(calendarEventDao = get()) }

    single<YearManagementLocalDataSource> { YearManagementLocalDataSourceImpl(yearManagementDao = get()) }

    single<YearManagementRepository> { YearManagementRepositoryImpl(yearManagementLocalDataSource = get()) }

    single<CalendarEventRepository> { CalendarEventRepositoryImpl(calendarEventRemoteDataSource = get(), calendarEventLocalDatasource = get()) }
}