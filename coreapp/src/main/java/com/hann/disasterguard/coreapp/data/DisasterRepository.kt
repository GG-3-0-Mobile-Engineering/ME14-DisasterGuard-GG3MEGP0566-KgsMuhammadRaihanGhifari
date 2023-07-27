package com.hann.disasterguard.coreapp.data

import com.hann.disasterguard.coreapp.data.local.LocalDataSource
import com.hann.disasterguard.coreapp.data.remote.RemoteDataSource
import com.hann.disasterguard.coreapp.data.remote.network.ApiResponse
import com.hann.disasterguard.coreapp.data.remote.response.archive.ArchiveReportItem
import com.hann.disasterguard.coreapp.domain.model.ArchiveReport
import com.hann.disasterguard.coreapp.domain.model.GeometryFlood
import com.hann.disasterguard.coreapp.domain.model.GeometryReport
import com.hann.disasterguard.coreapp.domain.repository.IDisasterRepository
import com.hann.disasterguard.coreapp.resource.Resource
import com.hann.disasterguard.coreapp.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class DisasterRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
):IDisasterRepository {


    override fun getLiveReport(admin: String?, disaster: String?): Flow<Resource<List<GeometryReport>>> = flow {
        try {
            emit(Resource.Loading())
            when(val liveReport = remoteDataSource.getLiveReport(admin,disaster).first()){
                is ApiResponse.Success -> {
                    val data = DataMapper.mapResponseToDomainLive(liveReport.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Disaster not found"))
                is ApiResponse.Error -> emit(Resource.Error(liveReport.errorMessage))
            }
        }catch (e: HttpException){
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected Error Occurred"
                )
            )
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet server"))
        }
    }

    override fun getArchiveReport(start: String, end: String, city: String?, geoformat: String?
    ): Flow<Resource<List<ArchiveReport>>>  =
        object : NetworkBoundResource<List<ArchiveReport>, List<ArchiveReportItem>>(){
            override fun loadFromDB(): Flow<List<ArchiveReport>> {
                return localDataSource.getAllArchive().map {
                    DataMapper.mapEntitiesToDomainArchive(it)
                }
            }

            override fun shouldFetch(data: List<ArchiveReport>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<ArchiveReportItem>>> =
                remoteDataSource.getArchiveReport(start, end, city, geoformat)

            override suspend fun saveCallResult(data: List<ArchiveReportItem>) {
                val geometryReport = DataMapper.mapResponsesToEntitiesArchive(data)
                localDataSource.insertArchive(geometryReport)
            }
        }.asFlow()

    override fun getFloodLevel(): Flow<Resource<List<GeometryFlood>>>  = flow {
        try {
            emit(Resource.Loading())
            when(val levelFlood = remoteDataSource.getFloodsLevel().first()){
                is ApiResponse.Success -> {
                    val data = DataMapper.mapResponseToDomainFlood(levelFlood.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Disaster not found"))
                is ApiResponse.Error -> emit(Resource.Error(levelFlood.errorMessage))
            }
        }catch (e: HttpException){
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected Error Occurred"
                )
            )
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet server"))
        }
    }

}