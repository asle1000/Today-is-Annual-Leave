package com.dayoff.core.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dayoff.core.model.Screen.AnnualMemo
import com.dayoff.core.model.Screen.AnnualUseDetail
import com.dayoff.core.model.Screen.Calendar
import com.dayoff.core.model.Screen.LeaveUsage
import com.dayoff.core.model.Screen.Splash
import com.dayoff.core.model.Screen.YearManager
import com.dayoff.feature.home.HomeScreen
import com.dayoff.feature.leave_usage.LeaveRegistrationScreen
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
        HomeScreen(onNavigate = { screen ->
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

    composable(LeaveUsage.route) {
        LeaveRegistrationScreen(onBack = {
            navController.popBackStack()
        }, onDone = {
            navController.popBackStack()
        })
    }

    composable(AnnualUseDetail("{id}").route) {

    }

    composable(AnnualMemo.route) {

    }
}