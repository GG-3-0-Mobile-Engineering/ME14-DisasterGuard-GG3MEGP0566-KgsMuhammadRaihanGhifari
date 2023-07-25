package com.hann.disasterguard.coreapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hann.disasterguard.coreapp.data.local.entity.ArchiveReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArchiveReportDao {

    @Query("SELECT * FROM archiveReport")
    fun getAllArchive(): Flow<List<ArchiveReportEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArchive(archive: List<ArchiveReportEntity>)

}