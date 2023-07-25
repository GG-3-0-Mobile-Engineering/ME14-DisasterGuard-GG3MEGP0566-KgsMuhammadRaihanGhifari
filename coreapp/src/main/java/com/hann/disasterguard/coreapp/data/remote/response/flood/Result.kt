package com.hann.disasterguard.coreapp.data.remote.response.flood

data class Result(
    val arcs: List<List<List<Int>>>,
    val bbox: List<Double>,
    val objects: Objects,
    val transform: Transform,
    val type: String
)