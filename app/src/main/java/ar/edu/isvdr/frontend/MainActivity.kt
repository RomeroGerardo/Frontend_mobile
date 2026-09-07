package ar.edu.isvdr.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ar.edu.isvdr.frontend.core.theme.IsvdrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FrontendMobileApp() }
    }
}

@Composable
private fun FrontendMobileApp() {
    // Tomamos el tema del celular por defecto, pero permitimos cambiarlo
    val systemDark = isSystemInDarkTheme()
    var isDarkTheme by remember { mutableStateOf(systemDark) }

    IsvdrTheme(darkTheme = isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ar.edu.isvdr.frontend.core.navigation.AppNavigation(
                isDarkTheme = isDarkTheme,
                onThemeToggle = { isDarkTheme = !isDarkTheme }
            )
        }
    }
}
