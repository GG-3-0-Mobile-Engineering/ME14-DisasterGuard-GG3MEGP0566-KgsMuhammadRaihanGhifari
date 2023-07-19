package com.hann.disasterguard.coreapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hann.disasterguard.coreapp.data.remote.response.report.Properties
import com.hann.disasterguard.coreapp.utils.TypeConverterEntity


@Entity(tableName = "geometryReport")
data class GeometryReportEntity (

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "coordinates")
    @TypeConverters(TypeConverterEntity::class)
    val coordinates: List<Double>,

    @ColumnInfo(name = "properties")
    val properties: Properties,

    @ColumnInfo(name = "type")
    val type: String
)