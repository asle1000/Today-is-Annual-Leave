package com.dayoff.core.db.dao

import androidx.room.*
import com.dayoff.core.db.entity.YearManagementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface YearManagementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: YearManagementEntity): Long

    @Update
    suspend fun update(entity: YearManagementEntity)

    @Delete
    suspend fun delete(entity: YearManagementEntity)

    @Query("SELECT * FROM year_management ORDER BY created_date DESC")
    fun getAll(): Flow<List<YearManagementEntity>>

    @Query("SELECT * FROM year_management WHERE id = :id")
    suspend fun getById(id: Long): YearManagementEntity?
}