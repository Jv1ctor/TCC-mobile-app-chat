package com.example.tccmobile.ui.components.DashboardBibliotecario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.ui.Alignment

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.incrementosgreen

// MODELO DE DADOS CENTRALIZADO: Esta é a única definição para evitar conflitos.
data class CompletionCardData(
    val mainTitle: String,
    val quantidade: Int,// (ex: "15")
    val detailText: String, // Texto de detalhe (ex: "TCCs Corrigidos")
    val iconVector: ImageVector,
    val iconTint: Color,
    val iconBackgroundColor: Color,
    val incrementoMes: Int? = null // +12 opcional
)


/**
 * Componente que exibe um único Card de Métrica/Conclusão com ícone e texto.
 */
@Composable
fun CompletionCard(
    mainTitle: String,
    quantidade: Int,
    detailText: String,
    iconVector: ImageVector,
    iconTint: Color,
    iconBackgroundColor: Color,
    incrementoMes: Int? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .width(500.dp), //largura dos cards/icones grandões
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .height(120.dp)

        ) {

            Box(
                modifier = Modifier
                    .offset(y = 18.dp) // onde o icone deve ficar
                    .background(
                        color = iconBackgroundColor.copy(alpha = 0.25f),
                        RoundedCornerShape(10.dp) //efeitos icones
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Ícone
                Icon(
                    imageVector = iconVector, // Usando o ImageVector
                    contentDescription = "Ícone de ${mainTitle}",
                    tint = iconTint,
                    modifier = Modifier.padding(18.dp, 18.dp) //fundo do icone

                )
            }

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(
                    1F,
                    fill = true
                ),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp) //espaço entre Título e o resto
            )
            {
                // Título Principal
                Text(
                    text = mainTitle,
                    color = Color.Black.copy(alpha = 0.85f),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    // QUANTIDADE ou NÚMERO
                    Text(
                        text = quantidade.toString(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp) //altura da quantidade
                    )

                    Spacer(Modifier.width(20.dp))


                    // Texto de Detalhe (Ex: "Em análise")
                    Text(
                        text = detailText,
                        color = Color.Black.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 2
                    )

                    // INDICADOR DE MÊS (verde)
                    if (incrementoMes != null) {
                        Spacer(
                            Modifier
                                .height(10.dp)
                                .width(8.dp)

                        )
                        //incremento mÊs
                        Text(
                            text = "+${incrementoMes} este mês",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF2ECC71),
                            modifier = Modifier.height(25.dp)


                        )
                        // 2. Row para agrupar Ícone e Texto de Incremento
                        Row(
                            verticalAlignment = Alignment.CenterVertically // Alinha verticalmente
                        ) {

                            Spacer(Modifier.width(20.dp)) // Espaço entre o ícone e o texto

                            // Ícone (Seta para cima)
                            Icon(
                                imageVector = Icons.Default.ArrowOutward,
                                contentDescription = "Aumento no mês",
                                tint = incrementosgreen, // Verde
                                modifier = Modifier
                                    .size(20.dp)
                                    // Ajuste leve para cima para alinhar melhor com o texto
                                    .offset(y = (-2).dp)
                            )
                        }

                        Spacer(
                            Modifier
                                .height(15.dp)
                                .width(15.dp)
                        )

                    }
                }
            }
        }
    }
}