package com.example.tccmobile.helpers

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import kotlinx.coroutines.flow.update

object HandlerFiles {
    // Constante para o tamanho máximo permitido (25 MB)
    private val maxFileMB = 25
    private val maxFileBytes = maxFileMB * 1024 * 1024L


    //Trata o arquivo selecionado, o tamanho máximo foi 25mb
    fun onFileSelected(fileUri: Uri, context: Context, callbackError: (error: String) -> Unit, callbackSuccess: (name: String) -> Unit) {
        val nomeArquivo = getFileName(fileUri, context) ?: "Arquivo Anexado"
        val tamanhoArquivo = getFileSize(context, fileUri)

        if (tamanhoArquivo != null && tamanhoArquivo > maxFileBytes) {
            val error = "Arquivo muito grande. O limite é $maxFileMB MB."
            callbackError(error)
            return
        }

        if (nomeArquivo.endsWith(".doc", ignoreCase = true) ||
            nomeArquivo.endsWith(".docx", ignoreCase = true)
        ) {
            callbackSuccess(nomeArquivo)
        } else {
            val error = "Formato inválido. Anexe um arquivo .doc ou .docx."
            callbackError(error)
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
}