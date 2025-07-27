package com.dayoff.core.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dayoff.feature.splash.TialSplashScreen
import com.dayoff.feature.year_management.YearManagementScreen
import com.dayoff.feature.year_management.YearManagementViewModel

/**
 *  Created by KyunghyunPark at 2025. 7. 23.
 * TODO Navigator mockup frame
 */

fun NavGraphBuilder.appNavGraph(navController: NavHostController) {
    composable(Screen.Splash.route) {
        TialSplashScreen {
            navController.navigate(route = Screen.YearManager.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
    }

    composable(Screen.Calendar.route) {

    }

    composable(Screen.YearManager.route) {
        val viewModel = viewModel<YearManagementViewModel>()
        YearManagementScreen(viewModel = viewModel, onBack = {}, onDone = {})
    }

    composable(Screen.AnnualUse.route) {

    }

    composable(Screen.AnnualUseDetail("{id}").route) {

    }

    composable(Screen.AnnualMemo.route) {

    }
}