package ar.edu.isvdr.frontend.feature.news.detail

import ar.edu.isvdr.frontend.feature.news.model.NewsEvent

/**
 * Estado inmutable de la pantalla de detalle de una publicación.
 */
data class NewsDetailUiState(
    val isLoading: Boolean = false,
    val publicacion: NewsEvent? = null,
    val errorMessage: String? = null
)