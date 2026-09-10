package ar.edu.isvdr.frontend.feature.news.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
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
import ar.edu.isvdr.frontend.feature.news.model.NewsEvent
import ar.edu.isvdr.frontend.feature.news.model.NewsType
import coil.compose.AsyncImage

/**
 * Pantalla principal de listado de noticias y eventos.
 * Composable "tonto": solo lee el estado y delega eventos al ViewModel.
 */
@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsListViewModel = viewModel(),
    onPublicacionClick: (NewsEvent) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Encabezado con estética ISVDR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Newspaper,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Column {
                    Text(
                        text = "Noticias y Eventos",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Actualidad y vida de la comunidad ISVDR",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            FiltrosBar(
                filtroTipo = uiState.filtroTipo,
                filtroFecha = uiState.filtroFecha,
                onTipoSelected = viewModel::setFiltroTipo,
                onFechaSelected = viewModel::setFiltroFecha
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.isLoading -> LoadingState(Modifier.align(Alignment.Center))
                    uiState.errorMessage != null -> ErrorState(
                        message = uiState.errorMessage.orEmpty(),
                        onRetry = viewModel::reintentar,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    uiState.isEmpty -> EmptyState(
                        hayFiltrosActivos = uiState.hayFiltrosActivos,
                        onLimpiarFiltros = viewModel::limpiarFiltros,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    else -> NewsList(
                        publicaciones = uiState.publicaciones,
                        onPublicacionClick = onPublicacionClick
                    )
                }
            }
        }
    }
}

@Composable
private fun FiltrosBar(
    filtroTipo: NewsType?,
    filtroFecha: RangoFecha,
    onTipoSelected: (NewsType?) -> Unit,
    onFechaSelected: (RangoFecha) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 12.dp)) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = filtroTipo == null,
                    onClick = { onTipoSelected(null) },
                    label = { Text("Todas") }
                )
            }
            item {
                FilterChip(
                    selected = filtroTipo == NewsType.NOTICIA,
                    onClick = { onTipoSelected(NewsType.NOTICIA) },
                    label = { Text("Noticias") }
                )
            }
            item {
                FilterChip(
                    selected = filtroTipo == NewsType.EVENTO,
                    onClick = { onTipoSelected(NewsType.EVENTO) },
                    label = { Text("Eventos") }
                )
            }
        }
        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = filtroFecha == RangoFecha.HOY,
                    onClick = { onFechaSelected(RangoFecha.HOY) },
                    label = { Text("Hoy") }
                )
            }
            item {
                FilterChip(
                    selected = filtroFecha == RangoFecha.ESTA_SEMANA,
                    onClick = { onFechaSelected(RangoFecha.ESTA_SEMANA) },
                    label = { Text("Esta semana") }
                )
            }
            item {
                FilterChip(
                    selected = filtroFecha == RangoFecha.ESTE_MES,
                    onClick = { onFechaSelected(RangoFecha.ESTE_MES) },
                    label = { Text("Este mes") }
                )
            }
            if (filtroFecha != RangoFecha.TODAS) {
                item {
                    FilterChip(
                        selected = false,
                        onClick = { onFechaSelected(RangoFecha.TODAS) },
                        label = { Text("Limpiar fecha") }
                    )
                }
            }
        }
    }
}

@Composable
private fun NewsList(
    publicaciones: List<NewsEvent>,
    onPublicacionClick: (NewsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(publicaciones, key = { it.id }) { publicacion ->
            NewsCard(
                publicacion = publicacion,
                onClick = { onPublicacionClick(publicacion) }
            )
        }
    }
}

@Composable
fun NewsCard(
    publicacion: NewsEvent,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            if (publicacion.imagenUrl != null) {
                AsyncImage(
                    model = publicacion.imagenUrl,
                    contentDescription = publicacion.titulo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    TipoChip(tipo = publicacion.tipo)
                    if (publicacion.destacada) {
                        SuggestionChip(
                            onClick = {},
                            label = { Text("Destacada") },
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Text(
                    text = publicacion.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = publicacion.resumen,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun TipoChip(tipo: NewsType, modifier: Modifier = Modifier) {
    val label = if (tipo == NewsType.EVENTO) "Evento" else "Noticia"
    SuggestionChip(
        onClick = {},
        label = { Text(label) },
        colors = SuggestionChipDefaults.suggestionChipColors(),
        modifier = modifier
    )
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier)
}

@Composable
private fun EmptyState(
    hayFiltrosActivos: Boolean,
    onLimpiarFiltros: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val mensaje = if (hayFiltrosActivos) {
            "No hay publicaciones que coincidan con estos filtros."
        } else {
            "Todavía no hay noticias ni eventos publicados."
        }
        Text(text = mensaje, style = MaterialTheme.typography.bodyLarge)
        if (hayFiltrosActivos) {
            Button(onClick = onLimpiarFiltros) {
                Text("Limpiar filtros")
            }
        }
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsCardPreview() {
    MaterialTheme {
        NewsCard(
            publicacion = NewsEvent(
                id = "1",
                titulo = "Jornada de Inteligencia Artificial 2026",
                slug = "jornada-de-inteligencia-artificial-2026",
                resumen = "Una jornada abierta para estudiantes.",
                contenido = "Contenido completo de la noticia...",
                tipo = NewsType.EVENTO,
                imagenUrl = null,
                fechaEvento = "2026-10-20T18:00:00.000Z",
                destacada = true,
                autorId = "u1",
                autor = null,
                createdAt = "2026-09-09T14:31:47.918Z",
                updatedAt = "2026-09-09T14:31:47.918Z"
            ),
            onClick = {}
        )
    }
}