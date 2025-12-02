package com.example.tccmobile.data.dto

import kotlinx.datetime.TimeZone
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class AttachmentDto @OptIn(ExperimentalTime::class) constructor(
    @SerialName("id")
    val id: Int,

    @SerialName("file_url")
    val fileUrl: String,

    @SerialName("file_name")
    val fileName: String,

    @SerialName("file_size")
    val fileSize: Int,

    @SerialName("file_type")
    val fileType: String,

    @Contextual
    @SerialName("created_at")
    val createdAt: Instant,

    @SerialName("message_id")
    val messageId: Int
)
