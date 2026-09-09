package ar.edu.isvdr.frontend.feature.institutional.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.isvdr.frontend.core.components.IsvdrErrorState
import ar.edu.isvdr.frontend.core.components.IsvdrLoadingState
import ar.edu.isvdr.frontend.core.components.IsvdrStandardCard
import ar.edu.isvdr.frontend.core.theme.IsvdrTheme
import ar.edu.isvdr.frontend.feature.institutional.model.FechaImportante
import ar.edu.isvdr.frontend.feature.institutional.model.InstitutionalMockData

/**
 * Pantalla de Fechas Importantes y Calendario Académico (I5 - Gabriel, Tarjeta 4).
 */
@Composable
fun FechasImportantesScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FechasImportantesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            FechasImportantesTopBar(onNavigateBack = onNavigateBack)
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    IsvdrLoadingState()
                }
                uiState.errorMessage != null -> {
                    IsvdrErrorState(
                        message = uiState.errorMessage ?: "Error desconocido",
                        onRetry = { viewModel.loadData() }
                    )
                }
                else -> {
                    FechasImportantesContent(
                        uiState = uiState,
                        onCategoriaSelected = viewModel::onCategoriaSelected,
                        onSearchQueryChanged = viewModel::onSearchQueryChanged
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FechasImportantesTopBar(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "Calendario Académico",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver"
                )
            }
        },
        modifier = modifier
    )
}

@Composable
private fun FechasImportantesContent(
    uiState: FechasImportantesUiState,
    onCategoriaSelected: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredFechas = uiState.fechas.filter { fecha ->
        val matchesCategory = uiState.selectedCategoria == "Todas" || fecha.tipo == uiState.selectedCategoria
        val matchesQuery = uiState.searchQuery.isBlank() ||
                fecha.titulo.contains(uiState.searchQuery, ignoreCase = true) ||
                fecha.descripcion.contains(uiState.searchQuery, ignoreCase = true)
        matchesCategory && matchesQuery
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            FechasAcademicasBanner()
        }

        item {
            FechasSearchBar(
                query = uiState.searchQuery,
                onQueryChanged = onSearchQueryChanged
            )
        }

        item {
            FechasCategoriasRow(
                categorias = uiState.categorias,
                selectedCategoria = uiState.selectedCategoria,
                onCategoriaSelected = onCategoriaSelected
            )
        }

        if (filteredFechas.isEmpty()) {
            item {
                FechaEmptyState()
            }
        } else {
            items(filteredFechas) { fecha ->
                FechaDetalleCard(fecha = fecha)
            }
        }

        item {
            FechaAvisoCard()
        }
    }
}

@Composable
private fun FechasAcademicasBanner(modifier: Modifier = Modifier) {
    IsvdrStandardCard(modifier = modifier) {
        Text(
            text = "Ciclo Lectivo 2026",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Consultá los períodos de inscripción, turnos de exámenes finales y fechas clave para la planificación de tu cursado en el ISVDR.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun FechasSearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text("Buscar fecha o evento...") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChanged("") }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpiar")
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun FechasCategoriasRow(
    categorias: List<String>,
    selectedCategoria: String,
    onCategoriaSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categorias) { categoria ->
            FilterChip(
                selected = selectedCategoria == categoria,
                onClick = { onCategoriaSelected(categoria) },
                label = { Text(categoria) }
            )
        }
    }
}

@Composable
private fun FechaDetalleCard(
    fecha: FechaImportante,
    modifier: Modifier = Modifier
) {
    IsvdrStandardCard(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = fecha.titulo,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    SuggestionChip(
                        onClick = {},
                        label = {
                            Text(fecha.tipo, style = MaterialTheme.typography.labelSmall)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = fecha.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Período: ${fecha.fecha}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun FechaEmptyState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No se encontraron fechas para el filtro seleccionado.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun FechaAvisoCard(modifier: Modifier = Modifier) {
    IsvdrStandardCard(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(
                    text = "Aviso Administrativo",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Las inscripciones a mesas de examen cierran 48 horas hábiles antes de cada fecha de evaluación.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FechasImportantesScreenPreview() {
    IsvdrTheme {
        FechasImportantesContent(
            uiState = FechasImportantesUiState(
                fechas = InstitutionalMockData.fechasImportantes,
                categorias = listOf("Todas", "Inscripción", "Exámenes", "Académico", "Ingreso"),
                selectedCategoria = "Todas"
            ),
            onCategoriaSelected = {},
            onSearchQueryChanged = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FechasImportantesScreenDarkPreview() {
    IsvdrTheme(darkTheme = true) {
        FechasImportantesContent(
            uiState = FechasImportantesUiState(
                fechas = InstitutionalMockData.fechasImportantes,
                categorias = listOf("Todas", "Inscripción", "Exámenes", "Académico", "Ingreso"),
                selectedCategoria = "Todas"
            ),
            onCategoriaSelected = {},
            onSearchQueryChanged = {}
        )
    }
}
