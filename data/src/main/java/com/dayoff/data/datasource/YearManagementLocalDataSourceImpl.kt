package com.dayoff.data.datasource

import com.dayoff.core.db.dao.YearManagementDao
import com.dayoff.core.db.entity.YearManagementEntity
import kotlinx.coroutines.flow.Flow

class YearManagementLocalDataSourceImpl(
    private val yearManagementDao: YearManagementDao
) : YearManagementLocalDataSource {

    override suspend fun insert(entity: YearManagementEntity): Long = yearManagementDao.insert(entity)

    override suspend fun update(entity: YearManagementEntity) = yearManagementDao.update(entity)

    override suspend fun delete(entity: YearManagementEntity) = yearManagementDao.delete(entity)

    override suspend fun getByAnnualLeaveYear(year: Int): YearManagementEntity? =
        yearManagementDao.getByAnnualLeaveYear(year)

    override suspend fun getAll(): List<YearManagementEntity> = yearManagementDao.getAll()

    override fun observeAll(): Flow<List<YearManagementEntity>> = yearManagementDao.observeAll()
}