package ar.edu.isvdr.frontend.feature.gallery.ui

import ar.edu.isvdr.frontend.feature.gallery.model.Album

data class AlbumListUiState(
    val isLoading: Boolean = false,
    val albums: List<Album> = emptyList(),
    val errorMessage: String? = null
)
