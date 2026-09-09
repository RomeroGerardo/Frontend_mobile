package ar.edu.isvdr.frontend.feature.news.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.isvdr.frontend.feature.news.data.NewsEventModule
import ar.edu.isvdr.frontend.feature.news.data.NewsEventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Nombre del argumento de navegación usado para llegar al detalle. */
const val NEWS_DETAIL_ARG_ID = "publicacionId"

/**
 * ViewModel de la pantalla de detalle de una publicación.
 * Recibe el id desde SavedStateHandle (tarjeta 6 lo conecta con la navegación).
 */
class NewsDetailViewModel @JvmOverloads constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: NewsEventRepository = NewsEventModule.repository
) : ViewModel() {

    private val publicacionId: String =
        checkNotNull(savedStateHandle[NEWS_DETAIL_ARG_ID]) {
            "Falta el argumento $NEWS_DETAIL_ARG_ID en la navegación al detalle"
        }

    private val _uiState = MutableStateFlow(NewsDetailUiState())
    val uiState: StateFlow<NewsDetailUiState> = _uiState.asStateFlow()

    init {
        cargarPublicacion()
    }

    fun reintentar() = cargarPublicacion()

    private fun cargarPublicacion() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            repository.getPublicacionById(publicacionId)
                .onSuccess { publicacion ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        publicacion = publicacion,
                        errorMessage = null
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "No se pudo cargar la publicación"
                    )
                }
        }
    }
}