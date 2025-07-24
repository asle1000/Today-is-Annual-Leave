package com.dayoff.feature.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dayoff.feature.splash.model.AppVersion
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

    companion object {
        private val remoteConfig by lazy {
            Firebase.remoteConfig.apply {
                setConfigSettingsAsync(
                    remoteConfigSettings {
                        minimumFetchIntervalInSeconds = 0
                    })
            }
        }
    }

    init {
        fetchAppVersionFromRemoteConfig()
    }

    private val _appVersionState = MutableStateFlow<AppVersion?>(null)
    val appVersionState: StateFlow<AppVersion?> = _appVersionState

    private fun fetchAppVersionFromRemoteConfig() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if(task.isSuccessful) {
                val json = remoteConfig["APP_LATEST_VERSION"].asString()
                _appVersionState.value = Json.decodeFromString<AppVersion>(json)
            } else {
                Timber.tag("Firebase.RemoteConfig").e("Failed fetch remote config")
            }
        }
    }
}