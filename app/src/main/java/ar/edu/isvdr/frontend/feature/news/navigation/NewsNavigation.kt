package ar.edu.isvdr.frontend.feature.news.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ar.edu.isvdr.frontend.feature.news.detail.NEWS_DETAIL_ARG_ID
import ar.edu.isvdr.frontend.feature.news.detail.NewsDetailScreen
import ar.edu.isvdr.frontend.feature.news.list.NewsListScreen

/**
 * Rutas del módulo de noticias y eventos.
 * No modifica el NavHost principal: solo expone constantes y una función
 * [newsGraph] para que Naomi (o quien arme el NavHost de app/) la registre
 * dentro de su propio NavGraphBuilder, ej:
 *
 *   NavHost(navController, startDestination = ...) {
 *       ...
 *       newsGraph(navController)
 *   }
 */
object NewsDestinations {
    const val LIST_ROUTE = "news_list"
    const val DETAIL_ROUTE = "news_detail/{$NEWS_DETAIL_ARG_ID}"

    fun detailRoute(publicacionId: String) = "news_detail/$publicacionId"
}

/**
 * Agrega las pantallas de noticias y eventos a un NavGraphBuilder existente.
 * No crea su propio NavHost: se integra al grafo principal de la app.
 */
fun NavGraphBuilder.newsGraph(navController: NavController) {
    composable(route = NewsDestinations.LIST_ROUTE) {
        NewsListScreen(
            onPublicacionClick = { publicacion ->
                navController.navigate(NewsDestinations.detailRoute(publicacion.id))
            }
        )
    }

    composable(
        route = NewsDestinations.DETAIL_ROUTE,
        arguments = listOf(navArgument(NEWS_DETAIL_ARG_ID) { type = NavType.StringType })
    ) {
        // El id llega solo: NewsDetailViewModel lo lee de SavedStateHandle.
        NewsDetailScreen(
            onBackClick = { navController.popBackStack() }
        )
    }
}