package com.dayoff.data.datasource

import com.dayoff.core.db.dao.AnnualLeaveRecordDao
import com.dayoff.core.db.entity.AnnualLeaveRecordEntity
import com.dayoff.core.db.entity.LeaveRecordWithHashtags
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/**
 *  Created by KyunghyunPark at 2025. 8. 31.

 */
class AnnualLeaveLocalDataSourceImpl(
    private val dao: AnnualLeaveRecordDao,
    private val timeZone: TimeZone = TimeZone.getTimeZone("Asia/Seoul"),
    private val locale: Locale = Locale.KOREA
) : AnnualLeaveLocalDataSource {

    override fun observeByRange(startYmd: Int, endYmd: Int): Flow<List<LeaveRecordWithHashtags>> =
        dao.observeByRangeOverlap(startYmd = startYmd, endYmd = endYmd)

    override suspend fun getByRange(startYmd: Int, endYmd: Int): List<LeaveRecordWithHashtags> =
        dao.getByRangeOverlap(startYmd = startYmd, endYmd = endYmd)

    override fun observeByMonth(year: Int, month: Int): Flow<List<LeaveRecordWithHashtags>> {
        val (startYmd, endYmd) = calculateMonthYmdRange(year = year, month = month)
        return dao.observeByRangeOverlap(startYmd = startYmd, endYmd = endYmd)
    }

    override suspend fun getByMonth(year: Int, month: Int): List<LeaveRecordWithHashtags> {
        val (startYmd, endYmd) = calculateMonthYmdRange(year = year, month = month)
        return dao.getByRangeOverlap(startYmd = startYmd, endYmd = endYmd)
    }

    override fun observeByYear(year: Int): Flow<List<LeaveRecordWithHashtags>> {
        val (startYmd, endYmd) = calculateYearYmdRange(year)
        return dao.observeByRangeOverlap(startYmd = startYmd, endYmd = endYmd)
    }

    override suspend fun getByYear(year: Int): List<LeaveRecordWithHashtags> {
        val (startYmd, endYmd) = calculateYearYmdRange(year)
        return dao.getByRangeOverlap(startYmd = startYmd, endYmd = endYmd)
    }

    override suspend fun insert(entity: AnnualLeaveRecordEntity): Long = dao.insert(entity)

    override suspend fun update(entity: AnnualLeaveRecordEntity) {
        dao.update(entity)
    }

    override suspend fun updateById(
        id: Long, startDate: Int?, endDate: Int?, minutes: Int?, memo: String?
    ): Int {
        val nothingToUpdate =
            startDate == null && endDate == null && minutes == null && memo == null
        if (nothingToUpdate) return 0

        return dao.updateById(
            id = id,
            startDate = startDate,
            endDate = endDate,
            minutes = minutes,
            memo = memo,
        )
    }

    override suspend fun updateMemoById(id: Long, memo: String): Int {
        return dao.updateById(id = id, memo = memo)
    }

    private fun calculateMonthYmdRange(year: Int, month: Int): YmdRange {
        val calendar = Calendar.getInstance(timeZone, locale).apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val startYmd = toYmd(calendar = calendar)
        val lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val endYmd = year * 10000 + month * 100 + lastDay
        return YmdRange(startYmd = startYmd, endYmd = endYmd)
    }

    private fun calculateYearYmdRange(year: Int): YmdRange {
        return YmdRange(startYmd = year * 10000 + 101, endYmd = year * 10000 + 1231)
    }

    private fun toYmd(calendar: Calendar): Int {
        return with(calendar) {
            get(Calendar.YEAR) * 10000 + (get(Calendar.MONTH) + 1) * 100 + get(Calendar.DAY_OF_MONTH)
        }
    }

    private data class YmdRange(val startYmd: Int, val endYmd: Int)
}