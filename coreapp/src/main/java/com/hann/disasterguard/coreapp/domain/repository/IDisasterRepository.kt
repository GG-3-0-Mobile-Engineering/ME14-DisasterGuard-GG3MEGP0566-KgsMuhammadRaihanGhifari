package com.hann.disasterguard.coreapp.domain.repository

import com.hann.disasterguard.coreapp.domain.model.ArchiveReport
import com.hann.disasterguard.coreapp.domain.model.GeometryFlood
import com.hann.disasterguard.coreapp.domain.model.GeometryReport
import com.hann.disasterguard.coreapp.resource.Resource
import kotlinx.coroutines.flow.Flow

interface IDisasterRepository {
    fun getLiveReport(admin :String?, disaster: String?): Flow<Resource<List<GeometryReport>>>

    fun getArchiveReport(start : String, end : String, city : String?, geoformat: String?) : Flow<Resource<List<ArchiveReport>>>
    fun getFloodLevel() : Flow<Resource<List<GeometryFlood>>>

}