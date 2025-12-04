package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tccmobile.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.AzulClaro

@Composable
fun HeaderForm(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.chapeuformatura), // ⬅ nome do PNG
            contentDescription = "Logo TCC Mobile",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "TCC Mobile",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Unifor - Biblioteca",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderFormPreview() {
    Box(
        modifier = Modifier
            .background(AzulClaro) //mudei para importar a cor de Theme/Color
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HeaderForm()
    }
}