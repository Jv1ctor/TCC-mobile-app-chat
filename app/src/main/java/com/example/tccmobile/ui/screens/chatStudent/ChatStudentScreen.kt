package com.example.tccmobile.ui.screens.chatStudent

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tccmobile.data.entity.Message
import com.example.tccmobile.ui.components.chat.ChatInputBar
import com.example.tccmobile.ui.components.chat.HeaderStudentChat
import com.example.tccmobile.ui.components.chat.MessageBox
import com.example.tccmobile.ui.components.utils.StatusBadgeModel
import com.example.tccmobile.ui.theme.DarkBlue
import com.example.tccmobile.ui.theme.LightBlue
import com.example.tccmobile.ui.theme.Orange
import com.example.tccmobile.ui.theme.SuperLightOrange
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Composable
fun ChatStudentScreen(
    viewModel: ChatStudentViewModel = viewModel(),
    ticketId: String,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LaunchedEffect(ticketId) {
        viewModel.init(ticketId.toInt(), context)
        viewModel.fetchTicket(ticketId.toInt())
    }

    LaunchedEffect(uiState.messages.size) {
        if(uiState.messages.isNotEmpty()){
            listState.animateScrollToItem(index= uiState.messages.lastIndex)
        }
    }

    val tccFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                viewModel.onFileSelected(
                    fileUri = uri,
                    context = context
                )
            }
        }
    )

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = LightBlue,
                trackColor = DarkBlue,
            )
        }
    } else {

        Column(
            modifier= Modifier.fillMaxSize()
        ){
            HeaderStudentChat(
                ticketId = ticketId,
                title = uiState.theme,
                subtitle = uiState.course,
                badges = listOf(
                    StatusBadgeModel(
                        text = uiState.status,
                        backgroundColor = SuperLightOrange,
                        textColor = Orange
                    )
                ),
                onBackClick = onBackClick
            )


            LazyColumn(
                state = listState,
                modifier = Modifier
                .weight(1f)
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),

            ){
                items(items= uiState.messages, key={ it.id }) { message ->
                    MessageBox(
                        message = message.content,
                        isAttachLoading = uiState.isAttachLoading,
                        nameSubmitter = message.senderName,
                        date = message.createdAt,
                        isStudent = message.isStudent,
                        attachmentName = message.fileName,
                        onAttachmentClick = {

                        }
                    )
                }
            }


            
            ChatInputBar(
                message = uiState.inputMessage,
                fileName = uiState.fileName,
                onMessageChange = { viewModel.setInputMessage(it) },
                onSendClick = { viewModel.sendMessage(ticketId.toInt(), context) },
                onAttachClick = {
                    tccFileLauncher.launch(
                        input= arrayOf(
                            "application/msword",
                            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                            "application/vnd.oasis.opendocument.text",
                        )
                    )
                },
                onRemoveFile = { viewModel.removeFile() }
            )
        }


    }
}


// Mock ViewModel para Preview
class MockChatStudentViewModel : ChatStudentViewModel() {
    @OptIn(ExperimentalTime::class)
    private val mockMessages = listOf(
        Message(
            id = 1,
            content = "Olá, preciso de ajuda com meu TCC sobre desenvolvimento mobile.",
            senderName = "João Silva",
            ticketId = "123",
            createdAt = Instant.fromEpochMilliseconds(System.currentTimeMillis() - 3600000),
            isStudent = true,
            fileName = null
        ),
        Message(
            id = 2,
            content = "Claro! Vou analisar seu trabalho e dar um feedback detalhado.",
            senderName = "Prof. Maria Santos",
            ticketId = "123",
            createdAt = Instant.fromEpochMilliseconds(System.currentTimeMillis() - 1800000),
            isStudent = false,
            fileName = null
        ),
        Message(
            id = 3,
            content = "Segue meu documento do TCC atualizado.",
            senderName = "João Silva",
            ticketId = "123",
            createdAt = Instant.fromEpochMilliseconds(System.currentTimeMillis() - 600000),
            isStudent = true,
            fileName = "TCC_Desenvolvimento_Mobile.docx"
        ),
        Message(
            id = 4,
            content = "Recebi o documento. Vou revisar e retorno em breve.",
            senderName = "Prof. Maria Santos",
            ticketId = "123",
            createdAt = Instant.fromEpochMilliseconds(System.currentTimeMillis()),
            isStudent = false,
            fileName = null
        )
    )

    init {
        _uiState.value = ChatStudentState(
            id = "123",
            theme = "Desenvolvimento de Aplicativo Mobile",
            course = "Engenharia de Software",
            status = "Em Andamento",
            inputMessage = "",
            messages = mockMessages,
            isLoading = false,
            chatError = null
        )
    }

    override fun init(channelId: Int, context: Context) {
        // Mock - não faz nada no preview
    }

    override fun fetchTicket(ticketId: Int) {
        // Mock - não faz nada no preview
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewChatStudentScreen(){
    val mockViewModel = remember { MockChatStudentViewModel() }
    ChatStudentScreen(
        viewModel = mockViewModel,
        ticketId = "123",
        onBackClick = { }
    )
}