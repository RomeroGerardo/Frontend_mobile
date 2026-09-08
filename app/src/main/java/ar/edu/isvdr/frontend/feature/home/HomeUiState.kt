package ar.edu.isvdr.frontend.feature.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val userName: String? = null,
    val highlightedNews: List<String> = emptyList(),
    val popularCareers: List<String> = emptyList()
)
