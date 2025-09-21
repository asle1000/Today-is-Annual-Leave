package com.dayoff.core.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 *  Created by KyunghyunPark at 2025. 9. 21.

 */

data class LeaveRecordWithHashtags(
    @Embedded val record: AnnualLeaveRecordEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = HashtagMapEntity::class,
            parentColumn = "recordId",
            entityColumn = "hashtagId"
        )
    )
    val hashtags: List<HashtagEntity>
)