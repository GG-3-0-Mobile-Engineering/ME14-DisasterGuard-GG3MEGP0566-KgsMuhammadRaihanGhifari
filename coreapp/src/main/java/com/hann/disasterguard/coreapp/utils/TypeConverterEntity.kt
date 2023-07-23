package com.hann.disasterguard.coreapp.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.hann.disasterguard.coreapp.data.remote.response.archive.Geometry
import com.hann.disasterguard.coreapp.data.remote.response.archive.Properties

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




}