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
 * ViewModel para la pantalla de Contacto y Atención (I5 - Gabriel, Tarjeta 5).
 */
class ContactoViewModel(
    private val repository: InstitutionalRepository = InstitutionalRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactoUiState())
    val uiState: StateFlow<ContactoUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                val contactoResult = repository.getContacto()
                val areasResult = repository.getAreasContacto()
                val faqsResult = repository.getPreguntasFrecuentes()

                _uiState.update { currentState ->
                    currentState.copy(
                        contacto = contactoResult.getOrNull(),
                        areas = areasResult.getOrDefault(emptyList()),
                        faqs = faqsResult.getOrDefault(emptyList()),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al cargar la información de contacto."
                    )
                }
            }
        }
    }
}
