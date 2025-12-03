package com.example.tccmobile.ui.components.DashboardBibliotecario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.BadgePositionColor
import com.example.tccmobile.ui.theme.BadgePositionColor2
import com.example.tccmobile.ui.theme.BadgePositionColor3
import com.example.tccmobile.ui.theme.yellow2

// --- ESTRUTURA DE DADOS ---
data class TeamMemberData(
    val name: String,
    val correctedCount: Int,
    val pendingCount: Int,
    val rating: Float, // Ex: 4.8f
    val position: Int // 1 para 1º, 2 para 2º, 3 para 3º, 4 para 4º (para badge/ícone)
)

// --- COMPONENTE DE CARTÃO INDIVIDUAL ---
@Composable
fun TeamPerformanceCard(data: TeamMemberData, modifier: Modifier = Modifier) {
    val starColor = yellow2
    val primaryTextColor = Color.Black.copy(alpha = 0.85f)
    val secondaryTextColor = Color.Black.copy(alpha = 0.6f)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 1. Ícone / Medalha de Posição
            TeamPositionBadge(position = data.position)

            Spacer(Modifier.width(16.dp))

            // 2. Nome e Detalhes
            Column(modifier = Modifier.weight(1f)) {
                // Nome
                Text(
                    text = data.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryTextColor
                )
                Spacer(Modifier.height(2.dp))
                // Estatísticas
                Text(
                    text = "${data.correctedCount} corrigidos • ${data.pendingCount} pendentes",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = secondaryTextColor
                )
            }

            // 3. Avaliação (Estrela e Nota)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Avaliação",
                    tint = starColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = String.format("%.1f", data.rating),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryTextColor
                )
            }
        }
    }
}

// --- FUNÇÃO AUXILIAR PARA O BADGE DE POSIÇÃO ---
@Composable
fun TeamPositionBadge(position: Int) {
    val (iconColor, bgColor) = when (position) {
        1 -> Pair(yellow2, yellow2.copy(alpha = 0.2f)) // Ouro
        2 -> Pair(BadgePositionColor, BadgePositionColor.copy(alpha = 0.2f)) // Prata
        3 -> Pair(BadgePositionColor2, BadgePositionColor2.copy(alpha = 0.2f)) // Bronze
        else -> Pair(BadgePositionColor3, BadgePositionColor3.copy(alpha = 0.1f)) // Outros (Cinzento)
    }

    Box(
        modifier = Modifier
            .size(48.dp) // Tamanho do Box
            .background(bgColor, CircleShape), // Fundo circular
        contentAlignment = Alignment.Center
    ) {
        if (position <= 3) {
            // Usando uma representação simplificada de medalha (pode ser ajustada para um ícone real)
            Text(
                text = "🏆", // Ícone unicode de troféu
                fontSize = 20.sp,
                modifier = Modifier.offset(y = (-2).dp)
            )
        } else {
            // Posição numérica para 4º lugar em diante
            Text(
                text = "$position°",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = iconColor
            )
        }
    }
}