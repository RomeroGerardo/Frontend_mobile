package ar.edu.isvdr.frontend.feature.institutional.ui

import ar.edu.isvdr.frontend.feature.institutional.model.FechaImportante

/**
 * Estado inmutable para la pantalla de Fechas Importantes (I5 - Gabriel, Tarjeta 4).
 */
data class FechasImportantesUiState(
    val fechas: List<FechaImportante> = emptyList(),
    val categorias: List<String> = emptyList(),
    val selectedCategoria: String = "Todas",
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
