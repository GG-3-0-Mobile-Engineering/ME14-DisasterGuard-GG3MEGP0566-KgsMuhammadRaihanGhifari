package com.hann.disasterguard.coreapp.domain.model

import com.hann.disasterguard.coreapp.data.remote.response.report.Properties

data class GeometryReport(
    val coordinates: List<Double>,
    val properties: Properties,
    val type: String
)