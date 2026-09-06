package ar.edu.isvdr.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FrontendMobileApp() }
    }
}

@Composable
private fun FrontendMobileApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val navController = androidx.navigation.compose.rememberNavController()
            
            androidx.navigation.compose.NavHost(
                navController = navController,
                startDestination = "careers_graph"
            ) {
                ar.edu.isvdr.frontend.feature.careers.navigation.careersGraph(navController)
            }
        }
    }
}

