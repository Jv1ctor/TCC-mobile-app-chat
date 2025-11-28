package com.example.tccmobile.ui.components.DashboardBibliotecario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tccmobile.R
import com.example.tccmobile.ui.theme.green // Cores importadas do seu Color.kt
import com.example.tccmobile.ui.theme.yellow // Cores importadas do seu Color.kt
import com.example.tccmobile.ui.theme.Pink80 // Usando temporariamente para "Novos Envio"
import com.example.tccmobile.ui.theme.DarkBlueBackground

/**
 * Componente contêiner para exibir a lista de cards EM COLUNA (um abaixo do outro).
 * Ele chama o CompletionCard. Esta é a ÚNICA definição desta função.
 */
@Composable
fun MetricCardRow(cardMetrics: List<CompletionCardData>) {
    // Usa Column para empilhar os cards (verticalmente)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espaçamento entre os cards
    ) {
        // Itera sobre a lista de dados e chama o CompletionCard para cada um
        cardMetrics.forEach { data ->
            CompletionCard(data = data)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MetricCardRowPreview() {
    // Dados de exemplo para o Preview (MOCK)
    val previewMetrics = listOf(
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
            iconTint = Pink80
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlueBackground)
            .padding(vertical = 16.dp)
    ) {
        // Chamando o componente MetricCardRow com os dados de exemplo
        MetricCardRow(cardMetrics = previewMetrics)
    }
}