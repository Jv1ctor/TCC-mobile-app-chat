package com.example.tccmobile.domain.repository

import com.example.tccmobile.features.dashboard.data.models.Metric
import com.example.tccmobile.features.dashboard.data.models.QualityMetric
import com.example.tccmobile.features.dashboard.data.models.ResponseTimeMetric
import com.example.tccmobile.features.dashboard.data.models.Review
import com.example.tccmobile.features.dashboard.data.models.TeamMember



import com.example.tccmobile.features.dashboard.data.models.*

/**
 * Interface que define os métodos para buscar todos os dados do Dashboard.
 *
 * Em um projeto real, esta interface seria implementada por:
 * 1. Uma classe que busca dados da API (ex: DashboardRepositoryImpl).
 * 2. Uma classe que busca dados do banco de dados local (ex: LocalDashboardRepository).
 */
interface DashboardRepository {

    /**
     * Busca todas as métricas principais do usuário (Corrigidos, Pendentes, Concluídos).
     * @return Lista de objetos Metric.
     */
    suspend fun getMyMetrics(): List<Metric>

    /**
     * Busca a métrica de qualidade (Avaliação Média).
     * @return QualityMetric.
     */
    suspend fun getQualityMetric(): QualityMetric

    /**
     * Busca a métrica de tempo de resposta.
     * @return ResponseTimeMetric.
     */
    suspend fun getResponseTimeMetric(): ResponseTimeMetric

    /**
     * Busca as avaliações mais recentes.
     * @return Lista de objetos Review.
     */
    suspend fun getRecentReviews(): List<Review>

    /**
     * Busca o desempenho resumido da equipe.
     * @return Lista de objetos TeamMember.
     */
    suspend fun getTeamPerformance(): List<TeamMember>

    /**
     * Agrupa todas as chamadas em um único objeto de retorno, ideal para otimização.
     */
    suspend fun getAllDashboardData(): DashboardDataBundle
}

/**
 * Classe para agrupar todos os dados retornados de uma única vez.
 */
data class DashboardDataBundle(
    val myMetrics: List<Metric>,
    val qualityMetric: QualityMetric,
    val responseTimeMetric: ResponseTimeMetric,
    val recentReviews: List<Review>,
    val teamPerformance: List<TeamMember>
)