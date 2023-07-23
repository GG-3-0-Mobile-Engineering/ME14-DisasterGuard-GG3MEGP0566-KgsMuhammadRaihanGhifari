package com.hann.disasterguard.presentation.di

import com.hann.disasterguard.presentation.archive.ArchiveViewModel
import com.hann.disasterguard.presentation.main.MainViewModel
import com.hann.disasterguard.presentation.map.MapViewModel
import com.hann.disasterguard.presentation.report.ReportViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ReportViewModel(get())
    }
    viewModel {
        MapViewModel(get())
    }
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        ArchiveViewModel(get())
    }
}