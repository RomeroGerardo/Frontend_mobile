package ar.edu.isvdr.frontend.feature.institutional.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ar.edu.isvdr.frontend.feature.institutional.ui.InstitutionalScreen

/**
 * Grafo de navegación del módulo Institucional (I5 - Gabriel).
 * Prepara la navegación interna y conecta la pantalla principal.
 */
fun NavGraphBuilder.institutionalGraph(
    navController: NavController,
    onNavigateToCareers: () -> Unit = {}
) {
    navigation(startDestination = "institutional_main", route = "institutional_route") {
        composable("institutional_main") {
            InstitutionalScreen(
                onNavigateToSection = { ruta ->
                    navController.navigate(ruta)
                },
                onNavigateToCareers = onNavigateToCareers
            )
        }

        composable("institutional_mision_vision") {
            ar.edu.isvdr.frontend.feature.institutional.ui.MisionVisionHistoriaScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("institutional_vida") {
            ar.edu.isvdr.frontend.feature.institutional.ui.VidaInstitucionalScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
