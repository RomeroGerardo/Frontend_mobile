package ar.edu.isvdr.frontend.feature.careers.detail

import ar.edu.isvdr.frontend.feature.careers.model.Career

data class CareerDetailUiState(
    val isLoading: Boolean = false,
    val career: Career? = null,
    val errorMessage: String? = null,
    val isSubmittingPreinscripcion: Boolean = false,
    val preinscripcionSuccess: Boolean = false,
    val preinscripcionError: String? = null
)
