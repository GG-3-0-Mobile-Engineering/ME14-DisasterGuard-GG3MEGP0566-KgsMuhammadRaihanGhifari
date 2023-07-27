package com.hann.disasterguard.coreapp.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateFormatter {
    fun formatDate(currentDateString: String): String {
        val instant = Instant.parse(currentDateString)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm")
            .withZone(ZoneId.of(TimeZone.getDefault().id))
        return formatter.format(instant)
    }
}