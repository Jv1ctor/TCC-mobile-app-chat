package com.example.tccmobile.Model

import com.google.firebase.Timestamp


data class Message(
    val id: String = "",
    val fromUid: String = "",
    val fromName: String = "",
    val text: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val attachmentUrl: String? = null // se for enviar TCC corrigido como arquivo
)