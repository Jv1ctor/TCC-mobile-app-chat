package com.example.tccmobile.ui.components.bibliotecaria

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.InsertChart
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
import com.example.tccmobile.ui.theme.HeaderBlue
import com.example.tccmobile.ui.theme.White

@Composable
fun HeaderTickets(
    countTotal: Int,
    countOpen: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = HeaderBlue)
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
                border = BorderStroke(width = 1.dp, color = Color.White.copy(alpha = 0.2F)), // Adiciona a borda branca e fina
                cornerRadius = 35,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.BarChart,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.White
                    )
                },
                onClick = { /* Ação futura de navegação */ }
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
                count = "4",
                icon = Icons.Outlined.Description
            )
            CardInfosTickets(
                modifier = Modifier.weight(1f),
                label = "Abertos",
                count = "2",
                icon = Icons.Outlined.Schedule
            )
        }
    }
}

@Preview
@Composable
fun HeaderTicketsPreview() {
    HeaderTickets(countOpen = 1, countTotal = 2)
}