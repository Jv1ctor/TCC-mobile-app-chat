package com.example.tccmobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tccmobile.ui.componentstwo.DashboardHeader
import com.example.tccmobile.ui.componentstwo.MetricCardRow // Localização correta
import com.example.tccmobile.ui.theme.DarkBlueBackground
import com.example.tccmobile.ui.theme.TextPrimary
import com.example.tccmobile.ui.theme.black
import com.example.tccmobile.ui.theme.white

@Composable
fun DashboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white) // Fundo da tela
            .verticalScroll(rememberScrollState()) // Permite rolagem se o conteúdo for grande
    ) {
        // 1. HEADER (Conexão com a funcionalidade 'Voltar')
        DashboardHeader(onBackClicked = {
            // Implementação da navegação de volta (Ex: navController.popBackStack())
            println("Navegar de volta...")
        })

        // 2. MEUS INDICADORES / LINHA DE CARDS
        MetricCardRow()

        // 3. SEÇÃO DE OUTROS CONTEÚDOS (Placeholder)
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                "Conteúdo do Dashboard",
                color = black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Futuramente, aqui virão seus gráficos e outros cards de informação.
            Spacer(modifier = Modifier.height(400.dp)) // Para simular mais conteúdo e permitir rolagem
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}