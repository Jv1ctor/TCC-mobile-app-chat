package com.example.tccmobile.ui.screens.newTicketScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tccmobile.ui.components.utils.NewTicketForm
import com.example.tccmobile.ui.components.utils.NewTicketHeader
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.tccmobile.helpers.HandlerFiles
import com.example.tccmobile.ui.theme.Branco


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTicketScreen(
    viewModel: NewTicketViewModel = viewModel(),
    onBackClick: () -> Unit,
    onTicketCreated: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val tccFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                viewModel.onFileSelected(
                    fileUri = uri,
                    context= context,
                )
            }
        }
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Branco),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            NewTicketHeader(onBackClick = onBackClick)

            NewTicketForm(
                state = state,
                onThemeChange = viewModel::onTemaChange,
                onCursoChange = viewModel::onCursoChange,
                onObservationsChange = viewModel::onObservacoesChange,

                onAttachTccClick = {
                    tccFileLauncher.launch(
                        input= arrayOf(
                            "application/msword",
                            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                            "application/vnd.oasis.opendocument.text",
                            "application/octet-stream"
                        )
                    )
                },

                onOpenTicketClick = {
                    viewModel.onAbrirTicketClick(onTicketCreated, context)
                },
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


@Preview
@Composable
fun NewTicketScreenPreview(){
    NewTicketScreen(
        onBackClick = {}
    ) { }
}