package ar.edu.isvdr.frontend.feature.careers.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ar.edu.isvdr.frontend.feature.careers.detail.CareerDetailScreen
import ar.edu.isvdr.frontend.feature.careers.list.CareerListScreen

fun NavGraphBuilder.careersGraph(navController: NavController) {
    navigation(startDestination = "careers_list", route = "careers_graph") {
        composable("careers_list") {
            CareerListScreen(
                onCareerClick = { careerId ->
                    navController.navigate("career_detail/$careerId")
                }
            )
        }
        
        composable(
            route = "career_detail/{careerId}",
            arguments = listOf(
                navArgument("careerId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val careerId = backStackEntry.arguments?.getString("careerId") ?: ""
            CareerDetailScreen(
                careerId = careerId,
                onNavigateBack = { navController.popBackStack() },
                onPreinscribirseClick = {
                    // TODO: Aquí va la lógica futura de la preinscripción (por ejemplo, abrir el navegador u otra pantalla)
                    // Por ahora solo lo dejamos conectado.
                }
            )
        }
    }
}
