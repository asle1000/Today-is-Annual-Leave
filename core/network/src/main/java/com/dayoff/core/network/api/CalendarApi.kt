package com.dayoff.core.network.api

import com.dayoff.core.network.NetworkClient
import com.dayoff.core.network.model.CalendarEventDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 *  Created by KyunghyunPark at 2025. 7. 14.

 */
class CalendarApi {
    private val client = NetworkClient.httpClient
    private val baseUrl = "https://holiday-json-repo.pages.dev"

    suspend fun fetchCalendarEventList(year: Int): List<CalendarEventDto> {
        return client.get("$baseUrl/holidays/$year.json") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}