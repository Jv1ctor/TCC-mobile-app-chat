package com.example.tccmobile.ui.screens.bibliotecarioTicketsScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Schedule
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tccmobile.data.entity.Ticket
import com.example.tccmobile.data.repository.AuthRepository
import com.example.tccmobile.data.repository.TicketRepository
import com.example.tccmobile.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.Locale.getDefault

class BiblioTicketsViewModel(
    val ticketRepository: TicketRepository = TicketRepository(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(BiblioTicketsState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregarTickets()
        }
    }

    private fun setTicketListAppend(tickets: List<Ticket>){
        _uiState.update {
            it.copy( tickets = it.tickets + tickets)
        }
    }

    private fun setTicketsList(tickets: List<Ticket>){
        _uiState.update {
            it.copy(tickets = tickets)
        }
    }

    private fun setFilteredTickets(tickets: List<Ticket>){
        _uiState.update {
            it.copy(filteredTickets = tickets)
        }
    }

    private fun setCountTickets(){
        _uiState.update {
            it.copy(countsTickets = it.tickets.size)
        }
    }

    private fun setCountPending(){
        _uiState.update {
            it.copy(
                countPending = it.tickets.filter { ticket ->
                    ticket.status.label.lowercase(getDefault()) == "pendente"
                }.size
            )
        }
    }

    private fun setCountEvalueted(){
        _uiState.update {
            it.copy(
                countEvaluted = it.tickets.filter { ticket ->
                    ticket.status.label.lowercase(getDefault()) == "avaliado"
                }.size
            )
        }
    }

    private fun setCountClosed(){
        _uiState.update {
            it.copy(
                countClosed = it.tickets.filter { ticket ->
                    ticket.status.label.lowercase(getDefault()) == "fechado"
                }.size
            )
        }
    }

    private fun carregarTickets() {
        viewModelScope.launch {
            val filter = _uiState.value.selectedFilter.lowercase(getDefault())

            if (filter != "todos"){
                val tickets = ticketRepository.getAllByStatus(filter)
                setFilteredTickets(tickets)
            }else{
                val tickets = ticketRepository.getAllTicket()
                setTicketsList(tickets)
                setFilteredTickets(_uiState.value.tickets)
            }

            setCountTickets()
            setCountPending()
            setCountEvalueted()
            setCountClosed()
        }
    }

    fun onSearchTextChanged(text: String) {
        _uiState.update { it.copy(searchText = text) }
        filterTickets()
    }

    fun onFilterSelected(filter: String) {
        _uiState.update { it.copy(selectedFilter = filter) }
        carregarTickets()
    }

    private fun filterTickets() {
//        val currentState = _uiState.value
//        val query = currentState.searchText
//        val filter = currentState.selectedFilter
//
//        val ticketsFilteredByStatus = if (filter == "Todos (4)") {
//            currentState.tickets
//        } else {
//            // Extrai o status do texto do filtro (ex: "Abertos (2)" -> "Aberto")
//            val statusToFilter = filter.substringBefore(" ").removeSuffix("s")
//            currentState.tickets.filter {
//                it.tags.any { tag -> tag.label.equals(statusToFilter, ignoreCase = true) }
//            }
//        }
//
//        val ticketsFilteredBySearch = if (query.isBlank()) {
//            ticketsFilteredByStatus
//        } else {
//            ticketsFilteredByStatus.filter {
//                it.titulo.contains(query, ignoreCase = true) ||
//                        it.nomeAluno?.contains(query, ignoreCase = true) == true ||
//                        it.categoria.contains(query, ignoreCase = true)
//            }
//        }
//
//        _uiState.update { it.copy(filteredTickets = ticketsFilteredBySearch) }
    }


    private fun loadMockTickets() {
//        val tagAnalisado = TicketTagStatus(
//            label = "Avaliado",
//            containerColor = StatusContainerAvaliado,
//            contentColor = StatusTextAvaliado,
//            icon = Icons.Outlined.Description
//        )
//
//        val tagFechado = TicketTagStatus(
//            label = "Concluído",
//            containerColor = StatusContainerConcluido, // Usando a cor de pendente para fechado, ajuste se necessário
//            contentColor = StatusTextConcluido,
//            icon = null
//        )
//
//        val tagPendente = TicketTagStatus(
//            label = "Pendente",
//            containerColor = StatusContainerPendente,
//            contentColor = StatusTextPendente,
//            icon = null,
//        )
//
//        val ticketList = listOf(
//            Ticket(
//                id = "1",
//                titulo = "Desenvolvimento de Sistema Web para Gestão de Biblioteca",
//                categoria = "Engenharia de Software",
//                dataAbertura = "15/10/2024",
//                dataAtualizacao = "20/10/2024",
//                notificacoes = 1,
//                tags = listOf(tagAnalisado), // Status: Analisado
//                nomeAluno = "Breno Sampaio Gonçalves",
//                atribuidoPara = "Rebeca Sena"
//            ),
//            Ticket(
//                id = "2",
//                titulo = "Aplicação Mobile para Monitoramento de Saúde",
//                categoria = "Sistemas de Informação",
//                dataAbertura = "01/11/2024",
//                dataAtualizacao = "05/11/2024",
//                notificacoes = 0,
//                tags = listOf(tagAnalisado), // Status: Aberto
//                nomeAluno = "Maria Clara",
//            ),
//            Ticket(
//                id = "3",
//                titulo = "Inteligência Artificial para Análise de Dados",
//                categoria = "Ciência da Computação",
//                dataAbertura = "20/11/2024",
//                dataAtualizacao = "21/11/2024",
//                notificacoes = 1,
//                tags = listOf(tagPendente), // Status: Aberto
//                nomeAluno = "João Pedro",
//            ),
//            Ticket(
//                id = "4",
//                titulo = "Segurança em Redes de Computadores",
//                categoria = "Redes de Computadores",
//                dataAbertura = "10/09/2024",
//                dataAtualizacao = "12/09/2024",
//                notificacoes = 0,
//                tags = listOf(tagFechado), // Status: Fechado
//                nomeAluno = "Ana Beatriz",
//            )
//        )
//        // No início, a lista filtrada é igual à lista completa
//        _uiState.value = BiblioTicketsState(tickets = ticketList, filteredTickets = ticketList)
//        // Aplicar o filtro inicial para garantir que a contagem e o filtro padrão sejam respeitados
//        filterTickets()
    }
}
