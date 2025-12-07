package com.example.tccmobile.ui.screens.bibliodashscreen

// Imports necessários para os modelos de dados (assumidos do código original)
import com.example.tccmobile.ui.components.DashboardBibliotecario.CompletionCardData
import com.example.tccmobile.ui.components.DashboardBibliotecario.QualityServiceCard
import com.example.tccmobile.ui.components.DashboardBibliotecario.RecentReviewData
import com.example.tccmobile.ui.components.DashboardBibliotecario.TeamMemberData

/**
 * Representa o estado completo da tela do Dashboard.
 *
 * @property isLoading Indica se os dados estão sendo carregados.
 * @property metrics Lista de cards de métricas principais (corrigidos, pendentes, etc.).
 * @property qualityServiceMetrics Lista de cards de qualidade do serviço (avaliação, tempo de resposta).
 * @property recentReviewMetrics Lista das últimas avaliações de estudantes.
 * @property teamPerformanceMetrics Lista de desempenho dos membros da equipe.
 * @property error Mensagem de erro, se houver.
 */
data class DashboardState(
    val isLoading: Boolean = false,
    val metrics: List<CompletionCardData> = emptyList(),
    val qualityServiceMetrics: List<QualityServiceCard> = emptyList(),
    val recentReviewMetrics: List<RecentReviewData> = emptyList(),
    val teamPerformanceMetrics: List<TeamMemberData> = emptyList(),
    val error: String? = null
)