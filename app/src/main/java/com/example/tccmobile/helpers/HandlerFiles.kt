package com.example.tccmobile.helpers

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import kotlinx.coroutines.flow.update

class HandlerFiles {
    // Constante para o tamanho máximo permitido (25 MB)
    private val maxFileMB = 25
    private val maxFileBytes = maxFileMB * 1024 * 1024L

    fun normalizeFileName(name: String): String {
        val temp = java.text.Normalizer.normalize(name, java.text.Normalizer.Form.NFD)
        val noAccents = temp.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
        return noAccents
            .replace("[^a-zA-Z0-9._-]".toRegex(), "_") // troca caracteres inválidos
            .lowercase()
    }


    fun generatePath(userId: String, messageId: Int, ticketId: Int, filename: String): String {
        return "$ticketId/$userId-$messageId-${normalizeFileName(filename)}"
    }

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

    fun getFileType(uri: Uri, context: Context): String?{
        return context.contentResolver.getType(uri)
    }

    fun getByteArray(uri: Uri, context: Context): ByteArray?{
        return context.contentResolver.openInputStream(uri)?.readBytes()
    }

    fun saveFileToDownloads(
        context: Context,
        fileName: String,
        mimeType: String,
        fileBytes: ByteArray
    ): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, mimeType)
            put(MediaStore.Downloads.RELATIVE_PATH, "Download/")
            put(MediaStore.Downloads.IS_PENDING, 1)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            resolver.openOutputStream(uri).use { output ->
                output?.write(fileBytes)
            }

            contentValues.clear()
            contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
            resolver.update(uri, contentValues, null, null)
        }

        return uri
    }

}