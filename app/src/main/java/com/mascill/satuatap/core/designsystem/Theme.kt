package com.mascill.satuatap.core.designsystem

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.mascill.satuatap.core.designsystem.tokens.OnSurfaceDark
import com.mascill.satuatap.core.designsystem.tokens.OnSurfaceLight
import com.mascill.satuatap.core.designsystem.tokens.Pink40
import com.mascill.satuatap.core.designsystem.tokens.Pink80
import com.mascill.satuatap.core.designsystem.tokens.SatuAtapAccent
import com.mascill.satuatap.core.designsystem.tokens.SatuAtapBlue
import com.mascill.satuatap.core.designsystem.tokens.SatuAtapLightBlue
import com.mascill.satuatap.core.designsystem.tokens.SurfaceDark
import com.mascill.satuatap.core.designsystem.tokens.SurfaceLight
import com.mascill.satuatap.core.designsystem.tokens.Typography

private val DarkColorScheme = darkColorScheme(
    primary = SatuAtapAccent,
    onPrimary = SurfaceDark,
    secondary = SatuAtapLightBlue,
    onSecondary = SurfaceDark,
    tertiary = Pink80,
    background = SurfaceDark,
    surface = SurfaceDark,
    onBackground = OnSurfaceDark,
    onSurface = OnSurfaceDark,
)

private val LightColorScheme = lightColorScheme(
    primary = SatuAtapBlue,
    onPrimary = SurfaceLight,
    secondary = SatuAtapLightBlue,
    onSecondary = SurfaceLight,
    tertiary = Pink40,
    background = SurfaceLight,
    surface = SurfaceLight,
    onBackground = OnSurfaceLight,
    onSurface = OnSurfaceLight,
)

@Composable
fun SatuAtapTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = if (darkTheme) SurfaceDark.toArgb() else SatuAtapBlue.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}