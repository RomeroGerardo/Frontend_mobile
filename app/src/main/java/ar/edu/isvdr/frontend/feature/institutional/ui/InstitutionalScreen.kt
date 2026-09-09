package ar.edu.isvdr.frontend.feature.institutional.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
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
import ar.edu.isvdr.frontend.core.components.IsvdrPrimaryButton
import ar.edu.isvdr.frontend.core.components.IsvdrSecondaryButton
import ar.edu.isvdr.frontend.core.components.IsvdrStandardCard
import ar.edu.isvdr.frontend.core.theme.IsvdrTheme
import ar.edu.isvdr.frontend.feature.institutional.model.ContactoInfo
import ar.edu.isvdr.frontend.feature.institutional.model.FechaImportante
import ar.edu.isvdr.frontend.feature.institutional.model.InstitutionalInfo
import ar.edu.isvdr.frontend.feature.institutional.model.InstitutionalMockData
import ar.edu.isvdr.frontend.feature.institutional.model.MisionVision
import ar.edu.isvdr.frontend.feature.institutional.model.SeccionInstitucional

/**
 * Pantalla principal del módulo Institucional (I5 - Gabriel).
 * Renderiza la información institucional y prepara la navegación interna.
 */
@Composable
fun InstitutionalScreen(
    modifier: Modifier = Modifier,
    viewModel: InstitutionalViewModel = viewModel(),
    onNavigateToSection: (String) -> Unit = {},
    onNavigateToCareers: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                IsvdrLoadingState()
            }
            uiState.errorMessage != null -> {
                IsvdrErrorState(
                    message = uiState.errorMessage ?: "Error desconocido",
                    onRetry = { viewModel.loadInstitutionalData() }
                )
            }
            else -> {
                InstitutionalContent(
                    uiState = uiState,
                    onTabSelected = viewModel::onTabSelected,
                    onNavigateToSection = onNavigateToSection,
                    onNavigateToCareers = onNavigateToCareers
                )
            }
        }
    }
}

@Composable
private fun InstitutionalContent(
    uiState: InstitutionalUiState,
    onTabSelected: (InstitutionalTab) -> Unit,
    onNavigateToSection: (String) -> Unit,
    onNavigateToCareers: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            InstitutionalHeroHeader(info = uiState.info)
        }

        item {
            InstitutionalTabsRow(
                selectedTab = uiState.selectedTab,
                onTabSelected = onTabSelected
            )
        }

        when (uiState.selectedTab) {
            InstitutionalTab.GENERAL -> {
                item {
                    InstitutionalWelcomeCard(info = uiState.info)
                }
                item {
                    InstitutionalSectionsNav(
                        secciones = uiState.secciones,
                        onNavigateToSection = onNavigateToSection
                    )
                }
                item {
                    InstitutionalFechasPreview(
                        fechas = uiState.fechas.take(2),
                        onVerMas = { onTabSelected(InstitutionalTab.FECHAS) }
                    )
                }
                item {
                    InstitutionalContactPreview(
                        contacto = uiState.contacto,
                        onVerMas = { onTabSelected(InstitutionalTab.CONTACTO) }
                    )
                }
                item {
                    InstitutionalActionButtons(
                        onNavigateToCareers = onNavigateToCareers,
                        onNavigateToContact = { onTabSelected(InstitutionalTab.CONTACTO) }
                    )
                }
            }
            InstitutionalTab.MISION -> {
                item {
                    InstitutionalMisionVisionSection(
                        misionVision = uiState.misionVision,
                        info = uiState.info
                    )
                }
            }
            InstitutionalTab.FECHAS -> {
                item {
                    InstitutionalFechasSection(fechas = uiState.fechas)
                }
            }
            InstitutionalTab.CONTACTO -> {
                item {
                    InstitutionalContactoSection(contacto = uiState.contacto)
                }
            }
        }
    }
}

