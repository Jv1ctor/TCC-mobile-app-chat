package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.DarkBlue
import com.example.tccmobile.ui.theme.LightBlue
import com.example.tccmobile.ui.theme.White

@Composable
fun LibrarianHeader(
    totalCount: Int = 0,
    openCount: Int = 0,
    priorityCount: Int = 0,
    onDashboardClick: () -> Unit = {}
) {
    val cardBackground = Color(0xFF183863) // deixei essa cor local pq ela só existe aqui nesse header

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBlue)
            .padding(20.dp)
    ) {
        Row( // linha do topo
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // joga o texto pra esquerda e o botão pra direita
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column { // coluna da esquerda com os textos
                Text(
                    text = "Tickets de TCC",
                    color = White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Gerencie as\nsubmissões dos alunos", // o \n quebra a linha visualmente
                    color = LightBlue,
                    fontSize = 14.sp
                )
            }


            Surface(// botão dashboard
                onClick = onDashboardClick, // usa a ação que foi passada lá em cima
                color = cardBackground,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(0.5.dp, LightBlue),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Row( // organiza o ícone e o texto do botão um do lado do outro
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.BarChart, // ícone de gráfico
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // espacinho entre icone e texto
                    Text(
                        text = "Dashboard",
                        color = White,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Row( // linha que segura os 3 quadrados
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // espaço igual entre cada quadrado
        ) {
            StatCard(//total
                icon = Icons.Outlined.Description,
                label = "Total",
                count = totalCount.toString(), // converte o numero pra texto
                backgroundColor = cardBackground,
                modifier = Modifier.weight(1f) // weight(1f) faz ele ocupar 1/3 da tela certinho
            )

            StatCard(//abertos
                icon = Icons.Outlined.Schedule,
                label = "Abertos",
                count = openCount.toString(),
                backgroundColor = cardBackground,
                modifier = Modifier.weight(1f)
            )

            StatCard(//prioridade
                icon = Icons.Outlined.Warning,
                label = "Prioridade",
                count = priorityCount.toString(),
                backgroundColor = cardBackground,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StatCard( // criei isso pra não ter que copiar e colar o mesmo código 3 vezes
    icon: ImageVector,
    label: String,
    count: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(100.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) { // linha do ícone + legenda
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = LightBlue,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = label,
                    color = LightBlue,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text( 
                text = count,
                color = LightBlue,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
fun LibrarianHeaderPreview() {
    LibrarianHeader(
        totalCount = 15, //total
        openCount = 3, //abertos
        priorityCount = 1 //prioridades
    )
}