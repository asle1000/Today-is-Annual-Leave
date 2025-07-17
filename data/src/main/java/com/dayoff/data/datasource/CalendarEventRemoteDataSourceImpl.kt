package com.dayoff.data.datasource


import com.dayoff.core.network.api.CalendarApi
import timber.log.Timber

/**
 *  Created by KyunghyunPark at 2025. 7. 13.

 */
class CalendarEventRemoteDataSourceImpl(
    private val calendarApi: CalendarApi
): CalendarEventRemoteDataSource {



    override suspend fun fetchCalendarEvent() {
        val a = calendarApi.fetchCalendarEventList(2025)

        Timber.d("""
            [TEST] RES
            ${a.toList().joinToString("\n")}
           
        """.trimIndent())



    }

}