package ar.edu.isvdr.frontend.feature.institutional.ui

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.isvdr.frontend.core.components.IsvdrErrorState
import ar.edu.isvdr.frontend.core.components.IsvdrLoadingState
import ar.edu.isvdr.frontend.core.components.IsvdrStandardCard
import ar.edu.isvdr.frontend.core.theme.IsvdrTheme
import ar.edu.isvdr.frontend.feature.institutional.model.AreaContacto
import ar.edu.isvdr.frontend.feature.institutional.model.ContactoInfo
import ar.edu.isvdr.frontend.feature.institutional.model.InstitutionalMockData
import ar.edu.isvdr.frontend.feature.institutional.model.PreguntaFrecuente

/**
 * Pantalla de Contacto y Atención (I5 - Gabriel, Tarjeta 5).
 * Permite acceder a teléfono, correo, web y redes del ISVDR.
 */
@Composable
fun ContactoScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContactoViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ContactoTopBar(onNavigateBack = onNavigateBack)
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
                    ContactoContent(uiState = uiState)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContactoTopBar(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "Contacto y Atención",
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
private fun ContactoContent(
    uiState: ContactoUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Encabezado
        item {
            ContactoHeaderCard(contacto = uiState.contacto)
        }

        // Botones de acceso rápido (teléfono, WhatsApp, correo, web)
        if (uiState.contacto != null) {
            item {
                AccesosRapidosSection(contacto = uiState.contacto)
            }
        }

        // Áreas de contacto
        if (uiState.areas.isNotEmpty()) {
            item {
                Text(
                    text = "Áreas de Atención",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            items(uiState.areas) { area ->
                AreaContactoCard(area = area)
            }
        }

        // Preguntas frecuentes
        if (uiState.faqs.isNotEmpty()) {
            item {
                Text(
                    text = "Preguntas Frecuentes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            items(uiState.faqs) { faq ->
                PreguntaFrecuenteItem(faq = faq)
            }
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}

@Composable
private fun ContactoHeaderCard(
    contacto: ContactoInfo?,
    modifier: Modifier = Modifier
) {
    IsvdrStandardCard(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Column {
                Text(
                    text = contacto?.sede ?: "ISVDR",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (contacto != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${contacto.direccion} — ${contacto.localidad}, ${contacto.provincia}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Horario de atención: ${contacto.horarioAtencion}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
private fun AccesosRapidosSection(
    contacto: ContactoInfo,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Acceso Directo",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Teléfono
            AccesoButton(
                label = "Llamar",
                icon = Icons.Default.Phone,
                modifier = Modifier.weight(1f),
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contacto.telefono}"))
                    context.startActivity(intent)
                }
            )

            // WhatsApp
            AccesoButton(
                label = "WhatsApp",
                icon = Icons.Default.Chat,
                modifier = Modifier.weight(1f),
                onClick = {
                    val number = contacto.whatsapp.replace(Regex("[^0-9]"), "")
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$number"))
                    context.startActivity(intent)
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Correo
            AccesoButton(
                label = "Correo",
                icon = Icons.Default.Email,
                modifier = Modifier.weight(1f),
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${contacto.email}"))
                    context.startActivity(intent)
                }
            )

            // Sitio Web
            AccesoButton(
                label = "Sitio Web",
                icon = Icons.Default.Language,
                modifier = Modifier.weight(1f),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(contacto.sitioWeb))
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
private fun AccesoButton(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun AreaContactoCard(
    area: AreaContacto,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    IsvdrStandardCard(modifier = modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = area.nombre,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = area.responsable,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = area.horario,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (area.email.isNotBlank()) {
                TextButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${area.email}"))
                        context.startActivity(intent)
                    },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = area.email,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Composable
private fun PreguntaFrecuenteItem(
    faq: PreguntaFrecuente,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    IsvdrStandardCard(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = faq.pregunta,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Colapsar" else "Expandir",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(6.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = faq.respuesta,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactoScreenPreview() {
    IsvdrTheme {
        ContactoContent(
            uiState = ContactoUiState(
                contacto = InstitutionalMockData.contacto,
                areas = InstitutionalMockData.areasContacto,
                faqs = InstitutionalMockData.preguntasFrecuentes
            )
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ContactoScreenDarkPreview() {
    IsvdrTheme(darkTheme = true) {
        ContactoContent(
            uiState = ContactoUiState(
                contacto = InstitutionalMockData.contacto,
                areas = InstitutionalMockData.areasContacto,
                faqs = InstitutionalMockData.preguntasFrecuentes
            )
        )
    }
}
