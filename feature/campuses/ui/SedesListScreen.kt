package ar.edu.isvdr.frontend.feature.campuses.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.isvdr.frontend.feature.campuses.data.Sede
import androidx.compose.ui.Alignment
@Preview(showBackground = true)
@Composable
fun SedesListScreenPreview() {
    SedesListScreen()
}
@Composable
fun SedesListScreen(
    viewModel: SedesListViewModel = viewModel(),
    onSedeClick: (String) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = state.query,
            onValueChange = viewModel::onQueryChange,
            label = { Text("Buscar por ciudad o provincia") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        when {
            state.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                Text(state.error!!, color = MaterialTheme.colorScheme.error)
            }
            state.isEmpty -> {
                Text("No se encontraron sedes")
            }
            else -> {
                LazyColumn {
                    items(state.sedes) { sede ->
                        SedeItem(sede = sede, onClick = { onSedeClick(sede.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun SedeItem(sede: Sede, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(sede.nombre, style = MaterialTheme.typography.titleMedium)
            Text("${sede.ciudad}, ${sede.provincia}")
        }
        import androidx.compose.ui.tooling.preview.Preview

                @Preview(showBackground = true)
                @Composable
                fun SedesListScreenPreview() {
                    SedesListScreen()
                }
    }

}