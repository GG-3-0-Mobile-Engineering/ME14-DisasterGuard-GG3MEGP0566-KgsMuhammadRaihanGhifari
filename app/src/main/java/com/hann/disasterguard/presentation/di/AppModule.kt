package com.hann.disasterguard.presentation.di

import com.hann.disasterguard.presentation.report.ReportViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ReportViewModel(get())
    }
}