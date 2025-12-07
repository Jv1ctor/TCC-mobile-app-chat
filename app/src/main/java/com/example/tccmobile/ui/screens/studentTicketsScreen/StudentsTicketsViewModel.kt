package com.example.tccmobile.ui.screens.studentTicketsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tccmobile.data.entity.Ticket
import com.example.tccmobile.data.repository.AuthRepository
import com.example.tccmobile.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudentTicketsViewModel(
    val ticketRepository: TicketRepository = TicketRepository(),
    val authRepository: AuthRepository = AuthRepository()
): ViewModel() {
    private val _uiState = MutableStateFlow(StudantsTicketsState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregarTickets()
        }
    }

    private fun setTicketList(tickets: List<Ticket>){
        _uiState.update {
            it.copy( tickets = it.tickets + tickets)
        }
    }

    private fun carregarTickets() {
        viewModelScope.launch {
            val user = authRepository.getUserInfo()

            user?.id?.let { id ->
                val tickets = ticketRepository.getAllTicketByStudent(id)

                setTicketList(tickets)
            }

        }
    }
}