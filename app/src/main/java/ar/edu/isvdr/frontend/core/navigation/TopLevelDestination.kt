package ar.edu.isvdr.frontend.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TopLevelDestination(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : TopLevelDestination("home_route", "Inicio", Icons.Default.Home)
    object Institutional : TopLevelDestination("institutional_route", "Instituto", Icons.Default.Info)
    object Careers : TopLevelDestination("careers_graph", "Carreras", Icons.Default.List)
}
