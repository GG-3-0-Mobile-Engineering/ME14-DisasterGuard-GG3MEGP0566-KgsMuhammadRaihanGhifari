package com.hann.disasterguard.coreapp.data.remote.response.flood

data class Output(
    val geometries: List<GeometryFloodItem>,
    val type: String
)