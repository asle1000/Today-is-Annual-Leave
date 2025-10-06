package com.dayoff.core.model

import com.dayoff.core.model.calendar.AnnualLeaveType

data class AnnualLeaveRecord(
    val id: Long,
    val startYmd: Int,
    val endYmd: Int,
    val minutes: Int?,
    val isConsumed: Boolean,
    val type: AnnualLeaveType,
    val memo: String? = null,
    val hashTags: List<Hashtag> ?= emptyList(),
    val modifiedAt: Long,
)