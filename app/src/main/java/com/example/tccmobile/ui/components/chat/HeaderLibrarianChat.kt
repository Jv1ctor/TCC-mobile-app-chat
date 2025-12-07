package com.example.tccmobile.ui.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.data.entity.TicketStatus
import com.example.tccmobile.ui.components.utils.StatusBadge
import com.example.tccmobile.ui.components.utils.StatusBadgeModel
import com.example.tccmobile.ui.theme.DarkBlue
import com.example.tccmobile.ui.theme.LightBlue
import com.example.tccmobile.ui.theme.StatusContainerPendente
import com.example.tccmobile.ui.theme.StatusTextPendente
import com.example.tccmobile.ui.theme.White

@Composable
fun HeaderLibrarianChat(
    ticketId: String,
    title: String,
    subtitle: String,
    studentName: String,
    studentRegistry: String,
    studentEmail: String,
    badge: TicketStatus,
    onBackClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBlue)
            .padding(20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                onClick = onBackClick,
                color = Color(0xFF183863),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(32.dp),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Voltar",
                        color = White,
                        fontSize = 14.sp
                    )
                }
            }

            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "#$ticketId",
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )

        Text(
            text = title,
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )

        Text(
            text = subtitle,
            color = LightBlue,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        InfoRow(label = "Aluno:", value = studentName)
        InfoRow(label = "Matrícula:", value = studentRegistry)
        InfoRow(label = "E-mail:", value = studentEmail)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)// spacedBy coloca um espacinho automático entre cada badge
        ) {

            StatusBadge(
                text = badge.label,
                backgroundColor = badge.containerColor,
                textColor = badge.contentColor
            )

        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.padding(bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = LightBlue,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            color = White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
fun HeaderLibrarianChatPreview() {
    HeaderLibrarianChat(
        ticketId = "123131",
        title = "Desenvolvimento de Sistema Web",
        subtitle = "Engenharia de Software",
        studentName = "João Silva",
        studentRegistry = "2021001234",
        studentEmail = "joao.silva@edu.unifor.br",
        badge = TicketStatus("Pendente", StatusContainerPendente, StatusTextPendente, null)
    )
}