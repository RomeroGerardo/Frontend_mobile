package ar.edu.isvdr.frontend.core.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color

// Tema Claro (Por defecto según los mockups)
private val LightColorScheme = lightColorScheme(
    primary = PrimaryYellow,
    onPrimary = OnPrimaryYellow,
    
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    
    background = BackgroundLight,
    onBackground = SecondaryDark,
    
    surface = SurfaceWhite,
    onSurface = SecondaryDark,
    
    error = ErrorRed
)

// Tema Oscuro (Se puede ajustar más adelante si deciden tener "Dark Mode")
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryYellow,
    onPrimary = OnPrimaryYellow,
    
    secondary = Color(0xFF343A40),
    onSecondary = Color.White,
    
    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),
    
    error = Color(0xFFCF6679)
)

@Composable
fun IsvdrTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Pintamos la barra de estado de arriba con el gris oscuro del diseño
            window.statusBarColor = SecondaryDark.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