@Composable
private fun InstitutionalHeroHeader(
    info: InstitutionalInfo?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = info?.sigla ?: "ISVDR",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Column {
                Text(
                    text = info?.nombre ?: "Instituto Superior Villa del Rosario",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = info?.lema ?: "Formación profesional superior",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun InstitutionalTabsRow(
    selectedTab: InstitutionalTab,
    onTabSelected: (InstitutionalTab) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(InstitutionalTab.entries) { tab ->
            FilterChip(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                label = { Text(tab.label) }
            )
        }
    }
}

@Composable
private fun InstitutionalWelcomeCard(
    info: InstitutionalInfo?,
    modifier: Modifier = Modifier
) {
    IsvdrStandardCard(modifier = modifier) {
        Text(
            text = "Nuestro Instituto",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = info?.descripcion ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun InstitutionalSectionsNav(
    secciones: List<SeccionInstitucional>,
    onNavigateToSection: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Secciones del Módulo",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        secciones.forEach { seccion ->
            InstitutionalSectionCard(
                seccion = seccion,
                onClick = { onNavigateToSection(seccion.ruta) }
            )
        }
    }
}

@Composable
private fun InstitutionalSectionCard(
    seccion: SeccionInstitucional,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IsvdrStandardCard(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = seccion.titulo,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = seccion.subtitulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun InstitutionalFechasPreview(
    fechas: List<FechaImportante>,
    onVerMas: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Próximas Fechas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(onClick = onVerMas) {
                Text("Ver todas", style = MaterialTheme.typography.bodySmall)
            }
        }
        fechas.forEach { fecha ->
            InstitutionalFechaCard(fecha = fecha)
        }
    }
}

@Composable
private fun InstitutionalFechaCard(
    fecha: FechaImportante,
    modifier: Modifier = Modifier
) {
    IsvdrStandardCard(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = fecha.titulo,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${fecha.fecha} • ${fecha.tipo}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun InstitutionalContactPreview(
    contacto: ContactoInfo?,
    onVerMas: () -> Unit,
    modifier: Modifier = Modifier
) {
    IsvdrStandardCard(modifier = modifier) {
        Text(
            text = "Atención y Contacto",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        contacto?.let {
            Text(
                text = "${it.sede} • ${it.localidad}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tel: ${it.telefono}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Horarios: ${it.horarioAtencion}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = onVerMas,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Más información de contacto")
        }
    }
}

@Composable
private fun InstitutionalActionButtons(
    onNavigateToCareers: () -> Unit,
    onNavigateToContact: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IsvdrPrimaryButton(
            text = "Conocé nuestras carreras",
            onClick = onNavigateToCareers
        )
        IsvdrSecondaryButton(
            text = "Canales de contacto",
            onClick = onNavigateToContact
        )
    }
}

@Composable
private fun InstitutionalMisionVisionSection(
    misionVision: MisionVision?,
    info: InstitutionalInfo?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IsvdrStandardCard {
            Text(
                text = "Misión",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = misionVision?.mision ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IsvdrStandardCard {
            Text(
                text = "Visión",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = misionVision?.vision ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IsvdrStandardCard {
            Text(
                text = "Nuestros Valores",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            misionVision?.valores?.forEach { valor ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = valor,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        IsvdrStandardCard {
            Text(
                text = "Reseña Histórica",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = info?.resena ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun InstitutionalFechasSection(
    fechas: List<FechaImportante>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Calendario Académico y Fechas Clave",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        fechas.forEach { fecha ->
            IsvdrStandardCard {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = fecha.titulo,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = fecha.descripcion,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Fecha: ${fecha.fecha} • Tipo: ${fecha.tipo}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InstitutionalContactoSection(
    contacto: ContactoInfo?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Canales de Contacto",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        contacto?.let {
            IsvdrStandardCard {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = it.sede,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${it.direccion}, ${it.localidad}, ${it.provincia}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            IsvdrStandardCard {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = "Teléfono de Informes",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = it.telefono,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "WhatsApp: ${it.whatsapp}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            IsvdrStandardCard {
                Text(
                    text = "Horarios y Canales Digitales",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Atención: ${it.horarioAtencion}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Correo: ${it.email}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Web: ${it.sitioWeb}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InstitutionalScreenPreview() {
    IsvdrTheme {
        InstitutionalContent(
            uiState = InstitutionalUiState(
                info = InstitutionalMockData.infoGeneral,
                misionVision = InstitutionalMockData.misionVision,
                fechas = InstitutionalMockData.fechasImportantes,
                contacto = InstitutionalMockData.contacto,
                secciones = InstitutionalMockData.secciones
            ),
            onTabSelected = {},
            onNavigateToSection = {},
            onNavigateToCareers = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun InstitutionalScreenDarkPreview() {
    IsvdrTheme(darkTheme = true) {
        InstitutionalContent(
            uiState = InstitutionalUiState(
                info = InstitutionalMockData.infoGeneral,
                misionVision = InstitutionalMockData.misionVision,
                fechas = InstitutionalMockData.fechasImportantes,
                contacto = InstitutionalMockData.contacto,
                secciones = InstitutionalMockData.secciones
            ),
            onTabSelected = {},
            onNavigateToSection = {},
            onNavigateToCareers = {}
        )
    }
}
