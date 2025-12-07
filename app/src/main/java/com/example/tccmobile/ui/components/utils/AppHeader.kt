package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.AzulSuperClaro
import com.example.tccmobile.ui.screens.studentTicketsScreen.TicketTagStatus
import com.example.tccmobile.ui.theme.AzulLetra

@Composable
fun AppHeader(
    title: String,
    subtitle: String? = null,
    statusTags: List<TicketTagStatus> = emptyList(),
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    actionContent: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AzulLetra)
            .padding(top = 32.dp, bottom = 24.dp, start = 16.dp, end = 24.dp)
    ) {
        if (showBackButton || actionContent != null) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (showBackButton) {
                    Row(
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .clickable(onClick = onBackClick),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Voltar", color = Color.White, fontSize = 12.sp)
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                if (actionContent != null) {
                    actionContent()
                }
            }
        }

        Column(modifier = Modifier.padding(horizontal = 4.dp)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 26.sp
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    color = AzulSuperClaro,
                    fontSize = 14.sp
                )
            }

            if (statusTags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    statusTags.forEach { tag ->
                        HeaderStatusChip(tag)
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderStatusChip(tag: TicketTagStatus) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(tag.containerColor, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = tag.label,
            color = tag.contentColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}