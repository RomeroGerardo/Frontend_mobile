package ar.edu.isvdr.frontend.feature.institutional.ui

import ar.edu.isvdr.frontend.feature.institutional.model.AreaContacto
import ar.edu.isvdr.frontend.feature.institutional.model.ContactoInfo
import ar.edu.isvdr.frontend.feature.institutional.model.PreguntaFrecuente

/**
 * Estado inmutable para la pantalla de Contacto (I5 - Gabriel, Tarjeta 5).
 */
data class ContactoUiState(
    val contacto: ContactoInfo? = null,
    val areas: List<AreaContacto> = emptyList(),
    val faqs: List<PreguntaFrecuente> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
