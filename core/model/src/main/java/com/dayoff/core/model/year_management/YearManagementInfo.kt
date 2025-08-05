package com.dayoff.core.model.year_management

/**
 *  Created by KyunghyunPark at 2025. 8. 3.

 */
data class YearManagementInfo(
    val id: Long = 0L,
    val annualLeaveYear: Int, // 연차 부여 연도
    val hireYear: Int, // 입사 연도
    val usedAnnualLeave: Int = 0, // 사용한 연차 (일수)
    val totalAnnualLeave: Int, // 총 사용 가능 연차 (일수)
)