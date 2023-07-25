package com.hann.disasterguard.coreapp.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.hann.disasterguard.coreapp.data.remote.response.archive.Geometry
import com.hann.disasterguard.coreapp.data.remote.response.archive.Properties
import com.hann.disasterguard.coreapp.domain.model.GeometryFlood

class TypeConverterEntity {

    @TypeConverter
    fun fromProperties(properties: Properties?): String? {
        return Gson().toJson(properties)
    }

    @TypeConverter
    fun toProperties(propertiesJson: String?): Properties? {
        return Gson().fromJson(propertiesJson, Properties::class.java)
    }


    @TypeConverter
    fun fromGeometry(geometry: Geometry?): String? {
        return Gson().toJson(geometry)
    }

    @TypeConverter
    fun toGeometry(geometryJson: String?): Geometry? {
        return Gson().fromJson(geometryJson, Geometry::class.java)
    }

    @TypeConverter
    fun fromGeometryFlood(geometry: GeometryFlood?): String? {
        return Gson().toJson(geometry)
    }

    @TypeConverter
    fun toGeometryFlood(geometryJson: String?): GeometryFlood? {
        return Gson().fromJson(geometryJson, GeometryFlood::class.java)
    }


}