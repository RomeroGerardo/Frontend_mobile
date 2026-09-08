package ar.edu.isvdr.frontend.feature.careers.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.isvdr.frontend.feature.careers.model.Career

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Clear

@Composable
fun CareerListScreen(
    modifier: Modifier = Modifier,
    viewModel: CareerListViewModel = viewModel(),
    onCareerClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        SearchAndFilterSection(
            searchQuery = uiState.searchQuery,
            selectedModality = uiState.selectedModality,
            onSearchQueryChanged = viewModel::onSearchQueryChanged,
            onModalitySelected = viewModel::onModalityFilterChanged
        )
        
        CareerListContent(
            uiState = uiState,
            modifier = Modifier.weight(1f),
            onCareerClick = onCareerClick,
            onRetry = { viewModel.loadCareers() }
        )
    }
}

@Composable
private fun SearchAndFilterSection(
    searchQuery: String,
    selectedModality: String?,
    onSearchQueryChanged: (String) -> Unit,
    onModalitySelected: (String?) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar carreras...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChanged("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Limpiar búsqueda")
                    }
                }
            },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val modalities = listOf(null, "PRESENCIAL", "VIRTUAL", "HIBRIDA")
            val labels = listOf("Todas", "Presencial", "Virtual", "Híbrida")
            
            modalities.forEachIndexed { index, mod ->
                FilterChip(
                    selected = selectedModality == mod,
                    onClick = { onModalitySelected(mod) },
                    label = { Text(labels[index]) }
                )
            }
        }
    }
}

@Composable
private fun CareerListContent(
    uiState: CareerListUiState,
    modifier: Modifier = Modifier,
    onCareerClick: (String) -> Unit,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> {
                ar.edu.isvdr.frontend.core.components.IsvdrLoadingState()
            }
            uiState.errorMessage != null -> {
                ar.edu.isvdr.frontend.core.components.IsvdrErrorState(
                    message = uiState.errorMessage,
                    onRetry = onRetry
                )
            }
            uiState.careers.isEmpty() -> {
                Text(
                    text = "No hay carreras disponibles.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.careers) { career ->
                        CareerCard(
                            career = career,
                            onClick = { onCareerClick(career.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CareerCard(
    career: Career,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ar.edu.isvdr.frontend.core.components.IsvdrStandardCard(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = career.nombre,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        career.tituloOtorgado?.let { titulo ->
            Text(
                text = "Título: $titulo",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        
        Text(
            text = "Modalidad: ${career.modalidad}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CareerListScreenPreview() {
    MaterialTheme {
        CareerListContent(
            uiState = CareerListUiState(
                isLoading = false,
                careers = listOf(
                    Career(
                        id = "1",
                        nombre = "Tecnicatura Superior en Desarrollo de Software",
                        slug = "software",
                        descripcion = null,
                        duracionAnios = 3,
                        tituloOtorgado = "Técnico Superior",
                        modalidad = "VIRTUAL",
                        activa = true,
                        createdAt = "",
                        updatedAt = ""
                    ),
                    Career(
                        id = "2",
                        nombre = "Tecnicatura Superior en Enfermería",
                        slug = "enfermeria",
                        descripcion = null,
                        duracionAnios = 3,
                        tituloOtorgado = "Enfermero/a",
                        modalidad = "PRESENCIAL",
                        activa = true,
                        createdAt = "",
                        updatedAt = ""
                    )
                )
            ),
            onCareerClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CareerListScreenLoadingPreview() {
    MaterialTheme {
        CareerListContent(
            uiState = CareerListUiState(isLoading = true),
            onCareerClick = {},
            onRetry = {}
        )
    }
}
