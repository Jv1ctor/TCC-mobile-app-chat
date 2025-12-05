package com.example.tccmobile.ui.screens.chatStudent

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tccmobile.data.entity.Message
import com.example.tccmobile.data.repository.AttachmentRepository
import com.example.tccmobile.data.repository.AuthRepository
import com.example.tccmobile.data.repository.MessageRepository
import com.example.tccmobile.data.repository.TicketRepository
import com.example.tccmobile.helpers.HandlerFiles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

open class ChatStudentViewModel(
    private val messageRepository: MessageRepository = MessageRepository(),
    private val authRepository: AuthRepository = AuthRepository(),
    private val ticketRepository: TicketRepository = TicketRepository(),
    private val attachRepository: AttachmentRepository = AttachmentRepository(),
    private val handlerFile: HandlerFiles = HandlerFiles()
) : ViewModel() {
    protected val _uiState = MutableStateFlow(ChatStudentState())
    val uiState = _uiState.asStateFlow()

    open fun init(channelId: Int, context: Context){
        viewModelScope.launch {
            messageRepository.startListening(channelId){
                insertNewMessage(id = it)
                uploadAttachment(id = it, context)
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            super.onCleared()
            messageRepository.clear()
        }

    }

    private fun setTheme(v: String) {
        _uiState.update { it.copy(theme = v) }
    }

    private fun setCourse(v: String){
        _uiState.update {  it.copy(course = v) }
    }

    private fun setIsLoading(v: Boolean){
        _uiState.update { it.copy(isLoading = v) }
    }

    private fun setIsAttachLoading(v: Boolean){
        _uiState.update { it.copy(isAttachLoading = v) }
    }


    fun setInputMessage(message: String){
        _uiState.update {
            it.copy(inputMessage = message)
        }
    }

    private fun setMessagesList(messages: List<Message>){
        _uiState.update {
            it.copy( messages = it.messages + messages)
        }
    }

    fun setStatus(v: String){
        _uiState.update { it.copy(status = v) }
    }

    fun removeFile(){
        _uiState.update {
            it.copy(
                fileName = "",
                fileUri = null
            )
        }
    }

    fun onFileSelected(fileUri: Uri, context: Context){
        handlerFile.onFileSelected(
            fileUri = fileUri,
            context = context,
            callbackError = {},
            callbackSuccess = { name ->
                _uiState.update {
                    it.copy(
                        fileName = name,
                        fileUri = fileUri
                    )
                }
            },
        )
    }

    @OptIn(ExperimentalTime::class)
    fun sendMessage(ticketId: Int, context: Context){
        viewModelScope.launch {
            val userId = authRepository.getUserInfo()?.id
            if(_uiState.value.inputMessage.isEmpty() && userId == null) return@launch

            val message = messageRepository.sendMessage(
                content = _uiState.value.inputMessage,
                ticketId = ticketId,
                senderId = userId as String
            )

            if(message == null) return@launch

            _uiState.value.fileUri?.let { uri ->
                val fileName = handlerFile.getFileName(context = context, uri = uri)
                val fileSize = handlerFile.getFileSize(context = context, uri= uri)
                val fileType = handlerFile.getFileType(context = context, uri = uri)


                if(fileSize != null && fileType != null && fileName != null){
                    val filePath = handlerFile.generatePath(
                        userId = userId,
                        messageId = message.id,
                        ticketId = ticketId,
                        filename = fileName
                    )
                    val result = attachRepository.registryFile(
                        attachPath = filePath,
                        fileName= fileName,
                        fileSize = fileSize,
                        fileType = fileType,
                        messageId = message.id,
                    )

                    if(!result){
//                        TODO() Mensagem de Erro e Funcao para cancelar a mensagem enviada sem anexo
                    }
                }
            }

            setInputMessage(" ")
            removeFile()
        }
    }

    private fun insertNewMessage(id: Int){
        viewModelScope.launch {
            val newMessage = messageRepository.getNewMessage(id)

            if(newMessage == null) return@launch

            val attach = attachRepository.getAttachmentByMessageId(newMessage.id)

            if(attach != null){
                newMessage.fileUrl = attach.fileUrl
                newMessage.fileName = attach.fileName
                newMessage.fileSize = attach.fileSize
                newMessage.fileType = attach.fileType
            }

            Log.d("DEBUG_SUPABASE", "attach: $attach\n message: $newMessage")
            setMessagesList(listOf(newMessage))
        }
    }

    private fun uploadAttachment(id: Int, context: Context){
        viewModelScope.launch {
            setIsAttachLoading(true)

            _uiState.value.fileUri?.let { uri ->
                val attach = attachRepository.getAttachmentByMessageId(id)

                val byte = handlerFile.getByteArray(
                    uri = uri,
                    context = context
                )

                if(attach == null || byte == null) return@launch
                attachRepository.uploadFile(attachPath = attach.fileUrl, byteArray = byte)
                setIsAttachLoading(false)
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    open fun fetchTicket(ticketId: Int) {
        viewModelScope.launch {
            setIsLoading(true)

            val ticket = ticketRepository.getTicket(ticketId)

            if(ticket == null) return@launch

            setTheme(ticket.subject)
            setCourse(ticket.course)
            setStatus(ticket.status)

            setMessagesList(messageRepository.listMessages(ticketId))

            setIsLoading(false)
        }
    }
}