package com.example.tccmobile.ui.components.DashboardBibliotecario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.green
import com.example.tccmobile.ui.theme.incrementosgreen
import com.example.tccmobile.ui.theme.yellow2


data class QualityServiceCard(
    val  mainTitle: String, //título
    val quantidade: Int, //quantidade - 4.8
    val detailText: String, // texto de detalhes - de 5.0 estrelas
    val iconVector: ImageVector, //icone
    val iconTint: Color, //cor do icone
    val iconBackgroundColor: Color, //fundo do icone
    val hasStars: Boolean = false, // Para mostrar estrelas (Avaliação Média)
    val incrementoMes: Int? = null // +12 opcional
)

@Composable
fun QualityServiceCard(
    data: QualityServiceCard,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(horizontal = 14.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = data.iconBackgroundColor.copy(alpha = 0.25f),
                        RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = data.iconVector,
                    contentDescription = "Ícone de ${data.mainTitle}",
                    tint = data.iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Título Principal (Ex: Avaliação Média)
                Text(
                    text = data.mainTitle,
                    color = Color.Black.copy(alpha = 0.85f),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(4.dp))

                // Linha do Valor/Estrelas
                Row(verticalAlignment = Alignment.CenterVertically)
                {
                    // Valor Principal (Ex: 4.8 ou 2.3 dias)
                    Text(
                        text = data.quantidade.toString(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(Modifier.width(8.dp))

                    if (data.hasStars) {
                        // Estrelas (Mockup Simplificado: 3.5 estrelas cheias de 5)
                        Row {
                            (1..5).forEach { index ->
                                Icon(
                                    imageVector = if (index <= 3) Icons.Default.Star else Icons.Default.StarBorder,
                                    contentDescription = null,
                                    tint = if (index <= 4) yellow2 else Color.LightGray, // Amarelo para 4 estrelas
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    } else if (data.incrementoMes != null) {
                        // Incremento de Tempo (Ex: -15% verde)
                        Icon(
                            imageVector = Icons.Default.ArrowUpward, // Seta para cima (simulando gráfico)
                            contentDescription = null,
                            tint = incrementosgreen, // Verde
                            modifier = Modifier
                                .size(20.dp)
                                .offset(y = (-4).dp) // Pequeno ajuste vertical
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = data.incrementoMes.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = incrementosgreen// Verde
                        )
                    }

                    Spacer(Modifier.width(20.dp))

                    // Texto de Detalhe (Ex: de 5.0 estrelas)
                    Text(
                        text = data.detailText,
                        color = Color.Black.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 2,
                        modifier = Modifier.offset(y = 2.dp)
                    )
                }
            }
        }
    }
}