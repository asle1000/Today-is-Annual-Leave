package com.dayoff.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dayoff.core.db.entity.AnnualLeaveRecordEntity
import kotlinx.coroutines.flow.Flow

/**
 *  Created by KyunghyunPark at 2025. 8. 30.

 */
@Dao
interface AnnualLeaveRecordDao {

    @Query(
        """
        SELECT *
        FROM $TABLE_NAME
        WHERE NOT (endDate < :startYmd OR startDate > :endYmd)
        ORDER BY startDate ASC, endDate ASC, id ASC
    """
    )
    suspend fun getByRangeOverlap(startYmd: Int, endYmd: Int): List<AnnualLeaveRecordEntity>

    @Query(
        """
        SELECT *
        FROM $TABLE_NAME
        WHERE NOT (endDate < :startYmd OR startDate > :endYmd)
        ORDER BY startDate ASC, endDate ASC, id ASC
    """
    )
    fun observeByRangeOverlap(startYmd: Int, endYmd: Int): Flow<List<AnnualLeaveRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: AnnualLeaveRecordEntity): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(entity: AnnualLeaveRecordEntity)

    @Query(
        value = """
                UPDATE $TABLE_NAME
                SET 
                    startDate = COALESCE(:startDate, startDate),
                    endDate   = COALESCE(:endDate, endDate),
                    minutes   = COALESCE(:minutes, minutes),
                    memo      = COALESCE(:memo, memo),
                    modifiedAt= :modifiedAt
                WHERE id = :id
            """
    )
    suspend fun updateById(
        id: Long,
        startDate: Int? = null,
        endDate: Int? = null,
        minutes: Int? = null,
        memo: String? = null,
        modifiedAt: Long = System.currentTimeMillis()
    ): Int

    companion object {
        private const val TABLE_NAME = "annual_leave_record"
    }
}