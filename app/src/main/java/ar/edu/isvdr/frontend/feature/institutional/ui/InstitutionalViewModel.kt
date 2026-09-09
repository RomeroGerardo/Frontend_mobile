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
 * ViewModel del módulo Institucional (I5 - Gabriel).
 * Gestiona el estado de la pantalla y la carga de información.
 */
class InstitutionalViewModel(
    private val repository: InstitutionalRepository = InstitutionalRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(InstitutionalUiState())
    val uiState: StateFlow<InstitutionalUiState> = _uiState.asStateFlow()

    init {
        loadInstitutionalData()
    }

    fun loadInstitutionalData() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                val infoResult = repository.getInstitutionalInfo()
                val misionResult = repository.getMisionVision()
                val fechasResult = repository.getFechasImportantes()
                val contactoResult = repository.getContacto()
                val seccionesResult = repository.getSecciones()

                _uiState.update { currentState ->
                    currentState.copy(
                        info = infoResult.getOrNull(),
                        misionVision = misionResult.getOrNull(),
                        fechas = fechasResult.getOrDefault(emptyList()),
                        contacto = contactoResult.getOrNull(),
                        secciones = seccionesResult.getOrDefault(emptyList()),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al cargar la información institucional."
                    )
                }
            }
        }
    }

    fun onTabSelected(tab: InstitutionalTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }
}
