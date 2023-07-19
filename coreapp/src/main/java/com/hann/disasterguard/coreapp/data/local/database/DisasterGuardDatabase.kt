package com.hann.disasterguard.coreapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hann.disasterguard.coreapp.data.local.dao.GeometryReportDao
import com.hann.disasterguard.coreapp.data.local.entity.GeometryReportEntity
import com.hann.disasterguard.coreapp.utils.TypeConverterEntity

@Database(entities = [GeometryReportEntity::class], version = 1, exportSchema = true)
@TypeConverters(TypeConverterEntity::class)
abstract class DisasterGuardDatabase : RoomDatabase() {

    abstract fun geometryReportDao() : GeometryReportDao

}