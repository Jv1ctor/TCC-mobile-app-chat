package com.example.tccmobile.ui.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.components.utils.StatusBadge
import com.example.tccmobile.ui.components.utils.StatusBadgeModel
import com.example.tccmobile.ui.theme.DarkBlue
import com.example.tccmobile.ui.theme.LightBlue
import com.example.tccmobile.ui.theme.Orange
import com.example.tccmobile.ui.theme.SuperLightOrange
import com.example.tccmobile.ui.theme.White

@Composable
fun HeaderStudentChat(
    ticketId: String,
    title: String,
    subtitle: String,
    badges: List<StatusBadgeModel>,
    onBackClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBlue)
            .padding(20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                onClick = onBackClick,
                color = Color(0xFF183863),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(32.dp),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Voltar",
                        color = White,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "#$ticketId",
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )

        Text(
            text = title,
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )

        Text(
            text = subtitle,
            color = LightBlue,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)// spacedBy coloca um espacinho automático entre cada badge
        ) {
            badges.forEach { badge ->
                StatusBadge(
                    text = badge.text,
                    backgroundColor = badge.backgroundColor,
                    textColor = badge.textColor
                )
            }
        }
    }
}

@Preview
@Composable
fun HeaderStudentChatPreview() {
    HeaderStudentChat(
        ticketId = "12313",
        title = "Desenvolvimento de Sistema Web",
        subtitle = "Engenharia de Software",
        badges = listOf(
            StatusBadgeModel("Aberto", LightBlue, DarkBlue),
            StatusBadgeModel("Pendente", SuperLightOrange, Orange),
            StatusBadgeModel("teste", Color.Red, Color.White)
        )
    )
}