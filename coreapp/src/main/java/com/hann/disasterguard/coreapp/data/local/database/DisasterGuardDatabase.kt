package com.hann.disasterguard.coreapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hann.disasterguard.coreapp.data.local.dao.ArchiveReportDao
import com.hann.disasterguard.coreapp.data.local.entity.ArchiveReportEntity
import com.hann.disasterguard.coreapp.utils.TypeConverterEntity

@Database(entities = [ArchiveReportEntity::class], version = 1, exportSchema = true)
@TypeConverters(TypeConverterEntity::class)
abstract class DisasterGuardDatabase : RoomDatabase() {

    abstract fun archiveReportDao() : ArchiveReportDao

}