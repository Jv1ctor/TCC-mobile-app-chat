package com.example.tccmobile.TicketApp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.*

// ---------- Models ----------

enum class TicketStatus {
    ABERTO, ANALISADO, AVALIADO, PENDENTE
}

data class Ticket(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val course: String,
    val observations: String,
    val studentName: String = "Aluno",
    val openedAt: Date = Date(),
    var updatedAt: Date? = null,
    var status: TicketStatus = TicketStatus.ABERTO
)

// ---------- ViewModel (in-memory repo) ----------

class TicketViewModel : ViewModel() {
    private val _tickets = mutableStateListOf<Ticket>()
    val tickets: List<Ticket> get() = _tickets

    init {
        // Sample tickets reflecting your Figma data
        val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val t1Opened = Calendar.getInstance().apply { set(2024, 9, 15) }.time // 15/10/2024 (month is 0-based)
        val t1Updated = Calendar.getInstance().apply { set(2024, 9, 20) }.time // 20/10/2024
        _tickets.add(
            Ticket(
                title = "Desenvolvimento de Sistema Web para Gestão de Biblioteca",
                course = "Engenharia de Software",
                observations = "Sistema para controlar empréstimos e catálogo.",
                openedAt = t1Opened,
                updatedAt = t1Updated,
                status = TicketStatus.ANALISADO
            )
        )
        val t2Opened = Calendar.getInstance().apply { set(2024, 10, 1) }.time // 01/11/2024
        val t2Updated = Calendar.getInstance().apply { set(2024, 10, 5) }.time // 05/11/2024
        _tickets.add(
            Ticket(
                title = "Aplicação Mobile para Monitoramento de Saúde",
                course = "Sistemas de Informação",
                observations = "App para rastrear sinais vitais e enviar alertas",
                openedAt = t2Opened,
                updatedAt = t2Updated,
                status = TicketStatus.PENDENTE
            )
        )
    }

    fun addTicket(ticket: Ticket) {
        _tickets.add(0, ticket)
    }

    fun updateTicketStatus(id: String, newStatus: TicketStatus) {
        val index = _tickets.indexOfFirst { it.id == id }
        if (index >= 0) {
            val t = _tickets[index]
            _tickets[index] = t.copy(status = newStatus, updatedAt = Date())
        }
    }
}

// ---------- UI Helpers ----------

private val DeepBlue = Color(0xFF003366)
private val LightBlueBackground = Color(0xFFF5F7FA)
private val BadgeAnalyzedBg = Color(0xFFFEF3C6)
private val BadgeAnalyzedOutline = Color(0xFFFEE685)
private val BadgeEvaluatedBg = Color(0xFFFFEDD4)
private val BadgeOpenBg = Color(0xFFDBEAFE)

private fun formatDate(date: Date?): String = date?.let {
    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
} ?: "-"

// ---------- Composables ----------

@Composable
fun TicketApp(viewModel: TicketViewModel = viewModel()) {
    var showNewDialog by remember { mutableStateOf(false) }
    var roleIsStudent by remember { mutableStateOf(true) } // true = Aluno, false = Bibliotecária

    Scaffold(
        topBar = { TopHeader() },
        floatingActionButton = {
            FloatingActionButton(onClick = { showNewDialog = true }, backgroundColor = DeepBlue) {
                Icon(Icons.Default.Add, contentDescription = "Novo")
            }
        },
        bottomBar = { BottomBar(roleIsStudent, onRoleToggle = { roleIsStudent = it }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            HeadingSection(onNewClick = { showNewDialog = true })
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Acompanhe seus tickets",
                color = Color(0xFF64748B),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(viewModel.tickets) { ticket ->
                    TicketCard(ticket = ticket, isStudent = roleIsStudent, onChangeStatus = { newStatus ->
                        viewModel.updateTicketStatus(ticket.id, newStatus)
                    })
                }
            }
        }
    }

    if (showNewDialog) {
        NewTicketDialog(onDismiss = { showNewDialog = false }, onCreate = { title, course, obs ->
            val newTicket = Ticket(
                title = title,
                course = course,
                observations = obs,
                openedAt = Date(),
                status = TicketStatus.ABERTO
            )
            viewModel.addTicket(newTicket)
            showNewDialog = false
        })
    }
}

