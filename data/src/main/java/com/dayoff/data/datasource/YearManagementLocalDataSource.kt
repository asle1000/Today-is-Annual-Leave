package com.dayoff.data.datasource

import com.dayoff.core.db.entity.YearManagementEntity
import kotlinx.coroutines.flow.Flow

/**
 *  Created by KyunghyunPark at 2025. 7. 13.

 */
interface YearManagementLocalDataSource {
    suspend fun insert(entity: YearManagementEntity): Long
    suspend fun update(entity: YearManagementEntity)
    suspend fun delete(entity: YearManagementEntity)
    suspend fun getByAnnualLeaveYear(year: Int): YearManagementEntity?
    suspend fun getAll(): List<YearManagementEntity>
}