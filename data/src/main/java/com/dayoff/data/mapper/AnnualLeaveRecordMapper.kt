package com.dayoff.data.mapper

import com.dayoff.core.db.entity.AnnualLeaveRecordEntity
import com.dayoff.data.repository.AnnualLeaveRecord
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 *  Created by KyunghyunPark at 2025. 9. 1.

 */
object AnnualLeaveRecordMapper {
    private val BASIC_YMD: DateTimeFormatter = DateTimeFormatter.BASIC_ISO_DATE
    private fun Int.toLocalDate(): LocalDate = LocalDate.parse(this.toString(), BASIC_YMD)

    fun AnnualLeaveRecordEntity.toDto(): AnnualLeaveRecord = AnnualLeaveRecord(
        id = id,
        startYmd = startDate,
        endYmd = endDate,
        minutes = minutes.takeIf { it != 0 },
        isConsumed = isConsumed,
        type = type,
        memo = memo,
        modifiedAt = modifiedAt ?: System.currentTimeMillis()
    )

    fun AnnualLeaveRecord.toEntity(): AnnualLeaveRecordEntity =
        AnnualLeaveRecordEntity(
            id = id, // 신규 insert면 0L 권장(Room autoGenerate)
            type = type,
            startDate = startYmd,
            endDate = endYmd,
            minutes = (minutes ?: 0).coerceAtLeast(0),
            isConsumed = isConsumed,
            memo = memo,
            modifiedAt = modifiedAt,
            createAt = null
        )

    fun AnnualLeaveRecord.totalMinutes(): Int {
        val start = startYmd.toLocalDate()
        val end = endYmd.toLocalDate()
        val daysInclusive = ChronoUnit.DAYS.between(start, end).toInt() + 1
        return daysInclusive * 8 * 60 + (minutes ?: 0)
    }
}