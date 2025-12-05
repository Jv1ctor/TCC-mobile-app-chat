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

import com.example.tccmobile.ui.theme.green

import com.example.tccmobile.ui.theme.DarkBlueBackground
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime

import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircleOutline

import com.example.tccmobile.ui.theme.ClaroBlueBackground
import com.example.tccmobile.ui.theme.IconBackgroundGreen
import com.example.tccmobile.ui.theme.IconBackgroundRed

import com.example.tccmobile.ui.theme.VermelhoTelha

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
            .padding(horizontal = 10.dp, vertical = 20.dp),
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
            .background(DarkBlueBackground)
            .padding(vertical = 16.dp)
    ) {
        // Chamando o componente MetricCardRow com os dados de exemplo
        MetricCardRow(cardMetrics = previewMetrics)
    }
}