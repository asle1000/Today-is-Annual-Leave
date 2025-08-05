package com.dayoff.data.repository

import com.dayoff.core.db.entity.YearManagementEntity
import com.dayoff.core.model.year_management.YearManagementInfo
import com.dayoff.data.datasource.YearManagementLocalDataSource
import com.dayoff.data.mapper.YearManagementMapper.toInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 *  Created by KyunghyunPark at 2025. 7. 29.

 */
class YearManagementRepositoryImpl(
    private val yearManagementLocalDataSource: YearManagementLocalDataSource
) : YearManagementRepository {

    override suspend fun registerAnnualYear(
        annualLeaveYear: Int,
        hireYear: Int,
        totalAnnualLeave: Int
    ): Result<Unit> = runCatching {
        val entity = YearManagementEntity(
            annualLeaveYear = annualLeaveYear,
            hireYear = hireYear,
            totalAnnualLeave = totalAnnualLeave,
            usedAnnualLeave = 0,
            createdDate = System.currentTimeMillis(),
            modifiedDate = System.currentTimeMillis()
        )

        yearManagementLocalDataSource.insert(entity = entity)
    }

    override suspend fun getYear(year: Int): YearManagementInfo? {
        return yearManagementLocalDataSource.getByAnnualLeaveYear(year = year)?.toInfo()
    }

    override suspend fun getAllYears(): List<YearManagementInfo> {
        return yearManagementLocalDataSource.getAll().map {
            it.toInfo()
        }
    }

    override fun observeYearManagementInfo(): Flow<List<YearManagementInfo>> {
        return yearManagementLocalDataSource.observeAll().map { list ->
            list.map {
                it.toInfo()
            }
        }
    }
}
