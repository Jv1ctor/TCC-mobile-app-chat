

package com.example.tccmobile.ui.screens.bibliodashscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// IMPORTS NECESSÁRIOS: Garante que os componentes e dados de outros arquivos sejam encontrados
import com.example.tccmobile.ui.components.DashboardBibliotecario.CompletionCardData
import com.example.tccmobile.ui.components.DashboardBibliotecario.DashboardHeader
import com.example.tccmobile.ui.components.DashboardBibliotecario.MetricCardRow
import com.example.tccmobile.ui.theme.ClaroBlueBackground
import com.example.tccmobile.ui.theme.DarkBlueBackground
import com.example.tccmobile.ui.theme.IconBackgroundGreen
import com.example.tccmobile.ui.theme.IconBackgroundRed
import com.example.tccmobile.ui.theme.VermelhoTelha
import com.example.tccmobile.ui.theme.BackgroundEnd
// Importando as cores necessárias
import com.example.tccmobile.ui.theme.white
import com.example.tccmobile.ui.theme.green




@Composable
fun DashboardScreen() {

    // DADOS DE TESTE (MOCK) - Lista de objetos CompletionCardData para preencher a tela.
    val sampleMetrics = listOf(
        CompletionCardData(
            mainTitle = "TCCs Corrigidos",
            quantidade = 45,
            detailText = "",
            incrementoMes = 12,
            // USANDO Material Icons (CheckCircle)
            iconVector = Icons.Default.Assignment,
            iconTint = DarkBlueBackground,
            iconBackgroundColor = ClaroBlueBackground
        ),
        CompletionCardData(
            mainTitle = "Pendentes",
            quantidade = 8,
            detailText = "Em Análise",
            incrementoMes = null,
            // USANDO Material Icons (PendingActions)
            iconVector = Icons.Default.AccessTime,
            iconTint = VermelhoTelha,
            iconBackgroundColor = IconBackgroundRed // NOVO: Fundo

        ),
        CompletionCardData(
            mainTitle = "Concluídos",
            quantidade = 37,
            detailText = "aprovados com sucesso",
            incrementoMes = null,
            // USANDO Material Icons (Assignment)
            iconVector = Icons.Default.CheckCircleOutline,
            iconTint = green,
            iconBackgroundColor = IconBackgroundGreen // NOVO: Fundo

        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white) // Fundo da tela
            .verticalScroll(rememberScrollState()) // Permite rolagem
    ) {
        // 1. HEADER (Componente de cabeçalho)
        DashboardHeader(onBackClicked = {
            println("Navegar de volta...")
        })

        Text(
            text = "Meus Indicadores",
            color = BackgroundEnd, // Ou a cor exata do seu tema para títulos
            fontSize = 20.sp, // Ajuste o tamanho da fonte para o seu design
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 24.dp, top = 20.dp, bottom = 4.dp) // <--- Padding para alinhamento
        )

        // 2. MEUS INDICADORES / LINHA DE CARDS
        // Chamada do componente MetricCardRow com os dados mockados
        MetricCardRow(cardMetrics = sampleMetrics)


    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}