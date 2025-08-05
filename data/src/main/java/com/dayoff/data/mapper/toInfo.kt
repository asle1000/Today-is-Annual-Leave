package com.dayoff.data.mapper

import com.dayoff.core.db.entity.YearManagementEntity
import com.dayoff.core.model.year_management.YearManagementInfo

object YearManagementMapper {
    fun YearManagementEntity.toInfo(): YearManagementInfo {
        return YearManagementInfo(
            id = id,
            annualLeaveYear = annualLeaveYear,
            hireYear = hireYear,
            usedAnnualLeave = usedAnnualLeave,
            totalAnnualLeave = totalAnnualLeave
        )
    }

    fun YearManagementInfo.toEntity(
        createdDate: Long = System.currentTimeMillis(),
        modifiedDate: Long = System.currentTimeMillis()
    ): YearManagementEntity {
        return YearManagementEntity(
            id = id,
            annualLeaveYear = annualLeaveYear,
            hireYear = hireYear,
            usedAnnualLeave = usedAnnualLeave,
            totalAnnualLeave = totalAnnualLeave,
            createdDate = createdDate,
            modifiedDate = modifiedDate
        )
    }
}