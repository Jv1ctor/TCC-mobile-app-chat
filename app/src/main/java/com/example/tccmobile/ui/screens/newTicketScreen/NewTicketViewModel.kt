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
class NewTicketViewModel : ViewModel() {

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

    fun setError(msg: String){
        _uiState.update {
            it.copy(
                anexoNomeArquivo = "",
                anexoUri = null,
                ticketError = msg
            )
        }
    }

    fun setFileNameAndUri(name: String, fileUri: Uri){
        _uiState.update {
            it.copy(
                anexoNomeArquivo = name,
                anexoUri = fileUri,
                ticketError = null
            )
        }
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


    fun onAbrirTicketClick(onTicketCreated: () -> Unit, context: Context) {
        viewModelScope.launch {
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

            if (hasError) {
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, ticketError = null) }

            try {
                val uri = currentState.anexoUri

                if (uri != null) {
                    val fileBytes = context.contentResolver.openInputStream(uri)?.use { input ->
                        input.readBytes()
                    }
                    Log.d("TICKET_UPLOAD", "Arquivo lido. Tamanho: ${fileBytes?.size ?: 0} bytes")

                }

                delay(1500)

                _uiState.update { it.copy(isLoading = false) }
                onTicketCreated()

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
