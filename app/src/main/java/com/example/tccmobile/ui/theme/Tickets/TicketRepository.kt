package com.example.tccmobile.ui.theme.Tickets

import com.example.tccmobile.Model.Message
import com.example.tccmobile.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class TicketRepository {
    private val db = Firebase.firestore
    private val auth = Firebase.auth


    suspend fun createChatForTicket(ticketId: String, studentUid: String, librarianUid: String?): String {
// chatId pode ser ticketId por simplicidade
        val chatRef = db.collection("chats").document(ticketId)
        val data = mapOf(
            "ticketId" to ticketId,
            "studentUid" to studentUid,
            "librarianUid" to librarianUid
        )
        chatRef.set(data).await()
        return chatRef.id
    }


    suspend fun sendMessage(chatId: String, message: Message) {
        val messagesRef = db.collection("chats").document(chatId).collection("messages")
// Documento automático
        messagesRef.add(message).await()
    }


    fun messagesCollection(chatId: String) = db.collection("chats").document(chatId).collection("messages").orderBy("timestamp")
}