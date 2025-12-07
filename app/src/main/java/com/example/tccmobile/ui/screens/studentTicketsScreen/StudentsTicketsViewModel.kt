package com.example.tccmobile.ui.screens.studentTicketsScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.tccmobile.ui.theme.*

class StudentTicketsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(StudantsTicketsState())
    val uiState = _uiState.asStateFlow()

    init {
        carregarTicketsMock()
    }

    private fun carregarTicketsMock() {
        val tagAvaliado = TicketTagStatus(
            label = "Avaliado",
            containerColor = StatusContainerAvaliado,
            contentColor = StatusTextAvaliado,
            icon = null
        )

        val tagPendente = TicketTagStatus(
            label = "Pendente",
            containerColor = StatusContainerPendente,
            contentColor = StatusTextPendente,
            icon = null,
        )

        // --- Dados Fictícios (Mock) ---
        val listaTickets = listOf(
            Ticket(
                id = "1",
                titulo = "Desenvolvimento de Sistema Web para Gestão de Biblioteca",
                categoria = "Engenharia de Software",
                dataAbertura = "15/10/2024",
                dataAtualizacao = "20/10/2024",
                notificacoes = 1,
                tags = listOf(tagAvaliado, tagPendente)
                // nomeAluno e atribuidoPara são nulos pois é a visão do próprio aluno
            ),
            Ticket(
                id = "2",
                titulo = "Aplicação Mobile para Monitoramento de Saúde",
                categoria = "Sistemas de Informação",
                dataAbertura = "01/11/2024",
                dataAtualizacao = "05/11/2024",
                notificacoes = 0,
                tags = listOf(tagAvaliado, tagPendente)
            ),
            Ticket(
                id = "3",
                titulo = "Aplicação Mobile para Monitoramento de Saúde",
                categoria = "Sistemas de Informação",
                dataAbertura = "01/11/2024",
                dataAtualizacao = "05/11/2024",
                notificacoes = 1,
                tags = listOf(tagAvaliado, tagPendente)
            )

        )

        _uiState.value = StudantsTicketsState(tickets = listaTickets)
    }
}