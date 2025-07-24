package com.dayoff.feature.splash.di

import androidx.lifecycle.SavedStateHandle
import org.koin.core.module.dsl.viewModel
import com.dayoff.feature.splash.TialSplashViewModel
import org.koin.dsl.module

/**
 *  Created by KyunghyunPark at 2025. 7. 24.

 */
val splashFeatureModule = module {
    viewModel { (handle: SavedStateHandle) ->
        TialSplashViewModel(handle = handle)
    }
}