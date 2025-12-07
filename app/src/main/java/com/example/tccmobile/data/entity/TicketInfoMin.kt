package com.example.tccmobile.data.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlin.time.Instant

data class TicketInfoMin(
    val id: Int,
    val subject: String,
    val course: String,
    val status: TicketStatus,
    val createBy: String,
)