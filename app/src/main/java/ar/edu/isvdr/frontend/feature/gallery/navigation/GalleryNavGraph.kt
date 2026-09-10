package ar.edu.isvdr.frontend.feature.gallery.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ar.edu.isvdr.frontend.feature.gallery.ui.AlbumDetailScreen
import ar.edu.isvdr.frontend.feature.gallery.ui.AlbumListScreen

object GalleryDestinations {
    const val LIST_ROUTE = "gallery_list"
    const val DETAIL_ROUTE = "gallery_detail/{albumId}"

    fun detailRoute(albumId: String) = "gallery_detail/$albumId"
}

fun NavGraphBuilder.galleryGraph(navController: NavController) {
    composable(route = GalleryDestinations.LIST_ROUTE) {
        AlbumListScreen(
            onAlbumClick = { albumId ->
                navController.navigate(GalleryDestinations.detailRoute(albumId))
            },
            onBackClick = { navController.popBackStack() }
        )
    }

    composable(
        route = GalleryDestinations.DETAIL_ROUTE,
        arguments = listOf(navArgument("albumId") { type = NavType.StringType })
    ) { backStackEntry ->
        val albumId = backStackEntry.arguments?.getString("albumId").orEmpty()
        AlbumDetailScreen(
            albumId = albumId,
            onBackClick = { navController.popBackStack() }
        )
    }
}
