package com.hann.disasterguard.coreapp.data.remote.response.flood

data class Properties(
    val area_id: String,
    val area_name: String,
    val city_name: String,
    val geom_id: String,
    val last_updated: String,
    val parent_name: String,
    val state: Int
)