package com.hann.disasterguard.coreapp.data

import com.hann.disasterguard.coreapp.data.local.LocalDataSource
import com.hann.disasterguard.coreapp.data.remote.RemoteDataSource
import com.hann.disasterguard.coreapp.data.remote.network.ApiResponse
import com.hann.disasterguard.coreapp.data.remote.response.report.GeometryReportItem
import com.hann.disasterguard.coreapp.domain.model.GeometryReport
import com.hann.disasterguard.coreapp.domain.repository.IDisasterRepository
import com.hann.disasterguard.coreapp.resource.Resource
import com.hann.disasterguard.coreapp.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DisasterRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
):IDisasterRepository {


    override fun getLiveReport(admin: String?): Flow<Resource<List<GeometryReport>>> =
        object : NetworkBoundResource<List<GeometryReport>, List<GeometryReportItem>>(){
            override fun loadFromDB(): Flow<List<GeometryReport>> {
                return localDataSource.getAllReport().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<GeometryReport>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<GeometryReportItem>>> =
                remoteDataSource.getLiveReport(admin)

            override suspend fun saveCallResult(data: List<GeometryReportItem>) {
                val geometryReport = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertReport(geometryReport)
            }
        }.asFlow()


}