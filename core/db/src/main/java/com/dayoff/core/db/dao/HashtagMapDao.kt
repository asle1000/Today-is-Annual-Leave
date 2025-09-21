package com.dayoff.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dayoff.core.db.entity.HashtagMapEntity

@Dao
interface HashtagMapDao {

    /** 단건 등록 (중복이면 무시) */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun link(map: HashtagMapEntity): Long

    /** 리스트 등록 (중복이면 무시) */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun linkAll(maps: List<HashtagMapEntity>): List<Long>

    /** 단건 제거 */
    @Query("DELETE FROM hashtag_map WHERE recordId = :recordId AND hashtagId = :hashtagId")
    suspend fun unlink(recordId: Long, hashtagId: Long): Int

    /** 특정 레코드의 모든 태그 제거 */
    @Query("DELETE FROM hashtag_map WHERE recordId = :recordId")
    suspend fun clearByRecord(recordId: Long): Int
}