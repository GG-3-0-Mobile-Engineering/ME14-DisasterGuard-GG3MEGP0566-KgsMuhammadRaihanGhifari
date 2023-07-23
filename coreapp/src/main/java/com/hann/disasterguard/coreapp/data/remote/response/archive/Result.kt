package com.hann.disasterguard.coreapp.data.remote.response.archive

data class  Result(
    val features: List<ArchiveReportItem>,
    val type: String
)