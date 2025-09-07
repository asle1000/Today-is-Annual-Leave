package com.dayoff.feature.leave_usage.di

import androidx.lifecycle.SavedStateHandle
import com.dayoff.feature.leave_usage.LeaveRegistrationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 *  Created by KyunghyunPark at 2025. 8. 2.

 */
val leaveUsageFeatureModule = module {
    viewModel { (handle: SavedStateHandle) ->
        LeaveRegistrationViewModel(
            handle = handle,
            calendarRepository = get(),
            yearManagementRepository = get(),
            annualLeaveRepository = get(),
        )
    }
}