package com.example.tccmobile.ui.screens.studentTicketsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tccmobile.ui.theme.BackgroundGray
import com.example.tccmobile.ui.components.utils.AppHeader
import com.example.tccmobile.ui.components.utils.BottomNavItem
import com.example.tccmobile.ui.components.utils.BottomNavigationBar
import com.example.tccmobile.ui.components.utils.ButtonForm
import com.example.tccmobile.ui.components.utils.TicketCard
import com.example.tccmobile.ui.theme.AzulLetra
import com.example.tccmobile.ui.theme.Gray
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun StudentsTicketsScreen(
    viewModel: StudentTicketsViewModel = viewModel(),
    navigateBarItems: List<BottomNavItem>,
    currentRoute: String,
    onTicketClick: (Int) -> Unit,
    onClickNew: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        AppHeader(
            title = "Painel de Envio",
            subtitle = "Crie um novo ticket para iniciar análise"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Meus Envios",
                        color = AzulLetra,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Acompanhe seus tickets",
                        color = Gray,
                        fontSize = 14.sp
                    )
                }

                ButtonForm(
                    text = "Novo",
                    backgroundColor = AzulLetra,
                    contentColor = Color.White,
                    cornerRadius = 16,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    onClick = { onClickNew() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(uiState.tickets) { ticket ->
                    TicketCard(
                        subject = ticket.subject,
                        course = ticket.course,
                        author = ticket.authorName!!,
                        status = ticket.status,
                        createAt = ticket.createdAt,
                        updatedAt = ticket.updatedAt,
                        showStudentInfo = false,
                        onClick = { onTicketClick(ticket.id) },

                    )
                }
            }
        }
        BottomNavigationBar(
            items = navigateBarItems,
            currentRoute = currentRoute,
        )
    }
}