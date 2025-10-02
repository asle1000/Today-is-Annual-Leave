package com.dayoff.data.mapper

import com.dayoff.core.db.entity.AnnualLeaveRecordEntity
import com.dayoff.core.db.entity.HashtagEntity
import com.dayoff.core.db.entity.LeaveRecordWithHashtags
import com.dayoff.core.model.AnnualLeaveRecord
import com.dayoff.core.model.Hashtag
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 *  Created by KyunghyunPark at 2025. 9. 1.

 */
object AnnualLeaveRecordMapper {
    private val BASIC_YMD: DateTimeFormatter = DateTimeFormatter.BASIC_ISO_DATE
    private fun Int.toLocalDate(): LocalDate = LocalDate.parse(this.toString(), BASIC_YMD)

    fun LeaveRecordWithHashtags.toDto(): AnnualLeaveRecord {
        return AnnualLeaveRecord(
            id = record.id,
            startYmd = record.startDate,
            endYmd = record.endDate,
            minutes = record.minutes.takeIf { it != 0 },
            isConsumed = record.isConsumed,
            type = record.type,
            memo = record.memo,
            hashTags = hashtags.map {
                it.toDto()
            },
            modifiedAt = record.modifiedAt ?: System.currentTimeMillis()
        )
    }

    fun AnnualLeaveRecord.toEntity(): AnnualLeaveRecordEntity =
        AnnualLeaveRecordEntity(
            id = id,
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

    fun HashtagEntity.toDto(): Hashtag = Hashtag(
        id = id,
        category = category,
        emoji = emoji,
        name = name
    )

    fun Hashtag.toEntity(): HashtagEntity = HashtagEntity(
        id = id,
        category = category,
        emoji = emoji,
        name = name,
    )
}