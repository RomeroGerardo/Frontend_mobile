package ar.edu.isvdr.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ar.edu.isvdr.frontend.core.theme.IsvdrTheme
import ar.edu.isvdr.frontend.feature.splash.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FrontendMobileApp() }
    }
}

@Composable
private fun FrontendMobileApp() {
    IsvdrTheme {
        var showSplash by rememberSaveable { mutableStateOf(true) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Crossfade(
                targetState = showSplash,
                animationSpec = tween(durationMillis = 500),
                label = "SplashCrossfade"
            ) { isSplash ->
                if (isSplash) {
                    SplashScreen(onSplashFinished = { showSplash = false })
                } else {
                    ar.edu.isvdr.frontend.core.navigation.AppNavigation()
                }
            }
        }
    }
}
