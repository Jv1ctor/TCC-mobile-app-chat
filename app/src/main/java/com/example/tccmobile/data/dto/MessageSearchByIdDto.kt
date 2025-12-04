package com.example.tccmobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MessageSearchByIdDto(
    @SerialName("p_id")
    val id: Int
)
