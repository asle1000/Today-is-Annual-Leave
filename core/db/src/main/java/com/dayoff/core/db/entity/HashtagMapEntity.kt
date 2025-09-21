package com.dayoff.core.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "hashtag_map",
    primaryKeys = ["recordId", "hashtagId"],
    foreignKeys = [
        ForeignKey(
            entity = AnnualLeaveRecordEntity::class,
            parentColumns = ["id"],
            childColumns = ["recordId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = HashtagEntity::class,
            parentColumns = ["id"],
            childColumns = ["hashtagId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["recordId"]),
        Index(value = ["hashtagId"])
    ]
)
data class HashtagMapEntity(
    val recordId: Long,
    val hashtagId: Long,
    val position: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)