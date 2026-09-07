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
import androidx.compose.ui.unit.sp
import ar.edu.isvdr.frontend.feature.careers.navigation.careersGraph


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    isDarkTheme: Boolean = false,
    onThemeToggle: () -> Unit = {}
) {
    val navController = rememberNavController()
    
    val items = listOf(
        TopLevelDestination.Home,
        TopLevelDestination.Institutional,
        TopLevelDestination.Careers
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ISVDR") },
                actions = {
                    IconButton(onClick = onThemeToggle) {
                        if (isDarkTheme) {
                            Text("☀️", fontSize = 24.sp)
                        } else {
                            Text("🌙", fontSize = 24.sp)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
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
                            navController.navigate(screen.route) {
                                // Evitar crear múltiples copias del mismo destino
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
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
                        navController.navigate(TopLevelDestination.Careers.route)
                    },
                    onNavigateToInstitutional = {
                        navController.navigate(TopLevelDestination.Institutional.route)
                    }
                )
            }
            composable(TopLevelDestination.Institutional.route) {
                Text("Pantalla Institucional", modifier = Modifier.padding(16.dp))
            }
            // Agregamos el grafo de carreras que hizo Gerardo
            careersGraph(navController)
        }
    }
}
