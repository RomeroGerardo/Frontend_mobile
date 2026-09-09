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
 * ViewModel para la pantalla de Fechas Importantes (I5 - Gabriel, Tarjeta 4).
 */
class FechasImportantesViewModel(
    private val repository: InstitutionalRepository = InstitutionalRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(FechasImportantesUiState())
    val uiState: StateFlow<FechasImportantesUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                val result = repository.getFechasImportantes()
                val fechas = result.getOrDefault(emptyList())
                val categorias = listOf("Todas") + fechas.map { it.tipo }.distinct()

                _uiState.update { currentState ->
                    currentState.copy(
                        fechas = fechas,
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
                        errorMessage = e.localizedMessage ?: "Error al cargar las fechas del calendario académico."
                    )
                }
            }
        }
    }

    fun onCategoriaSelected(categoria: String) {
        _uiState.update { it.copy(selectedCategoria = categoria) }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }
}
