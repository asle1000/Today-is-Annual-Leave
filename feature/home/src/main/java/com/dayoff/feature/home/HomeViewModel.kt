package com.dayoff.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayoff.core.model.AnnualLeaveRecord
import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.core.model.year_management.YearManagementInfo
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
import kotlinx.coroutines.launch
import java.time.YearMonth

class HomeViewModel(
    val handle: SavedStateHandle,
    val calendarRepository: CalendarRepository,
    val yearManagementRepository: YearManagementRepository,
    val annualLeaveRepository: AnnualLeaveRepository,
) : ViewModel() {

    private val _yearMonth = MutableStateFlow(YearMonth.now())
    val yearMonth: StateFlow<YearMonth> = _yearMonth.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val calendarEvents: StateFlow<List<CalendarDay>> = _yearMonth.flatMapLatest { ym ->
        calendarRepository.getCalendarEventsByYear(
            year = ym.year,
            month = ym.monthValue,
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val leaveRecords: StateFlow<List<AnnualLeaveRecord>> = _yearMonth.flatMapLatest { ym ->
        annualLeaveRepository.observeAnnualLeaveRecordList(year = yearMonth.value.year)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun onYearMonthChanged(year: Int, month: Int) {
        _yearMonth.value = YearMonth.of(year, month)
    }

    fun fetchCalendarEvents(year: Int) = viewModelScope.launch {
        calendarRepository.fetchCalendarEvents(year = year)
    }

    fun observeYearManagementInfo(): Flow<List<YearManagementInfo>> {
        return yearManagementRepository.observeYearManagementInfo()
    }

    suspend fun observeRegisteredAnnualLeaveRecordList(): Flow<List<AnnualLeaveRecord>> {
        return annualLeaveRepository.observeAnnualLeaveRecordList(year = yearMonth.value.year)
    }
}