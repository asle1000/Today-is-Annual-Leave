package com.dayoff.data.repository

import com.dayoff.core.db.entity.YearManagementEntity

/**
 *  Created by KyunghyunPark at 2025. 7. 29.

 */
interface YearManagementRepository {
    suspend fun registerAnnualYear(
        annualLeaveYear: Int,
        hireYear: Int,
        totalAnnualLeave: Int
    ): Result<Unit>

    suspend fun getYear(year: Int): YearManagementEntity?

    suspend fun getAllYears(): List<YearManagementEntity>
}