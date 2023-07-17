package com.hann.disasterguard.coreapp.data.remote.network

import com.hann.disasterguard.coreapp.data.remote.response.report.ReportsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("reports")
    suspend fun getLiveReports(
        @Query("admin") admin: String? = null ,
    ) : ReportsResponse

}