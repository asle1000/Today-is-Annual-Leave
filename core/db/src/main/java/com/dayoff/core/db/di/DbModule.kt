package com.dayoff.core.db.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dayoff.core.db.dao.CalendarEventDao
import com.dayoff.core.db.TialDatabase
import com.dayoff.core.db.dao.AnnualLeaveRecordDao
import com.dayoff.core.db.dao.YearManagementDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = TialDatabase::class.java,
            name = "tial_database",
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                createTriggers(db)
            }
        }

        ).build()
    }

    factory<CalendarEventDao> { get<TialDatabase>().calendarEventDao() }

    factory<YearManagementDao> { get<TialDatabase>().yearManagementDao() }

    factory<AnnualLeaveRecordDao> { get<TialDatabase>().annualLeaveRecordDao() }
}

fun createTriggers(db: SupportSQLiteDatabase) {
    db.execSQL(
        """
        CREATE TRIGGER IF NOT EXISTS trg_leave_check_range_insert
        BEFORE INSERT ON annual_leave_record
        FOR EACH ROW
        WHEN NEW.startDate > NEW.endDate
        BEGIN SELECT RAISE(ABORT, 'startDate must be <= endDate'); END;
      """.trimIndent()
    )

    db.execSQL(
        """
        CREATE TRIGGER IF NOT EXISTS trg_leave_check_range_update
        BEFORE UPDATE ON annual_leave_record
        FOR EACH ROW
        WHEN NEW.startDate > NEW.endDate
        BEGIN SELECT RAISE(ABORT, 'startDate must be <= endDate'); END;
      """.trimIndent()
    )

    db.execSQL(
        """
        CREATE TRIGGER IF NOT EXISTS trg_leave_no_overlap_insert
        BEFORE INSERT ON annual_leave_record
        FOR EACH ROW
        WHEN EXISTS (
          SELECT 1 FROM annual_leave_record t
          WHERE NOT (t.endDate < NEW.startDate OR t.startDate > NEW.endDate)
        )
        BEGIN SELECT RAISE(ABORT, 'date range overlaps with an existing record'); END;
      """.trimIndent()
    )

    db.execSQL(
        """
        CREATE TRIGGER IF NOT EXISTS trg_leave_no_overlap_update
        BEFORE UPDATE ON annual_leave_record
        FOR EACH ROW
        WHEN EXISTS (
          SELECT 1 FROM annual_leave_record t
          WHERE t.id != NEW.id
            AND NOT (t.endDate < NEW.startDate OR t.startDate > NEW.endDate)
        )
        BEGIN SELECT RAISE(ABORT, 'date range overlaps with an existing record'); END;
      """.trimIndent()
    )
}