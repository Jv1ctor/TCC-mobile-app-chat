package com.example.tccmobile.ui.screens.registerScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    MainBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderForm(modifier = Modifier.padding(top = 32.dp))

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ButtonForm(
                        modifier = Modifier.weight(1f),
                        text = "Login",
                        backgroundColor = Color.White,
                        contentColor = DarkBlueButton,
                        icon = { Icon(Icons.Default.Person, contentDescription = null, tint = DarkBlueButton) },
                        onClick = onNavigateToLogin // Navega para o login
                    )
                    ButtonForm(
                        modifier = Modifier.weight(1f),
                        text = "Cadastrar-se",
                        backgroundColor = DarkBlueButton,
                        contentColor = TextPrimary,
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        onClick = { /* Já está na tela */ }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                BoxForm {
                    InputForm(
                        label = "Nome Completo",
                        placeholder = "Digite seu nome completo",
                        value = uiState.nome,
                        onValueChange = viewModel::onNomeChange)

                    Spacer(modifier = Modifier.height(16.dp))

                    InputForm(
                        label = "Matrícula Institucional",
                        placeholder = "Digite sua matrícula",
                        value = uiState.matricula,
                        onValueChange = viewModel::onMatriculaChange,
                        keyboardType = KeyboardType.Number)

                    Spacer(modifier = Modifier.height(16.dp))

                    InputForm(
                        label = "E-mail Institucional",
                        placeholder = "seu.nome@edu.unifor.br",
                        value = uiState.email,
                        onValueChange = viewModel::onEmailChange,
                        keyboardType = KeyboardType.Email)

                    Spacer(modifier = Modifier.height(16.dp))

                    InputForm(
                        label = "Telefone",
                        placeholder = "(00) 98765-4321",
                        value = uiState.telefone,
                        onValueChange = viewModel::onTelefoneChange,
                        keyboardType = KeyboardType.Phone)

                    Spacer(modifier = Modifier.height(16.dp))

                    InputForm(
                        label = "Curso",
                        placeholder = "Ex: Engenharia de Software",
                        value = uiState.curso,
                        onValueChange = viewModel::onCursoChange)

                    Spacer(modifier = Modifier.height(16.dp))

                    InputForm(
                        label = "Senha",
                        placeholder = "Digite sua senha",
                        value = uiState.senha,
                        onValueChange = viewModel::onSenhaChange,
                        isPassword = true)
                    Spacer(modifier = Modifier.height(16.dp))
                    InputForm(
                        label = "Confirmar Senha",
                        placeholder = "Confirme sua senha",
                        value = uiState.confirmarSenha,
                        onValueChange = viewModel::onConfirmarSenhaChange,
                        isPassword = true)

                    Spacer(modifier = Modifier.height(24.dp))

                    if (uiState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        ButtonForm(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Cadastrar",
                            backgroundColor = PrimaryBlue,
                            icon = { Icon(Icons.Default.Person, contentDescription = null) },
                            onClick = { viewModel.onRegisterClick(onRegisterSuccess) }
                        )
                    }

                    uiState.registerError?.let { error ->
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = error, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            FooterLogo(modifier = Modifier.padding(bottom = 16.dp))
        }
    }
}