package com.example.tccmobile.ui.screens.studentTicketsScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Schedule
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.tccmobile.ui.theme.*

class StudentsTicketsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StudentsTicketsState())
    val uiState = _uiState.asStateFlow()

    init {
        carregarTicketsMock()
    }

    private fun carregarTicketsMock() {
        val tagAnalisado = TicketTagStatus(
            label = "Analisado",
            containerColor = StatusAnalisadoBg,
            contentColor = StatusAnalisadoText,
            icon = Icons.Outlined.Description
        )

        val tagAvaliado = TicketTagStatus(
            label = "Avaliado",
            containerColor = StatusAvaliadoBg,
            contentColor = StatusAvaliadoText,
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

        // --- Dados Fictícios (Mock) ---
        val listaTickets = listOf(
            Ticket(
                id = "1",
                titulo = "Desenvolvimento de Sistema Web para Gestão de Biblioteca",
                categoria = "Engenharia de Software",
                dataAbertura = "15/10/2024",
                dataAtualizacao = "20/10/2024",
                notificacoes = 1,
                tags = listOf(tagAnalisado, tagAvaliado),
                // nomeAluno e atribuidoPara são nulos pois é a visão do próprio aluno
            ),
            Ticket(
                id = "2",
                titulo = "Aplicação Mobile para Monitoramento de Saúde",
                categoria = "Sistemas de Informação",
                dataAbertura = "01/11/2024",
                dataAtualizacao = "05/11/2024",
                notificacoes = 0,
                tags = listOf(tagAberto, tagPendente)
            ),
            Ticket(
                id = "3",
                titulo = "Aplicação Mobile para Monitoramento de Saúde",
                categoria = "Sistemas de Informação",
                dataAbertura = "01/11/2024",
                dataAtualizacao = "05/11/2024",
                notificacoes = 1,
                tags = listOf(tagAberto, tagPendente)
            )

        )

        _uiState.value = StudentsTicketsState(tickets = listaTickets)
    }
}