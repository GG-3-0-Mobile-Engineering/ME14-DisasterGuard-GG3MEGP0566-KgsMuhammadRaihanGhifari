package com.hann.disasterguard.presentation.main

import com.hann.disasterguard.coreapp.domain.model.GeometryFlood

data class MainState (
    val isLoading : Boolean = false,
    val flood: List<GeometryFlood> = emptyList(),
    val error: String = ""
)