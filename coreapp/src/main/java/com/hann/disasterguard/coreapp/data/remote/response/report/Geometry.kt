package com.hann.disasterguard.coreapp.data.remote.response.report

data class Geometry(
    val coordinates: List<Int>,
    val properties: Properties,
    val type: String
)