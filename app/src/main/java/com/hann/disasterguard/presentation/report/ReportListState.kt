package com.hann.disasterguard.presentation.report

import com.hann.disasterguard.coreapp.domain.model.GeometryReport

data class ReportListState (
    val isLoading : Boolean = false,
    val report: List<GeometryReport> = emptyList(),
    val error: String = ""
)