package com.dayoff.data.repository

/**
 *  Created by KyunghyunPark at 2025. 9. 1.

 */
interface AnnualLeaveRepository {
    suspend fun registerAnnualLeave(record: AnnualLeaveRecord): Long
}