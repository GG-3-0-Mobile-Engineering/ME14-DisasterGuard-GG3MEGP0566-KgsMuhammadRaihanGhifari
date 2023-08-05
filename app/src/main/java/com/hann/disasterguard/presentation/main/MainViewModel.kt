package com.hann.disasterguard.presentation.main

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
class MainViewModel @Inject constructor(
    private val useCase: DisasterUseCase
) : ViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state : LiveData<MainState> = _state

    init {
        getFloodLevel()
    }

    private fun getFloodLevel() {
        useCase.getFloodLevel().onEach {
                result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = MainState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = MainState(error = result.message ?: "An unexpected Error occured")
                }
                is Resource.Success -> {
                    _state.value = MainState(flood = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

}