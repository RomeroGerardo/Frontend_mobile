package ar.edu.isvdr.frontend.feature.institutional.ui

import ar.edu.isvdr.frontend.feature.institutional.model.ContactoInfo
import ar.edu.isvdr.frontend.feature.institutional.model.FechaImportante
import ar.edu.isvdr.frontend.feature.institutional.model.InstitutionalInfo
import ar.edu.isvdr.frontend.feature.institutional.model.MisionVision
import ar.edu.isvdr.frontend.feature.institutional.model.SeccionInstitucional

/**
 * Estado inmutable de la pantalla institucional (I5 - Gabriel).
 * Sigue la regla del patrón MVVM / Clean Architecture simplificado.
 */
data class InstitutionalUiState(
    val info: InstitutionalInfo? = null,
    val misionVision: MisionVision? = null,
    val fechas: List<FechaImportante> = emptyList(),
    val contacto: ContactoInfo? = null,
    val secciones: List<SeccionInstitucional> = emptyList(),
    val selectedTab: InstitutionalTab = InstitutionalTab.GENERAL,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

enum class InstitutionalTab(val label: String) {
    GENERAL("General"),
    MISION("Misión y Visión"),
    FECHAS("Fechas"),
    CONTACTO("Contacto")
}
