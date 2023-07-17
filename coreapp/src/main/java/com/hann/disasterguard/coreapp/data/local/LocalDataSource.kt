package com.hann.disasterguard.coreapp.data.local

import com.hann.disasterguard.coreapp.data.local.dao.GeometryReportDao
import com.hann.disasterguard.coreapp.data.local.entity.GeometryReportEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(private val geometryReportDao: GeometryReportDao) {

    fun getAllReport() : Flow<List<GeometryReportEntity>> = geometryReportDao.getAllReport()

    suspend fun insertReport(geometryReport: List<GeometryReportEntity>) = geometryReportDao.insertReport(geometryReport)
}