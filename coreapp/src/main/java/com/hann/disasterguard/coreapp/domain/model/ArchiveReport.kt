package com.hann.disasterguard.coreapp.domain.model

import com.hann.disasterguard.coreapp.data.remote.response.archive.Geometry
import com.hann.disasterguard.coreapp.data.remote.response.archive.Properties

data class ArchiveReport(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)