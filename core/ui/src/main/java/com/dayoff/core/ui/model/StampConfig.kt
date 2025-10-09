package com.dayoff.core.ui.model

/**
 * Stamp config
 *
 * @property usedHoursStampCnt 사용한 시간(hour) 스탬프 개수
 * @property hasMinutesStamp 분(minute) 스탬프 존재 여부
 * @property countPerPage 페이지당 스탬프 개수
 * @property pageCount 전체 페이지 수
 * @property columns 열 개수
 * @property rows 행 개수
 * @constructor Create empty Stamp config
 */
data class StampConfig(
    val usedHoursStampCnt: Int,
    val hasMinutesStamp: Boolean,
    val countPerPage: Int,
    val pageCount: Int,
    val columns: Int,
    val rows: Int
)