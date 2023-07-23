package com.hann.disasterguard.coreapp.data.remote.response.archive

data class ReportData(
    val accessabilityFailure: Int,
    val airQuality: Int,
    val condition: Int,
    val evacuationArea: Boolean,
    val evacuationNumber: Int,
    val fireDistance: Double,
    val fireLocation: FireLocation,
    val fireRadius: FireRadius,
    val flood_depth: Int,
    val impact: Int,
    val personLocation: PersonLocation,
    val report_type: String,
    val structureFailure: Int,
    val visibility: Int,
    val volcanicSigns: List<Int>
)