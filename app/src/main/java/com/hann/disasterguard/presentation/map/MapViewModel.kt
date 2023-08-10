package com.hann.disasterguard.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hann.disasterguard.coreapp.domain.usecase.DisasterUseCase
import com.hann.disasterguard.coreapp.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val useCase: DisasterUseCase
) : ViewModel() {

    private val _state = MutableLiveData<MapListState>()
    val state : LiveData<MapListState> = _state

    private val _stateArchive = MutableLiveData<ArchiveListState>()
    val stateArchive : LiveData<ArchiveListState> = _stateArchive

     fun getReportLive(admin : String?, region: String?) {
        useCase.getLiveReport(admin, region).onEach {
                result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = MapListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = MapListState(error = result.message ?: "An unexpected Error occured", isLoading = false)
                }
                is Resource.Success -> {
                    _state.value = MapListState(map = result.data ?: emptyList(), isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getArchiveReport(start : String, end: String, geoformat: String ) {
        useCase.getArchiveReport(start, end, geoformat).onEach {
                result ->
            when(result){
                is Resource.Loading -> {
                    _stateArchive.value = ArchiveListState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateArchive.value = ArchiveListState(error = result.message ?: "An unexpected Error occured")
                }
                is Resource.Success -> {
                    _stateArchive.value = ArchiveListState(archive = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

}