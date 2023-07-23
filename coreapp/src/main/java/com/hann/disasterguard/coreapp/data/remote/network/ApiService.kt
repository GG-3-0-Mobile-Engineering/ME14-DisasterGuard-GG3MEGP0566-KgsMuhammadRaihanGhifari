package com.hann.disasterguard.coreapp.data.remote.network

import com.hann.disasterguard.coreapp.data.remote.response.archive.ArchiveResponse
import com.hann.disasterguard.coreapp.data.remote.response.report.ReportsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("reports")
    suspend fun getLiveReports(
        @Query("admin") admin: String? = null ,
    ) : ReportsResponse

    @GET("reports/archive")
    suspend fun getArchiveReport(
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("city") city: String?,
        @Query("geoformat") geoformat: String?
    ) : ArchiveResponse


}