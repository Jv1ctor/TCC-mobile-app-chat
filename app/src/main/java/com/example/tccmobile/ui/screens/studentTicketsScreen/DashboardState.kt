package com.example.tccmobile.ui.screens.dashboardTicketsScreen

import androidx.compose.ui.graphics.Color

data class Ticket(
    val id: String,
    val titulo: String,
    val categoria: String,
    val dataAbertura: String,
    val dataAtualizacao: String,
    val tags: List<TicketTagStatus>,
    val notificacoes: Int = 0,
    val nomeAluno: String? = null,
    val atribuidoPara: String? = null
)

data class TicketTagStatus(
    val label: String,
    val containerColor: Color,
    val contentColor: Color,
    val hasIcon: Boolean = false
)

data class DashboardState(
    val tickets: List<Ticket> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)