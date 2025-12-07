package com.example.tccmobile.data.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@Serializable
data class TicketListDto @OptIn(ExperimentalTime::class) constructor(
    val id: Int,

    val subject: String,

    val status: String,

    val course: String,

    val user: UserDto,

    @Contextual
    @SerialName("created_at")
    val createdAt: Instant,

    @Contextual
    @SerialName("updated_at")
    val updatedAt: Instant,
)