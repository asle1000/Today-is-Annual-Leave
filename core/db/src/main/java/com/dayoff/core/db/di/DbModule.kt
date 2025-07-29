package com.dayoff.core.db.di

import androidx.room.Room
import com.dayoff.core.db.dao.CalendarEventDao
import com.dayoff.core.db.TialDatabase
import com.dayoff.core.db.dao.YearManagementDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
                context = androidContext(),
                klass = TialDatabase::class.java,
                name = "tial_database",
            ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }

    factory<CalendarEventDao> { get<TialDatabase>().calendarEventDao() }

    factory<YearManagementDao> { get<TialDatabase>().yearManagementDao() }
} 