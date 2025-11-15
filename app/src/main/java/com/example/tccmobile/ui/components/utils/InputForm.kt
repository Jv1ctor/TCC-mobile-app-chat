package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TextField
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputForm(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    helperText: String? = null,
    cornerRadius: Int = 14,
    helperTextColor: Color = Color.White.copy(alpha = 0.7f)
) {

    Column (modifier = modifier) {

        Text(
            text = label,
            color = Color.White,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            shape = RoundedCornerShape(cornerRadius),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,

                cursorColor = Color(0xFF1A3E6C),

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        if (helperText != null) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = helperText,
                color = helperTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputFormPreview() {
    Column(
        modifier = Modifier
            .background(Color(0xFF002D72))
            .padding(20.dp)
            .fillMaxWidth()
    ) {

        // 1º input (sem helper text)
        InputForm(
            label = "Nome Completo",
            placeholder = "Digite seu nome",
            value = "",
            onValueChange = {},
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 2º input (com helper text)
        InputForm(
            label = "Curso *",
            placeholder = "Ex: Engenharia de Software",
            value = "",
            onValueChange = {},
            helperText = "Informe seu curso de graduação",
            helperTextColor = Color.Red.copy(alpha = 0.7f)
        )
    }
}
