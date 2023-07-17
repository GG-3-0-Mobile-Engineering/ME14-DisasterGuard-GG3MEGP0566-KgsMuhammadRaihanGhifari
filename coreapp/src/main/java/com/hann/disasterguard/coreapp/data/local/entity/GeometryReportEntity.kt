package com.hann.disasterguard.coreapp.data.local.entity

import androidx.room.Entity
import com.hann.disasterguard.coreapp.data.remote.response.report.Properties


@Entity(tableName = "geometryReport")
data class GeometryReportEntity (
    val coordinates: List<Int>,
    val properties: Properties,
    val type: String
)