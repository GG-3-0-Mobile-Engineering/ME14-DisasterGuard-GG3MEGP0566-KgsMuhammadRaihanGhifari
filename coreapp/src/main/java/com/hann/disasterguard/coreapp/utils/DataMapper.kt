package com.hann.disasterguard.coreapp.utils

import com.hann.disasterguard.coreapp.data.local.entity.ArchiveReportEntity
import com.hann.disasterguard.coreapp.data.remote.response.archive.ArchiveReportItem
import com.hann.disasterguard.coreapp.data.remote.response.report.GeometryReportItem
import com.hann.disasterguard.coreapp.domain.model.ArchiveReport
import com.hann.disasterguard.coreapp.domain.model.GeometryReport

object DataMapper {

    fun mapResponseToDomainLive(input: List<GeometryReportItem>) :  List<GeometryReport> {
        val geometryList = ArrayList<GeometryReport>()
        input.map {
            val geometry = GeometryReport(
                type = it.type,
                coordinates = it.coordinates,
                properties = it.properties
            )
            geometryList.add(geometry)
        }
        return geometryList
    }


    fun mapResponsesToEntitiesArchive(input: List<ArchiveReportItem>): List<ArchiveReportEntity> {
        val archiveList = ArrayList<ArchiveReportEntity>()
        input.map {
            val archive = ArchiveReportEntity(
                id = it.properties.pkey,
                type = it.type,
                geometry = it.geometry,
                properties = it.properties
            )
            archiveList.add(archive)
        }
        return archiveList
    }

    fun mapEntitiesToDomainArchive(input: List<ArchiveReportEntity>): List<ArchiveReport> =
        input.map {
            ArchiveReport(
                type = it.type,
                geometry = it.geometry,
                properties = it.properties
            )
        }

}