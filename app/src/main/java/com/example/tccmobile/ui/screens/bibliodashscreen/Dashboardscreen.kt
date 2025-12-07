package com.example.tccmobile.ui.screens.bibliodashscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tccmobile.ui.components.DashboardBibliotecario.DashboardHeader
import com.example.tccmobile.ui.components.DashboardBibliotecario.MetricCardRow
import com.example.tccmobile.ui.components.DashboardBibliotecario.QualityServiceCard
import com.example.tccmobile.ui.components.DashboardBibliotecario.RecentReviewsCard
import com.example.tccmobile.ui.components.DashboardBibliotecario.TeamPerformanceCard
import com.example.tccmobile.ui.components.bibliotecaria.HeaderTickets
import com.example.tccmobile.ui.components.utils.AppHeader
import com.example.tccmobile.ui.components.utils.BottomNavItem
import com.example.tccmobile.ui.components.utils.BottomNavigationBar
import com.example.tccmobile.ui.theme.BackgroundEnd
import com.example.tccmobile.ui.theme.Branco


@Composable
fun DashboardScreen(
    // Injeção de dependência do ViewModel
    viewModel: DashboardViewModel = viewModel(),
    navigateBarItems: List<BottomNavItem>,
    currentRoute: String,
    onDashboardClick: () -> Unit
) {
    // Observa e coleta o estado mais recente do ViewModel
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Branco)

    ) {
        AppHeader(
            title = "Dashboard de Desempenho",
            subtitle = "indicadores e estatística do sistema"
        )
        // 2. CONTEÚDO CENTRAL (Scrollable)
        // O .weight(1f) garante que esta coluna ocupe todo o espaço restante após o Header e a Nav Bar.
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()) // Apenas esta área rola!
        ) {

            // --- UI Principal (Usa os dados de 'state') ---

            // TÍTULO: Meus Indicadores
            Text(
                text = "Meus Indicadores",
                color = BackgroundEnd,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, top = 20.dp, bottom = 4.dp)
            )

            // COMPONENTES: Cards de Métrica
            MetricCardRow(cardMetrics = state.metrics)


            // TÍTULO: Qualidade do Atendimento
            Text(
                text = "Qualidade do Atendimento",
                color = BackgroundEnd,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, top = 32.dp, bottom = 4.dp)
            )

            // COMPONENTES: Cards de Qualidade do Atendimento
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.qualityServiceMetrics.forEach { data ->
                    QualityServiceCard(data = data)
                }
            }

            // TÍTULO: Avaliações Recentes
            Text(
                text = "Avaliações Recentes",
                color = BackgroundEnd,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, top = 32.dp, bottom = 4.dp)
            )

            // COMPONENTES: Cards de Avaliações Recentes
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.recentReviewMetrics.forEach { data ->
                    RecentReviewsCard(data = data)
                }
            }

            // NOVO TÍTULO: Desempenho da Equipe
            Text(
                text = "Desempenho da Equipe",
                color = BackgroundEnd,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, top = 32.dp, bottom = 4.dp)
            )

            // NOVO COMPONENTE: Cards de Desempenho da Equipe
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.teamPerformanceMetrics.forEach { data ->
                    TeamPerformanceCard(data = data)
                }
            }

            // Espaço final para o scroll
            Spacer(Modifier.height(52.dp))
        }

        // 6. Barra de Navegação Inferior
        BottomNavigationBar(
            items = navigateBarItems,
            currentRoute = currentRoute,
        )
    }
}
