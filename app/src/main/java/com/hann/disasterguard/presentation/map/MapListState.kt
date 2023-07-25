package com.hann.disasterguard.presentation.map

import com.hann.disasterguard.coreapp.domain.model.GeometryReport

data class MapListState (
    val isLoading : Boolean = false,
    val map: List<GeometryReport> = emptyList(),
    val error: String = ""
)