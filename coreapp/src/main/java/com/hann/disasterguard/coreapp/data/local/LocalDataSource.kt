package com.hann.disasterguard.coreapp.data.local

import com.hann.disasterguard.coreapp.data.local.dao.ArchiveReportDao
import com.hann.disasterguard.coreapp.data.local.entity.ArchiveReportEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val archiveReportDao: ArchiveReportDao) {

    fun getAllArchive() : Flow<List<ArchiveReportEntity>> = archiveReportDao.getAllArchive()

    suspend fun insertArchive(archive: List<ArchiveReportEntity>) = archiveReportDao.insertArchive(archive)
}