package com.hann.disasterguard.coreapp.data.remote.response.report

data class Properties(
    val created_at: String,
    val disaster_type: String,
    val image_url: String,
    val pkey: String,
    val report_data: Any,
    val source: String,
    val status: String,
    val tags: Tags,
    val text: String,
    val title: String,
    val url: Any
)