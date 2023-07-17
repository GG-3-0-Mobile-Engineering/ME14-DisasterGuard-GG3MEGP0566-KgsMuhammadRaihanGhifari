package com.hann.disasterguard.coreapp.domain.usecase

import com.hann.disasterguard.coreapp.domain.model.GeometryReport
import com.hann.disasterguard.coreapp.resource.Resource
import kotlinx.coroutines.flow.Flow

interface DisasterUseCase {

    fun getLiveReport(admin :String?): Flow<Resource<List<GeometryReport>>>
}