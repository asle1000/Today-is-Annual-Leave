package com.dayoff.feature.leave_usage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.core.model.year_management.YearManagementInfo
import com.dayoff.data.repository.AnnualLeaveRecord
import com.dayoff.data.repository.AnnualLeaveRepository
import com.dayoff.data.repository.CalendarRepository
import com.dayoff.data.repository.YearManagementRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.YearMonth

/**
 *  Created by KyunghyunPark at 2025. 8. 9.

 */
class LeaveRegistrationViewModel(
    val handle: SavedStateHandle,
    val calendarRepository: CalendarRepository,
    val yearManagementRepository: YearManagementRepository,
    val annualLeaveRepository: AnnualLeaveRepository,
) : ViewModel() {

    private val _yearMonth = MutableStateFlow(YearMonth.now())
    val yearMonth: StateFlow<YearMonth> = _yearMonth.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val calendarEvents: StateFlow<List<CalendarDay>> = _yearMonth.flatMapLatest { ym ->
        calendarRepository.getCalendarEventsByYear(ym.year, ym.monthValue)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun onYearMonthChanged(year: Int, month: Int) {
        _yearMonth.value = YearMonth.of(year, month)
    }

    fun observeYearManagementInfo(): Flow<List<YearManagementInfo>> {
        return yearManagementRepository.observeYearManagementInfo()
    }

    suspend fun registerAnnualLeave(record: AnnualLeaveRecord): Result<Long> {
        return runCatching {
            return@runCatching annualLeaveRepository.registerAnnualLeave(record = record)
        }
    }
}