package com.hann.disasterguard.coreapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hann.disasterguard.coreapp.data.remote.response.archive.Geometry
import com.hann.disasterguard.coreapp.data.remote.response.archive.Properties
import com.hann.disasterguard.coreapp.utils.TypeConverterEntity


@Entity(tableName = "archiveReport")
data class ArchiveReportEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "geometry")
    @TypeConverters(TypeConverterEntity::class)
    val geometry: Geometry,

    @ColumnInfo(name = "properties")
    @TypeConverters(TypeConverterEntity::class)
    val properties: Properties,

    @ColumnInfo(name = "type")
    val type: String
)