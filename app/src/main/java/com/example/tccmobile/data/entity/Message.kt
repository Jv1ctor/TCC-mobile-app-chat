package com.example.tccmobile.data.entity

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Message @OptIn(ExperimentalTime::class) constructor(
    val id: Int,
    val content: String,
    val senderName: String,
    val ticketId: String,
    val createdAt: Instant,
    val isStudent: Boolean,
    val fileUrl: String? = null,
    val fileName: String? = null,
    val fileSize: Int? = null,
    val fileType: String? = null,
)