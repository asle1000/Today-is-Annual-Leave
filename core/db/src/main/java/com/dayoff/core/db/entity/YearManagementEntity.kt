package com.dayoff.core.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  Created by KyunghyunPark at 2025. 7. 28.

 */
@Entity(tableName = "year_management")
data class YearManagementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "annual_leave_year")
    val annualLeaveYear: Int, // 연차 부여 연도
    @ColumnInfo(name = "hire_year")
    val hireYear: Int, // 입사 연도
    @ColumnInfo(name = "used_annual_leave")
    val usedAnnualLeave: Int = 0, // 사용한 연차 (일수)
    @ColumnInfo(name = "total_annual_leave")
    val totalAnnualLeave: Int, // 총 사용 가능 연차 (일수)
    @ColumnInfo(name = "created_date")
    val createdDate: Long = System.currentTimeMillis(),  // 생성 일시 (timestamp)
    @ColumnInfo(name = "modified_date")
    val modifiedDate: Long = System.currentTimeMillis() // 수정 일시 (timestamp)
)