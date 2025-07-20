package com.dayoff.core.db.di

import androidx.room.Room
import com.dayoff.core.db.CalendarEventDao
import com.dayoff.core.db.TialDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            TialDatabase::class.java,
            "tial_database"
        ).build()
    }
    factory<CalendarEventDao> { get<TialDatabase>().calendarEventDao() }
} 