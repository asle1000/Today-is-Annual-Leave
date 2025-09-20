package com.dayoff.core.db.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dayoff.core.db.dao.CalendarEventDao
import com.dayoff.core.db.TialDatabase
import com.dayoff.core.db.dao.AnnualLeaveRecordDao
import com.dayoff.core.db.dao.HashtagDao
import com.dayoff.core.db.dao.YearManagementDao
import com.dayoff.core.db.entity.HashtagEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {

    single {
        val appContext = androidContext()

        val roomDb = Room.databaseBuilder(
            context = appContext,
            klass = TialDatabase::class.java,
            name = "tial_database"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                createTriggers(db = db)
            }
        }).build()

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            roomDb.hashtagDao().insertAll(items = makeHashtagTableData())
        }

        roomDb
    }

    factory<CalendarEventDao> { get<TialDatabase>().calendarEventDao() }

    factory<YearManagementDao> { get<TialDatabase>().yearManagementDao() }

    factory<AnnualLeaveRecordDao> { get<TialDatabase>().annualLeaveRecordDao() }

    factory<HashtagDao> { get<TialDatabase>().hashtagDao() }
}

private fun makeHashtagTableData(): List<HashtagEntity> = listOf(
    // 휴식/충전
    HashtagEntity(category = HashtagCategory.REST, emoji = "😴", name = "완전휴식"),
    HashtagEntity(category = HashtagCategory.REST, emoji = "🏠", name = "집콕데이"),
    HashtagEntity(category = HashtagCategory.REST, emoji = "🛏️", name = "늦잠자기"),
    HashtagEntity(category = HashtagCategory.REST, emoji = "🧖️", name = "셀프케어"),
    HashtagEntity(category = HashtagCategory.REST, emoji = "🔋", name = "재충전완료"),
    HashtagEntity(category = HashtagCategory.REST, emoji = "☁️", name = "멍때리기"),

    // 외출/여행
    HashtagEntity(category = HashtagCategory.TRAVEL, emoji = "✈", name = "짧은여행"),
    HashtagEntity(category = HashtagCategory.TRAVEL, emoji = "🧺", name = "근교나들이"),
    HashtagEntity(category = HashtagCategory.TRAVEL, emoji = "🚗", name = "드라이브데이"),
    HashtagEntity(category = HashtagCategory.TRAVEL, emoji = "🌊", name = "바다보러"),
    HashtagEntity(category = HashtagCategory.TRAVEL, emoji = "🌲", name = "자연속으로"),
    HashtagEntity(category = HashtagCategory.TRAVEL, emoji = "🔍", name = "새로운장소발견"),

    // 취미/활동
    HashtagEntity(category = HashtagCategory.HOBBY, emoji = "🎨", name = "취미시간"),
    HashtagEntity(category = HashtagCategory.HOBBY, emoji = "🏋️", name = "운동"),
    HashtagEntity(category = HashtagCategory.HOBBY, emoji = "🍜", name = "맛집탐방"),
    HashtagEntity(category = HashtagCategory.HOBBY, emoji = "🛍️", name = "쇼핑데이"),
    HashtagEntity(category = HashtagCategory.HOBBY, emoji = "🎬", name = "문화생활"),
    HashtagEntity(category = HashtagCategory.HOBBY, emoji = "📵", name = "디지털디톡스"),

    // 감성/분위기
    HashtagEntity(category = HashtagCategory.MOOD, emoji = "🌤️", name = "느긋한하루"),
    HashtagEntity(category = HashtagCategory.MOOD, emoji = "💖", name = "행복한 하루"),
    HashtagEntity(category = HashtagCategory.MOOD, emoji = "🌙", name = "혼자만의시간"),
    HashtagEntity(category = HashtagCategory.MOOD, emoji = "🌀", name = "무계획의자유"),
    HashtagEntity(category = HashtagCategory.MOOD, emoji = "🎵", name = "잔잔한하루"),
    HashtagEntity(category = HashtagCategory.MOOD, emoji = "⏳", name = "시간이멈춘듯한날"),
)

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