package com.hann.disasterguard.coreapp.domain.usecase.interactor

import com.hann.disasterguard.coreapp.domain.model.GeometryReport
import com.hann.disasterguard.coreapp.domain.repository.IDisasterRepository
import com.hann.disasterguard.coreapp.domain.usecase.DisasterUseCase
import com.hann.disasterguard.coreapp.resource.Resource
import kotlinx.coroutines.flow.Flow

class DisasterInteractor(private val iDisasterRepository: IDisasterRepository) : DisasterUseCase {

    override fun getLiveReport(admin: String?): Flow<Resource<List<GeometryReport>>> {
        return iDisasterRepository.getLiveReport(admin)
    }

}