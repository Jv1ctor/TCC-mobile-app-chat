package com.example.tccmobile.data.repository

import android.util.Log
import com.example.tccmobile.data.dto.MessageDto
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
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.selects.select
import kotlinx.serialization.json.Json
import java.util.Locale
import java.util.Locale.getDefault
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class TicketRepository {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var currentChannel: RealtimeChannel? = null

    suspend fun startListening(callback: (id: Int) -> Unit){
        currentChannel = client.realtime.channel("ticket-update")

        val changeFlow = currentChannel!!.postgresChangeFlow<PostgresAction.Insert>(schema = "public"){
            table = "tickets"
        }

        changeFlow.onEach {
            val ticket = it.record
            val jsonString = Json.encodeToString(ticket)
            val ticketDecoded = Json.decodeFromString<TicketDto>(jsonString)

            Log.d("SUPABASE_DEBUG", jsonString)

            Log.d("SUPABASE_DEBUG","Realtime → novo ticket")
            callback(ticketDecoded.id)

        }.launchIn(scope)


        currentChannel!!.subscribe()
    }

    suspend fun clear(){
        currentChannel?.let { channel ->
            try {
                channel.unsubscribe()
            } catch (e: Exception) {
                Log.e("MessageRepository", "Erro ao desinscrever canal", e)
            }
        }
        currentChannel = null
        scope.cancel()
    }

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
                status = transformTicketStatus(ticket.status),
                createBy = ticket.createBy
            )
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "Erro ao tentar consultar ticket com id = $ticketId. Erro: $e")
            null
        }
    }

    @OptIn(ExperimentalTime::class)
    suspend fun getTicketFullInfo(ticketId: Int): Ticket? {
        return try{

            val join = Columns.raw("""
                id, subject, status, created_at, updated_at, course,
                user:users(*)
                """.trimIndent())

            val ticket = client.postgrest.from("tickets").select(join){
                filter {
                    eq("id", ticketId)
                }
            }.decodeSingle<TicketListDto>()

            Log.d("SUPABASE_DEBUG", "$ticket")

            Ticket(
                id = ticket.id,
                subject = ticket.subject,
                course = ticket.course,
                status = transformTicketStatus(ticket.status),
                createdAt = ticket.createdAt,
                updatedAt = ticket.updatedAt,
                authorName = ticket.user.name
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
    suspend fun getAllTicket(): List<Ticket>{
        return try {

            val join = Columns.raw("""
                id, subject, status, created_at, updated_at, course,
                user:users(*)
                """.trimIndent())

            val ticket = client.postgrest.from("tickets").select(join){
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
    suspend fun getAllByStatus(filter: String): List<Ticket>{
        return try {
            val join = Columns.raw("""
                id, subject, status, created_at, updated_at, course,
                user:users(*)
                """.trimIndent())

            val ticket = client.postgrest.from("tickets").select(join){
                filter {
                    eq("status", filter)
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
            Log.e("SUPABASE_DEBUG", "Erro ao tentar consultar tickets $filter: $e")
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
                status = transformTicketStatus(ticket.status),
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

    private suspend fun updatedStatus(ticketId: Int, status: String){
        client.postgrest.from("tickets").update({
            set("status", status.lowercase(getDefault()))
        }){
            filter {
                eq("id", ticketId)
            }
        }
    }

    suspend fun updatedStatusEvalueted(ticketId: Int){
        updatedStatus(ticketId, "avaliado")
    }

    suspend fun updatedStatusPending(ticketId: Int){
        updatedStatus(ticketId, "pendente")
    }

    suspend fun updatedStatusClosed(ticketId: Int){
        updatedStatus(ticketId, "fechado")
    }
}