package com.hann.disasterguard.coreapp.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.hann.disasterguard.coreapp.data.remote.response.report.Properties

class TypeConverterEntity {

    @TypeConverter
    fun fromCoordinates(coordinates: List<Double>?): String? {
        return coordinates?.joinToString(",")
    }

    @TypeConverter
    fun toCoordinates(coordinatesString: String?): List<Double>? {
        return coordinatesString?.split(",")?.map { it.toDouble() }
    }

    @TypeConverter
    fun fromProperties(properties: Properties?): String? {
        return Gson().toJson(properties)
    }

    @TypeConverter
    fun toProperties(propertiesJson: String?): Properties? {
        return Gson().fromJson(propertiesJson, Properties::class.java)
    }

}