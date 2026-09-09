package ar.edu.isvdr.frontend.feature.news.list

import ar.edu.isvdr.frontend.feature.news.model.NewsEvent
import ar.edu.isvdr.frontend.feature.news.model.NewsType

/**
 * Rango de fecha rápido para el filtro de la tarjeta 4.
 * Se resuelve a fechaDesde/fechaHasta ISO 8601 en el ViewModel.
 */
enum class RangoFecha {
    TODAS,
    HOY,
    ESTA_SEMANA,
    ESTE_MES
}

/**
 * Estado inmutable de la pantalla de listado de noticias y eventos.
 */
data class NewsListUiState(
    val isLoading: Boolean = false,
    val publicaciones: List<NewsEvent> = emptyList(),
    val errorMessage: String? = null,
    val filtroTipo: NewsType? = null,
    val filtroFecha: RangoFecha = RangoFecha.TODAS
) {
    val isEmpty: Boolean
        get() = !isLoading && errorMessage == null && publicaciones.isEmpty()

    val hayFiltrosActivos: Boolean
        get() = filtroTipo != null || filtroFecha != RangoFecha.TODAS
}