package com.dayoff.core.model.calendar

import com.dayoff.core.model.util.ListOrSingleSerializer
import kotlinx.serialization.Serializable

/**
 * 월별 특일 정보 응답 모델
 *
 * @property month 조회 대상 월 (01 ~ 12)
 * @property restHolidays 공휴일 정보 (getRestDeInfo)
 * @property anniversaries 기념일 정보 (getAnniversaryInfo)
 * @property divisions24 24절기 정보 (get24DivisionsInfo)
 * @property sundryDays 잡절 정보 (getSundryDayInfo)
 */
@Serializable
data class CalendarEventData(
    val month: String,
    val restHolidays: RestHolidays? = null,
    val anniversaries: Anniversaries? = null,
    val divisions24: Divisions24? = null,
    val sundryDays: SundryDays? = null
)

/**
 * 공휴일 응답 래퍼
 *
 * @property response 공휴일 응답 데이터
 */
@Serializable
data class RestHolidays(val response: ApiResponse<RestHolidayBody>? = null)

/**
 * 기념일 응답 래퍼
 *
 * @property response 기념일 응답 데이터
 */
@Serializable
data class Anniversaries(val response: ApiResponse<AnniversaryBody>? = null)

/**
 * 24절기 응답 래퍼
 *
 * @property response 24절기 응답 데이터
 */
@Serializable
data class Divisions24(val response: ApiResponse<DivisionBody>? = null)

/**
 * 잡절 응답 래퍼
 *
 * @property response 잡절 응답 데이터
 */
@Serializable
data class SundryDays(val response: ApiResponse<SundryBody>? = null)

/**
 * 공통 응답 포맷
 *
 * @property header 결과 코드 및 메시지
 * @property body 실제 응답 데이터
 */
@Serializable
data class ApiResponse<T>(
    val header: Header,
    val body: T
)

/**
 * 응답 헤더 정보
 *
 * @property resultCode 결과 코드 ("00" = 성공)
 * @property resultMsg 결과 메시지 ("NORMAL SERVICE.")
 */
@Serializable
data class Header(
    val resultCode: String,
    val resultMsg: String
)

/**
 * 공휴일 본문 데이터
 *
 * @property pageNo 현재 페이지 번호
 * @property totalCount 전체 항목 수
 * @property numOfRows 페이지당 항목 수
 * @property items 공휴일 항목 리스트
 */
@Serializable
data class RestHolidayBody(
    val pageNo: Int,
    val totalCount: Int,
    val numOfRows: Int,
    val items: Items<HolidayItem>? = null
)

/**
 * 기념일 본문 데이터
 */
@Serializable
data class AnniversaryBody(
    val pageNo: Int,
    val totalCount: Int,
    val numOfRows: Int,
    val items: Items<AnniversaryItem>? = null
)

/**
 * 24절기 본문 데이터
 */
@Serializable
data class DivisionBody(
    val pageNo: Int,
    val totalCount: Int,
    val numOfRows: Int,
    val items: Items<DivisionItem>? = null
)

/**
 * 잡절 본문 데이터
 */
@Serializable
data class SundryBody(
    val pageNo: Int,
    val totalCount: Int,
    val numOfRows: Int,
    val items: Items<SundryItem>? = null
)

/**
 * item 필드가 단일 객체 또는 배열로 올 수 있는 항목 리스트 포맷
 *
 * @property item 실제 특일 항목 목록
 */
@Serializable
data class Items<T>(
    @Serializable(with = ListOrSingleSerializer::class)
    val item: List<T>? = null
)

/**
 * 공휴일 항목
 *
 * @property dateKind 날짜 종류 코드 ("01" = 국경일)
 * @property dateName 날짜 이름 (예: 삼일절)
 * @property isHoliday 공공기관 휴일 여부 ("Y" or "N")
 * @property locdate 날짜 (예: 20240301)
 * @property seq 항목 순번
 */
@Serializable
data class HolidayItem(
    val dateKind: String,
    val dateName: String,
    val isHoliday: String,
    val locdate: Long,
    val seq: Int
)

/**
 * 기념일 항목
 *
 * @property dateKind 날짜 종류 코드 ("02" = 기념일)
 */
@Serializable
data class AnniversaryItem(
    val dateKind: String,
    val dateName: String,
    val isHoliday: String,
    val locdate: Long,
    val seq: Int
)

/**
 * 24절기 항목
 *
 * @property kst 한국표준시 시각 (예: "0610")
 * @property sunLongitude 태양황경 (예: 345)
 */
@Serializable
data class DivisionItem(
    val dateKind: String,
    val dateName: String,
    val isHoliday: String,
    val locdate: Long,
    val kst: String,
    val sunLongitude: Int? = null,
    val seq: Int
)

/**
 * 잡절 항목
 *
 * @property kst 한국표준시 시각 (선택적)
 * @property sunLongitude 태양황경 (선택적)
 */
@Serializable
data class SundryItem(
    val dateKind: String,
    val dateName: String,
    val isHoliday: String,
    val locdate: Long,
    val kst: String? = null,
    val sunLongitude: Int? = null,
    val seq: Int
)
