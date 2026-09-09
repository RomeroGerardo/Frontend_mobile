package ar.edu.isvdr.frontend.feature.institutional.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.isvdr.frontend.core.components.IsvdrErrorState
import ar.edu.isvdr.frontend.core.components.IsvdrLoadingState
import ar.edu.isvdr.frontend.core.components.IsvdrStandardCard
import ar.edu.isvdr.frontend.core.theme.IsvdrTheme
import ar.edu.isvdr.frontend.feature.institutional.model.ActividadVidaInstitucional
import ar.edu.isvdr.frontend.feature.institutional.model.InstitutionalMockData

/**
 * Pantalla de Vida Institucional (I5 - Gabriel, Tarjeta 3).
 */
@Composable
fun VidaInstitucionalScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: VidaInstitucionalViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            VidaInstitucionalTopBar(onNavigateBack = onNavigateBack)
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
                    VidaInstitucionalContent(
                        uiState = uiState,
                        onCategoriaSelected = viewModel::onCategoriaSelected
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VidaInstitucionalTopBar(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "Vida Institucional",
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
private fun VidaInstitucionalContent(
    uiState: VidaInstitucionalUiState,
    onCategoriaSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredActividades = if (uiState.selectedCategoria == null || uiState.selectedCategoria == "Todas") {
        uiState.actividades
    } else {
        uiState.actividades.filter { it.categoria == uiState.selectedCategoria }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            VidaInstitucionalBanner()
        }

        item {
            VidaCategoriasFilterRow(
                categorias = uiState.categorias,
                selectedCategoria = uiState.selectedCategoria,
                onCategoriaSelected = onCategoriaSelected
            )
        }

        items(filteredActividades) { actividad ->
            VidaActividadCard(actividad = actividad)
        }

        item {
            VidaComunidadCard()
        }
    }
}

@Composable
private fun VidaInstitucionalBanner(modifier: Modifier = Modifier) {
    IsvdrStandardCard(modifier = modifier) {
        Text(
            text = "Comunidad, Formación y Experiencia",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "En el ISVDR la formación trasciende el aula: promovemos la participación estudiantil en pasantías, talleres técnicos, tutorías y proyectos con impacto comunitario.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun VidaCategoriasFilterRow(
    categorias: List<String>,
    selectedCategoria: String?,
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
private fun VidaActividadCard(
    actividad: ActividadVidaInstitucional,
    modifier: Modifier = Modifier
) {
    IsvdrStandardCard(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = actividad.titulo,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            SuggestionChip(
                onClick = {},
                label = {
                    Text(
                        text = actividad.categoria,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = actividad.descripcion,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = actividad.estado,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun VidaComunidadCard(modifier: Modifier = Modifier) {
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
                    text = "Acompañamiento Permanente",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Acercate a Secretaría Estudiantil para conocer las convocatorias vigentes y sumarte a los equipos de trabajo.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VidaInstitucionalScreenPreview() {
    IsvdrTheme {
        VidaInstitucionalContent(
            uiState = VidaInstitucionalUiState(
                actividades = InstitutionalMockData.actividadesVidaInstitucional,
                categorias = listOf("Todas", "Pasantías", "Talleres", "Tutorías", "Extensión", "Biblioteca"),
                selectedCategoria = "Todas"
            ),
            onCategoriaSelected = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun VidaInstitucionalScreenDarkPreview() {
    IsvdrTheme(darkTheme = true) {
        VidaInstitucionalContent(
            uiState = VidaInstitucionalUiState(
                actividades = InstitutionalMockData.actividadesVidaInstitucional,
                categorias = listOf("Todas", "Pasantías", "Talleres", "Tutorías", "Extensión", "Biblioteca"),
                selectedCategoria = "Todas"
            ),
            onCategoriaSelected = {}
        )
    }
}
