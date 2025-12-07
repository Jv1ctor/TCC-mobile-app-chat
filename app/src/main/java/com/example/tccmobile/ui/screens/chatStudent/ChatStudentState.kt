package com.example.tccmobile.ui.screens.chatStudent

import android.net.Uri
import com.example.tccmobile.data.entity.Message

data class ChatStudentState(

//    Geral
    val id: String = "",
    val theme: String = "",
    val course: String = "",
    val status: String = "",

//  Informações para Bibliotecario
    val registry: String = "",
    val email: String = "",
    val author: String = "",

    val inputMessage: String = "",
    val fileName: String = "",
    val fileUri: Uri? = null,
    val isAttachLoading: Boolean = false,

    val messages: List<Message> = listOf(),

    val isLoading: Boolean = false,
    val chatError: String? = null,

    val isStudent: Boolean = true

)
