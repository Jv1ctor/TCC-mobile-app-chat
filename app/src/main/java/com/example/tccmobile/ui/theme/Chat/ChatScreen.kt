package com.example.tccmobile.ui.theme.Chat

import android.app.DownloadManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.Direction
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.*



data class Message(
    val id: String = UUID.randomUUID().toString(),
    val text: String = "",
    val sender: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val fileUrl: String? = null
)

@Composable
fun ChatScreen(
    ticketId: String,
    userRole: String,        // "aluno" ou "bibliotecaria"
    studentName: String,
) {
    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()

    var inputMessage by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<Message>()) }

    // Listener em tempo real
    LaunchedEffect(Unit) {
        db.collection("tickets")
            .document(ticketId)
            .collection("messages")
            .orderBy("timestamp", DownloadManager.Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    messages = snapshot.toObjects(Message::class.java)
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(8.dp)
    ) {

        // LISTA DE MENSAGENS
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { msg ->
                ChatBubble(message = msg, isMe = msg.sender == userRole)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // CAMPO DE ENVIO
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = inputMessage,
                onValueChange = { inputMessage = it },
                placeholder = { Text("Digite uma mensagem...") }
            )

            IconButton(
                onClick = {
                    if (inputMessage.isNotBlank()) {
                        sendMessage(
                            db = db,
                            ticketId = ticketId,
                            text = inputMessage,
                            sender = userRole
                        )
                        inputMessage = ""
                    }
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = "Enviar")
            }
        }
    }
}


// Enviar mensagem
fun sendMessage(
    db: FirebaseFirestore,
    ticketId: String,
    text: String,
    sender: String,
    fileUrl: String? = null
) {
    val message = Message(
        text = text,
        sender = sender,
        timestamp = System.currentTimeMillis(),
        fileUrl = fileUrl
    )

    db.collection("tickets")
        .document(ticketId)
        .collection("messages")
        .document(message.id)
        .set(message)
}


// Upload de arquivo (PDF)
suspend fun uploadFile(
    storage: FirebaseStorage,
    uri: Uri,
    ticketId: String
): String {
    val ref = storage.reference
        .child("tickets/$ticketId/${UUID.randomUUID()}.pdf")

    ref.putFile(uri).await()
    return ref.downloadUrl.await().toString()
}

@Composable
fun ChatBubble(message: Message, isMe: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 260.dp)
                .background(
                    if (isMe) Color(0xFF2287FF) else Color.White,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(12.dp)
        ) {

            if (message.fileUrl != null) {
                Text("📄 Arquivo enviado", color = Color.Black)
                Text(message.fileUrl, fontSize = 12.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(6.dp))
            }

            Text(
                text = message.text,
                color = if (isMe) Color.White else Color.Black
            )
        }
    }
}