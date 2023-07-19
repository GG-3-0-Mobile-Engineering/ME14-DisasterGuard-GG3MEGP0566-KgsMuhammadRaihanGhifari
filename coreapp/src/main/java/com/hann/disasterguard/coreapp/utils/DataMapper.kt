package com.hann.disasterguard.coreapp.utils

import com.hann.disasterguard.coreapp.data.local.entity.GeometryReportEntity
import com.hann.disasterguard.coreapp.data.remote.response.report.GeometryReportItem
import com.hann.disasterguard.coreapp.domain.model.GeometryReport

object DataMapper {

    fun mapResponsesToEntities(input: List<GeometryReportItem>): List<GeometryReportEntity> {
        val geometryList = ArrayList<GeometryReportEntity>()
        input.map {
            val geometry = GeometryReportEntity(
                id = it.properties.pkey,
                type = it.type,
                coordinates = it.coordinates,
                properties = it.properties
            )
            geometryList.add(geometry)
        }
        return geometryList
    }

    fun mapEntitiesToDomain(input: List<GeometryReportEntity>): List<GeometryReport> =
        input.map {
            GeometryReport(
                type = it.type,
                coordinates = it.coordinates,
                properties = it.properties
            )
        }

}