package com.dayoff.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dayoff.core.db.entity.AnnualLeaveRecordEntity

/**
 *  Created by KyunghyunPark at 2025. 8. 30.

 */
@Dao
interface AnnualLeaveRecordDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: AnnualLeaveRecordEntity): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(entity: AnnualLeaveRecordEntity)

    @Query(
        value = """
                UPDATE annual_leave_record
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
        startDate: Int?,
        endDate: Int?,
        minutes: Int?,
        memo: String?,
        modifiedAt: Long = System.currentTimeMillis()
    ): Int
}