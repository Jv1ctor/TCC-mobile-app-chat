package com.example.tccmobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketInsertDto(
    val id: Int,

    val subject: String,

    val status: String,

    val remark: String,

    val course: String,

    @SerialName("create_by")
    val createBy: String
)