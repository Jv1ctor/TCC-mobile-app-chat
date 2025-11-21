package com.example.tccmobile.ui.components.utils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.navigation.Routes
// Imports do Colors.kt
import com.example.tccmobile.ui.theme.HeaderBlue
import com.example.tccmobile.ui.theme.FooterActiveBg
import com.example.tccmobile.ui.theme.FooterActiveText

@Composable
fun AppBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderBlue)
            .padding(vertical = 12.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            text = "Meus Envios",
            icon = Icons.Outlined.Description,
            isActive = currentRoute == Routes.HOME,
            onClick = { if (currentRoute != Routes.HOME) onNavigate(Routes.HOME) }
        )

        BottomNavItem(
            text = "Perfil",
            icon = Icons.Outlined.Person,
            isActive = currentRoute == "perfil",
            onClick = { if (currentRoute != "perfil") onNavigate("perfil") }
        )
    }
}

@Composable
private fun BottomNavItem(
    text: String,
    icon: ImageVector,
    isActive: Boolean,
    onClick: () -> Unit
) {
    // Agora usando as variáveis corretas do Colors.kt
    val containerColor = if (isActive) FooterActiveBg else Color.Transparent
    val contentColor = if (isActive) FooterActiveText else Color.White

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = contentColor)
        Text(
            text = text,
            color = contentColor,
            fontSize = 12.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
        )
    }
}