package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.Orange
import com.example.tccmobile.ui.theme.StatusContainerConcluido
import com.example.tccmobile.ui.theme.StatusContainerPendente
import com.example.tccmobile.ui.theme.StatusTextConcluido
import com.example.tccmobile.ui.theme.StatusTextPendente


//basicamente esse componente vai mostrar o status do ticket
data class StatusBadgeModel(
    val text: String,
    val backgroundColor: Color,
    val textColor: Color
)

@Composable
fun StatusBadge(
    text: String,
    backgroundColor: Color = StatusContainerConcluido,
    textColor: Color = StatusTextConcluido,
    icon: ImageVector? = null
) {
    Box(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
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
        backgroundColor = StatusContainerPendente,
        textColor = StatusTextPendente
    )
}