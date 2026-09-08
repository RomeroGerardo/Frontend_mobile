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

class SedeDetailViewModel(
    private val repository: SedeRepository = SedeRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SedeDetailUiState())
    val uiState: StateFlow<SedeDetailUiState> = _uiState.asStateFlow()

    fun cargarSede(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val sede = repository.getSedeById(id)
                if (sede != null) {
                    _uiState.update { it.copy(isLoading = false, sede = sede) }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Sede no encontrada") }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "No se pudo cargar la sede")
                }
            }
        }
    }
}
