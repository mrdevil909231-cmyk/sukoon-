package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = InstaBlue,
    onPrimary = Color.White,
    primaryContainer = InstaDarkCard,
    onPrimaryContainer = Color.White,
    secondary = InstaPink,
    onSecondary = Color.White,
    tertiary = InstaPurple,
    background = InstaBlack,
    onBackground = InstaDarkTextPrimary,
    surface = InstaBlack,
    onSurface = InstaDarkTextPrimary,
    surfaceVariant = InstaDarkSurface,
    onSurfaceVariant = InstaDarkTextSecondary,
    outline = InstaDarkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = InstaBlue,
    onPrimary = Color.White,
    primaryContainer = InstaLightSurface,
    onPrimaryContainer = InstaLightTextPrimary,
    secondary = InstaPink,
    onSecondary = Color.White,
    tertiary = InstaPurple,
    background = InstaWhite,
    onBackground = InstaLightTextPrimary,
    surface = InstaWhite,
    onSurface = InstaLightTextPrimary,
    surfaceVariant = InstaLightSurface,
    onSurfaceVariant = InstaLightTextSecondary,
    outline = InstaLightBorder
)

@Composable
fun InstagramTheme(
    darkTheme: Boolean = true, // Default to sleek Instagram dark mode as preferred by users
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = !darkTheme
                controller.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    InstagramTheme(darkTheme = darkTheme, content = content)
}
