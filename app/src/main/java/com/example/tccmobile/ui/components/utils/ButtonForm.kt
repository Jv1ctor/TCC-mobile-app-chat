package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tccmobile.ui.theme.AzulbackgroundButtonForm

@Composable
fun ButtonForm(
    modifier: Modifier = Modifier,
    text: String,
    icon: (@Composable (() -> Unit))? = null,
    backgroundColor: Color = AzulbackgroundButtonForm,
    contentColor: Color = Color.White,
    cornerRadius: Int = 12,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        )
    ) {

        if (icon != null) {
            icon()
        }

        if (icon != null) {
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(text)
    }
}

@Preview
@Composable
fun ButtonFormPreview() {
    ButtonForm(
        modifier = Modifier.fillMaxWidth(),
        text = "Entrar",
        backgroundColor = Color(0xFF007BFF), //importar cor do Color theme
        cornerRadius = 16,
        icon = {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                tint = Color.White
            )
        },
        onClick = {}
    )
}