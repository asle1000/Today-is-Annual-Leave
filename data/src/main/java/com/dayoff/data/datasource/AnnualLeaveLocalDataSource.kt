package com.dayoff.data.datasource

import com.dayoff.core.db.entity.AnnualLeaveRecordEntity
import kotlinx.coroutines.flow.Flow

/**
 *  Created by KyunghyunPark at 2025. 8. 31.

 */
interface AnnualLeaveLocalDataSource {


    /** [startYmd, endYmd] 겹치는 레코드 스트림 반환 */
    fun observeByRange(startYmd: Int, endYmd: Int): Flow<List<AnnualLeaveRecordEntity>>

    /** [startYmd, endYmd] 겹치는 레코드 리스트 반환 */
    suspend fun getByRange(startYmd: Int, endYmd: Int): List<AnnualLeaveRecordEntity>

    /** (year, month) 겹치는 레코드 스트림 반환 */
    fun observeByMonth(year: Int, month: Int): Flow<List<AnnualLeaveRecordEntity>>

    /** (year, month) 겹치는 레코드 리스트 반환 */
    suspend fun getByMonth(year: Int, month: Int): List<AnnualLeaveRecordEntity>

    /** year 겹치는 레코드 스트림 반환 */
    fun observeByYear(year: Int): Flow<List<AnnualLeaveRecordEntity>>

    /** year 겹치는 레코드 리스트 반환 */
    suspend fun getByYear(year: Int): List<AnnualLeaveRecordEntity>

    /** 단건 생성 결과 id 반환 */
    suspend fun insert(entity: AnnualLeaveRecordEntity): Long

    /** id 기반 전체 갱신 결과 반환 */
    suspend fun update(entity: AnnualLeaveRecordEntity)

    /** id 기반 부분 갱신 영향 행 수 반환 */
    suspend fun updateById(
        id: Long,
        startDate: Int? = null,
        endDate: Int? = null,
        minutes: Int? = null,
        memo: String? = null
    ): Int

    suspend fun updateMemoById(id: Long, memo: String): Int
}