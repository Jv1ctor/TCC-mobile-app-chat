package com.example.tccmobile.ui.theme.Tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tccmobile.TicketApp.Ticket
import com.example.tccmobile.TicketApp.TicketStatus
import kotlinx.coroutines.launch


class TicketViewModel(private val repo: TicketRepository = TicketRepository()): ViewModel() {
    private val _tickets = androidx.compose.runtime.mutableStateListOf<Ticket>()
    val tickets: List<Ticket> get() = _tickets


    init {
// manter exemplo local — em produção busque do Firestore
    }


    fun addTicket(ticket: Ticket) {
        _tickets.add(0, ticket)
    }


    fun createChatForTicket(ticket: Ticket, onComplete: (String) -> Unit) {
        viewModelScope.launch {
// assumimos que ticket.id existe e que auth uid obtido externamente
            val uid = com.google.firebase.auth.ktx.auth.currentUser?.uid ?: "anon"
            val chatId = repo.createChatForTicket(ticket.id, uid, null)
            onComplete(chatId)
        }
    }
}