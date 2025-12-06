package com.example.tccmobile.data.repository

import android.util.Log
import com.example.tccmobile.data.dto.AttachmentDto
import com.example.tccmobile.data.dto.AttachmentInsertDto
import com.example.tccmobile.data.supabase.SupabaseClient.client
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage

class AttachmentRepository {


    suspend fun registryFile(
        attachPath: String,
        fileName: String,
        fileSize: Long,
        fileType: String,
        messageId: Int,
    ): Boolean {
        return try {
            val attach = AttachmentInsertDto(
                fileUrl = attachPath,
                fileName = fileName,
                fileSize = fileSize,
                fileType = fileType,
                messageId = messageId
            )

            client.postgrest.from("attachments").insert(attach)
            true
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "Erro ao registrar arquivo $e")
            false
        }
    }

    suspend fun uploadFile(
        attachPath: String,
        byteArray: ByteArray
    ): Boolean {

        return try {
            val bucket = client.storage.from("docs")

            bucket.upload(attachPath, byteArray){
                upsert = false
            }

            true
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "Erro ao subir arquivo $e")
            false
        }
    }

    suspend fun getAttachmentByMessageId(messageId: Int): AttachmentDto?{
        return try {
            client.postgrest.from("attachments").select {
                filter {
                    eq("message_id", messageId)
                }
            }.decodeSingle<AttachmentDto>()
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "Erro ao buscar arquivo $e")
            null
        }
    }

    suspend fun downloadAttach(path: String): ByteArray?{
        return try {
            val bucket = client.storage.from("docs")
            bucket.downloadAuthenticated(path)
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "Erro ao baixar arquivo $e")
            null
        }
    }

}