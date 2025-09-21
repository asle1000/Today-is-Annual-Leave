package com.dayoff.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dayoff.core.db.entity.HashtagEntity

@Dao
interface HashtagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<HashtagEntity>)

    @Query("SELECT * FROM hashtag")
    suspend fun getAll(): List<HashtagEntity>

    @Query("SELECT * FROM hashtag WHERE id = :id")
    suspend fun getById(id: Long): HashtagEntity?

    @Query("SELECT * FROM hashtag WHERE id = :id")
    suspend fun getByCategory(id: Long): HashtagEntity?
}