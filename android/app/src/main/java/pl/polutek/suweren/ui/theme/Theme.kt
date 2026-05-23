package pl.polutek.suweren.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = White,
    secondary = Zinc400,
    tertiary = Emerald400,
    background = Black,
    surface = Zinc900,
    onPrimary = Black,
    onSecondary = White,
    onTertiary = Black,
    onBackground = Zinc100,
    onSurface = Zinc100,
)

@Composable
fun SuwerenTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
