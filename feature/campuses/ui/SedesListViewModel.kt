package ar.edu.isvdr.frontend.feature.campuses.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.isvdr.frontend.feature.campuses.data.SedeRepository
import ar.edu.isvdr.frontend.feature.campuses.data.SedeRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SedesListViewModel(
    private val repository: SedeRepository = SedeRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SedesListUiState(isLoading = true))
    val uiState: StateFlow<SedesListUiState> = _uiState.asStateFlow()

    init {
        cargarSedes()
    }

    fun cargarSedes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val lista = repository.getSedes()
                _uiState.update { it.copy(isLoading = false, sedes = lista) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "No se pudieron cargar las sedes")
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
        viewModelScope.launch {
            val resultado = repository.buscarSedes(query)
            _uiState.update { it.copy(sedes = resultado) }
        }
    }
}
