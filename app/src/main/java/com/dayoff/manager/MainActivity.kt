package com.dayoff.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dayoff.core.model.Screen
import com.dayoff.core.navigation.appNavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            val isDark = isSystemInDarkTheme()

            SideEffect {
                window.statusBarColor = Color.Transparent.toArgb()
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
            }

            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route
            ) {
                appNavGraph(navController = navController)
            }
        }
    }
}
