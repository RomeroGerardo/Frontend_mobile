package ar.edu.isvdr.frontend.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.unit.dp
import ar.edu.isvdr.frontend.feature.careers.navigation.careersGraph
import ar.edu.isvdr.frontend.feature.campuses.navigation.sedesGraph
import ar.edu.isvdr.frontend.feature.institutional.navigation.institutionalGraph
import ar.edu.isvdr.frontend.feature.news.navigation.newsGraph
import ar.edu.isvdr.frontend.feature.gallery.navigation.galleryGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val items = listOf(
        TopLevelDestination.Home,
        TopLevelDestination.Institutional,
        TopLevelDestination.Careers,
        TopLevelDestination.Sedes,
        TopLevelDestination.News
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            if (screen == TopLevelDestination.Home) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                            } else {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TopLevelDestination.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TopLevelDestination.Home.route) {
                ar.edu.isvdr.frontend.feature.home.HomeScreen(
                    onNavigateToCareers = {
                        navController.navigate(TopLevelDestination.Careers.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToInstitutional = {
                        navController.navigate(TopLevelDestination.Institutional.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToNews = {
                        navController.navigate(TopLevelDestination.News.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToGallery = {
                        navController.navigate("gallery_list")
                    }
                )
            }
            // Grafo institucional
            institutionalGraph(
                navController = navController,
                onNavigateToCareers = {
                    navController.navigate(TopLevelDestination.Careers.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            // Grafo de carreras
            careersGraph(navController)
            // Grafo de sedes
            sedesGraph(navController)
            // Grafo de noticias
            newsGraph(navController)
            // Grafo de galería
            galleryGraph(navController)
        }
    }
}