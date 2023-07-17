package com.hann.disasterguard.coreapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hann.disasterguard.coreapp.data.local.dao.GeometryReportDao
import com.hann.disasterguard.coreapp.data.local.entity.GeometryReportEntity

@Database(entities = [GeometryReportEntity::class], version = 1, exportSchema = true)
abstract class DisasterGuardDatabase : RoomDatabase() {

    abstract fun geometryReportDao() : GeometryReportDao

}