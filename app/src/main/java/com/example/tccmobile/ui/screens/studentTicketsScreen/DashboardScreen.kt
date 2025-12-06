package com.example.tccmobile.ui.screens.studentTicketsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Person
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
import com.example.tccmobile.navigation.Routes
import com.example.tccmobile.ui.theme.BackgroundPage
import com.example.tccmobile.ui.theme.HeaderBlue
import com.example.tccmobile.ui.theme.TextGray
import com.example.tccmobile.ui.components.utils.AppHeader
import com.example.tccmobile.ui.components.utils.AppBottomBar
import com.example.tccmobile.ui.components.utils.BottomNavItem
import com.example.tccmobile.ui.components.utils.BottomNavigationBar
import com.example.tccmobile.ui.components.utils.ButtonForm
import com.example.tccmobile.ui.components.utils.TicketCard

@Composable
fun DashboardTicketsScreen(
    viewModel: DashboardViewModel = viewModel(),
    navigateBarItems: List<BottomNavItem>,
    currentRoute: String,
    onTicketClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPage)
    ) {
        // 1. Header Compartilhado
        AppHeader(
            title = "Painel de Envio",
            subtitle = "Crie um novo ticket para iniciar análise"
        )

        // 2. Conteúdo Central (Lista)
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
                        color = HeaderBlue,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Acompanhe seus tickets",
                        color = TextGray,
                        fontSize = 14.sp
                    )
                }

                ButtonForm(
                    text = "Novo",
                    backgroundColor = HeaderBlue,
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
                    onClick = { /* Ação Novo Ticket */ }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Tickets
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(uiState.tickets) { ticket ->
                    // showStudentInfo = false, pois aqui é o painel do próprio aluno
                    TicketCard(
                        ticket = ticket,
                        showStudentInfo = false,
                        onClick = { onTicketClick(ticket.id) }
                    )
                }
            }
        }

//        3. Bottom Bar Compartilhada
//        AppBottomBar(
//            currentRoute = Routes.HOME, // Indica que estamos na Home
//            onNavigate = onNavigate
//        )
        BottomNavigationBar(
            items = navigateBarItems,
            currentRoute = currentRoute,
        )
    }
}