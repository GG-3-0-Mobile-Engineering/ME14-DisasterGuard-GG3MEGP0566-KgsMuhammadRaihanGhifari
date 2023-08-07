package com.hann.disasterguard.utils

import com.hann.disasterguard.coreapp.data.remote.response.report.Properties
import com.hann.disasterguard.coreapp.data.remote.response.report.Tags
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

}