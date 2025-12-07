package com.example.tccmobile.ui.screens.bibliotecarioTicketsScreen

import com.example.tccmobile.data.entity.Ticket

data class BiblioTicketsState(
    val tickets: List<Ticket> = emptyList(),
    val filteredTickets: List<Ticket> = emptyList(),

    val countsTickets: Int = 0,
    val countPending: Int = 0,
    val countEvaluted: Int = 0,
    val countClosed: Int = 0,

    val searchText: String = "",
    val selectedFilter: String = "Todos", // Filtro padrão
    val isLoading: Boolean = false,
    val error: String? = null
)
