package com.dayoff.feature.splash.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppVersion(
    @SerialName("appVersionCode")
    val appVersionCode: Int,
    @SerialName("appVersionName")
    val appVersionName: String
)