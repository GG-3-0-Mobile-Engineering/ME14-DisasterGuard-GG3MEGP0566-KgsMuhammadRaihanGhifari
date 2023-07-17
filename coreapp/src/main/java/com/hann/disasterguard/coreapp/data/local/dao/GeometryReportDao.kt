package com.hann.disasterguard.coreapp.data.local.dao

import androidx.room.*
import com.hann.disasterguard.coreapp.data.local.entity.GeometryReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GeometryReportDao {

    @Query("SELECT * FROM geometryReport")
    fun getAllReport(): Flow<List<GeometryReportEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReport(user: List<GeometryReportEntity>)

}