package com.dayoff.core.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index
import com.dayoff.core.model.calendar.AnnualLeaveType

@Entity(
    tableName = "annual_leave_record",
    indices = [
        // startDate+endDate 중복 금지(동일 구간 중복 등록 방지)
        Index(value = ["startDate", "endDate"], name = "idx_annual_leave_range", unique = true)
    ]
//    indices = [
//        Index(value = ["startDate", "endDate"], unique = true),
//        Index(value = ["startDate"]),
//        Index(value = ["endDate"])
//    ]
)
data class AnnualLeaveRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    /** 연차 종류(FULL, HALF, HOURLY) */
    val type: AnnualLeaveType,
    /** 연차 시작일 */
    @ColumnInfo(name = "startDate")
    val startDate: Int,
    /**
     * 연차 종료일
     * AnnualLeaveType(HALF or HOURLY)인 경우  startDate == endDate
     * */
    @ColumnInfo(name = "endDate")
    val endDate: Int,
    val minutes: Int = 0,
    /** 소진 여부(true=소진, false=미소진 */
    val isConsumed: Boolean = true,
    /** 연차 메모 */
    val memo: String? =  null,
    /** 연차 수정일 */
    val modifiedAt: Long = System.currentTimeMillis(),
    /** 연차 생성일 */
    val createAt: Long = System.currentTimeMillis(),
) 