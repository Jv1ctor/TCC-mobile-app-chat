package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.DarkBlue
import com.example.tccmobile.ui.theme.LightBlue

// mudei o nome para "Generic" porque agora ele serve pra tudo
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericBottomSheet(
    title: String, // o título agora é dinâmico (ex: "Opções", "Transferir", "Alterar Status")
    onDismiss: () -> Unit, // pra fechar a janela
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 50.dp)
        ) {
            // o título
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            content()
        }
    }
}

@Composable
fun OptionItem(
    icon: ImageVector,
    text: String,
    subtitle: String? = null, // adicionei um subtítulo opcional, pq na tela de alterar status tem
    backgroundColor: Color,
    iconColor: Color,
    onClick: () -> Unit,
    enabled: Boolean = true // adicionei o parametro enabled
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    if (enabled) backgroundColor else Color(0xFFE0E0E0), // cinza quando desabilitado
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (enabled) iconColor else Color(0xFFBDBDBD) // ícone cinza quando desabilitado
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = text,
                fontSize = 16.sp,
                color = if (enabled) Color.Black else Color(0xFF9E9E9E), // texto cinza quando desabilitado
                fontWeight = FontWeight.Medium
            )

            if (subtitle != null) {// se tiver subtítulo, mostra ele embaixo (pra tela de status)
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = if (enabled) Color.Gray else Color(0xFFBDBDBD),
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}



@Composable
fun StatusSheet(
    onFinishTicket: () -> Unit,
    onDimiss: () -> Unit,
    show: Boolean = false
) {
    if(!show) return
    GenericBottomSheet(
        title = "Opções do Ticket",
        onDismiss = onDimiss
    ) {
        OptionItem(
            icon = Icons.Outlined.Description,
            text = "Alterar Status do Documento",
            backgroundColor = LightBlue,
            iconColor = DarkBlue,
            onClick = {},
            enabled = false // feature não implementada ainda
        )
        Spacer(modifier = Modifier.height(8.dp))
        OptionItem(
            icon = Icons.Outlined.Person,
            text = "Transferir Ticket",
            backgroundColor = Color(0xFFF3E5F5), // roxinho
            iconColor = Color(0xFF9C27B0),
            onClick = {},
            enabled = false // feature não implementada ainda
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color(0xFFF0F0F0), thickness = 1.dp)
        Spacer(modifier = Modifier.height(8.dp))
        OptionItem(
            icon = Icons.Default.CheckCircle,
            text = "Finalizar Ticket",
            backgroundColor = Color(0xFFE8F5E9), // verdinho
            iconColor = Color(0xFF4CAF50),
            onClick = onFinishTicket
        )
    }
}

