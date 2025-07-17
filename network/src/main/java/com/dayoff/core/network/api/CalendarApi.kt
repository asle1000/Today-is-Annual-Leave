package com.dayoff.core.network.api

import com.dayoff.core.model.calendar.CalendarEventData
import com.dayoff.core.network.NetworkClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 *  Created by KyunghyunPark at 2025. 7. 14.

 */
class CalendarApi {
    private val client = NetworkClient.httpClient
    private val baseUrl = "https://holiday-json-repo.pages.dev"

    suspend fun fetchCalendarEventList(year: Int): List<CalendarEventData> {
        return client.get("$baseUrl/holidays/$year.json") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}