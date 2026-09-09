package ar.edu.isvdr.frontend.feature.institutional.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
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
import ar.edu.isvdr.frontend.feature.institutional.model.HitoHistorico
import ar.edu.isvdr.frontend.feature.institutional.model.InstitutionalMockData
import ar.edu.isvdr.frontend.feature.institutional.model.ValorDetalle

/**
 * Pantalla de Misión, Visión e Historia (I5 - Gabriel, Tarjeta 2).
 */
@Composable
fun MisionVisionHistoriaScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MisionVisionHistoriaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            MisionVisionTopBar(onNavigateBack = onNavigateBack)
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
                    MisionVisionHistoriaContent(
                        uiState = uiState,
                        onTabSelected = viewModel::onTabSelected
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MisionVisionTopBar(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "Misión, Visión e Historia",
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
private fun MisionVisionHistoriaContent(
    uiState: MisionVisionHistoriaUiState,
    onTabSelected: (MisionVisionTab) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            MisionVisionTabsRow(
                selectedTab = uiState.selectedTab,
                onTabSelected = onTabSelected
            )
        }

        when (uiState.selectedTab) {
            MisionVisionTab.TODOS -> {
                item { MisionCard(mision = uiState.mision) }
                item { VisionCard(vision = uiState.vision) }
                item { ValoresSection(valores = uiState.valores) }
                item { HitosHistoriaSection(hitos = uiState.hitos) }
            }
            MisionVisionTab.MISION -> {
                item { MisionCard(mision = uiState.mision) }
            }
            MisionVisionTab.VISION -> {
                item { VisionCard(vision = uiState.vision) }
            }
            MisionVisionTab.VALORES -> {
                item { ValoresSection(valores = uiState.valores) }
            }
            MisionVisionTab.HISTORIA -> {
                item { HitosHistoriaSection(hitos = uiState.hitos) }
            }
        }
    }
}

@Composable
private fun MisionVisionTabsRow(
    selectedTab: MisionVisionTab,
    onTabSelected: (MisionVisionTab) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(MisionVisionTab.entries) { tab ->
            FilterChip(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                label = { Text(tab.label) }
            )
        }
    }
}

@Composable
private fun MisionCard(
    mision: String,
    modifier: Modifier = Modifier
) {
    IsvdrStandardCard(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Nuestra Misión",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = mision,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun VisionCard(
    vision: String,
    modifier: Modifier = Modifier
) {
    IsvdrStandardCard(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Nuestra Visión",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = vision,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ValoresSection(
    valores: List<ValorDetalle>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Valores Institucionales",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        valores.forEach { valor ->
            ValorCard(valor = valor)
        }
    }
}

@Composable
private fun ValorCard(
    valor: ValorDetalle,
    modifier: Modifier = Modifier
) {
    IsvdrStandardCard(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(20.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = valor.titulo,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = valor.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun HitosHistoriaSection(
    hitos: List<HitoHistorico>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Trayectoria e Hitos Históricos",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        hitos.forEach { hito ->
            HitoCard(hito = hito)
        }
    }
}

@Composable
private fun HitoCard(
    hito: HitoHistorico,
    modifier: Modifier = Modifier
) {
    IsvdrStandardCard(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = hito.titulo,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                SuggestionChip(
                    onClick = {},
                    label = { Text(hito.periodo, style = MaterialTheme.typography.labelSmall) }
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = hito.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MisionVisionHistoriaScreenPreview() {
    IsvdrTheme {
        MisionVisionHistoriaContent(
            uiState = MisionVisionHistoriaUiState(
                mision = InstitutionalMockData.misionVision.mision,
                vision = InstitutionalMockData.misionVision.vision,
                valores = InstitutionalMockData.valoresDetallados,
                hitos = InstitutionalMockData.hitosHistoricos
            ),
            onTabSelected = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MisionVisionHistoriaScreenDarkPreview() {
    IsvdrTheme(darkTheme = true) {
        MisionVisionHistoriaContent(
            uiState = MisionVisionHistoriaUiState(
                mision = InstitutionalMockData.misionVision.mision,
                vision = InstitutionalMockData.misionVision.vision,
                valores = InstitutionalMockData.valoresDetallados,
                hitos = InstitutionalMockData.hitosHistoricos
            ),
            onTabSelected = {}
        )
    }
}
