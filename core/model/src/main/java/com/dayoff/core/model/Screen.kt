package com.dayoff.core.model

/**
 *  Created by KyunghyunPark at 2025. 7. 23.

 */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Calendar : Screen("calendar")
    data object YearManager : Screen("year_manager")
    data object LeaveUsage : Screen("leave_usage")
    data object AnnualMemo : Screen("annual_memo")
    data class AnnualUseDetail(val id: String) : Screen("annual_use_detail/{id}") {
        companion object {
            fun routeWithId(id: String) = "annual_use_detail/$id"
        }
    }
}