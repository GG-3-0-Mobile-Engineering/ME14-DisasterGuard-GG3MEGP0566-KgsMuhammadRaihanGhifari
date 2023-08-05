package com.hann.disasterguard.presentation.report

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
class ReportViewModel @Inject constructor(
    private val useCase: DisasterUseCase
) : ViewModel(){

    private val _state = MutableLiveData<ReportListState>()
    val state : LiveData<ReportListState> = _state

    init {
        getReportLive(null,null)
    }

    fun getReportLive(admin : String?, region: String?) {
        useCase.getLiveReport(admin,region).onEach {
                result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = ReportListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = ReportListState(error = result.message ?: "An unexpected Error occured")
                }
                is Resource.Success -> {
                    _state.value = ReportListState(report = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}