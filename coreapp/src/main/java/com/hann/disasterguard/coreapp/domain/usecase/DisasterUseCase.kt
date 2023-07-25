package com.hann.disasterguard.coreapp.domain.usecase

import com.hann.disasterguard.coreapp.domain.model.ArchiveReport
import com.hann.disasterguard.coreapp.domain.model.GeometryFlood
import com.hann.disasterguard.coreapp.domain.model.GeometryReport
import com.hann.disasterguard.coreapp.resource.Resource
import kotlinx.coroutines.flow.Flow

interface DisasterUseCase {

    fun getLiveReport(admin :String?): Flow<Resource<List<GeometryReport>>>

    fun getArchiveReport(start : String, end : String, city : String?, geoformat: String?) : Flow<Resource<List<ArchiveReport>>>

    fun getFloodLevel() : Flow<Resource<List<GeometryFlood>>>
}