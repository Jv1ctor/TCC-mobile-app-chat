package com.example.tccmobile.helpers

import com.example.tccmobile.data.entity.TicketStatus
import com.example.tccmobile.ui.theme.StatusContainerAvaliado
import com.example.tccmobile.ui.theme.StatusContainerPendente
import com.example.tccmobile.ui.theme.StatusTextAvaliado
import com.example.tccmobile.ui.theme.StatusTextPendente

fun transformTicketStatus(value: String?): TicketStatus{
    return when(value){
        "pendente" ->{
            TicketStatus(
                label = "Pendente",
                containerColor = StatusContainerPendente,
                contentColor = StatusTextPendente,
                icon = null,
            )
        }

        "avaliado" -> {
            TicketStatus(
                label = "Avaliado",
                containerColor = StatusContainerAvaliado,
                contentColor = StatusTextAvaliado,
                icon = null
            )
        }

        else -> {
            TicketStatus(
                label = "Pendente",
                containerColor = StatusContainerPendente,
                contentColor = StatusTextPendente,
                icon = null,
            )
        }
    }
}