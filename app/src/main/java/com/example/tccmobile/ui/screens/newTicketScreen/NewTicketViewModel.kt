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
import android.provider.OpenableColumns
import android.util.Log

// Constante para o tamanho máximo permitido (25 MB)
private const val Max_File_Size = 25 * 1024 * 1024L

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

    //Trata o arquivo selecionado, o tamanho máximo foi 25mb
    fun onFileSelected(fileUri: Uri, context: Context) {
        val nomeArquivo = getFileName(fileUri, context) ?: "Arquivo Anexado"
        val tamanhoMaxMB = 25
        val tamanhoMaxBytes = tamanhoMaxMB * 1024 * 1024

        val tamanhoArquivo = getFileSize(context, fileUri)

        if (tamanhoArquivo != null && tamanhoArquivo > tamanhoMaxBytes) {
            _uiState.update {
                it.copy(
                    anexoNomeArquivo = "",
                    anexoUri = null,
                    ticketError = "Arquivo muito grande. O limite é $tamanhoMaxMB MB."
                )
            }
            return
        }

        if (nomeArquivo.endsWith(".doc", ignoreCase = true) ||
            nomeArquivo.endsWith(".docx", ignoreCase = true)
        ) {
            _uiState.update {
                it.copy(
                    anexoNomeArquivo = nomeArquivo,
                    anexoUri = fileUri,
                    ticketError = null
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    anexoNomeArquivo = "",
                    anexoUri = null,
                    ticketError = "Formato inválido. Anexe um arquivo .doc ou .docx."
                )
            }
        }
    }

    // Obtém o tamanho do arquivo a partir de um Uri usando o ContentResolver.
    fun getFileSize(context: Context, uri: Uri): Long? {
        return try {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (sizeIndex != -1) {
                    cursor.moveToFirst()
                    cursor.getLong(sizeIndex)
                } else null
            }
        } catch (e: Exception) {
            null
        }
    }



    fun getFileName(uri: Uri, context: Context): String? {
        var fileName: String? = null
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = cursor.getString(nameIndex)
                }
            }
        }
        return fileName
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
