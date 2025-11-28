package com.example.tccmobile.ui.components.DashboardBibliotecario

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // Importação adicionada para o clique
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.tccmobile.ui.theme.containerBlue


@Composable
fun DashboardHeader(onBackClicked: () -> Unit) {
    val buttonBackground = Color.Black.copy(alpha = 0.15f)
    val cornerRadius = 10.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(containerBlue) // Usando a nova cor de fundo
            .padding(bottom = 24.dp)
    ) {
        // Botão Voltar (Ação)
        Row(
            modifier = Modifier
                .padding(start = 12.dp, top = 48.dp)
                .clip(RoundedCornerShape(10.dp))
                // 1. Aplica o background sutil com cantos arredondados
                .background(buttonBackground, RoundedCornerShape(cornerRadius))
                // 2. Garante que a área de clique seja recortada com os cantos arredondados
                .clip(RoundedCornerShape(cornerRadius))
                .clickable { onBackClicked() } // Ação de clique ativada
                // 3. Adiciona padding interno para a área de toque/botão
                .clickable { onBackClicked() } // Ação de clique ativada
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.White,
                modifier = Modifier.size(24.dp).padding(start = 6.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                "Voltar",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(end = 12.dp, top = 6.dp, bottom = 6.dp)
            )
        }

        // Títulos
        Column(modifier = Modifier.padding(horizontal = 24.dp).padding(top = 28.dp)) {
            Text(
                "Dashboard de Desempenho",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Indicadores e estatísticas do sistema",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardHeaderPreview() {
    // Usaremos um Box para simular o fundo do telefone e ver a cor do Header
    Box(modifier = Modifier.background(Color.White)) {
        DashboardHeader(onBackClicked = {})
    }
}