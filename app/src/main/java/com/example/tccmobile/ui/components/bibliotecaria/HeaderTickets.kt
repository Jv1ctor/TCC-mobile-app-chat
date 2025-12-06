package com.example.tccmobile.ui.components.bibliotecaria

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.components.utils.ButtonForm
import com.example.tccmobile.ui.theme.AzulSuperClaro
import com.example.tccmobile.ui.theme.DarkBlue

@Composable
fun HeaderTickets(
    countTotal: Int,
    countOpen: Int,
    onDashboardClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = DarkBlue)
            .padding(24.dp)
    ) {
        // --- Linha Superior: Títulos e Botão Dashboard ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Tickets de TCC",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Gerencie as \nsubmissões dos alunos",
                    color = AzulSuperClaro,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            ButtonForm(
                text = "Dashboard",
                modifier = Modifier.height(36.dp),
                backgroundColor = Color.White.copy(alpha = 0.1F),
                border = BorderStroke(width = 1.dp, color = Color.White.copy(alpha = 0.2F)),
                cornerRadius = 35,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.BarChart,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.White
                    )
                },
                onClick = onDashboardClick
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CardInfosTickets(
                modifier = Modifier.weight(1f),
                label = "Total",
                count = countTotal.toString(),
                icon = Icons.Outlined.Description
            )
            CardInfosTickets(
                modifier = Modifier.weight(1f),
                label = "Abertos",
                count = countOpen.toString(),
                icon = Icons.Outlined.Schedule
            )
        }
    }
}

@Preview
@Composable
fun HeaderTicketsPreview() {
    HeaderTickets(countOpen = 1, countTotal = 2, onDashboardClick = {})
}
