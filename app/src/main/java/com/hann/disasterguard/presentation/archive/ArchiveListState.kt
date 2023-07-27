package com.hann.disasterguard.presentation.archive

import com.hann.disasterguard.coreapp.domain.model.ArchiveReport

data class ArchiveListState(
    val isLoading : Boolean = false,
    val archive: List<ArchiveReport> = emptyList(),
    val error: String = ""
)