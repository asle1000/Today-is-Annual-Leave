package com.dayoff.feature.year_management

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dayoff.feature.year_management.model.YearManagementUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.min

/**
 *  Created by KyunghyunPark at 2025. 7. 27.

 */
class YearManagementViewModel : ViewModel() {

    private val _uiState: MutableState<YearManagementUiState> = mutableStateOf(YearManagementUiState())
    val uiState: State<YearManagementUiState> = _uiState

    fun updateAnnualLeaveYear(year: String?) {
        _uiState.value = _uiState.value.copy(
            annualLeaveYear = year,
            annualLeaveYearErrorMessage = null
        )
    }

    fun updateHireYear(year: String?) {
        _uiState.value = _uiState.value.copy(hireYear = year)
    }

    fun updateTotalAnnualLeave(count: Int) {
        _uiState.value = _uiState.value.copy(totalAnnualLeave = count)
    }

    fun updateCurrentPage(page: Int) {
        _uiState.value = _uiState.value.copy(currentPage = page)
    }

    fun updateAnnualLeaveYearError(message: String?) {
        _uiState.value = _uiState.value.copy(annualLeaveYearErrorMessage = message)
    }

    fun onPreviousStep() {
        updateCurrentPage(ModuleConst.SELECT_YEAR_PAGE_INDEX)
    }

    fun onNextStep() {
        val state = _uiState.value

        val annualLeaveYear = state.annualLeaveYear?.toIntOrNull()
        val hireYear = state.hireYear?.toIntOrNull() ?: LocalDate.now().year

        if (annualLeaveYear == null) {
            updateAnnualLeaveYearError("관리 할 연차 연도를 선택해 주세요.")
            return
        }

        if (state.hireYear == null) {
            updateHireYear(hireYear.toString())
        }

        if (annualLeaveYear < hireYear) {
            updateAnnualLeaveYearError("관리 할 연차 연도가 입사 연도보다 큽니다.")
            return
        }

        val hireDate = LocalDate.of(hireYear, 1, 1)
        val annualLeaveDate = if (annualLeaveYear == LocalDate.now().year) {
            LocalDate.now()
        } else {
            LocalDate.of(annualLeaveYear, 12, 31)
        }

        val calculated = calculateOfficialAnnualLeave(hireDate, annualLeaveDate)
        updateTotalAnnualLeave(calculated)
        updateCurrentPage(ModuleConst.INPUT_ANNUAL_LEAVE_PAGE_INDEX)
    }

    private fun calculateOfficialAnnualLeave(
        hireDate: LocalDate,
        annualLeaveDate: LocalDate = LocalDate.now()
    ): Int {
        val monthsWorked = ChronoUnit.MONTHS.between(hireDate, annualLeaveDate).toInt()
        val yearsWorked = ChronoUnit.YEARS.between(hireDate, annualLeaveDate).toInt()
        val firstAnniversary = hireDate.plusYears(1)

        return if (annualLeaveDate.isBefore(firstAnniversary)) {
            min(monthsWorked, 11)
        } else {
            val additionalDays = (yearsWorked - 1) / 2
            min(15 + additionalDays, 25)
        }
    }
}
