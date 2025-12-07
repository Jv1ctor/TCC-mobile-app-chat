package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.data.entity.TicketStatus
import com.example.tccmobile.helpers.formattedInstant
import com.example.tccmobile.ui.theme.AzulLetra
import com.example.tccmobile.ui.theme.Cinza
import com.example.tccmobile.ui.theme.NotificationRed
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Composable
fun TicketCard(
    subject: String,
    course: String,
    author: String,
    status: TicketStatus,
    createAt: Instant,
    updatedAt: Instant,
    showStudentInfo: Boolean = false,
    countMessages: Int = 0,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            TicketHeader(
                titulo = subject
            )

            Spacer(modifier = Modifier.height(4.dp))

            TicketBodyInfo(
                categoria = course,
                nomeAluno = if (showStudentInfo) author else null,
                dataAbertura = formattedInstant(createAt)
            )

            Spacer(modifier = Modifier.height(12.dp))

            TicketStatusRow(tag = status)

            Spacer(modifier = Modifier.height(16.dp))

            TicketFooter(
                showStudentInfo = showStudentInfo,
                dataAbertura = formattedInstant(createAt),
                dataAtualizacao =  formattedInstant(updatedAt),
            )
        }
    }
}

@Composable
private fun TicketHeader(titulo: String, notificacoes: Int = 0) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = titulo,
            color = AzulLetra,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f).padding(end = 8.dp),
            lineHeight = 22.sp
        )

        if (notificacoes > 0) {
            NotificationBadge(count = notificacoes)
        }
    }
}

@Composable
private fun NotificationBadge(count: Int) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(NotificationRed, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TicketBodyInfo(categoria: String, nomeAluno: String?, dataAbertura: String) {
    Column {
        Text(
            text = categoria,
            color = Cinza,
            fontSize = 14.sp
        )

        // Só renderiza se o nomeAluno for passado (lógica de nulidade é do Kotlin, não visual)
        if (nomeAluno != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Aluno: $nomeAluno • Submetido em $dataAbertura",
                color = Color(0xFF475569),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun TicketStatusRow(tag: TicketStatus) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        StatusBadge(
            text = tag.label,
            backgroundColor = tag.containerColor,
            textColor = tag.contentColor,
            icon = tag.icon
        )

    }
}

@Composable
private fun TicketFooter(
    showStudentInfo: Boolean,
    dataAbertura: String,
    dataAtualizacao: String,
) {
    if (!showStudentInfo) {
        // Visão do Aluno: Datas
        Column {
            Text("Aberto em $dataAbertura", color = Cinza, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text("Atualizado em $dataAtualizacao", color = Cinza, fontSize = 12.sp)
        }
    }
}