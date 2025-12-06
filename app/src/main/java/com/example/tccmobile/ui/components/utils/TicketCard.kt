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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.HeaderBlue
import com.example.tccmobile.ui.theme.TextGray
import com.example.tccmobile.ui.theme.NotificationRed
import com.example.tccmobile.ui.screens.studentTicketsScreen.Ticket
import com.example.tccmobile.ui.screens.studentTicketsScreen.TicketTagStatus
import com.example.tccmobile.ui.theme.Gray

@Composable
fun TicketCard(
    ticket: Ticket,
    showStudentInfo: Boolean = false,
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
                titulo = ticket.titulo,
                notificacoes = ticket.notificacoes
            )

            Spacer(modifier = Modifier.height(4.dp))

            TicketBodyInfo(
                categoria = ticket.categoria,
                nomeAluno = if (showStudentInfo) ticket.nomeAluno else null,
                dataAbertura = ticket.dataAbertura
            )

            Spacer(modifier = Modifier.height(12.dp))

            TicketStatusRow(tags = ticket.tags)

            Spacer(modifier = Modifier.height(16.dp))

            TicketFooter(
                showStudentInfo = showStudentInfo,
                dataAbertura = ticket.dataAbertura,
                dataAtualizacao = ticket.dataAtualizacao,
                atribuidoPara = ticket.atribuidoPara
            )
        }
    }
}

@Composable
private fun TicketHeader(titulo: String, notificacoes: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = titulo,
            color = HeaderBlue,
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
            color = TextGray,
            fontSize = 14.sp
        )

        // Só renderiza se o nomeAluno for passado (lógica de nulidade é do Kotlin, não visual)
        if (nomeAluno != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                        append("Aluno: ")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(nomeAluno)
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                        append(" \nSubmetido em: ")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(dataAbertura)
                    }
                },
                color = Gray,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
private fun TicketStatusRow(tags: List<TicketTagStatus>) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        tags.forEach { tag ->
            StatusBadge(
                text = tag.label,
                backgroundColor = tag.containerColor,
                textColor = tag.contentColor,
                icon = tag.icon
            )
        }
    }
}

@Composable
private fun TicketFooter(
    showStudentInfo: Boolean,
    dataAbertura: String,
    dataAtualizacao: String,
    atribuidoPara: String?
) {
    if (!showStudentInfo) {
        // Visão do Aluno: Datas
        Column {
            Text("Aberto em $dataAbertura", color = TextGray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text("Atualizado em $dataAtualizacao", color = TextGray, fontSize = 12.sp)
        }
    } else if (atribuidoPara != null) {
        // Visão da Bibliotecária: Atribuição
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                        append("Atribuído: ")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(atribuidoPara)
                    }

                },
                color = TextGray,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}