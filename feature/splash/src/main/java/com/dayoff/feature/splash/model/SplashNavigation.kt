package com.dayoff.feature.splash.model

sealed class SplashNavigation {
    data object Home : SplashNavigation()
    data object Update : SplashNavigation()
    data object Error : SplashNavigation()
}
