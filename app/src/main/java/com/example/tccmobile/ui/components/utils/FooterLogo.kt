package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tccmobile.R

@Composable
fun FooterLogo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.unifor_logo_footer), // <- Use o nome do seu arquivo PNG
        contentDescription = "Logo Universidade de Fortaleza",
        modifier = modifier.size(width = 240.dp, height = 180.dp) // Ajuste o tamanho
    )
}