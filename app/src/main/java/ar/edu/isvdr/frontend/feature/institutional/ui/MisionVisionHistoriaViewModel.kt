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
 * ViewModel para la pantalla de Misión, Visión e Historia (I5 - Gabriel, Tarjeta 2).
 */
class MisionVisionHistoriaViewModel(
    private val repository: InstitutionalRepository = InstitutionalRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MisionVisionHistoriaUiState())
    val uiState: StateFlow<MisionVisionHistoriaUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                val misionResult = repository.getMisionVision()
                val valoresResult = repository.getValoresDetallados()
                val hitosResult = repository.getHitosHistoricos()

                val misionVision = misionResult.getOrNull()

                _uiState.update { currentState ->
                    currentState.copy(
                        mision = misionVision?.mision ?: "",
                        vision = misionVision?.vision ?: "",
                        valores = valoresResult.getOrDefault(emptyList()),
                        hitos = hitosResult.getOrDefault(emptyList()),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al cargar la identidad institucional."
                    )
                }
            }
        }
    }

    fun onTabSelected(tab: MisionVisionTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }
}
