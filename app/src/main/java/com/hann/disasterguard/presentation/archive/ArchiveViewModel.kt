package com.hann.disasterguard.presentation.archive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hann.disasterguard.coreapp.domain.usecase.DisasterUseCase
import com.hann.disasterguard.coreapp.resource.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ArchiveViewModel(
    private val useCase: DisasterUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ArchiveListState>()
    val state : LiveData<ArchiveListState> = _state

    fun getArchiveReport(start : String, end: String,city : String?, geoformat: String ) {
        useCase.getArchiveReport(start, end, city, geoformat).onEach {
                result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = ArchiveListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = ArchiveListState(error = result.message ?: "An unexpected Error occured")
                }
                is Resource.Success -> {
                    _state.value = ArchiveListState(report = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

}

