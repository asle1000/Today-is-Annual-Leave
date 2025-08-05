package com.dayoff.di

import com.dayoff.core.db.di.dbModule
import com.dayoff.core.network.di.networkModule
import com.dayoff.data.di.dataModule
import com.dayoff.feature.calendar.di.calendarFeatureModule
import com.dayoff.feature.splash.di.splashFeatureModule
import com.dayoff.feature.year_management.di.yearManagementFeatureModule
import org.koin.core.module.Module

/**
 *  Created by KyunghyunPark at 2025. 7. 13.

 */
object AppModules {
    val all: List<Module> = listOf(
        dbModule,
        networkModule,
        dataModule,
        splashFeatureModule,
        calendarFeatureModule,
        yearManagementFeatureModule,
    )
}