package com.example.tccmobile.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color

@Composable
fun Teste(){
    val colors = MaterialTheme.colorScheme
    // val extra = LocalExtendedColors.current  // se tiver cores extras

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            var name by remember { mutableStateOf("") }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name", color = colors.primary) },

                // aplica borda e texto com cores do seu tema
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = Color(0xFF0282EA)
                (
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colors.secondary,
                    focusedLabelColor = colors.primary,
                    unfocusedLabelColor = colors.secondary,
                    cursorColor = colors.primary,
                )
            ))
        }

    }
}