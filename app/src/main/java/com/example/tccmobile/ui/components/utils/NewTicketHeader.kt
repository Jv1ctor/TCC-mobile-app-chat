package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tccmobile.ui.theme.AzulLetra
import com.example.tccmobile.ui.theme.BlueEscuro
import com.example.tccmobile.ui.theme.AzulSuperClaro
import com.example.tccmobile.ui.theme.Branco

@Composable
fun NewTicketHeader(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AzulLetra)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .clickable(onClick = onBackClick)
                    .background(
                        color = BlueEscuro,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Branco
                    )
                    Text(
                        text = "Voltar",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Branco
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AzulLetra)
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Novo Ticket",
                style = MaterialTheme.typography.headlineSmall,
                color = Branco,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Inicie o processo de análise do seu TCC",
                style = MaterialTheme.typography.titleMedium,
                color = AzulSuperClaro
            )
        }
    }
}
