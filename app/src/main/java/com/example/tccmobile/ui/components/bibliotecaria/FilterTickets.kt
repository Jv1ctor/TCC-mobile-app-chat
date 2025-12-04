package com.example.tccmobile.ui.components.bibliotecaria

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.tccmobile.ui.theme.HeaderBlue
import com.example.tccmobile.ui.theme.White

@Composable
fun FilterChip(
    label: String,
    count: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) HeaderBlue else Color.Transparent
    val contentColor = if (isSelected) White else Color.Black
    val borderColor = if (isSelected) HeaderBlue else Color.LightGray.copy(alpha = 0.5f)

    Surface (
        modifier = Modifier
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp), // Borda arredondada suave
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor),
    ) {
        Row (
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$label ($count)",
                color = contentColor,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}

// 2. O Container que organiza os Chips (Usando FlowRow para quebrar linha)
@Composable
fun FilterSection(
    modifier: Modifier = Modifier
) {
    // Dados de exemplo
    val filters = listOf(
        Triple("Todos", 4, true),
        Triple("Abertos", 2, false),
        Triple("Analisados", 1, false),
        Triple("Fechados", 1, false)
    )

    // LazyRow permite rolar horizontalmente se os itens estourarem a tela
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Espaço entre os chips
        contentPadding = PaddingValues(horizontal = 24.dp) // Padding lateral igual ao do seu Header
    ) {
        items(filters) { (label, count, isSelected) ->
            FilterChip(
                label = label,
                count = count,
                isSelected = isSelected,
                onClick = { /* Lógica de filtro */ }
            )
        }
    }
}

// --- Preview para visualizar ---
@Preview(showBackground = true)
@Composable
fun FilterSectionPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        FilterSection()
    }
}