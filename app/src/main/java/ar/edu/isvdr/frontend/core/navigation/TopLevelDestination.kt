package ar.edu.isvdr.frontend.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TopLevelDestination(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : TopLevelDestination("home_route", "Inicio", Icons.Default.Home)
    object Institutional : TopLevelDestination("institutional_route", "Instituto", Icons.Default.AccountBalance)
    object Careers : TopLevelDestination("careers_graph", "Carreras", Icons.Default.School)
    object Sedes : TopLevelDestination("sedes_graph", "Sedes", Icons.Default.LocationOn)
    object News : TopLevelDestination("news_list", "Noticias", Icons.Default.Newspaper)
}