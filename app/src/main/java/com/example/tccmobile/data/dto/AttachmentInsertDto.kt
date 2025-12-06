package com.example.tccmobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttachmentInsertDto(
    @SerialName("file_url")
    val fileUrl: String,

    @SerialName("file_name")
    val fileName: String,

    @SerialName("file_size")
    val fileSize: Long,

    @SerialName("file_type")
    val fileType: String,

    @SerialName("message_id")
    val messageId: Int
)
