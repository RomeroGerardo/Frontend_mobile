package ar.edu.isvdr.frontend.feature.careers.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.isvdr.frontend.feature.careers.repository.CareerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CareerListViewModel(
    private val repository: CareerRepository = CareerRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CareerListUiState())
    val uiState: StateFlow<CareerListUiState> = _uiState.asStateFlow()

    init {
        loadCareers()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        loadCareers()
    }

    fun onModalityFilterChanged(modality: String?) {
        _uiState.update { it.copy(selectedModality = modality) }
        loadCareers()
    }

    fun loadCareers() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        
        viewModelScope.launch {
            val currentState = _uiState.value
            val result = repository.getCareers(
                buscar = currentState.searchQuery.takeIf { it.isNotBlank() },
                modalidad = currentState.selectedModality
            )
            
            if (result.isSuccess) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        careers = result.getOrDefault(emptyList())
                    ) 
                }
            } else {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = result.exceptionOrNull()?.message ?: "Error desconocido"
                    ) 
                }
            }
        }
    }
}
