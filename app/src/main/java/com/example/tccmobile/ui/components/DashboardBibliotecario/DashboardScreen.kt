package com.example.tccmobile.ui.components.DashboardBibliotecario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// IMPORTS NECESSÁRIOS: Garante que os componentes e dados de outros arquivos sejam encontrados
import com.example.tccmobile.R // Para acessar R.drawable.xxx (ícones)
import com.example.tccmobile.ui.theme.Pink40
// Importando as cores necessárias
import com.example.tccmobile.ui.theme.black
import com.example.tccmobile.ui.theme.white
import com.example.tccmobile.ui.theme.green
import com.example.tccmobile.ui.theme.yellow


@Composable
fun DashboardScreen() {

    // DADOS DE TESTE (MOCK) - Lista de objetos CompletionCardData para preencher a tela.
    val sampleMetrics = listOf(
        CompletionCardData(
            mainTitle = "TCCs Corrigidos",
            detailText = "15 aprovados com sucesso",
            iconResId = R.drawable.folha,
            iconTint = green
        ),
        CompletionCardData(
            mainTitle = "Pendentes",
            detailText = "Em Análise",
            iconResId = R.drawable.clock,
            iconTint = yellow
        ),
        CompletionCardData(
            mainTitle = "Concluídos",
            detailText = "Aprovados com sucesso",
            iconResId = R.drawable.uicon,
            iconTint = Pink40
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

        // 2. MEUS INDICADORES / LINHA DE CARDS
        // Chamada do componente MetricCardRow com os dados mockados
        MetricCardRow(cardMetrics = sampleMetrics)

        // 3. SEÇÃO DE OUTROS CONTEÚDOS (Placeholder)
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                "Conteúdo do Dashboard",
                color = black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Placeholder para simular mais conteúdo na tela
            Spacer(modifier = Modifier.height(400.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}