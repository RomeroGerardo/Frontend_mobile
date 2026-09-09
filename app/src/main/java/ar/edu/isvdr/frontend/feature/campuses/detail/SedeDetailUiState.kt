package ar.edu.isvdr.frontend.feature.campuses.detail

import ar.edu.isvdr.frontend.feature.campuses.model.Carrera
import ar.edu.isvdr.frontend.feature.campuses.model.Sede

data class SedeDetailUiState(
    val isLoading: Boolean = false,
    val sede: Sede? = null,
    val carreras: List<Carrera> = emptyList(),
    val error: String? = null
) {
    val isEmpty: Boolean get() = !isLoading && sede == null && error == null
}