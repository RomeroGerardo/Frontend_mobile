package ar.edu.isvdr.frontend.feature.campuses.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ar.edu.isvdr.frontend.feature.campuses.detail.SedeDetailScreen
import ar.edu.isvdr.frontend.feature.campuses.list.SedesListScreen

fun NavGraphBuilder.sedesGraph(navController: NavController) {
    navigation(startDestination = "sedes_list", route = "sedes_graph") {
        composable("sedes_list") {
            SedesListScreen(
                onSedeClick = { sedeId ->
                    navController.navigate("sede_detail/$sedeId")
                }
            )
        }

        composable(
            route = "sede_detail/{sedeId}",
            arguments = listOf(
                navArgument("sedeId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val sedeId = backStackEntry.arguments?.getString("sedeId") ?: ""
            SedeDetailScreen(
                sedeId = sedeId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}