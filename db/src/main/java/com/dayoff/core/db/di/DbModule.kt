package com.dayoff.core.db.di

import androidx.room.Room
import com.dayoff.core.db.CalendarEventDao
import com.dayoff.core.db.TialDatabase
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
} 