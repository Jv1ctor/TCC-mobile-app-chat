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

    fun onFileSelected(fileUri: Uri, context: Context) {
        val nomeArquivo = getFileName(fileUri, context) ?: "Arquivo Anexado"

        if (nomeArquivo.endsWith(".doc", ignoreCase = true) || nomeArquivo.endsWith(
                ".docx",
                ignoreCase = true
            )
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
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val fileBytes = inputStream?.readBytes()
                    Log.d("TICKET_UPLOAD", "Arquivo lido. Tamanho: ${fileBytes?.size ?: 0} bytes")


                    inputStream?.close()
                }

                delay(1500)

                _uiState.update { it.copy(isLoading = false) }
                onTicketCreated()

            } catch (e: Exception) {
                Log.e("TICKET_ERROR", "Falha no envio ou leitura do arquivo: ${e.message}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        ticketError = "Falha: Não foi possível ler ou enviar o TCC devido a um erro interno."
                    )
                }
            }
        }
    }
}
