package com.dayoff.data.repository

import com.dayoff.core.model.AnnualLeaveRecord
import kotlinx.coroutines.flow.Flow

/**
 *  Created by KyunghyunPark at 2025. 9. 1.

 */
interface AnnualLeaveRepository {
    suspend fun registerAnnualLeave(record: AnnualLeaveRecord): Long
    suspend fun observeAnnualLeaveRecordList(year: Int): Flow<List<AnnualLeaveRecord>>
}