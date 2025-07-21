package com.dayoff.manager

import androidx.lifecycle.SavedStateHandle
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 *  Created by KyunghyunPark at 2025. 7. 21.
// TODO viewModelModule 임시
 */
val viewModelModule = module {
    viewModel { (handle: SavedStateHandle) ->
        MainViewModel(
            handle = handle,
            calendarRepo = get()
        )
    }
}