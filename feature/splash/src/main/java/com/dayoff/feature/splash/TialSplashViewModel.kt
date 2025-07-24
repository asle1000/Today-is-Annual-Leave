package com.dayoff.feature.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dayoff.feature.splash.model.AppVersion
import com.dayoff.feature.splash.model.SplashNavigation
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
import timber.log.Timber

/**
 *  Created by KyunghyunPark at 2025. 7. 24.
 *
 *  Splash ViewModel
 */
class TialSplashViewModel(
    private val handle: SavedStateHandle,
) : ViewModel() {

    init {
        fetchAppVersionFromRemoteConfig()
    }

    private val remoteConfig = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(
            remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            })
    }

    private val _navigationState = MutableStateFlow<SplashNavigation?>(null)
    val navigationState: StateFlow<SplashNavigation?> = _navigationState

    private fun fetchAppVersionFromRemoteConfig() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                try {
                    val json = remoteConfig["APP_LATEST_VERSION"].asString()
                    val appVersion = Json.decodeFromString<AppVersion>(json)

                    checkVersionAndNavigate(appVersion = appVersion)

                } catch (e: Exception) {
                    Timber.tag("Firebase.RemoteConfig").e(e, "JSON 파싱 실패")
                    _navigationState.value = SplashNavigation.Error
                }
            } else {
                Timber.tag("Firebase.RemoteConfig").e("Failed fetch remote config")
                _navigationState.value = SplashNavigation.Error
            }
        }
    }

    private fun checkVersionAndNavigate(appVersion: AppVersion) {
        if (BuildConfig.APP_VERSION_CODE < appVersion.appVersionCode) {
            _navigationState.value = SplashNavigation.Update
        } else {
            _navigationState.value = SplashNavigation.Home
        }
    }
}