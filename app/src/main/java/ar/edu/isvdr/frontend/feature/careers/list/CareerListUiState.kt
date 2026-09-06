package ar.edu.isvdr.frontend.feature.careers.list

import ar.edu.isvdr.frontend.feature.careers.model.Career

data class CareerListUiState(
    val isLoading: Boolean = false,
    val careers: List<Career> = emptyList(),
    val errorMessage: String? = null
)
