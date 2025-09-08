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

        val firstDayOfTargetMonth = YearMonth.of(year, month).atDay(1)
        val lastDayOfTargetMonth = YearMonth.of(year, month).atEndOfMonth()

        val annualLeaveFlow = annualLeaveLocalDataSource.observeByYear(year)
        val calendarEventFlow = calendarEventLocalDatasource.observeCalendarEventsByYear(year)

        return combine(annualLeaveFlow, calendarEventFlow) { annualLeaveEntities, calendarEventEntities ->
            // 1) 기존 캘린더 이벤트: 해당 월만 필터링 (중복 제거 없음)
            val monthlyCalendarEvents: List<CalendarEventEntity> =
                calendarEventEntities.filter { it.year == year && it.month == month }

            // 2) 연차 엔티티를 월 범위로 클램핑하여 날짜별 이벤트로 전개 (중복 제거 없음)
            val monthlyAnnualLeaveEvents = buildList {
                annualLeaveEntities.forEach { annualLeaveEntity ->
                    val annualLeaveRecord = annualLeaveEntity.toDto()

                    val originalStartDate = annualLeaveRecord.startYmd.toLocalDate()
                    val originalEndDate = annualLeaveRecord.endYmd.toLocalDate()

                    val startDateInclusive =
                        if (originalStartDate.isAfter(firstDayOfTargetMonth)) originalStartDate else firstDayOfTargetMonth
                    val endDateInclusive =
                        if (originalEndDate.isBefore(lastDayOfTargetMonth)) originalEndDate else lastDayOfTargetMonth

                    if (startDateInclusive.isAfter(endDateInclusive)) return@forEach // 이 달과 겹치지 않음

                    var currentDate = startDateInclusive
                    while (!currentDate.isAfter(endDateInclusive)) {
                        add(
                            CalendarEventEntity(
                                name = annualLeaveRecord.type.name,
                                year = currentDate.year,
                                month = currentDate.monthValue,
                                day = currentDate.dayOfMonth,
                                type = CalendarEventType.ANNUAL_LEAVE, // 프로젝트 타입에 맞게 교체 가능 (예: ANNUAL_LEAVE)
                                isHoliday = false
                            )
                        )
                        currentDate = currentDate.plusDays(1)
                    }
                }
            }

            // 3) 병합: 기존 이벤트 먼저, 연차 이벤트 나중(= 기존 이벤트 우선 표시)
            val mergedEventEntities: List<CalendarEventEntity> = monthlyCalendarEvents + monthlyAnnualLeaveEvents

            // 4) CalendarDay로 변환 (여러 건이 있어도 그대로 전달; 우선순위는 리스트 순서)
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