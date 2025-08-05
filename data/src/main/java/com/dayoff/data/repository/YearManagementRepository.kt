package com.dayoff.data.repository

import com.dayoff.core.model.year_management.YearManagementInfo
import kotlinx.coroutines.flow.Flow

/**
 *  Created by KyunghyunPark at 2025. 7. 29.

 */
interface YearManagementRepository {
    suspend fun registerAnnualYear(
        annualLeaveYear: Int,
        hireYear: Int,
        totalAnnualLeave: Int
    ): Result<Unit>

    suspend fun getYear(year: Int): YearManagementInfo?

    suspend fun getAllYears(): List<YearManagementInfo>
    fun observeYearManagementInfo(): Flow<List<YearManagementInfo>>
}