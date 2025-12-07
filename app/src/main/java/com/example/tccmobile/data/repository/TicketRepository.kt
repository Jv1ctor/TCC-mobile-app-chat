package com.example.tccmobile.data.repository

import android.util.Log
import com.example.tccmobile.data.dto.TicketDto
import com.example.tccmobile.data.dto.TicketInsertDto
import com.example.tccmobile.data.dto.TicketListDto
import com.example.tccmobile.data.entity.Ticket
import com.example.tccmobile.data.entity.TicketInfoMin
import com.example.tccmobile.data.supabase.SupabaseClient.client
import com.example.tccmobile.helpers.generateTicketId
import com.example.tccmobile.helpers.transformTicketStatus
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.selects.select
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

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
                order("updated_at", order = Order.DESCENDING)
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

    @OptIn(ExperimentalTime::class)
    suspend fun createTicket(
        subject: String,
        status: String,
        remark: String,
        course: String,
        userId: String): TicketInfoMin?{
        return try {
            val newTicket = TicketInsertDto(
                id = generateTicketId(),
                subject = subject,
                status = status,
                remark = remark,
                course = course,
                createBy = userId
            )

            val ticket = client.postgrest.from("tickets").insert(newTicket){
                select()
            }.decodeSingle<TicketDto>()

            TicketInfoMin(
                id = ticket.id,
                subject = ticket.subject,
                status = ticket.status,
                course = ticket.course,
                createBy = ticket.createBy
            )
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "Erro ao criar ticket: $e")
            null
        }
    }

    @OptIn(ExperimentalTime::class)
    suspend fun updatedLastInteraction(ticketId: Int){
        val nowAt = Clock.System.now().toString()
        client.postgrest.from("tickets").update({
            set("updated_at", nowAt)
        }){
            filter {
                eq("id", ticketId)
            }
        }
    }
}