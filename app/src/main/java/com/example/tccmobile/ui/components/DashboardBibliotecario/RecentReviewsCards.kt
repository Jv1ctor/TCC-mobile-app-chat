package com.example.tccmobile.ui.components.DashboardBibliotecario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
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
import com.example.tccmobile.ui.theme.HeaderBackground
import com.example.tccmobile.ui.theme.yellow2

// --- ESTRUTURA DE DADOS ---
data class RecentReviewData(
    val studentName: String,
    val rating: Float, // Ex: 5.0, 4.0
    val comment: String
)

// --- COMPONENTE ---
@Composable
fun RecentReviewsCard(data: RecentReviewData, modifier: Modifier = Modifier) {
    // Definimos a cor amarela das estrelas
    val starColor = yellow2

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp), // Padding lateral para alinhamento com os cards acima
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Nome do Aluno
                Text(
                    text = data.studentName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = HeaderBackground
                )

                // Nota Numérica (5.0, 4.0)
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.LightGray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = String.format("%.1f", data.rating), // Formata para 1 casa decimal
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            // Linha de Estrelas e Comentário
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Estrelas
                RatingStars(
                    rating = data.rating,
                    starColor = starColor
                )
            }

            Spacer(Modifier.height(8.dp))

            // Comentário
            Text(
                text = data.comment,
                fontSize = 15.sp,
                color = Color.Black.copy(alpha = 0.7f),
                maxLines = 2 // Limita para não ocupar muito espaço
            )
        }
    }
}

// --- FUNÇÃO AUXILIAR PARA RENDERIZAR ESTRELAS ---
@Composable
fun RatingStars(rating: Float, starColor: Color) {
    val totalStars = 5
    val fullStars = rating.toInt()
    val hasHalfStar = (rating - fullStars) >= 0.5f

    Row {
        (1..totalStars).forEach { index ->
            val starIcon = when {
                index <= fullStars -> Icons.Default.Star // Estrela cheia
                index == fullStars + 1 && hasHalfStar -> {
                    // Compose não tem ícone de meia estrela nativo,
                    // então para simplificar a UI, usamos estrela cheia/vazia aqui,
                    // mas o visual final é mais fiel se usarmos um ícone cheios/vazios
                    Icons.Default.Star // Usamos cheia para simplificar, mas o ideal seria a meia estrela
                }
                else -> Icons.Default.StarBorder // Estrela vazia
            }

            Icon(
                imageVector = starIcon,
                contentDescription = null,
                tint = if (index <= fullStars || (index == fullStars + 1 && hasHalfStar)) starColor else Color.LightGray.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}