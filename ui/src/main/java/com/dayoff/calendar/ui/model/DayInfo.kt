package com.dayoff.calendar.ui.model

import java.time.LocalDate

/**
 * 날짜 셀 정보
 *
 * @property date 날짜
 * @property isHoliday 휴일 여부
 * @property isAlternativeHoliday 대체 휴일 여부
 * @property isAnnualLeave 연차 여부
 * @property isAnnualLeaveStart 연차 시작일 여부
 * @property isAnnualLeaveEnd 연차 종료일 여부
 * @property isAnnualLeaveSingle 단일 연차 여부
 * @property isRecommendedDay 추천 픽 여부
 */
data class DayInfo(
    val date: LocalDate,
    val isHoliday: Boolean = false,
    val isAlternativeHoliday: Boolean = false,
    val isAnnualLeave: Boolean = false,
    val isAnnualLeaveStart: Boolean = false,
    val isAnnualLeaveEnd: Boolean = false,
    val isAnnualLeaveSingle: Boolean = false,
    val isRecommendedDay: Boolean = false
)