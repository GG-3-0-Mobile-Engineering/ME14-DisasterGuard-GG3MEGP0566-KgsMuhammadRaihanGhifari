package com.hann.disasterguard.coreapp.data.local

import com.hann.disasterguard.coreapp.data.local.dao.ArchiveReportDao
import com.hann.disasterguard.coreapp.data.local.entity.ArchiveReportEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(private val archiveReportDao: ArchiveReportDao) {

    fun getAllArchive() : Flow<List<ArchiveReportEntity>> = archiveReportDao.getAllArchive()

    suspend fun insertArchive(archive: List<ArchiveReportEntity>) = archiveReportDao.insertArchive(archive)
}