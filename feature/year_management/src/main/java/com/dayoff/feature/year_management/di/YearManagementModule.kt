package com.dayoff.feature.year_management.di

import androidx.lifecycle.SavedStateHandle
import com.dayoff.feature.year_management.YearManagementViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 *  Created by KyunghyunPark at 2025. 7. 24.

 */
val yearManagementFeatureModule = module {
    viewModel { (_: SavedStateHandle) ->
        YearManagementViewModel()
    }
}