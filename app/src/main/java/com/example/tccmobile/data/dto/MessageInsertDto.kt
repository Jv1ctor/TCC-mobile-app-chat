package com.example.tccmobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageInsertDto(

    @SerialName("content")
    val content: String,

    @SerialName("sender_id")
    val senderId: String,

    @SerialName("ticket_id")
    val ticketId: Int,
)
