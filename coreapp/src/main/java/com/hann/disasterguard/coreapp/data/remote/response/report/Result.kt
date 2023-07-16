package com.hann.disasterguard.coreapp.data.remote.response.report

data class Result(
    val arcs: List<Any>,
    val bbox: List<Double>,
    val objects: Objects,
    val transform: Transform,
    val type: String
)