package com.example.tccmobile.ui.theme.Chat

import android.app.DownloadManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tccmobile.Model.Message
import com.example.tccmobile.model.Message
import com.example.tccmobile.ui.theme.Tickets.TicketRepository
import com.example.tccmobile.ui.tickets.TicketRepository
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class ChatViewModel(private val repo: TicketRepository = TicketRepository()): ViewModel() {
    val events = Channel<String>(Channel.BUFFERED)


    fun sendMessage(chatId: String, text: String, fromUid: String, fromName: String) {
        viewModelScope.launch {
            val msg = Message(
                fromUid = fromUid,
                fromName = fromName,
                text = text
            )
            repo.sendMessage(chatId, msg)
            events.trySend("sent")
        }
    }


    fun messagesQuery(chatId: String): DownloadManager.Query = repo.messagesCollection(chatId)
}