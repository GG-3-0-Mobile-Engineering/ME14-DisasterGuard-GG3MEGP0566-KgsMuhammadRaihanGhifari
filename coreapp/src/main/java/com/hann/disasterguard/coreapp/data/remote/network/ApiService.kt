package com.hann.disasterguard.coreapp.data.remote.network

import com.hann.disasterguard.coreapp.data.remote.response.archive.ArchiveResponse
import com.hann.disasterguard.coreapp.data.remote.response.flood.FloodResponse
import com.hann.disasterguard.coreapp.data.remote.response.report.ReportsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("reports")
    suspend fun getLiveReports(
        @Query("admin") admin: String? = null ,
        @Query("disaster") disaster : String? = null
    ) : ReportsResponse

    @GET("reports/archive")
    suspend fun getArchiveReport(
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("geoformat") geoformat: String?
    ) : ArchiveResponse
    @GET("floods?minimum_state=1")
    suspend fun getFloodsLevel(
    ) : FloodResponse


}