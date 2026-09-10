package ar.edu.isvdr.frontend.feature.gallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.isvdr.frontend.feature.gallery.model.Album
import ar.edu.isvdr.frontend.feature.gallery.repository.AlbumRepository
import ar.edu.isvdr.frontend.feature.gallery.repository.AlbumRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AlbumDetailUiState(
    val isLoading: Boolean = false,
    val album: Album? = null,
    val errorMessage: String? = null
)

class AlbumDetailViewModel @JvmOverloads constructor(
    private val repository: AlbumRepository = AlbumRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlbumDetailUiState(isLoading = true))
    val uiState: StateFlow<AlbumDetailUiState> = _uiState.asStateFlow()

    private var currentAlbumId: String? = null

    fun loadAlbum(albumId: String) {
        currentAlbumId = albumId
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            repository.getAlbumById(albumId)
                .onSuccess { album ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            album = album,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Error al cargar el álbum"
                        )
                    }
                }
        }
    }

    fun retry() {
        currentAlbumId?.let { loadAlbum(it) }
    }
}
