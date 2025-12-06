package com.example.tccmobile.data.entity

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Message @OptIn(ExperimentalTime::class) constructor(
    val id: Int,
    val content: String,
    val senderName: String,
    val ticketId: String,
    val createdAt: Instant,
    val isSent: Boolean,

    var fileUrl: String? = null,
    var fileName: String? = null,
    var fileSize: Long? = null,
    var fileType: String? = null,

    )