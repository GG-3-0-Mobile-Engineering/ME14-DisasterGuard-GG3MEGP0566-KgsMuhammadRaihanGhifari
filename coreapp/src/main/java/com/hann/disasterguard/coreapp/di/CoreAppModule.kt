package com.hann.disasterguard.coreapp.di

import androidx.room.Room
import com.hann.disasterguard.coreapp.BuildConfig
import com.hann.disasterguard.coreapp.data.DisasterRepository
import com.hann.disasterguard.coreapp.data.local.LocalDataSource
import com.hann.disasterguard.coreapp.data.local.database.DisasterGuardDatabase
import com.hann.disasterguard.coreapp.data.remote.RemoteDataSource
import com.hann.disasterguard.coreapp.data.remote.network.ApiService
import com.hann.disasterguard.coreapp.domain.repository.IDisasterRepository
import com.hann.disasterguard.coreapp.domain.usecase.DisasterUseCase
import com.hann.disasterguard.coreapp.domain.usecase.interactor.DisasterInteractor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



val databaseModule =  module {
    factory {
        get<DisasterGuardDatabase>().archiveReportDao()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            DisasterGuardDatabase::class.java, "disasterGuard.db"
        ).fallbackToDestructiveMigration().build()
    }
}


val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IDisasterRepository> {
        DisasterRepository(
            get(),
            get()
        )
    }
}

val useCaseModule = module {
    factory<DisasterUseCase> {
        DisasterInteractor(get())
    }
}


