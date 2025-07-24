package com.dayoff.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

/**
 *  Created by KyunghyunPark at 2025. 7. 23.
 * TODO Navigator mockup frame
 */

fun NavGraphBuilder.appNavGraph(navController: NavHostController) {
    composable(Screen.Splash.route) {

    }

    composable(Screen.Calendar.route) {

    }

    composable(Screen.YearManager.route) {

    }

    composable(Screen.AnnualUse.route) {

    }

    composable(Screen.AnnualUseDetail("{id}").route) {

    }

    composable(Screen.AnnualMemo.route) {

    }
}