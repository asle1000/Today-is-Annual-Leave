package com.dayoff.feature.year_management

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dayoff.core.ui.TialButton
import com.dayoff.core.ui.TialOutlineButton
import com.dayoff.core.ui.basic.BackIconButton
import com.dayoff.core.ui.basic.BasicExposedDropdown
import com.dayoff.core.ui.basic.BasicForm
import com.dayoff.core.ui.basic.BasicTextField
import com.dayoff.core.ui.basic.TopAppBar
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes
import com.dayoff.feature.year_management.ModuleConst.INPUT_ANNUAL_LEAVE_PAGE_INDEX
import com.dayoff.feature.year_management.ModuleConst.PAGE_TOTAL_COUNT
import com.dayoff.feature.year_management.ModuleConst.SELECT_YEAR_PAGE_INDEX
import com.dayoff.feature.year_management.components.HorizontalPagerIndicator
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 *  Created by KyunghyunPark at 2025. 7. 24.

 */

@Composable
fun YearManagementScreen(
    viewModel: YearManagementViewModel,
    onBack: () -> Unit = {},
    onDone: (Int) -> Unit = {},
) {
    val color = LocalTialColors.current
    val uiState by viewModel.uiState
    val pagerState = rememberPagerState(initialPage = uiState.currentPage) { PAGE_TOTAL_COUNT }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.currentPage) {
        coroutineScope.launch(AndroidUiDispatcher.CurrentThread) {
            pagerState.animateScrollToPage(uiState.currentPage)
        }
    }

    Scaffold(
        modifier = Modifier.background(color.background.base.primary),
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = stringResource(R.string.UI_TITLE_APP_BAR),
                prefixContent = { BackIconButton(onClick = onBack) },
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color.background.base.primary)
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            HorizontalPagerIndicator(
                total = pagerState.pageCount,
                current = pagerState.currentPage
            )
            Spacer(modifier = Modifier.height(16.dp))

            HorizontalPager(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                state = pagerState,
                verticalAlignment = Alignment.Top,
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    SELECT_YEAR_PAGE_INDEX -> SelectYearPage(
                        selectedYearOfAnnualLeave = uiState.annualLeaveYear,
                        selectedYearOfHire = uiState.hireYear,
                        annualLeaveYearErrorMessage = uiState.annualLeaveYearErrorMessage,
                        onAnnualLeaveYearSelected = viewModel::updateAnnualLeaveYear,
                        onHireYearSelected = viewModel::updateHireYear,
                    )
                    INPUT_ANNUAL_LEAVE_PAGE_INDEX -> InputAnnualLeavePage(
                        totalAnnualLeave = uiState.totalAnnualLeave.toString(),
                        onAnnualLeaveYearChanged = viewModel::updateTotalAnnualLeave,
                    )
                    else -> throw IllegalStateException("Unsupported page index: $page")
                }
            }

            BottomButtons(
                enabledPreviousButton = pagerState.currentPage == INPUT_ANNUAL_LEAVE_PAGE_INDEX,
                onPrevious = viewModel::onPreviousStep,
                onNextOrDone = {
                    if (pagerState.currentPage == SELECT_YEAR_PAGE_INDEX) {
                        viewModel.onNextStep()
                    } else {
                        onDone(uiState.annualLeaveYear?.toInt() ?: LocalDate.now().year)
                    }
                }
            )
        }
    }
}

@Composable
private fun BottomButtons(
    enabledPreviousButton: Boolean,
    onPrevious: () -> Unit,
    onNextOrDone: () -> Unit,
) {
    Row(modifier = Modifier.padding(vertical = 20.dp)) {
        AnimatedContent(
            targetState = enabledPreviousButton,
            transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(300)) },
            label = "pager_button_animation",
        ) { isActive ->
            if (isActive) {
                TialOutlineButton(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(end = 12.dp),
                    text = stringResource(R.string.BUTTON_PREVIOUS),
                    enabled = true,
                    onClick = onPrevious
                )
            } else {
                Box {}
            }
        }

        TialButton(
            modifier = Modifier.fillMaxWidth(),
            text = if (!enabledPreviousButton) stringResource(R.string.BUTTON_NEXT)
            else stringResource(R.string.BUTTON_CONFIRM),
            enabled = true,
            onClick = onNextOrDone
        )
    }
}


@Composable
fun SelectYearPage(
    modifier: Modifier = Modifier,
    selectedYearOfAnnualLeave: String?,
    selectedYearOfHire: String?,
    annualLeaveYearList: List<String> = listOf("2021", "2022", "2023", "2024", "2025"),
    hireYearList: List<String> = listOf("2021", "2022", "2023", "2024", "2025"),
    annualLeaveYearErrorMessage: String? = null,
    onAnnualLeaveYearSelected: (String?) -> Unit = {},
    onHireYearSelected: (String?) -> Unit = {}
) {
    val color = LocalTialColors.current
    val style = LocalTialTypes.current

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.UI_TITLE_SELECT_YEAR),
            style = style.headlineMedium.copy(color = color.text.surface.primary)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.UI_SUBTITLE_SELECT_YEAR),
            style = style.bodyLarge.copy(color = color.text.surface.tertiary)
        )

        Spacer(modifier = Modifier.height(56.dp))

        BasicForm(
            label = stringResource(R.string.UI_FORM_TITLE_SELECT_ANNUAL_LEAVE_YEAR),
            isRequired = true,
            errorMessage = annualLeaveYearErrorMessage
        ) {
            BasicExposedDropdown(
                modifier = Modifier.fillMaxWidth(),
                hint = stringResource(R.string.UI_FORM_HINT_SELECT_ANNUAL_LEAVE_YEAR),
                items = annualLeaveYearList,
                selectedOption = selectedYearOfAnnualLeave,
                isError = annualLeaveYearErrorMessage != null,
                onItemSelected = onAnnualLeaveYearSelected
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        BasicForm(
            label = stringResource(R.string.UI_FORM_TITLE_SELECT_HIRE_YEAR),
            infoMessage = stringResource(R.string.UI_FORM_INFO_MESSAGE_SELECT_HIRE_YEAR)
        ) {
            BasicExposedDropdown(
                modifier = Modifier.fillMaxWidth(),
                hint = stringResource(R.string.UI_FORM_HINT_SELECT_HIRE_YEAR),
                items = hireYearList,
                selectedOption = selectedYearOfHire,
                onItemSelected = onHireYearSelected
            )
        }
    }
}

@Composable
fun InputAnnualLeavePage(
    modifier: Modifier = Modifier,
    totalAnnualLeave: String,
    onAnnualLeaveYearChanged: (Int) -> Unit = {}
) {
    val color = LocalTialColors.current
    val style = LocalTialTypes.current

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.UI_TITLE_INPUT_ANNUAL_LEAVE_YEAR),
            style = style.headlineMedium.copy(color = color.text.surface.primary)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.UI_SUBTITLE_INPUT_ANNUAL_LEAVE_YEAR),
            style = style.bodyLarge.copy(color = color.text.surface.tertiary)
        )

        Spacer(modifier = Modifier.height(56.dp))

        BasicForm(
            label = stringResource(R.string.UI_FORM_TITLE_INPUT_ANNUAL_LEAVE_YEAR),
            isRequired = true
        ) {
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = totalAnnualLeave,
                onValueChange = {
                    if (it.length <= 2) onAnnualLeaveYearChanged(it.toIntOrNull() ?: 0)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = stringResource(R.string.UI_FORM_HINT_INPUT_ANNUAL_LEAVE_YEAR)
            )
        }
    }
}
