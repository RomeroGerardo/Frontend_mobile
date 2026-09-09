package ar.edu.isvdr.frontend.feature.campuses.list

import ar.edu.isvdr.frontend.feature.campuses.model.Sede

data class SedesListUiState(
    val isLoading: Boolean = false,
    val sedes: List<Sede> = emptyList(),
    val query: String = "",
    val error: String? = null
) {
    val isEmpty: Boolean get() = !isLoading && sedes.isEmpty() && error == null
}