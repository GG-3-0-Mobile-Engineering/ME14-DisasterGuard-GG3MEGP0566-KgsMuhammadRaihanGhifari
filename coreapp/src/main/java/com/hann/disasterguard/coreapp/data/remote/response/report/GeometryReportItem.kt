package com.hann.disasterguard.coreapp.data.remote.response.report

data class GeometryReportItem(
    val coordinates: List<Double>,
    val properties: Properties,
    val type: String
)