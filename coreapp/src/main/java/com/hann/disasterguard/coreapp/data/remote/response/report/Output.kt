package com.hann.disasterguard.coreapp.data.remote.response.report

data class Output(
    val geometries: List<GeometryReportItem>,
    val type: String
)