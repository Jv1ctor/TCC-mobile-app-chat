package com.example.tccmobile.ui.screens.bibliodashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Star
import com.example.tccmobile.ui.components.DashboardBibliotecario.CompletionCardData
import com.example.tccmobile.ui.components.DashboardBibliotecario.QualityServiceCard
import com.example.tccmobile.ui.components.DashboardBibliotecario.RecentReviewData
import com.example.tccmobile.ui.components.DashboardBibliotecario.TeamMemberData
import com.example.tccmobile.ui.theme.AmareloClaro2
import com.example.tccmobile.ui.theme.ClaroBlueBackground
import com.example.tccmobile.ui.theme.DarkBlueBackground
import com.example.tccmobile.ui.theme.IconBackgroundGreen
import com.example.tccmobile.ui.theme.IconBackgroundRed
import com.example.tccmobile.ui.theme.VermelhoTelha
import com.example.tccmobile.ui.theme.BackgroundIconTime
import com.example.tccmobile.ui.theme.green
import com.example.tccmobile.ui.theme.yellow2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    // 1. Estado Mutável (Modificável internamente)
    private val _state = MutableStateFlow(DashboardState())

    // 2. Estado Público (Somente leitura para a UI)
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    /**
     * Carrega todos os dados do dashboard. Em um aplicativo real,
     * isso faria chamadas de rede ou acessaria o banco de dados.
     */
    private fun loadDashboardData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            // Simulação de delay ou lógica de carregamento real
            // delay(1000)

            try {
                _state.value = DashboardState(
                    isLoading = false,
                    metrics = createSampleMetrics(),
                    qualityServiceMetrics = createQualityServiceMetrics(),
                    recentReviewMetrics = createRecentReviewMetrics(),
                    teamPerformanceMetrics = createTeamPerformanceMetrics()
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Erro ao carregar dados: ${e.message}"
                )
            }
        }
    }

    // --- Funções de Criação de Dados Mockados (Movidas do Composable) ---

    private fun createSampleMetrics(): List<CompletionCardData> = listOf(
        CompletionCardData(
            mainTitle = "TCCs corrigidos",
            quantidade = 45,
            detailText = "",
            incrementoMes = 12,
            iconVector = Icons.Default.Assignment,
            iconTint = DarkBlueBackground,
            iconBackgroundColor = ClaroBlueBackground
        ),
        CompletionCardData(
            mainTitle = "Pendentes",
            quantidade = 8,
            detailText = "Em Análise",
            incrementoMes = null,
            iconVector = Icons.Default.AccessTime,
            iconTint = VermelhoTelha,
            iconBackgroundColor = IconBackgroundRed
        ),
        CompletionCardData(
            mainTitle = "Concluídos",
            quantidade = 37,
            detailText = "aprovados com \n sucesso",
            incrementoMes = null,
            iconVector = Icons.Default.CheckCircleOutline,
            iconTint = green,
            iconBackgroundColor = IconBackgroundGreen
        )
    )

    private fun createQualityServiceMetrics(): List<QualityServiceCard> = listOf(
        QualityServiceCard(
            mainTitle = "Avaliação Média",
            quantidade = 4,
            detailText = "\nde 5.0 estrelas",
            hasStars = true,
            iconVector = Icons.Default.Star,
            iconTint = yellow2,
            iconBackgroundColor = AmareloClaro2
        ),
        QualityServiceCard(
            mainTitle = "Tempo Médio de Resposta",
            quantidade = 2,
            detailText = "Submissão - 1º\n feedback",
            incrementoMes = 15, // Por exemplo, 15% de melhoria
            iconVector = Icons.Default.AccessTime,
            iconTint = DarkBlueBackground,
            iconBackgroundColor = BackgroundIconTime
        )
    )

    private fun createRecentReviewMetrics(): List<RecentReviewData> = listOf(
        RecentReviewData(
            studentName = "João Silva",
            rating = 5.0f,
            comment = "Excelente atendimento e feedback detalhado!"
        ),
        RecentReviewData(
            studentName = "Maria Santos",
            rating = 5.0f,
            comment = "Muito atenciosa e prestativa."
        ),
        RecentReviewData(
            studentName = "Pedro Oliveira",
            rating = 4.0f,
            comment = "Bom atendimento, mas demorou um pouco."
        )
    )

    private fun createTeamPerformanceMetrics(): List<TeamMemberData> = listOf(
        TeamMemberData(
            name = "Ana Costa",
            correctedCount = 45,
            pendingCount = 8,
            rating = 4.8f,
            position = 1
        ),
        TeamMemberData(
            name = "Maria Santos",
            correctedCount = 38,
            pendingCount = 5,
            rating = 4.9f,
            position = 2
        ),
        TeamMemberData(
            name = "Paula Ferreira",
            correctedCount = 42,
            pendingCount = 6,
            rating = 4.7f,
            position = 3
        ),
        TeamMemberData(
            name = "Carla Lima",
            correctedCount = 35,
            pendingCount = 4,
            rating = 4.6f,
            position = 4
        )
    )

    // Ação para o clique no botão de voltar (exemplo de manipulação de evento)
    fun onBackClicked() {
        println("Navegar de volta (Ação no ViewModel)")
        // Lógica de navegação real seria implementada aqui (Ex: navigateUp())
    }
}