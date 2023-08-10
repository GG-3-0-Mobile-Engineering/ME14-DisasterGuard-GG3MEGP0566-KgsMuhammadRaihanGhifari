package com.hann.disasterguard.coreapp.data.remote

import android.util.Log
import com.hann.disasterguard.coreapp.data.remote.network.ApiResponse
import com.hann.disasterguard.coreapp.data.remote.network.ApiService
import com.hann.disasterguard.coreapp.data.remote.response.archive.ArchiveReportItem
import com.hann.disasterguard.coreapp.data.remote.response.flood.GeometryFloodItem
import com.hann.disasterguard.coreapp.data.remote.response.report.GeometryReportItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getLiveReport(admin : String?, disaster: String?): Flow<ApiResponse<List<GeometryReportItem>>> {
        return flow {
            try {
                val response = apiService.getLiveReports(admin, disaster)
                val dataArray = response.result.objects.output.geometries
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("Remote Data Source", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getArchiveReport(start : String, end: String, geoformat: String?): Flow<ApiResponse<List<ArchiveReportItem>>> {
        return flow {
            try {
                val response = apiService.getArchiveReport(start,end, geoformat)
                val dataArray = response.result.features
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("Remote Data Source", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFloodsLevel(): Flow<ApiResponse<List<GeometryFloodItem>>> {
        return flow {
            try {
                val response = apiService.getFloodsLevel()
                val dataArray = response.result.objects.output.geometries
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("Remote Data Source", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}