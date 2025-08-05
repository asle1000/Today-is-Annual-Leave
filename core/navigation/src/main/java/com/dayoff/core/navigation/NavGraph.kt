package com.dayoff.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dayoff.core.model.Screen.*
import com.dayoff.feature.calendar.CalendarScreen
import com.dayoff.feature.splash.TialSplashScreen
import com.dayoff.feature.year_management.YearManagementScreen

/**
 *  Created by KyunghyunPark at 2025. 7. 23.
 * TODO Navigator mockup frame
 */

fun NavGraphBuilder.appNavGraph(navController: NavHostController) {
    composable(route = Splash.route) {
        TialSplashScreen {
            navController.navigate(route = Calendar.route) {
                popUpTo(Splash.route) {
                    inclusive = true
                }
            }
        }
    }

    composable(Calendar.route) {
        CalendarScreen(onNavigate = { screen ->
            navController.navigate(route = screen.route)
        })

    }

    composable(route = YearManager.route) {
        YearManagementScreen(onBack = {
            navController.popBackStack()
        }, onDone = {
            navController.popBackStack()
        })
    }

    composable(AnnualUse.route) {

    }

    composable(AnnualUseDetail("{id}").route) {

    }

    composable(AnnualMemo.route) {

    }
}