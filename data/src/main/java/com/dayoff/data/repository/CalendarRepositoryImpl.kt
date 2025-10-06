package com.dayoff.data.repository

import com.dayoff.core.db.entity.CalendarEventEntity
import com.dayoff.core.db.model.CalendarEventType
import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.core.model.calendar.DayOfWeek
import com.dayoff.core.network.model.CalendarEventDto
import com.dayoff.data.datasource.AnnualLeaveLocalDataSource
import com.dayoff.data.datasource.CalendarEventLocalDatasource
import com.dayoff.data.datasource.CalendarEventRemoteDataSource
import com.dayoff.data.mapper.AnnualLeaveRecordMapper.toDto
import com.dayoff.data.mapper.CalendarEventMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth

class CalendarRepositoryImpl(
    private val calendarEventRemoteDataSource: CalendarEventRemoteDataSource,
    private val calendarEventLocalDatasource: CalendarEventLocalDatasource,
    private val annualLeaveLocalDataSource: AnnualLeaveLocalDataSource,
) : CalendarRepository {
    /**
     * TODO Error handling
     * Fetch remote month days
     *
     * @param year
     * @return
     */
    override suspend fun fetchCalendarEvents(year: Int): List<CalendarEventDto> {
        val response = calendarEventRemoteDataSource.fetchCalendarEvent(year = year)
        calendarEventLocalDatasource.updateCalendarEventsFromYear(
            year = year, yearEvents = response
        )
        Timber.d("fetchCalendarEvents: \n${response.joinToString("\n")}")
        return response
    }

    override fun getCalendarEventsByYear(year: Int, month: Int): Flow<List<CalendarDay>> {
        val firstDayOfMonth = YearMonth.of(year, month).atDay(1)
        val lastDayOfMonth = YearMonth.of(year, month).atEndOfMonth()

        val annualLeaveFlow = annualLeaveLocalDataSource.observeByMonth(year = year, month = month)
        val calendarEventFlow = calendarEventLocalDatasource.observeCalendarEventsByYear(year = year)

        return combine(annualLeaveFlow, calendarEventFlow) { annualLeaveEntities, calendarEventEntities ->
            val existingMonthlyEvents: List<CalendarEventEntity> = calendarEventEntities

            val convertedAnnualLeaveEvents = buildList {
                annualLeaveEntities.forEach { annualLeaveEntity ->
                    val annualLeaveRecord = annualLeaveEntity.toDto()

                    Timber.d("[TEST] $annualLeaveRecord")

                    val originalStartDate = annualLeaveRecord.startYmd.toLocalDate()
                    val originalEndDate = annualLeaveRecord.endYmd.toLocalDate()

                    val startDateInclusive = maxOf(a = originalStartDate, b = firstDayOfMonth)
                    val endDateInclusive = minOf(a = originalEndDate, b = lastDayOfMonth)

                    if (startDateInclusive.isAfter(endDateInclusive)) return@forEach

                    var currentDate = startDateInclusive

                    while (!currentDate.isAfter(endDateInclusive)) {
                        add(
                            CalendarEventEntity(
                                name = annualLeaveRecord.type.name,
                                year = currentDate.year,
                                month = currentDate.monthValue,
                                day = currentDate.dayOfMonth,
                                type = CalendarEventType.ANNUAL_LEAVE,
                                isHoliday = false
                            )
                        )
                        currentDate = currentDate.plusDays(1)
                    }
                }
            }

            val mergedEventEntities: List<CalendarEventEntity> = existingMonthlyEvents + convertedAnnualLeaveEvents

            CalendarEventMapper.mapEntitiesToCalendarDays(
                year = year,
                month = month,
                eventEntities = mergedEventEntities,
                startDayOfWeek = DayOfWeek.MONDAY
            )
        }
    }

    private fun Int.toLocalDate(): LocalDate {
        val year = this / 10000
        val month = (this / 100) % 100
        val day = this % 100
        return LocalDate.of(year, month, day)
    }
} 