package com.hann.disasterguard.coreapp.data.remote

import android.util.Log
import com.hann.disasterguard.coreapp.data.remote.network.ApiResponse
import com.hann.disasterguard.coreapp.data.remote.network.ApiService
import com.hann.disasterguard.coreapp.data.remote.response.report.GeometryReportItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(private val apiService: ApiService) {

    suspend fun getLiveReport(admin : String?): Flow<ApiResponse<List<GeometryReportItem>>> {
        return flow {
            try {
                val response = apiService.getLiveReports(admin)
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