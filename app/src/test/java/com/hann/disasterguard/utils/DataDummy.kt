package com.hann.disasterguard.utils

import com.hann.disasterguard.coreapp.data.remote.response.archive.*
import com.hann.disasterguard.coreapp.data.remote.response.report.Properties
import com.hann.disasterguard.coreapp.data.remote.response.report.Tags
import com.hann.disasterguard.coreapp.domain.model.ArchiveReport
import com.hann.disasterguard.coreapp.domain.model.GeometryFlood
import com.hann.disasterguard.coreapp.domain.model.GeometryReport

object DataDummy {

    fun generateDummyListGeometryReport(): List<GeometryReport> {
        val fakeListDTO = ArrayList<GeometryReport>()

        for (i in 1..10) {
            val fakeGeometryItem = GeometryReport(
               coordinates = listOf(1.5, 2.0, 3.7, 4.2),
                properties = Properties(
                     created_at = "created_at",
                 disaster_type  = "flood",
             image_url = "image_url",
             pkey = "pkey",
             report_data= "report_data",
             source= "source",
             status= "status",
             tags= Tags(
                 instance_region_code = "code",
                 local_area_id = "id"
             ),
             text= "text",
             title= "title",
             url= "url"
                ),
                type = "Point"
            )

            fakeListDTO.add(fakeGeometryItem)
        }

        return fakeListDTO
    }

    fun generateDummyListArchiveReport(): List<ArchiveReport> {
        val fakeListDTO = ArrayList<ArchiveReport>()

        for (i in 1..10) {
            val fakeArchiveItem = ArchiveReport(
                properties = Properties(
                    created_at = "created_at",
                    disaster_type  = "flood",
                    image_url = "image_url",
                    pkey = "pkey",
                    report_data= ReportData(
                         accessabilityFailure = 1,
                         airQuality = 2,
                         condition = 3,
                         evacuationArea = true,
                         evacuationNumber = 1,
                         fireDistance = 1.1,
                         fireLocation = FireLocation(
                             1.1,
                             1.1
                         ),
                         fireRadius = FireRadius(
                             1.1,
                             1.1
                         ),
                         flood_depth = 1,
                         impact = 1,
                         personLocation = PersonLocation(
                             1.1,
                             1.1
                         ),
                         report_type = "repot_type",
                         structureFailure = 1,
                         visibility = 1,
                         volcanicSigns = listOf(1, 2, 37, 4)
                    ),
                    source= "source",
                    status= "status",
                    tags= Tags(
                         district_id = "",
                         instance_region_code ="",
                         local_area_id = "",
                         region_code = ""
                    ),
                    text= "text",
                    title= "title",
                    url= "url",
                    partner_code = "partner code",
                    partner_icon = "partner icon"
                ),
                type = "Point",
                geometry = Geometry(
                    coordinates = listOf(1.1, 1.2),
                    type = ""
                )
            )

            fakeListDTO.add(fakeArchiveItem)
        }

        return fakeListDTO
    }

    fun generateDummyListGeometryFlood(): List<GeometryFlood> {
        val fakeListDTO = ArrayList<GeometryFlood>()

        for (i in 1..10) {
            val fakeGeometryItem = GeometryFlood(
                type = "type",
                arcs = listOf(),
                properties = com.hann.disasterguard.coreapp.data.remote.response.flood.Properties(
                     area_id = "area_id",
                     area_name = "area_name",
                     city_name = "city_name",
                     geom_id = "geom_id",
                     last_updated = "last_updated",
                     parent_name = "parent_name",
                     state = 1
                )
            )

            fakeListDTO.add(fakeGeometryItem)
        }

        return fakeListDTO
    }

}