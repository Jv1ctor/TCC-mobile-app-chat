package com.example.tccmobile.ui.components.bibliotecaria

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.AzulSuperClaro

@Composable
fun CardInfosTickets(
    modifier: Modifier = Modifier,
    label: String,
    count: String,
    icon: ImageVector
) {
    Column (
        modifier = modifier
            .background(
                color = Color.White.copy(alpha = 0.1F), // Fundo translúcido
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp) // Padding interno do card
    ) {
        // Linha do Ícone e Label
        Row (verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Número (Contador)
        Text(
            text = count,
            color = AzulSuperClaro,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF003366) // Define um fundo azul escuro para o Preview
@Composable
fun CardInfosTicketsPreview() {
    // Um Box apenas para dar um padding e simular o card isolado
    Box(modifier = Modifier.padding(16.dp)) {
        CardInfosTickets(
            modifier = Modifier.width(110.dp), // Simulando uma largura fixa para visualização
            label = "Total",
            count = "4",
            icon = Icons.Outlined.Description
        )
    }
}