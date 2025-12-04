package com.example.tccmobile.ui.components.bibliotecaria

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Imports do seu projeto
import com.example.tccmobile.ui.theme.HeaderBlue

@Composable
fun SearchBarTickets(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchChange,
        modifier = modifier
            .fillMaxWidth(),
        placeholder = {
            Text(
                text = "Buscar por tema, aluno ou curso...",
                color = Color.Gray,
                fontSize = 14.sp
            )
        },
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .padding(start = 16.dp),
                imageVector = Icons.Outlined.Search,
                contentDescription = "Ícone de busca",
                tint = Color.Gray
            )
        },
        shape = RoundedCornerShape(16.dp), // Arredondamento conforme a imagem
        singleLine = true, // Garante que o texto não quebre linha
        colors = OutlinedTextFieldDefaults.colors(
            // Cores do Container (Fundo)
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,

            // Cores da Borda
            focusedBorderColor = HeaderBlue, // Fica azul (sua cor de tema) quando clica
            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f), // Cinza suave quando parado

            // Cores do Texto/Cursor
            cursorColor = HeaderBlue,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun SearchBarTicketsPreview() {
    // Estado falso apenas para visualização no Preview
    val (text, setText) = remember { mutableStateOf("") }

    SearchBarTickets(
        modifier = Modifier.padding(16.dp),
        searchText = text,
        onSearchChange = setText
    )
}