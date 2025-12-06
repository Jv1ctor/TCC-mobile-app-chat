package com.example.tccmobile.data.repository

import android.util.Log
import com.example.tccmobile.data.dto.TicketDto
import com.example.tccmobile.data.entity.TicketInfoMin
import com.example.tccmobile.data.supabase.SupabaseClient.client
import io.github.jan.supabase.postgrest.postgrest
import kotlin.time.ExperimentalTime

class TicketRepository {
    @OptIn(ExperimentalTime::class)
    suspend fun getTicket(ticketId: Int): TicketInfoMin? {
        return try{

            val ticket = client.postgrest.from("tickets").select {
                filter {
                    eq("id", ticketId)
                }
            }.decodeSingle<TicketDto>()

            Log.d("SUPABASE_DEBUG", "$ticket")

            TicketInfoMin(
                id = ticket.id,
                subject = ticket.subject,
                course = ticket.course,
                status = ticket.status,
                createBy = ticket.createBy
            )
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "Erro ao tentar consultar ticket com id = $ticketId. Erro: $e")
            null
        }
    }
}