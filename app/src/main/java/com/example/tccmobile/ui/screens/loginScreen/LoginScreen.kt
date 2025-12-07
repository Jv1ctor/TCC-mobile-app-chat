package com.example.tccmobile.ui.screens.loginScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tccmobile.ui.components.MainBackground
import com.example.tccmobile.ui.components.utils.*
import com.example.tccmobile.ui.theme.DarkBlueButton
import com.example.tccmobile.ui.theme.PrimaryBlue
import com.example.tccmobile.ui.theme.TextPrimary

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: (isStudent: Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    MainBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderForm(modifier = Modifier.padding(top = 32.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f, fill = false) // Empurra o footer para baixo
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ButtonForm(
                        modifier = Modifier.weight(1f),
                        text = "Login",
                        backgroundColor = DarkBlueButton,
                        contentColor = TextPrimary,
                        icon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null
                            )},
                        onClick = { /* Já está na tela */ }
                    )
                    ButtonForm(
                        modifier = Modifier.weight(1f),
                        text = "Cadastrar-se",
                        backgroundColor = Color.White,
                        contentColor = DarkBlueButton,
                        icon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = DarkBlueButton)
                               },
                        onClick = onNavigateToRegister // Navega para o cadastro
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                BoxForm {
                    InputForm(
                        label = "Matrícula",
                        placeholder = "Digite sua matrícula",
                        value = uiState.matricula,
                        onValueChange = viewModel::onMatriculaChange,
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InputForm(
                        label = "Senha",
                        placeholder = "Digite sua senha",
                        value = uiState.senha,
                        onValueChange = viewModel::onSenhaChange,
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    if (uiState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        ButtonForm(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Entrar",
                            backgroundColor = PrimaryBlue,
                            icon = { Icon(Icons.Default.ArrowForward, contentDescription = null) },
                            onClick = { viewModel.onLoginClick(onLoginSuccess) }
                        )
                    }

                    uiState.loginError?.let { error ->
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = error, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }

            // 3. Footer
            FooterLogo(modifier = Modifier.padding(bottom = 16.dp))
        }
    }
}