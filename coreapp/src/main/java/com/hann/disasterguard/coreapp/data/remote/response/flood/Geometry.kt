package com.hann.disasterguard.coreapp.data.remote.response.flood

data class GeometryFloodItem(
    val arcs: List<List<Int>>,
    val properties: Properties,
    val type: String
)