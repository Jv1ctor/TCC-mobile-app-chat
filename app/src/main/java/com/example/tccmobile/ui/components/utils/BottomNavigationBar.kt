package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.DarkBlue
import com.example.tccmobile.ui.theme.LightBlue


data class BottomNavItem(// ela guarda só os dados: nome, ícone e pra onde o botão leva.
    val label: String,
    val icon: ImageVector,
    val route: String,
    val onClick: (String) -> Unit
)

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,   // lista de botões
    currentRoute: String,         // recebe qual tela está aberta agora
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(DarkBlue),


        horizontalArrangement = Arrangement.SpaceEvenly,// "spaceevenly" divide o espaço igualmente pra todo mundo
        verticalAlignment = Alignment.CenterVertically
    ) {

        items.forEach { item ->// aqui é um loop: "pra cada item que veio na lista, desenha isso aqui..."
            val isSelected = item.route == currentRoute
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isSelected) LightBlue else Color.Transparent)
                    .clickable { item.onClick(item.route) }
                    .padding(vertical = 8.dp, horizontal = 20.dp)
            ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) DarkBlue else Color.White, // lógica de cor do ícone: azul escuro se selecionado, branco se não
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp)) // espacinho entre o ícone e o texto

                        Text(
                            text = item.label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,// lógica do texto: negrito se selecionado, normal se não
                            color = if (isSelected) DarkBlue else Color.White // lógica da cor do texto: igual a do ícone
                        )
                }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun BottomNavigationPreview() {
//    BottomNavigationBar(
//        items = listOf(
//            BottomNavItem("Meus Envios", androidx.compose.material.icons.Icons.Outlined.Description, "home"),
//            BottomNavItem("Perfil", androidx.compose.material.icons.Icons.Outlined.Person, "profile")
//        ),
//        currentRoute = "home", // fingindo que que ta na "home"
//        onItemClick = {}
//    )
//}
