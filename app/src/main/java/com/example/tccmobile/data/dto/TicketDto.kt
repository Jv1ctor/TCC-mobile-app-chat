package com.example.tccmobile.data.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@Serializable
data class TicketDto @OptIn(ExperimentalTime::class) constructor(
    val id: Int,

    val subject: String,

    val status: String,

    val remark: String,

    val course: String,

    @Contextual
    @SerialName("created_at")
    val createdAt: Instant,

    @SerialName("create_by")
    val createBy: String
)