package com.dayoff.manager

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.data.repository.CalendarRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.YearMonth

/**
 *  Created by KyunghyunPark at 2025. 7. 20.

 */

// TODO viewModelModule 임시
class MainViewModel(
    val handle: SavedStateHandle,
    val calendarRepo: CalendarRepository
) : ViewModel() {

    private val _yearMonth = MutableStateFlow(YearMonth.now())
    val yearMonth: StateFlow<YearMonth> = _yearMonth.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val calendarEvents: StateFlow<List<CalendarDay>> = _yearMonth
        .flatMapLatest { ym ->
            calendarRepo.getCalendarEventsByYear(ym.year, ym.monthValue)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun onYearMonthChanged(year: Int, month: Int) {
        _yearMonth.value = YearMonth.of(year, month)
    }
}