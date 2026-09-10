package ar.edu.isvdr.frontend.feature.institutional.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.isvdr.frontend.feature.institutional.repository.InstitutionalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Vida Institucional (I5 - Gabriel, Tarjeta 3).
 */
class VidaInstitucionalViewModel(
    private val repository: InstitutionalRepository = InstitutionalRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(VidaInstitucionalUiState())
    val uiState: StateFlow<VidaInstitucionalUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                val result = repository.getActividadesVidaInstitucional()
                val actividades = result.getOrDefault(emptyList())
                val categorias = listOf("Todas") + actividades.map { it.categoria }.distinct()

                _uiState.update { currentState ->
                    currentState.copy(
                        actividades = actividades,
                        categorias = categorias,
                        selectedCategoria = "Todas",
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al cargar las actividades de vida institucional."
                    )
                }
            }
        }
    }

    fun onCategoriaSelected(categoria: String) {
        _uiState.update { it.copy(selectedCategoria = categoria) }
    }
}
