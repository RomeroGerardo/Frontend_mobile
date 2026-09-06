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

@Composable
fun CareerListScreen(
    modifier: Modifier = Modifier,
    viewModel: CareerListViewModel = viewModel(),
    onCareerClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CareerListContent(
        uiState = uiState,
        modifier = modifier,
        onCareerClick = onCareerClick,
        onRetry = { viewModel.loadCareers() }
    )
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
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            uiState.errorMessage != null -> {
                ErrorView(
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
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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
}

@Composable
private fun ErrorView(
    message: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error al cargar las carreras:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onErrorContainer
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
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
