package com.dayoff.data.repository

import com.dayoff.core.db.entity.YearManagementEntity
import com.dayoff.data.datasource.YearManagementLocalDataSource

/**
 *  Created by KyunghyunPark at 2025. 7. 29.

 */
class YearManagementRepositoryImpl(
    private val yearManagementLocalDataSource: YearManagementLocalDataSource
) : YearManagementRepository {

    override suspend fun registerYear(
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

    override suspend fun getYear(year: Int): YearManagementEntity? {
        return yearManagementLocalDataSource.getByAnnualLeaveYear(year = year)
    }

    override suspend fun getAllYears(): List<YearManagementEntity> {
        return yearManagementLocalDataSource.getAll()
    }
}
