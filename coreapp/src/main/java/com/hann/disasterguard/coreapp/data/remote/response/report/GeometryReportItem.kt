package com.hann.disasterguard.coreapp.data.remote.response.report

data class GeometryReportItem(
    val coordinates: List<Int>,
    val properties: Properties,
    val type: String
)