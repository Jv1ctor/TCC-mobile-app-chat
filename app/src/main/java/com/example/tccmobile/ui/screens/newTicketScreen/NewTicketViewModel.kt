package com.example.tccmobile.ui.screens.newTicketScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.tccmobile.data.entity.Ticket
import com.example.tccmobile.data.repository.AttachmentRepository
import com.example.tccmobile.data.repository.AuthRepository
import com.example.tccmobile.data.repository.MessageRepository
import com.example.tccmobile.data.repository.TicketRepository
import com.example.tccmobile.helpers.HandlerFiles

class NewTicketViewModel(
    val handlerFile: HandlerFiles = HandlerFiles(),
    val ticketRepository: TicketRepository = TicketRepository(),
    val authRepository: AuthRepository = AuthRepository(),
    val attachRepository: AttachmentRepository = AttachmentRepository(),
    val messageRepository: MessageRepository = MessageRepository()
): ViewModel() {

    private val _uiState = MutableStateFlow(NewTicketState())
    val uiState = _uiState.asStateFlow()
    fun onTemaChange(v: String) {
        _uiState.update { it.copy(temaTcc = v, ticketError = null, campoThemeError = null) }
    }

    fun onCursoChange(v: String) {
        _uiState.update { it.copy(curso = v, ticketError = null, campoCursorError = null) }
    }

    fun onObservacoesChange(v: String) {
        _uiState.update { it.copy(observacoes = v) }
    }

    private fun setError(msg: String){
        _uiState.update {
            it.copy(
                anexoNomeArquivo = "",
                anexoUri = null,
                ticketError = msg
            )
        }
    }

    private fun setFileNameAndUri(name: String, fileUri: Uri){
        _uiState.update {
            it.copy(
                anexoNomeArquivo = name,
                anexoUri = fileUri,
                ticketError = null
            )
        }
    }

    fun onFileSelected(fileUri: Uri, context: Context){
        handlerFile.onFileSelected(
            fileUri = fileUri,
            context = context,
            callbackError = { setError(it) },
            callbackSuccess = { setFileNameAndUri(name = it, fileUri = fileUri) }
        )
    }

    fun mapExceptionToUserMessage(e: Exception): String {

        val msg = e.message?.lowercase() ?: ""

        return when {
            "open failed" in msg || "permission" in msg ->
                "Não foi possível abrir o arquivo. Verifique se ele existe e se você tem permissão de acesso."

            "no space left" in msg || "size" in msg || "too large" in msg ->
                "O arquivo é muito grande para ser enviado."


            "network" in msg || "failed to connect" in msg || "timeout" in msg ->
                "Falha de conexão ao enviar o TCC. Verifique sua internet e tente novamente."


            "corrupt" in msg || "read failed" in msg ->
                "O arquivo parece estar corrompido ou não pôde ser lido."

            else ->
                "Falha ao enviar o TCC. Erro interno: ${e.message ?: "Desconhecido"}"
        }
    }

    private fun validateFields(): Boolean{
        val currentState = _uiState.value

        var hasError = false
        var temaError: String? = null
        var cursoError: String? = null
        var anexoError: String? = null

        if (currentState.temaTcc.isBlank()) {
            temaError = "Campo obrigatório"
            hasError = true
        }
        if (currentState.curso.isBlank()) {
            cursoError = "Campo obrigatório"
            hasError = true
        }
        if (currentState.anexoUri == null) {
            anexoError = "Por favor, anexe o arquivo TCC (.doc ou .docx)."
            hasError = true
        }

        _uiState.update {
            it.copy(
                campoThemeError = temaError,
                campoCursorError = cursoError,
                ticketError = anexoError
            )
        }

        if (hasError) return false


        return true
    }


    fun onAbrirTicketClick(onTicketCreated: (ticketId: Int) -> Unit, context: Context) {
        viewModelScope.launch {
            val isValid = validateFields()

            if(!isValid) return@launch

            _uiState.update { it.copy(isLoading = true, ticketError = null) }
            try {
                val user = authRepository.getUserInfo()
                val uri = _uiState.value.anexoUri

                user?.id?.let { id ->
                    val ticket = ticketRepository.createTicket(
                        subject = _uiState.value.temaTcc,
                        status = "pendente",
                        remark = _uiState.value.observacoes,
                        course = _uiState.value.curso,
                        userId = id
                    )


                    if(ticket == null || uri == null) return@launch

                    val message = messageRepository.sendMessage(
                        content = _uiState.value.observacoes.ifBlank { "Olá, segue meu documento" },
                        ticketId = ticket.id,
                        senderId = ticket.createBy
                    )

                    val bytes = handlerFile.getByteArray(uri = uri, context)
                    val fileName = handlerFile.getFileName(
                        uri = uri,
                        context = context
                    )

                    val fileSize = handlerFile.getFileSize(
                        uri = uri,
                        context = context
                    )

                    val fileType = handlerFile.getFileType(
                        uri= uri,
                        context= context
                    )

                    Log.d("TICKET_UPLOAD", "Arquivo lido. Tamanho: ${bytes?.size ?: 0} bytes")

                    if(message == null || fileName == null || fileSize == null || fileType == null || bytes == null) return@launch

                    val path = handlerFile.generatePath(
                        userId = id,
                        messageId = message.id,
                        ticketId = ticket.id,
                        filename = fileName
                    )

                    attachRepository.registryFile(
                        attachPath = path,
                        fileName = fileName,
                        fileSize = fileSize,
                        fileType = fileType,
                        messageId = message.id
                    )

                    attachRepository.uploadFile(
                        attachPath = path,
                        byteArray = bytes
                    )

                    _uiState.update { it.copy(isLoading = false) }
                    onTicketCreated(ticket.id)
                }
            } catch (e: Exception) {
                Log.e("TICKET_ERROR", "Falha no envio ou leitura do arquivo", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        ticketError = mapExceptionToUserMessage(e)
                    )
                }
            }
        }
    }
}
