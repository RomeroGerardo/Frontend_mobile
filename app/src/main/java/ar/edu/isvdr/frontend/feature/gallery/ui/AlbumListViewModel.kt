package ar.edu.isvdr.frontend.feature.gallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.isvdr.frontend.feature.gallery.repository.AlbumRepository
import ar.edu.isvdr.frontend.feature.gallery.repository.AlbumRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlbumListViewModel @JvmOverloads constructor(
    private val repository: AlbumRepository = AlbumRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlbumListUiState(isLoading = true))
    val uiState: StateFlow<AlbumListUiState> = _uiState.asStateFlow()

    init {
        loadAlbums()
    }

    fun loadAlbums() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            repository.getAlbums()
                .onSuccess { albums ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            albums = albums.filter { a -> a.activo },
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Error al cargar los álbumes"
                        )
                    }
                }
        }
    }
}
