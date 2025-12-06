package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
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
import com.example.tccmobile.ui.theme.Orange
import com.example.tccmobile.ui.theme.SuperLightOrange


//basicamente esse componente vai mostrar o status do ticket
@Composable
fun StatusBadge(
    text: String,
    backgroundColor: Color = SuperLightOrange,
    textColor: Color = Orange,
    icon: ImageVector? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
        Text(
            text = text,
            color = textColor,
            style = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun StatusBadgePreview() {
    StatusBadge(
        text = "Pendente",
        backgroundColor = SuperLightOrange,
        textColor = Orange,
        icon = Icons.Outlined.Schedule
    )
}