package com.dayoff.data.repository

import HashtagCategory
import com.dayoff.core.db.entity.LeaveRecordWithHashtags
import com.dayoff.core.model.AnnualLeaveRecord
import com.dayoff.core.model.calendar.AnnualLeaveType
import com.dayoff.data.datasource.AnnualLeaveLocalDataSource
import com.dayoff.data.mapper.AnnualLeaveRecordMapper
import com.dayoff.data.mapper.AnnualLeaveRecordMapper.toDto
import com.dayoff.data.mapper.AnnualLeaveRecordMapper.toEntity
import com.dayoff.data.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
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

    override suspend fun observeAnnualLeaveRecordList(year: Int): Flow<List<AnnualLeaveRecord>> {
        return withContext(dispatcherProvider.io) {
            annualLeaveLocalDataSource.observeByYear(year = year).transform { records ->
                emit(records.map { it.toDto() })
            }
        }
    }
}
