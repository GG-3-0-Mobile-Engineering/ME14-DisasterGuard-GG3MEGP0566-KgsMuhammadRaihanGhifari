package com.hann.disasterguard.presentation.main

import com.hann.disasterguard.coreapp.domain.model.GeometryReport

data class MainState (
    val isLoading : Boolean = false,
    val map: List<GeometryReport> = emptyList(),
    val error: String = ""
)