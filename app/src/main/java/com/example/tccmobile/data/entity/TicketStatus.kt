package com.example.tccmobile.data.entity

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TicketStatus(
    val label: String,
    val containerColor: Color,
    val contentColor: Color,
    val icon: ImageVector?
)