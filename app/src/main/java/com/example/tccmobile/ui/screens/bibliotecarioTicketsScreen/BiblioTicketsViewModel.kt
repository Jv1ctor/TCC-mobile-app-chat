package com.example.tccmobile.ui.screens.bibliotecarioTicketsScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Schedule
import androidx.lifecycle.ViewModel
import com.example.tccmobile.ui.screens.studentTicketsScreen.Ticket
import com.example.tccmobile.ui.screens.studentTicketsScreen.TicketTagStatus
import com.example.tccmobile.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BiblioTicketsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BiblioTicketsState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMockTickets()
    }

    fun onSearchTextChanged(text: String) {
        _uiState.update { it.copy(searchText = text) }
        filterTickets()
    }

    fun onFilterSelected(filter: String) {
        _uiState.update { it.copy(selectedFilter = filter) }
        filterTickets()
    }

    private fun filterTickets() {
        val currentState = _uiState.value
        val query = currentState.searchText
        val filter = currentState.selectedFilter

        val ticketsFilteredByStatus = if (filter == "Todos (4)") {
            currentState.tickets
        } else {
            // Extrai o status do texto do filtro (ex: "Abertos (2)" -> "Aberto")
            val statusToFilter = filter.substringBefore(" ").removeSuffix("s")
            currentState.tickets.filter {
                it.tags.any { tag -> tag.label.equals(statusToFilter, ignoreCase = true) }
            }
        }

        val ticketsFilteredBySearch = if (query.isBlank()) {
            ticketsFilteredByStatus
        } else {
            ticketsFilteredByStatus.filter {
                it.titulo.contains(query, ignoreCase = true) ||
                        it.nomeAluno?.contains(query, ignoreCase = true) == true ||
                        it.categoria.contains(query, ignoreCase = true)
            }
        }

        _uiState.update { it.copy(filteredTickets = ticketsFilteredBySearch) }
    }


    private fun loadMockTickets() {
        val tagAnalisado = TicketTagStatus(
            label = "Analisado",
            containerColor = StatusAnalisadoBg,
            contentColor = StatusAnalisadoText,
            icon = Icons.Outlined.Description
        )

        val tagFechado = TicketTagStatus(
            label = "Fechado",
            containerColor = StatusPendenteBg, // Usando a cor de pendente para fechado, ajuste se necessário
            contentColor = StatusPendenteText,
            icon = null
        )

        val tagAberto = TicketTagStatus(
            label = "Aberto",
            containerColor = StatusAbertoBg,
            contentColor = StatusAbertoText,
            icon = Icons.Outlined.Schedule
        )

        val tagPendente = TicketTagStatus(
            label = "Pendente",
            containerColor = StatusPendenteBg,
            contentColor = StatusPendenteText,
            icon = null,
        )

        val ticketList = listOf(
            Ticket(
                id = "1",
                titulo = "Desenvolvimento de Sistema Web para Gestão de Biblioteca",
                categoria = "Engenharia de Software",
                dataAbertura = "15/10/2024",
                dataAtualizacao = "20/10/2024",
                notificacoes = 1,
                tags = listOf(tagAnalisado), // Status: Analisado
                nomeAluno = "Breno Sampaio Gonçalves",
                atribuidoPara = "Rebeca Sena"
            ),
            Ticket(
                id = "2",
                titulo = "Aplicação Mobile para Monitoramento de Saúde",
                categoria = "Sistemas de Informação",
                dataAbertura = "01/11/2024",
                dataAtualizacao = "05/11/2024",
                notificacoes = 0,
                tags = listOf(tagAberto), // Status: Aberto
                nomeAluno = "Maria Clara",
            ),
            Ticket(
                id = "3",
                titulo = "Inteligência Artificial para Análise de Dados",
                categoria = "Ciência da Computação",
                dataAbertura = "20/11/2024",
                dataAtualizacao = "21/11/2024",
                notificacoes = 1,
                tags = listOf(tagAberto), // Status: Aberto
                nomeAluno = "João Pedro",
            ),
            Ticket(
                id = "4",
                titulo = "Segurança em Redes de Computadores",
                categoria = "Redes de Computadores",
                dataAbertura = "10/09/2024",
                dataAtualizacao = "12/09/2024",
                notificacoes = 0,
                tags = listOf(tagFechado), // Status: Fechado
                nomeAluno = "Ana Beatriz",
            )
        )
        // No início, a lista filtrada é igual à lista completa
        _uiState.value = BiblioTicketsState(tickets = ticketList, filteredTickets = ticketList)
        // Aplicar o filtro inicial para garantir que a contagem e o filtro padrão sejam respeitados
        filterTickets()
    }
}
