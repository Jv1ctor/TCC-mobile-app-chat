package com.example.tccmobile.data.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class MessageWithUserInfoDto @OptIn(ExperimentalTime::class) constructor(
    @SerialName("id")
    val id: Int,

    @SerialName("content")
    val content: String,

    @SerialName("sender_id")
    val senderId: String,

    @SerialName("ticket_id")
    val ticketId: Int,

    @Contextual
    @SerialName("created_at")
    val createdAt: Instant,

    @SerialName("name")
    val name: String,

    @SerialName("email")
    val email: String,

    @SerialName("registry")
    val registry: String,

    @SerialName("file_url")
    val fileUrl: String?,

    @SerialName("file_name")
    val fileName: String?,

    @SerialName("file_size")
    val fileSize: Long?,

    @SerialName("file_type")
    val fileType: String?,

)
