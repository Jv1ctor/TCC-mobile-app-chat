package com.example.tccmobile.ui.screens.bibliotecarioTicketsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tccmobile.ui.components.bibliotecaria.FilterSection
import com.example.tccmobile.ui.components.bibliotecaria.HeaderTickets
import com.example.tccmobile.ui.components.bibliotecaria.SearchBarTickets
import com.example.tccmobile.ui.components.utils.BottomNavItem
import com.example.tccmobile.ui.components.utils.BottomNavigationBar
import com.example.tccmobile.ui.components.utils.TicketCard
import com.example.tccmobile.ui.theme.BackgroundPage

@Composable
fun BiblioTicketsScreen(
    viewModel: BiblioTicketsViewModel = viewModel(),
    navigateBarItems: List<BottomNavItem>,
    currentRoute: String,
    onTicketClick: (String) -> Unit,
    onDashboardClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPage)
    ) {
        // 1. Header
        HeaderTickets(
            countTotal = uiState.tickets.size,
            countOpen = uiState.tickets.count { it.tags.any { tag -> tag.label == "Aberto" } },
            onDashboardClick = onDashboardClick
        )

        // 2. Conteúdo Central
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // 3. Filtros e Busca
            FilterSection(
                selectedFilter = uiState.selectedFilter,
                onFilterSelected = viewModel::onFilterSelected,
            )

            SearchBarTickets(
                searchText = uiState.searchText,
                onSearchChange = {
                    viewModel.onSearchTextChanged(it)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))


            // 5. Lista de Tickets
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(uiState.filteredTickets) { ticket ->
                    TicketCard(
                        ticket = ticket,
                        showStudentInfo = true, // Visão da bibliotecária
                        onClick = { onTicketClick(ticket.id) }
                    )
                }
            }
        }

        // 6. Barra de Navegação Inferior
        BottomNavigationBar(
            items = navigateBarItems,
            currentRoute = currentRoute,
        )
    }
}
