package com.dayoff.core.db.dao

import androidx.room.*
import com.dayoff.core.db.entity.YearManagementEntity

@Dao
interface YearManagementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: YearManagementEntity): Long

    @Update
    suspend fun update(entity: YearManagementEntity)

    @Delete
    suspend fun delete(entity: YearManagementEntity)

    @Query("SELECT * FROM year_management WHERE annual_leave_year = :year LIMIT 1")
    suspend fun getByAnnualLeaveYear(year: Int): YearManagementEntity?

    @Query("SELECT * FROM year_management ORDER BY annual_leave_year DESC")
    suspend fun getAll(): List<YearManagementEntity>
}