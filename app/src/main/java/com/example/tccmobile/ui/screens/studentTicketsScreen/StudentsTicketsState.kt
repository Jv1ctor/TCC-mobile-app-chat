package com.example.tccmobile.ui.screens.studentTicketsScreen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tccmobile.data.entity.Ticket

data class StudantsTicketsState(
    val tickets: List<Ticket> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)