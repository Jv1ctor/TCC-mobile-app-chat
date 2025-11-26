package com.example.tccmobile.ui.screens.newTicketScreen

import android.net.Uri

data class NewTicketState(
    val temaTcc: String = "",
    val curso: String = "",
    val observacoes: String = "",


    val anexoUri: Uri? = null,
    val anexoNomeArquivo: String = "",


    val isLoading: Boolean = false,

    val ticketError: String? = null,
    val campoThemeError: String? = null,
    val campoCursorError: String? = null
)
