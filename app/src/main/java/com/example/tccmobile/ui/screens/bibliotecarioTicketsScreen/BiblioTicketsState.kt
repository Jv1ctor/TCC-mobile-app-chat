package com.example.tccmobile.ui.screens.bibliotecarioTicketsScreen

import com.example.tccmobile.ui.screens.studentTicketsScreen.Ticket

data class BiblioTicketsState(
    val tickets: List<Ticket> = emptyList(),
    val filteredTickets: List<Ticket> = emptyList(),
    val searchText: String = "",
    val selectedFilter: String = "Todos (4)", // Filtro padrão
    val isLoading: Boolean = false,
    val error: String? = null
)
