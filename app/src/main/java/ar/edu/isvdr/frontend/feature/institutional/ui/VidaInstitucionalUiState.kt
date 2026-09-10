package ar.edu.isvdr.frontend.feature.institutional.ui

import ar.edu.isvdr.frontend.feature.institutional.model.ActividadVidaInstitucional

/**
 * Estado inmutable para la pantalla de Vida Institucional (I5 - Gabriel, Tarjeta 3).
 */
data class VidaInstitucionalUiState(
    val actividades: List<ActividadVidaInstitucional> = emptyList(),
    val categorias: List<String> = emptyList(),
    val selectedCategoria: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
