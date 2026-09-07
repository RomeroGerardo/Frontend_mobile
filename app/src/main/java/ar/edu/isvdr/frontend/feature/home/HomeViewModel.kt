package ar.edu.isvdr.frontend.feature.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Datos mockeados iniciales para cumplir con el diseño
        _uiState.value = HomeUiState(
            isLoading = false,
            highlightedNews = listOf("Charlas informativas", "Feria de Ciencias"),
            popularCareers = listOf("Desarrollo de Software", "Molinería")
        )
    }
}
