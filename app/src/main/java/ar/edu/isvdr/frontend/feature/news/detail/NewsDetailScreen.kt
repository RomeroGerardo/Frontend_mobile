package ar.edu.isvdr.frontend.feature.news.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.isvdr.frontend.feature.news.model.Author
import ar.edu.isvdr.frontend.feature.news.model.NewsEvent
import ar.edu.isvdr.frontend.feature.news.model.NewsType
import coil.compose.AsyncImage

/**
 * Pantalla de detalle de una publicación (noticia o evento).
 * Composable "tonto": solo lee el estado y delega eventos al ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsDetailViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(uiState.publicacion?.titulo.orEmpty()) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                uiState.errorMessage != null -> ErrorState(
                    message = uiState.errorMessage.orEmpty(),
                    onRetry = viewModel::reintentar,
                    modifier = Modifier.align(Alignment.Center)
                )
                uiState.publicacion != null -> PublicacionDetalle(uiState.publicacion!!)
            }
        }
    }
}

@Composable
private fun PublicacionDetalle(publicacion: NewsEvent, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
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
            EtiquetasFila(publicacion)

            Text(
                text = publicacion.titulo,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = publicacion.resumen,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )

            MetaInfo(publicacion, modifier = Modifier.padding(top = 12.dp))

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = publicacion.contenido,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun EtiquetasFila(publicacion: NewsEvent, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        val tipoLabel = if (publicacion.tipo == NewsType.EVENTO) "Evento" else "Noticia"
        SuggestionChip(onClick = {}, label = { Text(tipoLabel) })
    }
}

@Composable
private fun MetaInfo(publicacion: NewsEvent, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (publicacion.tipo == NewsType.EVENTO && publicacion.fechaEvento != null) {
            Text(
                text = "Fecha del evento: ${publicacion.fechaEvento}",
                style = MaterialTheme.typography.labelLarge
            )
        } else {
            Text(
                text = "Publicado: ${publicacion.createdAt}",
                style = MaterialTheme.typography.labelLarge
            )
        }
        publicacion.autor?.let { autor ->
            Text(
                text = "Por ${autor.nombreCompleto}",
                style = MaterialTheme.typography.labelLarge
            )
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PublicacionDetallePreview() {
    MaterialTheme {
        PublicacionDetalle(
            publicacion = NewsEvent(
                id = "1",
                titulo = "Jornada de Inteligencia Artificial 2026",
                slug = "jornada-de-inteligencia-artificial-2026",
                resumen = "Una jornada abierta para estudiantes.",
                contenido = "Contenido completo de la noticia. Lorem ipsum dolor sit amet, " +
                    "consectetur adipiscing elit. Detalle extendido de la publicación.",
                tipo = NewsType.EVENTO,
                imagenUrl = null,
                fechaEvento = "2026-10-20T18:00:00.000Z",
                destacada = true,
                autorId = "u1",
                autor = Author(id = "u1", nombre = "Instituto", apellido = "ISVDR"),
                createdAt = "2026-09-09T14:31:47.918Z",
                updatedAt = "2026-09-09T14:31:47.918Z"
            )
        )
    }
}