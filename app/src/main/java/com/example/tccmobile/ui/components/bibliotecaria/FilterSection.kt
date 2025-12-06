package com.example.tccmobile.ui.components.bibliotecaria

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.BorderFilterGray
import com.example.tccmobile.ui.theme.DarkBlue
import com.example.tccmobile.ui.theme.LightBlue
import com.example.tccmobile.ui.theme.White
import com.example.tccmobile.ui.theme.Gray


@Composable
fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val backgroundColor = if (isSelected) DarkBlue else Color.Transparent // se ta selecionado, fundo fica azul escuro
    val contentColor = if (isSelected) White else DarkBlue // texto azul escuro quando não selecionado

    val border = if (isSelected) null else BorderStroke(1.dp, BorderFilterGray)

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        contentColor = contentColor,
        border = border,
        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp) // espacinho pra não grudar no vizinho
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) { //linha 1
            // aqui a gente chama os botões um por um na primeira linha
            FilterChip(
                text = "Todos (4)",
                isSelected = selectedFilter == "Todos (4)",
                onClick = { onFilterSelected("Todos (4)") }
            )
            FilterChip(
                text = "Abertos (2)",
                isSelected = selectedFilter == "Abertos (2)",
                onClick = { onFilterSelected("Abertos (2)") }
            )
            FilterChip(
                text = "Analisados (1)",
                isSelected = selectedFilter == "Analisados (1)",
                onClick = { onFilterSelected("Analisados (1)") }
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) { //linha 2
            // o botão que sobrou fica aqui embaixo
            FilterChip(
                text = "Fechados (1)",
                isSelected = selectedFilter == "Fechados (1)",
                onClick = { onFilterSelected("Fechados (1)") }
            )
        }
    }
}


//@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
//@Composable
//fun FilterSectionPreview() {
//    // Como o componente agora não tem mais estado interno,
//    // precisamos criar um estado aqui fora para a preview funcionar.
//    var selectedFilter by remember { mutableStateOf("Todos (4)") }
//    var searchQuery by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        FilterSection(
//            selectedFilter = selectedFilter,
//            onFilterSelected = { selectedFilter = it },
//            searchQuery = searchQuery,
//            onSearchQueryChange = { searchQuery = it }
//        )
//    }
//}
