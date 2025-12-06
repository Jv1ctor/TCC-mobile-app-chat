package com.example.tccmobile.data.repository

import android.util.Log
import com.example.tccmobile.data.dto.TicketDto
import com.example.tccmobile.data.dto.TicketListDto
import com.example.tccmobile.data.entity.Ticket
import com.example.tccmobile.data.entity.TicketInfoMin
import com.example.tccmobile.data.supabase.SupabaseClient.client
import com.example.tccmobile.helpers.transformTicketStatus
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
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

    @OptIn(ExperimentalTime::class)
    suspend fun getAllTicketByStudent(userId: String): List<Ticket>{
        return try {

            val join = Columns.raw("""
                id, subject, status, created_at, updated_at, course,
                user:users(*)
                """.trimIndent())

            val ticket = client.postgrest.from("tickets").select(join){
                filter {
                    eq("create_by", userId)
                }
            }.decodeList<TicketListDto>()

            ticket.map {
                Ticket(
                    id = it.id,
                    subject = it.subject,
                    status = transformTicketStatus(it.status),
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt,
                    course = it.course,
                    authorName = it.user.name
                )
            }
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "Erro ao tentar consultar tickets abertos: $e")
            listOf()
        }
    }
}