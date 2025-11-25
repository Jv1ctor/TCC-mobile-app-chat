package com.example.tccmobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id")
    val id: String,

    @SerialName("email")
    val email: String,

    @SerialName("name")
    val name: String,

    @SerialName("registry")
    val registry: String,

    @SerialName("created_at")
    val createAt: String?
)
