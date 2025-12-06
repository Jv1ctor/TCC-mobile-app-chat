package com.example.tccmobile.helpers

import kotlinx.datetime.format
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.toJavaInstant


@OptIn(ExperimentalTime::class)
fun formattedInstant(instant: Instant): String {
    val formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm")

    return instant
        .toJavaInstant()
        .atZone(ZoneId.systemDefault())
        .format(formatter)
        .replace(" ", " às ")
}