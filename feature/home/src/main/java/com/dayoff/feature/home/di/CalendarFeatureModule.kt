package com.dayoff.feature.home.di

import androidx.lifecycle.SavedStateHandle
import com.dayoff.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 *  Created by KyunghyunPark at 2025. 8. 2.

 */
val calendarFeatureModule = module {
    viewModel { (handle: SavedStateHandle) ->
        HomeViewModel(
            handle = handle,
            calendarRepository = get(),
            yearManagementRepository = get(),
            annualLeaveRepository = get(),
        )
    }
}