package com.dayoff.feature.year_management.model

/**
 *  Created by KyunghyunPark at 2025. 7. 27.

 */
data class YearManagementUiState(
    val annualLeaveYear: String? = null,
    val hireYear: String? = null,
    val totalAnnualLeave: Int = 15,
    val currentPage: Int = 0,
    val annualLeaveYearErrorMessage : String? = null,
)
