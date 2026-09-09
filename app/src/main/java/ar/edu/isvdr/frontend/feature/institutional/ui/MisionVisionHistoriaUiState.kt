package ar.edu.isvdr.frontend.feature.institutional.ui

import ar.edu.isvdr.frontend.feature.institutional.model.HitoHistorico
import ar.edu.isvdr.frontend.feature.institutional.model.ValorDetalle

/**
 * Estado inmutable de la pantalla de Misión, Visión e Historia (I5 - Gabriel, Tarjeta 2).
 */
data class MisionVisionHistoriaUiState(
    val mision: String = "",
    val vision: String = "",
    val valores: List<ValorDetalle> = emptyList(),
    val hitos: List<HitoHistorico> = emptyList(),
    val selectedTab: MisionVisionTab = MisionVisionTab.TODOS,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

enum class MisionVisionTab(val label: String) {
    TODOS("Todo"),
    MISION("Misión"),
    VISION("Visión"),
    VALORES("Valores"),
    HISTORIA("Historia")
}
