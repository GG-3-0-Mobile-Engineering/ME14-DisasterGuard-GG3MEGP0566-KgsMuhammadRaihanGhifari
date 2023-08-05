package com.hann.disasterguard.coreapp.di

import android.content.Context
import androidx.room.Room
import com.hann.disasterguard.coreapp.BuildConfig
import com.hann.disasterguard.coreapp.data.DisasterRepository
import com.hann.disasterguard.coreapp.data.local.dao.ArchiveReportDao
import com.hann.disasterguard.coreapp.data.local.database.DisasterGuardDatabase
import com.hann.disasterguard.coreapp.data.remote.network.ApiService
import com.hann.disasterguard.coreapp.domain.repository.IDisasterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DisasterGuardDatabase = Room.databaseBuilder(
        context,
        DisasterGuardDatabase::class.java, "Tourism.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideArchiveDao(database: DisasterGuardDatabase): ArchiveReportDao = database.archiveReportDao()
}


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(disasterRepository: DisasterRepository): IDisasterRepository

}


