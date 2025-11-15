package com.example.tccmobile.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = CianoAzul,
    secondary = AzulSuperClaro,
    tertiary = Branco,
    quartenary = BlueEscuro,
    quinary = AzulLetra,
    senary = AzulClaro,
    sextiary = Cinza,
    septiary = VermelhoTelha,
    octiary = AmareloClaro1,
    nonary = AmareloClaro2,
    denary = Laranja,
    undary = Azul2,
    doenary = Cinza2,
    treenary = Cinza3,
    fourtenary = AzulNoite,
    fiftenary = CinzaAzul,
    sixenary = Black,
    sevenenary = AmareloEstrela,
    eightenary = Rosinha,
    nineenary = VerdeEscuro,
    tenenary = Vermelho,
    elevenenary = LaranjaMedalha,
    twelveenary = Purple40,
    thirteenenary = PurpleGrey40,
    fourteenenary = Pink40,
)

private val LightColorScheme = lightColorScheme(
    primary = BlueEscuro,
    secondary = AzulClaro,
    tertiary = Branco,
    quartenary = CianoAzul,
    quinary = AzulLetra,
    senary = AzulSuperClaro,
    sextiary = Cinza,
    septiary = VermelhoTelha,
    octiary = AmareloClaro1,
    nonary = AmareloClaro2,
    denary = Laranja,
    undary = Azul2,
    doenary = Cinza2,
    treenary = Cinza3,
    fourtenary = AzulNoite,
    fiftenary = CinzaAzul,
    sixenary = Black,
    sevenenary = AmareloEstrela,
    eightenary = Rosinha,
    nineenary = VerdeEscuro,
    tenenary = Vermelho,
    elevenenary = LaranjaMedalha,
    twelveenary = Purple40,
    thirteenenary = PurpleGrey40,
    fourteenenary = Pink40


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun TCCMobileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}