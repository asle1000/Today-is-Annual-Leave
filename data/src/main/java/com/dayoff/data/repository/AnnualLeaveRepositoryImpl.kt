package com.dayoff.data.repository

import HashtagCategory
import com.dayoff.core.model.calendar.AnnualLeaveType
import com.dayoff.data.datasource.AnnualLeaveLocalDataSource
import com.dayoff.data.mapper.AnnualLeaveRecordMapper.toEntity
import com.dayoff.data.util.DispatcherProvider
import kotlinx.coroutines.withContext

/**
 *  Created by KyunghyunPark at 2025. 9. 1.

 */
class AnnualLeaveRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val annualLeaveLocalDataSource: AnnualLeaveLocalDataSource
) : AnnualLeaveRepository {
    override suspend fun registerAnnualLeave(record: AnnualLeaveRecord): Long {
        return withContext(dispatcherProvider.io) {
            return@withContext annualLeaveLocalDataSource.insert(entity = record.toEntity())
        }
    }
}

data class AnnualLeaveRecord(
    val id: Long,
    val startYmd: Int,
    val endYmd: Int,
    val minutes: Int?,
    val isConsumed: Boolean,
    val type: AnnualLeaveType,
    val memo: String? = null,
    val hashTags: List<Hashtag> ?= emptyList(),
    val modifiedAt: Long,
)

data class Hashtag(
    val id: Long,
    val category: HashtagCategory,
    val emoji: String,
    val name: String,
)