package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
// Imports necessários para o ícone
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.TextSecondary

@Composable
fun InputForm(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    helperText: String? = null,
    cornerRadius: Int = 14,
    helperTextColor: Color = Color.White.copy(alpha = 0.7f),

    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    var passwordVisible by remember { mutableStateOf(false) }

    val visualTransformation = if (isPassword && !passwordVisible)
        PasswordVisualTransformation()
    else
        VisualTransformation.None

    val finalKeyboardType = if (isPassword) KeyboardType.Password else keyboardType

    val icon = if (isPassword) {
        if (passwordVisible) Icons.Filled.Visibility
        else Icons.Filled.VisibilityOff
    } else {
        null
    }

    Column (modifier = modifier) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextSecondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            },
            shape = RoundedCornerShape(cornerRadius),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                cursorColor = Color(0xFF1A3E6C), //importar cor do Color theme
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,

            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = finalKeyboardType),

            trailingIcon = {
                if (icon != null) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = if (passwordVisible) "Esconder senha" else "Mostrar senha",
                            tint = Color.Gray // Cor do ícone
                        )
                    }
                }
            }
        )

        if (helperText != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = helperText,
                color = helperTextColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}