package com.example.tccmobile.data.entity

import com.example.tccmobile.ui.theme.StatusContainerAvaliado
import com.example.tccmobile.ui.theme.StatusContainerPendente
import com.example.tccmobile.ui.theme.StatusTextAvaliado
import com.example.tccmobile.ui.theme.StatusTextPendente
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Ticket @OptIn(ExperimentalTime::class) constructor(
    val id: Int,
    val subject: String,
    val status: TicketStatus,
    val createdAt: Instant,
    val updatedAt: Instant,
    val course: String,

    val authorName: String? = null,
)
