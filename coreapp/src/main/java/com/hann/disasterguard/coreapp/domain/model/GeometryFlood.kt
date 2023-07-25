package com.hann.disasterguard.coreapp.domain.model

import com.hann.disasterguard.coreapp.data.remote.response.flood.Properties

data class GeometryFlood(
    val arcs: List<List<Int>>,
    val properties: Properties,
    val type: String
)