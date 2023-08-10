package com.hann.disasterguard.coreapp.domain.usecase.interactor

import com.hann.disasterguard.coreapp.domain.model.ArchiveReport
import com.hann.disasterguard.coreapp.domain.model.GeometryFlood
import com.hann.disasterguard.coreapp.domain.model.GeometryReport
import com.hann.disasterguard.coreapp.domain.repository.IDisasterRepository
import com.hann.disasterguard.coreapp.domain.usecase.DisasterUseCase
import com.hann.disasterguard.coreapp.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DisasterInteractor @Inject constructor(private val iDisasterRepository: IDisasterRepository) : DisasterUseCase {

    override fun getLiveReport(admin: String?, disaster: String?): Flow<Resource<List<GeometryReport>>> {
        return iDisasterRepository.getLiveReport(admin, disaster)
    }

    override fun getArchiveReport(
        start: String,
        end: String,
        geoformat: String?
    ): Flow<Resource<List<ArchiveReport>>> {
        return iDisasterRepository.getArchiveReport(start, end,  geoformat)
    }
    override fun getFloodLevel(): Flow<Resource<List<GeometryFlood>>> {
       return iDisasterRepository.getFloodLevel()
    }
}