@Composable
fun TopHeader() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(DeepBlue)
        .padding(16.dp)) {
        Text(
            text = "Painel de Envio",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Crie um novo ticket para iniciar análise",
            color = Color(0xFFC0E0FF),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HeadingSection(onNewClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Meus Envios",
                fontSize = 20.sp,
                color = DeepBlue,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            onClick = onNewClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = DeepBlue
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Novo", color = Color.White)
        }
    }
}

@Composable
fun TicketCard(ticket: Ticket, isStudent: Boolean, onChangeStatus: (TicketStatus) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 140.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = ticket.title, fontSize = 18.sp, color = DeepBlue, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = ticket.course, fontSize = 16.sp, color = Color(0xFF64748B))
                }
                // small red badge like the Figma count
                Box(modifier = Modifier
                    .size(width = 32.dp, height = 28.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFFB2C36)), contentAlignment = Alignment.Center) {
                    Text(text = "1", color = Color.White, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // status badges
                when (ticket.status) {
                    TicketStatus.ANALISADO -> StatusBadge(text = "Analisado", bg = BadgeAnalyzedBg, outline = BadgeAnalyzedOutline)
                    TicketStatus.AVALIADO -> StatusBadge(text = "Avaliado", bg = BadgeEvaluatedBg, outline = Color(0xFFFFD6A7))
                    TicketStatus.ABERTO -> StatusBadge(text = "Aberto", bg = BadgeOpenBg, outline = Color(0xFFBEDBFF))
                    TicketStatus.PENDENTE -> StatusBadge(text = "Pendente", bg = Color(0xFFF3F4F6), outline = Color(0xFFE5E7EB))
                }
                // extra static placeholder for second badge if applicable
                if (ticket.status == TicketStatus.ANALISADO) {
                    StatusBadge(text = "Avaliado", bg = BadgeEvaluatedBg, outline = Color(0xFFFFD6A7))
                } else if (ticket.status == TicketStatus.ABERTO) {
                    StatusBadge(text = "Pendente", bg = Color(0xFFF3F4F6), outline = Color(0xFFE5E7EB))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = "Aberto em ${formatDate(ticket.openedAt)}", color = Color(0xFF64748B), fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Atualizado em ${formatDate(ticket.updatedAt)}", color = Color(0xFF64748B), fontSize = 16.sp)
                }

                // If librarian, show quick actions to change state
                if (!isStudent) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Ações", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            IconButton(onClick = { onChangeStatus(TicketStatus.ANALISADO) }) { Icon(Icons.Default.CheckCircle, contentDescription = "Analisar") }
                            IconButton(onClick = { onChangeStatus(TicketStatus.AVALIADO) }) { Icon(Icons.Default.History, contentDescription = "Avaliar") }
                            IconButton(onClick = { onChangeStatus(TicketStatus.PENDENTE) }) { Icon(Icons.Default.Refresh, contentDescription = "Pendente") }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(text: String, bg: Color, outline: Color) {
    Box(modifier = Modifier
        .height(24.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(bg)
        .padding(horizontal = 8.dp), contentAlignment = Alignment.Center) {
        Text(text = text, fontSize = 12.sp, color = Color(0xFF193CB8))
    }
}

@Composable
fun NewTicketDialog(onDismiss: () -> Unit, onCreate: (String, String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var obs by remember { mutableStateOf("") }

    AlertDialog(onDismissRequest = onDismiss, title = { Text("Criar novo ticket") }, text = {
        Column {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Tema do TCC") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = course, onValueChange = { course = it }, label = { Text("Curso") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = obs, onValueChange = { obs = it }, label = { Text("Observações") }, maxLines = 4)
        }
    }, confirmButton = {
        TextButton(onClick = {
            if (title.isNotBlank() && course.isNotBlank()) onCreate(title.trim(), course.trim(), obs.trim())
        }) { Text("Criar") }
    }, dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } })
}

@Composable
fun BottomBar(roleIsStudent: Boolean, onRoleToggle: (Boolean) -> Unit) {
    Surface(elevation = 4.dp) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .background(DeepBlue)
            .padding(horizontal = 24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFD2EBFF))
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF1B5C9D), modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Meus Envios", color = Color(0xFF1B5C9D))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = if (roleIsStudent) "Aluno" else "Bibliotecária", color = Color.White, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                // Toggle role for demo purposes
                Button(onClick = { onRoleToggle(!roleIsStudent) }, shape = RoundedCornerShape(12.dp)) {
                    Text(text = "Trocar Papel")
                }
            }
        }
    }
}


