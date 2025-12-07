package com.example.tccmobile.data.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class UserDto @OptIn(ExperimentalTime::class) constructor(
    @SerialName("id")
    val id: String,

    @SerialName("email")
    val email: String,

    @SerialName("name")
    val name: String,

    @SerialName("registry")
    val registry: String,

    @Contextual
    @SerialName("created_at")
    val createdAt: Instant?,
)
