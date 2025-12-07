package com.example.tccmobile.data.repository

import android.util.Log
import com.example.tccmobile.data.dto.MessageDto
import com.example.tccmobile.data.dto.MessageInsertDto
import com.example.tccmobile.data.dto.MessageSearchByIdDto
import com.example.tccmobile.data.dto.MessageWithUserInfoDto
import com.example.tccmobile.data.dto.MessageSearchByTicketDto
import com.example.tccmobile.data.entity.Message
import com.example.tccmobile.data.supabase.SupabaseClient.client
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json
import kotlin.time.ExperimentalTime

class MessageRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var activeChannelId: Int? = null
    private var currentChannel: RealtimeChannel? = null

    suspend fun startListening(channelId: Int, callback: (id: Int) -> Unit){
        activeChannelId = channelId
        currentChannel = client.realtime.channel("chat-$channelId")

        val changeFlow = currentChannel!!.postgresChangeFlow<PostgresAction>(schema = "public"){
            table = "messages"
        }

        changeFlow.onEach {
            when(it) {
                is PostgresAction.Delete -> Log.d("SUPABASE_DEBUG","Deleted: ${it.oldRecord}")
                is PostgresAction.Select -> Log.d("SUPABASE_DEBUG","Selected: ${it.record}")
                is PostgresAction.Insert -> {
                    val msg = it.record
                    val jsonString = Json.encodeToString(msg)
                    val msgDto = Json.decodeFromString<MessageDto>(jsonString)

                    Log.d("SUPABASE_DEBUG", jsonString)

                    if (msgDto.ticketId == channelId) {
                        Log.d("SUPABASE_DEBUG","Realtime → nova mensagem do ticket $channelId")
                        callback(msgDto.id)
                    }
                 }
                is PostgresAction.Update -> Log.d("SUPABASE_DEBUG","Updated: ${it.oldRecord} with ${it.record}")
            }}.launchIn(scope)

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
        activeChannelId = null
        scope.cancel()
    }


    @OptIn(ExperimentalTime::class)
    suspend fun getNewMessage(id: Int, userId: String): Message?{
        return try {
            val message = client.postgrest
                .rpc("get_message_more_info_by_id", MessageSearchByIdDto(id = id))
                .decodeSingle<MessageWithUserInfoDto>()


            Message(
                id = message.id,
                content = message.content,
                senderName = message.name,
                ticketId = message.ticketId.toString(),
                createdAt = message.createdAt,
                isSent = message.senderId == userId,
                fileSize = message.fileSize,
                fileName = message.fileName,
                fileUrl = message.fileUrl,
                fileType = message.fileType
            )
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "ERRO AO BUSCAR MENSAGEM", e)
            null
        }
    }

    @OptIn(ExperimentalTime::class)
    suspend fun sendMessage(content: String, ticketId: Int, senderId: String): MessageDto?{
        return try {
            val newMessage = MessageInsertDto(
                content = content,
                senderId = senderId,
                ticketId = ticketId,
            )

            if(content.isEmpty())
                return null

            client.postgrest.from("messages").insert(newMessage){
                select()
            }.decodeSingleOrNull<MessageDto>()
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "ERRO AO ENVIAR MENSAGEM", e)
            null
        }
    }

    @OptIn(ExperimentalTime::class)
    suspend fun listMessages(ticketId: Int, userId: String): List<Message>{
        return try {
            val messagesList = client.postgrest
                .rpc("get_message_more_info", MessageSearchByTicketDto(ticketId = ticketId))
                .decodeList<MessageWithUserInfoDto>()

           val messagesMapList =  messagesList.map {
               Message(
                   id = it.id,
                   content = it.content,
                   senderName = it.name,
                   ticketId = it.ticketId.toString(),
                   createdAt = it.createdAt,
                   isSent = it.senderId == userId,
                   fileUrl = it.fileUrl,
                   fileName = it.fileName,
                   fileSize = it.fileSize,
                   fileType = it.fileType,
               )
           }

            Log.d("SUPABASE_DEBUG", messagesList.toString())
            messagesMapList
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "ERRO AO LISTAR MENSAGEM", e)
            listOf()
        }
    }

}
