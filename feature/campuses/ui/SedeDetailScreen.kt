package ar.edu.isvdr.frontend.feature.campuses.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.isvdr.frontend.feature.campuses.data.Sede

@Composable
fun SedeDetailScreen(
    sedeId: String,
    viewModel: SedeDetailViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(sedeId) {
        viewModel.cargarSede(sedeId)
    }

    Box(Modifier.fillMaxSize().padding(16.dp)) {
        when {
            state.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                Text(state.error!!, color = MaterialTheme.colorScheme.error)
            }
            state.sede != null -> {
                SedeDetailContent(sede = state.sede!!)
            }
        }
    }
}

@Composable
fun SedeDetailContent(sede: Sede) {
    Column(Modifier.fillMaxSize()) {
        Text(sede.nombre, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text("${sede.ciudad}, ${sede.provincia}", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(16.dp))
        SedeInfoRow(label = "Dirección", value = sede.direccion)
        SedeInfoRow(label = "Teléfono", value = sede.telefono)
        SedeInfoRow(label = "Horarios", value = sede.horarios)
    }
}

@Composable
fun SedeInfoRow(label: String, value: String) {
    Column(Modifier.padding(vertical = 6.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun SedeDetailScreenPreview() {
    SedeDetailContent(
        sede = Sede(
            id = "1",
            nombre = "Sede Central",
            ciudad = "Córdoba",
            provincia = "Córdoba",
            direccion = "Av. Colón 123",
            telefono = "351-4000000",
            horarios = "8 a 20 hs",
            carrerasIds = listOf("c1", "c2")
        )
    )
}
