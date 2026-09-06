package ar.edu.isvdr.frontend.feature.careers.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.isvdr.frontend.feature.careers.repository.CareerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CareerDetailViewModel(
    private val repository: CareerRepository = CareerRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CareerDetailUiState())
    val uiState: StateFlow<CareerDetailUiState> = _uiState.asStateFlow()

    fun loadCareerDetail(careerId: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        
        viewModelScope.launch {
            val result = repository.getCareerById(careerId)
            
            if (result.isSuccess) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        career = result.getOrNull()
                    ) 
                }
            } else {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message ?: "Error al obtener detalles"
                    ) 
                }
            }
        }
    }
}
