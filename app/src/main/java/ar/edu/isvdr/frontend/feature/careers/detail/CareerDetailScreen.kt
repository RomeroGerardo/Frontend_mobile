package ar.edu.isvdr.frontend.feature.careers.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.isvdr.frontend.feature.careers.model.Career

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CareerDetailScreen(
    careerId: String,
    modifier: Modifier = Modifier,
    viewModel: CareerDetailViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onPreinscribirseClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(careerId) {
        viewModel.loadCareerDetail(careerId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Carrera") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    ar.edu.isvdr.frontend.core.components.IsvdrLoadingState()
                }
                uiState.errorMessage != null -> {
                    ar.edu.isvdr.frontend.core.components.IsvdrErrorState(
                        message = uiState.errorMessage!!,
                        onRetry = { viewModel.loadCareerDetail(careerId) }
                    )
                }
                uiState.career != null -> {
                    val careerToDraw = uiState.career!!
                    CareerDetailContent(
                        career = careerToDraw,
                        onPreinscribirseClick = { onPreinscribirseClick(careerId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CareerDetailContent(
    career: Career,
    modifier: Modifier = Modifier,
    onPreinscribirseClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = career.nombre,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        career.tituloOtorgado?.let { titulo ->
            DetailItem(label = "Título a otorgar", value = titulo)
        }

        career.duracionAnios?.let { duracion ->
            DetailItem(label = "Duración", value = "$duracion años")
        }

        DetailItem(label = "Modalidad", value = career.modalidad)
        DetailItem(label = "Estado", value = if (career.activa) "Activa" else "Inactiva")

        career.descripcion?.let { desc ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(24.dp))

        ar.edu.isvdr.frontend.core.components.IsvdrPrimaryButton(
            text = "Preinscribirse",
            onClick = onPreinscribirseClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = career.activa
        )
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CareerDetailContentPreview() {
    MaterialTheme {
        CareerDetailContent(
            career = Career(
                id = "1",
                nombre = "Tecnicatura Superior en Desarrollo de Software",
                slug = "software",
                descripcion = "Carrera enfocada en la programación, bases de datos y desarrollo web y móvil.",
                duracionAnios = 3,
                tituloOtorgado = "Técnico Superior",
                modalidad = "VIRTUAL",
                activa = true,
                createdAt = "",
                updatedAt = ""
            ),
            onPreinscribirseClick = {}
        )
    }
}
