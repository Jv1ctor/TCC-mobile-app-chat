package com.example.tccmobile.ui.screens.chatStudent

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tccmobile.data.entity.Message
import com.example.tccmobile.data.entity.TicketStatus
import com.example.tccmobile.data.repository.AttachmentRepository
import com.example.tccmobile.data.repository.AuthRepository
import com.example.tccmobile.data.repository.MessageRepository
import com.example.tccmobile.data.repository.TicketRepository
import com.example.tccmobile.data.repository.UserRepository
import com.example.tccmobile.helpers.HandlerFiles
import com.example.tccmobile.helpers.transformTicketStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.Locale.getDefault
import kotlin.time.ExperimentalTime

open class ChatStudentViewModel(
    private val messageRepository: MessageRepository = MessageRepository(),
    private val authRepository: AuthRepository = AuthRepository(),
    private val ticketRepository: TicketRepository = TicketRepository(),
    private val userRepository: UserRepository = UserRepository(),
    private val attachRepository: AttachmentRepository = AttachmentRepository(),
    private val handlerFile: HandlerFiles = HandlerFiles()
) : ViewModel() {
    protected val _uiState = MutableStateFlow(ChatStudentState())
    val uiState = _uiState.asStateFlow()

    open fun init(channelId: Int, context: Context){
        viewModelScope.launch {
            messageRepository.startListening(channelId){
                insertNewMessage(id = it)
                updateInteractive(ticketId= channelId)
                handleStatusTicket(ticketId = channelId)
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            super.onCleared()
            messageRepository.clear()
        }

    }

    fun resetState() {
        _uiState.value = ChatStudentState()
    }

    fun exit(){
        viewModelScope.launch {
            messageRepository.clear()
        }
    }

    fun setShowMenu(v: Boolean){
        _uiState.update {
            it.copy(showMenu = v)
        }
    }

    private fun setActivate(v: Boolean){
        _uiState.update { it.copy(activeTicket = v ) }
    }
    private fun setTheme(v: String) {
        _uiState.update { it.copy(theme = v) }
    }

    private fun setIsStudent(v: Boolean) {
        _uiState.update { it.copy(isStudent = v) }
    }

    private fun setCourse(v: String){
        _uiState.update {  it.copy(course = v) }
    }

    private fun setEmail(v: String) {
        _uiState.update { it.copy(email = v) }
    }

    private fun setRegistry(v: String){
        _uiState.update {  it.copy(registry = v) }
    }

    private fun setAuthor(v: String){
        _uiState.update {  it.copy(author = v) }
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

    fun setStatus(v: TicketStatus){
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

    fun finishTicket(ticketId: Int){
        viewModelScope.launch {
            ticketRepository.updatedStatusClosed(ticketId)
            setStatus(transformTicketStatus("fechado"))
            setShowMenu(false)
            setActivate(false)
        }
    }

    private fun handleStatusTicket(ticketId: Int){
        viewModelScope.launch {
            val ticket = ticketRepository.getTicket(ticketId)
            ticket?.status?.let {  status ->
                setStatus(status)
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun sendMessage(ticketId: Int, context: Context){
        viewModelScope.launch {
            val userId = authRepository.getUserInfo()?.id
            val isStudent = _uiState.value.isStudent
            if(_uiState.value.inputMessage.isEmpty() || _uiState.value.inputMessage.isBlank() || userId == null) return@launch

            val message = messageRepository.sendMessage(
                content = _uiState.value.inputMessage,
                ticketId = ticketId,
                senderId = userId as String
            )

            if(message == null) return@launch

            if(isStudent){
                ticketRepository.updatedStatusPending(ticketId)
                setStatus(transformTicketStatus("pendente"))
            }else{
                ticketRepository.updatedStatusEvalueted(ticketId)
                setStatus(transformTicketStatus("avaliado"))
            }

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

                    uploadAttachment(messageId = message.id, context)

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
            authRepository.getUserInfo()?.id?.let { userId ->
                val newMessage = messageRepository.getNewMessage(id, userId) ?: return@launch

                Log.d("DEBUG_SUPABASE", "message: $newMessage")
                setMessagesList(listOf(newMessage))
            }
        }
    }

    private fun updateInteractive(ticketId: Int){
        viewModelScope.launch {
            ticketRepository.updatedLastInteraction(ticketId = ticketId)
        }
    }

    private fun uploadAttachment(messageId: Int, context: Context){
        viewModelScope.launch {
            setIsAttachLoading(true)

            _uiState.value.fileUri?.let { uri ->
                val attach = attachRepository.getAttachmentByMessageId(messageId)

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


    fun downloadAttach(path: String, fileName: String, mimeType: String, context: Context){
        viewModelScope.launch {
            try {
                val bytes = attachRepository.downloadAttach(path) ?: return@launch
                handlerFile.saveFileToDownloads(
                    context = context,
                    fileName = fileName,
                    mimeType = mimeType,
                    fileBytes = bytes,
                )

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Arquivo salvo em Downloads", Toast.LENGTH_LONG).show()
                }

            }catch (e: Exception){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Erro: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    open fun fetchTicket(ticketId: Int, isStudent: Boolean) {
        viewModelScope.launch {
            setIsLoading(true)

            val ticket = ticketRepository.getTicket(ticketId) ?: return@launch

            setIsStudent(isStudent)
            setTheme(ticket.subject)
            setCourse(ticket.course)
            setStatus(ticket.status)

            if(ticket.status.label.lowercase(getDefault()) == "fechado"){
                setActivate(false)
            }

            if(!_uiState.value.isStudent){
                val user = userRepository.getStudentById(ticket.createBy) ?: return@launch

                Log.d("SUPABASE_DEBUG", user.toString())
                setEmail(user.email)
                setAuthor(user.name)
                setRegistry(user.registry)
            }

            authRepository.getUserInfo()?.id?.let { userId ->
                setMessagesList(messageRepository.listMessages(ticketId, userId))
                setIsLoading(false)
            }
        }
    }
}